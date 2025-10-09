package com.whomade.kycarrots.mgt.member.service;

import javax.annotation.Resource;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.mail.util.mailUtil;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("findMemberService")
public class FindMemberServiceImpl extends EgovAbstractServiceImpl implements FindMemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FindMemberServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;


	/**
	 * <PRE>
	 * 1. MethodName : selectPageListBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 3. 13. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectId(ModelMap model, DataMap param)throws Exception {
		return commonMybatisDao.selectOne("mgt.member.selectId", param);
	}
	
	public DataMap selectPw(ModelMap model, DataMap param)throws Exception {
		return commonMybatisDao.selectOne("mgt.member.selectPw", param);
	}

	public void updatePw(ModelMap model,DataMap param)throws Exception{
		commonMybatisDao.update("mgt.member.updatePw", param);
	}
	
	public void searchUserPassword(DataMap param)throws Exception {
		String mailHost ="";
		String mailId ="seo3997@gmail.com";
		//비밀번호 재설정 안내 메일 발송
		String mContent = "<!DOCTYPE html>\r\n" + 
				"<html lang=\"ko\">\r\n" + 
				"<head>\r\n" + 
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body>\r\n" + 
				"	<div style=\"width:100%; max-width:640px; margin:0 auto; padding:0\">\r\n" + 
				"		<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
				"			<tr>\r\n" + 
				"				<td valign=\"top\" style=\"width:100%; height:415px; padding:65px 0 0 0; text-align:center; background:url('"+mailHost+"/common/new/images/mail/img_mailvisual.jpg')\">\r\n" +
				"					<p style=\"padding:0; margin:0; font-size:0\"><img src='"+mailHost+"/common/new/images/mail/logo_mail.png\' border=\"0\" alt=\"CrowdOh!\" /></p>\r\n" +
				"					<p style=\"padding:0; margin:75px 0 0 0; font-size:0\"><img src='"+mailHost+"/common/new/images/mail/tit_email.png' border=\"0\" alt=\"크라우드 오 비밀번호 재설정 안내메일\" /></p>\r\n" +
				"				</td>\r\n" + 
				"			</tr>\r\n" + 
				"			<tr>\r\n" + 
				"				<td valign=\"top\" style=\"padding:80px 0 0 0; text-align:center; background:#fff\">\r\n" + 
				"					<strong style=\"display:block; font-size:32px; color:#333; font-weight:bold; letter-spacing:-3px\">안녕하세요. "+param.getString("member_name")+" 님</strong>\r\n" + 
				"					<p style=\"margin:20px 0 40px 0; padding:0; font-size:20px; color:#333; letter-spacing:-1px; line-height:30px\">크라우드 소싱 플랫폼 Oh 비밀번호 재설정을 위해 <br>\r\n" + 
				"					아래 <strong style=\"color:#333; font-weight:bold\">비밀번호 재설정 버튼</strong>을 클릭해주세요. <br /></p>\r\n" + 
				"					\r\n" + 
				"					<form action='"+mailHost+":9000/mgt/member/chkSecNo.do' method='post'>\r\n" +
				"					 <input type='hidden' id=\"member_id\" name='member_id'  value=" +param.getString("encrypt_member_id")+ "> \r\n" + 
				"					 <input type='hidden' id=\"sec_no\" name='sec_no' value="+param.getString("sec_no")+"> \r\n" + 
				"					<button type='submit' style='width:170px; height:56px; background:#00cdc1; border:0; border-radius:28px; color:#fff; font-size:16px; font-weight:bold; letter-spacing:-1px'>비밀번호재설정</button>\r\n" +
				"   				</form> "+
				"				</td>\r\n" + 
				"			</tr>\r\n" + 
				"			<tr>\r\n" + 
				"				<td valign=\"top\" style=\"padding:80px 0; text-align:center; background:#fff\">\r\n" + 
				"					<p style=\"margin:0; padding:0; font-size:14px; color:#777; line-height:20px\">이메일 인증 메일은 5분간 유효합니다. <br />5분이 경과한 경우 비밀번호 찾기를 다시 시도해주세요.</p>\r\n" + 
				"				</td>\r\n" + 
				"			</tr>\r\n" + 
				"			<tr>\r\n" + 
				"				<td valign=\"top\" style=\"height:180px; padding:40px 0 0 40px; background:#4f4f4f; font-size:0\"><img src='"+mailHost+"/common/new/images/mail/txt_footer.jpg' alt=\"\" /></td>\r\n" +
				"			</tr>\r\n" + 
				"		</table>\r\n" + 
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>";

		mailUtil.send(mailId, "[KyCarrots] 비밀번호 재설정 메일입니다.", param.getString("member_email"), "text/html", mContent);
	}

}
