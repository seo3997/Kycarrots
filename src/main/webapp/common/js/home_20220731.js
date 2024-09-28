	    var saleAddFlag="ADD";
	    var jobAddFlag="ADD";
	    var investAddFlag="ADD";
	    var loanAddFlag="ADD";
	    var MCostAddFlag="ADD";
	    var jobCd="1";				//1:제조부분,2:판매관리
	    var investCd="1";			//1:유형자산,2:무형자산,3 기타 유동자산,4 기타 투자자산 
	    var bizCd="1";	    		//1:제조,2:판매 
	    var loanCd="1";				//1:자기자본,2:타인자금
	    var saleIndexNo=0;
	    var costIndexNo=0;
	    var yMenuIndexNo=0;
	    var yCostIndexNo=0;
	    var yYCostIndexNo=0;
	    var yJobIndexNo=0;
	    var jobIndexNo=0;
	    var investIndexNo=0;
	    var mCostIndexNo=0;
	    var loanIndexNo=0;
	
		let industryCd = "3";       //업종구분
		let workDay = 25;			//영업일수
		let monTargetPrice = 0;     //월목표이익
		
		let workAmt = 324000;      	//매출금액
		let jobAmt  = 0;      		//인건비

		//수정시 초기화
		function initUpdate(pIndustryCd,pWorkDay,pMonTargetPrice,pSaleJsonStr) {
			 
			 let PLANF_SALE_JSON = new Object();
	         let SALE;
	         let COST;
	         let JOB;
	         let INVEST;
	         let LOAN;
	         let MCOST;
	         let SCOST;
	         
			 if(""==pSaleJsonStr){
		         SALE 	= "";
		         COST 	= "";
		         JOB 	= "";
		         INVEST = "";
		         LOAN  	= "";
		         MCOST  = "";
		         SCOST  = "";
			 }else{
				 PLANF_SALE_JSON = JSON.parse(pSaleJsonStr);
		         SALE 	= new Object();
		         COST 	= new Object();
		         JOB  	= new Object();
		         INVEST = new Object();
		         LOAN  	= new Object();
		         MCOST  = new Object();
		         SCOST  = new Object();
		         
		         SALE = PLANF_SALE_JSON.SALE;
		         COST = PLANF_SALE_JSON.COST;
		         JOB  = PLANF_SALE_JSON.JOB;
		         INVEST = PLANF_SALE_JSON.INVEST;
		         LOAN = PLANF_SALE_JSON.LOAN;
		         MCOST  = PLANF_SALE_JSON.MCOST;
		         SCOST  = PLANF_SALE_JSON.SCOST;
			 }

			initSale(pIndustryCd,pWorkDay,pMonTargetPrice,SALE);
			initCost(COST);
			initJob(JOB);
			initInvest(INVEST);
			initLoan(LOAN);
			initMCost(MCOST);
			initSCost(SCOST);
		}
		
		//1.예상매출액 초기화
		function initSale(pIndustryCd,pWorkDay,pMonTargetPrice,pJson) {
			//1.1 업종구분
			industryCd = pIndustryCd;
			document.getElementById('sector_se_code_m').value = industryCd;
			initIndustryDis(industryCd);
			//1.2 영업일수
			workDay = pWorkDay;		
			document.getElementById('day_se_code_m').value = workDay;
			//1.3 월목표 이익
			monTargetPrice = getNumber(pMonTargetPrice);

			document.getElementById('inTargetPrice').value = setZeroToSpace(monTargetPrice);
			
			//1.4 매출계획 셋팅
	        let jsonMenu = new Object();

	        if(!pJson==""){
	        	jsonMenu = pJson;
	        } else{
		        jsonMenu = {"menu":[
									 {"MenuTitle":"A메뉴","MenuPrice":"20000","MenuQty":"30"}
									,{"MenuTitle":"B메뉴","MenuPrice":"7000","MenuQty":"40"}
									,{"MenuTitle":"C메뉴","MenuPrice":"3000","MenuQty":"50"}
									,{"MenuTitle":"D메뉴","MenuPrice":"1000","MenuQty":"50"}
							]
					       ,"menuM":[
										 {"MMenuQty":["750","750","750","750","750","750","750","750","750","750","750","750"]
										 ,"MMenuAmt":["150000","150000","150000","150000","150000","150000","150000","150000","150000","150000","150000","150000"]}
										,{"MMenuQty":["1000","1000","1000","1000","1000","1000","1000","1000","1000","1000","1000","1000"]
										 ,"MMenuAmt":["7000","7000","7000","7000","7000","7000","7000","7000","7000","7000","7000","7000"]}
										,{"MMenuQty":["1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250"]
										 ,"MMenuAmt":["3750","3750","3750","3750","3750","3750","3750","3750","3750","3750","3750","3750"]}
										,{"MMenuQty":["1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250"]
										 ,"MMenuAmt":["1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250","1250"]}
									]
					       ,"menuY":[
										 {"YMenuQty":["9000","9000","9000"]
										 ,"YMenuPrice":["20000","20000","20000"]
										 ,"YMenuAmt":["180000","180000","180000"]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":"0"
										 ,"YMenuPriceRate":"0"}
										,{"YMenuQty":["12000","12000","12000"]
										 ,"YMenuPrice":["7000","7000","7000"]
										 ,"YMenuAmt":["84000","84000","84000"]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":"0"
										 ,"YMenuPriceRate":"0"}
										,{"YMenuQty":["15000","15000","15000"]
										 ,"YMenuPrice":["3000","3000","3000"]
										 ,"YMenuAmt":["45000","45000","45000"]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":"0"
										 ,"YMenuPriceRate":"0"}
										,{"YMenuQty":["15000","15000","15000"]
										 ,"YMenuPrice":["1000","1000","1000"]
										 ,"YMenuAmt":["15000","15000","15000"]
										 ,"YMenuCd":"1"
										 ,"YMenuQtyRate":"0"
										 ,"YMenuPriceRate":"0"}
									]
				        };
	
	        }
	        
	        initMenuTable(jsonMenu);
		}
		
		//2.재료비 초기화
		function initCost(pJson) {
			let jsonCost = new Object();
	        if(!pJson==""){
	        	jsonCost = pJson;
	        }else{	
			
				jsonCost = {"cost":[
					        				 {"costRate":"40"}
					        				,{"costRate":"40"}
					        				,{"costRate":"30"}
					        				,{"costRate":"30"}
				        				]
						        ,"costM":[
										 	 {"MCostAmt":["6000","6000","6000","6000","6000","6000","6000","6000","6000","6000","6000","6000"]}
											,{"MCostAmt":["2800","2800","2800","2800","2800","2800","2800","2800","2800","2800","2800","2800"]}
											,{"MCostAmt":["1125","1125","1125","1125","1125","1125","1125","1125","1125","1125","1125","1125"]}
											,{"MCostAmt":["375","375","375","375","375","375","375","375","375","375","375","375"]}
										 ]
		        				,"costY":[
											 {"YCostAmt":["72000","72000","72000"]
					                  		 ,"YCostCd":"1"
							        		 ,"YCostAmtRate":"0"	
											 }
											,{"YCostAmt":["33600","33600","33600"]
						        			 ,"YCostCd":"1"
							        		 ,"YCostAmtRate":"0"	
											}
											,{"YCostAmt":["13500","13500","13500"]
						        			 ,"YCostCd":"1"
							        		 ,"YCostAmtRate":"0"	
											}
											,{"YCostAmt":["4500","4500","4500"]
						        			 ,"YCostCd":"1"
							        		 ,"YCostAmtRate":"0"	
											}
										   ]
		        				};
	        }
	        
	        initCostTable(jsonCost);
		}

		//3.인건비 초기화
		function initJob(pJson) {
			let jsonCost = new Object();
	        if(!pJson==""){
	        	jsonCost = pJson;
	        }else{	
			
				jsonCost = {"job01":[
					        			 {"JobTitle":"신입","JobCnt":"1","JobQty":"3000000"}
				        		    ]
				         	,"job02":[
				         				 {"JobTitle":"신입","JobCnt":"1","JobQty":"3000000"}
				         			 ]
					        ,"job01M":[
									 	 {"MJobAmt":["3000","3000","3000","3000","3000","3000","3000","3000","3000","3000","3000","3000"]}
									 ]
					       ,"job02M":[
									 	 {"MJobAmt":["3000","3000","3000","3000","3000","3000","3000","3000","3000","3000","3000","3000"]}
									 ]
							,"job01Y":[
										 {"YJobAmt":["36000","36000","36000"]
										 ,"YJobAmtCd":"1"
						        		 ,"YJobAmtRate":"0"
						        			 
										 }
									   ]
							,"job02Y":[
										 {"YJobAmt":["36000","36000","36000"]
										 ,"YJobAmtCd":"1"
						        		 ,"YJobAmtRate":"0"
						        			 
										 }
									   ]
		        		};
	        }
	        
	        initJobTable(jsonCost);
		}

		//4.투자계획 초기화
		function initInvest(pJson) {
			let jsonInvest = new Object();
	        if(!pJson==""){
	        	jsonInvest = pJson;
	        }else{	
			
	        	jsonInvest = {"invest01":[
					        			 {"investTitle":"토지","investTitleCd":"10","investPrice":"160000","investYear":"5"}
					        		   ]
						   ,"invest02":[
						       			 {"investTitle":"토지","investTitleCd":"10","investPrice":"3000","investYear":"5"}
						      		   ]
						   ,"invest03":[
						       			 {"investTitle":"영업권(권리금)","investTitleCd":"10","investPrice":"3000","investYear":"5"}
						      		   ]
						   ,"invest04":[
							   			 {"investTitle":"영업권(권리금)","investTitleCd":"10","investPrice":"3000","investYear":"5"}
						      		   ]
						   ,"invest05":[
								   		 {"investTitle":"임차보증금","investTitleCd":"10","investPrice":"3000"}
							      	   ]
						   ,"invest06":[
								   		 {"investTitle":"임차보증금","investTitleCd":"10","investPrice":"3000"}
							      	   ]
						   ,"invest07":[
								   		 {"investTitle":"초동상품(재료)비","investTitleCd":"10","investPrice":"3000"}
							      	   ]
						   ,"invest08":[
								   		 {"investTitle":"초동상품(재료)비","investTitleCd":"10","investPrice":"3000"}
							      	   ]
						   };
	        }
	        
	        initInvestTable(jsonInvest);
		}
		
		//5.자금조달 초기화
		function initLoan(pJson) {
			let jsonLoan = new Object();
	        if(!pJson==""){
	        	jsonLoan = pJson;
	        }else{	
			
	        	jsonLoan = {"loan01":[
					        			 {"loanTitle":"자본금","loanTitleCd":"10","loanYear":["100000","100000","100000"]}
					        		   ]
						   ,"loan02":[
							   			 {"loanTitle":"소상공인융자","loanTitleCd":"10","loanYear":["50000","50000","50000"],"loanRate":["3.0","3.0","3.0"]}
						      		 ]
						   };
	        }
	        
	        initLoanTable(jsonLoan);
		}

		//6.재조경비 초기화
		function initMCost(pJson) {
			let jsonMCost = new Object();
	        if(!pJson==""){
	        	jsonMCost = pJson;
	        }else{	
			
	        	jsonMCost = {"MCost":[
					        			 {"MCostTitle":"전력비","MCostTitleCd":"1","MCostCd":"매출액","MCostTVa":"","MCostTVaLabel":"%"}
				        		    ]
					        ,"MCostM":[
									 	 {"MMCostAmt":["","","","","","","","","","","",""]}
									 ]
							,"MCostY":[
										 {"YMCostAmtYear":["","",""]
										 ,"YMCostAmtCd":"1"
						        		 ,"YMCostAmtRate":"0"
						        			 
										 }
									   ]
		        		};
	        }
	        
	        initMCostTable(jsonMCost);
		}

		//7.판매경비 초기화
		function initSCost(pJson) {
			let jsonSCost = new Object();
	        if(!pJson==""){
	        	jsonSCost = pJson;
	        }else{	
			
	        	jsonSCost = {"SCost":[
					        			 {"SCostTitle":"지급임차료","SCostTitleCd":"12","SCostCd":"월정액","SCostTVa":"2700","SCostTVaLabel":"천원"}
				        		    ]
					        ,"SCostM":[
									 	 {"MSCostAmt":["2700","2700","2700","2700","2700","2700","2700","2700","2700","2700","2700","2700"]}
									 ]
							,"SCostY":[
										 {"YSCostAmtYear":["32400","32400","32400"]
										 ,"YSCostAmtCd":"1"
						        		 ,"YSCostAmtRate":"0"
						        			 
										 }
									   ]
		        		};
	        }
	        
	        initSCostTable(jsonSCost);
		}
		
		//메뉴셋팅
		function initMenuTable(pJson) {
			let menuArr      = pJson.menu;
			let menuM        = pJson.menuM;
			let menuY        = pJson.menuY;
			
			let costIndexValue ="";
			let i = 0;
			
			//메뉴셋팅, 월매출셋팅
			if (menuArr.length > 0) {
 				menuArr.forEach(function(Menu){
 					addMenuTable(Menu.MenuTitle,Menu.MenuPrice,Menu.MenuQty);
					setCostTable("ADD",i);		//재료비 셋팅
					costIndexValue 	=  $('[name=dcostIndex]')[i].innerHTML;
	            	addMenuMTable(i,costIndexValue,Menu.MenuTitle,Menu.MenuPrice,Menu.MenuQty,menuM,"N");
					setMCostTable("ADD",i);		//월재료비 셋팅
					i++;
	  	      });
	         }	
 			calSale();							//일매출액계산
            calMSale();							//월매출계산
            
            //년매출셋팅
 			if (menuArr.length > 0) {
 				i = 0;
 				menuArr.forEach(function(Menu){
					costIndexValue 	=  $('[name=dcostIndex]')[i].innerHTML;
 		            addMenuYTable(i,costIndexValue,Menu.MenuTitle,Menu.MenuPrice,menuY,"N");
					i++;
	  	      });
	         }	
            calYSale();							//년매출계산


		}
		//메뉴추가	
		function addMenuTable(pInputMenuTitle,pInputMenuPrice,pinputMenuQty) {
			var inputMenuIndex = "";
			var inputMenuTitle = "";
			var inputMenuPrice = "";
			var inputMenuQty = "";
			var inputMenuAmt = 0;
			var inputMenuTitleValue = pInputMenuTitle;
			var inputMenuPriceValue = pInputMenuPrice;
			var inputMenuQtyValue 	= pinputMenuQty;

			inputMenuAmt = getNumber(inputMenuPriceValue) * getNumber(inputMenuQtyValue);

			inputMenuIndex = "<span name=\"dmenuIndex\"></span>";
			inputMenuTitle = "<span name=\"dmenuTitle\">"+inputMenuTitleValue+"</span>";
			inputMenuPrice = "<span name=\"dmenuPrice\">"+setComma(inputMenuPriceValue)+"</span>원";
			inputMenuQty   = "<span name=\"dmenuQty\">"+setComma(inputMenuQtyValue)+"</span>";
			inputMenuAmt   = "<span name=\"dmenuAmt\">"+setComma(inputMenuAmt+"")+"</span>원";
			
     		var trStr="";
			trStr="<tr class=\"menuIndex\">";
			trStr += "<td>"+inputMenuIndex+"</td>";
			trStr += "<td>"+inputMenuTitle+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputMenuPrice+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputMenuQty+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputMenuAmt+"</td>";
			trStr += "<td style=\"text-align:center;\">"+"<input name=\"btnUpdMenu\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name=\"btnDelMenu\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				   +"</td>";
			$('#menuTable').append(trStr);
		}
		
		//메뉴월추가	
		function addMenuMTable(pIndex,pCostIndexValue,pInputMenuTitle,pInputMenuPrice,pinputMenuQty,pMenuM,pCalYs) {
	        let costIndexValue 	 	= "";
            let	menuTitleValue 	 	= "";
            let	menuPriceValue   	= "";
            let	menuQtyValue     	= "";

	        let costIndex 	 	 	= "";
	        let	menuTitle 	 	 	= "";

	        let MMenuQtyArr  		= new Array();  //월판매수량            
	        let MMenuAmtArr  		= new Array();  //월판매금액            
	        
	        let	menuQty01 	 		= "";
	        let	menuQty02 	 		= "";
	        let	menuQty03 	 		= "";
	        let	menuQty04 	 		= "";
	        let	menuQty05 	 		= "";
	        let	menuQty06 	 		= "";
	        let	menuQty07 	 		= "";
	        let	menuQty08 	 		= "";
	        let	menuQty09 	 		= "";
	        let	menuQty10 	 		= "";
	        let	menuQty11 	 		= "";
	        let	menuQty12 	 		= "";

	        let	menuAmt01  	 		= "";
	        let	menuAmt02  	 		= "";
	        let	menuAmt03  	 		= "";
	        let	menuAmt04  	 		= "";
	        let	menuAmt05  	 		= "";
	        let	menuAmt06  	 		= "";
	        let	menuAmt07  	 		= "";
	        let	menuAmt08  	 		= "";
	        let	menuAmt09  	 		= "";
	        let	menuAmt10  	 		= "";
	        let	menuAmt11  	 		= "";
	        let	menuAmt12  	 		= "";
	                      
    		let MmenuPrice      	= 0;
    		let MmenuQty        	= 0;
    		let MmenuAmt        	= 0;

    		
			let MmenuQtyTot     	= 0;      //메뉴별 년판매수량
			let MmenuAmtTot     	= 0;	  //메뉴볋 년판매금액	
 			let i = 0;

			 
        	var trStr="";

        	costIndexValue 	=  pCostIndexValue;		//index
        	menuTitleValue 	=  pInputMenuTitle;
        	menuPriceValue  =  pInputMenuPrice;
        	menuQtyValue     = pinputMenuQty;			//일판매기준수량	

			MmenuPrice       = getNumber(removeComma(menuPriceValue));

			if(pCalYs=="Y"){
				MmenuQty         = getNumber(removeComma(menuQtyValue)) * workDay	//월판매수량	
				MmenuAmt 		 = MmenuQty * MmenuPrice;							//월판매금액
				MmenuAmt         = getNumberPoint(MmenuAmt/1000,0)
     			for (i = 0; i < 12; i++){
     				MMenuQtyArr[i] = MmenuQty;
     				MMenuAmtArr[i] = MmenuAmt;
             	}
				
			}else{
		        MMenuQtyArr  		= pMenuM[pIndex].MMenuQty;   //월판매수량            
		        MMenuAmtArr  		= pMenuM[pIndex].MMenuAmt;;  //월판매금액            
			}
			
			
			
 			costIndex = "<span name='sMmenuIndex'>"+costIndexValue+"</span>";
 			menuTitle = "<span name='sMmenuTitle'>"+menuTitleValue+"</span>";
 			menuQty01 = "<span name='sMmenuQty01'>"+setComma(MMenuQtyArr[0]+"")+"</span>";
 			menuQty02 = "<span name='sMmenuQty02'>"+setComma(MMenuQtyArr[1]+"")+"</span>";
 			menuQty03 = "<span name='sMmenuQty03'>"+setComma(MMenuQtyArr[2]+"")+"</span>";
 			menuQty04 = "<span name='sMmenuQty04'>"+setComma(MMenuQtyArr[3]+"")+"</span>";
 			menuQty05 = "<span name='sMmenuQty05'>"+setComma(MMenuQtyArr[4]+"")+"</span>";
 			menuQty06 = "<span name='sMmenuQty06'>"+setComma(MMenuQtyArr[5]+"")+"</span>";
 			menuQty07 = "<span name='sMmenuQty07'>"+setComma(MMenuQtyArr[6]+"")+"</span>";
 			menuQty08 = "<span name='sMmenuQty08'>"+setComma(MMenuQtyArr[7]+"")+"</span>";
 			menuQty09 = "<span name='sMmenuQty09'>"+setComma(MMenuQtyArr[8]+"")+"</span>";
 			menuQty10 = "<span name='sMmenuQty10'>"+setComma(MMenuQtyArr[9]+"")+"</span>";
 			menuQty11 = "<span name='sMmenuQty11'>"+setComma(MMenuQtyArr[10]+"")+"</span>";
 			menuQty12 = "<span name='sMmenuQty12'>"+setComma(MMenuQtyArr[11]+"")+"</span>";
 			

 			menuAmt01 = "<span name='sMmenuAmt01'>"+setComma(MMenuAmtArr[0]+"")+"</span>";
 			menuAmt02 = "<span name='sMmenuAmt02'>"+setComma(MMenuAmtArr[1]+"")+"</span>";
 			menuAmt03 = "<span name='sMmenuAmt03'>"+setComma(MMenuAmtArr[2]+"")+"</span>";
 			menuAmt04 = "<span name='sMmenuAmt04'>"+setComma(MMenuAmtArr[3]+"")+"</span>";
 			menuAmt05 = "<span name='sMmenuAmt05'>"+setComma(MMenuAmtArr[4]+"")+"</span>";
 			menuAmt06 = "<span name='sMmenuAmt06'>"+setComma(MMenuAmtArr[5]+"")+"</span>";
 			menuAmt07 = "<span name='sMmenuAmt07'>"+setComma(MMenuAmtArr[6]+"")+"</span>";
 			menuAmt08 = "<span name='sMmenuAmt08'>"+setComma(MMenuAmtArr[7]+"")+"</span>";
 			menuAmt09 = "<span name='sMmenuAmt09'>"+setComma(MMenuAmtArr[8]+"")+"</span>";
 			menuAmt10 = "<span name='sMmenuAmt10'>"+setComma(MMenuAmtArr[9]+"")+"</span>";
 			menuAmt11 = "<span name='sMmenuAmt11'>"+setComma(MMenuAmtArr[10]+"")+"</span>";
 			menuAmt12 = "<span name='sMmenuAmt12'>"+setComma(MMenuAmtArr[11]+"")+"</span>";
 			
			trStr="<tr>";
			trStr += "<td rowspan='2'>"+costIndex+"</td>";
			trStr += "<td>"+menuTitle+"</td>";
			trStr += "<td>판매수량<span name='sMmenuQtyTot' style='display:none'></span></td>";
			trStr += "<td style='text-align:right;'>"+menuQty01+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty02+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty03+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty04+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty05+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty06+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty07+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty08+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty09+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty10+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty11+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty12+"</td>"
    		trStr += "</tr>";
 			$('#mmenuTable').append(trStr);
			trStr="<tr>";
			trStr += "<td>"+setComma(menuPriceValue)+"</td>";
			trStr += "<td>판매금액<span name='sMmenuAmtTot' style='display:none'></span></td></td>";
			trStr += "<td style='text-align:right;'>"+menuAmt01+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt02+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt03+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt04+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt05+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt06+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt07+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt08+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt09+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt10+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt11+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt12+"</td>"
    		trStr += "</tr>";
 			$('#mmenuTable').append(trStr);
        	
		}
		
		//메뉴년추가	
		function addMenuYTable(pIndex,pCostIndexValue,pMenuTitleValue,pMenuPriceValue,pYMenu,pCalYs) {

			let costIndexValue 		=  pCostIndexValue;		//index
        	let menuTitleValue 		=  pMenuTitleValue;
        	let menuPriceValue  	=  pMenuPriceValue;

        	
	        let YMenuQtyArr 		= new Array();  //년판매수량            
	        let YMenuPriceArr 		= new Array();  //년판매단가            
	        let YMenuAmtArr 		= new Array();  //년판매금액            

	        let YmenuCdValue        = "";
        	let YMenuQtyRateValue  	= "";
        	let YMenuPriceRateValue	= "";

        	
	        if(pCalYs=="Y"){
	        	YmenuCdValue        = "1";
	        	YMenuQtyRateValue   = "0";
	        	YMenuPriceRateValue = "0";

	        	YmenuQtyValue   = $('[name=sMmenuQtyTot]')[pIndex].innerHTML;		//메뉴별 년판매수량	
	           	YmenuAmtValue   = $('[name=sMmenuAmtTot]')[pIndex].innerHTML;		//메뉴별 년판매금액
	           	
	           	YMenuQtyArr[0]  = YmenuQtyValue;
	           	YMenuQtyArr[1]  = YmenuQtyValue;
	           	YMenuQtyArr[2]  = YmenuQtyValue;
	           	
	           	YMenuPriceArr[0] = menuPriceValue;
	           	YMenuPriceArr[1] = menuPriceValue;
	           	YMenuPriceArr[2] = menuPriceValue;

	           	YMenuAmtArr[0]  = YmenuAmtValue;
	           	YMenuAmtArr[1]  = YmenuAmtValue;
	           	YMenuAmtArr[2]  = YmenuAmtValue;
	        }else{
		        YMenuQtyArr = pYMenu[pIndex].YMenuQty;  	//년판매수량            
		        YMenuPriceArr = pYMenu[pIndex].YMenuPrice;  //년판매금액
		        YMenuAmtArr = pYMenu[pIndex].YMenuAmt;  	//년판매단가
	        	YmenuCdValue        = pYMenu[pIndex].YMenuCd;
	        	YMenuQtyRateValue   = pYMenu[pIndex].YMenuQtyRate;
	        	YMenuPriceRateValue = pYMenu[pIndex].YMenuPriceRate;
	        }
           	
           	
 			let costIndex = "<span name='sYMenuIndex'>"+costIndexValue+"</span>";
 			let menuTitle = "<span name='sYMenuTitle'>"+menuTitleValue+"</span>";
 			let menuQty01 = "<span name='sYMenuQtyYear1'>"+setComma(YMenuQtyArr[0]+"")+"</span>";
 			let menuQty02 = "<span name='sYMenuQtyYear2'>"+setComma(YMenuQtyArr[1]+"")+"</span>";
 			let menuQty03 = "<span name='sYMenuQtyYear3'>"+setComma(YMenuQtyArr[2]+"")+"</span>";

 			let menuPrice01 = "<span name='sYMenuPriceYear1'>"+setComma(YMenuPriceArr[0]+"")+"</span>";
 			let menuPrice02 = "<span name='sYMenuPriceYear2'>"+setComma(YMenuPriceArr[1]+"")+"</span>";
 			let menuPrice03 = "<span name='sYMenuPriceYear3'>"+setComma(YMenuPriceArr[2]+"")+"</span>";

 			
 			let menuAmt01 = "<span name='sYMenuAmtYear1'>"+setComma(YMenuAmtArr[0]+"")+"</span>";
 			let menuAmt02 = "<span name='sYMenuAmtYear2'>"+setComma(YMenuAmtArr[1]+"")+"</span>";
 			let menuAmt03 = "<span name='sYMenuAmtYear3'>"+setComma(YMenuAmtArr[2]+"")+"</span>";
 			
			trStr="<tr>";
			trStr += "<td rowspan='3'>"+costIndex+"</td>";
			trStr += "<td>"+menuTitle+"</td>";
			trStr += "<td>판매수량<span name='sYmenuCd' style='display:none'>"+YmenuCdValue+"</span><span name='sYMenuQtyRate' style='display:none'>"+YMenuQtyRateValue+"</span></td>";
			trStr += "<td style='text-align:right;'>"+menuQty01+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty02+"</td>"
			trStr += "<td style='text-align:right;'>"+menuQty03+"</td>"
			trStr += "<td rowspan='3' style='text-align:center;'><input name='btnUpdYMenu' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#ymenuTable').append(trStr);
			trStr="<tr>";
			trStr += "<td>"+setComma(menuPriceValue)+"</td>";
			trStr += "<td>판매단가<span name='sYMenuPriceRate' style='display:none'>"+YMenuPriceRateValue+"</span></td>";
			trStr += "<td style='text-align:right;'>"+menuPrice01+"</td>"
			trStr += "<td style='text-align:right;'>"+menuPrice02+"</td>"
			trStr += "<td style='text-align:right;'>"+menuPrice03+"</td>"
    		trStr += "</tr>";
 			$('#ymenuTable').append(trStr);
			trStr="<tr>";
			trStr += "<td></td>";
			trStr += "<td>판매금액</td>";
			trStr += "<td style='text-align:right;'>"+menuAmt01+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt02+"</td>"
			trStr += "<td style='text-align:right;'>"+menuAmt03+"</td>"
    		trStr += "</tr>";
 			$('#ymenuTable').append(trStr);
		
		}
		
		//재료비 셋팅
		function initCostTable(pJson) {
			let costArr  = pJson.cost;
			let costM    = pJson.costM;
			let costY    = pJson.costY;
			
			let MCostAmtArr  = new Array();
			let YCostAmtArr  = new Array();

 			if (costArr.length > 0) {
 				let i = 0;
 				costArr.forEach(function(cost){
 					document.querySelectorAll("[name=icostRate]")[i].value = cost.costRate;
 					initCostRateDis(cost.costRate,i,costM,costY,"N");
					i++;
	  	      });
	         }	
 			calCost();
		}
		
		//인건비 셋팅
		function initJobTable(pJson) {
			let Job01Arr  = pJson.job01;
			let Job02Arr  = pJson.job02;
			let jobO1M    = pJson.job01M;
			let jobO2M    = pJson.job02M;
			let job01Y    = pJson.job01Y;
			let job02Y    = pJson.job02Y;

			let i = 0;
			
			//월인건비 제조
 			if (Job01Arr.length > 0) {
 				i = 0;
 				Job01Arr.forEach(function(cost){
 					addJobTable("1",cost.JobTitle,cost.JobCnt,cost.JobQty)
	            	addJobMTable(i,"01",jobO1M,"N");
					i++;
	  	      });
	         }	

 			//월인건비 판매
 			if (Job02Arr.length > 0) {
 				i = 0;
 				Job02Arr.forEach(function(cost){
 					addJobTable("2",cost.JobTitle,cost.JobCnt,cost.JobQty)
	            	addJobMTable(i,"02",jobO2M,"N");
					i++;
	  	      });
	         }	

 			
 			calJob();							//일인건비
            calMJob();							//월인건비계산
            
            //년매출셋팅 제조
 			if (Job01Arr.length > 0) {
 				i = 0;
 				Job01Arr.forEach(function(Menu){
					addJobYTable(i,"01",job01Y,"N");
					i++;
	  	      });
	         }	

 			//년매출셋팅 판매
 			if (Job02Arr.length > 0) {
 				i = 0;
 				Job02Arr.forEach(function(Menu){
					addJobYTable(i,"02",job02Y,"N");
					i++;
	  	      });
	         }	
 			
            calYJob();							//년인건비 계산

 			
		}
		
		//인건비추가	
		function addJobTable(pJobCd,pInputJobTitleValue,pInputJobCntValue,pInputJobQtyValue) {
			
			let inputJobTitle = "";
			let inputJobCnt = "";
			let inputJobQty = "";
			var inputJobAmt = "";
			let jobCd = "";
			jobCd = pJobCd;
			
			let inputJobTitleValue 	= pInputJobTitleValue;
			let inputJobCntValue 	= pInputJobCntValue;
			let inputJobQtyValue 	= pInputJobQtyValue;
			let  inputJobAmtValue    = 0;

			inputJobAmtValue = getNumber(inputJobCntValue) * getNumber(inputJobQtyValue);

			if(jobCd == "1") {					
				inputJobTitle 	= "<span name=\"sJob01Title\">"+inputJobTitleValue+"</span>";
				inputJobCnt 	= "<span name=\"sJob01Cnt\">"+setComma(inputJobCntValue)+"</span>명";
				inputJobQty   	= "<span name=\"sJob01Qty\">"+setComma(inputJobQtyValue)+"</span>원";
				inputJobAmt   	= "<span name=\"sJob01Amt\">"+setComma(inputJobAmtValue+"")+"</span>원";

				
				
         		var trStr="";
        		trStr="<tr>";
				trStr += "<td>"+inputJobTitle+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobCnt+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobQty+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobAmt+"</td>";
				trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdJob01\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
													+" <input name=\"btnDelJob01\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
					  +"</td>";
				trStr += "</tr>";
				$('#jobTable01').append(trStr);
			}else{
				inputJobTitle 	= "<span name=\"sJob02Title\">"+inputJobTitleValue+"</span>";
				inputJobCnt 	= "<span name=\"sJob02Cnt\">"+setComma(inputJobCntValue)+"</span>명";
				inputJobQty   	= "<span name=\"sJob02Qty\">"+setComma(inputJobQtyValue)+"</span>원";
				inputJobAmt   	= "<span name=\"sJob02Amt\">"+setComma(inputJobAmtValue+"")+"</span>원";

				
				
         		var trStr="";
        		trStr="<tr>";
				trStr += "<td>"+inputJobTitle+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobCnt+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobQty+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputJobAmt+"</td>";
				trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdJob02\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
													+" <input name=\"btnDelJob02\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
					  +"</td>";
				trStr += "</tr>";
				$('#jobTable02').append(trStr);

			}
		}

		//인건비 월추가	
		function addJobMTable(pIndex,pJobCd,pJobM,pCalYs) {
            let	sMJobTitleValue   	= "";  
            let	sMJobCdValue	 	= "";  
            let sMJobCdTitleValue   = "";
            let sMJobCntValue 		= "";   
            let sJobAmtValue        = "";
            
	        let	sMJobTitle 	 	 	= "";
	        let sMJobCd            	= "";
		    let sMJobCnt           	= "";
		    let sJobAmt             = "";

			let JobAmt 	 		 	= new Array();  //월인건비 value
	        let MJobArr  			= new Array();  //월인건비            

			let iJobAmt       		= 0;     
			let iTemp               ="";
 			let i 					= 0;
			let iMJobTot      	    = 0;
        	var trStr="";
			let iIndex 				= pIndex;

			//console.log("pJobCd:",pJobCd);
			//console.log("pIndex:",pIndex);
			
        	sMJobTitleValue 	=  $('[name=sJob'+pJobCd+'Title]')[pIndex].innerHTML;

        	if(pJobCd=="01") {
            	sMJobCdTitleValue =  "제조";	
            	sMJobCdValue      =  "1";
        	}else {
            	sMJobCdTitleValue =  "판매";	
            	sMJobCdValue      =  "2";
        	}
            sMJobCntValue     	=  $('[name=sJob'+pJobCd+'Cnt]')[pIndex].innerHTML;		
        	sJobAmtValue     	=  removeComma($('[name=sJob'+pJobCd+'Amt]')[pIndex].innerHTML);		

        	if(pCalYs=="Y"){
	        	if($('[name=sJob'+pJobCd+'Amt]')[pIndex].innerHTML!=""){
	            	iJobAmt  = getNumber(sJobAmtValue) /1000;
	        		iMJobTot = (getNumber(iJobAmt) * 12);
	            }
        	}else{
            	for (i = 0; i < 12; i++){
    				JiMJobTot = iMJobTot + getNumber(pJobM[pIndex].MJobAmt[i]);
             	}
        	}
        	sMJobCd    = "<span name='sMJobCdTitle'>"+sMJobCdTitleValue+"</span><span name='sMJobCd' style='display:none'>"+sMJobCdValue+"</span>";
        	sMJobTitle = "<span name='sMJobTitle'>"+sMJobTitleValue+"</span><span name='sMJobTot' style='display:none'>"+(iMJobTot+"")+"</span>";
        	sMJobCnt   = "<span name='sMJobCnt'>"+sMJobCntValue+"</span>"
 			
        	
			if(pCalYs=="Y"){
     			for (i = 0; i < 12; i++){
     				MJobArr[i] = iJobAmt;
             	}
			}else{
				MJobArr	= pJobM[pIndex].MJobAmt;  //인건비            
			}

        	
        	//월인건비 셋팅
 			for (i = 0; i < 12; i++){
				if (i < 9 ) iTemp ="0"+(i+1)
				else iTemp = (i+1);
				JobAmt[i] ="<span name='sMJobAmt"+iTemp+"'>"+setComma(MJobArr[i]+"")+"</span>"
         	}
 			
			trStr="<tr>";
			trStr += "<td>"+sMJobCd+"</td>";
   			trStr += "<td>"+sMJobTitle+"</td>";
   			trStr += "<td style='text-align:right;'>"+sMJobCnt+"</td>";
   		    for (i = 0; i < 12; i++){
    			trStr += "<td style='text-align:right;'>"+JobAmt[i]+"</td>"
         	}
    		trStr += "</tr>";
 			$('#tbMjob'+pJobCd).append(trStr);

		}

		//인건비 년추가	
		function addJobYTable(pIndex,pJobCd,pYJob,pCalYs) {
            let	sYJobCdTitleValue 	= "";
            let	sYJobCdValue    	= "";	
            let	sYJobTitleValue    	= "";		
            let sYJobCntValue 		= "";
            let sYJobQtyValue 		= "";
            let sMJobTotValue       = "";
            let sYJobAmtRateValue   = "";
            
            
	        let	sYJobCdTitle 	 	= "";
	        let	sYJobCd				= "";	
	        let	sYJobTitle 	 		= "";
	        let	sYJobCnt 	 		= "";
	        let	sYJobQty 	 		= "";

	    	let sYJobAmtYear1		= "";
	        let sYJobAmtYear2		= "";
	        let sYJobAmtYear3		= "";

	        let isYJobAmtRateValue  = 0;
	        let isYJobAmtYear1		= 0;
	        let isYJobAmtYear2		= 0;
	        let isYJobAmtYear3		= 0;
	        let isYJobQty           = 0;
        	var trStr="";
			let iIndex = pIndex;
	        let YJobAmtArr 		= new Array();  //년인건비            
	        let YJobAmtCd      = "";
	        let YJobAmtRate    = "";

        	if(pJobCd=="01") {
        		sYJobCdTitleValue 	=  "제조";
            	sYJobCdValue		=  "1";		
        	}else {
        		sYJobCdTitleValue 	=  "판매";
            	sYJobCdValue		=  "2";		
        	}


        	sYJobTitleValue     =  $('[name=sJob'+pJobCd+'Title]')[pIndex].innerHTML;
        	sYJobCntValue		=  $('[name=sJob'+pJobCd+'Cnt]')[pIndex].innerHTML;		
        	sYJobQtyValue		=  removeComma($('[name=sJob'+pJobCd+'Qty]')[pIndex].innerHTML);	
     
        	if(sYJobQtyValue!="")  isYJobQty = getNumber(sYJobQtyValue)/1000;
        	
        	if(pJobCd=="02") {		//판매이면 제조갯수 만큼 플러서 해줘야 함
        		iIndex = pIndex + $('#jobTable01 >tbody tr').length;       	
           	}          		
        	sMJobTotValue       =  $('[name=sMJobTot]')[iIndex].innerHTML;

	        if(pCalYs=="Y"){
	        	YJobAmtCd      = "1";
	        	YJobAmtRate    = "0";
	           	YJobAmtArr[0]  = sMJobTotValue;
	           	YJobAmtArr[1]  = sMJobTotValue;
	           	YJobAmtArr[2]  = sMJobTotValue;
	        }else{
	        	YJobAmtCd      = pYJob[pIndex].YJobAmtCd;
	        	YJobAmtRate    = pYJob[pIndex].YJobAmtRate;
	        	YJobAmtArr 	   = pYJob[pIndex].YJobAmt;  //년판매수량            
	        }


        	    	
           	sYJobCd 	 = "<span name='sYJobCdTitle'>"+sYJobCdTitleValue+"</span><span name='sYJobCd' style='display:none'>"+sYJobCdValue+"</span>";
            sYJobTitle   = "<span name='sYJobTitle'>"+sYJobTitleValue+"</span><span name='sYJobAmtCd' style='display:none'>"+YJobAmtCd+"</span><span name='sYJobAmtRate' style='display:none'>"+YJobAmtRate+"</span>";
            sYJobQty 	 = "<span name='sYJobQty'>"+setComma(isYJobQty+"")+"</span>";
            sYJobCnt 	 = "<span name='sYJobCnt'>"+sYJobCntValue+"</span>";

            
            
            
            sYJobAmtYear1 = "<span name='sYJobAmtYear1'>"+setComma(YJobAmtArr[0])+"</span>";
            sYJobAmtYear2 = "<span name='sYJobAmtYear2'>"+setComma(YJobAmtArr[1])+"</span>";
            sYJobAmtYear3 = "<span name='sYJobAmtYear3'>"+setComma(YJobAmtArr[2])+"</span>";
 			
			trStr="<tr>";
			trStr += "<td>"+sYJobCd+"</td>";
			trStr += "<td>"+sYJobTitle+"</td>";
			trStr += "<td style='text-align:right;'>"+sYJobQty+"</td>";
			trStr += "<td style='text-align:right;'>"+sYJobCnt+"</td>";
			trStr += "<td style='text-align:right;'>"+sYJobAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+sYJobAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+sYJobAmtYear3+"</td>"
			trStr += "<td rowspan='2' style='text-align:center;'><input name='btnUpdYJob' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#tbYjob'+pJobCd).append(trStr);

			
			
		}
		

		//투자계획 셋팅
		function initInvestTable(pJson) {
			let invest01Arr  = pJson.invest01;
			let invest02Arr  = pJson.invest02;
			let invest03Arr  = pJson.invest03;
			let invest04Arr  = pJson.invest04;
			let invest05Arr  = pJson.invest05;
			let invest06Arr  = pJson.invest06;
			let invest07Arr  = pJson.invest07;
			let invest08Arr  = pJson.invest08;

			let i = 0;

			//제조 유형 자산
 			if (invest01Arr.length > 0) {
 				i = 0;
 				invest01Arr.forEach(function(invest){
 					addInvestTable("1","1",invest.investTitle,invest.investTitleCd,invest.investPrice,invest.investYear);
					i++;
	  	      });
	         }	

 			//제조 무형 자산
 			if (invest03Arr.length > 0) {
 				i = 0;
 				invest03Arr.forEach(function(invest){
 					addInvestTable("2","1",invest.investTitle,invest.investTitleCd,invest.investPrice,invest.investYear);
					i++;
	  	      });
	         }	

 			//제조 기타비유동자산
 			if (invest05Arr.length > 0) {
 				i = 0;
 				invest05Arr.forEach(function(invest){
 					addInvestTable("3","1",invest.investTitle,invest.investTitleCd,invest.investPrice);
					i++;
	  	      });
	         }	

 			//제조 기타투자비용
 			if (invest07Arr.length > 0) {
 				i = 0;
 				invest07Arr.forEach(function(invest){
 					addInvestTable("4","1",invest.investTitle,invest.investTitleCd,invest.investPrice);
					i++;
	  	      });
	         }	

			//판매 유형 자산
 			if (invest02Arr.length > 0) {
 				i = 0;
 				invest02Arr.forEach(function(invest){
 					addInvestTable("1","2",invest.investTitle,invest.investTitleCd,invest.investPrice,invest.investYear);
					i++;
	  	      });
	         }	

 			//판매 유형 자산
 			if (invest04Arr.length > 0) {
 				i = 0;
 				invest04Arr.forEach(function(invest){
 					addInvestTable("2","2",invest.investTitle,invest.investTitleCd,invest.investPrice,invest.investYear);
					i++;
	  	      });
	         }	

 			//판매 기타비유동자산
 			if (invest06Arr.length > 0) {
 				i = 0;
 				invest06Arr.forEach(function(invest){
 					addInvestTable("3","2",invest.investTitle,invest.investTitleCd,invest.investPrice);
					i++;
	  	      });
	         }	

 			//판매 기타투자비용
 			if (invest08Arr.length > 0) {
 				i = 0;
 				invest08Arr.forEach(function(invest){
 					addInvestTable("4","2",invest.investTitle,invest.investTitleCd,invest.investPrice);
					i++;
	  	      });
	         }	
			
 			calInvest();
		}
		
		//투자추가	
		function addInvestTable(pInvestCd,pMValue,pTitleValue,pTitleCdValue,pPriceValue,pYearValue) {
			let investCd = pInvestCd;
			
			let inputInvestBizcdCodeM 	= "";
			let inputInvestTitle 		= "";
			let inputInvestPirce 		= "";
			let inputInvestYear 		= "";
			let inputInvestAmt 			= "";

			let inputInvestBizcdCodeMValue  = pMValue;
			let inputInvestTitleValue  		= pTitleValue;
			let inputInvestTitleCdValue  	= pTitleCdValue;
			let inputInvestPirceValue    	= pPriceValue;
			let inputInvestYearValue   		= pYearValue;
			let inputInvestAmtValue    		= 0;

			if(investCd=="1" || investCd=="2") {
				if(getNumber(inputInvestYearValue) == 0){
					inputInvestAmtValue = 0;
				}else{
					inputInvestAmtValue = (getNumber(inputInvestPirceValue) / getNumber(inputInvestYearValue)) / 12;
					inputInvestAmtValue = inputInvestAmtValue.toFixed(0);
				}
			}
			
			let investNo = "01";
			if(investCd == "1"){
				if(inputInvestBizcdCodeMValue =="1") investNo = "01"
				else investNo = "02"	
			}else if(investCd == "2"){
				if(inputInvestBizcdCodeMValue =="1") investNo = "03"
				else investNo = "04"	
			}else if(investCd == "3"){
				if(inputInvestBizcdCodeMValue =="1") investNo = "05"
				else investNo = "06"	
			} else{
				if(inputInvestBizcdCodeMValue =="1") investNo = "07"
				else investNo = "08"	
			}

			inputInvestTitle 		= "<span name=\"dInvest"+investNo+"Title\">"+inputInvestTitleValue+"</span><span name=\"dInvest"+investNo+"TitleCd\" style=\"display:none\">"+inputInvestTitleCdValue+"</span>";
			inputInvestPirce   		= "<span name=\"dInvest"+investNo+"Price\">"+setComma(inputInvestPirceValue)+"</span>천원";
			inputInvestYear   		= "<span name=\"dInvest"+investNo+"Year\">"+inputInvestYearValue+"</span>년";
			inputInvestAmt   		= "<span name=\"dInvest"+investNo+"Amt\">"+setComma(inputInvestAmtValue+"")+"</span>천원";
			
     		var trStr="";
    		trStr="<tr>";
			trStr += "<td>"+inputInvestTitle+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputInvestPirce+"</td>";
			if(investCd == "1" || investCd == "2") {
				trStr += "<td style=\"text-align:right;\">"+inputInvestYear+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputInvestAmt+"</td>";
			}
			trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdInvest"+investNo+"\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name=\"btnDelInvest"+investNo+"\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				  +"</td>";
			trStr += "</tr>";
			$('#investTable'+investNo).append(trStr);
			
		}

		//자금조달 셋팅
		function initLoanTable(pJson) {
			let Loan01Arr  = pJson.loan01;
			let Loan02Arr  = pJson.loan02;

			let i = 0;

			//자기자본
 			if (Loan01Arr.length > 0) {
 				i = 0;
 				Loan01Arr.forEach(function(loan){
 					addLoanTable("1",loan.loanTitle,loan.loanTitleCd,loan.loanYear[0],loan.loanYear[1],loan.loanYear[2],"","","");
					i++;
	  	      });
	         }	

 			//자기자본
 			if (Loan02Arr.length > 0) {
 				i = 0;
 				Loan02Arr.forEach(function(loan){
 					addLoanTable("2",loan.loanTitle,loan.loanTitleCd,loan.loanYear[0],loan.loanYear[1],loan.loanYear[2],loan.loanRate[0],loan.loanRate[1],loan.loanRate[2]);
					i++;
	  	      });
	         }	

			
 			calLoan();
		}

		//자본계획추가	
		function addLoanTable(pLoanCd,pTitleValue,pTitleCdValue,pYear1Value,pYear2Value,pYear3Value,pLoanRate1Value,pLoanRate2Value,pLoanRate3Value) {
			let loanCd            = "";
			let inputLoanTitle 		= "";
			let inputLoanYear1 		= "";
			let inputLoanYear2 		= "";
			let inputLoanYear3 		= "";
			let inputLoanRate1 		= "";
			let inputLoanRate2 		= "";
			let inputLoanRate3 		= "";
			let inputLoanYearRate1 	= "";
			let inputLoanYearRate2 	= "";
			let inputLoanYearRate3 	= "";

			loanCd = pLoanCd;
			let inputLoanTitleValue  	= pTitleValue;
			let inputLoanTitleCdValue  	= pTitleCdValue;

			let inputLoanYear1Value    	= pYear1Value;
			let inputLoanYear2Value    	= pYear2Value;
			let inputLoanYear3Value    	= pYear3Value;


			let loanNo = "01";
			if(loanCd=="1") loanNo = "01";
			else loanNo = "02";


			let inputLoanRate1Value    	= "";
			let inputLoanRate2Value    	= "";
			let inputLoanRate3Value    	= "";

			let inputLoanYearRate1Value = 0;
			let inputLoanYearRate2Value = 0;
			let inputLoanYearRate3Value = 0;
			
			if(loanCd=="2") {
				inputLoanRate1Value    	= pLoanRate1Value;
				inputLoanRate2Value    	= pLoanRate2Value;
				inputLoanRate3Value    	= pLoanRate3Value;

				inputLoanYearRate1Value = 0;
				inputLoanYearRate2Value = 0;
				inputLoanYearRate3Value = 0;

				if(inputLoanRate1Value !="" && inputLoanYear1Value !=""){
					inputLoanYearRate1Value = getNumber(inputLoanYear1Value) * (getNumber(inputLoanRate1Value)/100)
					//inputLoanYearRate1Value = Math.floor(inputLoanYearRate1Value/10) * 10
				}
				if(inputLoanRate2Value !="" && inputLoanYear2Value !=""){
					inputLoanYearRate2Value = getNumber(inputLoanYear2Value) * (getNumber(inputLoanRate2Value)/100)
					//inputLoanYearRate2Value = Math.floor(inputLoanYearRate2Value/10) * 10
				}
				if(inputLoanRate3Value !="" && inputLoanYear3Value !=""){
					inputLoanYearRate3Value = getNumber(inputLoanYear3Value) * (getNumber(inputLoanRate3Value)/100)
					//inputLoanYearRate3Value = Math.floor(inputLoanYearRate3Value/10) * 10
				}

			}

			
     		var trStr="";
			if(loanCd=="1") {
				inputLoanTitle 		= "<span name=\"dLoan"+loanNo+"Title\">"+inputLoanTitleValue+"</span><span name=\"dLoan"+loanNo+"TitleCd\" style=\"display:none\">"+inputLoanTitleCdValue+"</span>";
				inputLoanYear1   	= "<span name=\"dLoan"+loanNo+"Year1\">"+setComma(inputLoanYear1Value+"")+"</span>천원";
				inputLoanYear2   	= "<span name=\"dLoan"+loanNo+"Year2\">"+setComma(inputLoanYear2Value+"")+"</span>천원";
				inputLoanYear3   	= "<span name=\"dLoan"+loanNo+"Year3\">"+setComma(inputLoanYear3Value+"")+"</span>천원";
				
        		trStr="<tr>";
				trStr += "<td>"+inputLoanTitle+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear1+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear2+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear3+"</td>";
				trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdLoan"+loanNo+"\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
													+" <input name=\"btnDelLoan"+loanNo+"\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
					  +"</td>";
				trStr += "</tr>";

			} else {

				
				inputLoanTitle 		= "<span name=\"dLoan"+loanNo+"Title\">"+inputLoanTitleValue+"</span><span name=\"dLoan"+loanNo+"TitleCd\" style=\"display:none\">"+inputLoanTitleCdValue+"</span>";
				inputLoanYear1   	= "<span name=\"dLoan"+loanNo+"Year1\">"+setComma(inputLoanYear1Value)+"</span>천원";
				inputLoanYear2   	= "<span name=\"dLoan"+loanNo+"Year2\">"+setComma(inputLoanYear2Value)+"</span>천원";
				inputLoanYear3   	= "<span name=\"dLoan"+loanNo+"Year3\">"+setComma(inputLoanYear3Value)+"</span>천원";

				inputLoanRate1   	= "<span name=\"dLoan"+loanNo+"Rate1\">"+setComma(inputLoanRate1Value)+"</span>%";
				inputLoanRate2   	= "<span name=\"dLoan"+loanNo+"Rate2\">"+setComma(inputLoanRate2Value)+"</span>%";
				inputLoanRate3   	= "<span name=\"dLoan"+loanNo+"Rate3\">"+setComma(inputLoanRate3Value)+"</span>%";

				inputLoanYearRate1  = "<span name=\"dLoan"+loanNo+"YearRate1\">"+setComma(inputLoanYearRate1Value+"")+"</span>천원";
				inputLoanYearRate2  = "<span name=\"dLoan"+loanNo+"YearRate2\">"+setComma(inputLoanYearRate2Value+"")+"</span>천원";
				inputLoanYearRate3  = "<span name=\"dLoan"+loanNo+"YearRate3\">"+setComma(inputLoanYearRate3Value+"")+"</span>천원";
				
        		trStr="<tr>";
				trStr += "<td rowspan='3'>"+inputLoanTitle+"</td>";
				trStr += "<td style=\"text-align:center;\">금액</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear1+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear2+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYear3+"</td>";
				trStr += "<td rowspan='3' style=\"text-align:right;\">"+"<input name=\"btnUpdLoan"+loanNo+"\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
													+" <input name=\"btnDelLoan"+loanNo+"\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"+"</td>";
				trStr += "</tr>";

        		trStr +="<tr>";
				trStr += "<td style=\"text-align:center;\">이자율</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanRate1+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanRate2+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanRate3+"</td>";
				trStr += "</tr>";

				trStr +="<tr>";
				trStr += "<td style=\"text-align:center;\">년이자비율</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYearRate1+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYearRate2+"</td>";
				trStr += "<td style=\"text-align:right;\">"+inputLoanYearRate3+"</td>";
				trStr += "</tr>";

			}

			$('#loanTable'+loanNo).append(trStr);
			
			
		}
		
		//제조경비셋팅
		function initMCostTable(pJson) {
			let MCostArr  = pJson.MCost;
			let MCostM    = pJson.MCostM;
			let MCostY    = pJson.MCostY;

			let i = 0;
			
			//제조경비 셋팅, 월 제조경비셋팅
 			if (MCostArr.length > 0) {
 				i = 0;
 				MCostArr.forEach(function(mcost){
 					addMCostTable(mcost.MCostTitle,mcost.MCostTitleCd,mcost.MCostCd,mcost.MCostTVa,mcost.MCostTVaLabel);
	            	addMCostMTable(i,MCostM,"N");
					i++;
	  	      });
	         }	
 			
 			calMMCost();
 			
            //년제조경비 세팅
 			if (MCostArr.length > 0) {
 				i = 0;
 				MCostArr.forEach(function(Menu){
					addMCostYTable(i,MCostY,"N");
					i++;
	  	      });
	         }	
            calYMCost();							
		}
		
		//제조경비추가
		function addMCostTable(pCodeMValue,pCodeMCdValue,pMCostCdValue,pCostTVaValue,pTVaLabelValue) {
			var inputMcostcdCodeM 	= "";
			var sMCostCdValue 		= "";
			var inputMCostTV 		= "";
			var MCostTVaAmt 		= "";
			var iMCostTVaAmt 		= 0;
			let McostNo 			= "01";
			let  iMCostCdAmt        = 0;

			var inputMcostcdCodeMValue   = pCodeMValue;
			var inputMcostcdCodeMCdValue = pCodeMCdValue;
			var sMCostCdValue    		 = pMCostCdValue;
			var inputMCostTVaValue 		 = pCostTVaValue;
			var sMCostTVaLabelValue   	 = pTVaLabelValue;
			
			
			//월정액,기준액,인건비 이면 가격 비율을 곱해서 월 제조경비를 구한다.
			if(sMCostCdValue!=""){
				if(sMCostCdValue =="매출액" || sMCostCdValue =="기준액"|| sMCostCdValue =="인건비"){
					if(sMCostCdValue =="매출액") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMmenuAmt01Tot').innerHTML));
					if(sMCostCdValue =="기준액") iMCostCdAmt = getNumber(removeComma(document.getElementById('innvest03TotAmt').innerHTML));
					if(sMCostCdValue =="인건비") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMJobAmt01Sub1Tot').innerHTML));
					
					console.log("iMCostCdAmt",iMCostCdAmt);
					iMCostTVaAmt = (iMCostCdAmt * getNumber(inputMCostTVaValue)/100);
					iMCostTVaAmt = getNumberPoint(iMCostTVaAmt,0);
				} else {
					iMCostTVaAmt = getNumber(inputMCostTVaValue);
				}
			}

			inputMcostcdCodeM 	= "<span name='sMCost"+McostNo+"Title'>"+inputMcostcdCodeMValue+"</span><span name='sMCost"+McostNo+"TitleCd' style='display:none'>"+inputMcostcdCodeMCdValue+"</span>";
			sMCostCdValue   	= "<span name='sMCost"+McostNo+"Cd'>"+sMCostCdValue+"</span>";
			inputMCostTV   		= "<span name='sMCost"+McostNo+"TVa'>"+setComma(inputMCostTVaValue)+"</span><span name='sMCost"+McostNo+"TVaLabel'>"+sMCostTVaLabelValue+"</span>";
			MCostTVaAmt   		= "<span name='sMCost"+McostNo+"TVaAmt'>"+setComma(iMCostTVaAmt+"")+"</span>천원";
			
     		var trStr="";
    		trStr="<tr>";
			trStr += "<td>"+inputMcostcdCodeM+"</td>";
			trStr += "<td>"+sMCostCdValue+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputMCostTV+"</td>";
			trStr += "<td style=\"text-align:right;\">"+MCostTVaAmt+"</td>";
			trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdMCost"+McostNo+"\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name=\"btnDelMCost"+McostNo+"\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				  +"</td>";
			trStr += "</tr>";
			$('#MCostTable01').append(trStr);
			
		}
		
		//월제조경비추가
		function addMCostMTable(pIndex,pMCostM,pCalYs)  {
            let	sMMCostTitleValue   = "";  //계정과목
            let	sMMCostCdValue	 	= "";  //계정과목기준
            let sMMCostTVaValue 	= "";  //기준금액 
            let	sMCostTVaLabelValue = "";  //기준금액 단위
            let sMCostTVaAmt		= "";   //적용금액
            
	        let	sMMCostTitle 	 	 = "";
	        let sMMCostCd            = "";
		    let sMMCostTVa           = "";
	        let MCostAmt 	 		 = new Array(); //월재조경비            
			let iMCostMenuAmt       = 0;     		//메뉴별 월판매금액
			let iTemp               ="";
 			let i = 0;
			let iMMCostMenuTot      = 0;
        	var trStr="";

        	let MCostAmtArr 	 	 = new Array(); //월재경비            
        	
		    console.log("pIndex",pIndex);
        	sMMCostTitleValue 	=  $('[name=sMCost01Title]')[pIndex].innerHTML;
        	sMMCostCdValue		=  $('[name=sMCost01Cd]')[pIndex].innerHTML;		
        	sMMCostTVaValue     =  $('[name=sMCost01TVa]')[pIndex].innerHTML;		
        	sMCostTVaLabelValue	=  $('[name=sMCost01TVaLabel]')[pIndex].innerHTML;	
        	sMCostTVaAmtValue	=  removeComma($('[name=sMCost01TVaAmt]')[pIndex].innerHTML);	

        	if(sMCostTVaLabelValue=="천원") sMCostTVaLabelValue = "";

        	if(pCalYs=="Y"){
	        	if($('[name=sMCost01TVaAmt]')[pIndex].innerHTML!=""){
	        		iMMCostMenuTot      = getNumber(sMCostTVaAmtValue) *12;
	        	}
        	}else{
            	for (i = 0; i < 12; i++){
            		iMMCostMenuTot = iMMCostMenuTot + getNumber(pMCostM[pIndex].MMCostAmt[i]);
             	}
        		
        	}
        	
        	sMMCostTitle = "<span name='sMMCostTitle'>"+sMMCostTitleValue+"</span>";
        	sMMCostCd    = "<span name='sMMCostCd'>"+sMMCostCdValue+"</span><span name='sMMCostMenuTot' style='display:none'>"+setComma(iMMCostMenuTot+"")+"</span>";
        	sMMCostTVa   = "<span name='sMMCostTVa'>"+sMMCostTVaValue+"</span><span name='sMCostTVaLabel'>"+sMCostTVaLabelValue+"</span>"
 			
        	if(pCalYs=="Y"){
     			for (i = 0; i < 12; i++){
     				MCostAmtArr[i] = sMCostTVaAmtValue;
             	}
        	}else{
        		MCostAmtArr	= pMCostM[pIndex].MMCostAmt;              
        	}
        	
        	
        	//월판매금액 셋팅
 			for (i = 0; i < 12; i++){
				if (i < 9 ) iTemp ="0"+(i+1)
				else iTemp = (i+1);
				MCostAmt[i] ="<span name='sMMCostAmt"+iTemp+"'>"+setComma(MCostAmtArr[i])+"</span>"
         	}
 			
			trStr="<tr>";
			trStr += "<td>"+sMMCostTitle+"</td>";
   			trStr += "<td>"+sMMCostCd+"</td>";
   			trStr += "<td style='text-align:right;'>"+sMMCostTVa+"</td>";
   		    for (i = 0; i < 12; i++){
    			trStr += "<td style='text-align:right;'>"+MCostAmt[i]+"</td>"
         	}
    		trStr += "</tr>";
 			$('#mMCostTable').append(trStr);
        	
		}

		//년제조경비추가
		function addMCostYTable(pIndex,pMCostY,pCalYs) {
            let	sYMCostTitleValue 	= "";
            let	sYMCostCdValue    	= "";	
            let	sYMCostTVaValue    	= "";		
            let	sYCostTVaLabelValue = "";		
            let sMMCostMenuTotValue = "";
            let sYMCostAmtRateValue = "";
	        let	sYMCostTitle 	 	= "";
	        let	sYMCostCd			= "";	
	        let	sYMCostTVa 	 		= "";
	        let YCostAmtYear1		= "";
	        let YCostAmtYear2		= "";
	        let YCostAmtYear3		= "";

	        let iYCostAmtYear1		= 0;
	        let iYCostAmtYear2		= 0;
	        let iYCostAmtYear3		= 0;
	        
	        let YMCostAmtArr 		= new Array();              
	        let YMCostAmtCd        = "";
	        let YMCostAmtRate      = "";

        	var trStr="";

        	sYMCostTitleValue 	=  $('[name=sMCost01Title]')[pIndex].innerHTML;
        	sYMCostCdValue		=  $('[name=sMCost01Cd]')[pIndex].innerHTML;		
        	sYMCostTVaValue     =  $('[name=sMCost01TVa]')[pIndex].innerHTML;		
        	sYCostTVaLabelValue	=  $('[name=sMCost01TVaLabel]')[pIndex].innerHTML;	
        	sMMCostMenuTotValue =  $('[name=sMMCostMenuTot]')[pIndex].innerHTML;	

        	
	        if(pCalYs=="Y"){
	        	YMCostAmtCd      = "1";
	        	YMCostAmtRate    = "0";
	        	YMCostAmtArr[0]  = sMMCostMenuTotValue;
	        	YMCostAmtArr[1]  = sMMCostMenuTotValue;
	        	YMCostAmtArr[2]  = sMMCostMenuTotValue;
	        }else{
	        	YMCostAmtCd      = pMCostY[pIndex].YMCostAmtCd;
	        	YMCostAmtRate    = pMCostY[pIndex].YMCostAmtRate;
	        	YMCostAmtArr     = pMCostY[pIndex].YMCostAmtYear;              
	        }

        	
        	
        	sYMCostTitle = "<span name='sYMCostTitle'>"+sYMCostTitleValue+"</span><span name='sYMCostAmtCd' style='display:none'>"+YMCostAmtCd+"</span><span name='sYMCostAmtRate' style='display:none'>"+YMCostAmtRate+"</span>";
        	sYMCostCd 	 = "<span name='sYMCostCd'>"+sYMCostCdValue+"</span>";
        	sYMCostTVa 	 = "<span name='sYMCostTVa'>"+sYMCostTVaValue+"</span><span name='sYCostTVaLabel'>"+sYCostTVaLabelValue+"</span>";

 			YCostAmtYear1 = "<span name='sYMCostAmtYear1'>"+setComma(YMCostAmtArr[0])+"</span>";
 			YCostAmtYear2 = "<span name='sYMCostAmtYear2'>"+setComma(YMCostAmtArr[1])+"</span>";
 			YCostAmtYear3 = "<span name='sYMCostAmtYear3'>"+setComma(YMCostAmtArr[2])+"</span>";
 			
			trStr="<tr>";
			trStr += "<td>"+sYMCostTitle+"</td>";
			trStr += "<td>"+sYMCostCd+"</td>";
			trStr += "<td style='text-align:right;'>"+sYMCostTVa+"</td>";
			trStr += "<td style='text-align:right;'>"+YCostAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear3+"</td>"
			trStr += "<td rowspan='2' style='text-align:center;'><input name='btnUpdYYMCost' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#yMCostTable').append(trStr);
			
		}
		
		//판매경비셋팅
		function initSCostTable(pJson) {
			let SCostArr  = pJson.SCost;
			let SCostM    = pJson.SCostM;
			let SCostY    = pJson.SCostY;

			let i = 0;
			
			//판매경비 셋팅, 월 판매경비셋팅
 			if (SCostArr.length > 0) {
 				i = 0;
 				SCostArr.forEach(function(scost){
 					addSCostTable(scost.SCostTitle,scost.SCostTitleCd,scost.SCostCd,scost.SCostTVa,scost.SCostTVaLabel);
	            	addSCostMTable(i,SCostM,"N");
					i++;
	  	      });
	         }	
 			
 			calMSCost();
 			
            //년판매경비 세팅
 			if (SCostArr.length > 0) {
 				i = 0;
 				SCostArr.forEach(function(Menu){
					addSCostYTable(i,SCostY,"N");
					i++;
	  	      });
	         }	
            calYSCost();							
		}
		
		//판매경비추가
		function addSCostTable(pCodeMValue,pCodeMCdValue,pMCostCdValue,pCostTVaValue,pTVaLabelValue) {
			var inputSCostcdCodeM 	= "";
			var sSCostCdValue 		= "";
			var inputSCostTV 		= "";
			var SCostTVaAmt 		= "";
			var iSCostTVaAmt 		= 0;

			var inputSCostcdCodeMValue   = pCodeMValue;
			var inputSCostcdCodeMCdValue = pCodeMCdValue;
			var sSCostCdValue    		 = pMCostCdValue;
			var inputSCostTVaValue 		 = pCostTVaValue;
			var sSCostTVaLabelValue   	 = pTVaLabelValue;

			let SCostNo = "01";

			let  iMCostCdAmt       = 0;
			if(sSCostCdValue!=""){
				if(sSCostCdValue =="매출액" || sSCostCdValue =="기준액"|| sSCostCdValue =="인건비"){
					if(sSCostCdValue =="매출액") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMmenuAmt01Tot').innerHTML));
					if(sSCostCdValue =="기준액") iMCostCdAmt = getNumber(removeComma(document.getElementById('innvest04TotAmt').innerHTML));
					if(sSCostCdValue =="인건비") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMJobAmt01Sub2Tot').innerHTML));
					
					console.log("iMCostCdAmt",iMCostCdAmt);
					iSCostTVaAmt = (iMCostCdAmt * getNumber(inputSCostTVaValue)/100);
					iSCostTVaAmt = getNumberPoint(iSCostTVaAmt,0);
				} else {
					iSCostTVaAmt = getNumber(inputSCostTVaValue);
				}
			}

			inputSCostcdCodeM 	= "<span name='sSCost"+SCostNo+"Title'>"+inputSCostcdCodeMValue+"</span><span name='sSCost"+SCostNo+"TitleCd' style='display:none'>"+inputSCostcdCodeMCdValue+"</span>";
			sSCostCdValue   	= "<span name='sSCost"+SCostNo+"Cd'>"+sSCostCdValue+"</span>";
			inputSCostTV   		= "<span name='sSCost"+SCostNo+"TVa'>"+setComma(inputSCostTVaValue)+"</span><span name='sSCost"+SCostNo+"TVaLabel'>"+sSCostTVaLabelValue+"</span>";
			SCostTVaAmt   		= "<span name='sSCost"+SCostNo+"TVaAmt'>"+setComma(iSCostTVaAmt+"")+"</span>천원";
			
     		var trStr="";
    		trStr="<tr>";
			trStr += "<td>"+inputSCostcdCodeM+"</td>";
			trStr += "<td>"+sSCostCdValue+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputSCostTV+"</td>";
			trStr += "<td style=\"text-align:right;\">"+SCostTVaAmt+"</td>";
			trStr += "<td style=\"text-align:right;\">"+"<input name=\"btnUpdSCost"+SCostNo+"\" class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name=\"btnDelSCost"+SCostNo+"\" class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				  +"</td>";
			trStr += "</tr>";
			$('#SCostTable01').append(trStr);
			
		}
		
		//월판매경비추가
		function addSCostMTable(pIndex,pSCostM,pCalYs)  {
            let	sMSCostTitleValue   = "";  //계정과목
            let	sMSCostCdValue	 	= "";  //계정과목기준
            let sMSCostTVaValue 	= "";  //기준금액 
            let	sSCostTVaLabelValue = "";  //기준금액 단위
            let sSCostTVaAmt		= "";   //적용금액
            
	        let	sMSCostTitle 	 	 = "";
	        let sMSCostCd            = "";
		    let sMSCostTVa           = "";
	        let SCostAmt 	 		 = new Array(); //월재료비            
			let iSCostMenuAmt       = 0;     //메뉴별 월판매금액
			let iTemp               ="";
 			let i = 0;
			let iMSCostMenuTot      = 0;
        	var trStr="";

        	let SCostAmtArr 	 	 = new Array(); //월판관비            


        	sMSCostTitleValue 	=  $('[name=sSCost01Title]')[pIndex].innerHTML;
        	sMSCostCdValue		=  $('[name=sSCost01Cd]')[pIndex].innerHTML;		
        	sMSCostTVaValue     =  $('[name=sSCost01TVa]')[pIndex].innerHTML;		
        	sSCostTVaLabelValue	=  $('[name=sSCost01TVaLabel]')[pIndex].innerHTML;	
        	sSCostTVaAmtValue	=  removeComma($('[name=sSCost01TVaAmt]')[pIndex].innerHTML);	

        	if(sSCostTVaLabelValue=="천원") sSCostTVaLabelValue = "";
        	if(pCalYs=="Y"){
	        	if($('[name=sSCost01TVaAmt]')[pIndex].innerHTML!=""){
	        		iMSCostMenuTot      = getNumber(sSCostTVaAmtValue) *12;
	        	}
        	}else{
            	for (i = 0; i < 12; i++){
            		iMSCostMenuTot = iMSCostMenuTot + getNumber(pSCostM[pIndex].MSCostAmt[i]);
             	}
        		
        	}
        	
        	sMSCostTitle = "<span name='sMSCostTitle'>"+sMSCostTitleValue+"</span>";
        	sMSCostCd    = "<span name='sMSCostCd'>"+sMSCostCdValue+"</span><span name='sMSCostMenuTot' style='display:none'>"+setComma(iMSCostMenuTot+"")+"</span>";
        	sMSCostTVa   = "<span name='sMSCostTVa'>"+sMSCostTVaValue+"</span><span name='sSCostTVaLabel'>"+sSCostTVaLabelValue+"</span>"
 			
        	if(pCalYs=="Y"){
     			for (i = 0; i < 12; i++){
     				SCostAmtArr[i] = sSCostTVaAmtValue;
             	}
        	}else{
        		SCostAmtArr	= pSCostM[pIndex].MSCostAmt;              
        	}

        	
        	//월판매금액 셋팅
 			for (i = 0; i < 12; i++){
				if (i < 9 ) iTemp ="0"+(i+1)
				else iTemp = (i+1);
				SCostAmt[i] ="<span name='sMSCostAmt"+iTemp+"'>"+setComma(sSCostTVaAmtValue)+"</span>"
         	}
 			
			trStr="<tr>";
			trStr += "<td>"+sMSCostTitle+"</td>";
   			trStr += "<td>"+sMSCostCd+"</td>";
   			trStr += "<td style='text-align:right;'>"+sMSCostTVa+"</td>";
   		    for (i = 0; i < 12; i++){
    			trStr += "<td style='text-align:right;'>"+SCostAmt[i]+"</td>"
         	}
    		trStr += "</tr>";
 			$('#mSCostTable').append(trStr);
        	
		}

		//년판매경비추가
		function addSCostYTable(pIndex,pSCostY,pCalYs) {
            let	sYSCostTitleValue 	= "";
            let	sYSCostCdValue    	= "";	
            let	sYSCostTVaValue    	= "";		
            let	sYCostTVaLabelValue = "";		
            let sMSCostMenuTotValue = "";
            let sYSCostAmtRateValue = "";
	        let	sYSCostTitle 	 	= "";
	        let	sYSCostCd			= "";	
	        let	sYSCostTVa 	 		= "";
	        let YCostAmtYear1		= "";
	        let YCostAmtYear2		= "";
	        let YCostAmtYear3		= "";

	        let iYCostAmtYear1		= 0;
	        let iYCostAmtYear2		= 0;
	        let iYCostAmtYear3		= 0;
	        
        	var trStr="";

        	let YSCostAmtArr 		= new Array();              
	        let YSCostAmtCd         = "";
	        let YSCostAmtRate       = "";
        	

        	sYSCostTitleValue 	=  $('[name=sSCost01Title]')[pIndex].innerHTML;
        	sYSCostCdValue		=  $('[name=sSCost01Cd]')[pIndex].innerHTML;		
        	sYSCostTVaValue     =  $('[name=sSCost01TVa]')[pIndex].innerHTML;		
        	sYCostTVaLabelValue	=  $('[name=sSCost01TVaLabel]')[pIndex].innerHTML;	
        	sMSCostMenuTotValue =  $('[name=sMSCostMenuTot]')[pIndex].innerHTML;	
        	
	        if(pCalYs=="Y"){
	        	YSCostAmtCd      = "1";
	        	YSCostAmtRate    = "0";
	        	YSCostAmtArr[0]  = sMMCostMenuTotValue;
	        	YSCostAmtArr[1]  = sMMCostMenuTotValue;
	        	YSCostAmtArr[2]  = sMMCostMenuTotValue;
	        }else{
	        	YSCostAmtCd      = pSCostY[pIndex].YSCostAmtCd;
	        	YSCostAmtRate    = pSCostY[pIndex].YSCostAmtRate;
	        	YSCostAmtArr     = pSCostY[pIndex].YSCostAmtYear;              
	        }

        	
        	sYSCostTitle = "<span name='sYSCostTitle'>"+sYSCostTitleValue+"</span><span name='sYSCostAmtCd' style='display:none'>"+YSCostAmtCd+"</span><span name='sYSCostAmtRate' style='display:none'>"+YSCostAmtRate+"</span>";
        	sYSCostCd 	 = "<span name='sYSCostCd'>"+sYSCostCdValue+"</span>";
        	sYSCostTVa 	 = "<span name='sYSCostTVa'>"+sYSCostTVaValue+"</span><span name='sYCostTVaLabel'>"+sYCostTVaLabelValue+"</span>";

 			YCostAmtYear1 = "<span name='sYSCostAmtYear1'>"+setComma(sMSCostMenuTotValue)+"</span>";
 			YCostAmtYear2 = "<span name='sYSCostAmtYear2'>"+setComma(sMSCostMenuTotValue)+"</span>";
 			YCostAmtYear3 = "<span name='sYSCostAmtYear3'>"+setComma(sMSCostMenuTotValue)+"</span>";
 			
			trStr="<tr>";
			trStr += "<td>"+sYSCostTitle+"</td>";
			trStr += "<td>"+sYSCostCd+"</td>";
			trStr += "<td style='text-align:right;'>"+sYSCostTVa+"</td>";
			trStr += "<td style='text-align:right;'>"+YCostAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear3+"</td>"
			trStr += "<td rowspan='2' style='text-align:center;'><input name='btnUpdYYSCost' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#ySCostTable').append(trStr);
        	
			
		}
		
		
		//1.1업종선택
		function initIndustryDis(pValue) {
	   		if("1"==pValue){
		   	    document.getElementById('smenuTitle').innerHTML  = "제품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "제품가격";
		   	    document.getElementById('modalTitle').innerHTML  = "제품등록";
		   	    document.getElementById('scostTitle').innerHTML  = "제품명";
		   	    document.getElementById('scostPrice').innerHTML  = "제품가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "제품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "제품가격";
		   	    
		   	    document.getElementById('sMMenuTitle').innerHTML  = "제품명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "제품명/제품가격";
		   	 
	   		}else if("2"==pValue){
		   	    document.getElementById('smenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "상품가격";
		   	    document.getElementById('modalTitle').innerHTML  = "상품등록";
		   	    document.getElementById('scostTitle').innerHTML  = "상품명";
		   	    document.getElementById('scostPrice').innerHTML  = "상품가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "상품가격";

		   	    document.getElementById('sMMenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "상품명/상품가격";
	   		}else if("3"==pValue){
		   	    document.getElementById('smenuTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('smenuPrice').innerHTML  = "메뉴가격";
		   	    document.getElementById('modalTitle').innerHTML  = "메뉴등록";
		   	    document.getElementById('scostTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('scostPrice').innerHTML  = "메뉴가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "메뉴가격";

		   	    document.getElementById('sMMenuTitle').innerHTML  = "메뉴명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "메뉴명/메뉴가격";
	   		}else if("4"==pValue){
		   	    document.getElementById('smenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('smenuPrice').innerHTML  = "서비스가격";
		   	    document.getElementById('modalTitle').innerHTML  = "서비스등록";
		   	    document.getElementById('scostTitle').innerHTML  = "서비스명";
		   	    document.getElementById('scostPrice').innerHTML  = "서비스가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "서비스가격";

		   	    document.getElementById('sMMenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "서비스명/서비스가격";
		   	    
	   		}else if("5"==pValue){
		   	    document.getElementById('smenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('smenuPrice').innerHTML  = "서비스가격";
		   	    document.getElementById('modalTitle').innerHTML  = "서비스등록";
		   	    document.getElementById('scostTitle').innerHTML  = "서비스명";
		   	    document.getElementById('scostPrice').innerHTML  = "서비스가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "서비스가격";

		   	    document.getElementById('sMMenuTitle').innerHTML  = "서비스명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "서비스명/서비스가격";
		   	    
			}else{
		   	    document.getElementById('modalTitle').innerHTML  = "상품등록";
		   	    document.getElementById('smenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('smenuPrice').innerHTML  = "상품가격";
		   	    document.getElementById('scostTitle').innerHTML  = "상품명";
		   	    document.getElementById('scostPrice').innerHTML  = "상품가격";
		   	    document.getElementById('modalmenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('modalmenuPrice').innerHTML  = "상품가격";

		   	    document.getElementById('sMMenuTitle').innerHTML  = "상품명";
		   	    document.getElementById('sYMenuTitle').innerHTML  = "상품명/상품가격";
			}
			
		}
		
		$('#sector_se_code_m').on('change', function(e) {
			industryCd = this.value;
			initIndustryDis(industryCd);
		});

		$('#day_se_code_m').on('change', function(e) {
			workDay = getNumber(this.value);
			//월매출,년간매출,월재료비, 년간재료비 다시계산
 			calSale();							//일매출액계산
            calMSale();							//월매출계산
            calYSale();							//년매출계산
            calCost();							//재료비계산
		});
		
		//연도별 매출등록팝업 입력방법선택	
		$('#inputYmenuCd').on('change', function(e) {
			initPopYMenu(this.value);
		});

		function initPopYMenu(pCd) {
	   		if("1"==pCd){
		   		$('#modalYMenuQtyRate').show();
		   		$('#modalYMenuPriceRate').show();
		   		$("#inputMenuQtyYear1").attr("readonly", true);
		   		$("#inputMenuQtyYear2").attr("readonly", true);
		   		$("#inputMenuQtyYear3").attr("readonly", true);
		   		$("#inputMenuPriceYear1").attr("readonly", true);
		   		$("#inputMenuPriceYear2").attr("readonly", true);
		   		$("#inputMenuPriceYear3").attr("readonly", true);
		   	}else if("2"==pCd){
		   		$('#modalYMenuQtyRate').hide();
		   		$('#modalYMenuPriceRate').hide();
		   		$("#inputMenuQtyYear1").attr("readonly", true);
		   		$("#inputMenuQtyYear2").attr("readonly", false);
		   		$("#inputMenuQtyYear3").attr("readonly", false);
		   		$("#inputMenuPriceYear1").attr("readonly", true);
		   		$("#inputMenuPriceYear2").attr("readonly", false);
		   		$("#inputMenuPriceYear3").attr("readonly", false);
			}
		 	document.getElementById('inputYmenuCd').value   	 = pCd;
		 	document.getElementById('inputYMenuQtyRate').value   = removeComma($('[name=sYMenuQtyRate]')[yMenuIndexNo].innerHTML);
		 	document.getElementById('inputYMenuPriceRate').value = removeComma($('[name=sYMenuPriceRate]')[yMenuIndexNo].innerHTML);
		 	document.getElementById('inputMenuQtyYear1').value   = removeComma($('[name=sYMenuQtyYear1]')[yMenuIndexNo].innerHTML);
			document.getElementById('inputMenuQtyYear2').value   = removeComma($('[name=sYMenuQtyYear2]')[yMenuIndexNo].innerHTML);
			document.getElementById('inputMenuQtyYear3').value   = removeComma($('[name=sYMenuQtyYear3]')[yMenuIndexNo].innerHTML);

		 	document.getElementById('inputMenuPriceYear1').value = removeComma($('[name=sYMenuPriceYear1]')[yMenuIndexNo].innerHTML);
			document.getElementById('inputMenuPriceYear2').value = removeComma($('[name=sYMenuPriceYear2]')[yMenuIndexNo].innerHTML);
			document.getElementById('inputMenuPriceYear3').value = removeComma($('[name=sYMenuPriceYear3]')[yMenuIndexNo].innerHTML);

			calPopYAmt();
		}

		//년 판매금액 계산
		function calPopYAmt() {
			let YMenuAmtYear1 = 0;
			let YMenuAmtYear2 = 0;
			let YMenuAmtYear3 = 0;

			var inputMenuQtyYear1Value 		= document.getElementById('inputMenuQtyYear1').value;
			var inputMenuQtyYear2Value 		= document.getElementById('inputMenuQtyYear2').value;
			var inputMenuQtyYear3Value 		= document.getElementById('inputMenuQtyYear3').value;
			var inputMenuPriceYear1Value 	= document.getElementById('inputMenuPriceYear1').value;
			var inputMenuPriceYear2Value 	= document.getElementById('inputMenuPriceYear2').value;
			var inputMenuPriceYear3Value 	= document.getElementById('inputMenuPriceYear3').value;
			
			YMenuAmtYear1 = (getNumber(inputMenuQtyYear1Value) * getNumber(inputMenuPriceYear1Value))/1000;
			YMenuAmtYear2 = (getNumber(inputMenuQtyYear2Value) * getNumber(inputMenuPriceYear2Value))/1000;
			YMenuAmtYear3 = (getNumber(inputMenuQtyYear3Value) * getNumber(inputMenuPriceYear3Value))/1000;

			
			document.getElementById('inputMenuAmtYear1').value   = removeComma(YMenuAmtYear1+"");
			document.getElementById('inputMenuAmtYear2').value   = removeComma(YMenuAmtYear2+"");
			document.getElementById('inputMenuAmtYear3').value   = removeComma(YMenuAmtYear3+"");

		}
		
		document.getElementById('btnAddMenu').addEventListener('click',function(e){
				console.log("*****btnAddMenu*******");
				if (document.getElementById('inputMenuTitle').value == "") {
					alert("명칭을 입력하세요");
					 document.getElementById("inputMenuTitle").focus();
					return;
				}
				if (document.getElementById('inputMenuPrice').value == "") {
					alert("가격을 입력하세요");
					 document.getElementById("inputMenuPrice").focus();
					return;
				}
				if (document.getElementById('inputMenuQty').value == "") {
					alert("수량을 입력하세요");
					 document.getElementById("inputMenuQty").focus();
					return;
				}
				
				var inputMenuAmt = 0;
				var inputMenuTitleValue = document.getElementById('inputMenuTitle').value;
				var inputMenuPriceValue = document.getElementById('inputMenuPrice').value;
				var inputMenuQtyValue 	= document.getElementById('inputMenuQty').value;

				if(saleAddFlag=="ADD") {		
					addMenuTable(inputMenuTitleValue,inputMenuPriceValue,inputMenuQtyValue);

					let dmenuIndex = document.querySelectorAll("[name=dmenuTitle]").length;
					
					setCostTable("ADD",(dmenuIndex-1));
					setMMenuTable("ADD",(dmenuIndex-1));
					setMCostTable("ADD",(dmenuIndex-1)); 
					
				} else{
					inputMenuAmt = getNumber(inputMenuPriceValue * inputMenuQtyValue);
					$('[name=dmenuTitle]')[saleIndexNo].innerHTML 	= inputMenuTitleValue;
					$('[name=dmenuPrice]')[saleIndexNo].innerHTML 	= setComma(inputMenuPriceValue);
					$('[name=dmenuQty]')[saleIndexNo].innerHTML 	= setComma(inputMenuQtyValue);
					$('[name=dmenuAmt]')[saleIndexNo].innerHTML 	= setComma(inputMenuAmt+"");

					setCostTable("UPD",saleIndexNo);
					setMMenuTable("UPD",saleIndexNo);
					setMCostTable("UPD",saleIndexNo); 
				}

				calSale();
				$('#myModal').modal('toggle');
		 });

			
		 $('#btnMenu').on('click', function(e) {
			 	saleAddFlag="ADD";
				document.getElementById('btnAddMenu').value="추가";
				document.getElementById('inputMenuTitle').value ="";
				document.getElementById('inputMenuPrice').value ="";
				document.getElementById('inputMenuQty').value 	="";
				$('#myModal').modal('toggle');
		 });

		 $('#btnMMenuS').on('click', function(e) {
			$('#mMenu').show();
			$('#yMenu').hide();
		 });

		 $('#btnYMenuS').on('click', function(e) {
			$('#mMenu').hide();
			$('#yMenu').show();
		 });
		 
		 $('#btnMMenuH').on('click', function(e) {
			$('#mMenu').hide();
		 });

		 $('#btnYMenuH').on('click', function(e) {
			$('#yMenu').hide();
		 });

	     //재료비 월년 보이기
		 $('#btnMCostS').on('click', function(e) {
				$('#mCost').show();
				$('#yCost').hide();
		 });

		 $('#btnYCostS').on('click', function(e) {
				$('#mCost').hide();
				$('#yCost').show();
		 });
			 
		 $('#btnMCostH').on('click', function(e) {
				$('#mCost').hide();
		 });

		 $('#btnYCostH').on('click', function(e) {
				$('#yCost').hide();
		 });

		 //재조경비 월년 보이기
		 $('#btnMMCostS').on('click', function(e) {
				$('#mMCost').show();
				$('#yMCost').hide();
		 });

		 $('#btnMYCostS').on('click', function(e) {
				$('#mMCost').hide();
				$('#yMCost').show();
		 });
			 
		 $('#btnMMCostH').on('click', function(e) {
				$('#mMCost').hide();
		 });

		 $('#btnYMCostH').on('click', function(e) {
				$('#yMCost').hide();
		 });
		 
		 $(document).on('click', 'input[name=btnUpdMenu]', function() {
			 	saleAddFlag="UPD";
				document.getElementById('btnAddMenu').value="수정";
			 	saleIndexNo = $('input[name=btnUpdMenu]').index(this);
				
				document.getElementById('inputMenuTitle').value = $('[name=dmenuTitle]')[saleIndexNo].innerHTML;
				document.getElementById('inputMenuPrice').value = removeComma($('[name=dmenuPrice]')[saleIndexNo].innerHTML);
				document.getElementById('inputMenuQty').value 	= removeComma($('[name=dmenuQty]')[saleIndexNo].innerHTML);
				$('#myModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelMenu]', function() {
				if(!confirm('메뉴항목을 삭제하시겠습니까?')){
					return;
				}

				saleIndexNo = $('input[name=btnDelMenu]').index(this);
				document.getElementById("menuTable").deleteRow(saleIndexNo+1);

				setCostTable("DEL",(saleIndexNo+1));
				setMMenuTable("DEL",(saleIndexNo));
				setMCostTable("DEL",(saleIndexNo));
				calSale();
			 	
		});

		 $(document).on('click', 'input[name=btnUpdYMenu]', function() {
			    yMenuIndexNo = $('input[name=btnUpdYMenu]').index(this);
			 	document.getElementById('YmenuTitle').innerHTML = $('[name=sYMenuTitle]')[yMenuIndexNo].innerHTML;

			 	let yMenuCD = $('[name=sYmenuCd]')[yMenuIndexNo].innerHTML;
				initPopYMenu(yMenuCD);

				
				$('#myYMenuModal').modal('toggle');
		});

		document.getElementById('btnAddYMenu').addEventListener('click',function(e){
				console.log("*****btnAddYMenu*******");

		        let inputYmenuCd 				= document.getElementById('inputYmenuCd');        
				var inputYmenuCdValue 			= inputYmenuCd.options[inputYmenuCd.selectedIndex].value;

				if (inputYmenuCdValue == "1") {

					if(document.getElementById('inputYMenuQtyRate').value ==""){
						 alert("판매수량 증감율을 입력하세요");
						 document.getElementById("inputYMenuQtyRate").focus();
						return;
					}

					if(document.getElementById('inputYMenuPriceRate').value ==""){
						 alert("판매단가 증감율을 입력하세요");
						 document.getElementById("inputYMenuPriceRate").focus();
						return;
					}
					
				} else{
					if(document.getElementById('inputMenuQtyYear2').value ==""){
						 alert("2차년도 판매수량을  입력하세요");
						 document.getElementById("inputMenuQtyYear2").focus();
						return;
					}
					if(document.getElementById('inputMenuQtyYear3').value ==""){
						 alert("3차년도 판매수량을  입력하세요");
						 document.getElementById("inputMenuQtyYear3").focus();
						return;
					}
					if(document.getElementById('inputMenuPriceYear2').value ==""){
						 alert("2차년도 판매단가를  입력하세요");
						 document.getElementById("inputMenuPriceYear2").focus();
						return;
					}
					if(document.getElementById('inputMenuPriceYear3').value ==""){
						 alert("3차년도 판매단가를  입력하세요");
						 document.getElementById("inputMenuPriceYear3").focus();
						return;
					}

				}


				var inputYMenuQtyRateValue 		= document.getElementById('inputYMenuQtyRate').value;
				var inputYMenuPriceRateValue 	= document.getElementById('inputYMenuPriceRate').value;
				var inputMenuQtyYear1Value 		= document.getElementById('inputMenuQtyYear1').value;
				var inputMenuQtyYear2Value 		= document.getElementById('inputMenuQtyYear2').value;
				var inputMenuQtyYear3Value 		= document.getElementById('inputMenuQtyYear3').value;
				var inputMenuPriceYear1Value 	= document.getElementById('inputMenuPriceYear1').value;
				var inputMenuPriceYear2Value 	= document.getElementById('inputMenuPriceYear2').value;
				var inputMenuPriceYear3Value 	= document.getElementById('inputMenuPriceYear3').value;
				
				let YMenuAmtYear1 = 0;
				let YMenuAmtYear2 = 0;
				let YMenuAmtYear3 = 0;
				
				YMenuAmtYear1 = (getNumber(inputMenuQtyYear1Value) * getNumber(inputMenuPriceYear1Value))/1000;
				YMenuAmtYear2 = (getNumber(inputMenuQtyYear2Value) * getNumber(inputMenuPriceYear2Value))/1000;
				YMenuAmtYear3 = (getNumber(inputMenuQtyYear3Value) * getNumber(inputMenuPriceYear3Value))/1000;
				

				$('[name=sYmenuCd]')[yMenuIndexNo].innerHTML 			= inputYmenuCdValue;
				$('[name=sYMenuQtyRate]')[yMenuIndexNo].innerHTML 		= inputYMenuQtyRateValue;
				$('[name=sYMenuPriceRate]')[yMenuIndexNo].innerHTML 	= inputYMenuPriceRateValue;
				//$('[name=sYMenuQtyYear1]')[yMenuIndexNo].innerHTML 		= setComma(inputMenuQtyYear1Value);
				$('[name=sYMenuQtyYear2]')[yMenuIndexNo].innerHTML 		= setComma(inputMenuQtyYear2Value);
				$('[name=sYMenuQtyYear3]')[yMenuIndexNo].innerHTML 		= setComma(inputMenuQtyYear3Value);
				$('[name=sYMenuPriceYear1]')[yMenuIndexNo].innerHTML	= setComma(inputMenuPriceYear1Value);
				$('[name=sYMenuPriceYear2]')[yMenuIndexNo].innerHTML	= setComma(inputMenuPriceYear2Value);
				$('[name=sYMenuPriceYear3]')[yMenuIndexNo].innerHTML	= setComma(inputMenuPriceYear3Value);

				//판매금액
				//$('[name=sYMenuAmtYear1]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear1+"");
				$('[name=sYMenuAmtYear2]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear2+"");
				$('[name=sYMenuAmtYear3]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear3+"");

				//년매출 합계계산
				calYSale();
				$('#myYMenuModal').modal('toggle');
		 });

		 //연도별 판매수량 증가율 계산 
		 $('#inputYMenuQtyRate').on('change', function(e) {
			if (this.value != ""){
				var inputYMenuQtyRateValue 		= document.getElementById('inputYMenuQtyRate').value;
				var inputMenuQtyYear1Value 		= document.getElementById('inputMenuQtyYear1').value;
				var inputMenuQtyYear2Value 		= document.getElementById('inputMenuQtyYear2').value;
				var inputMenuQtyYear3Value 		= document.getElementById('inputMenuQtyYear3').value;

				let inputMenuQtyYear1 = getNumber(inputMenuQtyYear1Value);
				let inputMenuQtyYear2 = 0;
				let inputMenuQtyYear3 = 0;

				let inputYMenuQtyRate = getNumber(inputYMenuQtyRateValue);
				
				inputMenuQtyYear2 = inputMenuQtyYear1 * (1+(inputYMenuQtyRate/100)) ;
				inputMenuQtyYear3 = inputMenuQtyYear2 * (1+(inputYMenuQtyRate/100)) ;

				inputMenuQtyYear2=Math.floor(inputMenuQtyYear2);
				inputMenuQtyYear3=Math.floor(inputMenuQtyYear3);
				
				document.getElementById('inputMenuQtyYear2').value =inputMenuQtyYear2+"";
				document.getElementById('inputMenuQtyYear3').value =inputMenuQtyYear3+"";
				
				calPopYAmt();

			}
		 });

		 //연도별 판매단가 증가율 계산 
		 $('#inputYMenuPriceRate').on('change', function(e) {
			if (this.value != ""){

				var inputYMenuPriceRateValue 	= document.getElementById('inputYMenuPriceRate').value;
				var inputMenuPriceYear1Value 	= document.getElementById('inputMenuPriceYear1').value;
				var inputMenuPriceYear2Value 	= document.getElementById('inputMenuPriceYear2').value;
				var inputMenuPriceYear3Value 	= document.getElementById('inputMenuPriceYear3').value;

				let inputMenuPriceYear1 = getNumber(inputMenuPriceYear1Value);
				let inputMenuPriceYear2 = 0;
				let inputMenuPriceYear3 = 0;

				let inputYMenuPriceRate = getNumber(inputYMenuPriceRateValue);
				
				inputMenuPriceYear2 = inputMenuPriceYear1 * (1+(inputYMenuPriceRate/100)) ;
				inputMenuPriceYear3 = inputMenuPriceYear2 * (1+(inputYMenuPriceRate/100)) ;

				inputMenuPriceYear2=Math.floor(inputMenuPriceYear2);
				inputMenuPriceYear3=Math.floor(inputMenuPriceYear3);
				
				document.getElementById('inputMenuPriceYear2').value =inputMenuPriceYear2+"";
				document.getElementById('inputMenuPriceYear3').value =inputMenuPriceYear3+"";

				calPopYAmt();

			}
		 });

		 $(document).on('click', 'input[name=btnUpdYCost]', function() {
			    yCostIndexNo = $('input[name=btnUpdYCost]').index(this);
			 	initPopYCost(yCostIndexNo);
				$('#myYCostModal').modal('toggle');
		 });

		 function initPopYCost(pIndexNO) {
			 	document.getElementById('YCostTitle').innerHTML 		= $('[name=sYCostTitle]')[pIndexNO].innerHTML;
			 	document.getElementById('inputYCostAmtRate').value   	= $('[name=sYCostAmtRate]')[pIndexNO].innerHTML;
			 	document.getElementById('inputCostMenuAmtYear1').value  = removeComma($('[name=sYCostMenuAmtYear1]')[pIndexNO].innerHTML);
			 	document.getElementById('inputCostMenuAmtYear2').value  = removeComma($('[name=sYCostMenuAmtYear2]')[pIndexNO].innerHTML);
			 	document.getElementById('inputCostMenuAmtYear3').value  = removeComma($('[name=sYCostMenuAmtYear3]')[pIndexNO].innerHTML);
			 	document.getElementById('inputCostAmtYear1').value  	= removeComma($('[name=sYCostAmtYear1]')[pIndexNO].innerHTML);
			 	document.getElementById('inputCostAmtYear2').value  	= removeComma($('[name=sYCostAmtYear2]')[pIndexNO].innerHTML);
			 	document.getElementById('inputCostAmtYear3').value  	= removeComma($('[name=sYCostAmtYear3]')[pIndexNO].innerHTML);
			 	$("#inputCostMenuAmtYear1").attr("readonly", true);
			 	$("#inputCostMenuAmtYear2").attr("readonly", true);
			 	$("#inputCostMenuAmtYear3").attr("readonly", true);
			 	$("#inputCostAmtYear1").attr("readonly", true);
			 	$("#inputCostAmtYear2").attr("readonly", true);
			 	$("#inputCostAmtYear3").attr("readonly", true);
		}
		//연도별 재료비 증가율 계산 
		$('#inputYCostAmtRate').on('change', function(e) {
			if (this.value != ""){
				var inputYCostAmtRateValue 		= document.getElementById('inputYCostAmtRate').value;
				var inputCostAmtYear1Value 		= document.getElementById('inputCostAmtYear1').value;

				let inputCostAmtYear1 = getNumber(inputCostAmtYear1Value);
				let inputCostAmtYear2 = 0;
				let inputCostAmtYear3 = 0;

				let inputYCostAmtRate = getNumber(inputYCostAmtRateValue);
				
				inputCostAmtYear2 = inputCostAmtYear1 * (1+(inputYCostAmtRate/100)) ;
				inputCostAmtYear3 = inputCostAmtYear2 * (1+(inputYCostAmtRate/100)) ;

				inputCostAmtYear2=Math.floor(inputCostAmtYear2);
				inputCostAmtYear3=Math.floor(inputCostAmtYear3);
				
				document.getElementById('inputCostAmtYear2').value =inputCostAmtYear2+"";
				document.getElementById('inputCostAmtYear3').value =inputCostAmtYear3+"";

			}
		 });

		//연도별 재료비등록
		document.getElementById('btnAddYCost').addEventListener('click',function(e){
				console.log("*****btnAddYCost*******");

				if(document.getElementById('inputYCostAmtRate').value ==""){
					 alert("재료비 증감율을 입력하세요");
					 document.getElementById("inputYCostAmtRate").focus();
					return;
				}

				var inputYCostAmtRateValue 		= document.getElementById('inputYCostAmtRate').value;
				var inputCostMenuAmtYear1Value 	= document.getElementById('inputCostMenuAmtYear1').value;
				var inputCostMenuAmtYear2Value 	= document.getElementById('inputCostMenuAmtYear2').value;
				var inputCostMenuAmtYear3Value 	= document.getElementById('inputCostMenuAmtYear3').value;
				var inputCostAmtYear1Value 		= document.getElementById('inputCostAmtYear1').value;
				var inputCostAmtYear2Value 		= document.getElementById('inputCostAmtYear2').value;
				var inputCostAmtYear3Value 		= document.getElementById('inputCostAmtYear3').value;


				$('[name=sYCostAmtRate]')[yCostIndexNo].innerHTML 		= inputYCostAmtRateValue;

				$('[name=sYCostMenuAmtYear2]')[yCostIndexNo].innerHTML 	= setComma(inputCostMenuAmtYear2Value);
				$('[name=sYCostMenuAmtYear3]')[yCostIndexNo].innerHTML 	= setComma(inputCostMenuAmtYear3Value);

				$('[name=sYCostAmtYear2]')[yCostIndexNo].innerHTML		= setComma(inputCostAmtYear2Value);
				$('[name=sYCostAmtYear3]')[yCostIndexNo].innerHTML		= setComma(inputCostAmtYear3Value);

				//년매출 재료비 합계계산
				//calYSale();
				$('#myYCostModal').modal('toggle');
		 });
		 
		 //재료비 계산 
		 function initCostRateDis(pValue,pIndex,pMCost,pYCost,pCalYs) {
			if (pValue == ""){
				$('[name=dcostAmt]')[pIndex].innerHTML = "";
				calCost();
				return; 	
			}
		 	
			let iCostRate  = getNumber(pValue);
			let iMenuPrice = getNumber(removeComma($('[name=dcostPrice]')[pIndex].innerHTML))
			let iMenuQty   = getNumber(removeComma($('[name=dmenuQty]')[pIndex].innerHTML))
			
			let iCostAmt    = 0;
			let iMCostAmt   = 0;
			let iYCostAmt   = 0;
			
			
	        let MCostAmtArr  		= new Array();  //월재료비            
	        let YCostAmtArr  		= new Array();  //년재료비            
	        let i = 0;

			let YCostCdValue="";
			let YCostAmtRateValue="";

			iCostAmt   = ((iCostRate * iMenuPrice)/100).toFixed(0);
			
			$('[name=dcostAmt]')[pIndex].innerHTML = setComma(iCostAmt+""); 	

			if(pCalYs == "Y"){
				iMCostAmt   = getNumberPoint((iCostAmt * iMenuQty * workDay)/1000,0);
				iYCostAmt   = iMCostAmt * 12;
				
     			for (i = 0; i < 12; i++){
     				MCostAmtArr[i] = iMCostAmt;
             	}
     			
     			for (i = 0; i < 3; i++){
     				YCostAmtArr[i] = iYCostAmt;
             	}

    			YCostCdValue="1";
    			YCostAmtRateValue="0";

			}else{
		        MCostAmtArr  		= pMCost[pIndex].MCostAmt;  //월재료비            
		        YCostAmtArr  		= pYCost[pIndex].YCostAmt;  //년재료비            
    			YCostCdValue		= pYCost[pIndex].YCostCd;
    			YCostAmtRateValue	= pYCost[pIndex].YCostAmtRate;
			}
			
            //월재료비 셋팅
			$('[name=sMCostRate]')[pIndex].innerHTML  = pValue; 	
			$('[name=sMCostAmt01]')[pIndex].innerHTML = setComma(MCostAmtArr[0]+""); 	
			$('[name=sMCostAmt02]')[pIndex].innerHTML = setComma(MCostAmtArr[1]+""); 	
			$('[name=sMCostAmt03]')[pIndex].innerHTML = setComma(MCostAmtArr[2]+""); 	
			$('[name=sMCostAmt04]')[pIndex].innerHTML = setComma(MCostAmtArr[3]+""); 	
			$('[name=sMCostAmt05]')[pIndex].innerHTML = setComma(MCostAmtArr[4]+""); 	
			$('[name=sMCostAmt06]')[pIndex].innerHTML = setComma(MCostAmtArr[5]+""); 	
			$('[name=sMCostAmt07]')[pIndex].innerHTML = setComma(MCostAmtArr[6]+""); 	
			$('[name=sMCostAmt08]')[pIndex].innerHTML = setComma(MCostAmtArr[7]+""); 	
			$('[name=sMCostAmt09]')[pIndex].innerHTML = setComma(MCostAmtArr[8]+""); 	
			$('[name=sMCostAmt10]')[pIndex].innerHTML = setComma(MCostAmtArr[9]+""); 	
			$('[name=sMCostAmt11]')[pIndex].innerHTML = setComma(MCostAmtArr[10]+""); 	
			$('[name=sMCostAmt12]')[pIndex].innerHTML = setComma(MCostAmtArr[11]+""); 	
			$('[name=sMCostAmtTot]')[pIndex].innerHTML= setComma(YCostAmtArr[0]+""); 	

			//연재료비 셋팅
			$('[name=sYCostRate]')[pIndex].innerHTML 	  = pValue; 	
			$('[name=sYCostAmtYear1]')[pIndex].innerHTML = setComma(YCostAmtArr[0]+""); 	
			$('[name=sYCostAmtYear2]')[pIndex].innerHTML = setComma(YCostAmtArr[1]+""); 	
			$('[name=sYCostAmtYear3]')[pIndex].innerHTML = setComma(YCostAmtArr[2]+""); 

			$('[name=sYCostCd]')[pIndex].innerHTML = YCostCdValue; 
			$('[name=sYCostAmtRate]')[pIndex].innerHTML = YCostAmtRateValue; 
			
			
		 }
		 

		 $(document).on('change', 'input[name=icostRate]', function() {
			costIndexNo = $('input[name=icostRate]').index(this);
			initCostRateDis(this.value,costIndexNo,"","",'Y');
			//재료비 합산 
			calCost();
		});

		 
		 
		 $('#btnCalSele').on('click', function(e) {
			 console.log("*************btnCalSele click*****");
			 calSale();
		 });
		 

		 //매출액 계산
		 function calSale() {
			 //일매출금액
			wrapLoadingMask("show");
            let daySaleAmt = 0;
            let monthSaleAmt = 0;
            let yearSaleAmt = 0;

 			let menuAmt = document.querySelectorAll("[name=dmenuAmt]");

 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		daySaleAmt = daySaleAmt + getNumber(removeComma(node.innerHTML));
	  	      });
	         }	

			 if (daySaleAmt > 0) {
	             
				 //월간매출금액
		         let day_se_code_m = document.getElementById('day_se_code_m');        
				 workDay = getNumber(day_se_code_m.options[day_se_code_m.selectedIndex].value);
	             monthSaleAmt = daySaleAmt * workDay
	             
				 //연간 매출금액
	             yearSaleAmt = monthSaleAmt * 12
			 }

			 document.getElementById('daySaleAmt').innerHTML = setComma(daySaleAmt+"");
             document.getElementById('monthSaleAmt').innerHTML = setComma(monthSaleAmt+"");
             document.getElementById('yearSaleAmt').innerHTML = setComma(yearSaleAmt+"");

             calNo();
			 wrapLoadingMask("hide");

		 }

		 //매출액 월계산
		 function calMSale() {

			let menuAmt 	 = document.querySelectorAll("[name=sMmenuQty01]");
			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;
			let menuQty04Tot = 0;
			let menuQty05Tot = 0;
			let menuQty06Tot = 0;
			let menuQty07Tot = 0;
			let menuQty08Tot = 0;
			let menuQty09Tot = 0;
			let menuQty10Tot = 0;
			let menuQty11Tot = 0;
			let menuQty12Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;
			let menuAmt04Tot = 0;
			let menuAmt05Tot = 0;
			let menuAmt06Tot = 0;
			let menuAmt07Tot = 0;
			let menuAmt08Tot = 0;
			let menuAmt09Tot = 0;
			let menuAmt10Tot = 0;
			let menuAmt11Tot = 0;
			let menuAmt12Tot = 0;

			let menuQtyTot     = 0;
			let menuAmtTot     = 0;

			let menuIndex=0;
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		menuQty01Tot = menuQty01Tot + getNumber(removeComma(node.innerHTML));
	        		menuQty02Tot = menuQty02Tot + getNumber(removeComma($('[name=sMmenuQty02]')[menuIndex].innerHTML));
	        		menuQty03Tot = menuQty03Tot + getNumber(removeComma($('[name=sMmenuQty03]')[menuIndex].innerHTML));
	        		menuQty04Tot = menuQty04Tot + getNumber(removeComma($('[name=sMmenuQty04]')[menuIndex].innerHTML));
	        		menuQty05Tot = menuQty05Tot + getNumber(removeComma($('[name=sMmenuQty05]')[menuIndex].innerHTML));
	        		menuQty06Tot = menuQty06Tot + getNumber(removeComma($('[name=sMmenuQty06]')[menuIndex].innerHTML));
	        		menuQty07Tot = menuQty07Tot + getNumber(removeComma($('[name=sMmenuQty07]')[menuIndex].innerHTML));
	        		menuQty08Tot = menuQty08Tot + getNumber(removeComma($('[name=sMmenuQty08]')[menuIndex].innerHTML));
	        		menuQty09Tot = menuQty09Tot + getNumber(removeComma($('[name=sMmenuQty09]')[menuIndex].innerHTML));
	        		menuQty10Tot = menuQty10Tot + getNumber(removeComma($('[name=sMmenuQty10]')[menuIndex].innerHTML));
	        		menuQty11Tot = menuQty11Tot + getNumber(removeComma($('[name=sMmenuQty11]')[menuIndex].innerHTML));
	        		menuQty12Tot = menuQty12Tot + getNumber(removeComma($('[name=sMmenuQty12]')[menuIndex].innerHTML));

	        		menuQtyTot  = getNumber(removeComma(node.innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty02]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty03]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty04]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty05]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty06]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty07]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty08]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty09]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty10]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty11]')[menuIndex].innerHTML))
	    			              +getNumber(removeComma($('[name=sMmenuQty11]')[menuIndex].innerHTML));

	    			$('[name=sMmenuQtyTot]')[menuIndex].innerHTML = menuQtyTot+"";
		    			
	    			menuAmt01Tot = menuAmt01Tot + getNumber(removeComma($('[name=sMmenuAmt01]')[menuIndex].innerHTML));
	        		menuAmt02Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt02]')[menuIndex].innerHTML));
	        		menuAmt03Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt03]')[menuIndex].innerHTML));
	        		menuAmt04Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt04]')[menuIndex].innerHTML));
	        		menuAmt05Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt05]')[menuIndex].innerHTML));
	        		menuAmt06Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt06]')[menuIndex].innerHTML));
	        		menuAmt07Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt07]')[menuIndex].innerHTML));
	        		menuAmt08Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt08]')[menuIndex].innerHTML));
	        		menuAmt09Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt09]')[menuIndex].innerHTML));
	        		menuAmt10Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt10]')[menuIndex].innerHTML));
	        		menuAmt11Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt11]')[menuIndex].innerHTML));
	        		menuAmt12Tot = menuAmt02Tot + getNumber(removeComma($('[name=sMmenuAmt12]')[menuIndex].innerHTML));

	        		menuAmtTot  =  getNumber(removeComma($('[name=sMmenuAmt01]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt02]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt03]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt04]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt05]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt06]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt07]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt08]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt09]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt10]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt11]')[menuIndex].innerHTML))
		              			  +getNumber(removeComma($('[name=sMmenuAmt12]')[menuIndex].innerHTML));
		
	    			$('[name=sMmenuAmtTot]')[menuIndex].innerHTML = menuAmtTot+"";

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sMmenuQty01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sMmenuQty02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sMmenuQty03Tot').innerHTML = setComma(menuQty03Tot+"");
			 document.getElementById('sMmenuQty04Tot').innerHTML = setComma(menuQty04Tot+"");
			 document.getElementById('sMmenuQty05Tot').innerHTML = setComma(menuQty05Tot+"");
			 document.getElementById('sMmenuQty06Tot').innerHTML = setComma(menuQty06Tot+"");
			 document.getElementById('sMmenuQty07Tot').innerHTML = setComma(menuQty07Tot+"");
			 document.getElementById('sMmenuQty08Tot').innerHTML = setComma(menuQty08Tot+"");
			 document.getElementById('sMmenuQty09Tot').innerHTML = setComma(menuQty09Tot+"");
			 document.getElementById('sMmenuQty10Tot').innerHTML = setComma(menuQty10Tot+"");
			 document.getElementById('sMmenuQty11Tot').innerHTML = setComma(menuQty11Tot+"");
			 document.getElementById('sMmenuQty12Tot').innerHTML = setComma(menuQty12Tot+"");

			 document.getElementById('sMmenuAmt01Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt02Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt03Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt04Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt05Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt06Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt07Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt08Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt09Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt10Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt11Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sMmenuAmt12Tot').innerHTML = setComma(menuAmt01Tot+"");

		 }
		 //매출액 연계산
		 function calYSale() {

			let menuAmt 	 = document.querySelectorAll("[name=sYMenuQtyYear1]");
			workAmt 		 = menuAmt;
			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let menuIndex=0;
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		menuQty01Tot = menuQty01Tot + getNumber(removeComma(node.innerHTML));
	        		menuQty02Tot = menuQty02Tot + getNumber(removeComma($('[name=sYMenuQtyYear2]')[menuIndex].innerHTML));
	        		menuQty03Tot = menuQty03Tot + getNumber(removeComma($('[name=sYMenuQtyYear3]')[menuIndex].innerHTML));

		    			
	        		menuAmt01Tot = menuAmt01Tot + getNumber(removeComma($('[name=sYMenuAmtYear1]')[menuIndex].innerHTML));
	        		menuAmt02Tot = menuAmt02Tot + getNumber(removeComma($('[name=sYMenuAmtYear2]')[menuIndex].innerHTML));
	        		menuAmt03Tot = menuAmt03Tot + getNumber(removeComma($('[name=sYMenuAmtYear3]')[menuIndex].innerHTML));

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sYmenuQty01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sYmenuQty02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sYmenuQty03Tot').innerHTML = setComma(menuQty03Tot+"");

			 document.getElementById('sYmenuAmt01Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sYmenuAmt02Tot').innerHTML = setComma(menuAmt02Tot+"");
			 document.getElementById('sYmenuAmt03Tot').innerHTML = setComma(menuAmt03Tot+"");

		 }
		 
		 //재료비 계산
		 function calCost() {
 			let menuIndex = document.querySelectorAll("[name=dcostAmt]");
			let monthCostAmt = "";
			let yearCostAmt  = "";
 			calYCost(); 
 			calMCost();

			/*
            let costAmt = 0;
            let costAmtValue = "";
            let dayCostAmt = 0;
            let monthCostAmt = 0;
            let yearCostAmt = 0;
 			if (menuIndex.length > 0) {
 	 			let i = 0;
 	 			menuIndex.forEach(function(node){
 	 	 			if(node.innerHTML== "") {
 	 	 				costAmtValue = "0";
 	 	 	 		}else{
 	 	 				costAmtValue = node.innerHTML;
 	 	 	 	 	 }
	 				costAmt = getNumber(removeComma(costAmtValue)) * getNumber(removeComma($('[name=dmenuQty]')[i].innerHTML));
 	 				dayCostAmt = dayCostAmt + costAmt;

 	 			});
	         }	

			 if (dayCostAmt > 0) {
	             
				 //월간 재료금액금액
		         let day_se_code_m = document.getElementById('day_se_code_m');        
				 workDay = getNumber(day_se_code_m.options[day_se_code_m.selectedIndex].value);
				 monthCostAmt = dayCostAmt * workDay
	             
				 //연간재료비 금액
	             yearCostAmt = monthCostAmt * 12
			 }
			 document.getElementById('dayCostAmt').innerHTML = setComma(dayCostAmt+"");
             document.getElementById('monthCostAmt').innerHTML = setComma(monthCostAmt+"");
             document.getElementById('yearCostAmt').innerHTML = setComma(yearCostAmt+"");

			 */

			 monthCostAmt = document.getElementById('sMCostAmt01Tot').innerHTML;
			 yearCostAmt  = document.getElementById('sYCostAmt01Tot').innerHTML;
			 
             document.getElementById('monthCostAmt').innerHTML = monthCostAmt;
             document.getElementById('yearCostAmt').innerHTML  = yearCostAmt;

 		}
		 
	     //메뉴 No 재계산 			
		 function calNo() {
 			let menuIndex = document.querySelectorAll("[name=dmenuIndex]");
 			if (menuIndex.length > 0) {
 	 			let i = 0;
 	 			menuIndex.forEach(function(node){
		        	i++;
	        		node.innerHTML = i;
	  	      });
	         }	

 			//재료비 테이블 셋팅
 			//setCostTable();
	     }

		 //월매출액 셋팅
		 function setMMenuTable(pCd,pIndex) {

	        let costIndexValue 	 = "";
            let	menuTitleValue 	 = "";
            let	menuPriceValue   = "";
            let	menuQtyValue     = "";

	        let costIndex 	 	 = "";
	        let	menuTitle 	 	 = "";

	                      
    		let MmenuPrice       = 0;
    		let MmenuQty         = 0;
    		let MmenuAmt         = 0;

			let MmenuQtyTot      = 0;      //메뉴별 년판매수량
			let MmenuAmtTot      = 0;	  //메뉴볋 년판매금액	

        	
            if(pCd == "ADD") {
            	costIndexValue 	=  $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 	=  $('[name=dmenuTitle]')[pIndex].innerHTML;
            	menuPriceValue  =  $('[name=dmenuPrice]')[pIndex].innerHTML;
            	menuQtyValue     = $('[name=dmenuQty]')[pIndex].innerHTML;			//일판매기준수량	
            	addMenuMTable(pIndex,costIndexValue,menuTitleValue,menuPriceValue,menuQtyValue,"","Y");

              }else if(pCd == "UPD"){
            	costIndexValue 	 = $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 	 = $('[name=dmenuTitle]')[pIndex].innerHTML;		//타이틀
            	menuPriceValue   = $('[name=dmenuPrice]')[pIndex].innerHTML;		//메뉴가격
            	menuQtyValue     = $('[name=dmenuQty]')[pIndex].innerHTML;			//일판매기준수량	

    			MmenuPrice       = getNumber(removeComma(menuPriceValue));
    			MmenuQty         = getNumber(removeComma(menuQtyValue)) * workDay		//월판매수량	
    			MmenuAmt 		 = MmenuQty * MmenuPrice;							//월판매금액
    			MmenuAmt         = getNumberPoint(MmenuAmt/1000,0);
    			     			
         			 
        		$('[name=sMmenuIndex]')[pIndex].innerHTML = costIndexValue;
    			$('[name=sMmenuTitle]')[pIndex].innerHTML = menuTitleValue;
     			$('[name=sMmenuQty01]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty02]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty03]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty04]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty05]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty06]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty07]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty08]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty09]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty10]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty11]')[pIndex].innerHTML = setComma(MmenuQty+""); 	
     			$('[name=sMmenuQty12]')[pIndex].innerHTML = setComma(MmenuQty+""); 	

     			$('[name=sMmenuAmt01]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt02]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt03]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt04]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt05]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt06]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt07]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt08]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt09]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt10]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt11]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	
     			$('[name=sMmenuAmt12]')[pIndex].innerHTML = setComma(MmenuAmt+""); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
                
				document.getElementById("mmenuTable").deleteRow((pIndex*2)+1);
				document.getElementById("mmenuTable").deleteRow((pIndex*2)+1);
				document.getElementById("mmenuTable").deleteRow((pIndex*2)+1);
            }

			//월 예상 매출액 합계 계산
            calMSale();
            //년 에상 매출액 셋팅 
            setYMenuTable(pCd,pIndex);
            //년 에상 매출액 합계 계산 
            calYSale();
            
		 }


		 //연매출액 셋팅
		 function setYMenuTable(pCd,pIndex) {

	        let costIndexValue 	 = "";
            let	menuTitleValue 	 = "";
            let	menuPriceValue   = "";
            let	YmenuQtyValue    = "";		//메뉴별 년판매수량	
            let	YmenuAmtValue    = "";		//메뉴별 년판매금액	

	        let	costIndex 	 = "";
	        let	menuTitle 	 = "";
	        let	menuQty01 	 = "";
	        let	menuQty02 	 = "";
	        let	menuQty03 	 = "";
	        let	menuPrice01  = "";
	        let	menuPrice02  = "";
	        let	menuPrice03  = "";
	        let	menuAmt01  	 = "";
	        let	menuAmt02  	 = "";
	        let	menuAmt03  	 = "";
			 
        	var trStr="";

        	
            if(pCd == "ADD") {
            	costIndexValue 	=  $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 	=  $('[name=dmenuTitle]')[pIndex].innerHTML;
            	menuPriceValue  =  $('[name=dmenuPrice]')[pIndex].innerHTML;
            	addMenuYTable(pIndex,costIndexValue,menuTitleValue,menuPriceValue,"","Y");

              }else if(pCd == "UPD"){
            	costIndexValue 	 = $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 	 = $('[name=dmenuTitle]')[pIndex].innerHTML;		//타이틀
            	menuPriceValue   = $('[name=dmenuPrice]')[pIndex].innerHTML;		//메뉴가격

               	YmenuQtyValue    = $('[name=sMmenuQtyTot]')[pIndex].innerHTML;		//메뉴별 년판매수량	
               	YmenuAmtValue    = $('[name=sMmenuAmtTot]')[pIndex].innerHTML;		//메뉴별 년판매금액	


    			
         			 
        		$('[name=sMmenuIndex]')[pIndex].innerHTML = costIndexValue;
    			$('[name=sMmenuTitle]')[pIndex].innerHTML = menuTitleValue;

    			$('[name=sYmenuCd]')[pIndex].innerHTML = "1";
    			$('[name=sYMenuQtyRate]')[pIndex].innerHTML = "0";
    			$('[name=sYMenuPriceRate]')[pIndex].innerHTML = "0";

         		$('[name=sYMenuQtyYear1]')[pIndex].innerHTML = setComma(YmenuQtyValue); 	
     			$('[name=sYMenuQtyYear2]')[pIndex].innerHTML = setComma(YmenuQtyValue); 	
     			$('[name=sYMenuQtyYear3]')[pIndex].innerHTML = setComma(YmenuQtyValue); 	

     			$('[name=sYMenuPriceYear1]')[pIndex].innerHTML = setComma(menuPriceValue); 	
     			$('[name=sYMenuPriceYear2]')[pIndex].innerHTML = setComma(menuPriceValue); 	
     			$('[name=sYMenuPriceYear3]')[pIndex].innerHTML = setComma(menuPriceValue); 	

     			$('[name=sYMenuAmtYear1]')[pIndex].innerHTML = setComma(YmenuAmtValue); 	
     			$('[name=sYMenuAmtYear2]')[pIndex].innerHTML = setComma(YmenuAmtValue); 	
     			$('[name=sYMenuAmtYear3]')[pIndex].innerHTML = setComma(YmenuAmtValue); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
                
				document.getElementById("ymenuTable").deleteRow((pIndex*3)+1);
				document.getElementById("ymenuTable").deleteRow((pIndex*3)+1);
				document.getElementById("ymenuTable").deleteRow((pIndex*3)+1);
            }

			//월 예상 매출액 합계 계산
            //calMSale();
		 }

		 //월재료비 셋팅
		 function setMCostTable(pCd,pIndex) {

	        let costIndexValue 	 	 = "";	//메뉴인덱스
            let	menuTitleValue 	 	 = "";  //메뉴타이틀
            let	MCostMenuAmtValue	 = "";  //메뉴월 월판매금액 
            let MCostMenuAmtTotValue = "";  //메뉴별 월판매금액 합 
            let	MCostAmtValueValue	 = "";  //메뉴별 월재료비
            let MCostRateValue       = "";  //메뉴별 월재료비 비율
            
            
	        let costIndex 	 		 = "";
	        let	menuTitle 	 		 = "";
	        let MCostMenuAmt 		 = new Array(); //월판매금액            
	        let MCostAmt 	 		 = new Array(); //월재료비            

			let iMCostMenuAmt       = 0;     //메뉴별 월판매금액
			let iMCostAmt      		= 0;	 //메뉴별 월재료비	
			let iMCostRate     		= 0;	 //메뉴별 월재료비 비율
			
			let iTemp               ="";
 			let i = 0;
			 
        	var trStr="";

        	
            if(pCd == "ADD") {
            	costIndexValue 		=  $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 		=  $('[name=dmenuTitle]')[pIndex].innerHTML;
            	MCostMenuAmtValue	=  removeComma($('[name=sMmenuAmt01]')[pIndex].innerHTML);		//월판매금액
            	MCostMenuAmtTotValue=  $('[name=sMmenuAmtTot]')[pIndex].innerHTML;		//월판매금액 합계

     			costIndex = "<span name='sMCostIndex'>"+costIndexValue+"</span>";
     			menuTitle = "<span name='sMCostTitle'>"+menuTitleValue+"</span>";

     			//월판매금액 셋팅
     			for (i = 0; i < 12; i++){
					if (i < 9 ) iTemp ="0"+(i+1)
					else iTemp = (i+1);
					MCostMenuAmt[i] ="<span name='sMCostMenuAmt"+iTemp+"'>"+setComma(MCostMenuAmtValue)+"</span>"
             	}

     			//월 재료비 셋팅
     			for (i = 0; i < 12; i++ ){
					if (i < 9 ) iTemp ="0"+(i+1)
					else iTemp = (i+1);
					MCostAmt[i] ="<span name='sMCostAmt"+iTemp+"'>"+MCostAmtValueValue+"</span>"
             	}
     			
    			trStr="<tr>";
    			trStr += "<td rowspan='2'>"+costIndex+"</td>";
    			trStr += "<td>"+menuTitle+"</td>";
    			trStr += "<td>판매금액<span name='sMCostMenuAmtTot' style='display:none'>"+MCostMenuAmtTotValue+"</span></td>";
     			for (i = 0; i < 12; i++){
        			trStr += "<td style='text-align:right;'>"+MCostMenuAmt[i]+"</td>"
             	}
        		trStr += "</tr>";
     			$('#mCostTable').append(trStr);

     			trStr="<tr>";
    			trStr += "<td><span name='sMCostRate'></span>%</td>"
    			trStr += "<td>재료비<span name='sMCostAmtTot' style='display:none'></span></td></td>";
     			for (i = 0; i < 12; i++  ){
        			trStr += "<td style='text-align:right;'>"+MCostAmt[i]+"</td>"
             	}
        		trStr += "</tr>";
     			$('#mCostTable').append(trStr);

              }else if(pCd == "UPD"){
            	console.log("pIndex",pIndex);  
              	costIndexValue 		=  $('[name=dcostIndex]')[pIndex].innerHTML;					//index
            	menuTitleValue 		=  $('[name=dmenuTitle]')[pIndex].innerHTML;
            	MCostMenuAmtValue	=  removeComma($('[name=sMmenuAmt01]')[pIndex].innerHTML);		//월판매금액
               	MCostMenuAmtTotValue=  $('[name=sMmenuAmtTot]')[pIndex].innerHTML;					//월판매금액 합계
               	MCostRateValue		=  $('[name=sMCostRate]')[pIndex].innerHTML;					//월재료비 비율

    			iMCostMenuAmt       = 0;     //메뉴별 월판매금액
    			iMCostAmt      		= 0;	 //메뉴별 월재료비	
    			iMCostRate     		= 0;	 //메뉴별 월재료비 비율
            	
    			iMCostMenuAmt  	 = getNumber(removeComma(MCostMenuAmtValue));
                if (MCostRateValue !="") {
	    			iMCostRate  	 = getNumber(MCostRateValue)												//월재료비 비율	
	    			iMCostAmt 	 	 = (iMCostMenuAmt * (iMCostRate/100));							    //월재료비
	             }
                        			 
        		$('[name=sMCostIndex]')[pIndex].innerHTML = costIndexValue;
    			$('[name=sMCostTitle]')[pIndex].innerHTML = menuTitleValue;
    			$('[name=sMCostMenuAmtTot]')[pIndex].innerHTML = MCostMenuAmtTotValue;

    			for (i = 1; i < 13; i++){
					if (i < 10 ) iTemp ="0"+i
					else iTemp = i;
	    			$('[name=sMCostMenuAmt'+iTemp+']')[pIndex].innerHTML = setComma(MCostMenuAmtValue); 	
				}

    			for (i = 1; i < 13; i++){
					if (i < 10 ) iTemp ="0"+i
					else iTemp = i;
					$('[name=sMCostAmt'+iTemp+']')[pIndex].innerHTML = setComma(iMCostAmt+""); 	
				}

    			$('[name=sMCostMenuAmtTot]')[pIndex].innerHTML = setComma((iMCostAmt*12)+""); 	


            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("mCostTable").deleteRow((pIndex*2)+1);
				document.getElementById("mCostTable").deleteRow((pIndex*2)+1);
            }

			//월 예상 매출액 합계 계산
            calMCost();
            //년 에상 재료비 셋팅 
            setYCostTable(pCd,pIndex);
            //년 에상 재료비 합계 계산 
            calYCost();
		 }
		 //재료비 월계산
		 function calMCost() {

			let menuAmt 	 	= document.querySelectorAll("[name=sMCostAmt01]");
			let menuCostAmtTot 	= new Array();
			let costAmtTot 		= new Array();

            let iMCostAmtTot   = 0;
            let iMCostMenuAmtTot=0;
            
			let i=0;
			let j=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				menuCostAmtTot[i] =0;
				costAmtTot[i] =0;
			}

			i=0;			
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){


	    			for(j=0; j< 12; j++){
						if(j < 9)  jTemp ="0"+(j+1);
						else jTemp = j+1;

						menuCostAmtTot[j] = menuCostAmtTot[j] + getNumber(removeComma($('[name=sMCostMenuAmt'+jTemp+']')[i].innerHTML));
					}

	    			iMCostMenuAmtTot = 0;
	    			for(j=1; j< 13; j++){
						if(j < 10)  jTemp ="0"+j;
						else jTemp = j;

						iMCostMenuAmtTot  = iMCostMenuAmtTot+getNumber(removeComma($('[name=sMCostMenuAmt'+jTemp+']')[i].innerHTML))
					}
	    			$('[name=sMCostMenuAmtTot]')[i].innerHTML = iMCostMenuAmtTot+"";

	    			for(j=0; j< 12; j++){
						if(j < 9)  jTemp ="0"+(j+1);
						else jTemp = j+1;

						if($('[name=sMCostAmt'+jTemp+']')[i].innerHTML!='') {			//재료비가 있는경우만 합산
		        			costAmtTot[j] = costAmtTot[j] + getNumber(removeComma($('[name=sMCostAmt'+jTemp+']')[i].innerHTML));
		        		}
					}

	    			iMCostAmtTot = 0;
					for(j=1; j< 13; j++){
						if(j < 10)  jTemp ="0"+j;
						else jTemp = j;
 						
		        		if($('[name=sMCostAmt'+jTemp+']')[i].innerHTML!='') {		//재료비가 있는경우만 합산
		        			iMCostAmtTot  = iMCostAmtTot+getNumber(removeComma($('[name=sMCostAmt'+jTemp+']')[i].innerHTML))
		        		}
					}
					
	    			$('[name=sMCostAmtTot]')[i].innerHTML = iMCostAmtTot+"";


	        		i++;
	  	      });
	         }	

			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
				 document.getElementById('sMCostMenuAmt'+iTemp+'Tot').innerHTML = setComma(menuCostAmtTot[i]+"");
				 document.getElementById('sMCostAmt'+iTemp+'Tot').innerHTML = setComma(costAmtTot[i]+"");
			}

		 }

		 //연재료비 셋팅
		 function setYCostTable(pCd,pIndex) {

	        let costIndexValue 	 		= "";
            let	menuTitleValue 	 		= "";
            let	MCostMenuAmtTotValue    = "";	//메뉴별 년판매금액	
            let	MCostAmtTotValue    	= "";	//메뉴별 년판매재료비	

	        let	costIndex 	 		 	= "";
	        let	menuTitle 	 		 	= "";
	        let	YCostRate			 	= "";	
	        let	YCostMenuAmtYear1 	 	= "";
	        let	YCostMenuAmtYear2 	 	= "";
	        let	YCostMenuAmtYear3 	 	= "";
	        let	YCostAmtYear1  	 		= "";
	        let	YCostAmtYear2  	 		= "";
	        let	YCostAmtYear3  	 		= "";
			 
        	var trStr="";

        	
            if(pCd == "ADD") {
            	costIndexValue 	=  $('[name=dcostIndex]')[pIndex].innerHTML;		//index
            	menuTitleValue 	=  $('[name=dmenuTitle]')[pIndex].innerHTML;
            	menuPriceValue  =  $('[name=dmenuPrice]')[pIndex].innerHTML;

            	MCostMenuAmtTotValue= $('[name=sMCostMenuAmtTot]')[pIndex].innerHTML;	//메뉴별 년판매금액	
            	MCostAmtTotValue	= "";												//메뉴별 년재료비	
            	
     			costIndex = "<span name='sYCostIndex'>"+costIndexValue+"</span>";
     			menuTitle = "<span name='sYCostTitle'>"+menuTitleValue+"</span>";
     			YCostRate = "<span name='sYCostRate'>"+menuTitleValue+"</span>%";
     			YCostMenuAmtYear1 = "<span name='sYCostMenuAmtYear1'>"+setComma(MCostMenuAmtTotValue+"")+"</span>";
     			YCostMenuAmtYear2 = "<span name='sYCostMenuAmtYear2'>"+setComma(MCostMenuAmtTotValue+"")+"</span>";
     			YCostMenuAmtYear3 = "<span name='sYCostMenuAmtYear3'>"+setComma(MCostMenuAmtTotValue+"")+"</span>";

     			YCostAmtYear1 = "<span name='sYCostAmtYear1'>"+setComma(MCostAmtTotValue+"")+"</span>";
     			YCostAmtYear2 = "<span name='sYCostAmtYear2'>"+setComma(MCostAmtTotValue+"")+"</span>";
     			YCostAmtYear3 = "<span name='sYCostAmtYear3'>"+setComma(MCostAmtTotValue+"")+"</span>";
     			
    			trStr="<tr>";
    			trStr += "<td rowspan='2'>"+costIndex+"</td>";
    			trStr += "<td rowspan='2'>"+menuTitle+"</td>";
    			trStr += "<td rowspan='2'>"+YCostRate+"</td>";
    			trStr += "<td>판매금액<span name='sYCostCd' style='display:none'>1</span><span name='sYCostAmtRate' style='display:none'>0</span></td>";
    			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear1+"</td>"
    			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear2+"</td>"
    			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear3+"</td>"
    			trStr += "<td rowspan='2' style='text-align:center;'><input name='btnUpdYCost' class='btn btn-mini' value='수정' type='button' />" 
        		trStr += "</tr>";
     			$('#ycostTable').append(trStr);
    			trStr="<tr>";
    			trStr += "<td>재료비</td>";
    			trStr += "<td style='text-align:right;'>"+YCostAmtYear1+"</td>"
    			trStr += "<td style='text-align:right;'>"+YCostAmtYear2+"</td>"
    			trStr += "<td style='text-align:right;'>"+YCostAmtYear3+"</td>"
        		trStr += "</tr>";
     			$('#ycostTable').append(trStr);

              }else if(pCd == "UPD"){
            	costIndexValue 		= $('[name=dcostIndex]')[pIndex].innerHTML;			//index
            	menuTitleValue 	 	= $('[name=dmenuTitle]')[pIndex].innerHTML;			//타이틀

            	MCostMenuAmtTotValue= $('[name=sMCostMenuAmtTot]')[pIndex].innerHTML;	//메뉴별 년판매금액	
            	MCostAmtTotValue	= $('[name=sMCostAmtTot]')[pIndex].innerHTML;		//메뉴별 년재료비	
    			
         			 
        		$('[name=sYCostIndex]')[pIndex].innerHTML = costIndexValue;
    			$('[name=sYCostTitle]')[pIndex].innerHTML = menuTitleValue;

    			$('[name=sYCostCd]')[pIndex].innerHTML = "1";
    			$('[name=sYCostRate]')[pIndex].innerHTML = $('[name=icostRate]')[pIndex].value;

     			$('[name=sYCostMenuAmtYear1]')[pIndex].innerHTML = setComma(MCostMenuAmtTotValue); 	
     			$('[name=sYCostMenuAmtYear2]')[pIndex].innerHTML = setComma(MCostMenuAmtTotValue); 	
     			$('[name=sYCostMenuAmtYear3]')[pIndex].innerHTML = setComma(MCostMenuAmtTotValue); 	

    			
     			$('[name=sYCostAmtYear1]')[pIndex].innerHTML = setComma(MCostAmtTotValue); 	
     			$('[name=sYCostAmtYear2]')[pIndex].innerHTML = setComma(MCostAmtTotValue); 	
     			$('[name=sYCostAmtYear3]')[pIndex].innerHTML = setComma(MCostAmtTotValue); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("ycostTable").deleteRow((pIndex*2)+1);
				document.getElementById("ycostTable").deleteRow((pIndex*2)+1);
            }

		 }
		 //재료비 연계산
		 function calYCost() {

			let menuAmt 	 = document.querySelectorAll("[name=sYCostAmtYear1]");
			let menu01Tot = 0;
			let menu02Tot = 0;
			let menu03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;


			let menuIndex=0;
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		menu01Tot 	= menu01Tot + getNumber(removeComma($('[name=sYCostMenuAmtYear1]')[menuIndex].innerHTML));
	        		menu02Tot 	= menu02Tot + getNumber(removeComma($('[name=sYCostMenuAmtYear2]')[menuIndex].innerHTML));
	        		menu03Tot 	= menu03Tot + getNumber(removeComma($('[name=sYCostMenuAmtYear3]')[menuIndex].innerHTML));

	        		if(node.innerHTML!="") menuAmt01Tot = menuAmt01Tot + getNumber(removeComma(node.innerHTML));
	        		if($('[name=sYCostAmtYear2]')[menuIndex].innerHTML!="") menuAmt02Tot = menuAmt02Tot + getNumber(removeComma($('[name=sYCostAmtYear2]')[menuIndex].innerHTML));
	        		if($('[name=sYCostAmtYear3]')[menuIndex].innerHTML!="") menuAmt03Tot = menuAmt03Tot + getNumber(removeComma($('[name=sYCostAmtYear3]')[menuIndex].innerHTML));

	        		
		    			

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sYCostMentAmt01Tot').innerHTML = setComma(menu01Tot+"");
			 document.getElementById('sYCostMentAmt02Tot').innerHTML = setComma(menu02Tot+"");
			 document.getElementById('sYCostMentAmt03Tot').innerHTML = setComma(menu03Tot+"");

			 document.getElementById('sYCostAmt01Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sYCostAmt02Tot').innerHTML = setComma(menuAmt02Tot+"");
			 document.getElementById('sYCostAmt03Tot').innerHTML = setComma(menuAmt03Tot+"");

		 }


		 
		 
		 //재료비 테이블 셋팅
		 function setCostTable(pCd,pIndex) {
 
			var inputCostIndex = "";
			var inputCostTitle = "";
			var inputCostPrice = "";
			var inputCostRate  = "";
			var inputCostAmt   = 0;

			var inputCostTitleValue = "";
			var inputCostPriceValue = "";
			var inputCostRateValue 	= "";
			var inputCostAmtValue 	= 0;
        	var trStr="";

        	
            if(pCd == "ADD") {
     			inputCostTitleValue =  $('[name=dmenuTitle]')[pIndex].innerHTML;
     			inputCostPriceValue =  $('[name=dmenuPrice]')[pIndex].innerHTML;

     			inputCostIndex = "<span name=\"dcostIndex\">"+(pIndex+1)+"</span>";
    			inputCostTitle = "<span name=\"dcostTitle\">"+inputCostTitleValue+"</span>";
    			inputCostPrice = "<span name=\"dcostPrice\">"+inputCostPriceValue+"</span>원";
    			inputCostRate  = "<input name=\"icostRate\" style=\"text-align:right;\" class=\"input-block-level span1 numeric\" type=\"text\" placeholder=\"비율\">%";
    			inputCostAmt   = "<span name=\"dcostAmt\">"+inputCostAmtValue+"</span>원";
    			
    			trStr="<tr class=\"costIndex\">";
    			trStr += "<td>"+inputCostIndex+"</td>";
    			trStr += "<td>"+inputCostTitle+"</td>";
    			trStr += "<td style=\"text-align:right;\">"+inputCostPrice+"</td>";
    			trStr += "<td style=\"text-align:right;\">"+inputCostRate+"</td>";
    			trStr += "<td style=\"text-align:right;\">"+inputCostAmt+"</td>";
    			trStr += "</tr>";

    			$('#costTable').append(trStr);
            }else if(pCd == "UPD"){
                
     			inputCostTitleValue =  $('[name=dmenuTitle]')[pIndex].innerHTML;
     			inputCostPriceValue =  $('[name=dmenuPrice]')[pIndex].innerHTML;
      		  
        		$('[name=dcostTitle]')[pIndex].innerHTML = inputCostTitleValue;
    			$('[name=dcostPrice]')[pIndex].innerHTML = inputCostPriceValue;

    			//메뉴가격과 재료비 비율이 입력되어 있다면 재계산 한다.
	    		let iCostAmt   = 0;
    			if(inputCostPriceValue!="" && $('[name=icostRate]')[pIndex].value!="") {
    				let iCostRate  = getNumber($('[name=icostRate]')[pIndex].value);
	    			let iMenuPrice = getNumber(removeComma(inputCostPriceValue))
	    			iCostAmt   = ((iCostRate * iMenuPrice)/100).toFixed(0);
    			}
    			$('[name=dcostAmt]')[pIndex].innerHTML = setComma(iCostAmt+""); 	
    			calCost();

            }else if(pCd == "DEL"){
				document.getElementById("costTable").deleteRow(pIndex);
				calSale();
            }
			

		 }

		 //인건비 추가
		 function addJobPop(pCd) {
		 	jobAddFlag="ADD";
		 	jobCd = pCd;
			document.getElementById('btnAddJob').value="추가";
			document.getElementById('inputJobTitle').value ="";
			document.getElementById('inputJobCnt').value ="";
			document.getElementById('inputJobQty').value 	="";
			$('#myJobModal').modal('toggle');
		 };

		 document.getElementById('btnAddJob').addEventListener('click',function(e){
				console.log("*****btnAddJob*******");
				if (document.getElementById('inputJobTitle').value == "") {
					alert("직위를 입력하세요");
					 document.getElementById("inputJobTitle").focus();
					return;
				}
				if (document.getElementById('inputJobCnt').value == "") {
					alert("기준인원을 입력하세요");
					 document.getElementById("inputJobCnt").focus();
					return;
				}
				if (document.getElementById('inputJobQty').value == "") {
					alert("월 기준급여를 입력하세요");
					 document.getElementById("inputJobQty").focus();
					return;
				}
				
				var inputJobTitle = "";
				var inputJobCnt = "";
				var inputJobQty = "";
				var inputJobAmt = "";
				var inputJobTitleValue 	= document.getElementById('inputJobTitle').value;
				var inputJobCntValue 	= document.getElementById('inputJobCnt').value;
				var inputJobQtyValue 	= document.getElementById('inputJobQty').value;
				var inputJobAmtValue    = 0;

				

				if(jobAddFlag=="ADD") {		//추가버튼 클릭시
					addJobTable(jobCd,inputJobTitleValue,inputJobCntValue,inputJobQtyValue)
					jobIndexNo = document.querySelectorAll("[name=sJob0"+jobCd+"Title]").length;
					setMJobTable("ADD",jobIndexNo-1,"0"+jobCd);
				} else{
					inputJobAmtValue = getNumber(inputJobCntValue) * getNumber(inputJobQtyValue);
					if(jobCd == "1") {		
						$('[name=sJob01Title]')[jobIndexNo].innerHTML 	= inputJobTitleValue;
						$('[name=sJob01Cnt]')[jobIndexNo].innerHTML 	= setComma(inputJobCntValue);
						$('[name=sJob01Qty]')[jobIndexNo].innerHTML 	= setComma(inputJobQtyValue);
						$('[name=sJob01Amt]')[jobIndexNo].innerHTML 	= setComma(inputJobAmtValue+"");
					}else{
						$('[name=sJob02Title]')[jobIndexNo].innerHTML 	= inputJobTitleValue;
						$('[name=sJob02Cnt]')[jobIndexNo].innerHTML 	= setComma(inputJobCntValue);
						$('[name=sJob02Qty]')[jobIndexNo].innerHTML 	= setComma(inputJobQtyValue);
						$('[name=sJob02Amt]')[jobIndexNo].innerHTML 	= setComma(inputJobAmtValue+"");
					}
					setMJobTable("UPD",jobIndexNo,"0"+jobCd);
				}
				calJob();
				$('#myJobModal').modal('toggle');
		 });

		 $(document).on('click', 'input[name=btnUpdJob01]', function() {
			 	jobAddFlag="UPD";
			 	jobCd = "1"
				document.getElementById('btnAddJob').value="수정";
				jobIndexNo = $('input[name=btnUpdJob01]').index(this);
				
				document.getElementById('inputJobTitle').value = $('[name=sJob01Title]')[jobIndexNo].innerHTML;
				document.getElementById('inputJobCnt').value   = removeComma($('[name=sJob01Cnt]')[jobIndexNo].innerHTML);
				document.getElementById('inputJobQty').value   = removeComma($('[name=sJob01Qty]')[jobIndexNo].innerHTML);
				$('#myJobModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelJob01]', function() {
				if(!confirm('인건비항목을 삭제하시겠습니까?')){
					return;
				}
			 	jobCd = "1"
				jobIndexNo = $('input[name=btnDelJob01]').index(this);
				document.getElementById("jobTable01").deleteRow(jobIndexNo+1);
				setMJobTable("DEL",jobIndexNo,"0"+jobCd);
				calJob();
		});

		 $(document).on('click', 'input[name=btnUpdJob02]', function() {
			 	jobAddFlag="UPD";
			 	jobCd = "2"
				document.getElementById('btnAddJob').value="수정";
				jobIndexNo = $('input[name=btnUpdJob02]').index(this);
				
				document.getElementById('inputJobTitle').value = $('[name=sJob02Title]')[jobIndexNo].innerHTML;
				document.getElementById('inputJobCnt').value   = removeComma($('[name=sJob02Cnt]')[jobIndexNo].innerHTML);
				document.getElementById('inputJobQty').value   = removeComma($('[name=sJob02Qty]')[jobIndexNo].innerHTML);
				$('#myJobModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelJob02]', function() {
				if(!confirm('인건비항목을 삭제하시겠습니까?')){
					return;
				}
			 	jobCd = "2"
				jobIndexNo = $('input[name=btnDelJob02]').index(this);
				document.getElementById("jobTable02").deleteRow(jobIndexNo+1);
				setMJobTable("DEL",jobIndexNo,"0"+jobCd);
				calJob();
		});

		 //인건비 계산
		 function calJob() {
			 //일매출금액
            let monthJobAmt = 0;
            let yearJomAmt = 0;

            let job01TotCnt = 0;
            let job01TotQty = 0;
            let job01TotAmt = 0;

 			let job01Amt = document.querySelectorAll("[name=sJob01Amt]");

			let job01Index = 0;
 			if (job01Amt.length > 0) {
 				job01Amt.forEach(function(node){
 
 					job01TotAmt = job01TotAmt + getNumber(removeComma(node.innerHTML));

 					job01TotCnt   = job01TotCnt+getNumber(removeComma($('[name=sJob01Cnt]')[job01Index].innerHTML));
 					job01TotQty   = job01TotQty+getNumber(removeComma($('[name=sJob01Qty]')[job01Index].innerHTML));
 	 					
 					job01Index++;
	  	      });
	         }	

 			
	        document.getElementById('sJob01TotCnt').innerHTML = setComma(job01TotCnt+"");
	        document.getElementById('sJob01TotQty').innerHTML = setComma(job01TotQty+"");
	        document.getElementById('sJob01TotAmt').innerHTML = setComma(job01TotAmt+"");
            
            let job02TotCnt = 0;
            let job02TotQty = 0;
            let job02TotAmt = 0;

 			let job02Amt = document.querySelectorAll("[name=sJob02Amt]");

			let job02Index = 0;
 			if (job02Amt.length > 0) {
 				job02Amt.forEach(function(node){
 
 					job02TotAmt = job02TotAmt + getNumber(removeComma(node.innerHTML));

 					job02TotCnt   = job02TotCnt+getNumber(removeComma($('[name=sJob02Cnt]')[job02Index].innerHTML));
 					job02TotQty   = job02TotQty+getNumber(removeComma($('[name=sJob02Qty]')[job02Index].innerHTML));
 	 					
 					job02Index++;
	  	      });
	         }	

 			
	        document.getElementById('sJob02TotCnt').innerHTML = setComma(job02TotCnt+"");
	        document.getElementById('sJob02TotQty').innerHTML = setComma(job02TotQty+"");
	        document.getElementById('sJob02TotAmt').innerHTML = setComma(job02TotAmt+"");

	        monthJobAmt = job01TotAmt + job02TotAmt;
	        
 			 if (monthJobAmt > 0) {
				 //연간 매출금액
	             yearJomAmt = monthJobAmt * 12
			 }


	         document.getElementById('monthJobAmt').innerHTML = setComma(monthJobAmt+"");
	         document.getElementById('yearJobAmt').innerHTML  = setComma(yearJomAmt+"");
		 }

		 //인건비 월년 보이기
		 $('#btnMJobS').on('click', function(e) {
				$('#mJob').show();
				$('#yJob').hide();
		 });

		 $('#btnYJobS').on('click', function(e) {
				$('#mJob').hide();
				$('#yJob').show();
		 });
			 
		 $('#btnMJobH').on('click', function(e) {
				$('#mJob').hide();
		 });

		 $('#btnYJobH').on('click', function(e) {
				$('#yJob').hide();
		 });

		 //인건비 셋팅
		 function setMJobTable(pCd,pIndex,pJobCd) {

            let	sMJobTitleValue   	= "";  
            let	sMJobCdValue	 	= "";  
            let sMJobCdTitleValue   = "";
            let sMJobCntValue 		= "";   
            let sJobAmtValue        = "";
            
	        let	sMJobTitle 	 	 	= "";
	        let sMJobCd            	= "";
		    let sMJobCnt           	= "";
		    let sJobAmt             = "";

			let JobAmt 	 		 	= new Array(); //월재료비            
			let iJobAmt       		= 0;     
			let iTemp               ="";
 			let i 					= 0;
			let iMJobTot      	    = 0;
        	var trStr="";
			let iIndex 				= pIndex;

        	
            if(pCd == "ADD") {
            	addJobMTable(pIndex,pJobCd,"","Y");
              }else if(pCd == "UPD"){
			    console.log("pIndex",pIndex);
                  
            	sMJobTitleValue 	=  $('[name=sJob'+pJobCd+'Title]')[pIndex].innerHTML;
            	if(pJobCd=="01")  sMJobCdValue =  "제조";	
            	else	 sMJobCdValue =  "판매";	
                sMJobCntValue     	=  $('[name=sJob'+pJobCd+'Cnt]')[pIndex].innerHTML;		
            	sJobAmtValue     	=  removeComma($('[name=sJob'+pJobCd+'Amt]')[pIndex].innerHTML);		

            	if($('[name=sJob'+pJobCd+'Amt]')[pIndex].innerHTML!=""){
                	iJobAmt         = getNumber(sJobAmtValue) /1000;
            		iMJobTot = (getNumber(iJobAmt) * 12);
                }
               	if(pJobCd=="02") {
               		iIndex = pIndex + $('#jobTable01 >tbody tr').length;       	
               	}
            	     
      			 
    			$('[name=sMJobCd]')[iIndex].innerHTML 	 = sMJobCdValue;
        		$('[name=sMJobTitle]')[iIndex].innerHTML = sMJobTitleValue;
       			$('[name=sMJobCnt]')[iIndex].innerHTML 	 = sMJobCntValue;

    			for (i = 1; i < 13; i++){
					if (i < 10 ) iTemp ="0"+i
					else iTemp = i;
	    			$('[name=sMJobAmt'+iTemp+']')[iIndex].innerHTML = setComma(iJobAmt+""); 	
				}

    			$('[name=sMJobTot]')[iIndex].innerHTML = iMJobTot+"";

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("tbMjob"+pJobCd).deleteRow(pIndex);
            }

			//월 인건비 합계 계산
            calMJob();
            //년 인건비 셋팅 
            setYJobTable(pCd,pIndex,pJobCd);
            //년 인건비 계산 
            calYJob();
		 }

		 //연인건비 셋팅
		 function setYJobTable(pCd,pIndex,pJobCd) {

            let	sYJobCdTitleValue 	= "";
            let	sYJobCdValue    	= "";	
            let	sYJobTitleValue    	= "";		
            let sYJobCntValue 		= "";
            let sYJobQtyValue 		= "";
            let sMJobTotValue       = "";
            let sYJobAmtRateValue   = "";
            
            
	        let	sYJobCdTitle 	 	= "";
	        let	sYJobCd				= "";	
	        let	sYJobTitle 	 		= "";
	        let	sYJobCnt 	 		= "";
	        let	sYJobQty 	 		= "";

	    	let sYJobAmtYear1		= "";
	        let sYJobAmtYear2		= "";
	        let sYJobAmtYear3		= "";

	        let isYJobAmtRateValue  = 0;
	        let isYJobAmtYear1		= 0;
	        let isYJobAmtYear2		= 0;
	        let isYJobAmtYear3		= 0;
	        let isYJobQty           = 0;
        	var trStr="";
			let iIndex = pIndex;

        	
            if(pCd == "ADD") {
            	
            	addJobYTable(pIndex,pJobCd,"","Y");
 
              }else if(pCd == "UPD"){

              	if(pJobCd=="01") {
            		sYJobCdTitleValue 	=  "제조";
                	sYJobCdValue		=  "1";		
            	}else {
            		sYJobCdTitleValue 	=  "판매";
                	sYJobCdValue		=  "2";		
            	}

              	
              	sYJobTitleValue     =  $('[name=sJob'+pJobCd+'Title]')[pIndex].innerHTML;
            	sYJobCntValue		=  $('[name=sJob'+pJobCd+'Cnt]')[pIndex].innerHTML;		
            	sYJobQtyValue		=  removeComma($('[name=sJob'+pJobCd+'Qty]')[pIndex].innerHTML);	
				
               	if(sYJobQtyValue!="")  isYJobQty = getNumber(sYJobQtyValue)/1000;
            	
            	 if(pJobCd=="02") {		//판매이면 제조갯수 만큼 플러서 해줘야 함
               		iIndex = pIndex + $('#jobTable01 >tbody tr').length;       	
                  }          		

            	sMJobTotValue       =  $('[name=sMJobTot]')[iIndex].innerHTML;	
            	sYJobAmtRateValue   =  $('[name=sYJobAmtRate]')[iIndex].innerHTML;	



             	         			 
      			$('[name=sYJobTitle]')[iIndex].innerHTML= sYJobTitleValue;
      			$('[name=sYJobCd]')[iIndex].innerHTML 	 	 = sYJobCdValue;
      		    $('[name=sYJobTitle]')[iIndex].innerHTML   	 = sYJobTitleValue;
       			$('[name=sYJobCnt]')[iIndex].innerHTML 	 	 = sYJobCntValue;
       			$('[name=sYJobQty]')[iIndex].innerHTML 		 = setComma(isYJobQty+"");

				if(sMJobTotValue!="" || sMJobTotValue!="0"){
					isYJobAmtYear1  = getNumber(sMJobTotValue);
					isYJobAmtYear2  = isYJobAmtYear1;
					isYJobAmtYear3  = isYJobAmtYear1;
				}
				
				if(sYJobAmtRateValue!="0" && isYJobAmtYear1!=0) {
					if(sMJobTotValue !=""){
						isYJobAmtYear2  = isYJobAmtYear1 * (1+(sYJobAmtRateValue/100));
						isYJobAmtYear3  = isYJobAmtYear2 * (1+(sYJobAmtRateValue/100)) ;
					}
				}
     			
     			$('[name=sYJobAmtYear1]')[iIndex].innerHTML = setComma(isYJobAmtYear1+""); 	
     			$('[name=sYJobAmtYear2]')[iIndex].innerHTML = setComma(isYJobAmtYear2+""); 	
     			$('[name=sYJobAmtYear3]')[iIndex].innerHTML = setComma(isYJobAmtYear3+""); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("tbMjob"+pJobCd).deleteRow(pIndex);
	        }

		 }

		 //인건비년수정
		 $(document).on('click', 'input[name=btnUpdYJob]', function() {
			    yJobIndexNo = $('input[name=btnUpdYJob]').index(this);
			 	document.getElementById('YJobTitle').innerHTML = $('[name=sYJobTitle]')[yJobIndexNo].innerHTML;

				if ($('[name=sYJobAmtYear1]')[yJobIndexNo].innerHTML==""){
					 alert( $('[name=sYJobTitle]')[yJobIndexNo].innerHTML+"의 월기준급여를 먼저 입력하세요");
					 return;
				}

			 	
			 	let sYJobAmtCd = $('[name=sYJobAmtCd]')[yJobIndexNo].innerHTML;
			 	
			 	console.log("sYJobAmtCd:",sYJobAmtCd);
			 	initPopYJob(sYJobAmtCd);

				
				$('#myYJobModal').modal('toggle');
		});
		//연판매관리비 팝업 셋팅	
		function initPopYJob(pCd) {
	   		if("1"==pCd){
		   		$('#modalYJobRate').show();
		   		$("#inputYJobYear1").attr("readonly", true);
		   		$("#inputYJobYear2").attr("readonly", true);
		   		$("#inputYJobYear3").attr("readonly", true);
		   	}else if("2"==pCd){
		   		$('#modalYJobRate').hide();
		   		$("#inputYJobYear1").attr("readonly", true);
		   		$("#inputYJobYear2").attr("readonly", false);
		   		$("#inputYJobYear3").attr("readonly", false);
			}
		 	document.getElementById('inputYJobAmtCd').value   = pCd;
		 	document.getElementById('inputYJobRate').value    = removeComma($('[name=sYJobAmtRate]')[yJobIndexNo].innerHTML);
		 	document.getElementById('inputYJobYear1').value   = removeComma($('[name=sYJobAmtYear1]')[yJobIndexNo].innerHTML);
			document.getElementById('inputYJobYear2').value   = removeComma($('[name=sYJobAmtYear2]')[yJobIndexNo].innerHTML);
			document.getElementById('inputYJobYear3').value   = removeComma($('[name=sYJobAmtYear3]')[yJobIndexNo].innerHTML);

		}
		//연도별 판매관리비팝업 입력방법선택	
		$('#inputYJobCd').on('change', function(e) {
			initPopYJob(this.value);
		});

		//연인건비 수정
		document.getElementById('btnAddYJob').addEventListener('click',function(e){
			console.log("*****btnAddYJob*******");

			
	        let inputYJobAmtCd 			= document.getElementById('inputYJobAmtCd');        
			var inputYJobAmtCdValue 		= inputYmenuCd.options[inputYJobAmtCd.selectedIndex].value;


			
			if (inputYJobAmtCdValue == "1") {

				if(document.getElementById('inputYJobRate').value ==""){
					 alert("인건비 증감율을 입력하세요");
					 document.getElementById("inputYJobRate").focus();
					return;
				}
				
			} else{
				if(document.getElementById('inputYJobYear2').value ==""){
					 alert("2차년도 인건비를  입력하세요");
					 document.getElementById("inputYJobYear2").focus();
					return;
				}
				if(document.getElementById('inputYJobYear3').value ==""){
					 alert("3차년도 인건비를  입력하세요");
					 document.getElementById("inputYJobYear3").focus();
					return;
				}

			}


			var inputYJobRateValue 		    = document.getElementById('inputYJobRate').value;
			var inputYJobYear1Value 		= removeComma(document.getElementById('inputYJobYear1').value);
			var inputYJobYear2Value 		= removeComma(document.getElementById('inputYJobYear2').value);
			var inputYJobYear3Value 		= removeComma(document.getElementById('inputYJobYear3').value);

			$('[name=sYJobAmtCd]')[yJobIndexNo].innerHTML 		= inputYJobAmtCdValue;
			$('[name=sYJobAmtRate]')[yJobIndexNo].innerHTML 	= inputYJobRateValue;

			//인건비
			//$('[name=sYMenuAmtYear1]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear1+"");
			$('[name=sYJobAmtYear2]')[yJobIndexNo].innerHTML	= setComma(inputYJobYear2Value);
			$('[name=sYJobAmtYear3]')[yJobIndexNo].innerHTML	= setComma(inputYJobYear3Value);

			//년인건비계산
			calYJob();
			$('#myYJobModal').modal('toggle');
	     });

		 //연도별 판매관리비 증가율 계산 
		 $('#inputYJobRate').on('change', function(e) {
			if (this.value != ""){

				var inputYJobRateValue 	= document.getElementById('inputYJobRate').value;
				var inputYJobYear1Value 	= document.getElementById('inputYJobYear1').value;
				var inputYJobYear2Value 	= document.getElementById('inputYJobYear2').value;
				var inputYJobYear3Value 	= document.getElementById('inputYJobYear3').value;

				let iinputYJobYear1 = getNumber(inputYJobYear1Value);
				let iinputYJobYear2 = 0;
				let iinputYJobYear3 = 0;

				let iinputYJobRateValue = getNumber(inputYJobRateValue);
				
				iinputYJobYear2 = iinputYJobYear1 * (1+(iinputYJobRateValue/100)) ;
				iinputYJobYear3 = iinputYJobYear2 * (1+(iinputYJobRateValue/100)) ;

				iinputYJobYear2=Math.floor(iinputYJobYear2);
				iinputYJobYear3=Math.floor(iinputYJobYear3);
				
				document.getElementById('inputYJobYear2').value =iinputYJobYear2+"";
				document.getElementById('inputYJobYear3').value =iinputYJobYear3+"";

			}
		 });

		 //인건비 월계산
		 function calMJob() {

			 var trLength = jQuery('#tbMjob01 >tr').length;


			 
			let sMJobAmt 	 	= document.querySelectorAll("[name=sMJobAmt01]");
			let costAmtTot 		= new Array();
			let costAmtSub1Tot 	= new Array();
			let costAmtSub2Tot 	= new Array();

            let iJobAmtTot   = 0;
            let iMJobCnt1    = 0;
            let iMJobCnt2    = 0;
            
			let i=0;
			let j=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				costAmtTot[i] =0;
				costAmtSub1Tot[i] =0;
				costAmtSub2Tot[i] =0;
			}

			//제조
			//인원계산
			iMJobCnt1 = $("#tbMjob01 >tr span[name=sMJobCnt]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);


			//월계산
   			for(i=0; i< 12; i++){
				if(i < 9)  iTemp ="0"+(i+1);
				else iTemp = i+1;
				costAmtSub1Tot[i]=$('#tbMjob01 >tr span[name=sMJobAmt'+iTemp+']').map(function() {
					let iReturn =0;
					if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
					return iReturn 
				}).get().reduce((pre, val) => pre + val);

   			}
			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
				 document.getElementById('sMJobAmt'+iTemp+'Sub1Tot').innerHTML = setComma(costAmtSub1Tot[i]+"");
			}

			document.getElementById('sMJobCnt01Sub1Tot').innerHTML = setComma(iMJobCnt1+"");

			//판매
			//인원계산
			iMJobCnt2 = 0;
			iMJobCnt2 = $("#tbMjob02 >tr span[name=sMJobCnt]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);


			//월계산
			for(i=0; i< 12; i++){
				costAmtSub2Tot[i] =0;
			}
			for(i=0; i< 12; i++){
				if(i < 9)  iTemp ="0"+(i+1);
				else iTemp = i+1;
				costAmtSub2Tot[i]=$('#tbMjob02 >tr span[name=sMJobAmt'+iTemp+']').map(function() {
					let iReturn =0;
					if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
					return iReturn 
				}).get().reduce((pre, val) => pre + val);

   			}
			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);

        		document.getElementById('sMJobAmt'+iTemp+'Sub2Tot').innerHTML = setComma(costAmtSub2Tot[i]+"");
			}

			document.getElementById('sMJobCnt01Sub2Tot').innerHTML = setComma(iMJobCnt2+"");

			
			
			//월합계산(제조, 판매)
			i = 0;
 			if (sMJobAmt.length > 0) {
 				sMJobAmt.forEach(function(node){
	    			iJobAmtTot = 0;
	    			for(j=1; j< 13; j++){
						if(j < 10)  jTemp ="0"+j;
						else jTemp = j;
		    
						if($('[name=sMJobAmt'+jTemp+']')[i].innerHTML!="")
						iJobAmtTot  = iJobAmtTot+getNumber(removeComma($('[name=sMJobAmt'+jTemp+']')[i].innerHTML))
					}
	    			$('[name=sMJobTot]')[i].innerHTML = iJobAmtTot+"";
	        		i++;
	  	      });
	         }	


 			//소계합
			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
        		document.getElementById('sMJobAmt'+iTemp+'Tot').innerHTML = setComma((costAmtSub1Tot[i]+costAmtSub2Tot[i])+"");
			}
    		document.getElementById('sMJobCnt012Tot').innerHTML = setComma((iMJobCnt1+iMJobCnt2)+"");
			
			
		 }

		 //인건비  연계산
		 function calYJob() {
						
			let menuAmt01Sub1Tot = 0;
			let menuAmt02Sub1Tot = 0;
			let menuAmt03Sub1Tot = 0;

			let menuAmt01Sub2Tot = 0;
			let menuAmt02Sub2Tot = 0;
			let menuAmt03Sub2Tot = 0;

			let iYJobCnt1        = 0; 
			let iYJobCnt2        = 0; 
			//제조
			//1차년도
			iYJobCnt1 = $("#tbYjob01 >tr span[name=sYJobCnt]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt01Sub1Tot = $("#tbYjob01 >tr span[name=sYJobAmtYear1]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt02Sub1Tot = $("#tbYjob01 >tr span[name=sYJobAmtYear2]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt03Sub1Tot = $("#tbYjob01 >tr span[name=sYJobAmtYear3]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			 document.getElementById('sYJobCntSub1Tot').innerHTML = setComma(iYJobCnt1+"");
			 document.getElementById('sYJobAmt01Sub1Tot').innerHTML = setComma(menuAmt01Sub1Tot+"");
			 document.getElementById('sYJobAmt02Sub1Tot').innerHTML = setComma(menuAmt02Sub1Tot+"");
			 document.getElementById('sYJobAmt03Sub1Tot').innerHTML = setComma(menuAmt03Sub1Tot+"");


			//판매
			//1차년도
			iYJobCnt2 = $("#tbYjob02 >tr span[name=sYJobCnt]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt01Sub2Tot = $("#tbYjob02 >tr span[name=sYJobAmtYear1]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt02Sub2Tot = $("#tbYjob02 >tr span[name=sYJobAmtYear2]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			menuAmt03Sub2Tot = $("#tbYjob02 >tr span[name=sYJobAmtYear3]").map(function() { 
				let iReturn =0;
				if ($(this).text()!="") iReturn = getNumber(removeComma($(this).text()));
				return iReturn 
			}).get().reduce((pre, val) => pre + val);

			 document.getElementById('sYJobCntSub2Tot').innerHTML = setComma(iYJobCnt2+"");
			 document.getElementById('sYJobAmt01Sub2Tot').innerHTML = setComma(menuAmt01Sub2Tot+"");
			 document.getElementById('sYJobAmt02Sub2Tot').innerHTML = setComma(menuAmt02Sub2Tot+"");
			 document.getElementById('sYJobAmt03Sub2Tot').innerHTML = setComma(menuAmt03Sub2Tot+"");
			 

			 document.getElementById('sYJobCntTot').innerHTML = setComma((iYJobCnt1+iYJobCnt2)+"");
			 document.getElementById('sYJobAmt01Tot').innerHTML = setComma((menuAmt01Sub1Tot+menuAmt01Sub2Tot)+"");
			 document.getElementById('sYJobAmt02Tot').innerHTML = setComma((menuAmt02Sub1Tot+menuAmt02Sub2Tot)+"");
			 document.getElementById('sYJobAmt03Tot').innerHTML = setComma((menuAmt03Sub1Tot+menuAmt03Sub2Tot)+"");
			 
		 }
		 
		 
		 //투자비용 추가
		 function addInvestPop(pCd,pInvesCodeM) {
		 	investAddFlag="ADD";
		 	investCd = pCd;
			document.getElementById('btnAddInvest').value="추가";

			document.getElementById('inputInvestBizcdCodeM').value = pInvesCodeM;

			if (investCd=="1") {
				$('#inputInvestTitle').html(invest1ComboStr);
				$('#dInvestYear').show();
			}else if (investCd=="2") {
				$('#inputInvestTitle').html(invest2ComboStr);
				$('#dInvestYear').show();
			}else if (investCd=="3") {
				$('#inputInvestTitle').html(invest3ComboStr);
				$('#dInvestYear').hide();
			}else if (investCd=="4") {	
				$('#inputInvestTitle').html(invest4ComboStr);
				$('#dInvestYear').hide();
			}

			
			document.getElementById('inputInvestTitle').value ="";
			
			document.getElementById('inputInvestPirce').value ="";
			document.getElementById('inputInvestYear').value ="";
			$('#myInvestModal').modal('toggle');
		 };

		 document.getElementById('btnAddInvest').addEventListener('click',function(e){
				console.log("*****btnAddInvest*******");
				if (document.getElementById('inputInvestBizcdCodeM').value == "") {
					alert("제조/판매를 선택하세요");
					 document.getElementById("inputInvestBizcdCodeM").focus();
					return;
				}
				if (document.getElementById('inputInvestTitle').value == "") {
					alert("투자내역을 선택하세요");
					 document.getElementById("inputInvestTitle").focus();
					return;
				}
				if (document.getElementById('inputInvestPirce').value == "") {
					alert("투자비용을 입력하세요");
					 document.getElementById("inputInvestPirce").focus();
					return;
				}

				if(investCd=="1" || investCd=="2") {
					if (document.getElementById('inputInvestYear').value == "") {
						alert("삼각년수을 입력하세요");
						 document.getElementById("inputInvestYear").focus();
						return;
					}
				}
				
				var inputInvestBizcdCodeM 	= "";
				var inputInvestTitle 		= "";
				var inputInvestPirce 		= "";
				var inputInvestYear 		= "";
				var inputInvestAmt 			= "";

				var inputInvestBizcdCodeMValue  = document.getElementById('inputInvestBizcdCodeM').value;
				var inputInvestTitleValue  		= $("#inputInvestTitle option:selected").text();
				var inputInvestTitleCdValue  	= $("#inputInvestTitle option:selected").val();
				var inputInvestPirceValue    	= document.getElementById('inputInvestPirce').value;
				var inputInvestYearValue   		= document.getElementById('inputInvestYear').value;
				var inputInvestAmtValue    		= 0;

				if(investCd=="1" || investCd=="2") {
					if(getNumber(inputInvestYearValue) == 0){
						inputInvestAmtValue = 0;
					}else{
						inputInvestAmtValue = (getNumber(inputInvestPirceValue) / getNumber(inputInvestYearValue)) / 12;
						inputInvestAmtValue = inputInvestAmtValue.toFixed(0);
					}
				}
				
				let investNo = "01";
				if(investCd == "1"){
					if(inputInvestBizcdCodeMValue =="1") investNo = "01"
					else investNo = "02"	
				}else if(investCd == "2"){
					if(inputInvestBizcdCodeMValue =="1") investNo = "03"
					else investNo = "04"	
				}else if(investCd == "3"){
					if(inputInvestBizcdCodeMValue =="1") investNo = "05"
					else investNo = "06"	
				} else{
					if(inputInvestBizcdCodeMValue =="1") investNo = "07"
					else investNo = "08"	
				}

				if(investAddFlag=="ADD") {		//추가버튼 클릭시

					addInvestTable(investCd,inputInvestBizcdCodeMValue,inputInvestTitleValue,inputInvestTitleCdValue,inputInvestPirceValue,inputInvestYearValue);
					
				} else{
					$('[name=dInvest'+investNo+'Title]')[investIndexNo].innerHTML = inputInvestTitleValue;
					$('[name=dInvest'+investNo+'TitleCd]')[investIndexNo].innerHTML = inputInvestTitleCdValue;
					$('[name=dInvest'+investNo+'Price]')[investIndexNo].innerHTML = setComma(inputInvestPirceValue);
					if(investCd == "1" || investCd == "2") {
						$('[name=dInvest'+investNo+'Year]')[investIndexNo].innerHTML 	= inputInvestYearValue;
						$('[name=dInvest'+investNo+'Amt]')[investIndexNo].innerHTML 	= setComma(inputInvestAmtValue+"");
					}
				}
				calInvest();
				$('#myInvestModal').modal('toggle');
		 });

		 $(document).on('click', 'input[name=btnUpdInvest01]', function() {
			 	investAddFlag="UPD";
			 	investCd = "1";
			 	bizCd    = "1";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest1ComboStr);
				$('#dInvestYear').show();

				
			 	investIndexNo = $('input[name=btnUpdInvest01]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest01TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest01Price]')[investIndexNo].innerHTML);
				document.getElementById('inputInvestYear').value   	   = $('[name=dInvest01Year]')[investIndexNo].innerHTML;
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest01]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest01]').index(this);
				document.getElementById("investTable01").deleteRow(investIndexNo+1);
				calInvest();
				
		});

		$(document).on('click', 'input[name=btnUpdInvest02]', function() {
			 	investAddFlag="UPD";
			 	investCd = "1";
			 	bizCd    = "2";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest1ComboStr);
				$('#dInvestYear').show();
			 	investIndexNo = $('input[name=btnUpdInvest02]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest02TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest02Price]')[investIndexNo].innerHTML);
				document.getElementById('inputInvestYear').value   	   = $('[name=dInvest02Year]')[investIndexNo].innerHTML;
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest02]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest02]').index(this);
				document.getElementById("investTable02").deleteRow(investIndexNo+1);
				calInvest();
				
		});

		$(document).on('click', 'input[name=btnUpdInvest03]', function() {
			 	investAddFlag="UPD";
			 	investCd = "2";
			 	bizCd    = "1";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest2ComboStr);
				$('#dInvestYear').show();
			 	investIndexNo = $('input[name=btnUpdInvest03]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest03TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest03Price]')[investIndexNo].innerHTML);
				document.getElementById('inputInvestYear').value   	   = $('[name=dInvest03Year]')[investIndexNo].innerHTML;
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest03]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "2"
				investIndexNo = $('input[name=btnDelInvest03]').index(this);
				document.getElementById("investTable03").deleteRow(investIndexNo+1);
				calInvest();
				
		});

			$(document).on('click', 'input[name=btnUpdInvest04]', function() {
			 	investAddFlag="UPD";
			 	investCd = "2";
			 	bizCd    = "2";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest2ComboStr);
				$('#dInvestYear').show();
			 	investIndexNo = $('input[name=btnUpdInvest04]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest04TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest04Price]')[investIndexNo].innerHTML);
				document.getElementById('inputInvestYear').value   	   = $('[name=dInvest04Year]')[investIndexNo].innerHTML;
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest04]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest04]').index(this);
				document.getElementById("investTable04").deleteRow(investIndexNo+1);
				calInvest();
				
		});

			$(document).on('click', 'input[name=btnUpdInvest05]', function() {
			 	investAddFlag="UPD";
			 	investCd = "3";
			 	bizCd    = "1";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest3ComboStr);
				$('#dInvestYear').hide();
			 	investIndexNo = $('input[name=btnUpdInvest05]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest05TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest05Price]')[investIndexNo].innerHTML);
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest05]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest05]').index(this);
				document.getElementById("investTable05").deleteRow(investIndexNo+1);
				calInvest();
				
		});
		 
		$(document).on('click', 'input[name=btnUpdInvest06]', function() {
			 	investAddFlag="UPD";
			 	investCd = "3";
			 	bizCd    = "2";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest3ComboStr);
				$('#dInvestYear').hide();
			 	investIndexNo = $('input[name=btnUpdInvest06]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest06TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest06Price]')[investIndexNo].innerHTML);
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest06]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest06]').index(this);
				document.getElementById("investTable06").deleteRow(investIndexNo+1);
				calInvest();
				
		});

		$(document).on('click', 'input[name=btnUpdInvest07]', function() {
			 	investAddFlag="UPD";
			 	investCd = "4";
			 	bizCd    = "1";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest4ComboStr);
				$('#dInvestYear').hide();
			 	investIndexNo = $('input[name=btnUpdInvest07]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest07TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest07Price]')[investIndexNo].innerHTML);
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest07]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest07]').index(this);
				document.getElementById("investTable07").deleteRow(investIndexNo+1);
				calInvest();
				
		});
		 
		$(document).on('click', 'input[name=btnUpdInvest08]', function() {
			 	investAddFlag="UPD";
			 	investCd = "4";
			 	bizCd    = "2";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest4ComboStr);
				$('#dInvestYear').hide();
			 	investIndexNo = $('input[name=btnUpdInvest08]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = $('[name=dInvest08TitleCd]')[investIndexNo].innerHTML;
				document.getElementById('inputInvestPirce').value      = removeComma($('[name=dInvest08Price]')[investIndexNo].innerHTML);
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest08]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest08]').index(this);
				document.getElementById("investTable08").deleteRow(investIndexNo+1);
				calInvest();
		});

		 //투자비계산
		 function calInvest() {
			 //일매출금액
            let monthJobAmt = 0;
            let yearJomAmt = 0;

            let invest01TotPrice = 0;
            let invest01TotAmt   = 0;
            let invest02TotPrice = 0;
            let invest02TotAmt   = 0;
            let invest03TotPrice = 0;
            let invest03TotAmt   = 0;
            let invest04TotPrice = 0;
            let invest04TotAmt   = 0;
            let invest05TotPrice = 0;
            let invest06TotPrice = 0;
            let invest07TotPrice = 0;
            let invest08TotPrice = 0;

 			let invest01Price = document.querySelectorAll("[name=dInvest01Price]");
 			let invest02Price = document.querySelectorAll("[name=dInvest02Price]");
 			let invest03Price = document.querySelectorAll("[name=dInvest03Price]");
 			let invest04Price = document.querySelectorAll("[name=dInvest04Price]");
 			let invest05Price = document.querySelectorAll("[name=dInvest05Price]");
 			let invest06Price = document.querySelectorAll("[name=dInvest06Price]");
 			let invest07Price = document.querySelectorAll("[name=dInvest07Price]");
 			let invest08Price = document.querySelectorAll("[name=dInvest08Price]");
 			let investIndex = 0;

			//1.유형자산 제조
 			investIndex = 0;
 			if (invest01Price.length > 0) {
 				invest01Price.forEach(function(node){
 					invest01TotPrice = invest01TotPrice + getNumber(removeComma(node.innerHTML));
 					invest01TotAmt   = invest01TotAmt+getNumber(removeComma($('[name=dInvest01Amt]')[investIndex].innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest01TotPrice').innerHTML = setComma(invest01TotPrice+"");
	        document.getElementById('dInvest01TotAmt').innerHTML = setComma(invest01TotAmt+"");

			//2.유형자산 판매
 			investIndex = 0;
 			if (invest02Price.length > 0) {
 				invest02Price.forEach(function(node){
 					invest02TotPrice = invest02TotPrice + getNumber(removeComma(node.innerHTML));
 					invest02TotAmt   = invest02TotAmt+getNumber(removeComma($('[name=dInvest02Amt]')[investIndex].innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest02TotPrice').innerHTML = setComma(invest02TotPrice+"");
	        document.getElementById('dInvest02TotAmt').innerHTML = setComma(invest02TotAmt+"");
	        
			//3.무형자산 제조
 			investIndex = 0;
 			if (invest03Price.length > 0) {
 				invest03Price.forEach(function(node){
 					invest03TotPrice = invest03TotPrice + getNumber(removeComma(node.innerHTML));
 					invest03TotAmt   = invest03TotAmt+getNumber(removeComma($('[name=dInvest03Amt]')[investIndex].innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest03TotPrice').innerHTML = setComma(invest03TotPrice+"");
	        document.getElementById('dInvest03TotAmt').innerHTML = setComma(invest03TotAmt+"");

			//4.무형자산 판매
 			investIndex = 0;
 			if (invest04Price.length > 0) {
 				invest04Price.forEach(function(node){
 					invest04TotPrice = invest04TotPrice + getNumber(removeComma(node.innerHTML));
 					invest04TotAmt   = invest04TotAmt+getNumber(removeComma($('[name=dInvest04Amt]')[investIndex].innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest04TotPrice').innerHTML = setComma(invest04TotPrice+"");
	        document.getElementById('dInvest04TotAmt').innerHTML = setComma(invest04TotAmt+"");
	        
			//5.기타유동자산 제조
 			investIndex = 0;
 			if (invest05Price.length > 0) {
 				invest05Price.forEach(function(node){
 					invest05TotPrice = invest05TotPrice + getNumber(removeComma(node.innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest05TotPrice').innerHTML = setComma(invest05TotPrice+"");

			//6.기타유동자산 판매
 			investIndex = 0;
 			if (invest06Price.length > 0) {
 				invest06Price.forEach(function(node){
 					invest06TotPrice = invest06TotPrice + getNumber(removeComma(node.innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest06TotPrice').innerHTML = setComma(invest06TotPrice+"");

			//7.기타투자비용 제조
 			investIndex = 0;
 			if (invest07Price.length > 0) {
 				invest07Price.forEach(function(node){
 					invest07TotPrice = invest07TotPrice + getNumber(removeComma(node.innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest07TotPrice').innerHTML = setComma(invest07TotPrice+"");

			//7.기타투자비용 판매
 			investIndex = 0;
 			if (invest08Price.length > 0) {
 				invest08Price.forEach(function(node){
 					invest08TotPrice = invest08TotPrice + getNumber(removeComma(node.innerHTML));
 					investIndex++;
	  	      });
	         }	
	        document.getElementById('dInvest08TotPrice').innerHTML = setComma(invest08TotPrice+"");


            let innvest01TotAmt =0;
            let innvest02TotAmt =0;
            let innvest03TotAmt =0;
            let innvest04TotAmt =0;
			//제조부문합계
            innvest01TotAmt = invest01TotPrice+invest03TotPrice+invest05TotPrice+invest07TotPrice;
			//판매부문합계
            innvest02TotAmt = invest02TotPrice+invest04TotPrice+invest06TotPrice+invest08TotPrice;
			//월제조비용합계
            innvest03TotAmt = invest01TotAmt+invest03TotAmt;
			//월판관비용합계
            innvest04TotAmt = invest02TotAmt+invest04TotAmt;

            
	        document.getElementById('innvest01TotAmt').innerHTML = setComma(innvest01TotAmt+"");
	        document.getElementById('innvest02TotAmt').innerHTML = setComma(innvest02TotAmt+"");
	        document.getElementById('innvest03TotAmt').innerHTML = setComma(innvest03TotAmt+"");
	        document.getElementById('innvest04TotAmt').innerHTML = setComma(innvest04TotAmt+"");
		 }

		 //자금 추가
		 function addTrLoan(pCd){
	     		var tbStr="";
	     		tbStr="<thead>";
	     		tbStr += "<tr>";
	     		tbStr += "<th>년도</th>";
				if (loanCd=="1") {
	     			tbStr += "<th width=\"50%\"><span>자본금액(단위:천원)</span></th>";
				} else{
	     			tbStr += "<th width=\"35%\"><span>차입금(단위:천원)</span></th>";
	     			tbStr += "<th width=\"35%\"><span>이자율(%)</span></th>";
				}
	     		tbStr += "</tr>";
	     		tbStr += "</thead>";
	     		tbStr += "<tbody>";
	     		tbStr += "<tr>";
	     		tbStr += "<td>1차년도</td>";
	     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanYear1\" value=\"\" name=\"inputLoanYear1\" placeholder=\"1차년도 자본금을 입력하세요(단위:천원)\"></td>";
				if (loanCd!="1") {
		     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanRate1\" value=\"\" name=\"inputLoanRate1\" placeholder=\"1차년도 이자율을 입력하세요\"></td>";
		   		}
	         	tbStr += "</tr>";
	     		tbStr += "<tr>";
	     		tbStr += "<td>2차년도</td>";
	     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanYear2\" value=\"\" name=\"inputLoanYear2\" placeholder=\"2차년도 자본금을 입력하세요(단위:천원)\"></td>";
				if (loanCd!="1") {
		     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanRate2\" value=\"\" name=\"inputLoanRate2\" placeholder=\"2차년도 이자율을 입력하세요\"></td>";
		   		}
	     		tbStr += "</tr>";
	     		tbStr += "<tr>";
	     		tbStr += "<td>3차년도</td>";
	     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanYear3\" value=\"\" name=\"inputLoanYear3\" placeholder=\"3차년도 자본금을 입력하세요(단위:천원)\"></td>";
				if (loanCd!="1") {
		     		tbStr += "<td style=\"text-align:left;\"><input type=\"text\" class=\"numeric span2\" id=\"inputLoanRate3\" value=\"\" name=\"inputLoanRate3\" placeholder=\"3차년도 이자율을 입력하세요\"></td>";
		   		}
	     		tbStr += "</tr>";
	     		tbStr += "</tbody>";


				$('#modalLoan').html(tbStr);
		 }
		 
		 function addLoanPop(pCd) {
			loanAddFlag="ADD";
			loanCd = pCd;
			document.getElementById('btnAddLoan').value="추가";
			if (loanCd=="1") {
				$('#inputLoanTitle').html(loan1ComboStr);
			}else {	
				$('#inputLoanTitle').html(loan2ComboStr);
			}

			addTrLoan(loanCd);
			
			document.getElementById('inputLoanTitle').value ="";
			document.getElementById('inputLoanYear1').value ="";
			document.getElementById('inputLoanYear2').value ="";
			document.getElementById('inputLoanYear3').value ="";
			$('#myLoanModal').modal('toggle');
		 };

		 document.getElementById('btnAddLoan').addEventListener('click',function(e){
				console.log("*****btnAddLoan*******");
				if (document.getElementById('inputLoanTitle').value == "") {
					alert("자금항목을 선택하세요");
					 document.getElementById("inputLoanTitle").focus();
					return;
				}

				let loanYearCnt = 0;

				if (document.getElementById('inputLoanYear1').value == "") {
					loanYearCnt++;
				}
				if (document.getElementById('inputLoanYear2').value == "") {
					loanYearCnt++;
				}
				if (document.getElementById('inputLoanYear3').value == "") {
					loanYearCnt++;
				}

				
				if (loanYearCnt > 2) {
					alert("년도별 자본금액을 입력하세요.");
					document.getElementById("inputLoanYear1").focus();
					return;
				}

				
				var inputLoanTitle 		= "";
				var inputLoanYear1 		= "";
				var inputLoanYear2 		= "";
				var inputLoanYear3 		= "";
				var inputLoanRate1 		= "";
				var inputLoanRate2 		= "";
				var inputLoanRate3 		= "";
				var inputLoanYearRate1 	= "";
				var inputLoanYearRate2 	= "";
				var inputLoanYearRate3 	= "";

				var inputLoanTitleValue  	= $("#inputLoanTitle option:selected").text();
				var inputLoanTitleCdValue  	= $("#inputLoanTitle option:selected").val();

				var inputLoanYear1Value    	= document.getElementById('inputLoanYear1').value;
				var inputLoanYear2Value    	= document.getElementById('inputLoanYear2').value;
				var inputLoanYear3Value    	= document.getElementById('inputLoanYear3').value;


				let loanNo = "01";
				if(loanCd=="1") loanNo = "01";
				else loanNo = "02";


				let inputLoanRate1Value    	= "";
				let inputLoanRate2Value    	= "";
				let inputLoanRate3Value    	= "";

				let inputLoanYearRate1Value = 0;
				let inputLoanYearRate2Value = 0;
				let inputLoanYearRate3Value = 0;
				
				if(loanCd=="2") {
					inputLoanRate1Value    	= document.getElementById('inputLoanRate1').value;
					inputLoanRate2Value    	= document.getElementById('inputLoanRate2').value;
					inputLoanRate3Value    	= document.getElementById('inputLoanRate3').value;

					inputLoanYearRate1Value = 0;
					inputLoanYearRate2Value = 0;
					inputLoanYearRate3Value = 0;

					if(inputLoanRate1Value !="" && inputLoanYear1Value !=""){
						inputLoanYearRate1Value = getNumber(inputLoanYear1Value) * (getNumber(inputLoanRate1Value)/100)
						//inputLoanYearRate1Value = Math.floor(inputLoanYearRate1Value/10) * 10
					}
					if(inputLoanRate2Value !="" && inputLoanYear2Value !=""){
						inputLoanYearRate2Value = getNumber(inputLoanYear2Value) * (getNumber(inputLoanRate2Value)/100)
						//inputLoanYearRate2Value = Math.floor(inputLoanYearRate2Value/10) * 10
					}
					if(inputLoanRate3Value !="" && inputLoanYear3Value !=""){
						inputLoanYearRate3Value = getNumber(inputLoanYear3Value) * (getNumber(inputLoanRate3Value)/100)
						//inputLoanYearRate3Value = Math.floor(inputLoanYearRate3Value/10) * 10
					}

				}
				 	
				
				if(loanAddFlag=="ADD") {		//추가버튼 클릭시
					addLoanTable(loanCd,inputLoanTitleValue,inputLoanTitleCdValue,inputLoanYear1Value,inputLoanYear2Value,inputLoanYear3Value,inputLoanYearRate1Value,inputLoanYearRate2Value,inputLoanYearRate3Value); 
				} else{
					$('[name=dLoan'+loanNo+'Title]')[loanIndexNo].innerHTML 	= inputLoanTitleValue;
					$('[name=dLoan'+loanNo+'TitleCd]')[loanIndexNo].innerHTML 	= inputLoanTitleCdValue;
					$('[name=dLoan'+loanNo+'Year1]')[loanIndexNo].innerHTML 	= setComma(inputLoanYear1Value);
					$('[name=dLoan'+loanNo+'Year2]')[loanIndexNo].innerHTML 	= setComma(inputLoanYear2Value);
					$('[name=dLoan'+loanNo+'Year3]')[loanIndexNo].innerHTML 	= setComma(inputLoanYear3Value);

					if(loanCd=="2") {
						$('[name=dLoan'+loanNo+'Rate1]')[loanIndexNo].innerHTML 	= setComma(inputLoanRate1Value);
						$('[name=dLoan'+loanNo+'Rate2]')[loanIndexNo].innerHTML 	= setComma(inputLoanRate2Value);
						$('[name=dLoan'+loanNo+'Rate3]')[loanIndexNo].innerHTML 	= setComma(inputLoanRate3Value);
						$('[name=dLoan'+loanNo+'YearRate1]')[loanIndexNo].innerHTML = setComma(inputLoanYearRate1Value+"");
						$('[name=dLoan'+loanNo+'YearRate2]')[loanIndexNo].innerHTML = setComma(inputLoanYearRate2Value+"");
						$('[name=dLoan'+loanNo+'YearRate3]')[loanIndexNo].innerHTML = setComma(inputLoanYearRate3Value+"");
					}
				}
				calLoan();
				$('#myLoanModal').modal('toggle');
		 });

			$(document).on('click', 'input[name=btnUpdLoan01]', function() {
			 	loanAddFlag="UPD";
			 	loanCd = "1";
				document.getElementById('btnAddLoan').value="수정";
				$('#inputLoanTitle').html(loan1ComboStr);
			 	loanIndexNo = $('input[name=btnUpdLoan01]').index(this);
				addTrLoan(loanCd);

			 	document.getElementById('inputLoanTitle').value      = $('[name=dLoan01TitleCd]')[loanIndexNo].innerHTML;
			 	document.getElementById('inputLoanYear1').value      = removeComma($('[name=dLoan01Year1]')[loanIndexNo].innerHTML);
				document.getElementById('inputLoanYear2').value      = removeComma($('[name=dLoan01Year2]')[loanIndexNo].innerHTML);
				document.getElementById('inputLoanYear3').value      = removeComma($('[name=dLoan01Year3]')[loanIndexNo].innerHTML);
				$('#myLoanModal').modal('toggle');
		});


		$(document).on('click', 'input[name=btnDelLoan01]', function() {
				if(!confirm('자본항목을 삭제하시겠습니까?')){
					return;
				}
			 	loanCd = "1"
				loanIndexNo = $('input[name=btnDelLoan01]').index(this);
				document.getElementById("loanTable01").deleteRow(loanIndexNo+1);
				calLoan();
		});

		$(document).on('click', 'input[name=btnUpdLoan02]', function() {
			 	loanAddFlag="UPD";
			 	loanCd = "2";
				document.getElementById('btnAddLoan').value="수정";
				$('#inputLoanTitle').html(loan2ComboStr);
			 	loanIndexNo = $('input[name=btnUpdLoan02]').index(this);
				addTrLoan(loanCd);

			 	document.getElementById('inputLoanTitle').value      = $('[name=dLoan02TitleCd]')[loanIndexNo].innerHTML;
			 	document.getElementById('inputLoanYear1').value      = removeComma($('[name=dLoan02Year1]')[loanIndexNo].innerHTML);
				document.getElementById('inputLoanYear2').value      = removeComma($('[name=dLoan02Year2]')[loanIndexNo].innerHTML);
				document.getElementById('inputLoanYear3').value      = removeComma($('[name=dLoan02Year3]')[loanIndexNo].innerHTML);
			 	document.getElementById('inputLoanRate1').value      = removeComma($('[name=dLoan02Rate1]')[loanIndexNo].innerHTML);
			 	document.getElementById('inputLoanRate2').value      = removeComma($('[name=dLoan02Rate2]')[loanIndexNo].innerHTML);
			 	document.getElementById('inputLoanRate3').value      = removeComma($('[name=dLoan02Rate3]')[loanIndexNo].innerHTML);
				$('#myLoanModal').modal('toggle');
		});


		$(document).on('click', 'input[name=btnDelLoan02]', function() {
				if(!confirm('자본항목을 삭제하시겠습니까?')){
					return;
				}
			 	loanCd = "2"
				loanIndexNo = $('input[name=btnDelLoan02]').index(this);
				document.getElementById("loanTable02").deleteRow(loanIndexNo+1);
				document.getElementById("loanTable02").deleteRow(loanIndexNo+1);
				document.getElementById("loanTable02").deleteRow(loanIndexNo+1);
		});

		 //자금금액계산
		 function calLoan() {
			 //일매출금액
            let monthJobAmt = 0;
            let yearJomAmt = 0;

            let loan01TotYear1Amt = 0;
            let loan01TotYear2Amt = 0;
            let loan01TotYear3Amt = 0;

            let loan02TotYear1Amt = 0;
            let loan02TotYear2Amt = 0;
            let loan02TotYear3Amt = 0;

            let loan02TotYearRate1Amt = 0;
            let loan02TotYearRate2Amt = 0;
            let loan02TotYearRate3Amt = 0;
            
 			let loan01Year = document.querySelectorAll("[name=dLoan01Year1]");
 			let loan02Year = document.querySelectorAll("[name=dLoan02Year1]");
 			let loanIndex = 0;

			//1.자기자금계산
 			loanIndex = 0;
 			if (loan01Year.length > 0) {
 				loan01Year.forEach(function(node){
 	 				if(node.innerHTML!="") loan01TotYear1Amt = loan01TotYear1Amt + getNumber(removeComma(node.innerHTML));
					if($('[name=dLoan01Year2]')[loanIndex].innerHTML!="") loan01TotYear2Amt = loan01TotYear2Amt + getNumber(removeComma($('[name=dLoan01Year2]')[loanIndex].innerHTML));
					if($('[name=dLoan01Year3]')[loanIndex].innerHTML!="") loan01TotYear3Amt = loan01TotYear3Amt + getNumber(removeComma($('[name=dLoan01Year3]')[loanIndex].innerHTML));
 					loanIndex++;
	  	      });
	         }	
	        document.getElementById('dLoan01TotYear1').innerHTML = setComma(loan01TotYear1Amt+"");
	        document.getElementById('dLoan01TotYear2').innerHTML = setComma(loan01TotYear2Amt+"");
	        document.getElementById('dLoan01TotYear3').innerHTML = setComma(loan01TotYear3Amt+"");

			//2.타인자금계산
 			loanIndex = 0;
 			if (loan02Year.length > 0) {
 				loan02Year.forEach(function(node){
 	 				if(node.innerHTML!="") loan02TotYear1Amt = loan02TotYear1Amt + getNumber(removeComma(node.innerHTML));
					if($('[name=dLoan02Year2]')[loanIndex].innerHTML!="") loan02TotYear2Amt = loan02TotYear2Amt + getNumber(removeComma($('[name=dLoan02Year2]')[loanIndex].innerHTML));
					if($('[name=dLoan02Year3]')[loanIndex].innerHTML!="") loan02TotYear3Amt = loan02TotYear3Amt + getNumber(removeComma($('[name=dLoan02Year3]')[loanIndex].innerHTML));

					if($('[name=dLoan02YearRate1]')[loanIndex].innerHTML!="") loan02TotYearRate1Amt = loan02TotYearRate1Amt + getNumber(removeComma($('[name=dLoan02YearRate1]')[loanIndex].innerHTML));
					if($('[name=dLoan02YearRate2]')[loanIndex].innerHTML!="") loan02TotYearRate2Amt = loan02TotYearRate2Amt + getNumber(removeComma($('[name=dLoan02YearRate2]')[loanIndex].innerHTML));
					if($('[name=dLoan02YearRate3]')[loanIndex].innerHTML!="") loan02TotYearRate3Amt = loan02TotYearRate3Amt + getNumber(removeComma($('[name=dLoan02YearRate3]')[loanIndex].innerHTML));
 					loanIndex++;
	  	      });
	         }	
	        document.getElementById('dLoan02TotYear1').innerHTML = setComma(loan02TotYear1Amt+"");
	        document.getElementById('dLoan02TotYear2').innerHTML = setComma(loan02TotYear2Amt+"");
	        document.getElementById('dLoan02TotYear3').innerHTML = setComma(loan02TotYear3Amt+"");
	        document.getElementById('dLoan02TotYearRate1').innerHTML = setComma(loan02TotYearRate1Amt+"");
	        document.getElementById('dLoan02TotYearRate2').innerHTML = setComma(loan02TotYearRate2Amt+"");
	        document.getElementById('dLoan02TotYearRate3').innerHTML = setComma(loan02TotYearRate3Amt+"");


	        //합계계산
            let loanTotYear1Amt =0;
            let loanTotYear2Amt =0;
            let loanTotYear3Amt =0;

            loanTotYear1Amt = loan01TotYear1Amt + loan02TotYear1Amt;
            loanTotYear2Amt = loan01TotYear2Amt + loan02TotYear2Amt;
            loanTotYear3Amt = loan01TotYear3Amt + loan02TotYear3Amt;
            
	        document.getElementById('loanTotYear1Amt').innerHTML = setComma(loanTotYear1Amt+"");
	        document.getElementById('loanTotYear2Amt').innerHTML = setComma(loanTotYear2Amt+"");
	        document.getElementById('loanTotYear3Amt').innerHTML = setComma(loanTotYear3Amt+"");

		 }

		 //제조경비 추가
		 function addMCostPop() {
			MCostAddFlag="ADD";
			document.getElementById('btnAddMcost').value="추가";
			document.getElementById('inputMcostcdCodeM').value ="";
			document.getElementById('sMCostCd').innerHTML ="";
			document.getElementById('inputMCostTVa').value ="";
			document.getElementById('sMCostTVaLabel').innerHTML ="";
			$('#myMCostModal').modal('toggle');
		 };

		//제조경비 계정과목 변경시 
		$('#inputMcostcdCodeM').on('change', function(e) {
			let costcdCodeM = this.value;
		   	mCostJson.forEach(function(node){
				if(costcdCodeM == node.CODE){
					document.getElementById('sMCostCd').innerHTML = node.ATTRB_1;
					document.getElementById('sMCostTVaLabel').innerHTML = node.ATTRB_2;
					return false;
    			}
  	        })
		});
		 
		 document.getElementById('btnAddMcost').addEventListener('click',function(e){
				console.log("*****btnAddMcost*******");
				if (document.getElementById('inputMcostcdCodeM').value == "") {
					alert("계정과목을 선택하세요");
					 document.getElementById("inputMcostcdCodeM").focus();
					return;
				}
				
				var inputMcostcdCodeM 	= "";
				var sMCostCdValue 		= "";
				var inputMCostTV 		= "";
				var MCostTVaAmt 		= "";
				var iMCostTVaAmt 		= 0;
				let McostNo = "01";
				let  iMCostCdAmt       = 0;

				var inputMcostcdCodeMValue   = $("#inputMcostcdCodeM option:selected").text();
				var inputMcostcdCodeMCdValue = $("#inputMcostcdCodeM option:selected").val();
				var inputMCostTVaValue 		 = document.getElementById('inputMCostTVa').value;
				var sMCostCdValue    		 = document.getElementById('sMCostCd').innerHTML;
				var sMCostTVaLabelValue   	 = document.getElementById('sMCostTVaLabel').innerHTML;




				if(MCostAddFlag=="ADD") {		//추가버튼 클릭시
					addMCostTable(inputMcostcdCodeMValue,inputMcostcdCodeMCdValue,sMCostCdValue,inputMCostTVaValue,sMCostTVaLabelValue);
					mCostIndexNo = (document.querySelectorAll("[name=sMCost01Title]").length-1);
					setMMCostTable("ADD", mCostIndexNo);	

				} else{
					//월정액,기준액,인건비 이면 가격 비율을 곱해서 월 제조경비를 구한다.
					if(sMCostCdValue!=""){
						if(sMCostCdValue =="매출액" || sMCostCdValue =="기준액"|| sMCostCdValue =="인건비"){
							if(sMCostCdValue =="매출액") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMmenuAmt01Tot').innerHTML));
							if(sMCostCdValue =="기준액") iMCostCdAmt = getNumber(removeComma(document.getElementById('innvest03TotAmt').innerHTML));
							if(sMCostCdValue =="인건비") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMJobAmt01Sub1Tot').innerHTML));
							
							console.log("iMCostCdAmt",iMCostCdAmt);
							iMCostTVaAmt = (iMCostCdAmt * getNumber(inputMCostTVaValue)/100);
							iMCostTVaAmt = getNumberPoint(iMCostTVaAmt,0);
						} else {
							iMCostTVaAmt = getNumber(inputMCostTVaValue);
						}
					}

					$('[name=sMCost'+McostNo+'Title]')[mCostIndexNo].innerHTML   	= inputMcostcdCodeMValue;
					$('[name=sMCost'+McostNo+'TitleCd]')[mCostIndexNo].innerHTML 	= inputMcostcdCodeMCdValue;
					$('[name=sMCost'+McostNo+'TVa]')[mCostIndexNo].innerHTML 	 	= setComma(inputMCostTVaValue);
					$('[name=sMCost'+McostNo+'TVaLabel]')[mCostIndexNo].innerHTML 	= sMCostTVaLabelValue;
					$('[name=sMCost'+McostNo+'TVaAmt]')[mCostIndexNo].innerHTML 	= setComma(iMCostTVaAmt+"");
					setMMCostTable("UPD",mCostIndexNo);	
				}

				//calInvest();
				$('#myMCostModal').modal('toggle');

				
		 });

		$(document).on('click', 'input[name=btnUpdMCost01]', function() {
				MCostAddFlag="UPD";
				document.getElementById('btnAddMcost').value="수정";
				mCostIndexNo = $('input[name=btnUpdMCost01]').index(this);

			 	document.getElementById('inputMcostcdCodeM').value = $('[name=sMCost01TitleCd]')[mCostIndexNo].innerHTML;
				document.getElementById('sMCostCd').innerHTML  	   = $('[name=sMCost01Cd]')[mCostIndexNo].innerHTML;
			 	document.getElementById('inputMCostTVa').value     = removeComma($('[name=sMCost01TVa]')[mCostIndexNo].innerHTML);
			 	document.getElementById('sMCostTVaLabel').innerHTML= $('[name=sMCost01TVaLabel]')[mCostIndexNo].innerHTML;
				$('#myMCostModal').modal('toggle');

							
		});


		 $(document).on('click', 'input[name=btnDelMCost01]', function() {
				if(!confirm('제조경비항목을 삭제하시겠습니까?')){
					return;
				}
			 	mCostIndexNo = $('input[name=btnDelMCost01]').index(this);
				document.getElementById("MCostTable01").deleteRow(mCostIndexNo+1);
				//calInvest();
				setMMCostTable("DEL",mCostIndexNo+1);				

		 });

		 //재조경비 셋팅
		 function setMMCostTable(pCd,pIndex) {

            let	sMMCostTitleValue   = "";  //계정과목
            let	sMMCostCdValue	 	= "";  //계정과목기준
            let sMMCostTVaValue 	= "";  //기준금액 
            let	sMCostTVaLabelValue = "";  //기준금액 단위
            let sMCostTVaAmt		= "";   //적용금액
            
	        let	sMMCostTitle 	 	 = "";
	        let sMMCostCd            = "";
		    let sMMCostTVa           = "";
	        let MCostAmt 	 		 = new Array(); //월재료비            
			let iMCostMenuAmt       = 0;     //메뉴별 월판매금액
			let iTemp               ="";
 			let i = 0;
			let iMMCostMenuTot      = 0;
        	var trStr="";

        	
            if(pCd == "ADD") {
			    console.log("pIndex",pIndex);
			    addMCostMTable(pIndex,"","Y");

              }else if(pCd == "UPD"){
			    console.log("pIndex",pIndex);
                  
              	sMMCostTitleValue 	=  $('[name=sMCost01Title]')[pIndex].innerHTML;
            	sMMCostCdValue		=  $('[name=sMCost01Cd]')[pIndex].innerHTML;		
            	sMMCostTVaValue     =  $('[name=sMCost01TVa]')[pIndex].innerHTML;		
            	sMCostTVaLabelValue	=  $('[name=sMCost01TVaLabel]')[pIndex].innerHTML;	
            	sMCostTVaAmtValue	=  removeComma($('[name=sMCost01TVaAmt]')[pIndex].innerHTML);	

            	if($('[name=sMCost01TVaAmt]')[pIndex].innerHTML!=""){
            		iMMCostMenuTot      = getNumber(sMCostTVaAmtValue) *12;
            	}
                        			 
        		$('[name=sMMCostTitle]')[pIndex].innerHTML 	 = sMMCostTitleValue;
    			$('[name=sMMCostCd]')[pIndex].innerHTML 	 = sMMCostCdValue;
       			$('[name=sMMCostTVa]')[pIndex].innerHTML 	 = sMMCostTVaValue;
      			$('[name=sMCostTVaLabel]')[pIndex].innerHTML = sMCostTVaLabelValue;

    			for (i = 1; i < 13; i++){
					if (i < 10 ) iTemp ="0"+i
					else iTemp = i;
	    			$('[name=sMMCostAmt'+iTemp+']')[pIndex].innerHTML = setComma(sMCostTVaAmtValue); 	
				}

    			$('[name=sMMCostMenuTot]')[pIndex].innerHTML = iMMCostMenuTot+"";

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("mMCostTable").deleteRow(pIndex);
            }

			//월 예상 매출액 합계 계산
            calMMCost();
            //년 에상 재료비 셋팅 
            setYMCostTable(pCd,pIndex);
            //년 에상 재조경비 계산 
            calYMCost();
		 }

		 //연재조경비 셋팅
		 function setYMCostTable(pCd,pIndex) {

            let	sYMCostTitleValue 	= "";
            let	sYMCostCdValue    	= "";	
            let	sYMCostTVaValue    	= "";		
            let	sYCostTVaLabelValue = "";		
            let sMMCostMenuTotValue = "";
            let sYMCostAmtRateValue = "";
	        let	sYMCostTitle 	 	= "";
	        let	sYMCostCd			= "";	
	        let	sYMCostTVa 	 		= "";
	        let YCostAmtYear1		= "";
	        let YCostAmtYear2		= "";
	        let YCostAmtYear3		= "";

	        let iYCostAmtYear1		= 0;
	        let iYCostAmtYear2		= 0;
	        let iYCostAmtYear3		= 0;
	        
        	var trStr="";

        	
            if(pCd == "ADD") {
            	addMCostYTable(pIndex,"","Y");
              }else if(pCd == "UPD"){
     			
              	sYMCostTitleValue 	=  $('[name=sMCost01Title]')[pIndex].innerHTML;
            	sYMCostCdValue		=  $('[name=sMCost01Cd]')[pIndex].innerHTML;		
            	sYMCostTVaValue     =  $('[name=sMCost01TVa]')[pIndex].innerHTML;		
            	sYCostTVaLabelValue	=  $('[name=sMCost01TVaLabel]')[pIndex].innerHTML;	
            	sMMCostMenuTotValue =  $('[name=sMMCostMenuTot]')[pIndex].innerHTML;	
            	sYMCostAmtRateValue =  $('[name=sYMCostAmtRate]')[pIndex].innerHTML;	
         			 
        		$('[name=sYMCostTitle]')[pIndex].innerHTML   = sYMCostTitleValue;
    			$('[name=sYMCostCd]')[pIndex].innerHTML 	 = sYMCostCdValue;
       			$('[name=sYMCostTVa]')[pIndex].innerHTML 	 = sYMCostTVaValue;
       			$('[name=sYCostTVaLabel]')[pIndex].innerHTML = sYCostTVaLabelValue;

				if(sMMCostMenuTotValue!="" || sMMCostMenuTotValue!="0"){
					iYCostAmtYear1  = getNumber(sMMCostMenuTotValue);
					iYCostAmtYear2  = iYCostAmtYear1;
					iYCostAmtYear3  = iYCostAmtYear1;
				}
				
				if(sYMCostAmtRateValue!="0" && iYCostAmtYear1!=0) {
					if(sMMCostMenuTotValue !=""){
						iYCostAmtYear2  = iYCostAmtYear1 * (1+(sYMCostAmtRateValue/100));
						iYCostAmtYear3  = iYCostAmtYear2 * (1+(sYMCostAmtRateValue/100)) ;
					}
				}
     			
     			$('[name=sYMCostAmtYear1]')[pIndex].innerHTML = setComma(iYCostAmtYear1+""); 	
     			$('[name=sYMCostAmtYear2]')[pIndex].innerHTML = setComma(iYCostAmtYear2+""); 	
     			$('[name=sYMCostAmtYear3]')[pIndex].innerHTML = setComma(iYCostAmtYear3+""); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("yMCostTable").deleteRow(pIndex);
	        }

		 }

		 $(document).on('click', 'input[name=btnUpdYYMCost]', function() {
			    yYCostIndexNo = $('input[name=btnUpdYYMCost]').index(this);
			 	document.getElementById('YMCostTitle').innerHTML = $('[name=sYMCostTitle]')[yYCostIndexNo].innerHTML;

				if (document.getElementById('monthMCostAmt').innerHTML==""){
					 alert( $('[name=sYMCostTitle]')[yYCostIndexNo].innerHTML+"의 적용값을 먼저 입력하세요");
					 return;
				}

			 	
			 	let YMCostAmtCd = $('[name=sYMCostAmtCd]')[yYCostIndexNo].innerHTML;
			 	initPopYMCOST(YMCostAmtCd);

				
				$('#myYMCostModal').modal('toggle');
		});
		//연제조경비 팝업 셋팅	
		function initPopYMCOST(pCd) {
	   		if("1"==pCd){
		   		$('#modalYMCostRate').show();
		   		$("#inputYMCostYear1").attr("readonly", true);
		   		$("#inputYMCostYear2").attr("readonly", true);
		   		$("#inputYMCostYear3").attr("readonly", true);
		   	}else if("2"==pCd){
		   		$('#modalYMCostRate').hide();
		   		$("#inputYMCostYear1").attr("readonly", true);
		   		$("#inputYMCostYear2").attr("readonly", false);
		   		$("#inputYMCostYear3").attr("readonly", false);
			}
		 	document.getElementById('inputYMCostAmtCd').value   	= pCd;
		 	document.getElementById('inputYMCostRate').value    = removeComma($('[name=sYMCostAmtRate]')[yYCostIndexNo].innerHTML);
		 	document.getElementById('inputYMCostYear1').value   = removeComma($('[name=sYMCostAmtYear1]')[yYCostIndexNo].innerHTML);
			document.getElementById('inputYMCostYear2').value   = removeComma($('[name=sYMCostAmtYear2]')[yYCostIndexNo].innerHTML);
			document.getElementById('inputYMCostYear3').value   = removeComma($('[name=sYMCostAmtYear3]')[yYCostIndexNo].innerHTML);

		}
		//연도별 매출등록팝업 입력방법선택	
		$('#inputYMCostAmtCd').on('change', function(e) {
			initPopYMCOST(this.value);
		});

		//연 제조경비 수정
		document.getElementById('btnAddYMCost').addEventListener('click',function(e){
			console.log("*****btnAddYMCost*******");

			
	        let inputYMCostAmtCd 				= document.getElementById('inputYMCostAmtCd');        
			var inputYMCostAmtCdValue 		= inputYmenuCd.options[inputYMCostAmtCd.selectedIndex].value;


			
			if (inputYMCostAmtCdValue == "1") {

				if(document.getElementById('inputYMCostRate').value ==""){
					 alert("재조경비 증감율을 입력하세요");
					 document.getElementById("inputYMCostRate").focus();
					return;
				}
				
			} else{
				if(document.getElementById('inputYMCostYear2').value ==""){
					 alert("2차년도 제보경비를  입력하세요");
					 document.getElementById("inputYMCostYear2").focus();
					return;
				}
				if(document.getElementById('inputYMCostYear3').value ==""){
					 alert("3차년도 재보경비를  입력하세요");
					 document.getElementById("inputYMCostYear3").focus();
					return;
				}

			}


			var inputYMCostRateValue 		= document.getElementById('inputYMCostRate').value;
			var inputYMCostYear1Value 		= removeComma(document.getElementById('inputYMCostYear1').value);
			var inputYMCostYear2Value 		= removeComma(document.getElementById('inputYMCostYear2').value);
			var inputYMCostYear3Value 		= removeComma(document.getElementById('inputYMCostYear3').value);

			$('[name=sYMCostAmtCd]')[yYCostIndexNo].innerHTML 		= inputYMCostAmtCdValue;
			$('[name=sYMCostAmtRate]')[yYCostIndexNo].innerHTML 	= inputYMCostRateValue;

			//판매금액
			//$('[name=sYMenuAmtYear1]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear1+"");
			$('[name=sYMCostAmtYear2]')[yYCostIndexNo].innerHTML	= setComma(inputYMCostYear2Value);
			$('[name=sYMCostAmtYear3]')[yYCostIndexNo].innerHTML	= setComma(inputYMCostYear3Value);

			//년매출 재조경비합계계산
			calYMCost();
			$('#myYMCostModal').modal('toggle');
	     });

		 //연도별 재조경비 증가율 계산 
		 $('#inputYMCostRate').on('change', function(e) {
			if (this.value != ""){

				var inputYMCostRateValue 	= document.getElementById('inputYMCostRate').value;
				var inputYMCostYear1Value 	= document.getElementById('inputYMCostYear1').value;
				var inputYMCostYear2Value 	= document.getElementById('inputYMCostYear2').value;
				var inputYMCostYear3Value 	= document.getElementById('inputYMCostYear3').value;

				let iinputYMCostYear1 = getNumber(inputYMCostYear1Value);
				let iinputYMCostYear2 = 0;
				let iinputYMCostYear3 = 0;

				let iinputYMCostRateValue = getNumber(inputYMCostRateValue);
				
				iinputYMCostYear2 = iinputYMCostYear1 * (1+(iinputYMCostRateValue/100)) ;
				iinputYMCostYear3 = iinputYMCostYear2 * (1+(iinputYMCostRateValue/100)) ;

				iinputYMCostYear2=Math.floor(iinputYMCostYear2);
				iinputYMCostYear3=Math.floor(iinputYMCostYear3);
				
				document.getElementById('inputYMCostYear2').value =iinputYMCostYear2+"";
				document.getElementById('inputYMCostYear3').value =iinputYMCostYear3+"";

			}
		 });
		
		 //재조경비 월계산
		 function calMMCost() {

			let sMMCostAmt 	 	= document.querySelectorAll("[name=sMMCostAmt01]");
			let costAmtTot 		= new Array();

            let iMCostAmtTot   = 0;
            
			let i=0;
			let j=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				costAmtTot[i] =0;
			}

			i=0;			
 			if (sMMCostAmt.length > 0) {
 				sMMCostAmt.forEach(function(node){


	    			for(j=0; j< 12; j++){
						if(j < 9)  jTemp ="0"+(j+1);
						else jTemp = j+1;

						if($('[name=sMMCostAmt'+jTemp+']')[i].innerHTML!="")
						costAmtTot[j] = costAmtTot[j] + getNumber(removeComma($('[name=sMMCostAmt'+jTemp+']')[i].innerHTML));
					}

	    			iMCostAmtTot = 0;
	    			for(j=1; j< 13; j++){
						if(j < 10)  jTemp ="0"+j;
						else jTemp = j;
		    
						if($('[name=sMMCostAmt'+jTemp+']')[i].innerHTML!="")
						iMCostAmtTot  = iMCostAmtTot+getNumber(removeComma($('[name=sMMCostAmt'+jTemp+']')[i].innerHTML))
					}
	    			$('[name=sMMCostMenuTot]')[i].innerHTML = iMCostAmtTot+"";

	        		i++;
	  	      });
	         }	

			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
				 document.getElementById('sMMCostAmt'+iTemp+'Tot').innerHTML = setComma(costAmtTot[i]+"");
			}

			document.getElementById('monthMCostAmt').innerHTML = setComma(costAmtTot[0]+"");

			
		 }
		 //재조경비  연계산
		 function calYMCost() {

			let YMCostAmtYear 	 = document.querySelectorAll("[name=sYMCostAmtYear1]");
			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let menuIndex=0;
 			if (YMCostAmtYear.length > 0) {
 				YMCostAmtYear.forEach(function(node){

		        	if(node.innerHTML!="") 
			        	menuQty01Tot = menuQty01Tot + getNumber(removeComma(node.innerHTML));

		        	if($('[name=sYMCostAmtYear2]')[menuIndex].innerHTML!="")
	        			menuQty02Tot = menuQty02Tot + getNumber(removeComma($('[name=sYMCostAmtYear2]')[menuIndex].innerHTML));

		        	if($('[name=sYMCostAmtYear3]')[menuIndex].innerHTML!="")
		        	menuQty03Tot = menuQty03Tot + getNumber(removeComma($('[name=sYMCostAmtYear3]')[menuIndex].innerHTML));

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sYMCostAmt01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sYMCostAmt02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sYMCostAmt03Tot').innerHTML = setComma(menuQty03Tot+"");

			 document.getElementById('yearMCostAmt').innerHTML = setComma(menuQty01Tot+"");

		 }
		 //판매관리비 추가
		 function addSCostPop() {
			SCostAddFlag="ADD";
			document.getElementById('btnAddSCost').value="추가";
			document.getElementById('inputSCostcdCodeM').value ="";
			document.getElementById('sSCostCd').innerHTML ="";
			document.getElementById('inputSCostTVa').value ="";
			document.getElementById('sSCostTVaLabel').innerHTML ="";
			$('#mySCostModal').modal('toggle');
		 };

		//판매관리비 계정과목 변경시 
		$('#inputSCostcdCodeM').on('change', function(e) {
			let costcdCodeM = this.value;
		   	sCostJson.forEach(function(node){
				if(costcdCodeM == node.CODE){
					document.getElementById('sSCostCd').innerHTML = node.ATTRB_1;
					document.getElementById('sSCostTVaLabel').innerHTML = node.ATTRB_2;
					return false;
    			}
  	        })
		});
		 
		 document.getElementById('btnAddSCost').addEventListener('click',function(e){
				console.log("*****btnAddSCost*******");
				if (document.getElementById('inputSCostcdCodeM').value == "") {
					alert("계정과목을 선택하세요");
					 document.getElementById("inputSCostcdCodeM").focus();
					return;
				}
				
				var inputSCostcdCodeM 	= "";
				var sSCostCdValue 		= "";
				var inputSCostTV 		= "";
				var SCostTVaAmt 		= "";
				var iSCostTVaAmt 		= 0;
				let SCostNo = "01";
				let  iMCostCdAmt       = 0;

				var inputSCostcdCodeMValue   = $("#inputSCostcdCodeM option:selected").text();
				var inputSCostcdCodeMCdValue = $("#inputSCostcdCodeM option:selected").val();
				var inputSCostTVaValue 		 = document.getElementById('inputSCostTVa').value;
				var sSCostCdValue    		 = document.getElementById('sSCostCd').innerHTML;
				var sSCostTVaLabelValue   	 = document.getElementById('sSCostTVaLabel').innerHTML;


				if(SCostAddFlag=="ADD") {		//추가버튼 클릭시
					addSCostTable(inputSCostcdCodeMValue,inputSCostcdCodeMCdValue,sSCostCdValue,inputSCostTVaValue,sSCostTVaLabelValue);
					SCostIndexNo = (document.querySelectorAll("[name=sSCost01Title]").length-1);
					setMSCostTable("ADD", SCostIndexNo);	

				} else{
					if(sSCostCdValue!=""){
						if(sSCostCdValue =="매출액" || sSCostCdValue =="기준액"|| sSCostCdValue =="인건비"){
							if(sSCostCdValue =="매출액") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMmenuAmt01Tot').innerHTML));
							if(sSCostCdValue =="기준액") iMCostCdAmt = getNumber(removeComma(document.getElementById('innvest04TotAmt').innerHTML));
							if(sSCostCdValue =="인건비") iMCostCdAmt = getNumber(removeComma(document.getElementById('sMJobAmt01Sub2Tot').innerHTML));
							
							console.log("iMCostCdAmt",iMCostCdAmt);
							iSCostTVaAmt = (iMCostCdAmt * getNumber(inputSCostTVaValue)/100);
							iSCostTVaAmt = getNumberPoint(iSCostTVaAmt,0);
						} else {
							iSCostTVaAmt = getNumber(inputSCostTVaValue);
						}
					}

					$('[name=sSCost'+SCostNo+'Title]')[SCostIndexNo].innerHTML   	= inputSCostcdCodeMValue;
					$('[name=sSCost'+SCostNo+'TitleCd]')[SCostIndexNo].innerHTML 	= inputSCostcdCodeMCdValue;
					$('[name=sSCost'+SCostNo+'TVa]')[SCostIndexNo].innerHTML 	 	= setComma(inputSCostTVaValue);
					$('[name=sSCost'+SCostNo+'TVaLabel]')[SCostIndexNo].innerHTML 	= sSCostTVaLabelValue;
					$('[name=sSCost'+SCostNo+'TVaAmt]')[SCostIndexNo].innerHTML 	= setComma(iSCostTVaAmt+"");
					setMSCostTable("UPD",SCostIndexNo);	
				}

				//calInvest();
				$('#mySCostModal').modal('toggle');

				
		 });

		$(document).on('click', 'input[name=btnUpdSCost01]', function() {
				SCostAddFlag="UPD";
				document.getElementById('btnAddSCost').value="수정";
				SCostIndexNo = $('input[name=btnUpdSCost01]').index(this);

			 	document.getElementById('inputSCostcdCodeM').value = $('[name=sSCost01TitleCd]')[SCostIndexNo].innerHTML;
				document.getElementById('sSCostCd').innerHTML  	   = $('[name=sSCost01Cd]')[SCostIndexNo].innerHTML;
			 	document.getElementById('inputSCostTVa').value     = removeComma($('[name=sSCost01TVa]')[SCostIndexNo].innerHTML);
			 	document.getElementById('sSCostTVaLabel').innerHTML= $('[name=sSCost01TVaLabel]')[SCostIndexNo].innerHTML;
				$('#mySCostModal').modal('toggle');

							
		});


		 $(document).on('click', 'input[name=btnDelSCost01]', function() {
				if(!confirm('판매관리비 항목을 삭제하시겠습니까?')){
					return;
				}
			 	SCostIndexNo = $('input[name=btnDelSCost01]').index(this);
				document.getElementById("SCostTable01").deleteRow(SCostIndexNo+1);
				//calInvest();
				setMSCostTable("DEL",SCostIndexNo+1);				

		 });

		 //판매관리비 월년 보이기
		 $('#btnMSCostS').on('click', function(e) {
				$('#mSCost').show();
				$('#ySCost').hide();
		 });

		 $('#btnYSCostS').on('click', function(e) {
				$('#mSCost').hide();
				$('#ySCost').show();
		 });
			 
		 $('#btnMSCostH').on('click', function(e) {
				$('#mSCost').hide();
		 });

		 $('#btnYSCostH').on('click', function(e) {
				$('#ySCost').hide();
		 });

		 
		 //판매관리비 셋팅
		 function setMSCostTable(pCd,pIndex) {

            let	sMSCostTitleValue   = "";  //계정과목
            let	sMSCostCdValue	 	= "";  //계정과목기준
            let sMSCostTVaValue 	= "";  //기준금액 
            let	sSCostTVaLabelValue = "";  //기준금액 단위
            let sSCostTVaAmt		= "";   //적용금액
            
	        let	sMSCostTitle 	 	 = "";
	        let sMSCostCd            = "";
		    let sMSCostTVa           = "";
	        let SCostAmt 	 		 = new Array(); //월재료비            
			let iSCostMenuAmt       = 0;     //메뉴별 월판매금액
			let iTemp               ="";
 			let i = 0;
			let iMSCostMenuTot      = 0;
        	var trStr="";

        	
            if(pCd == "ADD") {
			    console.log("pIndex",pIndex);
			    addSCostMTable(pIndex,"","Y");
              }else if(pCd == "UPD"){
			    console.log("pIndex",pIndex);
                  
              	sMSCostTitleValue 	=  $('[name=sSCost01Title]')[pIndex].innerHTML;
            	sMSCostCdValue		=  $('[name=sSCost01Cd]')[pIndex].innerHTML;		
            	sMSCostTVaValue     =  $('[name=sSCost01TVa]')[pIndex].innerHTML;		
            	sSCostTVaLabelValue	=  $('[name=sSCost01TVaLabel]')[pIndex].innerHTML;	
            	sSCostTVaAmtValue	=  removeComma($('[name=sSCost01TVaAmt]')[pIndex].innerHTML);	

            	if($('[name=sSCost01TVaAmt]')[pIndex].innerHTML!=""){
            		iMSCostMenuTot      = getNumber(sSCostTVaAmtValue) *12;
            	}
                        			 
        		$('[name=sMSCostTitle]')[pIndex].innerHTML 	 = sMSCostTitleValue;
    			$('[name=sMSCostCd]')[pIndex].innerHTML 	 = sMSCostCdValue;
       			$('[name=sMSCostTVa]')[pIndex].innerHTML 	 = sMSCostTVaValue;
      			$('[name=sSCostTVaLabel]')[pIndex].innerHTML = sSCostTVaLabelValue;

    			for (i = 1; i < 13; i++){
					if (i < 10 ) iTemp ="0"+i
					else iTemp = i;
	    			$('[name=sMSCostAmt'+iTemp+']')[pIndex].innerHTML = setComma(sSCostTVaAmtValue); 	
				}

    			$('[name=sMSCostMenuTot]')[pIndex].innerHTML = iMSCostMenuTot+"";

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("mSCostTable").deleteRow(pIndex);
            }

			//월 예상 매출액 합계 계산
            calMSCost();
            //년 에상 재료비 셋팅 
            setYSCostTable(pCd,pIndex);
            //년 에상 재조경비 계산 
            calYSCost();
		 }

		 //연판매관리비 셋팅
		 function setYSCostTable(pCd,pIndex) {

            let	sYSCostTitleValue 	= "";
            let	sYSCostCdValue    	= "";	
            let	sYSCostTVaValue    	= "";		
            let	sYCostTVaLabelValue = "";		
            let sMSCostMenuTotValue = "";
            let sYSCostAmtRateValue = "";
	        let	sYSCostTitle 	 	= "";
	        let	sYSCostCd			= "";	
	        let	sYSCostTVa 	 		= "";
	        let YCostAmtYear1		= "";
	        let YCostAmtYear2		= "";
	        let YCostAmtYear3		= "";

	        let iYCostAmtYear1		= 0;
	        let iYCostAmtYear2		= 0;
	        let iYCostAmtYear3		= 0;
	        
        	var trStr="";

        	
            if(pCd == "ADD") {

            	addSCostYTable(pIndex,"","Y");
 
              }else if(pCd == "UPD"){
     			
              	sYSCostTitleValue 	=  $('[name=sSCost01Title]')[pIndex].innerHTML;
            	sYSCostCdValue		=  $('[name=sSCost01Cd]')[pIndex].innerHTML;		
            	sYSCostTVaValue     =  $('[name=sSCost01TVa]')[pIndex].innerHTML;		
            	sYCostTVaLabelValue	=  $('[name=sSCost01TVaLabel]')[pIndex].innerHTML;	
            	sMSCostMenuTotValue =  $('[name=sMSCostMenuTot]')[pIndex].innerHTML;	
            	sYSCostAmtRateValue =  $('[name=sYSCostAmtRate]')[pIndex].innerHTML;	
         			 
        		$('[name=sYSCostTitle]')[pIndex].innerHTML   = sYSCostTitleValue;
    			$('[name=sYSCostCd]')[pIndex].innerHTML 	 = sYSCostCdValue;
       			$('[name=sYSCostTVa]')[pIndex].innerHTML 	 = sYSCostTVaValue;
       			$('[name=sYCostTVaLabel]')[pIndex].innerHTML = sYCostTVaLabelValue;

				if(sMSCostMenuTotValue!="" || sMSCostMenuTotValue!="0"){
					iYCostAmtYear1  = getNumber(sMSCostMenuTotValue);
					iYCostAmtYear2  = iYCostAmtYear1;
					iYCostAmtYear3  = iYCostAmtYear1;
				}
				
				if(sYSCostAmtRateValue!="0" && iYCostAmtYear1!=0) {
					if(sMSCostMenuTotValue !=""){
						iYCostAmtYear2  = iYCostAmtYear1 * (1+(sYSCostAmtRateValue/100));
						iYCostAmtYear3  = iYCostAmtYear2 * (1+(sYSCostAmtRateValue/100)) ;
					}
				}
     			
     			$('[name=sYSCostAmtYear1]')[pIndex].innerHTML = setComma(iYCostAmtYear1+""); 	
     			$('[name=sYSCostAmtYear2]')[pIndex].innerHTML = setComma(iYCostAmtYear2+""); 	
     			$('[name=sYSCostAmtYear3]')[pIndex].innerHTML = setComma(iYCostAmtYear3+""); 	

     			
            }else if(pCd == "DEL"){
                console.log("pIndex:",pIndex);
				document.getElementById("ySCostTable").deleteRow(pIndex);
	        }

		 }

		 $(document).on('click', 'input[name=btnUpdYYSCost]', function() {
			    yYCostIndexNo = $('input[name=btnUpdYYSCost]').index(this);
			 	document.getElementById('YSCostTitle').innerHTML = $('[name=sYSCostTitle]')[yYCostIndexNo].innerHTML;

				if (document.getElementById('monthSCostAmt').innerHTML==""){
					 alert( $('[name=sYSCostTitle]')[yYCostIndexNo].innerHTML+"의 적용값을 먼저 입력하세요");
					 return;
				}

			 	
			 	let YSCostAmtCd = $('[name=sYSCostAmtCd]')[yYCostIndexNo].innerHTML;
			 	initPopYSCOST(YSCostAmtCd);

				
				$('#myYSCostModal').modal('toggle');
		});
		//연판매관리비 팝업 셋팅	
		function initPopYSCOST(pCd) {
	   		if("1"==pCd){
		   		$('#modalYSCostRate').show();
		   		$("#inputYSCostYear1").attr("readonly", true);
		   		$("#inputYSCostYear2").attr("readonly", true);
		   		$("#inputYSCostYear3").attr("readonly", true);
		   	}else if("2"==pCd){
		   		$('#modalYSCostRate').hide();
		   		$("#inputYSCostYear1").attr("readonly", true);
		   		$("#inputYSCostYear2").attr("readonly", false);
		   		$("#inputYSCostYear3").attr("readonly", false);
			}
		 	document.getElementById('inputYSCostAmtCd').value   	= pCd;
		 	document.getElementById('inputYSCostRate').value    = removeComma($('[name=sYSCostAmtRate]')[yYCostIndexNo].innerHTML);
		 	document.getElementById('inputYSCostYear1').value   = removeComma($('[name=sYSCostAmtYear1]')[yYCostIndexNo].innerHTML);
			document.getElementById('inputYSCostYear2').value   = removeComma($('[name=sYSCostAmtYear2]')[yYCostIndexNo].innerHTML);
			document.getElementById('inputYSCostYear3').value   = removeComma($('[name=sYSCostAmtYear3]')[yYCostIndexNo].innerHTML);

		}
		//연도별 판매관리비팝업 입력방법선택	
		$('#inputYSCostAmtCd').on('change', function(e) {
			initPopYSCOST(this.value);
		});

		//연 판매관리비 수정
		document.getElementById('btnAddYSCost').addEventListener('click',function(e){
			console.log("*****btnAddYSCost*******");

			
	        let inputYSCostAmtCd 				= document.getElementById('inputYSCostAmtCd');        
			var inputYSCostAmtCdValue 		= inputYmenuCd.options[inputYSCostAmtCd.selectedIndex].value;


			
			if (inputYSCostAmtCdValue == "1") {

				if(document.getElementById('inputYSCostRate').value ==""){
					 alert("재조경비 증감율을 입력하세요");
					 document.getElementById("inputYSCostRate").focus();
					return;
				}
				
			} else{
				if(document.getElementById('inputYSCostYear2').value ==""){
					 alert("2차년도 제조경비를  입력하세요");
					 document.getElementById("inputYSCostYear2").focus();
					return;
				}
				if(document.getElementById('inputYSCostYear3').value ==""){
					 alert("3차년도 재조경비를  입력하세요");
					 document.getElementById("inputYSCostYear3").focus();
					return;
				}

			}


			var inputYSCostRateValue 		= document.getElementById('inputYSCostRate').value;
			var inputYSCostYear1Value 		= removeComma(document.getElementById('inputYSCostYear1').value);
			var inputYSCostYear2Value 		= removeComma(document.getElementById('inputYSCostYear2').value);
			var inputYSCostYear3Value 		= removeComma(document.getElementById('inputYSCostYear3').value);

			$('[name=sYSCostAmtCd]')[yYCostIndexNo].innerHTML 		= inputYSCostAmtCdValue;
			$('[name=sYSCostAmtRate]')[yYCostIndexNo].innerHTML 	= inputYSCostRateValue;

			//판매금액
			//$('[name=sYMenuAmtYear1]')[yMenuIndexNo].innerHTML	= setComma(YMenuAmtYear1+"");
			$('[name=sYSCostAmtYear2]')[yYCostIndexNo].innerHTML	= setComma(inputYSCostYear2Value);
			$('[name=sYSCostAmtYear3]')[yYCostIndexNo].innerHTML	= setComma(inputYSCostYear3Value);

			//년매출 재조경비합계계산
			calYSCost();
			$('#myYSCostModal').modal('toggle');
	     });

		 //연도별 판매관리비 증가율 계산 
		 $('#inputYSCostRate').on('change', function(e) {
			if (this.value != ""){

				var inputYSCostRateValue 	= document.getElementById('inputYSCostRate').value;
				var inputYSCostYear1Value 	= document.getElementById('inputYSCostYear1').value;
				var inputYSCostYear2Value 	= document.getElementById('inputYSCostYear2').value;
				var inputYSCostYear3Value 	= document.getElementById('inputYSCostYear3').value;

				let iinputYSCostYear1 = getNumber(inputYSCostYear1Value);
				let iinputYSCostYear2 = 0;
				let iinputYSCostYear3 = 0;

				let iinputYSCostRateValue = getNumber(inputYSCostRateValue);
				
				iinputYSCostYear2 = iinputYSCostYear1 * (1+(iinputYSCostRateValue/100)) ;
				iinputYSCostYear3 = iinputYSCostYear2 * (1+(iinputYSCostRateValue/100)) ;

				iinputYSCostYear2=Math.floor(iinputYSCostYear2);
				iinputYSCostYear3=Math.floor(iinputYSCostYear3);
				
				document.getElementById('inputYSCostYear2').value =iinputYSCostYear2+"";
				document.getElementById('inputYSCostYear3').value =iinputYSCostYear3+"";

			}
		 });
		
		 //판매관리비 월계산
		 function calMSCost() {

			let sMSCostAmt 	 	= document.querySelectorAll("[name=sMSCostAmt01]");
			let costAmtTot 		= new Array();

            let iSCostAmtTot   = 0;
            
			let i=0;
			let j=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				costAmtTot[i] =0;
			}

			i=0;			
 			if (sMSCostAmt.length > 0) {
 				sMSCostAmt.forEach(function(node){


	    			for(j=0; j< 12; j++){
						if(j < 9)  jTemp ="0"+(j+1);
						else jTemp = j+1;

						if($('[name=sMSCostAmt'+jTemp+']')[i].innerHTML!="")
						costAmtTot[j] = costAmtTot[j] + getNumber(removeComma($('[name=sMSCostAmt'+jTemp+']')[i].innerHTML));
					}

	    			iSCostAmtTot = 0;
	    			for(j=1; j< 13; j++){
						if(j < 10)  jTemp ="0"+j;
						else jTemp = j;
		    
						if($('[name=sMSCostAmt'+jTemp+']')[i].innerHTML!="")
						iSCostAmtTot  = iSCostAmtTot+getNumber(removeComma($('[name=sMSCostAmt'+jTemp+']')[i].innerHTML))
					}
	    			$('[name=sMSCostMenuTot]')[i].innerHTML = iSCostAmtTot+"";

	        		i++;
	  	      });
	         }	

			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
				 document.getElementById('sMSCostAmt'+iTemp+'Tot').innerHTML = setComma(costAmtTot[i]+"");
			}

			document.getElementById('monthSCostAmt').innerHTML = setComma(costAmtTot[0]+"");

			
		 }
		 //판매관리비  연계산
		 function calYSCost() {

			let YSCostAmtYear= document.querySelectorAll("[name=sYSCostAmtYear1]");
			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let menuIndex=0;
 			if (YSCostAmtYear.length > 0) {
 				YSCostAmtYear.forEach(function(node){

		        	if(node.innerHTML!="") 
			        	menuQty01Tot = menuQty01Tot + getNumber(removeComma(node.innerHTML));

		        	if($('[name=sYSCostAmtYear2]')[menuIndex].innerHTML!="")
	        			menuQty02Tot = menuQty02Tot + getNumber(removeComma($('[name=sYSCostAmtYear2]')[menuIndex].innerHTML));

		        	if($('[name=sYSCostAmtYear3]')[menuIndex].innerHTML!="")
		        	menuQty03Tot = menuQty03Tot + getNumber(removeComma($('[name=sYSCostAmtYear3]')[menuIndex].innerHTML));

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sYSCostAmt01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sYSCostAmt02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sYSCostAmt03Tot').innerHTML = setComma(menuQty03Tot+"");

			 document.getElementById('yearSCostAmt').innerHTML = setComma(menuQty01Tot+"");

		 }

	     //손익게산서 계산하기			
		 function setInComes() {
			console.log("******************setInComes()*****************"); 
				 
			//매출금액
			let iInCome1Year1 = 0;
			let iInCome1Year2 = 0;
			let iInCome1Year3 = 0;

			iInCome1Year1 = getNumber(removeComma(document.getElementById('sYmenuAmt01Tot').innerHTML));
			iInCome1Year2 = getNumber(removeComma(document.getElementById('sYmenuAmt02Tot').innerHTML));
			iInCome1Year3 = getNumber(removeComma(document.getElementById('sYmenuAmt03Tot').innerHTML));

			//매출액 셋팅
			document.getElementById('sInCome1Year1').innerHTML = setComma(iInCome1Year1+"");
			document.getElementById('sInCome1Year2').innerHTML = setComma(iInCome1Year2+"");
			document.getElementById('sInCome1Year3').innerHTML = setComma(iInCome1Year3+"");

			//매출원가 
			let iInCome2Year1 = 0;
			let iInCome2Year2 = 0;
			let iInCome2Year3 = 0;

			iInCome2Year1 = getNumber(removeComma(document.getElementById('sYCostAmt01Tot').innerHTML));
			iInCome2Year2 = getNumber(removeComma(document.getElementById('sYCostAmt02Tot').innerHTML));
			iInCome2Year3 = getNumber(removeComma(document.getElementById('sYCostAmt03Tot').innerHTML));

			//매출원가 셋팅
			document.getElementById('sInCome2Year1').innerHTML = setComma(iInCome2Year1+"");
			document.getElementById('sInCome2Year2').innerHTML = setComma(iInCome2Year2+"");
			document.getElementById('sInCome2Year3').innerHTML = setComma(iInCome2Year3+"");

			//매출총이익
			let iInCome3Year1 = 0;
			let iInCome3Year2 = 0;
			let iInCome3Year3 = 0;

			iInCome3Year1 = iInCome1Year1-iInCome2Year1;
			iInCome3Year2 = iInCome1Year2-iInCome2Year2;
			iInCome3Year3 = iInCome1Year3-iInCome2Year3;
			
			document.getElementById('sInCome3Year1').innerHTML = setComma(iInCome3Year1+"");
			document.getElementById('sInCome3Year2').innerHTML = setComma(iInCome3Year2+"");
			document.getElementById('sInCome3Year3').innerHTML = setComma(iInCome3Year3+"");

			//매출총이익율
			let iInCome4Year1 = 0;
			let iInCome4Year2 = 0;
			let iInCome4Year3 = 0;

			iInCome4Year1 = getCalRate(iInCome3Year1,iInCome1Year1,1);
			iInCome4Year2 = getCalRate(iInCome3Year2,iInCome1Year2,1);
			iInCome4Year3 = getCalRate(iInCome3Year3,iInCome1Year3,1);
			
			document.getElementById('sInCome4Year1').innerHTML = setComma(iInCome4Year1+"");
			document.getElementById('sInCome4Year2').innerHTML = setComma(iInCome4Year2+"");
			document.getElementById('sInCome4Year3').innerHTML = setComma(iInCome4Year3+"");
			
			//판매관리비 
			let iInCome5Year1 = 0;
			let iInCome5Year2 = 0;
			let iInCome5Year3 = 0;

			iInCome5Year1 = getNumber(removeComma(document.getElementById('sYSCostAmt01Tot').innerHTML));
			iInCome5Year2 = getNumber(removeComma(document.getElementById('sYSCostAmt02Tot').innerHTML));
			iInCome5Year3 = getNumber(removeComma(document.getElementById('sYSCostAmt03Tot').innerHTML));

			//판매관리비 셋팅
			document.getElementById('sInCome5Year1').innerHTML = setComma(iInCome5Year1+"");
			document.getElementById('sInCome5Year2').innerHTML = setComma(iInCome5Year2+"");
			document.getElementById('sInCome5Year3').innerHTML = setComma(iInCome5Year3+"");

			//영업이익
			let iInCome6Year1 = 0;
			let iInCome6Year2 = 0;
			let iInCome6Year3 = 0;

			iInCome6Year1 = iInCome3Year1-iInCome5Year1;
			iInCome6Year2 = iInCome3Year2-iInCome5Year2;
			iInCome6Year3 = iInCome3Year3-iInCome5Year3;
			
			document.getElementById('sInCome6Year1').innerHTML = setComma(iInCome6Year1+"");
			document.getElementById('sInCome6Year2').innerHTML = setComma(iInCome6Year2+"");
			document.getElementById('sInCome6Year3').innerHTML = setComma(iInCome6Year3+"");

			//매출액 영업이익율
			let iInCome7Year1 = 0;
			let iInCome7Year2 = 0;
			let iInCome7Year3 = 0;

			iInCome7Year1 = getCalRate(iInCome6Year1,iInCome1Year1,1);
			iInCome7Year2 = getCalRate(iInCome6Year2,iInCome1Year2,1);
			iInCome7Year3 = getCalRate(iInCome6Year3,iInCome1Year3,1);
			
			document.getElementById('sInCome7Year1').innerHTML = setComma(iInCome7Year1+"");
			document.getElementById('sInCome7Year2').innerHTML = setComma(iInCome7Year2+"");
			document.getElementById('sInCome7Year3').innerHTML = setComma(iInCome7Year3+"");


			//영업외 비용 
			let iInCome8Year1 = 0;
			let iInCome8Year2 = 0;
			let iInCome8Year3 = 0;

			iInCome8Year1 = getNumber(removeComma(document.getElementById('dLoan02TotYearRate1').innerHTML));
			iInCome8Year2 = getNumber(removeComma(document.getElementById('dLoan02TotYearRate2').innerHTML));
			iInCome8Year3 = getNumber(removeComma(document.getElementById('dLoan02TotYearRate3').innerHTML));

			//영업외비용 셋팅
			document.getElementById('sInCome8Year1').innerHTML = setComma(iInCome8Year1+"");
			document.getElementById('sInCome8Year2').innerHTML = setComma(iInCome8Year2+"");
			document.getElementById('sInCome8Year3').innerHTML = setComma(iInCome8Year3+"");
			

			//경상이익
			let iInCome9Year1 = 0;
			let iInCome9Year2 = 0;
			let iInCome9Year3 = 0;

			iInCome9Year1 = iInCome6Year1-iInCome8Year1;
			iInCome9Year2 = iInCome6Year2-iInCome8Year2;
			iInCome9Year3 = iInCome6Year3-iInCome8Year3;
			
			document.getElementById('sInCome9Year1').innerHTML = setComma(iInCome9Year1+"");
			document.getElementById('sInCome9Year2').innerHTML = setComma(iInCome9Year2+"");
			document.getElementById('sInCome9Year3').innerHTML = setComma(iInCome9Year3+"");

			//매출액 경상이익율
			let iInCome10Year1 = 0;
			let iInCome10Year2 = 0;
			let iInCome10Year3 = 0;

			iInCome10Year1 = getCalRate(iInCome9Year1,iInCome1Year1,1);
			iInCome10Year2 = getCalRate(iInCome9Year2,iInCome1Year2,1);
			iInCome10Year3 = getCalRate(iInCome9Year3,iInCome1Year3,1);
			
			document.getElementById('sInCome10Year1').innerHTML = setComma(iInCome10Year1+"");
			document.getElementById('sInCome10Year2').innerHTML = setComma(iInCome10Year2+"");
			document.getElementById('sInCome10Year3').innerHTML = setComma(iInCome10Year3+"");
			
	     }

	     //재무상태 계산하기			
		 function setFinancial() {
			console.log("******************setFinancial()*****************"); 

			//3.비유동부채
			let iFinancial3Year1 = 0;
			let iFinancial3Year2 = 0;
			let iFinancial3Year3 = 0;

			iFinancial3Year1 = getNumber(removeComma(document.getElementById('dLoan01TotYear1').innerHTML));
			iFinancial3Year2 = getNumber(removeComma(document.getElementById('dLoan01TotYear2').innerHTML));
			iFinancial3Year3 = getNumber(removeComma(document.getElementById('dLoan01TotYear3').innerHTML));
			
			document.getElementById('sFinancial3Year1').innerHTML = setComma(iFinancial3Year1+"");
			document.getElementById('sFinancial3Year2').innerHTML = setComma(iFinancial3Year2+"");
			document.getElementById('sFinancial3Year3').innerHTML = setComma(iFinancial3Year3+"");

			//4.자본금
			let iFinancial4Year1 = 0;
			let iFinancial4Year2 = 0;
			let iFinancial4Year3 = 0;

			iFinancial4Year1 = getNumber(removeComma(document.getElementById('dLoan02TotYear1').innerHTML));
			iFinancial4Year2 = getNumber(removeComma(document.getElementById('dLoan02TotYear2').innerHTML));
			iFinancial4Year3 = getNumber(removeComma(document.getElementById('dLoan02TotYear3').innerHTML));
			
			document.getElementById('sFinancial4Year1').innerHTML = setComma(iFinancial4Year1+"");
			document.getElementById('sFinancial4Year2').innerHTML = setComma(iFinancial4Year2+"");
			document.getElementById('sFinancial4Year3').innerHTML = setComma(iFinancial4Year3+"");
			
			
			//5.이익잉여금
			let iFinancial5Year1 = 0;
			let iFinancial5Year2 = 0;
			let iFinancial5Year3 = 0;

			iFinancial5Year1 = getNumber(removeComma(document.getElementById('sInCome9Year1').innerHTML));
			iFinancial5Year2 = getNumber(removeComma(document.getElementById('sInCome9Year2').innerHTML));
			iFinancial5Year3 = getNumber(removeComma(document.getElementById('sInCome9Year3').innerHTML));
			
			document.getElementById('sFinancial5Year1').innerHTML = setComma(iFinancial5Year1+"");
			document.getElementById('sFinancial5Year2').innerHTML = setComma(iFinancial5Year2+"");
			document.getElementById('sFinancial5Year3').innerHTML = setComma(iFinancial5Year3+"");

			//부채와 자본총계
            let iFinancialT2Year1 = 0;
            let iFinancialT2Year2 = 0;
            let iFinancialT2Year3 = 0;

            let iFinancialT3Year1 = 0;
            let iFinancialT3Year2 = 0;
            let iFinancialT3Year3 = 0;

            iFinancialT2Year1 = iFinancial4Year1+iFinancial5Year1;
            iFinancialT2Year2 = iFinancial4Year2+iFinancial5Year2;
            iFinancialT2Year3 = iFinancial4Year3+iFinancial5Year3;

            iFinancialT3Year1 = iFinancialT2Year1 + iFinancial3Year1;
            iFinancialT3Year2 = iFinancialT2Year2 + iFinancial3Year2;
            iFinancialT3Year3 = iFinancialT2Year3 + iFinancial3Year3;
            
            
			document.getElementById('sFinancialT2Year1').innerHTML = setComma(iFinancialT2Year1+"");
			document.getElementById('sFinancialT2Year2').innerHTML = setComma(iFinancialT2Year2+"");
			document.getElementById('sFinancialT2Year3').innerHTML = setComma(iFinancialT2Year3+"");

			document.getElementById('sFinancialT3Year1').innerHTML = setComma(iFinancialT3Year1+"");
			document.getElementById('sFinancialT3Year2').innerHTML = setComma(iFinancialT3Year2+"");
			document.getElementById('sFinancialT3Year3').innerHTML = setComma(iFinancialT3Year3+"");

			//2.비유동자산(투자 판매부분합계- 제조감각상각-판매 감각상각)
			let iFinancial2Year1 = 0;
			let iFinancial2Year2 = 0;
			let iFinancial2Year3 = 0;

			let iInnvest02TotAmt  = 0;
			iInnvest02TotAmt = getNumber(removeComma(document.getElementById('innvest02TotAmt').innerHTML));
			let iMDepreciationYear1 = 0;
			let iMDepreciationYear2 = 0;
			let iMDepreciationYear3 = 0;

			let YMCostTitle = document.querySelectorAll("[name=sYMCostTitle]");
			let iIndex=0;
			if (YMCostTitle.length > 0) {
				YMCostTitle.forEach(function(node){
					if(node.innerHTML=="감가상각비"){
						iMDepreciationYear1 = getNumber(removeComma($('[name=sYMCostAmtYear1]')[iIndex].innerHTML));
						iMDepreciationYear2 = getNumber(removeComma($('[name=sYMCostAmtYear2]')[iIndex].innerHTML));
						iMDepreciationYear3 = getNumber(removeComma($('[name=sYMCostAmtYear3]')[iIndex].innerHTML));
					}
					iIndex++;
		        })
		        
			}

			

			let iSDepreciationYear1 = 0;
			let iSDepreciationYear2 = 0;
			let iSDepreciationYear3 = 0;

			let YSCostTitle = document.querySelectorAll("[name=sYSCostTitle]");
			iIndex=0;
			if (YSCostTitle.length > 0) {
				YSCostTitle.forEach(function(node){
					if(node.innerHTML=="감가상각비"){
						iYSCostAmtYear1 = getNumber(removeComma($('[name=sYSCostAmtYear1]')[iIndex].innerHTML));
						iYSCostAmtYear2 = getNumber(removeComma($('[name=sYSCostAmtYear2]')[iIndex].innerHTML));
						iYSCostAmtYear3 = getNumber(removeComma($('[name=sYSCostAmtYear3]')[iIndex].innerHTML));
					}
					iIndex++;
		        })
		        
			}

			
			iFinancial2Year1 = iInnvest02TotAmt - iMDepreciationYear1 - iSDepreciationYear1;
			iFinancial2Year2 = iInnvest02TotAmt - iMDepreciationYear2 - iSDepreciationYear2;
			iFinancial2Year3 = iInnvest02TotAmt - iMDepreciationYear3 - iSDepreciationYear3;
			
			document.getElementById('sFinancial2Year1').innerHTML = setComma(iFinancial2Year1+"");
			document.getElementById('sFinancial2Year2').innerHTML = setComma(iFinancial2Year2+"");
			document.getElementById('sFinancial2Year3').innerHTML = setComma(iFinancial2Year3+"");


			//1.유동자산
			let iFinancial1Year1 = 0;
			let iFinancial1Year2 = 0;
			let iFinancial1Year3 = 0;

			iFinancial1Year1 = iFinancialT3Year1 - iFinancial2Year1;
			iFinancial1Year2 = iFinancialT3Year2 - iFinancial2Year2;
			iFinancial1Year3 = iFinancialT3Year3 - iFinancial2Year2;
			
			document.getElementById('sFinancial1Year1').innerHTML = setComma(iFinancial1Year1+"");
			document.getElementById('sFinancial1Year2').innerHTML = setComma(iFinancial1Year2+"");
			document.getElementById('sFinancial1Year3').innerHTML = setComma(iFinancial1Year3+"");

			
			//자산총계
            let iFinancialT1Year1 = 0;
            let iFinancialT1Year2 = 0;
            let iFinancialT1Year3 = 0;

            iFinancialT1Year1 = iFinancial1Year1+iFinancial2Year1;
            iFinancialT1Year2 = iFinancial1Year2+iFinancial2Year2;
            iFinancialT1Year3 = iFinancial1Year3+iFinancial2Year3;

            
			document.getElementById('sFinancialT1Year1').innerHTML = setComma(iFinancialT1Year1+"");
			document.getElementById('sFinancialT1Year2').innerHTML = setComma(iFinancialT1Year2+"");
			document.getElementById('sFinancialT1Year3').innerHTML = setComma(iFinancialT1Year3+"");
			
	     }

	     //손익분기점 계산하기			
		 function setBreakPoint() {
			console.log("******************setBreakPoint()*****************"); 

			//1.매출액
			let iBreakPoint1Year1 = 0;
			let iBreakPoint1Year2 = 0;
			let iBreakPoint1Year3 = 0;

			iBreakPoint1Year1 = getNumber(removeComma(document.getElementById('sYmenuAmt01Tot').innerHTML));
			iBreakPoint1Year2 = getNumber(removeComma(document.getElementById('sYmenuAmt02Tot').innerHTML));
			iBreakPoint1Year3 = getNumber(removeComma(document.getElementById('sYmenuAmt03Tot').innerHTML));
			
			document.getElementById('sBreakPoint1Year1').innerHTML = setComma(iBreakPoint1Year1+"");
			document.getElementById('sBreakPoint1Year2').innerHTML = setComma(iBreakPoint1Year2+"");
			document.getElementById('sBreakPoint1Year3').innerHTML = setComma(iBreakPoint1Year3+"");

			//2.변동비 (재료비+제조경비+판매경비)
			let iBreakPoint2Year1 = 0;
			let iBreakPoint2Year2 = 0;
			let iBreakPoint2Year3 = 0;

			iBreakPoint2Year1 = getNumber(removeComma(document.getElementById('sYCostAmt01Tot').innerHTML))
						   	  + getNumber(removeComma(document.getElementById('sYMCostAmt01Tot').innerHTML))
							  + getNumber(removeComma(document.getElementById('sYSCostAmt01Tot').innerHTML))
							  ;

			iBreakPoint2Year2 = getNumber(removeComma(document.getElementById('sYCostAmt02Tot').innerHTML))
						   	  + getNumber(removeComma(document.getElementById('sYMCostAmt02Tot').innerHTML))
							  + getNumber(removeComma(document.getElementById('sYSCostAmt02Tot').innerHTML))
							  ;
			iBreakPoint2Year3 = getNumber(removeComma(document.getElementById('sYCostAmt03Tot').innerHTML))
						   	  + getNumber(removeComma(document.getElementById('sYMCostAmt03Tot').innerHTML))
							  + getNumber(removeComma(document.getElementById('sYSCostAmt03Tot').innerHTML))
							  ;
			
			document.getElementById('sBreakPoint2Year1').innerHTML = setComma(iBreakPoint2Year1+"");
			document.getElementById('sBreakPoint2Year2').innerHTML = setComma(iBreakPoint2Year2+"");
			document.getElementById('sBreakPoint2Year3').innerHTML = setComma(iBreakPoint2Year3+"");

			//3.한계이익
			let iBreakPoint3Year1 = 0;
			let iBreakPoint3Year2 = 0;
			let iBreakPoint3Year3 = 0;

			iBreakPoint3Year1 = iBreakPoint1Year1 - iBreakPoint2Year1;
			iBreakPoint3Year2 = iBreakPoint1Year2 - iBreakPoint2Year2;
			iBreakPoint3Year3 = iBreakPoint1Year3 - iBreakPoint2Year3;
			
			document.getElementById('sBreakPoint3Year1').innerHTML = setComma(iBreakPoint3Year1+"");
			document.getElementById('sBreakPoint3Year2').innerHTML = setComma(iBreakPoint3Year2+"");
			document.getElementById('sBreakPoint3Year3').innerHTML = setComma(iBreakPoint3Year3+"");

			//4.한계이익율
			let iBreakPoint4Year1 = 0;
			let iBreakPoint4Year2 = 0;
			let iBreakPoint4Year3 = 0;

			iBreakPoint4Year1 = getCalRate(iBreakPoint3Year1,iBreakPoint1Year1,1);
			iBreakPoint4Year2 = getCalRate(iBreakPoint3Year2,iBreakPoint1Year2,1);
			iBreakPoint4Year3 = getCalRate(iBreakPoint3Year3,iBreakPoint1Year1,1);
			
			document.getElementById('sBreakPoint4Year1').innerHTML = setComma(iBreakPoint4Year1+"");
			document.getElementById('sBreakPoint4Year2').innerHTML = setComma(iBreakPoint4Year2+"");
			document.getElementById('sBreakPoint4Year3').innerHTML = setComma(iBreakPoint4Year3+"");

			//5.고정비 (인건비+제조경비인건비+제조경비월정액+제조경비기준액+판매경비인건비+판매경비월정액+판매경비기준액)
			let iBreakPoint5Year1 = 0;
			let iBreakPoint5Year2 = 0;
			let iBreakPoint5Year3 = 0;

			//인건비
			let iBreakPoint5Year1Sub1 = 0;
			let iBreakPoint5Year2Sub1 = 0;
			let iBreakPoint5Year3Sub1 = 0;

			iBreakPoint5Year1Sub1 = getNumber(removeComma(document.getElementById('sYJobAmt01Tot').innerHTML));
			iBreakPoint5Year2Sub1 = getNumber(removeComma(document.getElementById('sYJobAmt01Tot').innerHTML));
			iBreakPoint5Year3Sub1 = getNumber(removeComma(document.getElementById('sYJobAmt01Tot').innerHTML));

			//제조경비 인건비,월정액,기준액
			let iBreakPoint5Year1Sub2 = 0;
			let iBreakPoint5Year2Sub2 = 0;
			let iBreakPoint5Year3Sub2 = 0;

			let iBreakPoint5Year1Sub3 = 0;
			let iBreakPoint5Year2Sub3 = 0;
			let iBreakPoint5Year3Sub3 = 0;

			let iBreakPoint5Year1Sub4 = 0;
			let iBreakPoint5Year2Sub4 = 0;
			let iBreakPoint5Year3Sub4 = 0;

			let YMCostTitle = document.querySelectorAll("[name=sYMCostCd]");
			let iIndex=0;
			let iYMCostAmtYear1=0;
			let iYMCostAmtYear2=0;
			let iYMCostAmtYear3=0;
			if (YMCostTitle.length > 0) {
				YMCostTitle.forEach(function(node){
					iYMCostAmtYear1 = getNumber(removeComma($('[name=sYMCostAmtYear1]')[iIndex].innerHTML));
					iYMCostAmtYear2 = getNumber(removeComma($('[name=sYMCostAmtYear2]')[iIndex].innerHTML));
					iYMCostAmtYear3 = getNumber(removeComma($('[name=sYMCostAmtYear3]')[iIndex].innerHTML));

					if(node.innerHTML=="인건비"){
						iBreakPoint5Year1Sub2 = iBreakPoint5Year1Sub2 + iYMCostAmtYear1;
						iBreakPoint5Year2Sub2 = iBreakPoint5Year2Sub2 + iYMCostAmtYear2;
						iBreakPoint5Year3Sub2 = iBreakPoint5Year3Sub2 + iYMCostAmtYear3;
					} else if(node.innerHTML=="월정액"){
						iBreakPoint5Year1Sub3 = iBreakPoint5Year1Sub3 + iYMCostAmtYear1;
						iBreakPoint5Year2Sub3 = iBreakPoint5Year2Sub3 + iYMCostAmtYear2;
						iBreakPoint5Year3Sub3 = iBreakPoint5Year3Sub3 + iYMCostAmtYear3;
					} else if(node.innerHTML=="기준액"){
						iBreakPoint5Year1Sub4 = iBreakPoint5Year1Sub4 + iYMCostAmtYear1;
						iBreakPoint5Year2Sub4 = iBreakPoint5Year2Sub4 + iYMCostAmtYear2;
						iBreakPoint5Year3Sub4 = iBreakPoint5Year3Sub4 + iYMCostAmtYear3;
					}	 
					iIndex++;
		        })
		        
			}

			

			let iBreakPoint5Year1Sub5 = 0;
			let iBreakPoint5Year2Sub5 = 0;
			let iBreakPoint5Year3Sub5 = 0;

			let iBreakPoint5Year1Sub6 = 0;
			let iBreakPoint5Year2Sub6 = 0;
			let iBreakPoint5Year3Sub6 = 0;

			let iBreakPoint5Year1Sub7 = 0;
			let iBreakPoint5Year2Sub7 = 0;
			let iBreakPoint5Year3Sub7 = 0;

			let YSCostTitle = document.querySelectorAll("[name=sYSCostCd]");
			iIndex=0;
			let iYSCostAmtYear1=0;
			let iYSCostAmtYear2=0;
			let iYSCostAmtYear3=0;
			if (YSCostTitle.length > 0) {
				YSCostTitle.forEach(function(node){
					iYSCostAmtYear1 = getNumber(removeComma($('[name=sYSCostAmtYear1]')[iIndex].innerHTML));
					iYSCostAmtYear2 = getNumber(removeComma($('[name=sYSCostAmtYear2]')[iIndex].innerHTML));
					iYSCostAmtYear3 = getNumber(removeComma($('[name=sYSCostAmtYear3]')[iIndex].innerHTML));

					if(node.innerHTML=="인건비"){
						iBreakPoint5Year1Sub5 = iBreakPoint5Year1Sub5 + iYSCostAmtYear1;
						iBreakPoint5Year2Sub5 = iBreakPoint5Year2Sub5 + iYSCostAmtYear2;
						iBreakPoint5Year3Sub5 = iBreakPoint5Year3Sub5 + iYSCostAmtYear3;
					} else if(node.innerHTML=="월정액"){
						iBreakPoint5Year1Sub6 = iBreakPoint5Year1Sub6 + iYSCostAmtYear1;
						iBreakPoint5Year2Sub6 = iBreakPoint5Year2Sub6 + iYSCostAmtYear2;
						iBreakPoint5Year3Sub6 = iBreakPoint5Year3Sub6 + iYSCostAmtYear3;
					} else if(node.innerHTML=="기준액"){
						iBreakPoint5Year1Sub7 = iBreakPoint5Year1Sub7 + iYSCostAmtYear1;
						iBreakPoint5Year2Sub7 = iBreakPoint5Year2Sub7 + iYSCostAmtYear2;
						iBreakPoint5Year3Sub7 = iBreakPoint5Year3Sub7 + iYSCostAmtYear3;
					}	 
					iIndex++;
		        })
		        
			}


			
			iBreakPoint5Year1 = iBreakPoint5Year1Sub1
			  + iBreakPoint5Year1Sub2
			  + iBreakPoint5Year1Sub3
			  + iBreakPoint5Year1Sub4
			  + iBreakPoint5Year1Sub5
			  + iBreakPoint5Year1Sub6
			  + iBreakPoint5Year1Sub7
			  ;
			iBreakPoint5Year2 = iBreakPoint5Year2Sub1
			  + iBreakPoint5Year2Sub2
			  + iBreakPoint5Year2Sub3
			  + iBreakPoint5Year2Sub4
			  + iBreakPoint5Year2Sub5
			  + iBreakPoint5Year2Sub6
			  + iBreakPoint5Year2Sub7
			  ;
			iBreakPoint5Year3 = iBreakPoint5Year3Sub1
			  + iBreakPoint5Year3Sub2
			  + iBreakPoint5Year3Sub3
			  + iBreakPoint5Year3Sub4
			  + iBreakPoint5Year3Sub5
			  + iBreakPoint5Year3Sub6
			  + iBreakPoint5Year3Sub7
			  ;

			
			document.getElementById('sBreakPoint5Year1').innerHTML = setComma(iBreakPoint5Year1+"");
			document.getElementById('sBreakPoint5Year2').innerHTML = setComma(iBreakPoint5Year2+"");
			document.getElementById('sBreakPoint5Year3').innerHTML = setComma(iBreakPoint5Year3+"");

			//6.손익분기점
			let iBreakPoint6Year1 = 0;
			let iBreakPoint6Year2 = 0;
			let iBreakPoint6Year3 = 0;

			iBreakPoint6Year1 = getCalRate(iBreakPoint5Year1,iBreakPoint4Year1,0);
			iBreakPoint6Year2 = getCalRate(iBreakPoint5Year2,iBreakPoint4Year2,0);
			iBreakPoint6Year3 = getCalRate(iBreakPoint5Year3,iBreakPoint4Year1,0);
			
			document.getElementById('sBreakPoint6Year1').innerHTML = setComma(iBreakPoint6Year1+"");
			document.getElementById('sBreakPoint6Year2').innerHTML = setComma(iBreakPoint6Year2+"");
			document.getElementById('sBreakPoint6Year3').innerHTML = setComma(iBreakPoint6Year3+"");

			//7.손익분기점 비율
			let iBreakPoint7Year1 = 0;
			let iBreakPoint7Year2 = 0;
			let iBreakPoint7Year3 = 0;

			iBreakPoint7Year1 = getCalRate(iBreakPoint6Year1,iBreakPoint1Year1,1);
			iBreakPoint7Year2 = getCalRate(iBreakPoint6Year2,iBreakPoint1Year2,1);
			iBreakPoint7Year3 = getCalRate(iBreakPoint6Year3,iBreakPoint1Year1,1);
			
			document.getElementById('sBreakPoint7Year1').innerHTML = setComma(iBreakPoint7Year1+"");
			document.getElementById('sBreakPoint7Year2').innerHTML = setComma(iBreakPoint7Year2+"");
			document.getElementById('sBreakPoint7Year3').innerHTML = setComma(iBreakPoint7Year3+"");

			//8.목표이익
			let iBreakPoint8Year1 = 0;
			let iBreakPoint8Year2 = 0;
			let iBreakPoint8Year3 = 0;

			let iInTargetPrice = 0;
			iInTargetPrice  = (getNumber(removeComma($('#inTargetPrice').val())))*10*12;
			
			iBreakPoint8Year1 = iInTargetPrice;
			iBreakPoint8Year2 = iInTargetPrice;
			iBreakPoint8Year3 = iInTargetPrice;
			
			document.getElementById('sBreakPoint8Year1').innerHTML = setComma(iBreakPoint8Year1+"");
			document.getElementById('sBreakPoint8Year2').innerHTML = setComma(iBreakPoint8Year2+"");
			document.getElementById('sBreakPoint8Year3').innerHTML = setComma(iBreakPoint8Year3+"");

			//9.목표매출액
			let iBreakPoint9Year1 = 0;
			let iBreakPoint9Year2 = 0;
			let iBreakPoint9Year3 = 0;

			
			iBreakPoint9Year1 = getCalRate((iBreakPoint5Year1 + iBreakPoint8Year1),iBreakPoint4Year1,0);
			iBreakPoint9Year2 = getCalRate((iBreakPoint5Year2 + iBreakPoint8Year2),iBreakPoint4Year2,0);
			iBreakPoint9Year3 = getCalRate((iBreakPoint5Year3 + iBreakPoint8Year3),iBreakPoint4Year3,0);
			
			document.getElementById('sBreakPoint9Year1').innerHTML = setComma(iBreakPoint9Year1+"");
			document.getElementById('sBreakPoint9Year2').innerHTML = setComma(iBreakPoint9Year2+"");
			document.getElementById('sBreakPoint9Year3').innerHTML = setComma(iBreakPoint9Year3+"");

			let iBreakPoint10Year1 = 0;
			let iBreakPoint10Year2 = 0;
			let iBreakPoint10Year3 = 0;

			
			iBreakPoint10Year1 = getCalRate(iBreakPoint9Year1,iBreakPoint1Year1,1);
			iBreakPoint10Year2 = getCalRate(iBreakPoint9Year2,iBreakPoint1Year2,1);
			iBreakPoint10Year3 = getCalRate(iBreakPoint9Year3,iBreakPoint1Year3,1);
			
			document.getElementById('sBreakPoint10Year1').innerHTML = setComma(iBreakPoint10Year1+"");
			document.getElementById('sBreakPoint10Year2').innerHTML = setComma(iBreakPoint10Year2+"");
			document.getElementById('sBreakPoint10Year3').innerHTML = setComma(iBreakPoint10Year3+"");
			
		  }
	     
	     //수익성 계산하기			
		 function setBizResult() {
			console.log("******************setBizResult*****************"); 
			//매출금액
			let iYmenuAmt01Tot = 0;
			let iYmenuAmt02Tot = 0;
			let iYmenuAmt03Tot = 0;


			iYmenuAmt01Tot = getNumber(removeComma(document.getElementById('sYmenuAmt01Tot').innerHTML));
			iYmenuAmt02Tot = getNumber(removeComma(document.getElementById('sYmenuAmt02Tot').innerHTML));
			iYmenuAmt03Tot = getNumber(removeComma(document.getElementById('sYmenuAmt03Tot').innerHTML));


			//매출액 셋팅
			document.getElementById('sBiz1C3Year1').innerHTML = setComma(iYmenuAmt01Tot+"");
			document.getElementById('sBiz1C3Year2').innerHTML = setComma(iYmenuAmt02Tot+"");
			document.getElementById('sBiz1C3Year3').innerHTML = setComma(iYmenuAmt03Tot+"");

			document.getElementById('sBiz2C3Year1').innerHTML = setComma(iYmenuAmt01Tot+"");
			document.getElementById('sBiz2C3Year2').innerHTML = setComma(iYmenuAmt02Tot+"");
			document.getElementById('sBiz2C3Year3').innerHTML = setComma(iYmenuAmt03Tot+"");
						
			document.getElementById('sBiz4C3Year1').innerHTML = setComma(iYmenuAmt01Tot+"");
			document.getElementById('sBiz4C3Year2').innerHTML = setComma(iYmenuAmt02Tot+"");
			document.getElementById('sBiz4C3Year3').innerHTML = setComma(iYmenuAmt03Tot+"");

			//1.투자금액 회전율
			let iBiz1C4Year1	= 0;
			let iBiz1C4Year2	= 0;
			let iBiz1C4Year3	= 0;

			iBiz1C4Year1 = getNumber(removeComma(document.getElementById('innvest01TotAmt').innerHTML))
						  +getNumber(removeComma(document.getElementById('innvest02TotAmt').innerHTML));

			iBiz1C4Year2 = iBiz1C4Year1;
			iBiz1C4Year3 = iBiz1C4Year1;
			
			document.getElementById('sBiz1C4Year1').innerHTML = setComma(iBiz1C4Year1+"");
			document.getElementById('sBiz1C4Year2').innerHTML = setComma(iBiz1C4Year2+"");
			document.getElementById('sBiz1C4Year3').innerHTML = setComma(iBiz1C4Year3+"");

			setBizRate(1,iYmenuAmt01Tot,iYmenuAmt01Tot,iYmenuAmt01Tot,iBiz1C4Year1,iBiz1C4Year1,iBiz1C4Year1,1);

			//2.매출액 영업이익율
			let iBiz2C4Year1	= 0;
			let iBiz2C4Year2	= 0;
			let iBiz2C4Year3	= 0;
			
			iBiz2C4Year1 = getNumber(removeComma(document.getElementById('sInCome6Year1').innerHTML));
			iBiz2C4Year2 = getNumber(removeComma(document.getElementById('sInCome6Year2').innerHTML));
			iBiz2C4Year3 = getNumber(removeComma(document.getElementById('sInCome6Year3').innerHTML));

			document.getElementById('sBiz2C4Year1').innerHTML = setComma(iBiz2C4Year1+"");
			document.getElementById('sBiz2C4Year2').innerHTML = setComma(iBiz2C4Year2+"");
			document.getElementById('sBiz2C4Year3').innerHTML = setComma(iBiz2C4Year3+"");

			
			setBizRate(2,iBiz2C4Year1,iBiz2C4Year2,iBiz2C4Year3,iYmenuAmt01Tot,iYmenuAmt02Tot,iYmenuAmt03Tot,1);

			//3.총자산  영업이익율
			let iBiz3C3Year1	= 0;
			let iBiz3C3Year2	= 0;
			let iBiz3C3Year3	= 0;
			let iBiz3C4Year1	= 0;
			let iBiz3C4Year2	= 0;
			let iBiz3C4Year3	= 0;
			
			iBiz3C3Year1 = iBiz1C4Year1;
			iBiz3C3Year2 = iBiz1C4Year2;
			iBiz3C3Year3 = iBiz1C4Year3;

			iBiz3C4Year1 = iBiz2C4Year1;
			iBiz3C4Year2 = iBiz2C4Year2;
			iBiz3C4Year3 = iBiz2C4Year3;

			document.getElementById('sBiz3C3Year1').innerHTML = setComma(iBiz3C3Year1+"");
			document.getElementById('sBiz3C3Year2').innerHTML = setComma(iBiz3C3Year2+"");
			document.getElementById('sBiz3C3Year3').innerHTML = setComma(iBiz3C3Year3+"");

			document.getElementById('sBiz3C4Year1').innerHTML = setComma(iBiz3C4Year1+"");
			document.getElementById('sBiz3C4Year2').innerHTML = setComma(iBiz3C4Year2+"");
			document.getElementById('sBiz3C4Year3').innerHTML = setComma(iBiz3C4Year3+"");

			
			setBizRate(3,iBiz3C4Year1,iBiz3C4Year2,iBiz3C4Year3,iBiz3C3Year1,iBiz3C3Year2,iBiz3C3Year3,1);
			
			
			//4.인건비 비율 
			//인건비 
			let iBiz4C3Year1	= iYmenuAmt01Tot;
			let iBiz4C3Year2	= iYmenuAmt02Tot;
			let iBiz4C3Year3	= iYmenuAmt03Tot;

			let iBiz4C4Year1	= 0;
			let iBiz4C4Year2	= 0;
			let iBiz4C4Year3	= 0;

			iBiz4C4Year1 = getNumber(removeComma(document.getElementById('sYJobAmt01Tot').innerHTML));
			iBiz4C4Year2 = getNumber(removeComma(document.getElementById('sYJobAmt02Tot').innerHTML));
			iBiz4C4Year3 = getNumber(removeComma(document.getElementById('sYJobAmt03Tot').innerHTML));

			
			document.getElementById('sBiz4C4Year1').innerHTML = setComma(iBiz4C4Year1+"");
			document.getElementById('sBiz4C4Year2').innerHTML = setComma(iBiz4C4Year2+"");
			document.getElementById('sBiz4C4Year3').innerHTML = setComma(iBiz4C4Year3+"");

			setBizRate(4,iBiz4C4Year1,iBiz4C4Year2,iBiz4C4Year3,iBiz4C3Year1,iBiz4C3Year2,iBiz4C3Year3,1);


			//5.임차율 비율
			let iYSCostAmtYear1	= 0;
			let iYSCostAmtYear2	= 0;
			let iYSCostAmtYear3	= 0;

			
			let YSCostTitle = document.querySelectorAll("[name=sYSCostTitle]");
			let iIndex=0;
			if (YSCostTitle.length > 0) {
				YSCostTitle.forEach(function(node){
					if(node.innerHTML=="지급임차료"){
						iYSCostAmtYear1 = getNumber(removeComma($('[name=sYSCostAmtYear1]')[iIndex].innerHTML));
						iYSCostAmtYear2 = getNumber(removeComma($('[name=sYSCostAmtYear2]')[iIndex].innerHTML));
						iYSCostAmtYear3 = getNumber(removeComma($('[name=sYSCostAmtYear3]')[iIndex].innerHTML));
					}
					iIndex++;
		        })
		        
			}

			let iBiz5C3Year1=0;
			let iBiz5C3Year2=0;
			let iBiz5C3Year3=0;
			iBiz5C3Year1=(iYmenuAmt01Tot/12);
			iBiz5C3Year2=(iYmenuAmt02Tot/12);
			iBiz5C3Year3=(iYmenuAmt03Tot/12);
			iBiz5C3Year1 = iBiz5C3Year1.toFixed(0);
			iBiz5C3Year2 = iBiz5C3Year2.toFixed(0);
			iBiz5C3Year3 = iBiz5C3Year3.toFixed(0);
			

			let iBiz5C4Year1=0;
			let iBiz5C4Year2=0;
			let iBiz5C4Year3=0;
			iBiz5C4Year1=(iYSCostAmtYear1/12);
			iBiz5C4Year2=(iYSCostAmtYear2/12);
			iBiz5C4Year3=(iYSCostAmtYear3/12);

			iBiz5C4Year1 = iBiz5C4Year1.toFixed(0);
			iBiz5C4Year2 = iBiz5C4Year2.toFixed(0);
			iBiz5C4Year3 = iBiz5C4Year3.toFixed(0);

			
			//월매출액
			document.getElementById('sBiz5C3Year1').innerHTML = setComma(iBiz5C3Year1+"");
			document.getElementById('sBiz5C3Year1').innerHTML = setComma(iBiz5C3Year2+"");
			document.getElementById('sBiz5C3Year1').innerHTML = setComma(iBiz5C3Year3+"");
			
			//월임차료
			document.getElementById('sBiz5C4Year1').innerHTML = setComma(iBiz5C4Year1+"");
			document.getElementById('sBiz5C4Year2').innerHTML = setComma(iBiz5C4Year2+"");
			document.getElementById('sBiz5C4Year3').innerHTML = setComma(iBiz5C4Year3+"");
          
			setBizRate(5,iBiz5C4Year1,iBiz5C4Year2,iBiz5C4Year3,iBiz5C3Year1,iBiz5C3Year2,iBiz5C3Year3,1);


			//6.자기자본 비율 
			let iBiz6C3Year1	= 0;
			let iBiz6C3Year2	= 0;
			let iBiz6C3Year3	= 0;

			let iBiz6C4Year1	= 0;
			let iBiz6C4Year2	= 0;
			let iBiz6C4Year3	= 0;

			iBiz6C3Year1 = getNumber(removeComma(document.getElementById('sFinancialT1Year1').innerHTML));
			iBiz6C3Year2 = getNumber(removeComma(document.getElementById('sFinancialT1Year2').innerHTML));
			iBiz6C3Year3 = getNumber(removeComma(document.getElementById('sFinancialT1Year3').innerHTML));

			iBiz6C4Year1 = getNumber(removeComma(document.getElementById('sFinancialT2Year1').innerHTML));
			iBiz6C4Year2 = getNumber(removeComma(document.getElementById('sFinancialT2Year2').innerHTML));
			iBiz6C4Year3 = getNumber(removeComma(document.getElementById('sFinancialT2Year3').innerHTML));
			

			document.getElementById('sBiz6C3Year1').innerHTML = setComma(iBiz6C3Year1+"");
			document.getElementById('sBiz6C3Year2').innerHTML = setComma(iBiz6C3Year2+"");
			document.getElementById('sBiz6C3Year3').innerHTML = setComma(iBiz6C3Year3+"");
			
			document.getElementById('sBiz6C4Year1').innerHTML = setComma(iBiz6C4Year1+"");
			document.getElementById('sBiz6C4Year2').innerHTML = setComma(iBiz6C4Year2+"");
			document.getElementById('sBiz6C4Year3').innerHTML = setComma(iBiz6C4Year3+"");

			setBizRate(6,iBiz6C4Year1,iBiz6C4Year2,iBiz6C4Year3,iBiz6C3Year1,iBiz6C3Year2,iBiz6C3Year3,1);

			//7.손익분기점 비율 
			let iBiz7C3Year1	= 0;
			let iBiz7C3Year2	= 0;
			let iBiz7C3Year3	= 0;

			let iBiz7C4Year1	= 0;
			let iBiz7C4Year2	= 0;
			let iBiz7C4Year3	= 0;

			iBiz7C3Year1 = iYmenuAmt01Tot;
			iBiz7C3Year2 = iYmenuAmt02Tot;
			iBiz7C3Year3 = iYmenuAmt03Tot;

			iBiz7C4Year1 = getNumber(removeComma(document.getElementById('sBreakPoint6Year1').innerHTML));
			iBiz7C4Year2 = getNumber(removeComma(document.getElementById('sBreakPoint6Year2').innerHTML));
			iBiz7C4Year3 = getNumber(removeComma(document.getElementById('sBreakPoint6Year3').innerHTML));
			

			document.getElementById('sBiz7C3Year1').innerHTML = setComma(iBiz7C3Year1+"");
			document.getElementById('sBiz7C3Year2').innerHTML = setComma(iBiz7C3Year2+"");
			document.getElementById('sBiz7C3Year3').innerHTML = setComma(iBiz7C3Year3+"");
			
			document.getElementById('sBiz7C4Year1').innerHTML = setComma(iBiz7C4Year1+"");
			document.getElementById('sBiz7C4Year2').innerHTML = setComma(iBiz7C4Year2+"");
			document.getElementById('sBiz7C4Year3').innerHTML = setComma(iBiz7C4Year3+"");

			setBizRate(7,iBiz7C4Year1,iBiz7C4Year2,iBiz7C4Year3,iBiz7C3Year1,iBiz7C3Year2,iBiz7C3Year3,1);
			
			
			//챠트계산
			/*
			chartDataYear1 = [50, 59, 90, iBiz4Rate01, 55, 40, 50];
			chartDataYear2 = [28, 48, 40, iBiz4Rate02, 96, 100, 50];
			chartDataYear3 = [50, 100, 27, iBiz4Rate03, 19, 40, 28];
			*/
				
			console.log("chartDataYear1",chartDataYear1); 
			console.log("chartDataYear2",chartDataYear2); 
			console.log("chartDataYear3",chartDataYear1); 

			myChart.data.datasets[0].data = chartDataYear1;
			myChart.data.datasets[1].data = chartDataYear2;
			myChart.data.datasets[2].data = chartDataYear3;
			
			myChart.update();
			
			
		 }

	     //사업적정  구하기			
		 function setBizRate(pBizcd,pBizC3Year1,pBizC3Year2,pBizC3Year3,pBizC4Year1,pBizC4Year2,pBizC4Year3,pPoint) {

			 
				let iBizRate01 = 0;
				let iBizRate02 = 0;
				let iBizRate03 = 0;

				if(pBizC4Year1>0) {
					iBizRate01	= pBizC3Year1/pBizC4Year1;
				}	
				if(pBizC4Year2>0) {
					iBizRate02	= pBizC3Year2/pBizC4Year2;
				}	
				if(pBizC4Year3>0) {
					iBizRate03	= pBizC3Year3/pBizC4Year3;
				}	

				
				if(pBizcd>1) {
					iBizRate01	= iBizRate01*100;
					iBizRate01  = iBizRate01.toFixed(pPoint);
					iBizRate02	= iBizRate02*100;
					iBizRate02  = iBizRate02.toFixed(pPoint);
					iBizRate03	= iBizRate03*100;
					iBizRate03  = iBizRate03.toFixed(pPoint);

					chartDataYear1[pBizcd-1] = iBizRate01;
					chartDataYear2[pBizcd-1] = iBizRate02;
					chartDataYear3[pBizcd-1] = iBizRate03;

					document.getElementById('sBiz'+pBizcd+'C2Year1').innerHTML = getBizTrue(pBizcd,iBizRate01/100);
					document.getElementById('sBiz'+pBizcd+'C2Year2').innerHTML = getBizTrue(pBizcd,iBizRate02/100);
					document.getElementById('sBiz'+pBizcd+'C2Year3').innerHTML = getBizTrue(pBizcd,iBizRate03/100);
						

				} else{	 //투자금액회전율
					iBizRate01  = iBizRate01.toFixed(pPoint);
					iBizRate02  = iBizRate02.toFixed(pPoint);
					iBizRate03  = iBizRate03.toFixed(pPoint);

					chartDataYear1[pBizcd-1] = (iBizRate01*20).toFixed(pPoint);
					chartDataYear2[pBizcd-1] = (iBizRate02*20).toFixed(pPoint);
					chartDataYear3[pBizcd-1] = (iBizRate03*20).toFixed(pPoint);

					document.getElementById('sBiz'+pBizcd+'C2Year1').innerHTML = getBizTrue(pBizcd,iBizRate01);
					document.getElementById('sBiz'+pBizcd+'C2Year2').innerHTML = getBizTrue(pBizcd,iBizRate02);
					document.getElementById('sBiz'+pBizcd+'C2Year3').innerHTML = getBizTrue(pBizcd,iBizRate03);
				}
				

				
				document.getElementById('sBiz'+pBizcd+'C1Year1').innerHTML = iBizRate01+"";
				document.getElementById('sBiz'+pBizcd+'C1Year2').innerHTML = iBizRate02+"";
				document.getElementById('sBiz'+pBizcd+'C1Year3').innerHTML = iBizRate03+"";


		 }

	     
	     //사업적정  구하기			
		 function getBizTrue(pBizcd, pRate) {
			  //업종구분
		      let sector_se_code_m = document.getElementById('sector_se_code_m');        
		      let workCd 		   = sector_se_code_m.options[sector_se_code_m.selectedIndex].value;
	    	  let sReturn 		   = "?";

	    	  if(pBizcd == 1) {
					if(workCd == "1") {
	                  if (pRate >= 1.5) sReturn="적정";
					}else if(workCd == "2") {
		              if (pRate >= 2.7) sReturn="적정";
					}else if(workCd == "3") {
			          if (pRate >= 1.8) sReturn="적정";
					}else if(workCd == "4") {
					  if (pRate >= 0.9) sReturn="적정";
					}else if(workCd == "5") {
					  if (pRate >= 1.4) sReturn="적정";
					}
			  }

	    	  if(pBizcd == 2) {
					if(workCd == "1") {
	                  if (pRate >= 0.1) sReturn="적정";
					}else if(workCd == "2") {
		              if (pRate >= 0.108) sReturn="적정";
					}else if(workCd == "3") {
			          if (pRate >= 0.162) sReturn="적정";
					}else if(workCd == "4") {
					  if (pRate >= 0.324) sReturn="적정";
					}else if(workCd == "5") {
					  if (pRate >= 0.218) sReturn="적정";
					}
			  }
	    	  if(pBizcd == 3) {
					if(workCd == "1") {
	                  if (pRate >= 0.02) sReturn="적정";
					}else if(workCd == "2") {
		              if (pRate >= 0.027) sReturn="적정";
					}else if(workCd == "3") {
			          if (pRate >= 0.027) sReturn="적정";
					}else if(workCd == "4") {
					  console.log("pRate:",pRate);
					  if (pRate >= 0.027) sReturn="적정";
					}else if(workCd == "5") {
					  if (pRate >= 0.027) sReturn="적정";
					}
			  }
	    	  
		      if(pBizcd == 4) {
				if(workCd == "1") {
                  if (pRate <= 0.15) sReturn="적정";
				}else if(workCd == "2") {
	              if (pRate <= 0.088) sReturn="적정";
				}else if(workCd == "3") {
		          if (pRate <= 0.15) sReturn="적정";
				}else if(workCd == "4") {
				  console.log("pRate:",pRate);
				  if (pRate <= 0.275) sReturn="적정";
				}else if(workCd == "5") {
				  if (pRate <= 0.44) sReturn="적정";
				}
			  }

		     if(pBizcd == 5) {
				if(workCd == "1") {
                  if (pRate <= 0.05) sReturn="적정";
				}else if(workCd == "2") {
	              if (pRate <= 0.088) sReturn="적정";
				}else if(workCd == "3") {
		          if (pRate <= 0.11) sReturn="적정";
				}else if(workCd == "4") {
				  console.log("pRate:",pRate);
				  if (pRate <= 0.165) sReturn="적정";
				}else if(workCd == "5") {
				  if (pRate <= 0.11) sReturn="적정";
				}
			 }

		     if(pBizcd == 6) {
                 if (pRate >= 0.5) sReturn="적정";
			 }

		     if(pBizcd == 7) {
				if(workCd == "1") {
                  if (pRate <= 0.66) sReturn="적정";
				}else if(workCd == "2") {
	              if (pRate <= 0.66) sReturn="적정";
				}else if(workCd == "3") {
		          if (pRate <= 0.736) sReturn="적정";
				}else if(workCd == "4") {
				  console.log("pRate:",pRate);
				  if (pRate <= 0.66) sReturn="적정";
				}else if(workCd == "5") {
				  if (pRate <= 0.80) sReturn="적정";
				}
			  }
			     
			 
			 return sReturn;
	     }
	     
	     //메뉴 클릭			
		 function selectMenu(pIndex) {
 			let menuLi = document.querySelectorAll(".menunav > li");
			console.log(menuLi.length);

 			if (menuLi.length > 0) {
 	 			let i = 0;
 	 			menuLi.forEach(function(node){
 	 	 			if(i == pIndex){
						node.classList="active";
 	 	 			}else{
						node.classList="";
 	 	 	 		}
 	 	 			i++;
 	 			});
	         }

 			if(pIndex==0){
   				$("#diDash").show();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==1){
   				$("#diDash").hide();
   				$("#diSale").show();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==2){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").show();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==3){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").show();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==4){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").show();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==5){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").show();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==6){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").show();
   				$("#diSCost").hide();
   				$("#diPlan").hide();
 			}else if(pIndex==7){
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").show();
   				$("#diPlan").hide();
 	 	 	}else {
   				$("#diDash").hide();
   				$("#diSale").hide();
   				$("#diCost").hide();
   				$("#diJob").hide();
   				$("#diInvest").hide();
   				$("#diLoan").hide();
   				$("#diMCost").hide();
   				$("#diSCost").hide();
   				$("#diPlan").show();
   				setInComes();
   				setFinancial();
   				setBreakPoint();
   				setBizResult();
 	 	 	 }

	     }

	     //업종가이드 보이기
	     $('#btnHelpBizCdS').on('click', function(e) {
			$('#dBtnHelpBizCdS').show();
		 });

	     //업종가이드 숨기기
	     $('#btnHelpBizCdH').on('click', function(e) {
			$('#dBtnHelpBizCdS').hide();
		 });

	     //투자계획 보이기
	     $('#btnHelpInvestCdS').on('click', function(e) {
			$('#dBtnHelpInvestCd').show();
		 });

	     //투자계획 숨기기
	     $('#btnHelpInvestCdH').on('click', function(e) {
			$('#dBtnHelpInvestCd').hide();
		 });
	     
	     document.getElementById('btnPopMenuSave').addEventListener('click',function(e){
				console.log("*****btnPopMenuSave*******");
				if (document.getElementById('inputPlanTitle').value == "") {
					alert("수익분석 타이틀을 입력하세요");
					 document.getElementById("inputPlanTitle").focus();
					return;
				}
				fnSaveMenu();
				$('#mySaveModal').modal('toggle');
		 });
	     
	     
		function fnSaveMenu(){
			if(confirm("등록하시겠습니까?")){
		         let day_se_code_m = document.getElementById('day_se_code_m');        
				 workDay = getNumber(day_se_code_m.options[day_se_code_m.selectedIndex].value);
				 
				 fnMakePlanFSale();
				 
				 var sUrl 	= "/front/savePlanFAjax.do";
				 var sParam ={'PLANF_ID'			: $('#PLANF_ID').val()
						     ,'PLANF_TITLE' 		: $('#inputPlanTitle').val()
						 	 ,'INDUSTRY_CD' 		: industryCd
					         ,'WORK_DAY_CNT' 		: workDay
						     ,'PLANF_SALE' 		    : $('#PLANF_SALE').val()
					         ,'MON_TARGET_PROFIT' 	: $('#inTargetPrice').val()
						 	 };
				 var sType 	= "post";

				 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
							console.log(pResponse);
					 	var results=pResponse.resultStats.resultList;
						//alert(pResponse.resultStats.resultMsg);
					 	if(pResponse.resultStats.resultCode=="ok"){
	                		alert("저장 되었습니다.");
					 	}else{
	                		alert("처리중 오류가 생겼습니다 \N 저장버튼을 다시 클릭하세요.");
						}
				 });
				 
			}
		}
		
		function fnMakePlanFSale(){
			let planfJson 	= new Object();
			let planfSALE 	= new Object();
			let planfCOST 	= new Object();
			let planfJOB   	= new Object();
			let planfINVEST	= new Object();
			let planfLOAN  	= new Object();
			let planfMCOST 	= new Object();
			let planfSCOST 	= new Object();
			
			//매출
			let menuArr 	  = new Array();
			let aMenu;

			let menuMArr 	  = new Array();
			let MMenuQtyArr;
			let MMenuAmtArr;
			let aMenuM;

			let menuYArr 	  = new Array();
			let YMenuQtyArr;
			let YMenuPriceArr;
			let YMenuAmtArr;
			let aMenuY;

			//재료비
			let costArr 	  = new Array();
			let aCost;

			let costMArr 	  = new Array();
			let MCostAmtArr;
			let aCostM;
			
			let costYArr 	  = new Array();
			let YCostAmtArr;
			let aCostY;

			
			
			//인건비
			let job01Arr 	  = new Array();
			let job02Arr 	  = new Array();
			let aJob01;
			let aJob02;

			let job01MArr 	  = new Array();
			let job02MArr 	  = new Array();
			let MJobAmtArr;
			let ajob01M;
			let ajob02M;
			
			let job01YArr 	  = new Array();
			let job02YArr 	  = new Array();
			let YjobAmtArr;
			let aJob01Y;
			let aJob02Y;

			//투자계획
			let invest01Arr 	  = new Array();
			let invest02Arr 	  = new Array();
			let invest03Arr 	  = new Array();
			let invest04Arr 	  = new Array();
			let invest05Arr 	  = new Array();
			let invest06Arr 	  = new Array();
			let invest07Arr 	  = new Array();
			let invest08Arr 	  = new Array();
			let aInvest01;
			let aInvest02;
			let aInvest03;
			let aInvest04;
			let aInvest05;
			let aInvest06;
			let aInvest07;
			let aInvest08;

			//자금
			let loan01Arr 	  = new Array();
			let loan02Arr 	  = new Array();
			let aLoan01;
			let aLoan02;
			let loanYearArr;
			let loanRateArr;

			//제조경비
			let MCostArr 	  = new Array();
			let aMCost;

			let MCostMArr 	  = new Array();
			let MMCostAmtArr;
			let aMCostM;
			
			let MCostYArr 	  = new Array();
			let YMCostAmtArr;
			let aMCostY;

			//판매경비
			let SCostArr 	  = new Array();
			let aSCost;

			let SCostMArr 	  = new Array();
			let MSCostAmtArr;
			let aSCostM;
			
			let SCostYArr 	  = new Array();
			let YSCostAmtArr;
			let aSCostY;

			
			
			let dmenuTitle 	 = document.querySelectorAll("[name=dmenuTitle]");

			//매출생성
			let iIndex=0;
 			if (dmenuTitle.length > 0) {
 				dmenuTitle.forEach(function(node){
 	 				aMenu = new Object();
 	 				aMenuM = new Object();
 	 				aMenuY = new Object();
 					aMenu.MenuTitle = node.innerHTML;
 					aMenu.MenuPrice = removeComma($('[name=dmenuPrice]')[iIndex].innerHTML); 
 					aMenu.MenuQty   = removeComma($('[name=dmenuQty]')[iIndex].innerHTML); 
 					menuArr.push(aMenu);
 					
 					MMenuQtyArr = new Array();
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty01]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty02]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty03]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty04]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty05]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty06]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty07]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty08]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty09]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty10]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty11]')[iIndex].innerHTML));
 					MMenuQtyArr.push(removeComma($('[name=sMmenuQty12]')[iIndex].innerHTML));

 					MMenuAmtArr = new Array();
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt01]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt02]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt03]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt04]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt05]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt06]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt07]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt08]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt09]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt10]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt11]')[iIndex].innerHTML));
 					MMenuAmtArr.push(removeComma($('[name=sMmenuAmt12]')[iIndex].innerHTML));
 					
 					aMenuM.MMenuQty = MMenuQtyArr;  
 					aMenuM.MMenuAmt = MMenuAmtArr;
 					
 					menuMArr.push(aMenuM);
 					
 					YMenuQtyArr = new Array();
					YMenuQtyArr.push(removeComma($('[name=sYMenuQtyYear1]')[iIndex].innerHTML));
					YMenuQtyArr.push(removeComma($('[name=sYMenuQtyYear2]')[iIndex].innerHTML));
					YMenuQtyArr.push(removeComma($('[name=sYMenuQtyYear3]')[iIndex].innerHTML));

					
					YMenuPriceArr = new Array();
					YMenuPriceArr.push(removeComma($('[name=sYMenuPriceYear1]')[iIndex].innerHTML));
					YMenuPriceArr.push(removeComma($('[name=sYMenuPriceYear2]')[iIndex].innerHTML));
					YMenuPriceArr.push(removeComma($('[name=sYMenuPriceYear3]')[iIndex].innerHTML));
					
					
 					YMenuAmtArr = new Array();
					YMenuAmtArr.push(removeComma($('[name=sYMenuAmtYear1]')[iIndex].innerHTML));
 					YMenuAmtArr.push(removeComma($('[name=sYMenuAmtYear2]')[iIndex].innerHTML));
 					YMenuAmtArr.push(removeComma($('[name=sYMenuAmtYear3]')[iIndex].innerHTML));

 					aMenuY.YMenuQty = YMenuQtyArr;
 					aMenuY.YMenuPrice = YMenuPriceArr;
 					aMenuY.YMenuAmt = YMenuAmtArr;
 					aMenuY.YMenuCd  = $('[name=sYmenuCd]')[iIndex].innerHTML;
 					aMenuY.YMenuQtyRate = $('[name=sYMenuQtyRate]')[iIndex].innerHTML;
 					aMenuY.YMenuPriceRate = $('[name=sYMenuPriceRate]')[iIndex].innerHTML;
 					
 					
 					menuYArr.push(aMenuY);
 					iIndex++;
	  	      });
	         }	
 			
 			planfSALE.menu  = menuArr;
 			planfSALE.menuM = menuMArr;
 			planfSALE.menuY = menuYArr;
 			
 			//재료비 생성
			let icostRate 	 = document.querySelectorAll("[name=icostRate]");
			iIndex=0;
 			if (icostRate.length > 0) {
 				icostRate.forEach(function(node){
 	 				aCost = new Object();
 	 				aCostM = new Object();
 	 				aCostY = new Object();

 					aCost.costRate = node.value;
	 				costArr.push(aCost);
	 				
	 				MCostAmtArr = new Array();
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt01]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt02]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt03]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt04]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt05]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt06]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt07]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt08]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt09]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt10]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt11]')[iIndex].innerHTML));
	 				MCostAmtArr.push(removeComma($('[name=sMCostAmt12]')[iIndex].innerHTML));

	 				aCostM.MCostAmt = MCostAmtArr;
	 				costMArr.push(aCostM);

	 				
	 				YCostAmtArr = new Array();
	 				YCostAmtArr.push(removeComma($('[name=sYCostAmtYear1]')[iIndex].innerHTML));
	 				YCostAmtArr.push(removeComma($('[name=sYCostAmtYear2]')[iIndex].innerHTML));
	 				YCostAmtArr.push(removeComma($('[name=sYCostAmtYear3]')[iIndex].innerHTML));

	 				aCostY.YCostAmt = YCostAmtArr;
	 				aCostY.YCostCd = $('[name=sYCostCd]')[iIndex].innerHTML;
	 				aCostY.YCostAmtRate = $('[name=sYCostAmtRate]')[iIndex].innerHTML;
	 				
	 				costYArr.push(aCostY);
	 				

	 				iIndex++;
 		  	      });
 			}
			planfCOST.cost  = costArr;
			planfCOST.costM = costMArr;
			planfCOST.costY = costYArr;


			//인건비 생성
			let sJob01Title 	 = document.querySelectorAll("[name=sJob01Title]");
			iIndex=0;
 			if (sJob01Title.length > 0) {
 				sJob01Title.forEach(function(node){
 	 				aJob01  = new Object();
 	 				aJob01M = new Object();
 	 				aJob01Y = new Object();

 	 				aJob01.JobTitle = node.innerHTML;
 	 				aJob01.JobCnt = removeComma($('[name=sJob01Cnt]')[iIndex].innerHTML);
 	 				aJob01.JobQty = removeComma($('[name=sJob01Qty]')[iIndex].innerHTML);
 	 				
	 				job01Arr.push(aJob01);
	 				
	 				MJobAmtArr = new Array();
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt01]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt02]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt03]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt04]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt05]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt06]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt07]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt08]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt09]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt10]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt11]')[iIndex].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt12]')[iIndex].innerHTML));

	 				aJob01M.MJobAmt = MJobAmtArr;
	 				job01MArr.push(aJob01M);

	 				
	 				YjobAmtArr = new Array();
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear1]')[iIndex].innerHTML));
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear2]')[iIndex].innerHTML));
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear3]')[iIndex].innerHTML));

	 				aJob01Y.YJobAmt = YjobAmtArr;
	 				aJob01Y.YJobAmtCd = $('[name=sYJobAmtCd]')[iIndex].innerHTML;
	 				aJob01Y.YJobAmtRate = $('[name=sYJobAmtRate]')[iIndex].innerHTML;
	 				
	 				job01YArr.push(aJob01Y);
	 				

	 				iIndex++;
 		  	      });
 			}

			let sJob02Title 	 = document.querySelectorAll("[name=sJob02Title]");
			let sJob02index      = getNumber(sJob01Title.length);; 

			iIndex=0;
 			if (sJob02Title.length > 0) {
 				sJob02Title.forEach(function(node){
 	 				aJob02  = new Object();
 	 				aJob02M = new Object();
 	 				aJob02Y = new Object();

 	 				aJob02.JobTitle = node.innerHTML;
 	 				aJob02.JobCnt = removeComma($('[name=sJob02Cnt]')[iIndex].innerHTML);
 	 				aJob02.JobQty = removeComma($('[name=sJob02Qty]')[iIndex].innerHTML);
 	 				
	 				job02Arr.push(aJob02);
	 				
	 				MJobAmtArr = new Array();
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt01]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt02]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt03]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt04]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt05]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt06]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt07]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt08]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt09]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt10]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt11]')[iIndex+sJob02index].innerHTML));
	 				MJobAmtArr.push(removeComma($('[name=sMJobAmt12]')[iIndex+sJob02index].innerHTML));

	 				aJob02M.MJobAmt = MJobAmtArr;
	 				job02MArr.push(aJob02M);

	 				
	 				YjobAmtArr = new Array();
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear1]')[iIndex+sJob02index].innerHTML));
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear2]')[iIndex+sJob02index].innerHTML));
	 				YjobAmtArr.push(removeComma($('[name=sYJobAmtYear3]')[iIndex+sJob02index].innerHTML));

	 				aJob02Y.YJobAmt = YjobAmtArr;
	 				aJob02Y.YJobAmtCd = $('[name=sYJobAmtCd]')[iIndex+sJob02index].innerHTML;
	 				aJob02Y.YJobAmtRate = $('[name=sYJobAmtRate]')[iIndex+sJob02index].innerHTML;
	 				
	 				job02YArr.push(aJob02Y);
	 				

	 				iIndex++;
 		  	      });
 			}
 			
			
			planfJOB.job01 	= job01Arr;
			planfJOB.job02 	= job02Arr;
			planfJOB.job01M = job01MArr;
			planfJOB.job02M = job02MArr;
			planfJOB.job01Y = job01YArr;
			planfJOB.job02Y = job02YArr;
			
			
 			//투자계획 생성
			let investTitle 	 = document.querySelectorAll("[name=dInvest01Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest01 = new Object();
 					aInvest01.investTitle 	= node.innerHTML;
 					aInvest01.investTitleCd = removeComma($('[name=dInvest01TitleCd]')[iIndex].innerHTML);
 					aInvest01.investPrice 	= removeComma($('[name=dInvest01Price]')[iIndex].innerHTML);
 					aInvest01.investYear 	= removeComma($('[name=dInvest01Year]')[iIndex].innerHTML);
 					invest01Arr.push(aInvest01);
	 				iIndex++;
 		  	      });
 			}

			investTitle 	 = document.querySelectorAll("[name=dInvest02Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest02 = new Object();
 					aInvest02.investTitle 	= node.innerHTML;
 					aInvest02.investTitleCd = removeComma($('[name=dInvest02TitleCd]')[iIndex].innerHTML);
 					aInvest02.investPrice 	= removeComma($('[name=dInvest02Price]')[iIndex].innerHTML);
 					aInvest02.investYear 	= removeComma($('[name=dInvest02Year]')[iIndex].innerHTML);
 					invest02Arr.push(aInvest02);
	 				iIndex++;
 		  	      });
 			}
 			
			investTitle 	 = document.querySelectorAll("[name=dInvest03Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest03 = new Object();
 					aInvest03.investTitle 	= node.innerHTML;
 					aInvest03.investTitleCd = removeComma($('[name=dInvest03TitleCd]')[iIndex].innerHTML);
 					aInvest03.investPrice 	= removeComma($('[name=dInvest03Price]')[iIndex].innerHTML);
 					aInvest03.investYear 	= removeComma($('[name=dInvest03Year]')[iIndex].innerHTML);
 					invest03Arr.push(aInvest03);
	 				iIndex++;
 		  	      });
 			}
 			
			investTitle 	 = document.querySelectorAll("[name=dInvest04Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest04 = new Object();
 					aInvest04.investTitle 	= node.innerHTML;
 					aInvest04.investTitleCd = removeComma($('[name=dInvest04TitleCd]')[iIndex].innerHTML);
 					aInvest04.investPrice 	= removeComma($('[name=dInvest04Price]')[iIndex].innerHTML);
 					aInvest04.investYear 	= removeComma($('[name=dInvest04Year]')[iIndex].innerHTML);
 					invest04Arr.push(aInvest04);
	 				iIndex++;
 		  	      });
 			}

			investTitle 	 = document.querySelectorAll("[name=dInvest05Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest05 = new Object();
 					aInvest05.investTitle 	= node.innerHTML;
 					aInvest05.investTitleCd = removeComma($('[name=dInvest05TitleCd]')[iIndex].innerHTML);
 					aInvest05.investPrice 	= removeComma($('[name=dInvest05Price]')[iIndex].innerHTML);
 					invest05Arr.push(aInvest05);
	 				iIndex++;
 		  	      });
 			}

			investTitle 	 = document.querySelectorAll("[name=dInvest06Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest06 = new Object();
 					aInvest06.investTitle 	= node.innerHTML;
 					aInvest06.investTitleCd = removeComma($('[name=dInvest06TitleCd]')[iIndex].innerHTML);
 					aInvest06.investPrice 	= removeComma($('[name=dInvest06Price]')[iIndex].innerHTML);
 					invest06Arr.push(aInvest06);
	 				iIndex++;
 		  	      });
 			}

 			investTitle 	 = document.querySelectorAll("[name=dInvest07Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest07 = new Object();
 					aInvest07.investTitle 	= node.innerHTML;
 					aInvest07.investTitleCd = removeComma($('[name=dInvest07TitleCd]')[iIndex].innerHTML);
 					aInvest07.investPrice 	= removeComma($('[name=dInvest07Price]')[iIndex].innerHTML);
 					invest07Arr.push(aInvest07);
	 				iIndex++;
 		  	      });
 			}

 			investTitle 	 = document.querySelectorAll("[name=dInvest08Title]");
			iIndex=0;
 			if (investTitle.length > 0) {
 				investTitle.forEach(function(node){
 					aInvest08 = new Object();
 					aInvest08.investTitle 	= node.innerHTML;
 					aInvest08.investTitleCd = removeComma($('[name=dInvest08TitleCd]')[iIndex].innerHTML);
 					aInvest08.investPrice 	= removeComma($('[name=dInvest08Price]')[iIndex].innerHTML);
 					invest08Arr.push(aInvest08);
	 				iIndex++;
 		  	      });
 			}
 			
 			planfINVEST.invest01  = invest01Arr;
 			planfINVEST.invest02  = invest02Arr;
 			planfINVEST.invest03  = invest03Arr;
 			planfINVEST.invest04  = invest04Arr;
 			planfINVEST.invest05  = invest05Arr;
 			planfINVEST.invest06  = invest06Arr;
 			planfINVEST.invest07  = invest07Arr;
 			planfINVEST.invest08  = invest08Arr;

 			//자본 생성
			let loanTitle 	 = document.querySelectorAll("[name=dLoan01Title]");
			iIndex=0;
 			if (loanTitle.length > 0) {
 				loanTitle.forEach(function(node){
 					aLoan01 = new Object();
 					loanYearArr = new Array();
 					aLoan01.loanTitle 	= node.innerHTML;
 					aLoan01.loanTitleCd = $('[name=dLoan01TitleCd]')[iIndex].innerHTML;
 					loanYearArr.push(removeComma($('[name=dLoan01Year1]')[iIndex].innerHTML));
 					loanYearArr.push(removeComma($('[name=dLoan01Year2]')[iIndex].innerHTML));
 					loanYearArr.push(removeComma($('[name=dLoan01Year3]')[iIndex].innerHTML));
 					aLoan01.loanYear = loanYearArr;
 					loan01Arr.push(aLoan01);
	 				iIndex++;
 		  	      });
 			}

			loanTitle 	 = document.querySelectorAll("[name=dLoan02Title]");
			iIndex=0;
 			if (loanTitle.length > 0) {
 				loanTitle.forEach(function(node){
 					aLoan02 = new Object();
 					loanYearArr = new Array();
 					loanRateArr = new Array();
 					aLoan02.loanTitle 	= node.innerHTML;
 					aLoan02.loanTitleCd = $('[name=dLoan02TitleCd]')[iIndex].innerHTML;
 					
 					loanYearArr.push(removeComma($('[name=dLoan02Year1]')[iIndex].innerHTML));
 					loanYearArr.push(removeComma($('[name=dLoan02Year2]')[iIndex].innerHTML));
 					loanYearArr.push(removeComma($('[name=dLoan02Year3]')[iIndex].innerHTML));
 					aLoan02.loanYear = loanYearArr;

 					loanRateArr.push(removeComma($('[name=dLoan02Rate1]')[iIndex].innerHTML));
 					loanRateArr.push(removeComma($('[name=dLoan02Rate2]')[iIndex].innerHTML));
 					loanRateArr.push(removeComma($('[name=dLoan02Rate3]')[iIndex].innerHTML));
 					aLoan02.loanRate = loanRateArr;
 					
 					loan02Arr.push(aLoan02);
	 				iIndex++;
 		  	      });
 			}
 			
 			planfLOAN.loan01  = loan01Arr;
 			planfLOAN.loan02  = loan02Arr;
 			
 			
			//재조경비 생성
			let sMCostTitle 	 = document.querySelectorAll("[name=sMCost01Title]");
			iIndex=0;
 			if (sMCostTitle.length > 0) {
 				sMCostTitle.forEach(function(node){
 					aMCost  = new Object();
 					aMCostM = new Object();
 					aMCostY = new Object();

 					aMCost.MCostTitle = node.innerHTML;
 					aMCost.MCostTitleCd = removeComma($('[name=sMCost01TitleCd]')[iIndex].innerHTML);
 					aMCost.MCostCd = removeComma($('[name=sMCost01Cd]')[iIndex].innerHTML);
 					aMCost.MCostTVa = removeComma($('[name=sMCost01TVa]')[iIndex].innerHTML);
 					aMCost.MCostTVaLabel = removeComma($('[name=sMCost01TVaLabel]')[iIndex].innerHTML);
 	 				
 					MCostArr.push(aMCost);
	 				
 					MMCostAmtArr = new Array();
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt01]')[iIndex].innerHTML));
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt02]')[iIndex].innerHTML));
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt03]')[iIndex].innerHTML));
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt04]')[iIndex].innerHTML));
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt05]')[iIndex].innerHTML));
 					MMCostAmtArr.push(removeComma($('[name=sMMCostAmt06]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt07]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt08]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt09]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt10]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt11]')[iIndex].innerHTML));
	 				MMCostAmtArr.push(removeComma($('[name=sMMCostAmt12]')[iIndex].innerHTML));

	 				aMCostM.MMCostAmt = MMCostAmtArr;
	 				MCostMArr.push(aMCostM);

	 				
	 				YMCostAmtArr = new Array();
	 				YMCostAmtArr.push(removeComma($('[name=sYMCostAmtYear1]')[iIndex].innerHTML));
	 				YMCostAmtArr.push(removeComma($('[name=sYMCostAmtYear2]')[iIndex].innerHTML));
	 				YMCostAmtArr.push(removeComma($('[name=sYMCostAmtYear3]')[iIndex].innerHTML));

	 				aMCostY.YMCostAmtYear = YMCostAmtArr;
	 				aMCostY.YMCostAmtCd = $('[name=sYMCostAmtCd]')[iIndex].innerHTML;
	 				aMCostY.YMCostAmtRate = $('[name=sYMCostAmtRate]')[iIndex].innerHTML;
	 				
	 				MCostYArr.push(aMCostY);
	 				

	 				iIndex++;
 		  	      });
 			}
 			
 			
 			planfMCOST.MCost    = MCostArr;
 			planfMCOST.MCostM   = MCostMArr;
 			planfMCOST.MCostY   = MCostYArr;

			//판매경비 생성
			let sSCostTitle 	 = document.querySelectorAll("[name=sSCost01Title]");
			iIndex=0;
 			if (sSCostTitle.length > 0) {
 				sSCostTitle.forEach(function(node){
 					aSCost  = new Object();
 					aSCostM = new Object();
 					aSCostY = new Object();

 					aSCost.SCostTitle = node.innerHTML;
 					aSCost.SCostTitleCd = removeComma($('[name=sSCost01TitleCd]')[iIndex].innerHTML);
 					aSCost.SCostCd = removeComma($('[name=sSCost01Cd]')[iIndex].innerHTML);
 					aSCost.SCostTVa = removeComma($('[name=sSCost01TVa]')[iIndex].innerHTML);
 					aSCost.SCostTVaLabel = removeComma($('[name=sSCost01TVaLabel]')[iIndex].innerHTML);
 	 				
 					SCostArr.push(aSCost);
	 				
 					MSCostAmtArr = new Array();
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt01]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt02]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt03]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt04]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt05]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt06]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt07]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt08]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt09]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt10]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt11]')[iIndex].innerHTML));
 					MSCostAmtArr.push(removeComma($('[name=sMSCostAmt12]')[iIndex].innerHTML));

	 				aSCostM.MSCostAmt = MSCostAmtArr;
	 				SCostMArr.push(aSCostM);

	 				
	 				YSCostAmtArr = new Array();
	 				YSCostAmtArr.push(removeComma($('[name=sYSCostAmtYear1]')[iIndex].innerHTML));
	 				YSCostAmtArr.push(removeComma($('[name=sYSCostAmtYear2]')[iIndex].innerHTML));
	 				YSCostAmtArr.push(removeComma($('[name=sYSCostAmtYear3]')[iIndex].innerHTML));

	 				aSCostY.YSCostAmtYear = YMCostAmtArr;
	 				aSCostY.YSCostAmtCd = $('[name=sYSCostAmtCd]')[iIndex].innerHTML;
	 				aSCostY.YSCostAmtRate = $('[name=sYSCostAmtRate]')[iIndex].innerHTML;
	 				
	 				SCostYArr.push(aSCostY);
	 				

	 				iIndex++;
 		  	      });
 			}
 			
 			
 			planfSCOST.SCost    = SCostArr;
 			planfSCOST.SCostM   = SCostMArr;
 			planfSCOST.SCostY   = SCostYArr;

			
			//root 생성
			planfJson.SALE 	= planfSALE;
			planfJson.COST 	= planfCOST;
			planfJson.JOB  	= planfJOB;
			planfJson.INVEST= planfINVEST;
			planfJson.LOAN  = planfLOAN;
			planfJson.MCOST  = planfMCOST;
			planfJson.SCOST  = planfSCOST;
			
			console.log(planfJson);
			document.getElementById('PLANF_SALE').value = JSON.stringify(planfJson);
			
			
			
		}	     
	     