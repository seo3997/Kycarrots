package com.whomade.kycarrots.framework.interceptor;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.mgt.branch.service.MgtBranchService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class BranchInterceptor implements HandlerInterceptor {

    @Resource(name = "mgtBranchService")
    private MgtBranchService mgtBranchService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String serverName = request.getServerName();
        HttpSession session = request.getSession();

        DataMap param = new DataMap();
        param.put("domainUrl", serverName);

        // 매번 DB에서 최신 지점 정보를 조회하여 실시간 반영
        DataMap branchInfo = mgtBranchService.selectBranchByDomain(param);

        if (branchInfo != null) {
            // 조회한 최신 정보를 세션과 request에 모두 담아 JSP에서 즉시 참조 가능하도록 함
            session.setAttribute("branchInfo", branchInfo);
            session.setAttribute("BRANCH_ID", branchInfo.getLong("BRANCH_ID"));
            request.setAttribute("branchInfo", branchInfo);
        } else {
            log.warn("No active branch found for domain: {}", serverName);
        }

        return true;
    }
}
