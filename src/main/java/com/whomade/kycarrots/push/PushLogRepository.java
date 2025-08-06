package com.whomade.kycarrots.push;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushLogRepository extends JpaRepository<PushLog, Long> {

    // 예시: 특정 사용자 알림 리스트
    //List<PushLog> findByTARGETUSERIDOrderBySENTATDesc(String userId);

    // 예시: 읽지 않은 알림
    //List<PushLog> findByTARGETUSERIDAndREADYN(String userId, String readYn);
}
