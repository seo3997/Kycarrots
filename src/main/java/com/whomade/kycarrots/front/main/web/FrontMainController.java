package com.whomade.kycarrots.front.main.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.PlanFUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.front.main.service.FrontMainService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class FrontMainController {

	private static Log log = LogFactory.getLog(FrontMainController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "frontMainService")
	private FrontMainService frontMainService;

	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	

	@RequestMapping(value = "/")
	public String home(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		codeParam.put("group_id", "R010300");
		List sectorComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("sectorComboStr", sectorComboStr);

		codeParam.put("group_id", "R010320");
		List dayComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("dayComboStr", dayComboStr);
	
		codeParam.put("group_id", "R010340");
		List invest1ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("invest1ComboStr", invest1ComboStr);

		codeParam.put("group_id", "R010350");
		List invest2ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("invest2ComboStr", invest2ComboStr);

		codeParam.put("group_id", "R010360");
		List invest3ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("invest3ComboStr", invest3ComboStr);
		
		codeParam.put("group_id", "R010370");
		List invest4ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("invest4ComboStr", invest4ComboStr);

		codeParam.put("group_id", "R010380");
		List bizCdComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("bizCdComboStr", bizCdComboStr);

		codeParam.put("group_id", "R010390");
		List loan1ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("loan1ComboStr", loan1ComboStr);
		
		codeParam.put("group_id", "R010400");
		List loan2ComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("loan2ComboStr", loan2ComboStr);

		codeParam.put("group_id", "R010410");
		List mCostComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("mCostComboStr", mCostComboStr);
		
		codeParam.put("group_id", "R010420");
		List sCostComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("sCostComboStr", sCostComboStr);
		
		DataMap tmpMap;
		JSONArray jArray = new JSONArray();
		JSONObject jsonO = null;

		for(int i = 0; i < mCostComboStr.size(); i++){
			tmpMap = (DataMap)mCostComboStr.get(i);
			jsonO = new JSONObject();
			jsonO.put("CODE", tmpMap.getString("CODE"));
			jsonO.put("CODE_NM", tmpMap.getString("CODE_NM"));
			jsonO.put("ATTRB_1", tmpMap.getString("ATTRB_1"));
			jsonO.put("ATTRB_2", tmpMap.getString("ATTRB_2"));
			jArray.add(jsonO);
		}
		model.addAttribute("mCostStr", jArray.toString());

		JSONArray jArrayS = new JSONArray();
		JSONObject jsonOS = null;

		for(int i = 0; i < sCostComboStr.size(); i++){
			tmpMap = (DataMap)sCostComboStr.get(i);
			jsonOS = new JSONObject();
			jsonOS.put("CODE", tmpMap.getString("CODE"));
			jsonOS.put("CODE_NM", tmpMap.getString("CODE_NM"));
			jsonOS.put("ATTRB_1", tmpMap.getString("ATTRB_1"));
			jsonOS.put("ATTRB_2", tmpMap.getString("ATTRB_2"));
			jArrayS.add(jsonOS);
		}

		model.addAttribute("sCostStr", jArrayS.toString());
		

		jArrayS = new JSONArray();
		for(int i = 0; i < invest1ComboStr.size(); i++){
			tmpMap = (DataMap)invest1ComboStr.get(i);
			jsonOS = new JSONObject();
			jsonOS.put("CODE", tmpMap.getString("CODE"));
			jsonOS.put("CODE_NM", tmpMap.getString("CODE_NM"));
			jsonOS.put("ATTRB_1", tmpMap.getString("ATTRB_1"));
			jsonOS.put("ATTRB_2", tmpMap.getString("ATTRB_2"));
			jArrayS.add(jsonOS);
		}

		model.addAttribute("invest1Str", jArrayS.toString());

		jArrayS = new JSONArray();
		for(int i = 0; i < invest2ComboStr.size(); i++){
			tmpMap = (DataMap)invest2ComboStr.get(i);
			jsonOS = new JSONObject();
			jsonOS.put("CODE", tmpMap.getString("CODE"));
			jsonOS.put("CODE_NM", tmpMap.getString("CODE_NM"));
			jsonOS.put("ATTRB_1", tmpMap.getString("ATTRB_1"));
			jsonOS.put("ATTRB_2", tmpMap.getString("ATTRB_2"));
			jArrayS.add(jsonOS);
		}

		model.addAttribute("invest2Str", jArrayS.toString());

		
		
		
		System.out.println("****************param:"+param.getString("PLANF_ID"));
		DataMap planFMap = new DataMap();
		if(!"".equals(param.getString("PLANF_ID"))) {  //상세보기
			planFMap = frontMainService.selectPlanF(param);
		}
		
		model.addAttribute("param",param);
		model.addAttribute("planFMap",planFMap);
		
		return "home";
	}
	
	@RequestMapping(value = "/front/salePlan.do")
	public String salesPlan(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		codeParam.put("group_id", "R010300");
		List sectorComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("sectorComboStr", sectorComboStr);

		codeParam.put("group_id", "R010320");
		List dayComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("dayComboStr", dayComboStr);

		
		
		model.addAttribute("param",param);
		return "front/salePlan";
	}

	@RequestMapping(value = "/front/docPlan.do")
	public String docPlan(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		codeParam.put("group_id", "R010510");
		List typeBizComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("typeBizComboStr", typeBizComboStr);

		codeParam.put("group_id", "R010520");
		List cateBizComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("cateBizComboStr", cateBizComboStr);

		codeParam.put("group_id", "R010530");
		List sectinLabelsComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("sectinLabelsComboStr", sectinLabelsComboStr);

		
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
		
		//return 상태
		
		resultJSON.put("typeBizs", typeBizComboStr);
		resultJSON.put("cateBizs", cateBizComboStr);
		resultJSON.put("sectinLabels", sectinLabelsComboStr);
		param.put("bizComboStr", resultJSON.toString());

		
		
		model.addAttribute("param",param);
		return "front/docPlan";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: savePlanFAjax
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: planF저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/front/savePlanFAjax.do")
	public void savePlanFAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		response.setContentType("text/html; charset=utf-8");

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("USER_NO", userInfoVo.getUserNo());		//사용자NO 
		param.put("REGISTER_NO", userInfoVo.getUserNo());	//사용자NO 
		param.put("UPDUSR_NO", userInfoVo.getUserNo());		//사용자NO 

		param.put("PLANF_SALE", request.getParameter("PLANF_SALE"));		//수익성검토 JSON

		//System.out.println("param:"+param);
		
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();

		if("".equals(param.getString("PLANF_ID"))) {
			frontMainService.insertPlanF(param);
		}else {
			frontMainService.updatePlanF(param);
		}
		
		//return 상태
		returnMsg="저장되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("resultStats", resultStats);
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}	

	/**
	 * <PRE>
	 * 1. MethodName 	: calPlanFAjax
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: planF손익분기점
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/front/calPlanFAjax.do")
	public void calPlanFAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		response.setContentType("text/html; charset=utf-8");

		param.put("PLANF_SALE", request.getParameter("PLANF_SALE"));		//수익성검토 JSON

		String sPlanfSale ="";
		sPlanfSale = request.getParameter("PLANF_SALE");
		
		String sWorkCd ="";
		sWorkCd = request.getParameter("INDUSTRY_CD");

		String sTragetProfit ="0";
		sTragetProfit = request.getParameter("MON_TARGET_PROFIT");
        if (sTragetProfit.equals(""))  sTragetProfit ="0";
		
		System.out.println("sPlanfSale:"+sPlanfSale);

		JSONObject planFJSON 	= JSONObject.fromObject(sPlanfSale);
		JSONObject jSALE 		= planFJSON.getJSONObject("SALE");
		JSONObject jCOST 		= planFJSON.getJSONObject("COST");
		JSONObject jJOB			= planFJSON.getJSONObject("JOB");
		JSONObject jINVEST 		= planFJSON.getJSONObject("INVEST");
		JSONObject jLOAN 		= planFJSON.getJSONObject("LOAN");
		JSONObject jMCOST 		= planFJSON.getJSONObject("MCOST");
		JSONObject jSCOST 		= planFJSON.getJSONObject("SCOST");
		
		DataMap planF = new DataMap();
		//년도별 매출금액 	
		Long[] yMenuAmtArr= PlanFUtil.getItemYAmtArr(jSALE.getJSONArray("menuY"),"YMenuAmt");
		planF.put("YMenuAmt", yMenuAmtArr);

		//년도별 매출 원가 
		Long[] yCostAmtArr= PlanFUtil.getItemYAmtArr(jCOST.getJSONArray("costY"),"YCostAmt");
		planF.put("YCostAmt", yCostAmtArr);
		
		//년도별 매출 총이익
		Long[] yInCome3AmtArr = PlanFUtil.getItemYMinusArr(yMenuAmtArr,yCostAmtArr);
		planF.put("yInCome3Amt", yInCome3AmtArr);
		
		//년도별 판매 관리비
		Long[] ySCostAmtArr= PlanFUtil.getItemYAmtArr(jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		planF.put("YSCostAmt", ySCostAmtArr);
		
		//년도별 영업이익
		Long[] yInCome6AmtArr= PlanFUtil.getItemYMinusArr(yInCome3AmtArr,ySCostAmtArr);
		planF.put("yInCome6Amt", yInCome6AmtArr);
		
		//년도별 영업외 비용
		Long[] loanRateAmtArr= PlanFUtil.getItemYAmtArr(jLOAN.getJSONArray("loan02"),"loanRateAmt");
		planF.put("YLonaRateAmt", loanRateAmtArr);
		
		//경상이익
		Long[] yInCome9AmtArr= PlanFUtil.getItemYMinusArr(yInCome6AmtArr,loanRateAmtArr);
		planF.put("yInCome9Amt", yInCome9AmtArr);
		
		//비유동 부채 
		Long[] loanYear02Arr= PlanFUtil.getItemYAmtAccArr(jLOAN.getJSONArray("loan02"),"loanYear");
		planF.put("loanYear02Amt", loanYear02Arr);

		//자본금 
		Long[] loanYear01Arr= PlanFUtil.getItemYAmtAccArr(jLOAN.getJSONArray("loan01"),"loanYear");
		planF.put("loanYear01Amt", loanYear01Arr);

		//이익잉여금 
		Long[] financial5Arr= PlanFUtil.getItemYPlusAccArr(yInCome9AmtArr);
		planF.put("financial5Amt", financial5Arr);
		
		//자본총계
		Long[] yFinancialT2AmtArr= PlanFUtil.getItemYPlusArr(loanYear01Arr,financial5Arr);
		planF.put("financialT2Amt", yFinancialT2AmtArr);

		//부채와자본총계
		Long[] yFinancialT3AmtArr= PlanFUtil.getItemYPlusArr(loanYear02Arr,yFinancialT2AmtArr);
		planF.put("financialT3Amt", yFinancialT3AmtArr);
		
		//비유동자산
		//제조경비 감가상각 비용
		//년도별 판매 관리비
		
		//투자계획 제조부분 합
		Long yInvestMAmt= PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest01"),"investPrice")
						+PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest03"),"investPrice")
						+PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest05"),"investPrice")
						+PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest07"),"investPrice");

		//투자계획 판매부분 합
		Long yInvestSAmt = PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest02"),"investPrice")
						+PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest04"),"investPrice")
		                +PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest06"),"investPrice")
		                +PlanFUtil.getItemSumAmtArr(jINVEST.getJSONArray("invest08"),"investPrice");
		//투자금액합
		Long yInvestAmt = yInvestMAmt+yInvestSAmt;
		
		System.out.println("yInvestAmt:"+yInvestSAmt);

		Long[] yInvestSAmtArr = new Long[]{yInvestSAmt, yInvestSAmt, yInvestSAmt};
		
		//제조경비 판매관리비 감각상각 비용
		Long[] yDpreMAmtArr		= PlanFUtil.getItemYAmtFindArr(jMCOST.getJSONArray("MCost"),"MCostTitle","감가상각비",jMCOST.getJSONArray("MCostY"),"YMCostAmtYear");
		Long[] yDpreSAmtArr		= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostTitle","감가상각비",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		Long[] yDpreAmtArr 		= PlanFUtil.getItemYPlusArr(yDpreMAmtArr,yDpreSAmtArr);
		
		Long[] yFinancial2AmtArr =  PlanFUtil.getItemYMinusArr(yInvestSAmtArr,yDpreAmtArr);
		planF.put("Financial2Amt", yFinancial2AmtArr);
		
		
		//유동자산
		Long[] yFinancial1AmtArr= PlanFUtil.getItemYMinusArr(yFinancialT3AmtArr,yFinancial2AmtArr);
		planF.put("Financial1Amt", yFinancial1AmtArr);

		//자산총계
		Long[] yFinancialT11AmtArr= PlanFUtil.getItemYPlusArr(yFinancial2AmtArr,yFinancial1AmtArr);
		planF.put("FinancialT11Amt", yFinancialT11AmtArr);


		//변동비
		//년도별 재료비 + 재조경비 + 판매경비 	
		Long[] yYCostAmtArr 	= PlanFUtil.getItemYAmtArr(jCOST.getJSONArray("costY"),"YCostAmt");
		Long[] yYSaleMAmtArr	= PlanFUtil.getItemYAmtFindArr(jMCOST.getJSONArray("MCost"),"MCostCd","매출액",jMCOST.getJSONArray("MCostY"),"YMCostAmtYear");
		Long[] yYSaleSAmtArr	= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostCd","매출액",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");

		Long[] yYSaleAmtArr= PlanFUtil.getItemYPlusArr(yYSaleMAmtArr,yYSaleSAmtArr);
		Long[] yBreakPoint2AmtArr= PlanFUtil.getItemYPlusArr(yYCostAmtArr,yYSaleAmtArr);
		
		planF.put("BreakPoint2Amt", yBreakPoint2AmtArr);

		//한계이익
		Long[] yBreakPoint3AmtArr= PlanFUtil.getItemYMinusArr(yMenuAmtArr,yBreakPoint2AmtArr);
		planF.put("BreakPoint3Amt", yBreakPoint3AmtArr);
		
		//한계이익율 
		double [] BreakPoint4RateArr= PlanFUtil.getRateArr(yBreakPoint3AmtArr,yMenuAmtArr,1);
		planF.put("BreakPoint4Rate", BreakPoint4RateArr);
		
		
		
		//고정비 (제조인건비+제조경비인건비+제조경비월정액+제조경비기준액+판매경비인건비+판매경비월정액+판매경비기준액+이자비용)
		//년도별 제조인건비 	
		Long[] yJob01AmtArr		= PlanFUtil.getItemYAmtArr(jJOB.getJSONArray("job01Y"),"YJobAmt");
		Long[] yJob02AmtArr		= PlanFUtil.getItemYAmtArr(jJOB.getJSONArray("job02Y"),"YJobAmt");
		
		
		Long[] yYJob01MAmtArr	= PlanFUtil.getItemYAmtFindArr(jMCOST.getJSONArray("MCost"),"MCostCd","월정액",jMCOST.getJSONArray("MCostY"),"YMCostAmtYear");
		Long[] yYJob02MAmtArr	= PlanFUtil.getItemYAmtFindArr(jMCOST.getJSONArray("MCost"),"MCostCd","인건비",jMCOST.getJSONArray("MCostY"),"YMCostAmtYear");
		Long[] yYJob03MAmtArr	= PlanFUtil.getItemYAmtFindArr(jMCOST.getJSONArray("MCost"),"MCostCd","기준액",jMCOST.getJSONArray("MCostY"),"YMCostAmtYear");

		Long[] yYJobMAmtArrTemp = PlanFUtil.getItemYPlusArr(yYJob01MAmtArr,yYJob02MAmtArr);
		Long[] yYJobMAmtArr     = PlanFUtil.getItemYPlusArr(yYJobMAmtArrTemp,yYJob03MAmtArr);
		

		Long[] yYJob01SAmtArr	= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostCd","월정액",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		Long[] yYJob02SAmtArr	= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostCd","인건비",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		Long[] yYJob03SAmtArr	= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostCd","기준액",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");

		Long[] yYJobSAmtArrTemp	= PlanFUtil.getItemYPlusArr(yYJob01SAmtArr,yYJob02SAmtArr);
		Long[] yYJobSAmtArr   	= PlanFUtil.getItemYPlusArr(yYJobSAmtArrTemp,yYJob03SAmtArr);
		
		Long[] yYJobMSAmtArr     = PlanFUtil.getItemYPlusArr(yYJobMAmtArr,yYJobSAmtArr);
		
		Long[] yloanRateAmtAmtArr= PlanFUtil.getItemYAmtArr(jLOAN.getJSONArray("loan02"),"loanRateAmt");
		
		Long[] yloanRateAmtAmtArrTemp = PlanFUtil.getItemYPlusArr(yYJobMSAmtArr,yloanRateAmtAmtArr);
		
		
		Long[] yBreakPoint5AmtArr = PlanFUtil.getItemYPlusArr(yJob01AmtArr,yloanRateAmtAmtArrTemp);
		planF.put("BreakPoint5Amt", yBreakPoint5AmtArr);

		//손익분기점 
		Long [] BreakPoint6AmtArr= PlanFUtil.deceAmtArr(yBreakPoint5AmtArr,yBreakPoint3AmtArr,yMenuAmtArr);
		planF.put("BreakPoint6Amt", BreakPoint6AmtArr);

		//목표매출액 
		//목료 이익금액
		Long lsTragetProfit = Long.parseLong(sTragetProfit)*10*12;
		Long[] BreakPoint8AmtArr = new Long[]{lsTragetProfit, lsTragetProfit, lsTragetProfit};
		planF.put("BreakPoint8Amt", BreakPoint8AmtArr);
		
		Long[] yBreakPoint5AmtArrTemp = PlanFUtil.getItemYPlusArr(yBreakPoint5AmtArr,BreakPoint8AmtArr);
		Long[] BreakPoint9AmrArr= PlanFUtil.deceAmtArr(yBreakPoint5AmtArrTemp,yBreakPoint3AmtArr,yMenuAmtArr);
		planF.put("BreakPoint9Amt", BreakPoint9AmrArr);

		
		//1.투자금액회전율
		//투자금액 
		int bizCd=1;
		Long[] yInvestAmtArr = new Long[]{yInvestAmt, yInvestAmt, yInvestAmt};
		planF.put("Biz1C4Amt", yInvestSAmtArr);
		//산출값
		double [] Biz1C1RateDArr = PlanFUtil.setBizRate(bizCd,yMenuAmtArr,yInvestAmtArr,1);
		String [] Biz1C1RateArr  = PlanFUtil.setBizRateAsString(Biz1C1RateDArr,1);
		planF.put("Biz1C1Rate", Biz1C1RateArr);
		//검토의견
		String [] Biz1C2CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz1C1RateDArr,bizCd);
		planF.put("Biz1C2Cd", Biz1C2CdArr);
		
		//2.매출액 영어 이익율
		//영업이익
		bizCd=2;
		//감가상각비용,대손상가비용
		Long[] yDBedSAmtArr		= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostTitle","대손상각비",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		Long[] yDpreAmtArrTemp  = PlanFUtil.getItemYPlusArr(yDpreAmtArr,yDBedSAmtArr);
		Long[] yBiz2C4AmtArr    = PlanFUtil.getItemYMinusArr(yInCome6AmtArr,yDpreAmtArrTemp);
		planF.put("Biz2C4Amt", yBiz2C4AmtArr);
		
		//산출값
		double [] Biz2C1RateDArr = PlanFUtil.setBizRate(bizCd,yBiz2C4AmtArr,yMenuAmtArr,1);
		String [] Biz2C1RateArr  = PlanFUtil.setBizRateAsString(Biz2C1RateDArr,1);
		planF.put("Biz2C1Rate", Biz2C1RateArr);
		//검토의견
		String [] Biz2C2CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz2C1RateDArr,bizCd);
		planF.put("Biz2C2Cd", Biz2C2CdArr);
		
		//3.총자산 영업이익율
		bizCd=3;
		//산출값
		double [] Biz3C1RateDArr = PlanFUtil.setBizRate(bizCd,yBiz2C4AmtArr,yInvestAmtArr,1);
		String [] Biz3C1RateArr  = PlanFUtil.setBizRateAsString(Biz3C1RateDArr,1);
		planF.put("Biz3C1Rate", Biz3C1RateArr);
		//검토의견
		String [] Biz3C2CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz3C1RateDArr,bizCd);
		planF.put("Biz3C2Cd", Biz3C2CdArr);

		//4.인건비비율
		bizCd=4;
		Long[] yJobAmtArr  = PlanFUtil.getItemYPlusArr(yJob01AmtArr,yJob02AmtArr);
		planF.put("JobAmt", yJobAmtArr);
		//산출값
		double [] Biz4C1RateDArr = PlanFUtil.setBizRate(bizCd,yJobAmtArr,yMenuAmtArr,1);
		String [] Biz4C1RateArr  = PlanFUtil.setBizRateAsString(Biz4C1RateDArr,1);
		planF.put("Biz4C1Rate", Biz4C1RateArr);
		//검토의견
		String [] Biz4C3CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz4C1RateDArr,bizCd);
		planF.put("Biz4C2Cd", Biz4C3CdArr);
		
		//5.임차율비율
		bizCd=5;
		//월매출액
		Long[] mMenuAmtArr      = PlanFUtil.getMonthAmt(yMenuAmtArr);
		planF.put("mMenuAmt", mMenuAmtArr);

		//월임차료
		Long[] yRentSAmtArr		= PlanFUtil.getItemYAmtFindArr(jSCOST.getJSONArray("SCost"),"SCostTitle","지급임차료",jSCOST.getJSONArray("SCostY"),"YSCostAmtYear");
		Long[] mRentSAmtArr     = PlanFUtil.getMonthAmt(yRentSAmtArr);
		planF.put("RentSAmt", mRentSAmtArr);

		//산출값
		double [] Biz5C1RateDArr = PlanFUtil.setBizRate(bizCd,mRentSAmtArr,mMenuAmtArr,1);
		String [] Biz5C1RateArr  = PlanFUtil.setBizRateAsString(Biz5C1RateDArr,1);
		planF.put("Biz5C1Rate", Biz5C1RateArr);
		//검토의견
		String [] Biz5C3CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz5C1RateDArr,bizCd);
		planF.put("Biz5C2Cd", Biz5C3CdArr);
		
		//6.자기자본비율
		bizCd=6;
		//산출값
		double [] Biz6C1RateDArr = PlanFUtil.setBizRate(bizCd,yFinancialT2AmtArr,yFinancialT11AmtArr,1);
		String [] Biz6C1RateArr  = PlanFUtil.setBizRateAsString(Biz6C1RateDArr,1);
		planF.put("Biz6C1Rate", Biz6C1RateArr);
		//검토의견
		String [] Biz6C3CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz6C1RateDArr,bizCd);
		planF.put("Biz6C2Cd", Biz6C3CdArr);

		//7.손익분기점률
		bizCd=7;
		//산출값
		double [] Biz7C1RateDArr = PlanFUtil.setBizRate(bizCd,BreakPoint6AmtArr,yMenuAmtArr,1);
		String [] Biz7C1RateArr  = PlanFUtil.setBizRateAsString(Biz7C1RateDArr,1);
		planF.put("Biz7C1Rate", Biz7C1RateArr);
		//검토의견
		String [] Biz7C3CdArr  = PlanFUtil.setBizTrueString(sWorkCd,Biz7C1RateDArr,bizCd);
		planF.put("Biz7C2Cd", Biz7C3CdArr);

		
		
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
		
		//return 상태
		returnMsg="계산되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		
		resultJSON.put("resultStats", resultStats);
		resultJSON.put("planF", planF);

		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectPlanF
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: planF 이동
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/selectPlanF.do")
	public String selectPlanF(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		System.out.println("param:"+param.getString("PLANF_ID"));

		String sReturnUrl = "";
		
		sReturnUrl = "redirect:/?PLANF_ID="+param.getString("PLANF_ID");

		return sReturnUrl;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: listPlanFAjax
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: planF리스트 불러오기
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/front/listPlanFAjax.do")
	public void listPlanFAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		response.setContentType("text/html; charset=utf-8");
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("USER_NO", userInfoVo.getUserNo());		//사용자NO 

		//수익분석리스트
		List<DataMap> resultList = frontMainService.selectPlanFList(param);
		
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
		
		//return 상태
		returnMsg="조회되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("resultStats", resultStats);
		resultJSON.put("resultList", resultList);
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}	

}
