package com.whomade.kycarrots.mgt.survey.vo;


public class TbSurvey {

	private	String SURVEY_ID ;
	private	String SURVEY_TITLE ;
	private	String OPENED ;
	private	String CLOSED;
	private	String ADMINEMAIL;
	private	String ADMINPID;
	private	String ACCESSRESULTSRESTRICTION;
	private	String RESULTSPASSWORD;
	private	String ENTRYRESTRICTION;
	private	String ENTRYPASSWORD;
	private	String MEMBERS;
	private	String ONEENTRYONLY;
	private	String SHOWBORDER;
	private	String EXPORTDELIMITER;
	private	String EXPORTINCLUDEQUESTIONS	;
	private	String IP;
	private	String BROWSERID;
	private	String BGCOLOR;
	private	String TEXTCOLOR;
	private	String FONT_SIZE;
	private	String HEADER;
	private	String FOOTER;
	private	String BASEHREF;
	private	String USERCSS;
	private	String DIVIDER;
	private	String EXITPAGETEXT_ISHTML;
	private	String EXITPAGETEXT;
	private	String NUMBERRODUCTIONTEXT;
	private int NUMENTRIES;
	public Boolean singleEntry=false;
	private int QUESTION_CNT;
	
	
	public String getSURVEY_ID() {
		return SURVEY_ID;
	}
	public void setSURVEY_ID(String sURVEY_ID) {
		SURVEY_ID = sURVEY_ID;
	}
	public String getSURVEY_TITLE() {
		return SURVEY_TITLE;
	}
	public void setSURVEY_TITLE(String sURVEY_TITLE) {
		SURVEY_TITLE = sURVEY_TITLE;
	}
	public String getOPENED() {
		return (OPENED==null) ? "" :OPENED;
	}
	public void setOPENED(String oPENED) {
		OPENED = oPENED;
	}
	public String getCLOSED() {
		return (CLOSED==null) ? "" :CLOSED;
	}
	public void setCLOSED(String cLOSED) {
		CLOSED = cLOSED;
	}
	public String getADMINEMAIL() {
		return ADMINEMAIL;
	}
	public void setADMINEMAIL(String aDMINEMAIL) {
		ADMINEMAIL = aDMINEMAIL;
	}
	public String getADMINPID() {
		return ADMINPID;
	}
	public void setADMINPID(String aDMINPID) {
		ADMINPID = aDMINPID;
	}
	public String getACCESSRESULTSRESTRICTION() {
		return ACCESSRESULTSRESTRICTION;
	}
	public void setACCESSRESULTSRESTRICTION(String aCCESSRESULTSRESTRICTION) {
		ACCESSRESULTSRESTRICTION = aCCESSRESULTSRESTRICTION;
	}
	public String getRESULTSPASSWORD() {
		return RESULTSPASSWORD;
	}
	public void setRESULTSPASSWORD(String rESULTSPASSWORD) {
		RESULTSPASSWORD = rESULTSPASSWORD;
	}
	public String getENTRYRESTRICTION() {
		return ENTRYRESTRICTION;
	}
	public void setENTRYRESTRICTION(String eNTRYRESTRICTION) {
		ENTRYRESTRICTION = eNTRYRESTRICTION;
	}
	public String getENTRYPASSWORD() {
		return ENTRYPASSWORD;
	}
	public void setENTRYPASSWORD(String eNTRYPASSWORD) {
		ENTRYPASSWORD = eNTRYPASSWORD;
	}
	public String getMEMBERS() {
		return MEMBERS;
	}
	public void setMEMBERS(String mEMBERS) {
		MEMBERS = mEMBERS;
	}
	public String getONEENTRYONLY() {
		return ONEENTRYONLY;
	}
	public void setONEENTRYONLY(String oNEENTRYONLY) {
		ONEENTRYONLY = oNEENTRYONLY;
	}
	public String getSHOWBORDER() {
		return SHOWBORDER;
	}
	public void setSHOWBORDER(String sHOWBORDER) {
		SHOWBORDER = sHOWBORDER;
	}
	public String getEXPORTDELIMITER() {
		if (EXPORTDELIMITER==null) EXPORTDELIMITER="";
		return EXPORTDELIMITER;
	}
	public void setEXPORTDELIMITER(String eXPORTDELIMITER) {
		EXPORTDELIMITER = eXPORTDELIMITER;
	}
	public String getEXPORTINCLUDEQUESTIONS() {
		if(EXPORTINCLUDEQUESTIONS==null ) EXPORTINCLUDEQUESTIONS="";
		return EXPORTINCLUDEQUESTIONS;
	}
	public void setEXPORTINCLUDEQUESTIONS(String eXPORTINCLUDEQUESTIONS) {
		EXPORTINCLUDEQUESTIONS = eXPORTINCLUDEQUESTIONS;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getBROWSERID() {
		return BROWSERID;
	}
	public void setBROWSERID(String bROWSERID) {
		BROWSERID = bROWSERID;
	}
	public String getBGCOLOR() {
		return BGCOLOR;
	}
	public void setBGCOLOR(String bGCOLOR) {
		BGCOLOR = bGCOLOR;
	}
	public String getTEXTCOLOR() {
		return TEXTCOLOR;
	}
	public void setTEXTCOLOR(String tEXTCOLOR) {
		TEXTCOLOR = tEXTCOLOR;
	}
	public String getFONT_SIZE() {
		return FONT_SIZE;
	}
	public void setFONT_SIZE(String fONT_SIZE) {
		FONT_SIZE = fONT_SIZE;
	}
	public String getHEADER() {
		return HEADER;
	}
	public void setHEADER(String hEADER) {
		HEADER = hEADER;
	}
	public String getFOOTER() {
		return FOOTER;
	}
	public void setFOOTER(String fOOTER) {
		FOOTER = fOOTER;
	}
	public String getBASEHREF() {
		return BASEHREF;
	}
	public void setBASEHREF(String bASEHREF) {
		BASEHREF = bASEHREF;
	}
	public String getUSERCSS() {
		return USERCSS;
	}
	public void setUSERCSS(String uSERCSS) {
		USERCSS = uSERCSS;
	}
	public String getDIVIDER() {
		return DIVIDER;
	}
	public void setDIVIDER(String dIVIDER) {
		DIVIDER = dIVIDER;
	}
	public String getEXITPAGETEXT_ISHTML() {
		return EXITPAGETEXT_ISHTML;
	}
	public void setEXITPAGETEXT_ISHTML(String eXITPAGETEXT_ISHTML) {
		EXITPAGETEXT_ISHTML = eXITPAGETEXT_ISHTML;
	}
	public String getEXITPAGETEXT() {
		return EXITPAGETEXT;
	}
	public void setEXITPAGETEXT(String eXITPAGETEXT) {
		EXITPAGETEXT = eXITPAGETEXT;
	}
	public String getNUMBERRODUCTIONTEXT() {
		return NUMBERRODUCTIONTEXT;
	}
	public void setNUMBERRODUCTIONTEXT(String nUMBERRODUCTIONTEXT) {
		NUMBERRODUCTIONTEXT = nUMBERRODUCTIONTEXT;
	}

	  public String getStatusAsText () {
		    if ( this.getOPENED ().equals("") ) {
		      return "in development";
		    }
		    else {
		      if ( this.getCLOSED ().equals("") ) {
		        return "opened on"+" " + this.getOPENED () + "</font>";
		      }
		      else {
		        return "closed on"+" " + this.getCLOSED () + "</font>";
		      }
		    }
	}
	public int getNUMENTRIES() {
		return NUMENTRIES;
	}
	public void setNUMENTRIES(int nUMENTRIES) {
		NUMENTRIES = nUMENTRIES;
	}
	public Boolean getSingleEntry() {
		return singleEntry;
	}
	public void setSingleEntry(Boolean singleEntry) {
		this.singleEntry = singleEntry;
	}
	public int getQUESTION_CNT() {
		return QUESTION_CNT;
	}
	public void setQUESTION_CNT(int qUESTION_CNT) {
		QUESTION_CNT = qUESTION_CNT;
	}
	

 
}