/**
 * 
 *
 * 1. FileName : pageNavigationUtil.java
 * 2. Package : egovframework.framework.common.util
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2013. 8. 26. 오후 1:45:02
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2013. 8. 26. :            : 신규 개발.
 */

package com.whomade.kycarrots.framework.common.page.util;

import com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : pageNavigationUtil.java
 * 3. Package  : egovframework.framework.common.util
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 8. 26. 오후 1:45:02
 * </PRE>
 */

 public class pageNavigationUtil {

	private static Log log = LogFactory.getLog(pageNavigationUtil.class);

	/**
	 * <PRE>
	 * 1. MethodName : pageNavigationUtil
	 * 2. ClassName  : pageNavigationUtil
	 * 3. Comment   : 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 8. 26. 오후 1:45:02
	 * </PRE>
	 */
	public pageNavigationUtil() {
		// TODO Auto-generated constructor stub
	}

	public static DataMap createNavigationInfo(ModelMap model, DataMap dataMap) {

		pageNavigationVo pnv = new pageNavigationVo();
		
		// 임의의 currentPage input name을 설정
		pnv.setPageInputName(dataMap.getString("pageInputName", "currentPage"));
		pnv.setPageCallFunction(dataMap.getString("pageCallFunction", "fnGoPage"));

		pnv.setTotalCount(dataMap.getInt("totalCount"));
		pnv.setRowPerPage(Integer.parseInt(dataMap.getString("rowPerPage", Const.defRowPerPage)));
		pnv.setCurrentPage(Integer.parseInt(dataMap.getString(pnv.getPageInputName(), Const.defCurrentPage)));
		pnv.setNaviCount(Integer.parseInt(dataMap.getString("naviCount", Const.defNaviCount)));

		int lastPage = pnv.getTotalCount() / pnv.getRowPerPage();
		int dummyPage = 0;
		if (pnv.getTotalCount() % pnv.getRowPerPage() > 0) { //나머지가 존재할경우 1페이지 추가		
			dummyPage = 1;
		}
		pnv.setLastPage(lastPage + dummyPage);
		int plusPage = pnv.getCurrentPage() % pnv.getNaviCount() == 0 ? -1 * pnv.getNaviCount() + 1 : 1;
		pnv.setFirstPage(pnv.getCurrentPage() / pnv.getNaviCount() * pnv.getNaviCount() + plusPage);
		pnv.setCurrDataNo(pnv.getTotalCount() - ((pnv.getCurrentPage() - 1) * pnv.getRowPerPage()));

		model.addAttribute("pageNavigationVo", pnv);

		dataMap.put("limitStart", (pnv.getCurrentPage() - 1) * pnv.getRowPerPage());
		dataMap.put("limitCount", pnv.getRowPerPage());

		// mysql 경우 limit 이용시 끝에는 한페이지에 보여줄 게시물수만 있으면 된다.
		dataMap.put("limitEnd", pnv.getRowPerPage());
		//SooHyun.Seo 2017.12.20 tibero는 페이지별 rownum 계산 해야함
		//dataMap.put("limitEnd", pnv.getRowPerPage()*pnv.getCurrentPage());

		String naviBar = createNavigationBar(pnv);
		// 페이지 관련 태그 스트링 등록
		model.addAttribute("navigationBar", naviBar);

		return dataMap;
	}

	public static String createNavigationBar(pageNavigationVo pnv) {

		StringBuffer rtnStr = new StringBuffer();
		int nextPage = 0;

		if (pnv.getTotalCount() > 0) {
			rtnStr.append("<nav aria-label=\"Page navigation\"><ul class=\"pagination justify-content-center\">");

			if (pnv.getFirstPage() + pnv.getNaviCount() > pnv.getLastPage()) {
				nextPage = pnv.getLastPage() + 1;
			} else {
				nextPage = pnv.getFirstPage() + pnv.getNaviCount();
			}

			rtnStr.append("<li class=\"page-item\"><a class=\"page-link\" href=\"#\" title=\"맨앞으로가기\" onclick=\"" + pnv.getPageCallFunction() + "('1'); return false;\"><span aria-hidden=\"true\">&laquo;</span></a></li>");

			if (pnv.getFirstPage() > pnv.getNaviCount()) {
				rtnStr.append("<li class=\"page-item\"><a class=\"page-link\" href=\"#\" title=\"앞으로가기\" onclick=\"" + pnv.getPageCallFunction() + "('" + (pnv.getFirstPage() - 1) + "'); return false;\"><span aria-hidden=\"true\">&lt;</span></a></li>");
			}
			//			else
			//			{
			//				rtnStr.append("<li class=\"ui-state-default ui-state-disabled\"><span class=\"ui-icon ui-icon-seek-prev\"></span></li>");
			//			}

			for (int i = pnv.getFirstPage(); i < nextPage; i++) {
				if (pnv.getCurrentPage() == i) {
					rtnStr.append("<li class=\"page-item  active\"><a class=\"page-link\" href=\"#\" onclick=\"" + pnv.getPageCallFunction() + "('" + i + "'); return false;\">" + i + "<span class=\"sr-only\">(current page)</span></a></li>");
				} else {
					rtnStr.append("<li class=\"page-item \"><a class=\"page-link\" href=\"#\" onclick=\"" + pnv.getPageCallFunction() + "('" + i + "'); return false;\">" + i + "</a></li>");
				}
			}

			if (pnv.getFirstPage() + pnv.getNaviCount() - 1 < pnv.getLastPage()) {
				rtnStr.append("<li class=\"page-item\"><a class=\"page-link\" href=\"#\" title=\"뒤로가기\" onclick=\"" + pnv.getPageCallFunction() + "('" + (pnv.getFirstPage() + pnv.getNaviCount()) + "'); return false;\"><span aria-hidden=\"true\">&gt;</span></a></li>");
			}
			/*else
			{
				out.print("<li class=\"ui-state-default ui-state-disabled\"><span class=\"ui-icon ui-icon-seek-next\"></span></li>");
			}*/

			rtnStr.append("<li class=\"page-item\"><a class=\"page-link\" href=\"#\" title=\"맨뒤로가기\" onclick=\"" + pnv.getPageCallFunction() + "('" + pnv.getLastPage() + "'); return false;\"><span aria-hidden=\"true\">&raquo;</span></a></li>");

			rtnStr.append("</ul></nav>");

			rtnStr.append("<input type=\"hidden\" name=\"" + pnv.getPageInputName() + "\" id=\"" + pnv.getPageInputName() + "\" value=\"" + pnv.getCurrentPage() + "\"/>");
		}
		
		log.debug("#######rtnStr.toString():"+rtnStr.toString());
		return rtnStr.toString();
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : pageNavigationUtil
	 * 2. ClassName  : pageNavigationUtil
	 * 3. Comment   : 
	 * 4. 작성자    : 정현준
	 * 5. 작성일    : 2021. 1. 28. 오후 3:59:02
	 * </PRE>
	 */
	public static DataMap createNavigationInfoToCustom(ModelMap model, DataMap dataMap , String rowPerPage) {

		pageNavigationVo pnv = new pageNavigationVo();
		
		// 임의의 currentPage input name을 설정
		pnv.setPageInputName(dataMap.getString("pageInputName", "currentPage"));
		pnv.setPageCallFunction(dataMap.getString("pageCallFunction", "fnGoPage"));

		pnv.setTotalCount(dataMap.getInt("totalCount"));
		pnv.setRowPerPage(Integer.parseInt(dataMap.getString("rowPerPage", rowPerPage)));
		pnv.setCurrentPage(Integer.parseInt(dataMap.getString(pnv.getPageInputName(), Const.defCurrentPage)));
		pnv.setNaviCount(Integer.parseInt(dataMap.getString("naviCount", Const.defNaviCount)));

		int lastPage = pnv.getTotalCount() / pnv.getRowPerPage();
		int dummyPage = 0;
		if (pnv.getTotalCount() % pnv.getRowPerPage() > 0) { //나머지가 존재할경우 1페이지 추가		
			dummyPage = 1;
		}
		pnv.setLastPage(lastPage + dummyPage);
		int plusPage = pnv.getCurrentPage() % pnv.getNaviCount() == 0 ? -1 * pnv.getNaviCount() + 1 : 1;
		pnv.setFirstPage(pnv.getCurrentPage() / pnv.getNaviCount() * pnv.getNaviCount() + plusPage);
		pnv.setCurrDataNo(pnv.getTotalCount() - ((pnv.getCurrentPage() - 1) * pnv.getRowPerPage()));

		model.addAttribute("pageNavigationVo", pnv);

		dataMap.put("limitStart", (pnv.getCurrentPage() - 1) * pnv.getRowPerPage());
		dataMap.put("limitCount", pnv.getRowPerPage());

		// mysql 경우 limit 이용시 끝에는 한페이지에 보여줄 게시물수만 있으면 된다.
		dataMap.put("limitEnd", pnv.getRowPerPage());
		//SooHyun.Seo 2017.12.20 tibero는 페이지별 rownum 계산 해야함
		//dataMap.put("limitEnd", pnv.getRowPerPage()*pnv.getCurrentPage());

		String naviBar = createNavigationBar(pnv);
		// 페이지 관련 태그 스트링 등록
		model.addAttribute("navigationBar", naviBar);

		return dataMap;
	}
	
}
