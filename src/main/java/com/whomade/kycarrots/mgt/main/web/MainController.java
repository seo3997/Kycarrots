package com.whomade.kycarrots.mgt.main.web;

import java.util.List;
import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.admin.user.service.UserMgtService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.mboard.service.MicroBizBoardService;
import com.whomade.kycarrots.service.product.TnProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.whomade.kycarrots.common.service.CommonCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Resource(name = "userMgtService")
	private UserMgtService userMgtService;

	@Resource(name = "microBizBoardService")
	private MicroBizBoardService boardService;

	@Resource(name = "mgtOrderService")
	private com.whomade.kycarrots.mgt.order.service.MgtOrderService mgtOrderService;

	private final TnProductService tnProductService;
	private final CommonCodeService commonCodeService;

	@Resource(name = "mgtBranchService")
	private com.whomade.kycarrots.mgt.branch.service.MgtBranchService mgtBranchService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/mgt/main/dashBoard.do", method = RequestMethod.GET)
	public String newsmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		DataMap param = RequestUtil.getDataMap(request);

		param.put("ss_user_no", userInfoVo.getUserNo());

		param.put("userNo", userInfoVo.getUserNo());
		param.put("branchId", userInfoVo.getBranchId());

		String memberCode = userInfoVo.getAuthorId();
		param.put("memberCode", memberCode);

		DataMap prductCntMap = tnProductService.getProductStatusCounts(param);
		DataMap dashboardStats = mgtOrderService.selectDashboardStats(param);
		List<DataMap> dashboardOrderList = mgtOrderService.selectDashboardOrderList(param);

		// 본사 정보 조회 (BR_0002)
		DataMap headQuarterParam = new DataMap();
		headQuarterParam.put("branchCode", "BR_0002");
		DataMap headQuarterBranch = mgtBranchService.selectBranchByCode(headQuarterParam);

		// 택배사 코드 조회 (R010660)
		DataMap codeParam = new DataMap();
		codeParam.put("group_id", "R010660");
		List deliveryCompanyList = commonCodeService.selectCodeList(codeParam);

		model.addAttribute("deliveryCompanyList", deliveryCompanyList);
		model.addAttribute("paramin", param);
		model.addAttribute("prductCntMap", prductCntMap);
		model.addAttribute("dashboardStats", dashboardStats);
		model.addAttribute("dashboardOrderList", dashboardOrderList);
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("headQuarterBranch", headQuarterBranch);

		return "admin/main";
	}

}
