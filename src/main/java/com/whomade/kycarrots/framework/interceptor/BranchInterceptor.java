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

        // Already checked for this session?
        DataMap branchInfo = (DataMap) session.getAttribute("branchInfo");

        if (branchInfo == null || !serverName.equals(branchInfo.getString("DOMAIN_URL"))) {
            DataMap param = new DataMap();
            param.put("domainUrl", serverName);

            branchInfo = mgtBranchService.selectBranchByDomain(param);

            if (branchInfo != null) {
                session.setAttribute("branchInfo", branchInfo);
                session.setAttribute("BRANCH_ID", branchInfo.getLong("BRANCH_ID"));
                log.info("Detected Branch: {} (ID: {}) for domain {}",
                        branchInfo.getString("BRANCH_NAME"),
                        branchInfo.get("BRANCH_ID"),
                        serverName);
            } else {
                // If no branch matches, we might want to set a default or just leave it null
                // For now, let's not block access, but some pages might require it
                log.warn("No active branch found for domain: {}", serverName);
            }
        }

        return true;
    }
}
