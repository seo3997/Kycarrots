package com.whomade.kycarrots.rest.appversion;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.mgt.appversion.service.MgtAppVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/appversion")
@Slf4j
public class RestAppVersionController {

    private final MgtAppVersionService mgtAppVersionService;

    /**
     * 앱 버전 체크 API
     * @param osType 플랫폼 (ANDROID / IOS)
     * @param appVersion 현재 앱 버전
     * @return 최신 버전 정보 및 업데이트 필요 여부
     */
    @GetMapping("/check")
    public ResponseEntity<DataMap> checkVersion(
            @RequestParam("osType") String osType,
            @RequestParam("appVersion") String appVersion) {
        
        log.info("[버전체크] 요청 osType: {}, appVersion: {}", osType, appVersion);
        
        try {
            DataMap param = new DataMap();
            param.put("osType", osType.toUpperCase());
            
            DataMap latestInfo = mgtAppVersionService.checkVersion(param);
            
            DataMap result = new DataMap();
            if (latestInfo != null) {
                String latestVersion = latestInfo.getString("LATEST_VERSION");
                String minVersion = latestInfo.getString("MIN_VERSION");
                
                String updateType = "NONE"; // NONE, OPTIONAL, FORCE
                
                if (isVersionLower(appVersion, minVersion)) {
                    updateType = "FORCE";
                } else if (isVersionLower(appVersion, latestVersion)) {
                    updateType = "OPTIONAL";
                }
                
                result.put("updateType", updateType);
                result.put("latestVersion", latestVersion);
                result.put("minVersion", minVersion);
                result.put("updateMsg", latestInfo.getString("UPDATE_MSG"));
                result.put("storeUrl", latestInfo.getString("STORE_URL"));
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("message", "버전 정보를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("버전 체크 중 오류 발생", e);
            DataMap errorResult = new DataMap();
            errorResult.put("success", false);
            errorResult.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(errorResult);
        }
    }

    /**
     * 버전 비교 로직 (v1 < v2 인 경우 true)
     */
    private boolean isVersionLower(String v1, String v2) {
        if (v1 == null || v2 == null) return false;
        
        String[] v1Parts = v1.split("\\.");
        String[] v2Parts = v2.split("\\.");
        
        int length = Math.max(v1Parts.length, v2Parts.length);
        for (int i = 0; i < length; i++) {
            int part1 = i < v1Parts.length ? Integer.parseInt(v1Parts[i].replaceAll("[^0-9]", "")) : 0;
            int part2 = i < v2Parts.length ? Integer.parseInt(v2Parts[i].replaceAll("[^0-9]", "")) : 0;
            
            if (part1 < part2) return true;
            if (part1 > part2) return false;
        }
        return false;
    }
}
