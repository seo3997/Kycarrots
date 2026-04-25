package com.whomade.kycarrots.chat;

import com.whomade.kycarrots.entity.chat.ChatMessageVo;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.service.chat.ChatMessageService;
import com.whomade.kycarrots.service.member.OpUserService;
import com.whomade.kycarrots.service.product.TnProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWsController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final OpUserService opUserService;

    @Autowired
    private WebSocketUserTracker userTracker;
    @Autowired
    private FcmService fcmService;
    @Autowired
    private TnProductService tnProductService;

    @MessageMapping("/chat.enter.{roomId}")
    public void enterRoom(@DestinationVariable String roomId, ChatMessage message) {
        log.info("채팅방 진입 - roomId: {}, userId: {}", roomId, message.getSenderId());
        userTracker.addChatter(message.getSenderId(), roomId);
    }

    @MessageMapping("/chat.exit.{roomId}")
    public void exitRoom(@DestinationVariable String roomId, ChatMessage message) {
        log.info("채팅방 퇴장 신호 수신 - roomId: {}, userId: {}", roomId, message.getSenderId());
        
        // 1. 퇴장하는 유저 정보 조회
        OpUserVO senderInfo = opUserService.fetchFcmToken(message.getSenderId());
        
        if (senderInfo != null) {
            String role = senderInfo.getMemberCode();
            String branchId = senderInfo.getBranchId();
            
            // ROLE_PROJ (본사/센터관리)인 경우: 본사 지점 전체 인원 오프라인 처리
            if (com.whomade.kycarrots.framework.common.constant.Const.ROLE_PROJ.equals(role)) {
                List<OpUserVO> hqStaff = opUserService.selectUsersByBranchAndRole(branchId, com.whomade.kycarrots.framework.common.constant.Const.ROLE_PROJ);
                for (OpUserVO staff : hqStaff) {
                    userTracker.removeChatter(staff.getUserId());
                }
                log.info("퇴장으로 인해 본사(ROLE_PROJ) 그룹의 모든 관련자 채팅 상태를 해제했습니다. (Branch: {})", branchId);
            } 
            // ROLE_SELL (판매지점 직원)인 경우: 해당 지점 전체 인원 오프라인 처리
            else if (com.whomade.kycarrots.framework.common.constant.Const.ROLE_SELL.equals(role)) {
                List<OpUserVO> branchStaff = opUserService.selectUsersByBranchAndRole(branchId, com.whomade.kycarrots.framework.common.constant.Const.ROLE_SELL);
                for (OpUserVO staff : branchStaff) {
                    userTracker.removeChatter(staff.getUserId());
                }
                log.info("퇴장으로 인해 판매점(ROLE_SELL) 그룹의 모든 관련자 채팅 상태를 해제했습니다. (Branch: {})", branchId);
            } 
            else {
                // 일반 구매자 권한 등은 본인만 오프라인 처리
                userTracker.removeChatter(message.getSenderId());
            }
        } else {
            userTracker.removeChatter(message.getSenderId());
        }
    }

    @MessageMapping("/chat.sendsample")
    @SendTo("/topic/room1sample")
    public ChatMessage send(ChatMessage message) {
        System.out.println("수신된 메시지: " + message.getMessage());
        return message; // 구독 중인 모든 클라이언트에게 전송
    }

    @MessageMapping("/chat.send.{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendRoomId(@DestinationVariable String roomId, ChatMessage message) {
        log.info("message.getSenderId(): {}", message.getSenderId());
        log.info("message.getMessage(): {}", message.getMessage());
        log.info("roomId: {}", roomId);
        if (message.getSenderId() == null || message.getMessage() == null) {
            log.warn("필수 필드 누락: senderId 또는 message가 null");
            return null; // 또는 기본 에러 메시지 반환
        }

        try {
            // 발신자의 권한(Group) 정보 가져오기
            OpUserVO senderInfo = opUserService.fetchFcmToken(message.getSenderId());
            String senderGroup = (senderInfo != null) ? senderInfo.getMemberCode() : "ROLE_PUB";
            log.info("발신자 정보 조회 - ID: {}, Group: {}", message.getSenderId(), senderGroup);
            
            // 클라이언트로 보낼 메시지에도 세팅
            message.setSenderGroup(senderGroup);

            ChatMessageVo chatMessageVo = ChatMessageVo.builder()
                    .roomId(roomId)
                    .senderId(message.getSenderId())
                    .senderGroup(senderGroup)
                    .message(message.getMessage())
                    .build();
            chatMessageService.insertChatMessage(chatMessageVo); // MyBatis 방식 저장

            // 1. ChatRoom 정보 조회 (roomId로 또는 productId, buyerId, sellerId로)
            Optional<ChatRoomEntity> chatRoomOpt = chatRoomService.findByRoomId(roomId);
            if (!chatRoomOpt.isPresent()) {
                log.warn("채팅방 정보가 없습니다. roomId: {}", roomId);
                return null;
            }

            String senderId = message.getSenderId();
            ChatRoomEntity chatRoom = chatRoomOpt.get();

            String id1 = chatRoom.getBuyerId(); // Buyer or Branch
            String id2 = chatRoom.getBranchId(); // Branch or HQ

            log.info("채팅방 정보 확인 - roomId: {}, id1: {}, id2: {}, senderId: {}", roomId, id1, id2, senderId);

            String targetTopic = null;
            OpUserVO singleReceiver = null;

            if (senderId.equals(id1)) {
                log.info("발신자가 id1({})입니다. 타겟은 id2({})", id1, id2);
                if ("2".equals(id2)) {
                    log.info("id2가 본사(2)입니다. 본사(ROLE_SELL) 토픽으로 발송합니다.");
                    targetTopic = "ROLE_SELL";
                } else {
                    log.info("id2가 지점({})입니다. 지점(ROLE_PROJ) 토픽으로 발송합니다.", id2);
                    targetTopic = "BRANCH_" + id2 + "_ROLE_PROJ";
                }
            } else {
                log.info("발신자가 id2({})입니다. 타겟은 id1({})", id2, id1);
                if ("2".equals(id2)) {
                    log.info("발신자 id2가 본사(2)입니다. 타겟 지점({})의 (ROLE_PROJ) 토픽으로 발송합니다.", id1);
                    targetTopic = "BRANCH_" + id1 + "_ROLE_PROJ";
                } else {
                    log.info("발신자 id2가 지점입니다. 타겟 단일 구매자({})의 FCM 토큰을 검색합니다.", id1);
                    singleReceiver = opUserService.fetchFcmToken(id1);
                }
            }

            Long productId = chatRoom.getProductId();
            DataMap param = new DataMap();
            param.put("productId", productId);
            param.put("userNo", "0");
            TnProductVo product = tnProductService.getProduct(param);
            String messageTitle = (product != null ? product.getTitle() : "알림") + " 채팅메시지";

            Map<String, String> data = new HashMap<>();
            data.put("targetId", roomId);
            data.put("type", "chat");
            data.put("msg", message.getMessage());
            data.put("title", messageTitle);
            data.put("body", message.getMessage());

            // 1. 토픽 발송 (본사 또는 지점 담당자들)
            if (targetTopic != null) {
                boolean anyStaffOnline = false;
                List<OpUserVO> staffList;

                if ("ROLE_SELL".equals(targetTopic)) {
                    // 본사 담당자 리스트
                    staffList = opUserService.selectUsersByRole("ROLE_SELL");
                } else {
                    // 지점 담당자 리스트 (BRANCH_ID_ROLE_PROJ 형식에서 ID 추출)
                    String branchId = targetTopic.split("_")[1];
                    staffList = opUserService.selectUsersByBranchAndRole(branchId, "ROLE_PROJ");
                }

                if (staffList != null) {
                    for (OpUserVO staff : staffList) {
                        if (userTracker.isUserOnline(staff.getUserId())) {
                            anyStaffOnline = true;
                            break;
                        }
                    }
                }

                if (anyStaffOnline) {
                    log.info("채팅방에 접속 중인 담당자가 있어 토픽 푸시를 건너뜁니다. topic: {}", targetTopic);
                } else {
                    log.info("온라인 담당자가 없어 토픽 푸시를 발송합니다. topic: {}", targetTopic);
                    OpUserVO sender = opUserService.fetchFcmToken(senderId);
                    Long actorNo = (sender != null && sender.getUserNo() != null) ? Long.parseLong(sender.getUserNo()) : 0L;
                    fcmService.sendPushToTopicAndLog(actorNo, targetTopic, messageTitle, message.getMessage(), data, "chat");
                }
            } 
            // 2. 단일 발송 (구매자)
            else if (singleReceiver != null) {
                String rcvUserId = singleReceiver.getUserId();
                if (!userTracker.isUserOnline(rcvUserId)) {
                    log.info("구매자 오프라인 - 단일 푸시 발송: {}", rcvUserId);
                    fcmService.sendPushToUserAndLog(0L, singleReceiver.getDeviceType(), singleReceiver.getUserNo(), 
                        singleReceiver.getPushToken(), messageTitle, message.getMessage(), roomId, "chat", data);
                } else {
                    log.info("구매자가 온라인 상태여서 푸시를 건너뜁니다: {}", rcvUserId);
                }
            }

        } catch (Exception e) {
            log.error("메시지 저장 실패", e);
        }
        return message;
    }
}
