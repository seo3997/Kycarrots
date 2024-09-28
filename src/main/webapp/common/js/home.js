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
	    var sCostIndexNo=0;
	    var loanIndexNo=0;
	
		let industryCd = "3";       //업종구분
		let workDay = 25;			//영업일수
		let monTargetPrice = 0;     //월목표이익
		
		let workAmt = 324000;      	//매출금액
		let jobAmt  = 0;      		//인건비
		
		
		//초기화
		function initUpdate(pIndustryCd,pWorkDay,pMonTargetPrice) {
			initSale(pIndustryCd,pWorkDay,pMonTargetPrice);
			initJson();
			
			if(PLANF_ID!="") {
				selectMenu(1);	
			}
			
			
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
		}

		//2.JSON 표시
		function initJson() {
			initMenuTable(PLANF_SALE_JSON.SALE);		
			initCostTable(PLANF_SALE_JSON.COST);		
			initJobTable(PLANF_SALE_JSON.JOB);		
			initInvestTable(PLANF_SALE_JSON.INVEST);		
			initLoanTable(PLANF_SALE_JSON.LOAN);		
			initMCostTable(PLANF_SALE_JSON.MCOST);		
			initSCostTable(PLANF_SALE_JSON.SCOST);		
		}

		
		//Side1 - Json 수정: json삭제 
		function delJson(pSideId,pIndex,pTabCd,pInvestCd) {
			switch(pSideId) {
				case 1:
					delMenu(pIndex);
					//매출액이 변경되면 제조경비/판매관리비 변경
					updMScost();
					break;
				case 3:
					delJob(pIndex,pTabCd);
					//인건비이 변경되면 제조경비/판매관리비 변경
					updMScost();
					break
				case 4:
					delInvest(pIndex,pTabCd,pInvestCd);
					//월제조비용 판관비용 변경되면 제조경비/판매관리비 변경
					updMScost();
					break
				case 5:
					delLoan(pIndex,pTabCd);
					break
				case 6:
					delMcost(pIndex);
					break
				case 7:
					delScost(pIndex);
					break
			}
		}
		
		//Side1 - Json 수정: 메뉴삭제 
		function delMenu(pIndex) {
			PLANF_SALE_JSON.SALE.menu.splice(pIndex,1);
			PLANF_SALE_JSON.SALE.menuM.splice(pIndex,1);
			PLANF_SALE_JSON.SALE.menuY.splice(pIndex,1);
			PLANF_SALE_JSON.COST.cost.splice(pIndex,1);
			PLANF_SALE_JSON.COST.costM.splice(pIndex,1);
			PLANF_SALE_JSON.COST.costY.splice(pIndex,1);
			initJson();
		}

		//Side3 - Json 수정: 인건비 삭제 
		function delJob(pIndex,pJobCd) {
			if("01" == pJobCd) {
				PLANF_SALE_JSON.JOB.job01.splice(pIndex,1);
				PLANF_SALE_JSON.JOB.job01M.splice(pIndex,1);
				PLANF_SALE_JSON.JOB.job01Y.splice(pIndex,1);
			}else {
				PLANF_SALE_JSON.JOB.job02.splice(pIndex,1);
				PLANF_SALE_JSON.JOB.job02M.splice(pIndex,1);
				PLANF_SALE_JSON.JOB.job02Y.splice(pIndex,1);
			}
			initJobTable(PLANF_SALE_JSON.JOB);
		}

		//Side4 - Json 수정: 투자 삭제 
		function delInvest(pIndex,pBizCd,pInvestCd) {
			let investStr  = '';
			if("1" == pInvestCd) {
				if("01" == pBizCd) investStr  ='invest01';
				else investStr  ='invest02';
			}else if("2" == pInvestCd){
				if("01" == pBizCd) investStr  ='invest03';
				else investStr  ='invest04';
			}else if("3" == pInvestCd){
				if("01" == pBizCd) investStr  ='invest05';
				else investStr  ='invest06';
			}else if("4" == pInvestCd){
				if("01" == pBizCd) investStr  ='invest07';
				else investStr  ='invest08';
			}
			PLANF_SALE_JSON.INVEST[investStr].splice(pIndex,1);
			initInvestTable(PLANF_SALE_JSON.INVEST);
		}
		

		//Side5 - Json 수정: 투자 삭제 
		function delLoan(pIndex,pBizCd) {
			let loanStr  = '';
			if("1" == pBizCd) {
				loanStr  ='loan01';
			}else if("2" == pBizCd){
				loanStr  ='loan02';
			}
			PLANF_SALE_JSON.LOAN[loanStr].splice(pIndex,1);
			initLoanTable(PLANF_SALE_JSON.LOAN);
		}

		//Side6 - Json 수정: 제조경비 삭제 
		function delMcost(pIndex) {
			PLANF_SALE_JSON.MCOST.MCost.splice(pIndex,1);
			PLANF_SALE_JSON.MCOST.MCostM.splice(pIndex,1);
			PLANF_SALE_JSON.MCOST.MCostY.splice(pIndex,1);
			initMCostTable(PLANF_SALE_JSON.MCOST);		
		}

		//Side7 - Json 수정: 판매관리비 계획 삭제 
		function delScost(pIndex) {
			PLANF_SALE_JSON.SCOST.SCost.splice(pIndex,1);
			PLANF_SALE_JSON.SCOST.SCostM.splice(pIndex,1);
			PLANF_SALE_JSON.SCOST.SCostY.splice(pIndex,1);
			initSCostTable(PLANF_SALE_JSON.SCOST);		
		}
		
		//Side1 - Json 수정: 메뉴수정
		function setMenu(pCd,pInputMenuTitleValue,pInputMenuPriceValue,pInputMenuQtyValue,pIndex) {
			let aMenu ;
			let menuMArr 	  = new Array();
			let MMenuQtyArr;
			let MMenuAmtArr;
			let aMenuM;

			let menuYArr 	  = new Array();
			let YMenuQtyArr;
			let YMenuPriceArr;
			let YMenuAmtArr;
			let aMenuY;
			
    		let MmenuPrice      	= 0;
    		let MmenuQty        	= 0;
    		let MmenuAmt        	= 0;

			let YmenuQtyValue       = 0;
			let YmenuAmtValue       = 0;

			//메뉴 수정
			aMenu = new Object();
			aMenuM = new Object();
			aMenuY = new Object()
			
			aMenu.MenuTitle = pInputMenuTitleValue;
			aMenu.MenuPrice = getNumber(removeComma(pInputMenuPriceValue)); 
			aMenu.MenuQty   = getNumber(removeComma(pInputMenuQtyValue)); 
			
			
			//월판매금액 수정
			MmenuQty         = getNumber(aMenu.MenuQty) * workDay					//월판매수량	
			MmenuAmt 		 = MmenuQty * getNumber(aMenu.MenuPrice);				//월판매금액
			MmenuAmt         = getNumberPoint(MmenuAmt/1000,0);

			
			MMenuQtyArr = new Array();
			MMenuAmtArr = new Array();
			let i=0;
			for (i = 0; i < 12; i++){
				MMenuQtyArr[i] = MmenuQty;
				MMenuAmtArr[i] = getNumber(MmenuAmt);
				YmenuQtyValue = YmenuQtyValue + getNumber(MmenuQty);
				YmenuAmtValue = YmenuAmtValue + getNumber(MmenuAmt);
         	}
					
			aMenuM.MMenuQty = MMenuQtyArr;  
			aMenuM.MMenuAmt = MMenuAmtArr;

			
			//년판매금액 수정
			YMenuQtyArr = new Array();
			YMenuQtyArr.push(YmenuQtyValue);
			YMenuQtyArr.push(YmenuQtyValue);
			YMenuQtyArr.push(YmenuQtyValue);

				
			YMenuPriceArr = new Array();
			YMenuPriceArr.push(aMenu.MenuPrice);
			YMenuPriceArr.push(aMenu.MenuPrice);
			YMenuPriceArr.push(aMenu.MenuPrice);
				
				
			YMenuAmtArr = new Array();
			YMenuAmtArr.push(YmenuAmtValue);
			YMenuAmtArr.push(YmenuAmtValue);
			YMenuAmtArr.push(YmenuAmtValue);

			aMenuY.YMenuQty 		= YMenuQtyArr;
			aMenuY.YMenuPrice 		= YMenuPriceArr;
			aMenuY.YMenuAmt 		= YMenuAmtArr;
			aMenuY.YMenuCd  		= '1';
			aMenuY.YMenuQtyRate 	= '';
			aMenuY.YMenuPriceRate 	= '';
			
			//재료비도 수정되야함
			let aCost = new Object();
	 		let aCostM = new Object();
	 		let aCostY = new Object();
			
			aCost.costRate = "";
			let MCostAmtArr = new Array();
			let iMCostAmt   = 0;
			
			for (i = 0; i < 12; i++){
				MCostAmtArr[i] = iMCostAmt;
         	}
			aCostM.MCostAmt = MCostAmtArr;
			
			let YCostAmtArr = new Array();
			YCostAmtArr[0]=0;
			YCostAmtArr[1]=0;
			YCostAmtArr[2]=0;

			let YCostRateArr = new Array();
			YCostRateArr[0]=aCost.costRate;
			YCostRateArr[1]=aCost.costRate;
			YCostRateArr[2]=aCost.costRate;
			
			aCostY.YCostAmt = YCostAmtArr;
			aCostY.YCostCd = "1";
			aCostY.YCostRate = YCostRateArr;
			aCostY.YCostAmtRate = 0;
			
			if(pCd=="ADD") {
				PLANF_SALE_JSON.SALE.menu.push(aMenu);
				PLANF_SALE_JSON.SALE.menuM.push(aMenuM);
				PLANF_SALE_JSON.SALE.menuY.push(aMenuY);
				PLANF_SALE_JSON.COST.cost.push(aCost);
				PLANF_SALE_JSON.COST.costM.push(aCostM);
				PLANF_SALE_JSON.COST.costY.push(aCostY);
			} else  {
				PLANF_SALE_JSON.SALE.menu[pIndex] 	= aMenu;
				PLANF_SALE_JSON.SALE.menuM[pIndex] 	= aMenuM;
				PLANF_SALE_JSON.SALE.menuY[pIndex] 	= aMenuY;
			
				aCost.costRate = PLANF_SALE_JSON.COST.cost[pIndex].costRate;
				PLANF_SALE_JSON.COST.cost[pIndex]	= aCost;
				updCost(aCost.costRate,pIndex);
			}
			
			//메뉴금액이 변경되면  매출총액이 변경됨 제조경비 계획, 판매관리비 매출 기준으로 적용금액 변경
			updMScost();
			
			initJson();
		}
		
		//Side1- Json 수정:메뉴연도별 메뉴 수정
		function setYMenu(pAMenuY,pACostY,pIndex) {
			PLANF_SALE_JSON.SALE.menuY[pIndex] 	= pAMenuY;
			PLANF_SALE_JSON.COST.costY[pIndex] 	= pACostY;
			initJson();
		}

		//Side2 - Json 수정:재료비 수정
		function updCost(pIcostRate,pIndex) {
			
			//재료비도 수정되야함
			let aCost = new Object();
	 		let aCostM = new Object();
	 		let aCostY = new Object();
			
			aCost.costRate = getNumber(pIcostRate);
			let MCostAmtArr = new Array();
			
			let iCostRate  = getNumber(aCost.costRate);
			let iMenuPrice = getNumber(PLANF_SALE_JSON.SALE.menu[pIndex].MenuPrice);
			let iMenuQty   = getNumber(PLANF_SALE_JSON.SALE.menu[pIndex].MenuQty);
			let iCostAmt   = ((iCostRate * iMenuPrice)/100).toFixed(0);
			
			let iMCostAmt   = getNumberPoint((iCostAmt * iMenuQty * workDay)/1000,0);
			let iYCostAmt   = iMCostAmt * 12;
			
			for (i = 0; i < 12; i++){
				MCostAmtArr[i] = iMCostAmt;
         	}
			aCostM.MCostAmt = MCostAmtArr;
			
			let YCostAmtArr = new Array();
			YCostAmtArr[0]=iYCostAmt;
			YCostAmtArr[1]=iYCostAmt;
			YCostAmtArr[2]=iYCostAmt;

			let YCostRateArr = new Array();
			YCostRateArr[0]=aCost.costRate;
			YCostRateArr[1]=aCost.costRate;
			YCostRateArr[2]=aCost.costRate;
			
			aCostY.YCostAmt = YCostAmtArr;
			aCostY.YCostCd = "1";
			aCostY.YCostRate = YCostRateArr;
			aCostY.YCostAmtRate = 0;
			
			PLANF_SALE_JSON.COST.cost[pIndex] 	= aCost;
			PLANF_SALE_JSON.COST.costM[pIndex] 	= aCostM;
			PLANF_SALE_JSON.COST.costY[pIndex] 	= aCostY;
			
			initCostTable(PLANF_SALE_JSON.COST);
		}

		//Side2 - Json 수정:재료비년 수정
		function updYCost(pACostY,pIndex) {
			PLANF_SALE_JSON.COST.costY[pIndex] 	= pACostY;
			initCostTable(PLANF_SALE_JSON.COST);
		}

		//Side3 - Json 수정:인건비 수정
		function setJob(pCd,pJobCd,pInputJobTitleValue,pInputJobCntValue,pInputJobQtyValue,pIndex) {

			let aJob;
			let aJobM;
			let aJobY;

			let jobMArr 	  = new Array();
			let jobYArr 	  = new Array();
			let jobYRateArr   = new Array();

			let jobStr  ='job'+pJobCd;
			let jobMStr ='job'+pJobCd+"M";
			let jobYStr ='job'+pJobCd+"Y";
			
			//인건비
			aJob  = new Object();
			aJob.JobTitle = pInputJobTitleValue;
	 		aJob.JobCnt 	= pInputJobCntValue;
	 		aJob.JobQty 	= pInputJobQtyValue;

	 		//월인건비 
	 		let iJobAmt = getNumber(pInputJobCntValue) * getNumber(pInputJobQtyValue);
	 		
	 		iJobAmt = numFixed(iJobAmt/1000,0);
	 		
	 		let i=0;
	 		for (i = 0; i < 12; i++){
		 		jobMArr[i] = iJobAmt;
	 		}
	 		
	 		aJobM = new Object();
	 		aJobM.MJobAmt = jobMArr;
			

			//년인건비
	 		let iYJobAmt = iJobAmt * 12;
	 		for (i = 0; i < 3; i++){
	 			jobYArr[i] = iYJobAmt;
	 			jobYRateArr[i] = iJobAmt*1000;
	 		}

	 		
	 		aJobY = new Object();
	 		aJobY.YJobAmtCd="1";
	 		aJobY.YJobAmtRate=0;
	 		aJobY.YJobAmt=jobYArr;
	 		aJobY.YJobRateAmt=jobYRateArr;

	 		if(pCd=="ADD") {
	 			PLANF_SALE_JSON.JOB[jobStr].push(aJob);
	 			PLANF_SALE_JSON.JOB[jobMStr].push(aJobM);
	 			PLANF_SALE_JSON.JOB[jobYStr].push(aJobY);
			} else  {
				PLANF_SALE_JSON.JOB[jobStr][pIndex]	= aJob;
				PLANF_SALE_JSON.JOB[jobMStr][pIndex]= aJobM;
				PLANF_SALE_JSON.JOB[jobYStr][pIndex]= aJobY;
			}
	 		
	 		updMScost();
	 		
	 		initJobTable(PLANF_SALE_JSON.JOB);
	 		
		}

		//Side3 - Json 수정:인건비년 수정
		function updYJob(pJobY,pIndex,pJobCd) {

			let jobYStr ='job'+pJobCd+"Y";
			PLANF_SALE_JSON.JOB[jobYStr][pIndex]= pJobY;

			initJobTable(PLANF_SALE_JSON.JOB);
		}

		//Side4 - Json 수정:투자 수정
		function setInvest(pCd,pInvestNo,pInvest,pIndex) {
			
			let aInvest;
			let aInvestNo="invest"+pInvestNo;
			//투자비
			aInvest  = pInvest;

	 		if(pCd=="ADD") {
	 			PLANF_SALE_JSON.INVEST[aInvestNo].push(aInvest);
			} else  {
				PLANF_SALE_JSON.INVEST[aInvestNo][pIndex] = aInvest;
			}

	 		updMScost();
	 		initInvestTable(PLANF_SALE_JSON.INVEST);
		}

		//Side5 - Json 수정:자금조달계획 수정
		function setLoan(pCd,pLoanNo,pLoan,pIndex) {
			
			let aLoan;
			let aLoanNo="loan0"+pLoanNo;
			//투자비
			aLoan  = pLoan;

	 		if(pCd=="ADD") {
	 			PLANF_SALE_JSON.LOAN[aLoanNo].push(aLoan);
			} else  {
				PLANF_SALE_JSON.LOAN[aLoanNo][pIndex] = aLoan;
			}

	 		initLoanTable(PLANF_SALE_JSON.LOAN);
		}

		//Side6 - Json 수정:재조경비 계획 수정
		function setMcost(pCd,pMcost,pIndex) {

			let MCostArr 	  = new Array();
			let aMCost;

			let MCostMArr 	  = new Array();
			let aMCostM;
			
			let MCostYArr 	  = new Array();
			let aMCostY;
			
			aMCost =pMcost;

	 		//월예상 제조경비 
	 		let MCostTVaAmt = pMcost.MCostTVaAmt;
	 		
	 		let i=0;
	 		for (i = 0; i < 12; i++){
	 			MCostMArr[i] = MCostTVaAmt;
	 		}
	 		
	 		aMCostM = new Object();
	 		aMCostM.MMCostAmt = MCostMArr;
			

			//년제조경비
	 		let iYCostTVaAmt = MCostTVaAmt*12;
	 		for (i = 0; i < 3; i++){
	 			MCostYArr[i] = iYCostTVaAmt;
	 		}
	 		
	 		aMCostY = new Object();
	 		aMCostY.YMCostAmtYear=MCostYArr;
	 		aMCostY.YMCostAmtCd="1";
	 		aMCostY.YMCostAmtRate=0;

	 		if(pCd=="ADD") {
	 			PLANF_SALE_JSON.MCOST.MCost.push(aMCost);
	 			PLANF_SALE_JSON.MCOST.MCostM.push(aMCostM);
	 			PLANF_SALE_JSON.MCOST.MCostY.push(aMCostY);
			} else  {
				PLANF_SALE_JSON.MCOST.MCost[pIndex]	= aMCost;
				PLANF_SALE_JSON.MCOST.MCostM[pIndex]= aMCostM;
				PLANF_SALE_JSON.MCOST.MCostY[pIndex]= aMCostY;
			}

	 		initMCostTable(PLANF_SALE_JSON.MCOST);	
		}

		function updMcost() {

			let MCostArr 	  = new Array();
			let aMCost;

			let MCostMArr 	  = new Array();
			let aMCostM;
			
			let MCostYArr 	  = new Array();
			let aMCostY;
			
			let mCostArr = PLANF_SALE_JSON.MCOST.MCost;
			if (mCostArr.length > 0) {
				mCostArr.forEach(function(item,index){
					item.MCostTVaAmt = calculateMCostTVaAmt(item.MCostCd,item.MCostTVa); 
					PLANF_SALE_JSON.MCOST.MCost[index]	= item;
				
			 		let i=0;
			 		for (i = 0; i < 12; i++){
			 			MCostMArr[i] = item.MCostTVaAmt;
			 		}
			 		
			 		aMCostM = new Object();
			 		aMCostM.MMCostAmt = MCostMArr;
					
					PLANF_SALE_JSON.MCOST.MCostM[index]= aMCostM;

					//년제조경비
			 		let iYCostTVaAmt = item.MCostTVaAmt*12;
			 		for (i = 0; i < 3; i++){
			 			MCostYArr[i] = iYCostTVaAmt;
			 		}
			 		
			 		aMCostY = new Object();
			 		aMCostY.YMCostAmtYear=MCostYArr;
			 		aMCostY.YMCostAmtCd="1";
			 		aMCostY.YMCostAmtRate=0;

			 		PLANF_SALE_JSON.MCOST.MCostY[index]= aMCostY;

				});
	         }				

		}
		

		//Side7 - Json 수정:판매관리비 계획 수정
		function setScost(pCd,pScost,pIndex) {
			let aSCost;

			let SCostMArr 	  = new Array();
			let aSCostM;
			
			let SCostYArr 	  = new Array();
			let aSCostY;
			
			aSCost = pScost;

	 		//월예상 제조경비 
	 		let SCostTVaAmt = pScost.SCostTVaAmt;
	 		
	 		let i=0;
	 		SCostMArr 	  = new Array();
	 		for (i = 0; i < 12; i++){
	 			SCostMArr[i] = SCostTVaAmt;
	 		}
	 		
	 		aSCostM = new Object();
	 		aSCostM.MSCostAmt = SCostMArr;
	 		
			//년인건비
	 		let iYCostTVaAmt = SCostTVaAmt*12;
	 		SCostYArr 	  = new Array();
	 		for (i = 0; i < 3; i++){
	 			SCostYArr[i] = iYCostTVaAmt;
	 		}
	 		
	 		aSCostY = new Object();
	 		aSCostY.YSCostAmtYear=SCostYArr;
	 		aSCostY.YSCostAmtCd="1";
	 		aSCostY.YSCostAmtRate=0;

	 		if(pCd=="ADD") {
	 			PLANF_SALE_JSON.SCOST.SCost.push(aSCost);
	 			PLANF_SALE_JSON.SCOST.SCostM.push(aSCostM);
	 			PLANF_SALE_JSON.SCOST.SCostY.push(aSCostY);
			} else  {
				PLANF_SALE_JSON.SCOST.SCost[pIndex]	= aSCost;
				PLANF_SALE_JSON.SCOST.SCostM[pIndex]= aSCostM;
				PLANF_SALE_JSON.SCOST.SCostY[pIndex]= aSCostY;
			}

	 		initSCostTable(PLANF_SALE_JSON.SCOST);	
		}

		function updScost() {
			let aSCost;

			let SCostMArr 	  = new Array();
			let aSCostM;
			
			let SCostYArr 	  = new Array();
			let aSCostY;
			
			let sCostArr = PLANF_SALE_JSON.SCOST.SCost;
			if (sCostArr.length > 0) {
				sCostArr.forEach(function(item,index){
					item.SCostTVaAmt = calculateSCostTVaAmt(item.SCostCd,item.SCostTitle,item.SCostTVa); 
					PLANF_SALE_JSON.SCOST.SCost[index]	= item;
				
			 		let i=0;
			 		SCostMArr 	  = new Array()
			 		for (i = 0; i < 12; i++){
			 			SCostMArr[i] = item.SCostTVaAmt;
			 		}
			 		
			 		aSCostM = new Object();
			 		aSCostM.MSCostAmt = SCostMArr;
				
					PLANF_SALE_JSON.SCOST.SCostM[index]= aSCostM;

					
					//년인건비
			 		let iYCostTVaAmt = item.SCostTVaAmt*12;
			 		SCostYArr 	  = new Array();
			 		for (i = 0; i < 3; i++){
			 			SCostYArr[i] = iYCostTVaAmt;
			 		}
			 		
			 		aSCostY = new Object();
			 		aSCostY.YSCostAmtYear=SCostYArr;
			 		aSCostY.YSCostAmtCd="1";
			 		aSCostY.YSCostAmtRate=0;

					PLANF_SALE_JSON.SCOST.SCostY[index]= aSCostY;

				});
	         }				
		}
		
		
		//메뉴셋팅
		function initMenuTable(pJson) {
			let menuArr      = pJson.menu;
			let menuM        = pJson.menuM;
			let menuY        = pJson.menuY;
			
			let costIndexValue ="";
			let i = 0;
			
			$('#menuTable > tbody').empty();
			$('#mmenuTable > tbody').empty();
			$('#ymenuTable > tbody').empty();
			
			//메뉴셋팅, 월매출셋팅
			if (menuArr.length > 0) {
 				i = 0;
 				menuArr.forEach(function(Menu){
 					addMenuTable(Menu.MenuTitle,Menu.MenuPrice,Menu.MenuQty);
 					costIndexValue 	=  (i+1)+"";
 					console.log("costIndexValue["+costIndexValue+"]");
	            	addMenuMTable(i,costIndexValue,Menu.MenuTitle,Menu.MenuPrice,Menu.MenuQty,menuM);
					i++;
	  	      });
	         }	
 			calSale();							//일매출액계산
            calMSale();							//월매출계산
            
            //년매출셋팅
 			if (menuArr.length > 0) {
 				i = 0;
 				menuArr.forEach(function(Menu){
					costIndexValue 	=  $('[name=dmenuIndex]')[i].innerHTML;
 		            addMenuYTable(i,costIndexValue,Menu.MenuTitle,Menu.MenuPrice,menuY);
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
		function addMenuMTable(pIndex,pCostIndexValue,pInputMenuTitle,pInputMenuPrice,pinputMenuQty,pMenuM) {
	        let costIndexValue 	 	= "";
            let	menuTitleValue 	 	= "";
            let	menuPriceValue   	= "";
            let	menuQtyValue     	= "";

	        let costIndex 	 	 	= "";
	        let	menuTitle 	 	 	= "";
	        let	menuPrice 	 	 	= "";

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

    		
			let MmenuQtyTot     	= 0;      //메뉴별 년판매수량
			let MmenuAmtTot     	= 0;	  //메뉴볋 년판매금액	
 			let i = 0;

			 
        	var trStr="";

        	costIndexValue 	=  pCostIndexValue;		//index
        	menuTitleValue 	=  pInputMenuTitle;
        	menuPriceValue  =  pInputMenuPrice;
        	menuQtyValue     = pinputMenuQty;			//일판매기준수량	

			MmenuPrice       = getNumber(removeComma(menuPriceValue));
	        MMenuQtyArr  	 = pMenuM[pIndex].MMenuQty;   //월판매수량            
	        MMenuAmtArr  	 = pMenuM[pIndex].MMenuAmt;;  //월판매금액            
			
			
			
 			costIndex = "<span name='sMmenuIndex'>"+costIndexValue+"</span>";
 			menuTitle = "<span name='sMmenuTitle'>"+menuTitleValue+"</span>";
 			menuPrice = "<span name='sMmenuPrice'>"+setComma(menuPriceValue)+"</span>";
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
			trStr += "<td>"+menuPrice+"</td>";
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
		function addMenuYTable(pIndex,pCostIndexValue,pMenuTitleValue,pMenuPriceValue,pYMenu) {

			let costIndexValue 		=  pCostIndexValue;		//index
        	let menuTitleValue 		=  pMenuTitleValue;
        	let menuPriceValue  	=  pMenuPriceValue;

        	
	        let YMenuQtyArr 		= new Array();  //년판매수량            
	        let YMenuPriceArr 		= new Array();  //년판매단가            
	        let YMenuAmtArr 		= new Array();  //년판매금액            

	        let YmenuCdValue        = "";
        	let YMenuQtyRateValue  	= "";
        	let YMenuPriceRateValue	= "";

        	
	        YMenuQtyArr 		= pYMenu[pIndex].YMenuQty;  	//년판매수량            
	        YMenuPriceArr 		= pYMenu[pIndex].YMenuPrice;  //년판매금액
	        YMenuAmtArr 		= pYMenu[pIndex].YMenuAmt;  	//년판매단가
        	YmenuCdValue        = pYMenu[pIndex].YMenuCd;
        	YMenuQtyRateValue   = pYMenu[pIndex].YMenuQtyRate;
        	YMenuPriceRateValue = pYMenu[pIndex].YMenuPriceRate;
           	
           	
 			let costIndex = "<span name='sYMenuIndex'>"+costIndexValue+"</span>";
 			let menuTitle = "<span name='sYMenuTitle'>"+menuTitleValue+"</span>";
 			let menuPrice = "<span name='sYMenuPrice'>"+setComma(menuPriceValue)+"</span>";
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
			trStr += "<td>"+menuPrice+"</td>";
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

			$('#costTable > tbody').empty();
			$('#mCostTable > tbody').empty();
			$('#ycostTable > tbody').empty();

            //재료비 계산
			let i = 0;
			if (costArr.length > 0) {
 				i = 0;
 				costArr.forEach(function(Menu){
					setCostTable(i);		//재료비 셋팅
					i++;
	  	      });
	         }	
			if (costArr.length > 0) {
 				i = 0;
 				costArr.forEach(function(Menu){
					setMCostTable(i);		//월재료비 셋팅
					i++;
	  	      });
	         }	

			if (costArr.length > 0) {
 				i = 0;
 				costArr.forEach(function(Menu){
					setYCostTable(i);		//년재료비 셋팅
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

			$('#jobTable01 > tbody').empty();
			$('#jobTable02 > tbody').empty();
			$('#tbMjob01').empty();
			$('#tbMjob02').empty();
			$('#tbYjob01').empty();
			$('#tbYjob02').empty();
			
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
			trStr += "<td style='text-align:center;'><input name='btnUpdYJob' class='btn btn-mini' value='수정' type='button' />" 
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
			$('#investTable01 > tbody').empty();
			$('#investTable03 > tbody').empty();
			$('#investTable05 > tbody').empty();
			$('#investTable07 > tbody').empty();
			$('#investTable02 > tbody').empty();
			$('#investTable04 > tbody').empty();
			$('#investTable06 > tbody').empty();
			$('#investTable08 > tbody').empty();

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
			$('#loanTable01 > tbody').empty();
			$('#loanTable02 > tbody').empty();

			//자기자본
 			if (Loan01Arr.length > 0) {
 				i = 0;
 				Loan01Arr.forEach(function(loan){
 					addLoanTable("1",loan);
					i++;
	  	      });
	         }	

 			//자기자본
 			if (Loan02Arr.length > 0) {
 				i = 0;
 				Loan02Arr.forEach(function(loan){
 					addLoanTable("2",loan);
					i++;
	  	      });
	         }	

			
 			calLoan();
		}

		//자본계획추가	
		function addLoanTable(pLoanCd,pLoan) {
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
			let inputLoanTitleValue  	= pLoan.loanTitle;
			let inputLoanTitleCdValue  	= pLoan.loanTitleCd;

			let inputLoanYear1Value    	= pLoan.loanYear[0];
			let inputLoanYear2Value    	= pLoan.loanYear[1];
			let inputLoanYear3Value    	= pLoan.loanYear[2];


			if(isEmpty(inputLoanYear1Value)) inputLoanYear1Value="";
			if(isEmpty(inputLoanYear2Value)) inputLoanYear2Value="";
			if(isEmpty(inputLoanYear3Value)) inputLoanYear3Value="";
			
				
				
			
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
				inputLoanRate1Value    	= pLoan.loanRate[0];
				inputLoanRate2Value    	= pLoan.loanRate[1];
				inputLoanRate3Value    	= pLoan.loanRate[2];

				inputLoanYearRate1Value = pLoan.loanRateAmt[0];
				inputLoanYearRate2Value = pLoan.loanRateAmt[1];
				inputLoanYearRate3Value = pLoan.loanRateAmt[2];

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
			$('#MCostTable01 > tbody').empty();
			$('#mMCostTable > tbody').empty();
			$('#yMCostTable > tbody').empty();
			
			//제조경비 셋팅, 월 제조경비셋팅
 			if (MCostArr.length > 0) {
 				i = 0;
 				MCostArr.forEach(function(mcost){
 					addMCostTable(mcost);
	            	addMCostMTable(i,MCostM);
					i++;
	  	      });
	         }	
 			
 			calMMCost();
 			
            //년제조경비 세팅
 			if (MCostArr.length > 0) {
 				i = 0;
 				MCostArr.forEach(function(Menu){
					addMCostYTable(i,MCostY);
					i++;
	  	      });
	         }	
            calYMCost();							
		}
		
		//제조경비추가
		function addMCostTable(pMcost) {
			var inputMcostcdCodeM 	= "";
			var inputMCostTV 		= "";
			var MCostTVaAmt 		= "";
			
			var inputMcostcdCodeMValue   = pMcost.MCostTitle;
			var inputMcostcdCodeMCdValue = pMcost.MCostTitleCd;
			var sMCostCdValue    		 = pMcost.MCostCd;
			var inputMCostTVaValue 		 = pMcost.MCostTVa;
			var sMCostTVaLabelValue   	 = pMcost.MCostTVaLabel;
			var inputMCostTVaAmtValue	 = pMcost.MCostTVaAmt;

			inputMcostcdCodeM 	= "<span name='sMCost01Title'>"+inputMcostcdCodeMValue+"</span><span name='sMCost01TitleCd' style='display:none'>"+inputMcostcdCodeMCdValue+"</span>";
			sMCostCdValue   	= "<span name='sMCost01Cd'>"+sMCostCdValue+"</span>";
			inputMCostTV   		= "<span name='sMCost01TVa'>"+setComma(inputMCostTVaValue)+"</span><span name='sMCost01TVaLabel'>"+sMCostTVaLabelValue+"</span>";
			MCostTVaAmt   		= "<span name='sMCost01TVaAmt'>"+setComma(inputMCostTVaAmtValue+"")+"</span>천원";
			
     		var trStr="";
    		trStr="<tr>";
			trStr += "<td>"+inputMcostcdCodeM+"</td>";
			trStr += "<td>"+sMCostCdValue+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputMCostTV+"</td>";
			trStr += "<td style=\"text-align:right;\">"+MCostTVaAmt+"</td>";
			trStr += "<td style=\"text-align:right;\">"+"<input name='btnUpdMCost01' class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name='btnDelMCost01' class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				  +"</td>";
			trStr += "</tr>";
			$('#MCostTable01').append(trStr);
			
		}
		
		//월제조경비추가
		function addMCostMTable(pIndex,pMCostM)  {
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
        	sMMCostTitleValue 	=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTitle;
        	sMMCostCdValue		=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostCd;		
        	sMMCostTVaValue     =  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVa;		
        	sMCostTVaLabelValue	=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVaLabel;	
        	sMCostTVaAmtValue	=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVaAmt;	

        	if(sMCostTVaLabelValue=="천원") sMCostTVaLabelValue = "";

        	for (i = 0; i < 12; i++){
        		iMMCostMenuTot = iMMCostMenuTot + getNumber(pMCostM[pIndex].MMCostAmt[i]);
         	}
            	                                                               
        	
        	sMMCostTitle = "<span name='sMMCostTitle'>"+sMMCostTitleValue+"</span>";
        	sMMCostCd    = "<span name='sMMCostCd'>"+sMMCostCdValue+"</span><span name='sMMCostMenuTot' style='display:none'>"+setComma(iMMCostMenuTot+"")+"</span>";
        	sMMCostTVa   = "<span name='sMMCostTVa'>"+sMMCostTVaValue+"</span><span name='sMCostTVaLabel'>"+sMCostTVaLabelValue+"</span>"
 			
        	MCostAmtArr	= pMCostM[pIndex].MMCostAmt;              
        	
        	
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
		function addMCostYTable(pIndex,pMCostY) {
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

        	sYMCostTitleValue 	=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTitle;
        	sYMCostCdValue		=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostCd;		
        	sYMCostTVaValue     =  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVa;		
        	sYCostTVaLabelValue	=  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVaLabel;	
        	sMMCostMenuTotValue =  PLANF_SALE_JSON.MCOST.MCost[pIndex].MCostTVaAmt;	
        	
        	YMCostAmtCd      = pMCostY[pIndex].YMCostAmtCd;
        	YMCostAmtRate    = pMCostY[pIndex].YMCostAmtRate;
        	YMCostAmtArr     = pMCostY[pIndex].YMCostAmtYear;              

        	
        	
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
			trStr += "<td style='text-align:center;'><input name='btnUpdYYMCost' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#yMCostTable').append(trStr);
			
		}
		
		//판매경비셋팅
		function initSCostTable(pJson) {
			let SCostArr  = pJson.SCost;
			let SCostM    = pJson.SCostM;
			let SCostY    = pJson.SCostY;

			let i = 0;
			$('#SCostTable01 > tbody').empty();
			$('#mSCostTable > tbody').empty();
			$('#ySCostTable > tbody').empty();
			
			//판매경비 셋팅, 월 판매경비셋팅
 			if (SCostArr.length > 0) {
 				i = 0;
 				SCostArr.forEach(function(scost){
 					addSCostTable(scost);
	            	addSCostMTable(i,SCostM);
					i++;
	  	      });
	         }	
 			
 			calMSCost();
 			
            //년판매경비 세팅
 			if (SCostArr.length > 0) {
 				i = 0;
 				SCostArr.forEach(function(Menu){
					addSCostYTable(i,SCostY);
					i++;
	  	      });
	         }	
            calYSCost();							
		}
		
		//판매경비추가
		function addSCostTable(pScost) {
			var inputSCostcdCodeM 	= "";
			var sSCostCdValue 		= "";
			var inputSCostTV 		= "";
			var SCostTVaAmt 		= "";
			var iSCostTVaAmt 		= 0;

			var inputSCostcdCodeMValue   = pScost.SCostTitle;
			var inputSCostcdCodeMCdValue = pScost.SCostTitleCd;
			var sSCostCdValue    		 = pScost.SCostCd;
			var inputSCostTVaValue 		 = pScost.SCostTVa;
			var sSCostTVaLabelValue   	 = pScost.SCostTVaLabel;
			var inputSCostTVaAmtValue	 = pScost.SCostTVaAmt;
			
			
			inputSCostcdCodeM 	= "<span name='sSCost01Title'>"+inputSCostcdCodeMValue+"</span><span name='sSCost01TitleCd' style='display:none'>"+inputSCostcdCodeMCdValue+"</span>";
			sSCostCdValue   	= "<span name='sSCost01Cd'>"+sSCostCdValue+"</span>";
			inputSCostTV   		= "<span name='sSCost01TVa'>"+setComma(inputSCostTVaValue)+"</span><span name='sSCost01TVaLabel'>"+sSCostTVaLabelValue+"</span>";
			SCostTVaAmt   		= "<span name='sSCost01TVaAmt'>"+setComma(inputSCostTVaAmtValue+"")+"</span>천원";
			
     		var trStr="";
    		trStr="<tr>";
			trStr += "<td>"+inputSCostcdCodeM+"</td>";
			trStr += "<td>"+sSCostCdValue+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputSCostTV+"</td>";
			trStr += "<td style=\"text-align:right;\">"+SCostTVaAmt+"</td>";
			trStr += "<td style=\"text-align:right;\">"+"<input name='btnUpdSCost01' class=\"btn btn-mini\" value=\"수정\" type=\"button\" />"
												+" <input name='btnDelSCost01' class=\"btn btn-mini btn-danger\" value=\"삭제\" type=\"button\" />"						
				  +"</td>";
			trStr += "</tr>";
			$('#SCostTable01').append(trStr);
			
		}
		
		//월판매경비추가
		function addSCostMTable(pIndex,pSCostM)  {
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


        	sMSCostTitleValue 	=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTitle;
        	sMSCostCdValue		=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostCd;		
        	sMSCostTVaValue     =  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTVa;		
        	sSCostTVaLabelValue	=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTVaLabel;	
 
        	
        	if(sSCostTVaLabelValue=="천원") sSCostTVaLabelValue = "";
         	for (i = 0; i < 12; i++){
        		iMSCostMenuTot = iMSCostMenuTot + getNumber(pSCostM[pIndex].MSCostAmt[i]);
         	}
        		
        	
        	sMSCostTitle = "<span name='sMSCostTitle'>"+sMSCostTitleValue+"</span>";
        	sMSCostCd    = "<span name='sMSCostCd'>"+sMSCostCdValue+"</span><span name='sMSCostMenuTot' style='display:none'>"+setComma(iMSCostMenuTot+"")+"</span>";
        	sMSCostTVa   = "<span name='sMSCostTVa'>"+sMSCostTVaValue+"</span><span name='sSCostTVaLabel'>"+sSCostTVaLabelValue+"</span>"
 			
        	SCostAmtArr	= pSCostM[pIndex].MSCostAmt;              

        	
        	//월판매금액 셋팅
 			for (i = 0; i < 12; i++){
				if (i < 9 ) iTemp ="0"+(i+1)
				else iTemp = (i+1);
				SCostAmt[i] ="<span name='sMSCostAmt"+iTemp+"'>"+setComma(SCostAmtArr[i])+"</span>"
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
		function addSCostYTable(pIndex,pSCostY) {
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
        	

        	sYSCostTitleValue 	=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTitle;
        	sYSCostCdValue		=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostCd;		
        	sYSCostTVaValue     =  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTVa;		
        	sYCostTVaLabelValue	=  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTVaLabel;	
        	sMSCostMenuTotValue =  PLANF_SALE_JSON.SCOST.SCost[pIndex].SCostTVaAmt;	
        	
        	YSCostAmtCd      = pSCostY[pIndex].YSCostAmtCd;
        	YSCostAmtRate    = pSCostY[pIndex].YSCostAmtRate;
        	YSCostAmtArr     = pSCostY[pIndex].YSCostAmtYear;              

        	
        	sYSCostTitle = "<span name='sYSCostTitle'>"+sYSCostTitleValue+"</span><span name='sYSCostAmtCd' style='display:none'>"+YSCostAmtCd+"</span><span name='sYSCostAmtRate' style='display:none'>"+YSCostAmtRate+"</span>";
        	sYSCostCd 	 = "<span name='sYSCostCd'>"+sYSCostCdValue+"</span>";
        	sYSCostTVa 	 = "<span name='sYSCostTVa'>"+sYSCostTVaValue+"</span><span name='sYCostTVaLabel'>"+sYCostTVaLabelValue+"</span>";

 			YCostAmtYear1 = "<span name='sYSCostAmtYear1'>"+setComma(YSCostAmtArr[0])+"</span>";
 			YCostAmtYear2 = "<span name='sYSCostAmtYear2'>"+setComma(YSCostAmtArr[1])+"</span>";
 			YCostAmtYear3 = "<span name='sYSCostAmtYear3'>"+setComma(YSCostAmtArr[2])+"</span>";
 			
			trStr="<tr>";
			trStr += "<td>"+sYSCostTitle+"</td>";
			trStr += "<td>"+sYSCostCd+"</td>";
			trStr += "<td style='text-align:right;'>"+sYSCostTVa+"</td>";
			trStr += "<td style='text-align:right;'>"+YCostAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear3+"</td>"
			trStr += "<td style='text-align:center;'><input name='btnUpdYYSCost' class='btn btn-mini' value='수정' type='button' />" 
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
			
			let menuAmt 	 = PLANF_SALE_JSON.SALE.menu;
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node,index){
	    			setMenu("UPD",node.MenuTitle,node.MenuPrice,node.MenuQty,index);
	        	});
	         }	
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
		 	document.getElementById('inputYMenuQtyRate').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuQtyRate;
		 	document.getElementById('inputYMenuPriceRate').value = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuPriceRate;
		 	
		 	document.getElementById('inputMenuQtyYear1').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuQty[0];
			document.getElementById('inputMenuQtyYear2').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuQty[1];
			document.getElementById('inputMenuQtyYear3').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuQty[2];

		 	document.getElementById('inputMenuPriceYear1').value = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuPrice[0];
			document.getElementById('inputMenuPriceYear2').value = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuPrice[1];
			document.getElementById('inputMenuPriceYear3').value = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuPrice[2];

			document.getElementById('inputMenuAmtYear1').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuAmt[0];
			document.getElementById('inputMenuAmtYear2').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuAmt[1];
			document.getElementById('inputMenuAmtYear3').value   = PLANF_SALE_JSON.SALE.menuY[yMenuIndexNo].YMenuAmt[2];
			
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
					setMenu("ADD",inputMenuTitleValue,inputMenuPriceValue,inputMenuQtyValue,0);
				} else{
					setMenu("UPD",inputMenuTitleValue,inputMenuPriceValue,inputMenuQtyValue,saleIndexNo);
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
				
				document.getElementById('inputMenuTitle').value = PLANF_SALE_JSON.SALE.menu[saleIndexNo].MenuTitle;
				document.getElementById('inputMenuPrice').value = PLANF_SALE_JSON.SALE.menu[saleIndexNo].MenuPrice;
				document.getElementById('inputMenuQty').value 	= PLANF_SALE_JSON.SALE.menu[saleIndexNo].MenuQty;
				$('#myModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelMenu]', function() {
				if(!confirm('메뉴항목을 삭제하시겠습니까?')){
					return;
				}
				saleIndexNo = $('input[name=btnDelMenu]').index(this);
				delJson(1,saleIndexNo,0);
			 	
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

				//년도별 메뉴
				let aMenuY = new Object();
				let YMenuQtyArr = new Array();
				YMenuQtyArr.push(inputMenuQtyYear1Value);
				YMenuQtyArr.push(inputMenuQtyYear2Value);
				YMenuQtyArr.push(inputMenuQtyYear3Value);

				let YMenuPriceArr = new Array();
				YMenuPriceArr.push(inputMenuPriceYear1Value);
				YMenuPriceArr.push(inputMenuPriceYear2Value);
				YMenuPriceArr.push(inputMenuPriceYear3Value);

				let YMenuAmtArr = new Array();
				YMenuAmtArr.push(YMenuAmtYear1);
				YMenuAmtArr.push(YMenuAmtYear2);
				YMenuAmtArr.push(YMenuAmtYear3);


				aMenuY.YMenuQty 		= YMenuQtyArr;
				aMenuY.YMenuPrice 		= YMenuPriceArr;
				aMenuY.YMenuAmt 		= YMenuAmtArr;
				aMenuY.YMenuCd  		= inputYmenuCdValue;
				aMenuY.YMenuQtyRate 	= inputYMenuQtyRateValue;
				aMenuY.YMenuPriceRate 	= inputYMenuPriceRateValue;

				//년도별 제료비
				let YCostAmtArr  = new Array();
				let YCostAmtRateArr = new Array();
				
				YCostAmtRateArr[0] 		= PLANF_SALE_JSON.COST.cost[yMenuIndexNo].costRate;
				YCostAmtRateArr[1] 		= PLANF_SALE_JSON.COST.cost[yMenuIndexNo].costRate;
				YCostAmtRateArr[2] 		= PLANF_SALE_JSON.COST.cost[yMenuIndexNo].costRate;

				YCostAmtArr[0]			= getNumber($('[name=sMCostAmtTot]')[yMenuIndexNo].innerHTML);
				YCostAmtArr[1]			= getCalRateCostAmt(getNumber(YMenuAmtYear2),getNumber(YCostAmtRateArr[1]));
				YCostAmtArr[2]			= getCalRateCostAmt(getNumber(YMenuAmtYear3),getNumber(YCostAmtRateArr[2]));
				
				let aCostY = new Object();
 				aCostY.YCostAmt 		= YCostAmtArr;
 				aCostY.YCostRate 		= YCostAmtRateArr;
 				aCostY.YCostCd 			= "1";
 				aCostY.YCostAmtRate 	= 0;
				
				setYMenu(aMenuY,aCostY,yMenuIndexNo);
				
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
			 	document.getElementById('YCostTitle').innerHTML 		= PLANF_SALE_JSON.SALE.menu[pIndexNO].MenuTitle;
			 	document.getElementById('inputYCostRate').value   		= PLANF_SALE_JSON.COST.cost[pIndexNO].costRate;
			 	document.getElementById('inputYCostAmtRate').value   	= PLANF_SALE_JSON.COST.costY[pIndexNO].YCostAmtRate;
			 	document.getElementById('inputCostMenuAmtYear1').value  = PLANF_SALE_JSON.SALE.menuY[pIndexNO].YMenuAmt[0];
			 	document.getElementById('inputCostMenuAmtYear2').value  = PLANF_SALE_JSON.SALE.menuY[pIndexNO].YMenuAmt[1];
			 	document.getElementById('inputCostMenuAmtYear3').value  = PLANF_SALE_JSON.SALE.menuY[pIndexNO].YMenuAmt[2];
			 	document.getElementById('inputCostAmtYearRate1').value  = PLANF_SALE_JSON.COST.costY[pIndexNO].YCostRate[0];
			 	document.getElementById('inputCostAmtYearRate2').value  = PLANF_SALE_JSON.COST.costY[pIndexNO].YCostRate[1];
			 	document.getElementById('inputCostAmtYearRate3').value  = PLANF_SALE_JSON.COST.costY[pIndexNO].YCostRate[2];
			 	document.getElementById('inputCostAmtYear1').value  	= PLANF_SALE_JSON.COST.costY[pIndexNO].YCostAmt[0];
			 	document.getElementById('inputCostAmtYear2').value  	= PLANF_SALE_JSON.COST.costY[pIndexNO].YCostAmt[1];
			 	document.getElementById('inputCostAmtYear3').value  	= PLANF_SALE_JSON.COST.costY[pIndexNO].YCostAmt[2];
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
				var inputYCostRateValue 		= document.getElementById('inputYCostRate').value;
				var inputYCostAmtRateValue 		= document.getElementById('inputYCostAmtRate').value;
				var inputCostAmtYear1Value 		= document.getElementById('inputCostAmtYear1').value;

				var inputCostMenuAmtYear2Value 	= document.getElementById('inputCostMenuAmtYear2').value;
				var inputCostMenuAmtYear3Value 	= document.getElementById('inputCostMenuAmtYear3').value;

				let inputCostAmtYear1 	  = getNumber(inputCostAmtYear1Value);
				let inputCostMenuAmtYear2 = getNumber(inputCostMenuAmtYear2Value);
				let inputCostMenuAmtYear3 = getNumber(inputCostMenuAmtYear3Value);
				let inputCostAmtYear2 	  = 0;
				let inputCostAmtYear3 	  = 0;
				let inputCostAmtYearRate1 = 0;
				let inputCostAmtYearRate2 = 0;
				let inputCostAmtYearRate3 = 0;

				let inputYCostRate 		= getNumber(inputYCostRateValue);
				let inputYCostAmtRate 	= getNumber(inputYCostAmtRateValue);
				
				inputCostAmtYearRate1   = inputYCostRate;
				inputCostAmtYearRate2   = numFixed(((inputCostAmtYearRate1/100) * (1+(inputYCostAmtRate/100)))*100,1);
				inputCostAmtYearRate3   = numFixed(((inputCostAmtYearRate2/100) * (1+(inputYCostAmtRate/100)))*100,1);
				
				inputCostAmtYear2 = getCalRateCostAmt(inputCostMenuAmtYear2,inputCostAmtYearRate2);
				inputCostAmtYear3 = getCalRateCostAmt(inputCostMenuAmtYear3,inputCostAmtYearRate3);

				inputCostAmtYear2=Math.floor(inputCostAmtYear2);
				inputCostAmtYear3=Math.floor(inputCostAmtYear3);
				
				document.getElementById('inputCostAmtYear2').value =inputCostAmtYear2+"";
				document.getElementById('inputCostAmtYear3').value =inputCostAmtYear3+"";

				document.getElementById('inputCostAmtYearRate2').value =inputCostAmtYearRate2+"";
				document.getElementById('inputCostAmtYearRate3').value =inputCostAmtYearRate3+"";
				
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
				var inputCostAmtYearRate1Value 	= document.getElementById('inputCostAmtYearRate1').value;
				var inputCostAmtYearRate2Value 	= document.getElementById('inputCostAmtYearRate2').value;
				var inputCostAmtYearRate3Value 	= document.getElementById('inputCostAmtYearRate3').value;

				//년도별 제료비
				let YCostAmtArr  = new Array();
				let YCostAmtRateArr = new Array();
				
				YCostAmtRateArr[0] 		= getNumber(inputCostAmtYearRate1Value);
				YCostAmtRateArr[1] 		= getNumber(inputCostAmtYearRate2Value);
				YCostAmtRateArr[2] 		= getNumber(inputCostAmtYearRate3Value);

				YCostAmtArr[0]			= getNumber($('[name=sMCostAmtTot]')[yMenuIndexNo].innerHTML);
				YCostAmtArr[1]			= (getNumber(inputCostMenuAmtYear2Value) * getNumber(YCostAmtRateArr[1])/100);
				YCostAmtArr[2]			= (getNumber(inputCostMenuAmtYear3Value) * getNumber(YCostAmtRateArr[2])/100);
				
				let aCostY = new Object();
 				aCostY.YCostAmt 		= YCostAmtArr;
 				aCostY.YCostRate 		= YCostAmtRateArr;
 				aCostY.YCostCd 			= "1";
 				aCostY.YCostAmtRate 	= getNumber(inputYCostAmtRateValue);
				
 				updYCost(aCostY,yMenuIndexNo);
 				
				$('#myYCostModal').modal('toggle');
		 });

		
		 $(document).on('change', 'input[name=icostRate]', function() {
			costIndexNo = $('input[name=icostRate]').index(this);
			updCost(this.value,costIndexNo);
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

 			let menuAmt = PLANF_SALE_JSON.SALE.menu;

 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		daySaleAmt = daySaleAmt + (getNumber(node.MenuPrice)*getNumber(node.MenuQty));
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

			 document.getElementById('daySaleAmt').innerHTML 	= setComma(daySaleAmt+"");
             document.getElementById('monthSaleAmt').innerHTML 	= setComma(monthSaleAmt+"");
             document.getElementById('yearSaleAmt').innerHTML 	= setComma(yearSaleAmt+"");

             calNo();
			 wrapLoadingMask("hide");

		 }

		 //매출액 월계산
		 function calMSale() {

			let menuAmt 	 = PLANF_SALE_JSON.SALE.menuM;
			
			console.log();
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
	        	menuAmt.forEach(function(node,index){
	        		menuQty01Tot = menuQty01Tot + getNumber(menuAmt[index].MMenuQty[0]);
	        		menuQty02Tot = menuQty02Tot + getNumber(menuAmt[index].MMenuQty[1]);
	        		menuQty03Tot = menuQty03Tot + getNumber(menuAmt[index].MMenuQty[2]);
	        		menuQty04Tot = menuQty04Tot + getNumber(menuAmt[index].MMenuQty[3]);
	        		menuQty05Tot = menuQty05Tot + getNumber(menuAmt[index].MMenuQty[4]);
	        		menuQty06Tot = menuQty06Tot + getNumber(menuAmt[index].MMenuQty[5]);
	        		menuQty07Tot = menuQty07Tot + getNumber(menuAmt[index].MMenuQty[6]);
	        		menuQty08Tot = menuQty08Tot + getNumber(menuAmt[index].MMenuQty[7]);
	        		menuQty09Tot = menuQty09Tot + getNumber(menuAmt[index].MMenuQty[8]);
	        		menuQty10Tot = menuQty10Tot + getNumber(menuAmt[index].MMenuQty[9]);
	        		menuQty11Tot = menuQty11Tot + getNumber(menuAmt[index].MMenuQty[10]);
	        		menuQty12Tot = menuQty12Tot + getNumber(menuAmt[index].MMenuQty[11]);

	        		menuQtyTot  =  getNumber(menuAmt[index].MMenuQty[0])
	    			              +getNumber(menuAmt[index].MMenuQty[1])
	    			              +getNumber(menuAmt[index].MMenuQty[2])
	    			              +getNumber(menuAmt[index].MMenuQty[3])
	    			              +getNumber(menuAmt[index].MMenuQty[4])
	    			              +getNumber(menuAmt[index].MMenuQty[5])
	    			              +getNumber(menuAmt[index].MMenuQty[6])
	    			              +getNumber(menuAmt[index].MMenuQty[7])
	    			              +getNumber(menuAmt[index].MMenuQty[8])
	    			              +getNumber(menuAmt[index].MMenuQty[9])
	    			              +getNumber(menuAmt[index].MMenuQty[10])
	    			              +getNumber(menuAmt[index].MMenuQty[11]);

	    			$('[name=sMmenuQtyTot]')[menuIndex].innerHTML = menuQtyTot+"";
		    			
	    			menuAmt01Tot = menuAmt01Tot + getNumber(menuAmt[index].MMenuAmt[0]);
	        		menuAmt02Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[1]);
	        		menuAmt03Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[2]);
	        		menuAmt04Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[3]);
	        		menuAmt05Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[4]);
	        		menuAmt06Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[5]);
	        		menuAmt07Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[6]);
	        		menuAmt08Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[7]);
	        		menuAmt09Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[8]);
	        		menuAmt10Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[9]);
	        		menuAmt11Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[10]);
	        		menuAmt12Tot = menuAmt02Tot + getNumber(menuAmt[index].MMenuAmt[11]);

	        		menuAmtTot  =  getNumber(menuAmt[index].MMenuAmt[0])
		              			  +getNumber(menuAmt[index].MMenuAmt[1])
		              			  +getNumber(menuAmt[index].MMenuAmt[2])
		              			  +getNumber(menuAmt[index].MMenuAmt[3])
		              			  +getNumber(menuAmt[index].MMenuAmt[4])
		              			  +getNumber(menuAmt[index].MMenuAmt[5])
		              			  +getNumber(menuAmt[index].MMenuAmt[6])
		              			  +getNumber(menuAmt[index].MMenuAmt[7])
		              			  +getNumber(menuAmt[index].MMenuAmt[8])
		              			  +getNumber(menuAmt[index].MMenuAmt[9])
		              			  +getNumber(menuAmt[index].MMenuAmt[10])
		              			  +getNumber(menuAmt[index].MMenuAmt[11]);
		
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

			let menuAmt 	 = PLANF_SALE_JSON.SALE.menuY;
			workAmt 		 = menuAmt;
			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let menuAmt02TotRate = 0;
			let menuAmt03TotRate = 0;

			let menuIndex=0;
 			if (menuAmt.length > 0) {
	        	menuAmt.forEach(function(node){
	        		menuQty01Tot = menuQty01Tot + getNumber(node.YMenuQty[0]);
	        		menuQty02Tot = menuQty02Tot + getNumber(node.YMenuQty[1]);
	        		menuQty03Tot = menuQty03Tot + getNumber(node.YMenuQty[2]);

		    			
	        		menuAmt01Tot = menuAmt01Tot + getNumber(node.YMenuAmt[0]);
	        		menuAmt02Tot = menuAmt02Tot + getNumber(node.YMenuAmt[1]);
	        		menuAmt03Tot = menuAmt03Tot + getNumber(node.YMenuAmt[2]);

	        		menuIndex++;
	  	      });
	         }	

			 document.getElementById('sYmenuQty01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sYmenuQty02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sYmenuQty03Tot').innerHTML = setComma(menuQty03Tot+"");

			 document.getElementById('sYmenuAmt01Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sYmenuAmt02Tot').innerHTML = setComma(menuAmt02Tot+"");
			 document.getElementById('sYmenuAmt03Tot').innerHTML = setComma(menuAmt03Tot+"");

			 //년도별 매출액 증가율
			 if (menuQty02Tot ==0) menuAmt02TotRate = 0 
			 menuAmt02TotRate = numFixed(((menuAmt02Tot/menuAmt01Tot)-1)*100,1);

			 if (menuQty03Tot ==0) menuAmt03TotRate = 0 
			 menuAmt03TotRate = numFixed(((menuAmt03Tot/menuAmt02Tot)-1)*100,1);

			 document.getElementById('sYmenuAmtRate02Tot').innerHTML = menuAmt02TotRate;
			 document.getElementById('sYmenuAmtRate03Tot').innerHTML = menuAmt03TotRate;
		 }
		 
		 //재료비 계산
		 function calCost() {
 			let monthCostAmt = "";
			let yearCostAmt  = "";
 			calYCost(); 
 			calMCost();

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

	     }


		 //월재료비 셋팅
		 function setMCostTable(pIndex) {

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

        	
        	costIndexValue 		=  pIndex+1;															//index
        	menuTitleValue 		=  PLANF_SALE_JSON.SALE.menu[pIndex].MenuTitle;
        	MCostMenuAmtValue	=  PLANF_SALE_JSON.SALE.menuM[pIndex].MMenuAmt[0];						//월판매금액
        	MCostMenuAmtTotValue=  PLANF_SALE_JSON.SALE.menuY[pIndex].YMenuAmt[0];						//년판매금액
        	MCostRateValue      =  PLANF_SALE_JSON.COST.cost[pIndex].costRate;  
        	let   MCostAmtArr   =  PLANF_SALE_JSON.COST.costM[pIndex].MCostAmt;
        	
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
 				MCostAmtValueValue = MCostAmtArr[i];
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
			trStr += "<td><span name='sMCostRate'>"+MCostRateValue+"</span>%</td>"
			trStr += "<td>재료비<span name='sMCostAmtTot' style='display:none'></span></td></td>";
 			for (i = 0; i < 12; i++  ){
    			trStr += "<td style='text-align:right;'>"+MCostAmt[i]+"</td>"
         	}
    		trStr += "</tr>";
 			$('#mCostTable').append(trStr);

		 }
		 //재료비 월계산
		 function calMCost() {

			let menuAmt 	 	= PLANF_SALE_JSON.COST.costM;
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

	        		//판매금액	
	    			for(j=0; j< 12; j++){
						menuCostAmtTot[j] = menuCostAmtTot[j] + getNumber(PLANF_SALE_JSON.SALE.menuM[i].MMenuAmt[j]);
					}

	    			iMCostMenuAmtTot = 0;
	    			for(j=1; j< 12; j++){
						iMCostMenuAmtTot  = iMCostMenuAmtTot+getNumber(PLANF_SALE_JSON.SALE.menuM[i].MMenuAmt[j]);
					}
	    			$('[name=sMCostMenuAmtTot]')[i].innerHTML = iMCostMenuAmtTot+"";

	    			
	    			//재료금액
	    			for(j=0; j< 12; j++){
	    				if(getNumber(node.MCostAmt[j]) != 0) {			//재료비가 있는경우만 합산
		        			costAmtTot[j] = costAmtTot[j] + getNumber(node.MCostAmt[j]);
		        		}
	    				
					}

	    			iMCostAmtTot = 0;
					for(j=1; j< 12; j++){
		        		if(getNumber(node.MCostAmt[j])!=0) {		//재료비가 있는경우만 합산
		        			iMCostAmtTot  = iMCostAmtTot+getNumber(node.MCostAmt[j]);
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
				 document.getElementById('sMCostAmt'+iTemp+'Tot').innerHTML 	= setComma(costAmtTot[i]+"");
			}

		 }

		 //연재료비 셋팅
		 function setYCostTable(pIndex) {
			
			console.log("setYCostTable");
	        let costIndexValue 	 		= "";
            let	menuTitleValue 	 		= "";
            let	MCostMenuAmtTotValue1    = "";	//메뉴별 년판매금액	
            let	MCostAmtTotValue1    	= "";	//메뉴별 년판매재료비	
            let	MCostMenuAmtTotValue2    = "";	//메뉴별 년판매금액	
            let	MCostAmtTotValue2    	= "";	//메뉴별 년판매재료비	
            let	MCostMenuAmtTotValue3    = "";	//메뉴별 년판매금액	
            let	MCostAmtTotValue3    	= "";	//메뉴별 년판매재료비	

	        let	costIndex 	 		 	= "";
	        let	menuTitle 	 		 	= "";
	        let	YCostAmtRateValue		= "";	
	        let	YCostMenuAmtYear1 	 	= "";
	        let	YCostMenuAmtYear2 	 	= "";
	        let	YCostMenuAmtYear3 	 	= "";
	        let	YCostAmtYear1  	 		= "";
	        let	YCostAmtYear2  	 		= "";
	        let	YCostAmtYear3  	 		= "";
	        
	        let sYCostAmtRate1			= "";
	        let sYCostAmtRate2			= "";
	        let sYCostAmtRate3			= "";
	        let sYCostAmtRateValue1		= "";
	        let sYCostAmtRateValue2		= "";
	        let sYCostAmtRateValue3		= "";
			 
        	var trStr="";

        	
        	costIndexValue 		  =  pIndex+1;		//index
        	menuTitleValue 		  =  PLANF_SALE_JSON.SALE.menu[pIndex].MenuTitle;
        	YCostAmtRateValue  	  =  PLANF_SALE_JSON.COST.costY[pIndex].YCostAmtRate;

        	MCostMenuAmtTotValue1 = PLANF_SALE_JSON.SALE.menuY[pIndex].YMenuAmt[0];		//메뉴별 년판매금액	
        	sYCostAmtRateValue1   = PLANF_SALE_JSON.COST.costY[pIndex].YCostRate[0];
        	
        	if(sYCostAmtRateValue1 !="")
        		MCostAmtTotValue1	  = getCalRateCostAmt(getNumber(MCostMenuAmtTotValue1),getNumber(sYCostAmtRateValue1));													//메뉴별 년재료비	
        	else 
        		MCostAmtTotValue1	  = "";													//메뉴별 년재료비	
        		
        	MCostMenuAmtTotValue2 = PLANF_SALE_JSON.SALE.menuY[pIndex].YMenuAmt[1];		//메뉴별 년판매금액	
        	sYCostAmtRateValue2   = PLANF_SALE_JSON.COST.costY[pIndex].YCostRate[1];
        	
        	if(sYCostAmtRateValue2 !="")
        		MCostAmtTotValue2	  = getCalRateCostAmt(getNumber(MCostMenuAmtTotValue2),getNumber(sYCostAmtRateValue2));													//메뉴별 년재료비	
        	else 
        		MCostAmtTotValue2	  = "";													//메뉴별 년재료비	
        	

        	MCostMenuAmtTotValue3 = PLANF_SALE_JSON.SALE.menuY[pIndex].YMenuAmt[2];		//메뉴별 년판매금액	
        	sYCostAmtRateValue3   = PLANF_SALE_JSON.COST.costY[pIndex].YCostRate[2];

        	if(sYCostAmtRateValue3 !="")
        		MCostAmtTotValue3	  = getCalRateCostAmt(getNumber(MCostMenuAmtTotValue3),getNumber(sYCostAmtRateValue3));													//메뉴별 년재료비	
        	else 
        		MCostAmtTotValue3	  = "";													//메뉴별 년재료비	
        	
        	
 			costIndex = "<span name='sYCostIndex'>"+costIndexValue+"</span>";
 			menuTitle = "<span name='sYCostTitle'>"+menuTitleValue+"</span>";
 			YCostMenuAmtYear1 = "<span name='sYCostMenuAmtYear1'>"+setComma(MCostMenuAmtTotValue1+"")+"</span>";
 			YCostMenuAmtYear2 = "<span name='sYCostMenuAmtYear2'>"+setComma(MCostMenuAmtTotValue2+"")+"</span>";
 			YCostMenuAmtYear3 = "<span name='sYCostMenuAmtYear3'>"+setComma(MCostMenuAmtTotValue3+"")+"</span>";

 			YCostAmtYear1 = "<span name='sYCostAmtYear1'>"+setComma(MCostAmtTotValue1+"")+"</span>";
 			YCostAmtYear2 = "<span name='sYCostAmtYear2'>"+setComma(MCostAmtTotValue2+"")+"</span>";
 			YCostAmtYear3 = "<span name='sYCostAmtYear3'>"+setComma(MCostAmtTotValue3+"")+"</span>";
 			
 			sYCostAmtRate1= "<span name='sYCostAmtRate1'>"+sYCostAmtRateValue1+"</span>%"
 			sYCostAmtRate2= "<span name='sYCostAmtRate2'>"+sYCostAmtRateValue2+"</span>%"
 			sYCostAmtRate3= "<span name='sYCostAmtRate3'>"+sYCostAmtRateValue3+"</span>%"
 			
			trStr="<tr>";
			trStr += "<td rowspan='3'>"+costIndex+"</td>";
			trStr += "<td rowspan='3'>"+menuTitle+"</td>";
			trStr += "<td>판매금액<span name='sYCostCd' style='display:none'>1</span><span name='sYCostAmtRate' style='display:none'>"+YCostAmtRateValue+"</span></td>";
			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostMenuAmtYear3+"</td>"
			trStr += "<td rowspan='3' style='text-align:center;'><input name='btnUpdYCost' class='btn btn-mini' value='수정' type='button' />" 
    		trStr += "</tr>";
 			$('#ycostTable').append(trStr);
			trStr="<tr>";
			trStr += "<td>재료비비율</td>";
			trStr += "<td style='text-align:right;'>"+sYCostAmtRate1+"</td>"
			trStr += "<td style='text-align:right;'>"+sYCostAmtRate2+"</td>"
			trStr += "<td style='text-align:right;'>"+sYCostAmtRate3+"</td>"
    		trStr += "</tr>";
 			$('#ycostTable').append(trStr);
			trStr="<tr>";
			trStr += "<td>재료비</td>";
			trStr += "<td style='text-align:right;'>"+YCostAmtYear1+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear2+"</td>"
			trStr += "<td style='text-align:right;'>"+YCostAmtYear3+"</td>"
    		trStr += "</tr>";
 			$('#ycostTable').append(trStr);


		 }
		 //재료비 연계산
		 function calYCost() {
            console.log("**********calYCost()*********"); 
			let menu01Tot = 0;
			let menu02Tot = 0;
			let menu03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;


			//판매금액 합계
			let menuYArr = PLANF_SALE_JSON.SALE.menuY;
 			if (menuYArr.length > 0) {
 				menuYArr.forEach(function(node){
	        		menu01Tot 	= menu01Tot + getNumber(node.YMenuAmt[0]);
	        		menu02Tot 	= menu02Tot + getNumber(node.YMenuAmt[1]);
	        		menu03Tot 	= menu03Tot + getNumber(node.YMenuAmt[2]);
 				});
	         }	

			
			//재료금액 합계
			let costYArr = PLANF_SALE_JSON.COST.costY;
 			if (costYArr.length > 0) {
 				costYArr.forEach(function(node){
	        		menuAmt01Tot = menuAmt01Tot + getNumber(node.YCostAmt[0]);
	        		menuAmt02Tot = menuAmt02Tot + getNumber(node.YCostAmt[1]);
	        		menuAmt03Tot = menuAmt03Tot + getNumber(node.YCostAmt[2]);

 				});
	         }	
 			
 			
            console.log("**********menuAmt02Tot",menuAmt02Tot); 
            console.log("**********menuAmt03Tot",menuAmt03Tot); 

			 document.getElementById('sYCostMentAmt01Tot').innerHTML = setComma(menu01Tot+"");
			 document.getElementById('sYCostMentAmt02Tot').innerHTML = setComma(menu02Tot+"");
			 document.getElementById('sYCostMentAmt03Tot').innerHTML = setComma(menu03Tot+"");

			 document.getElementById('sYCostAmt01Tot').innerHTML = setComma(menuAmt01Tot+"");
			 document.getElementById('sYCostAmt02Tot').innerHTML = setComma(menuAmt02Tot+"");
			 document.getElementById('sYCostAmt03Tot').innerHTML = setComma(menuAmt03Tot+"");
			 
			 //재료비 비율
			 document.getElementById('sYCostMentAmt01Rate').innerHTML = getCalRate(menuAmt01Tot,menu01Tot,1);
			 document.getElementById('sYCostMentAmt02Rate').innerHTML = getCalRate(menuAmt02Tot,menu02Tot,1);
			 document.getElementById('sYCostMentAmt03Rate').innerHTML = getCalRate(menuAmt03Tot,menu03Tot,1);

		 }


		 
		 
		 //재료비 테이블 셋팅
		 function setCostTable(pIndex) {
 
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

			let iCostRate  = PLANF_SALE_JSON.COST.cost[pIndex].costRate;
			
			let iMenuPrice = getNumber(PLANF_SALE_JSON.SALE.menu[pIndex].MenuPrice);
			let iMenuQty   = getNumber(PLANF_SALE_JSON.SALE.menu[pIndex].MenuQty);

			if(iCostRate!="") {
				inputCostAmtValue = getCalRateCostAmt(iMenuPrice,getNumber(iCostRate));
			}else {
				inputCostAmtValue = "";
			}
			
        	
 			inputCostTitleValue =  PLANF_SALE_JSON.SALE.menu[pIndex].MenuTitle;
 			inputCostPriceValue =  iMenuPrice;

 
 			inputCostIndex = "<span name=\"dcostIndex\">"+(pIndex+1)+"</span>";
			inputCostTitle = "<span name=\"dcostTitle\">"+inputCostTitleValue+"</span>";
			inputCostPrice = "<span name=\"dcostPrice\">"+setComma(inputCostPriceValue)+"</span>원";
			inputCostRate  = "<input name=\"icostRate\" value=\""+ iCostRate+"\" style=\"text-align:right;\" class=\"input-block-level span1 numeric\" type=\"text\" placeholder=\"비율\">%";
			inputCostAmt   = "<span name=\"dcostAmt\">"+setComma(inputCostAmtValue)+"</span>원";
			
			trStr="<tr class=\"costIndex\">";
			trStr += "<td>"+inputCostIndex+"</td>";
			trStr += "<td>"+inputCostTitle+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputCostPrice+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputCostRate+"</td>";
			trStr += "<td style=\"text-align:right;\">"+inputCostAmt+"</td>";
			trStr += "</tr>";

			$('#costTable').append(trStr);
        
			

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
				setJob(jobAddFlag,"0"+jobCd,inputJobTitleValue,inputJobCntValue,inputJobQtyValue,jobIndexNo);
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
				jobIndexNo = $('input[name=btnDelJob01]').index(this);
				delJson(3,jobIndexNo,'01');
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
				jobIndexNo = $('input[name=btnDelJob02]').index(this);
				delJson(3,jobIndexNo,'02');
				
		});

		 //인건비 계산
		 function calJob() {
			 console.log("*****calJob*********");
			 //일매출금액
            let monthJobAmt = 0;
            let yearJomAmt = 0;

            let job01TotCnt = 0;
            let job01TotQty = 0;
            let job01TotAmt = 0;

 
 			let job01Arr = PLANF_SALE_JSON.JOB.job01;
 			if (job01Arr.length > 0) {
 				job01Arr.forEach(function(node){
 					job01TotCnt	= job01TotCnt+getNumber(removeComma(node.JobCnt));
 					job01TotQty	= job01TotQty+getNumber(removeComma(node.JobQty));
 					job01TotAmt	= job01TotAmt + (getNumber(node.JobCnt) * getNumber(node.JobQty));
	  	      });
	         }	

			console.log("job01TotQty",job01TotQty);
	        document.getElementById('sJob01TotCnt').innerHTML = setComma(job01TotCnt+"");
	        document.getElementById('sJob01TotQty').innerHTML = setComma(job01TotQty+"");
	        document.getElementById('sJob01TotAmt').innerHTML = setComma(job01TotAmt+"");
            
            let job02TotCnt = 0;
            let job02TotQty = 0;
            let job02TotAmt = 0;

 			let job02Arr = PLANF_SALE_JSON.JOB.job02;
 			if (job02Arr.length > 0) {
 				job02Arr.forEach(function(node){
 					job02TotCnt   = job02TotCnt+getNumber(removeComma(node.JobCnt));
 					job02TotQty   = job02TotQty+getNumber(removeComma(node.JobQty));
 					job02TotAmt   = job02TotAmt + (getNumber(node.JobCnt) * getNumber(node.JobQty));
 	 					
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

		 //인건비년수정
		 $(document).on('click', 'input[name=btnUpdYJob]', function() {
			    yJobIndexNo = $('input[name=btnUpdYJob]').index(this);

			    sYJobCd = $('[name=sYJobCd]')[yJobIndexNo].innerHTML;
			    
			    if(sYJobCd=="2") {
			    	if(PLANF_SALE_JSON.JOB.job01.length > 0) yJobIndexNo = yJobIndexNo - PLANF_SALE_JSON.JOB.job01.length;
			    }
			    
			 	initPopYJob(sYJobCd);
				
				$('#myYJobModal').modal('toggle');
		});
		//연판매관리비 팝업 셋팅	
		function initPopYJob(pJobCd) {
		 	
			let jobStr  ='job0'+pJobCd;
			let jobMStr ='job0'+pJobCd+"M";
			let jobYStr ='job0'+pJobCd+"Y";

			document.getElementById('YJobTitle').innerHTML = PLANF_SALE_JSON.JOB[jobStr][yJobIndexNo].JobTitle;
			let pCd=PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmtCd;
			
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
		 	document.getElementById('inputYJobCd').value   	  	= pJobCd;
		 	document.getElementById('inputYJobCnt').value   	= PLANF_SALE_JSON.JOB[jobStr][yJobIndexNo].JobCnt;
		 	document.getElementById('inputYJobAmtCd').value   	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmtCd;
		 	document.getElementById('inputYJobRate').value    	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmtRate;
		 	document.getElementById('inputYJobYear1').value   	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmt[0];
			document.getElementById('inputYJobYear2').value   	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmt[1];
			document.getElementById('inputYJobYear3').value   	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobAmt[2];
		 	document.getElementById('inputYJobRateAmt1').value 	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobRateAmt[0];
			document.getElementById('inputYJobRateAmt2').value 	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobRateAmt[1];
			document.getElementById('inputYJobRateAmt3').value 	= PLANF_SALE_JSON.JOB[jobYStr][yJobIndexNo].YJobRateAmt[2];

		}
		//연도별 판매관리비팝업 입력방법선택	
		$('#inputYJobCd').on('change', function(e) {
			initPopYJob(this.value);
		});

		//연인건비 수정
		document.getElementById('btnAddYJob').addEventListener('click',function(e){
			console.log("*****btnAddYJob*******");

			
	        let inputYJobAmtCd 			= document.getElementById('inputYJobAmtCd');        
	        let inputYJobCdValue 		= document.getElementById('inputYJobCd').value;        
			var inputYJobAmtCdValue 	= inputYmenuCd.options[inputYJobAmtCd.selectedIndex].value;


			
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
			var inputYJobCntValue 		    = document.getElementById('inputYJobCnt').value;
			var inputYJobYear1Value 		= removeComma(document.getElementById('inputYJobYear1').value);
			var inputYJobYear2Value 		= removeComma(document.getElementById('inputYJobYear2').value);
			var inputYJobYear3Value 		= removeComma(document.getElementById('inputYJobYear3').value);
			var iinputYJobRateAmt1Value 	= removeComma(document.getElementById('inputYJobRateAmt1').value);
			var iinputYJobRateAmt2Value 	= removeComma(document.getElementById('inputYJobRateAmt2').value);
			var iinputYJobRateAmt3Value 	= removeComma(document.getElementById('inputYJobRateAmt3').value);

			let aJobY 			= new Object();
			let jobYArr 		= new Array();
			let jobYRateAmtArr 	= new Array();
			
			jobYArr[0]			= getNumber(inputYJobYear1Value);
			jobYArr[1]			= getNumber(inputYJobYear2Value);
			jobYArr[2]			= getNumber(inputYJobYear3Value);

			jobYRateAmtArr[0]	= getNumber(iinputYJobRateAmt1Value);
			jobYRateAmtArr[1]	= getNumber(iinputYJobRateAmt2Value);
			jobYRateAmtArr[2]	= getNumber(iinputYJobRateAmt3Value);
			
	 		aJobY.YJobAmtCd=inputYJobAmtCdValue;
	 		aJobY.YJobAmtRate=getNumber(inputYJobRateValue);
	 		aJobY.YJobAmt=jobYArr;
	 		aJobY.YJobRateAmt=jobYRateAmtArr;
			
	 		updYJob(aJobY,yJobIndexNo,"0"+inputYJobCdValue);
			
			$('#myYJobModal').modal('toggle');
	     });

		 //연도별 판매관리비 증가율 계산 
		 $('#inputYJobRate').on('change', function(e) {
			if (this.value != ""){

				var inputYJobRateValue 		= document.getElementById('inputYJobRate').value;
				var inputYJobYear1Value 	= document.getElementById('inputYJobYear1').value;
				var inputYJobYear2Value 	= document.getElementById('inputYJobYear2').value;
				var inputYJobYear3Value 	= document.getElementById('inputYJobYear3').value;
				var inputYJobCntValue 		= document.getElementById('inputYJobCnt').value;

				let iinputYJobYear1 = getNumber(inputYJobYear1Value);
				let iinputYJobYear2 = 0;
				let iinputYJobYear3 = 0;

				let iinputYJobRateValue = getNumber(inputYJobRateValue);

				let inputYJobRateAmt1Value = getNumber(document.getElementById('inputYJobRateAmt1').value);
				let inputYJobRateAmt2Value = 0;
				let inputYJobRateAmt3Value = 0;

				inputYJobRateAmt2Value = (inputYJobRateAmt1Value * (1+(iinputYJobRateValue/100)))/10000 ;
				inputYJobRateAmt2Value = Math.floor(inputYJobRateAmt2Value)*10000;
				
				inputYJobRateAmt3Value = (inputYJobRateAmt2Value * (1+(iinputYJobRateValue/100)))/10000 ;
				inputYJobRateAmt3Value = Math.floor(inputYJobRateAmt3Value)*10000;
				
				document.getElementById('inputYJobRateAmt2').value =inputYJobRateAmt2Value+"";
				document.getElementById('inputYJobRateAmt3').value =inputYJobRateAmt3Value+"";

				
				let jobCnt=getNumber(inputYJobCntValue);
				
				iinputYJobYear2 = (inputYJobRateAmt2Value*jobCnt*12/100000) ;
				iinputYJobYear3 = (inputYJobRateAmt3Value*jobCnt*12/100000) ;

				iinputYJobYear2=Math.floor(iinputYJobYear2)*100;
				iinputYJobYear3=Math.floor(iinputYJobYear3)*100;
				
				document.getElementById('inputYJobYear2').value =iinputYJobYear2+"";
				document.getElementById('inputYJobYear3').value =iinputYJobYear3+"";

			}
		 });

		 //인건비 월계산
		 function calMJob() {
			 
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
			iMJobCnt1 = 0;
			let job01Arr = PLANF_SALE_JSON.JOB.job01;
 			if (job01Arr.length > 0) {
 				job01Arr.forEach(function(node){
 					iMJobCnt1	= iMJobCnt1+getNumber(removeComma(node.JobCnt));
	  	      });
	        }	
			
			//월계산
			let job01MArr = PLANF_SALE_JSON.JOB.job01M;
			if(job01MArr.length > 0) {
				job01MArr.forEach(function(node,index){
					//각월별 합계
					for(i=0; i< 12; i++){
						costAmtSub1Tot[i]= costAmtSub1Tot[i] + getNumber(node.MJobAmt[i]); 
					}
					//1월 ~12 월합계
					iJobAmtTot =0;
					iJobAmtTot = node.MJobAmt.reduce((acc,curr) => acc+curr,0); 
					$('[name=sMJobTot]')[index].innerHTML = iJobAmtTot+"";
				});
			}		
			
 			
			
			
			for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
				 document.getElementById('sMJobAmt'+iTemp+'Sub1Tot').innerHTML = setComma(costAmtSub1Tot[i]+"");
			}

			document.getElementById('sMJobCnt01Sub1Tot').innerHTML = setComma(iMJobCnt1+"");

			//판매
			//인원계산
			let job02Arr = PLANF_SALE_JSON.JOB.job02;
 			if (job02Arr.length > 0) {
 				job02Arr.forEach(function(node){
 					iMJobCnt2	= iMJobCnt1+getNumber(removeComma(node.JobCnt));
	  	      });
 			}	
			//월계산
			for(i=0; i< 12; i++){
				costAmtSub2Tot[i] =0;
			}

			//월계산
			let job02MArr = PLANF_SALE_JSON.JOB.job02M;
			if(job02MArr.length > 0) {
				job02MArr.forEach(function(node,index){
					for(i=0; i< 12; i++){
						costAmtSub2Tot[i]= costAmtSub2Tot[i] + getNumber(node.MJobAmt[i]); 
					}

					//1월 ~12 월합계
					iJobAmtTot =0;
					iJobAmtTot = node.MJobAmt.reduce((acc,curr) => acc+curr,0); 
					$('[name=sMJobTot]')[index].innerHTML = iJobAmtTot+"";

				});
			}		
			
			 for(i=0; i< 12; i++){
        		if(i < 9) iTemp ="0"+(i+1);
        		else iTemp = (i+1);
        		document.getElementById('sMJobAmt'+iTemp+'Sub2Tot').innerHTML = setComma(costAmtSub2Tot[i]+"");
			}

			document.getElementById('sMJobCnt01Sub2Tot').innerHTML = setComma(iMJobCnt2+"");

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
			
			
			
			//소계 계산
			iYJobCnt1 = 0;
			let job01Arr = PLANF_SALE_JSON.JOB.job01;
 			if (job01Arr.length > 0) {
 				job01Arr.forEach(function(node){
 					iYJobCnt1	= iYJobCnt1+getNumber(removeComma(node.JobCnt));
	  	      });
	        }	

			
			let job01YArr = PLANF_SALE_JSON.JOB.job01Y;
			if(job01YArr.length > 0) {
				menuAmt01Sub1Tot = 0;
				menuAmt02Sub1Tot = 0;
				menuAmt03Sub1Tot = 0;
				job01YArr.forEach(function(node,index){
					node.YJobAmt.forEach(function(item,i){
						    if (i==0) menuAmt01Sub1Tot = menuAmt01Sub1Tot +getNumber(item);
						    if (i==1) menuAmt02Sub1Tot = menuAmt02Sub1Tot +getNumber(item);
						    if (i==2) menuAmt03Sub1Tot = menuAmt03Sub1Tot +getNumber(item);
						}
					)
				});
			}		

			document.getElementById('sYJobCntSub1Tot').innerHTML = setComma(iYJobCnt1+"");
			document.getElementById('sYJobAmt01Sub1Tot').innerHTML = setComma(menuAmt01Sub1Tot+"");
			document.getElementById('sYJobAmt02Sub1Tot').innerHTML = setComma(menuAmt02Sub1Tot+"");
			document.getElementById('sYJobAmt03Sub1Tot').innerHTML = setComma(menuAmt03Sub1Tot+"");


			iYJobCnt2 = 0;
			let job02Arr = PLANF_SALE_JSON.JOB.job02;
 			if (job02Arr.length > 0) {
 				job02Arr.forEach(function(node){
 					iYJobCnt2	= iYJobCnt2+getNumber(removeComma(node.JobCnt));
	  	      });
	        }	

			let job02YArr = PLANF_SALE_JSON.JOB.job02Y;
			if(job02YArr.length > 0) {
				menuAmt01Sub2Tot = 0;
				menuAmt02Sub2Tot = 0;
				menuAmt03Sub2Tot = 0;
				job02YArr.forEach(function(node,index){
					node.YJobAmt.forEach(function(item,i){
						    if (i==0) menuAmt01Sub2Tot = menuAmt01Sub2Tot +getNumber(item);
						    if (i==1) menuAmt02Sub2Tot = menuAmt02Sub2Tot +getNumber(item);
						    if (i==2) menuAmt03Sub2Tot = menuAmt03Sub2Tot +getNumber(item);
						}
					)
				});
			}		
			

			 document.getElementById('sYJobCntSub2Tot').innerHTML = setComma(iYJobCnt2+"");
			 document.getElementById('sYJobAmt01Sub2Tot').innerHTML = setComma(menuAmt01Sub2Tot+"");
			 document.getElementById('sYJobAmt02Sub2Tot').innerHTML = setComma(menuAmt02Sub2Tot+"");
			 document.getElementById('sYJobAmt03Sub2Tot').innerHTML = setComma(menuAmt03Sub2Tot+"");
			 

			 document.getElementById('sYJobCntTot').innerHTML = setComma((iYJobCnt1+iYJobCnt2)+"");
			 document.getElementById('sYJobAmt01Tot').innerHTML = setComma((menuAmt01Sub1Tot+menuAmt01Sub2Tot)+"");
			 document.getElementById('sYJobAmt02Tot').innerHTML = setComma((menuAmt02Sub1Tot+menuAmt02Sub2Tot)+"");
			 document.getElementById('sYJobAmt03Tot').innerHTML = setComma((menuAmt03Sub1Tot+menuAmt03Sub2Tot)+"");
			 
			 //인건비 비율
			 let iYJobAmt01Tot = getNumber(menuAmt01Sub1Tot)+getNumber(menuAmt01Sub2Tot);
			 let iYJobAmt02Tot = getNumber(menuAmt02Sub1Tot)+getNumber(menuAmt02Sub2Tot);
			 let iYJobAmt03Tot = getNumber(menuAmt03Sub1Tot)+getNumber(menuAmt03Sub2Tot);

			 let iYJobAmt01TotRate = 0;
			 let iYJobAmt02TotRate = 0;
			 let iYJobAmt03TotRate = 0;
			 
			 let aMenuYamtArr = aMenuYamtArrFn();
			 if(aMenuYamtArr[0] == 0) iYJobAmt01TotRate=0;
			 iYJobAmt01TotRate = getCalRate(iYJobAmt01Tot,aMenuYamtArr[0],1);

			 if(aMenuYamtArr[1] == 0) iYJobAmt02TotRate=0;
			 iYJobAmt02TotRate = getCalRate(iYJobAmt02Tot,aMenuYamtArr[1],1);

			 if(aMenuYamtArr[2] == 0) iYJobAmt03TotRate=0;
			 iYJobAmt03TotRate = getCalRate(iYJobAmt03Tot,aMenuYamtArr[2],1);
			 
			 document.getElementById('sYJobAmt01TotRate').innerHTML = iYJobAmt01TotRate+"";
			 document.getElementById('sYJobAmt02TotRate').innerHTML = iYJobAmt02TotRate+"";
			 document.getElementById('sYJobAmt03TotRate').innerHTML = iYJobAmt03TotRate+"";

			 
			 let iYJobCntTot = getNumber(iYJobCnt1)+getNumber(iYJobCnt2);
			 //1인당 매출액
			 let iYJobAmt01Tot1Amt = 0;
			 let iYJobAmt02Tot1Amt = 0;
			 let iYJobAmt03Tot1Amt = 0;

			 if(iYJobCntTot == 0) iYJobAmt01Tot1Amt = 0;
			 else iYJobAmt01Tot1Amt = numFixed(aMenuYamtArr[0]/iYJobCntTot,0);
			 
			 if(iYJobCntTot == 0) iYJobAmt02Tot1Amt = 0;
			 else iYJobAmt02Tot1Amt = numFixed(aMenuYamtArr[1]/iYJobCntTot,0);

			 if(iYJobCntTot == 0) iYJobAmt03Tot1Amt = 0;
			 else iYJobAmt03Tot1Amt = numFixed(aMenuYamtArr[2]/iYJobCntTot,0);

			 document.getElementById('sYJobAmt01Tot1Amt').innerHTML = setComma(iYJobAmt01Tot1Amt+"");
			 document.getElementById('sYJobAmt02Tot1Amt').innerHTML = setComma(iYJobAmt02Tot1Amt+"");
			 document.getElementById('sYJobAmt03Tot1Amt').innerHTML = setComma(iYJobAmt03Tot1Amt+"");

			 
			 //1인당 인건비
			 let iYJobAmt01TotJob1Amt = 0;
			 let iYJobAmt02TotJob1Amt = 0;
			 let iYJobAmt03TotJob1Amt = 0;
			 
			 if(iYJobCntTot == 0) iYJobAmt01TotJob1Amt = 0;
			 else iYJobAmt01TotJob1Amt = numFixed(iYJobAmt01Tot/iYJobCntTot,0);

			 if(iYJobCntTot == 0) iYJobAmt02TotJob1Amt = 0;
			 else iYJobAmt02TotJob1Amt = numFixed(iYJobAmt02Tot/iYJobCntTot,0);
			 
			 if(iYJobCntTot == 0) iYJobAmt03TotJob1Amt = 0;
			 else iYJobAmt03TotJob1Amt = numFixed(iYJobAmt03Tot/iYJobCntTot,0);
			 
			 document.getElementById('sYJobAmt01TotJob1Amt').innerHTML = setComma(iYJobAmt01TotJob1Amt+"");
			 document.getElementById('sYJobAmt02TotJob1Amt').innerHTML = setComma(iYJobAmt02TotJob1Amt+"");
			 document.getElementById('sYJobAmt03TotJob1Amt').innerHTML = setComma(iYJobAmt03TotJob1Amt+"");
			 
			 
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

			$("#inputInvestYear").attr("readonly", false);

			
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
				
				var inputInvestYn    			= document.getElementById('inputInvestYn').value;
				if(inputInvestYn=="Y") {
					if (document.getElementById('inputInvestYear').value == "" || document.getElementById('inputInvestYear').value == "0") {
						alert("삼각년수을 입력하세요");
						 document.getElementById("inputInvestYear").focus();
						return;
					}
				}
				
				var inputInvestBizcdCodeM 	= "";
				var inputInvestTitle 		= "";
				var inputInvestPirce 		= "";
				var inputInvestYear 		= "0";
				var inputInvestAmt 			= "0";

				var inputInvestBizcdCodeMValue  = document.getElementById('inputInvestBizcdCodeM').value;
				var inputInvestTitleValue  		= $("#inputInvestTitle option:selected").text();
				var inputInvestTitleCdValue  	= $("#inputInvestTitle option:selected").val();
				var inputInvestPirceValue    	= document.getElementById('inputInvestPirce').value;
				var inputInvestYearValue   		= document.getElementById('inputInvestYear').value;
				var inputInvestAmtValue    		= 0;

				
				if(inputInvestYn == "Y") {
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

				let aInvest  = new Object();
				aInvest.investTitle 	= inputInvestTitleValue;
				aInvest.investTitleCd   = inputInvestTitleCdValue;
				aInvest.investPrice 	= inputInvestPirceValue;
				aInvest.investYear 		= inputInvestYearValue;
				aInvest.investYearAmt 	= inputInvestAmtValue;
				aInvest.investYn 		= inputInvestYn;				
				
				setInvest(investAddFlag,investNo,aInvest,investIndexNo);
				
				$('#myInvestModal').modal('toggle');
		 });

		$('#inputInvestTitle').on('change', function(e) {
			let costcdCodeM = this.value;
			let investYn = getInvestYn(costcdCodeM,investCd);
			
			document.getElementById('inputInvestYn').value = investYn;
			
			if(investYn == "N") {
				document.getElementById('inputInvestYear').value	="0";
				document.getElementById('inputInvestYearAmt').value ="0";
				$("#inputInvestYear").attr("readonly", true);
			} else{
				$("#inputInvestYear").attr("readonly", false);
			}
			
			
		});
		 
		function getInvestYn (pCostcdCodeM,pInvestCd) {
			let sReturn= "N";
			let sNode;
			
			if(pInvestCd == "1"){
				sNode = invest1Json.filter(function(node){
					if(pCostcdCodeM == node.CODE)
					return true;
				});
				sReturn = sNode[0].ATTRB_1;
				if(sReturn!="Y") sReturn="N";
			}
			if(pInvestCd == "2"){
				sNode = invest2Json.filter(function(node){
					if(pCostcdCodeM == node.CODE)
					return true;
				});
				sReturn = sNode[0].ATTRB_1;
				if(sReturn!="Y") sReturn="N";
			}
			
			return sReturn;
			
		} 
		
		
		$(document).on('click', 'input[name=btnUpdInvest01]', function() {
			 	investAddFlag="UPD";
			 	investCd = "1";
			 	bizCd    = "1";
				document.getElementById('btnAddInvest').value="수정";
				$('#inputInvestTitle').html(invest1ComboStr);
				$('#dInvestYear').show();

				
			 	investIndexNo = $('input[name=btnUpdInvest01]').index(this);

			 	document.getElementById('inputInvestBizcdCodeM').value = bizCd;
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest01[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest01[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest01[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest01[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest01[investIndexNo].investYn;
				
				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest01]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest01]').index(this);
			 	delJson(4,investIndexNo,"01",investCd);
				
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest02[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest02[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest02[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest02[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest02[investIndexNo].investYn;

				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}
				
				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest02]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "1"
				investIndexNo = $('input[name=btnDelInvest02]').index(this);
			 	delJson(4,investIndexNo,"02",investCd);
			 	
				
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest03[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest03[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest03[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest03[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest03[investIndexNo].investYn;

				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest03]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "2"
				investIndexNo = $('input[name=btnDelInvest03]').index(this);
			 	delJson(4,investIndexNo,"01",investCd);
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest04[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest04[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest04[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest04[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest04[investIndexNo].investYn;
				
				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest04]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "2"
				investIndexNo = $('input[name=btnDelInvest04]').index(this);
			 	delJson(4,investIndexNo,"02",investCd);
				
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest05[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest05[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest05[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest05[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investYn;
				
				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');

				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest05]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "3"
				investIndexNo = $('input[name=btnDelInvest05]').index(this);
			 	delJson(4,investIndexNo,"01",investCd);
				
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest06[investIndexNo].investYn;

				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');

		});


		 $(document).on('click', 'input[name=btnDelInvest06]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "3"
				investIndexNo = $('input[name=btnDelInvest06]').index(this);
			 	delJson(4,investIndexNo,"02",investCd);
				
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest07[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest07[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest07[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest07[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest07[investIndexNo].investYn;

				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');

		});


		 $(document).on('click', 'input[name=btnDelInvest07]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "4"
				investIndexNo = $('input[name=btnDelInvest07]').index(this);
			 	delJson(4,investIndexNo,"01",investCd);
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
			 	document.getElementById('inputInvestTitle').value      = PLANF_SALE_JSON.INVEST.invest08[investIndexNo].investTitleCd;
				document.getElementById('inputInvestPirce').value      = PLANF_SALE_JSON.INVEST.invest08[investIndexNo].investPrice;
				document.getElementById('inputInvestYear').value   	   = PLANF_SALE_JSON.INVEST.invest08[investIndexNo].investYear;
				document.getElementById('inputInvestYearAmt').value    = PLANF_SALE_JSON.INVEST.invest08[investIndexNo].investYearAmt;
				document.getElementById('inputInvestYn').value   	   = PLANF_SALE_JSON.INVEST.invest08[investIndexNo].investYn;

				let investYn = document.getElementById('inputInvestYn').value;
				if(investYn == "N") {
					$("#inputInvestYear").attr("readonly", true);
				} else{
					$("#inputInvestYear").attr("readonly", false);
				}

				$('#myInvestModal').modal('toggle');
		});


		 $(document).on('click', 'input[name=btnDelInvest08]', function() {
				if(!confirm('투자비항목을 삭제하시겠습니까?')){
					return;
				}
			 	investCd = "4"
				investIndexNo = $('input[name=btnDelInvest08]').index(this);
			 	delJson(4,investIndexNo,"02",investCd);
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


			//1.유형자산 제조
 			let invest01Arr = PLANF_SALE_JSON.INVEST.invest01;
 			if (invest01Arr.length > 0) {
 				invest01Arr.forEach(function(node){
 					invest01TotPrice = invest01TotPrice + getNumber(removeComma(node.investPrice));
 					invest01TotAmt   = invest01TotAmt + getNumber(removeComma(node.investYearAmt));
	  	      });
	         }				
	        document.getElementById('dInvest01TotPrice').innerHTML = setComma(invest01TotPrice+"");
	        document.getElementById('dInvest01TotAmt').innerHTML = setComma(invest01TotAmt+"");

			//2.유형자산 판매
 			let invest02Arr = PLANF_SALE_JSON.INVEST.invest02;
 			if (invest02Arr.length > 0) {
 				invest02Arr.forEach(function(node){
 					invest02TotPrice = invest02TotPrice + getNumber(removeComma(node.investPrice));
 					invest02TotAmt   = invest02TotAmt + getNumber(removeComma(node.investYearAmt));
	  	      });
	         }				
	        document.getElementById('dInvest02TotPrice').innerHTML = setComma(invest02TotPrice+"");
	        document.getElementById('dInvest02TotAmt').innerHTML = setComma(invest02TotAmt+"");
	        
			//3.무형자산 제조
 			let invest03Arr = PLANF_SALE_JSON.INVEST.invest03;
 			if (invest03Arr.length > 0) {
 				invest03Arr.forEach(function(node){
 					invest03TotPrice = invest03TotPrice + getNumber(removeComma(node.investPrice));
 					invest03TotAmt   = invest03TotAmt + getNumber(removeComma(node.investYearAmt));
	  	      });
	         }				
	        document.getElementById('dInvest03TotPrice').innerHTML = setComma(invest03TotPrice+"");
	        document.getElementById('dInvest03TotAmt').innerHTML = setComma(invest03TotAmt+"");

			//4.무형자산 판매
 			let invest04Arr = PLANF_SALE_JSON.INVEST.invest04;
 			if (invest04Arr.length > 0) {
 				invest04Arr.forEach(function(node){
 					invest04TotPrice = invest04TotPrice + getNumber(removeComma(node.investPrice));
 					invest04TotAmt   = invest04TotAmt + getNumber(removeComma(node.investYearAmt));
	  	      });
	         }				
	        document.getElementById('dInvest04TotPrice').innerHTML = setComma(invest04TotPrice+"");
	        document.getElementById('dInvest04TotAmt').innerHTML = setComma(invest04TotAmt+"");
	        
			//5.기타유동자산 제조
 			let invest05Arr = PLANF_SALE_JSON.INVEST.invest05;
 			if (invest05Arr.length > 0) {
 				invest05Arr.forEach(function(node){
 					invest05TotPrice = invest05TotPrice + getNumber(removeComma(node.investPrice));
	  	      });
	         }				
	        document.getElementById('dInvest05TotPrice').innerHTML = setComma(invest05TotPrice+"");

			//6.기타유동자산 판매
 			let invest06Arr = PLANF_SALE_JSON.INVEST.invest06;
 			if (invest06Arr.length > 0) {
 				invest06Arr.forEach(function(node){
 					invest06TotPrice = invest06TotPrice + getNumber(removeComma(node.investPrice));
	  	      });
	         }				
	        document.getElementById('dInvest06TotPrice').innerHTML = setComma(invest06TotPrice+"");

			//7.기타투자비용 제조
 			let invest07Arr = PLANF_SALE_JSON.INVEST.invest07;
 			if (invest07Arr.length > 0) {
 				invest07Arr.forEach(function(node){
 					invest07TotPrice = invest07TotPrice + getNumber(removeComma(node.investPrice));
	  	      });
	         }				
	        document.getElementById('dInvest07TotPrice').innerHTML = setComma(invest07TotPrice+"");

			//7.기타투자비용 판매
 			let invest08Arr = PLANF_SALE_JSON.INVEST.invest08;
 			if (invest08Arr.length > 0) {
 				invest08Arr.forEach(function(node){
 					invest08TotPrice = invest08TotPrice + getNumber(removeComma(node.investPrice));
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

				if(loanCd=="2") {
					if (document.getElementById("inputLoanYear1").value !="" && document.getElementById('inputLoanRate1').value=="") {
						alert("년도별 이자율 입력하세요.");
						document.getElementById("inputLoanRate1").focus();
						return;
					}
					
					if (document.getElementById("inputLoanRate1").value !="" && document.getElementById('inputLoanRate2').value=="") {
						document.getElementById('inputLoanRate2').value=document.getElementById("inputLoanRate1").value;
					}

					if (document.getElementById("inputLoanRate1").value !="" && document.getElementById('inputLoanRate3').value=="") {
						document.getElementById('inputLoanRate3').value=document.getElementById("inputLoanRate1").value;
					}
					
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
					
					if(inputLoanRate2Value== "") inputLoanRate2Value = inputLoanRate1Value;
					if(inputLoanRate3Value== "") inputLoanRate3Value = inputLoanRate1Value;

					inputLoanYearRate1Value = 0;
					inputLoanYearRate2Value = 0;
					inputLoanYearRate3Value = 0;

					let loanRateAmt = 0;
					if(inputLoanRate1Value !="" && inputLoanYear1Value !=""){
						loanRateAmt = getNumber(inputLoanYear1Value);

						inputLoanYearRate1Value = getNumber(loanRateAmt) * (getNumber(inputLoanRate1Value)/100)
						inputLoanYearRate1Value = numFixed(inputLoanYearRate1Value,0);
					}
					
					if(inputLoanRate2Value !="" || inputLoanYear2Value !=""){
						loanRateAmt = getNumber(inputLoanYear1Value)+getNumber(inputLoanYear2Value);
						inputLoanYearRate2Value = getNumber(loanRateAmt) * (getNumber(inputLoanRate2Value)/100)
						inputLoanYearRate2Value = numFixed(inputLoanYearRate2Value,0);
					}
					
					
					if(inputLoanRate3Value !="" || inputLoanYear3Value !=""){
						loanRateAmt = getNumber(inputLoanYear1Value)+getNumber(inputLoanYear2Value)+getNumber(inputLoanYear3Value);
						inputLoanYearRate3Value = getNumber(loanRateAmt) * (getNumber(inputLoanRate3Value)/100)
						inputLoanYearRate3Value = numFixed(inputLoanYearRate3Value,0);
					}

				}
				 
				let loanYearArr 	= new Array();
				let loanRateArr 	= new Array();
				let loanRateAmtArr 	= new Array();
				let aLoan;
				aLoan = new Object();
				
				aLoan.loanTitle   = inputLoanTitleValue;
				aLoan.loanTitleCd = inputLoanTitleValue;
								
				loanYearArr.push(inputLoanYear1Value);
				loanYearArr.push(inputLoanYear2Value);
				loanYearArr.push(inputLoanYear3Value);
				aLoan.loanYear = loanYearArr;
				
				if(loanCd=="2") {
					loanRateArr.push(inputLoanRate1Value);
					loanRateArr.push(inputLoanRate1Value);
					loanRateArr.push(inputLoanRate1Value);
					aLoan.loanRate = loanRateArr;
					
					loanRateAmtArr.push(inputLoanYearRate1Value);
					loanRateAmtArr.push(inputLoanYearRate2Value);
					loanRateAmtArr.push(inputLoanYearRate3Value);
					aLoan.loanRateAmt = loanRateAmtArr;

				}
				
				setLoan(loanAddFlag,loanCd,aLoan,loanIndexNo);
				
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


		$(document).on("change","#inputLoanRate1",function(e){  
			if (this.value !="" && document.getElementById('inputLoanRate2').value=="") {
				document.getElementById('inputLoanRate2').value=this.value;
			}

			if (this.value !="" && document.getElementById('inputLoanRate3').value=="") {
				document.getElementById('inputLoanRate3').value=this.value;
			}
		});

		$(document).on('click', 'input[name=btnDelLoan01]', function() {
				if(!confirm('자본항목을 삭제하시겠습니까?')){
					return;
				}
			 	loanCd = "1"
				loanIndexNo = $('input[name=btnDelLoan01]').index(this);
			 	
			 	delJson(5,loanIndexNo,loanCd);
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
			 	delJson(5,loanIndexNo,loanCd);
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
            
 			let loan01Year = PLANF_SALE_JSON.LOAN.loan01;
 			let loan02Year = PLANF_SALE_JSON.LOAN.loan02;
 			let loanIndex = 0;

			//1.자기자금계산
 			loanIndex = 0;
 			if (loan01Year.length > 0) {
 				loan01Year.forEach(function(node){
 	 				if(!isEmpty(node.loanYear[0])) loan01TotYear1Amt = loan01TotYear1Amt + getNumber(node.loanYear[0]);
 	 				if(!isEmpty(node.loanYear[1])) loan01TotYear2Amt = loan02TotYear1Amt + getNumber(node.loanYear[1]);
 	 				if(!isEmpty(node.loanYear[2])) loan01TotYear3Amt = loan03TotYear1Amt + getNumber(node.loanYear[2]);
	  	      });
	         }	
	        
 			if(loan01TotYear1Amt==0) document.getElementById('dLoan01TotYear1').innerHTML="";
 			else document.getElementById('dLoan01TotYear1').innerHTML = setComma(loan01TotYear1Amt+"");
	        
 			if(loan01TotYear2Amt==0) document.getElementById('dLoan01TotYear2').innerHTML="";
 			else document.getElementById('dLoan01TotYear2').innerHTML = setComma(loan01TotYear2Amt+"");
 			
 			if(loan01TotYear3Amt==0) document.getElementById('dLoan01TotYear3').innerHTML="";
 			else document.getElementById('dLoan01TotYear3').innerHTML = setComma(loan01TotYear3Amt+"");

			//2.타인자금계산
 			loanIndex = 0;
 			if (loan02Year.length > 0) {
 				loan02Year.forEach(function(node){
	 				if(!isEmpty(node.loanYear[0])) loan02TotYear1Amt = loan02TotYear1Amt + getNumber(node.loanYear[0]);
	 				if(!isEmpty(node.loanYear[1])) loan02TotYear2Amt = loan02TotYear2Amt + getNumber(node.loanYear[1]);
	 				if(!isEmpty(node.loanYear[2])) loan02TotYear3Amt = loan02TotYear2Amt + getNumber(node.loanYear[2]);

	 				if(!isEmpty(node.loanRateAmt[0])) loan02TotYearRate1Amt = loan02TotYearRate1Amt + getNumber(node.loanRateAmt[0]);
	 				if(!isEmpty(node.loanRateAmt[1])) loan02TotYearRate2Amt = loan02TotYearRate2Amt + getNumber(node.loanRateAmt[1]);
	 				if(!isEmpty(node.loanRateAmt[2])) loan02TotYearRate3Amt = loan02TotYearRate3Amt + getNumber(node.loanRateAmt[2]);
 				});
	         }	
	        
 			if(loan02TotYear1Amt==0) document.getElementById('dLoan02TotYear1').innerHTML="";
 			else document.getElementById('dLoan02TotYear1').innerHTML = setComma(loan02TotYear1Amt+"");
 			
 			if(loan02TotYear2Amt==0) document.getElementById('dLoan02TotYear2').innerHTML="";
 			else document.getElementById('dLoan02TotYear2').innerHTML = setComma(loan02TotYear2Amt+"");
 			
 			if(loan02TotYear3Amt==0) document.getElementById('dLoan02TotYear3').innerHTML="";
	        document.getElementById('dLoan02TotYear3').innerHTML = setComma(loan02TotYear3Amt+"");
	       
	        
 			if(loan02TotYearRate1Amt==0) document.getElementById('dLoan02TotYearRate1').innerHTML="";
 			else document.getElementById('dLoan02TotYearRate1').innerHTML = setComma(loan02TotYearRate1Amt+"");

 			if(loan02TotYearRate2Amt==0) document.getElementById('dLoan02TotYearRate2').innerHTML="";
 			else document.getElementById('dLoan02TotYearRate2').innerHTML = setComma(loan02TotYearRate2Amt+"");

 			if(loan02TotYearRate3Amt==0) document.getElementById('dLoan02TotYearRate3').innerHTML="";
 			else document.getElementById('dLoan02TotYearRate3').innerHTML = setComma(loan02TotYearRate3Amt+"");


	        //합계계산
            let loanTotYear1Amt =0;
            let loanTotYear2Amt =0;
            let loanTotYear3Amt =0;

            loanTotYear1Amt = loan01TotYear1Amt + loan02TotYear1Amt;
            loanTotYear2Amt = loan01TotYear2Amt + loan02TotYear2Amt;
            loanTotYear3Amt = loan01TotYear3Amt + loan02TotYear3Amt;
            
            if(loanTotYear1Amt==0) document.getElementById('loanTotYear1Amt').innerHTML = "";
            else document.getElementById('loanTotYear1Amt').innerHTML = setComma(loanTotYear1Amt+"");
	        
            if(loanTotYear2Amt==0) document.getElementById('loanTotYear2Amt').innerHTML = "";
            else document.getElementById('loanTotYear2Amt').innerHTML = setComma(loanTotYear2Amt+"");
            
            if(loanTotYear3Amt==0) document.getElementById('loanTotYear3Amt').innerHTML = "";
            else document.getElementById('loanTotYear3Amt').innerHTML = setComma(loanTotYear3Amt+"");

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

				if (document.getElementById('inputMCostTVa').value == "") {
					alert("적용값을입력하세요 ");
					 document.getElementById("inputMCostTVa").focus();
					return;
				}

				var inputMcostcdCodeM 	= "";
				var sMCostCdValue 		= "";
				var inputMCostTV 		= "";
				var MCostTVaAmt 		= "";
				var iMCostTVaAmt 		= 0;
				let McostNo 			= "01";
				let  iMCostCdAmt        = 0;
				
				
				var inputMcostcdCodeMValue   = $("#inputMcostcdCodeM option:selected").text();
				var inputMcostcdCodeMCdValue = $("#inputMcostcdCodeM option:selected").val();
				var inputMCostTVaValue 		 = document.getElementById('inputMCostTVa').value;
				var sMCostCdValue    		 = document.getElementById('sMCostCd').innerHTML;
				var sMCostTVaLabelValue   	 = document.getElementById('sMCostTVaLabel').innerHTML;

				//월정액,기준액,인건비 이면 가격 비율을 곱해서 월 제조경비를 구한다.
				
				iMCostTVaAmt = calculateMCostTVaAmt(sMCostCdValue,inputMCostTVaValue);
				
				if(iMCostTVaAmt==0) {
					alert("적용금액은 0보다 커야 합니다.");
					$('#myMCostModal').modal('toggle');
					return;
				}
				
				if(MCostAddFlag=="ADD") {
					let isTitle = hasTitle( PLANF_SALE_JSON.MCOST.MCost,'MCostTitle',inputMcostcdCodeMValue);
					if(isTitle) {
						alert("같은 항목을 추가 할수 없습니다..");
						return;
					}
				}
				
				console.log("iMCostTVaAmt",iMCostTVaAmt);
				let aMCost = new Object();
				aMCost.MCostTitle 	= inputMcostcdCodeMValue;
				aMCost.MCostTitleCd = inputMcostcdCodeMCdValue;
				aMCost.MCostCd 		= sMCostCdValue;
				aMCost.MCostTVa 	= inputMCostTVaValue;
				aMCost.MCostTVaLabel= sMCostTVaLabelValue;
				aMCost.MCostTVaAmt 	= iMCostTVaAmt;
				
				setMcost(MCostAddFlag,aMCost,mCostIndexNo);

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

				delJson(6,mCostIndexNo);				

		 });


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

			let costAmtTot 		= new Array();
            let iMCostAmtTot   	= 0;
			let i=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				costAmtTot[i] =0;
			}

			let mCostMArr = PLANF_SALE_JSON.MCOST.MCostM;
			if(mCostMArr.length > 0) {
				mCostMArr.forEach(function(node,index){
					//각월별 합계
					for(i=0; i< 12; i++){
						costAmtTot[i]= costAmtTot[i] + getNumber(node.MMCostAmt[i]); 
					}
					//1월 ~12 월합계
					iMCostAmtTot =0;
					iMCostAmtTot = node.MMCostAmt.reduce((acc,curr) => acc+curr,0); 
					$('[name=sMMCostMenuTot]')[index].innerHTML = iMCostAmtTot+"";
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

			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let mCostYArr = PLANF_SALE_JSON.MCOST.MCostY;
			if(mCostYArr.length > 0) {
				menuQty01Tot = 0;
				menuQty02Tot = 0;
				menuQty03Tot = 0;
				mCostYArr.forEach(function(node,index){
					node.YMCostAmtYear.forEach(function(item,i){
						    if (i==0) menuQty01Tot = menuQty01Tot +getNumber(item);
						    if (i==1) menuQty02Tot = menuQty02Tot +getNumber(item);
						    if (i==2) menuQty03Tot = menuQty03Tot +getNumber(item);
						}
					)
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
			
			if(costcdCodeM=="1") {     //급여인경우
				document.getElementById('inputSCostTVa').value="100";		
				$("#inputSCostTVa").attr("readonly", true);
			}else {
				document.getElementById('inputSCostTVa').value="";		
				$("#inputSCostTVa").attr("readonly", false);
			}
			
			
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
				
				if (document.getElementById('inputSCostTVa').value == "") {
					alert("적용값을입력하세요 ");
					 document.getElementById("inputSCostTVa").focus();
					return;
				}				
				
				var inputSCostcdCodeM 	= "";
				var sSCostCdValue 		= "";
				var inputSCostTV 		= "";
				var SCostTVaAmt 		= "";
				var iSCostTVaAmt 		= 0;
				let SCostNo 			= "01";
				let iMCostCdAmt       	= 0;

				var inputSCostcdCodeMValue   = $("#inputSCostcdCodeM option:selected").text();
				var inputSCostcdCodeMCdValue = $("#inputSCostcdCodeM option:selected").val();
				var inputSCostTVaValue 		 = document.getElementById('inputSCostTVa').value;
				var sSCostCdValue    		 = document.getElementById('sSCostCd').innerHTML;
				var sSCostTVaLabelValue   	 = document.getElementById('sSCostTVaLabel').innerHTML;


				iSCostTVaAmt = calculateSCostTVaAmt(sSCostCdValue,inputSCostcdCodeMValue,inputSCostTVaValue);
				
				if(iSCostTVaAmt==0) {
					alert("적용금액은 0보다 커야 합니다.");
					$('#mySCostModal').modal('toggle');
					return;
				}

				
				if(SCostAddFlag=="ADD") {
					let isTitle = hasTitle( PLANF_SALE_JSON.SCOST.SCost,'SCostTitle',inputSCostcdCodeMValue);
					if(isTitle) {
						alert("같은 항목을 추가 할수 없습니다..");
						return;
					}
				}

				
				
				let aSCost = new Object();
				aSCost.SCostTitle 	= inputSCostcdCodeMValue;
				aSCost.SCostTitleCd = inputSCostcdCodeMCdValue;
				aSCost.SCostCd 		= sSCostCdValue;
				aSCost.SCostTVa 	= inputSCostTVaValue;
				aSCost.SCostTVaLabel= sSCostTVaLabelValue;
				aSCost.SCostTVaAmt 	= iSCostTVaAmt;
				
				setScost(SCostAddFlag,aSCost,sCostIndexNo);
				
				$('#mySCostModal').modal('toggle');

				
		 });

		$(document).on('click', 'input[name=btnUpdSCost01]', function() {
				SCostAddFlag="UPD";
				document.getElementById('btnAddSCost').value="수정";
				sCostIndexNo = $('input[name=btnUpdSCost01]').index(this);

			 	document.getElementById('inputSCostcdCodeM').value = $('[name=sSCost01TitleCd]')[sCostIndexNo].innerHTML;
				document.getElementById('sSCostCd').innerHTML  	   = $('[name=sSCost01Cd]')[sCostIndexNo].innerHTML;
			 	document.getElementById('inputSCostTVa').value     = removeComma($('[name=sSCost01TVa]')[sCostIndexNo].innerHTML);
			 	document.getElementById('sSCostTVaLabel').innerHTML= $('[name=sSCost01TVaLabel]')[sCostIndexNo].innerHTML;
				$('#mySCostModal').modal('toggle');

							
		});


		 $(document).on('click', 'input[name=btnDelSCost01]', function() {
				if(!confirm('판매관리비 항목을 삭제하시겠습니까?')){
					return;
				}
			 	sCostIndexNo = $('input[name=btnDelSCost01]').index(this);
				document.getElementById("SCostTable01").deleteRow(sCostIndexNo+1);
				//calInvest();
				delJson(7,sCostIndexNo);
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

			let costAmtTot 		= new Array();
            let iSCostAmtTot    = 0;
			let i=0;
			let iTemp           ="";
			let jTemp           ="";

			for(i=0; i< 12; i++){
				costAmtTot[i] =0;
			}


			let sCostMArr = PLANF_SALE_JSON.SCOST.SCostM;
			if(sCostMArr.length > 0) {
				sCostMArr.forEach(function(node,index){
					//각월별 합계
					for(i=0; i< 12; i++){
						costAmtTot[i]= costAmtTot[i] + getNumber(node.MSCostAmt[i]); 
					}
					//1월 ~12 월합계
					iSCostAmtTot =0;
					iSCostAmtTot = node.MSCostAmt.reduce((acc,curr) => acc+curr,0); 
					$('[name=sMSCostMenuTot]')[index].innerHTML = iSCostAmtTot+"";
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

			let menuQty01Tot = 0;
			let menuQty02Tot = 0;
			let menuQty03Tot = 0;

			let menuAmt01Tot = 0;
			let menuAmt02Tot = 0;
			let menuAmt03Tot = 0;

			let sCostYArr = PLANF_SALE_JSON.SCOST.SCostY;
			if(sCostYArr.length > 0) {
				menuQty01Tot = 0;
				menuQty02Tot = 0;
				menuQty03Tot = 0;
				sCostYArr.forEach(function(node,index){
					node.YSCostAmtYear.forEach(function(item,i){
						    if (i==0) menuQty01Tot = menuQty01Tot +getNumber(item);
						    if (i==1) menuQty02Tot = menuQty02Tot +getNumber(item);
						    if (i==2) menuQty03Tot = menuQty03Tot +getNumber(item);
						}
					)
				});
			 }				

			 document.getElementById('sYSCostAmt01Tot').innerHTML = setComma(menuQty01Tot+"");
			 document.getElementById('sYSCostAmt02Tot').innerHTML = setComma(menuQty02Tot+"");
			 document.getElementById('sYSCostAmt03Tot').innerHTML = setComma(menuQty03Tot+"");

			 document.getElementById('yearSCostAmt').innerHTML = setComma(menuQty01Tot+"");

		 }

		 //수익성 검토 계산 결과값 가져오기
		 function fnCalPlanF(){
			 
			 document.getElementById('PLANF_SALE').value = JSON.stringify(PLANF_SALE_JSON);
			 
			 var sUrl 	= "/front/calPlanFAjax.do";
			 var sParam ={'INDUSTRY_CD' 		: industryCd
				         ,'WORK_DAY_CNT' 		: workDay
				         ,'MON_TARGET_PROFIT' 	: removeComma($('#inTargetPrice').val())
					     ,'PLANF_SALE' 		    : $('#PLANF_SALE').val()
					 	 };
			 var sType 	= "post";
             console.log("sParam:",sParam);
			 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
						console.log(pResponse);
				 	var results=pResponse.resultStats.resultList;
				 	if(pResponse.resultStats.resultCode=="ok"){
				 		setProfit(pResponse.planF);
				 	}else{
                		alert("처리중 오류가 생겼습니다 \N 저장버튼을 다시 클릭하세요.");
					}
			 });
				 
		}
		 
	     //손익게산서 계산하기			
		 function setProfit(pPlanF) {
			 setInComes(pPlanF);
			 setFinancial(pPlanF);
			 setBreakPoint(pPlanF);
			 setBizResult(pPlanF)
		 }
		 
		 
	     //손익게산서 계산하기			
		 function setInComes(pPlanF) {
			console.log("******************setInComes()*****************"); 
				 
			//매출금액
			let iInCome1Year1 = 0;
			let iInCome1Year2 = 0;
			let iInCome1Year3 = 0;

			iInCome1Year1 = getNumber(pPlanF.YMenuAmt[0]);
			iInCome1Year2 = getNumber(pPlanF.YMenuAmt[1]);
			iInCome1Year3 = getNumber(pPlanF.YMenuAmt[2]);

			//매출액 셋팅
			document.getElementById('sInCome1Year1').innerHTML = setComma(iInCome1Year1+"");
			document.getElementById('sInCome1Year2').innerHTML = setComma(iInCome1Year2+"");
			document.getElementById('sInCome1Year3').innerHTML = setComma(iInCome1Year3+"");

			//매출원가 
			let iInCome2Year1 = 0;
			let iInCome2Year2 = 0;
			let iInCome2Year3 = 0;

			iInCome2Year1 = getNumber(pPlanF.YCostAmt[0]);
			iInCome2Year2 = getNumber(pPlanF.YCostAmt[1]);
			iInCome2Year3 = getNumber(pPlanF.YCostAmt[2]);

			//매출원가 셋팅
			document.getElementById('sInCome2Year1').innerHTML = setComma(iInCome2Year1+"");
			document.getElementById('sInCome2Year2').innerHTML = setComma(iInCome2Year2+"");
			document.getElementById('sInCome2Year3').innerHTML = setComma(iInCome2Year3+"");

			//매출총이익
			let iInCome3Year1 = 0;
			let iInCome3Year2 = 0;
			let iInCome3Year3 = 0;

			iInCome3Year1 = getNumber(pPlanF.yInCome3Amt[0]);
			iInCome3Year2 = getNumber(pPlanF.yInCome3Amt[1]);
			iInCome3Year3 = getNumber(pPlanF.yInCome3Amt[2]);
			
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

			iInCome5Year1 = getNumber(pPlanF.YSCostAmt[0]);
			iInCome5Year2 = getNumber(pPlanF.YSCostAmt[1]);
			iInCome5Year3 = getNumber(pPlanF.YSCostAmt[2]);

			//판매관리비 셋팅
			document.getElementById('sInCome5Year1').innerHTML = setComma(iInCome5Year1+"");
			document.getElementById('sInCome5Year2').innerHTML = setComma(iInCome5Year2+"");
			document.getElementById('sInCome5Year3').innerHTML = setComma(iInCome5Year3+"");

			//영업이익
			let iInCome6Year1 = 0;
			let iInCome6Year2 = 0;
			let iInCome6Year3 = 0;

			iInCome6Year1 = getNumber(pPlanF.yInCome6Amt[0]);
			iInCome6Year2 = getNumber(pPlanF.yInCome6Amt[1]);
			iInCome6Year3 = getNumber(pPlanF.yInCome6Amt[2]);
			
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

			iInCome8Year1 = getNumber(removeComma(pPlanF.YLonaRateAmt[0]));
			iInCome8Year2 = getNumber(removeComma(pPlanF.YLonaRateAmt[1]));
			iInCome8Year3 = getNumber(removeComma(pPlanF.YLonaRateAmt[2]));

			//영업외비용 셋팅
			document.getElementById('sInCome8Year1').innerHTML = setComma(iInCome8Year1+"");
			document.getElementById('sInCome8Year2').innerHTML = setComma(iInCome8Year2+"");
			document.getElementById('sInCome8Year3').innerHTML = setComma(iInCome8Year3+"");
			

			//경상이익
			let iInCome9Year1 = 0;
			let iInCome9Year2 = 0;
			let iInCome9Year3 = 0;

			iInCome9Year1 = getNumber(pPlanF.yInCome9Amt[0]);
			iInCome9Year2 = getNumber(pPlanF.yInCome9Amt[1]);
			iInCome9Year3 = getNumber(pPlanF.yInCome9Amt[2]);
			
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
		 function setFinancial(pPlanF) {
			console.log("******************setFinancial()*****************"); 

			//3.비유동부채
			let iFinancial3Year1 = 0;
			let iFinancial3Year2 = 0;
			let iFinancial3Year3 = 0;

			iFinancial3Year1 = getNumber(pPlanF.loanYear02Amt[0]);
			iFinancial3Year2 = getNumber(pPlanF.loanYear02Amt[1]);
			iFinancial3Year3 = getNumber(pPlanF.loanYear02Amt[2]);
			
			document.getElementById('sFinancial3Year1').innerHTML = setComma(iFinancial3Year1+"");
			document.getElementById('sFinancial3Year2').innerHTML = setComma(iFinancial3Year2+"");
			document.getElementById('sFinancial3Year3').innerHTML = setComma(iFinancial3Year3+"");

			//4.자본금
			let iFinancial4Year1 = 0;
			let iFinancial4Year2 = 0;
			let iFinancial4Year3 = 0;

			iFinancial4Year1 = getNumber(pPlanF.loanYear01Amt[0]);
			iFinancial4Year2 = getNumber(pPlanF.loanYear01Amt[1]);
			iFinancial4Year3 = getNumber(pPlanF.loanYear01Amt[2]);
			
			document.getElementById('sFinancial4Year1').innerHTML = setComma(iFinancial4Year1+"");
			document.getElementById('sFinancial4Year2').innerHTML = setComma(iFinancial4Year2+"");
			document.getElementById('sFinancial4Year3').innerHTML = setComma(iFinancial4Year3+"");
			
			
			//5.이익잉여금
			let iFinancial5Year1 = 0;
			let iFinancial5Year2 = 0;
			let iFinancial5Year3 = 0;

			iFinancial5Year1 = getNumber(pPlanF.financial5Amt[0]);
			iFinancial5Year2 = getNumber(pPlanF.financial5Amt[1]);
			iFinancial5Year3 = getNumber(pPlanF.financial5Amt[2]);
			
			
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

            iFinancialT2Year1 = getNumber(pPlanF.financialT2Amt[0]);
            iFinancialT2Year2 = getNumber(pPlanF.financialT2Amt[1]);
            iFinancialT2Year3 = getNumber(pPlanF.financialT2Amt[2]);

            iFinancialT3Year1 = getNumber(pPlanF.financialT3Amt[0]);
            iFinancialT3Year2 = getNumber(pPlanF.financialT3Amt[1]);
            iFinancialT3Year3 = getNumber(pPlanF.financialT3Amt[2]);
            
            
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

			
			iFinancial2Year1 = getNumber(pPlanF.Financial2Amt[0]);
			iFinancial2Year2 = getNumber(pPlanF.Financial2Amt[1]);
			iFinancial2Year3 = getNumber(pPlanF.Financial2Amt[2]);
			
			document.getElementById('sFinancial2Year1').innerHTML = setComma(iFinancial2Year1+"");
			document.getElementById('sFinancial2Year2').innerHTML = setComma(iFinancial2Year2+"");
			document.getElementById('sFinancial2Year3').innerHTML = setComma(iFinancial2Year3+"");


			//1.유동자산
			let iFinancial1Year1 = 0;
			let iFinancial1Year2 = 0;
			let iFinancial1Year3 = 0;

			iFinancial1Year1 = getNumber(pPlanF.Financial1Amt[0]);
			iFinancial1Year2 = getNumber(pPlanF.Financial1Amt[1]);
			iFinancial1Year3 = getNumber(pPlanF.Financial1Amt[2]);
			
			document.getElementById('sFinancial1Year1').innerHTML = setComma(iFinancial1Year1+"");
			document.getElementById('sFinancial1Year2').innerHTML = setComma(iFinancial1Year2+"");
			document.getElementById('sFinancial1Year3').innerHTML = setComma(iFinancial1Year3+"");

			
			//자산총계
            let iFinancialT1Year1 = 0;
            let iFinancialT1Year2 = 0;
            let iFinancialT1Year3 = 0;

            iFinancialT1Year1 = getNumber(pPlanF.FinancialT11Amt[0]);
            iFinancialT1Year2 = getNumber(pPlanF.FinancialT11Amt[1]);
            iFinancialT1Year3 = getNumber(pPlanF.FinancialT11Amt[2]);

            
			document.getElementById('sFinancialT1Year1').innerHTML = setComma(iFinancialT1Year1+"");
			document.getElementById('sFinancialT1Year2').innerHTML = setComma(iFinancialT1Year2+"");
			document.getElementById('sFinancialT1Year3').innerHTML = setComma(iFinancialT1Year3+"");
			
	     }

	     //손익분기점 계산하기			
		 function setBreakPoint(pPlanF) {
			console.log("******************setBreakPoint()*****************"); 

			//1.매출액
			let iBreakPoint1Year1 = 0;
			let iBreakPoint1Year2 = 0;
			let iBreakPoint1Year3 = 0;

			iBreakPoint1Year1 = getNumber(pPlanF.YMenuAmt[0]);
			iBreakPoint1Year2 = getNumber(pPlanF.YMenuAmt[1]);
			iBreakPoint1Year3 = getNumber(pPlanF.YMenuAmt[2]);
			
			document.getElementById('sBreakPoint1Year1').innerHTML = setComma(iBreakPoint1Year1+"");
			document.getElementById('sBreakPoint1Year2').innerHTML = setComma(iBreakPoint1Year2+"");
			document.getElementById('sBreakPoint1Year3').innerHTML = setComma(iBreakPoint1Year3+"");

			//2.변동비 (재료비+제조경비+판매경비)
			let iBreakPoint2Year1 = 0;
			let iBreakPoint2Year2 = 0;
			let iBreakPoint2Year3 = 0;

			iBreakPoint2Year1 = getNumber(pPlanF.BreakPoint2Amt[0]);
			iBreakPoint2Year2 = getNumber(pPlanF.BreakPoint2Amt[1]);
			iBreakPoint2Year3 = getNumber(pPlanF.BreakPoint2Amt[2]);
			
			document.getElementById('sBreakPoint2Year1').innerHTML = setComma(iBreakPoint2Year1+"");
			document.getElementById('sBreakPoint2Year2').innerHTML = setComma(iBreakPoint2Year2+"");
			document.getElementById('sBreakPoint2Year3').innerHTML = setComma(iBreakPoint2Year3+"");

			//3.한계이익
			let iBreakPoint3Year1 = 0;
			let iBreakPoint3Year2 = 0;
			let iBreakPoint3Year3 = 0;

			iBreakPoint3Year1 = getNumber(pPlanF.BreakPoint3Amt[0]);
			iBreakPoint3Year2 = getNumber(pPlanF.BreakPoint3Amt[1]);
			iBreakPoint3Year3 = getNumber(pPlanF.BreakPoint3Amt[2]);
			
			document.getElementById('sBreakPoint3Year1').innerHTML = setComma(iBreakPoint3Year1+"");
			document.getElementById('sBreakPoint3Year2').innerHTML = setComma(iBreakPoint3Year2+"");
			document.getElementById('sBreakPoint3Year3').innerHTML = setComma(iBreakPoint3Year3+"");

			//4.한계이익율
			let iBreakPoint4Year1 = 0;
			let iBreakPoint4Year2 = 0;
			let iBreakPoint4Year3 = 0;

			iBreakPoint4Year1 = getNumber(pPlanF.BreakPoint4Rate[0]);
			iBreakPoint4Year2 = getNumber(pPlanF.BreakPoint4Rate[1]);
			iBreakPoint4Year3 = getNumber(pPlanF.BreakPoint4Rate[2]);
			
			document.getElementById('sBreakPoint4Year1').innerHTML = setComma(iBreakPoint4Year1+"");
			document.getElementById('sBreakPoint4Year2').innerHTML = setComma(iBreakPoint4Year2+"");
			document.getElementById('sBreakPoint4Year3').innerHTML = setComma(iBreakPoint4Year3+"");

			//5.고정비 (인건비+제조경비인건비+제조경비월정액+제조경비기준액+판매경비인건비+판매경비월정액+판매경비기준액)
			let iBreakPoint5Year1 = 0;
			let iBreakPoint5Year2 = 0;
			let iBreakPoint5Year3 = 0;

			iBreakPoint5Year1 = getNumber(pPlanF.BreakPoint5Amt[0]);
			iBreakPoint5Year2 = getNumber(pPlanF.BreakPoint5Amt[1]);
			iBreakPoint5Year3 = getNumber(pPlanF.BreakPoint5Amt[2]);

			
			document.getElementById('sBreakPoint5Year1').innerHTML = setComma(iBreakPoint5Year1+"");
			document.getElementById('sBreakPoint5Year2').innerHTML = setComma(iBreakPoint5Year2+"");
			document.getElementById('sBreakPoint5Year3').innerHTML = setComma(iBreakPoint5Year3+"");

			//6.손익분기점
			let iBreakPoint6Year1 = 0;
			let iBreakPoint6Year2 = 0;
			let iBreakPoint6Year3 = 0;

			iBreakPoint6Year1 = getNumber(pPlanF.BreakPoint6Amt[0]);
			iBreakPoint6Year2 = getNumber(pPlanF.BreakPoint6Amt[1]);
			iBreakPoint6Year3 = getNumber(pPlanF.BreakPoint6Amt[2]);
			
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
			
			iBreakPoint8Year1 = getNumber(pPlanF.BreakPoint8Amt[0]);
			iBreakPoint8Year2 = getNumber(pPlanF.BreakPoint8Amt[1]);
			iBreakPoint8Year3 = getNumber(pPlanF.BreakPoint8Amt[2]);
			
			document.getElementById('sBreakPoint8Year1').innerHTML = setComma(iBreakPoint8Year1+"");
			document.getElementById('sBreakPoint8Year2').innerHTML = setComma(iBreakPoint8Year2+"");
			document.getElementById('sBreakPoint8Year3').innerHTML = setComma(iBreakPoint8Year3+"");

			//9.목표매출액
			let iBreakPoint9Year1 = 0;
			let iBreakPoint9Year2 = 0;
			let iBreakPoint9Year3 = 0;
			
			
			iBreakPoint9Year1 = getNumber(pPlanF.BreakPoint9Amt[0]);
			iBreakPoint9Year2 = getNumber(pPlanF.BreakPoint9Amt[1]);
			iBreakPoint9Year3 = getNumber(pPlanF.BreakPoint9Amt[2]);

			
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
		 function setBizResult(pPlanF) {
			console.log("******************setBizResult*****************"); 
			//매출금액
			let iYmenuAmt01Tot = 0;
			let iYmenuAmt02Tot = 0;
			let iYmenuAmt03Tot = 0;


			iYmenuAmt01Tot = getNumber(pPlanF.YMenuAmt[0]);
			iYmenuAmt02Tot = getNumber(pPlanF.YMenuAmt[1]);
			iYmenuAmt03Tot = getNumber(pPlanF.YMenuAmt[2]);


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

			iBiz1C4Year1 = getNumber(pPlanF.Biz1C4Amt[0]);
			iBiz1C4Year2 = getNumber(pPlanF.Biz1C4Amt[1]);
			iBiz1C4Year3 = getNumber(pPlanF.Biz1C4Amt[2]);
			
			document.getElementById('sBiz1C4Year1').innerHTML = setComma(iBiz1C4Year1+"");
			document.getElementById('sBiz1C4Year2').innerHTML = setComma(iBiz1C4Year2+"");
			document.getElementById('sBiz1C4Year3').innerHTML = setComma(iBiz1C4Year3+"");

			document.getElementById('sBiz1C1Year1').innerHTML = pPlanF.Biz1C1Rate[0];
			document.getElementById('sBiz1C1Year2').innerHTML = pPlanF.Biz1C1Rate[1];
			document.getElementById('sBiz1C1Year3').innerHTML = pPlanF.Biz1C1Rate[2];

			document.getElementById('sBiz1C2Year1').innerHTML = pPlanF.Biz1C2Cd[0];
			document.getElementById('sBiz1C2Year2').innerHTML = pPlanF.Biz1C2Cd[1];
			document.getElementById('sBiz1C2Year3').innerHTML = pPlanF.Biz1C2Cd[2];

			chartDataYear1[0] = (getNumber(pPlanF.Biz1C1Rate[0])*20).toFixed(1);
			chartDataYear2[0] = (getNumber(pPlanF.Biz1C1Rate[1])*20).toFixed(1);
			chartDataYear3[0] = (getNumber(pPlanF.Biz1C1Rate[2])*20).toFixed(1);

			//setBizRate(1,iYmenuAmt01Tot,iYmenuAmt01Tot,iYmenuAmt01Tot,iBiz1C4Year1,iBiz1C4Year1,iBiz1C4Year1,1);

			//2.매출액 영업이익율
			let iBiz2C4Year1	= 0;
			let iBiz2C4Year2	= 0;
			let iBiz2C4Year3	= 0;
			
			iBiz2C4Year1 = getNumber(pPlanF.Biz2C4Amt[0]);
			iBiz2C4Year2 = getNumber(pPlanF.Biz2C4Amt[1]);
			iBiz2C4Year3 = getNumber(pPlanF.Biz2C4Amt[2]);

			document.getElementById('sBiz2C4Year1').innerHTML = setComma(iBiz2C4Year1+"");
			document.getElementById('sBiz2C4Year2').innerHTML = setComma(iBiz2C4Year2+"");
			document.getElementById('sBiz2C4Year3').innerHTML = setComma(iBiz2C4Year3+"");


			document.getElementById('sBiz2C1Year1').innerHTML = pPlanF.Biz2C1Rate[0];
			document.getElementById('sBiz2C1Year2').innerHTML = pPlanF.Biz2C1Rate[1];
			document.getElementById('sBiz2C1Year3').innerHTML = pPlanF.Biz2C1Rate[2];

			document.getElementById('sBiz2C2Year1').innerHTML = pPlanF.Biz2C2Cd[0];
			document.getElementById('sBiz2C2Year2').innerHTML = pPlanF.Biz2C2Cd[1];
			document.getElementById('sBiz2C2Year3').innerHTML = pPlanF.Biz2C2Cd[2];

			chartDataYear1[1] = getNumber(pPlanF.Biz2C1Rate[0]);
			chartDataYear2[1] = getNumber(pPlanF.Biz2C1Rate[1]);
			chartDataYear3[1] = getNumber(pPlanF.Biz2C1Rate[2]);

			//setBizRate(2,iBiz2C4Year1,iBiz2C4Year2,iBiz2C4Year3,iYmenuAmt01Tot,iYmenuAmt02Tot,iYmenuAmt03Tot,1);

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


			document.getElementById('sBiz3C1Year1').innerHTML = pPlanF.Biz3C1Rate[0];
			document.getElementById('sBiz3C1Year2').innerHTML = pPlanF.Biz3C1Rate[1];
			document.getElementById('sBiz3C1Year3').innerHTML = pPlanF.Biz3C1Rate[2];

			document.getElementById('sBiz3C2Year1').innerHTML = pPlanF.Biz3C2Cd[0];
			document.getElementById('sBiz3C2Year2').innerHTML = pPlanF.Biz3C2Cd[1];
			document.getElementById('sBiz3C2Year3').innerHTML = pPlanF.Biz3C2Cd[2];

			chartDataYear1[2] = getNumber(pPlanF.Biz3C1Rate[0]);
			chartDataYear2[2] = getNumber(pPlanF.Biz3C1Rate[1]);
			chartDataYear3[2] = getNumber(pPlanF.Biz3C1Rate[2]);

			//setBizRate(3,iBiz3C4Year1,iBiz3C4Year2,iBiz3C4Year3,iBiz3C3Year1,iBiz3C3Year2,iBiz3C3Year3,1);
			
			
			//4.인건비 비율 
			//인건비 
			let iBiz4C3Year1	= iYmenuAmt01Tot;
			let iBiz4C3Year2	= iYmenuAmt02Tot;
			let iBiz4C3Year3	= iYmenuAmt03Tot;

			let iBiz4C4Year1	= 0;
			let iBiz4C4Year2	= 0;
			let iBiz4C4Year3	= 0;

			iBiz4C4Year1 = getNumber(pPlanF.JobAmt[0]);
			iBiz4C4Year2 = getNumber(pPlanF.JobAmt[1]);
			iBiz4C4Year3 = getNumber(pPlanF.JobAmt[2]);

			
			document.getElementById('sBiz4C4Year1').innerHTML = setComma(iBiz4C4Year1+"");
			document.getElementById('sBiz4C4Year2').innerHTML = setComma(iBiz4C4Year2+"");
			document.getElementById('sBiz4C4Year3').innerHTML = setComma(iBiz4C4Year3+"");

			document.getElementById('sBiz4C1Year1').innerHTML = pPlanF.Biz4C1Rate[0];
			document.getElementById('sBiz4C1Year2').innerHTML = pPlanF.Biz4C1Rate[1];
			document.getElementById('sBiz4C1Year3').innerHTML = pPlanF.Biz4C1Rate[2];

			document.getElementById('sBiz4C2Year1').innerHTML = pPlanF.Biz4C2Cd[0];
			document.getElementById('sBiz4C2Year2').innerHTML = pPlanF.Biz4C2Cd[1];
			document.getElementById('sBiz4C2Year3').innerHTML = pPlanF.Biz4C2Cd[2];

			chartDataYear1[3] = getNumber(pPlanF.Biz4C1Rate[0]);
			chartDataYear2[3] = getNumber(pPlanF.Biz4C1Rate[1]);
			chartDataYear3[3] = getNumber(pPlanF.Biz4C1Rate[2]);

			
			//setBizRate(4,iBiz4C4Year1,iBiz4C4Year2,iBiz4C4Year3,iBiz4C3Year1,iBiz4C3Year2,iBiz4C3Year3,1);


			//5.임차율 비율
			let iBiz5C3Year1=0;
			let iBiz5C3Year2=0;
			let iBiz5C3Year3=0;
			
			let iBiz5C4Year1=0;
			let iBiz5C4Year2=0;
			let iBiz5C4Year3=0;

			iBiz5C3Year1=getNumber(pPlanF.mMenuAmt[0]);
			iBiz5C3Year2=getNumber(pPlanF.mMenuAmt[1]);
			iBiz5C3Year3=getNumber(pPlanF.mMenuAmt[2]);

			iBiz5C4Year1=getNumber(pPlanF.RentSAmt[0]);
			iBiz5C4Year2=getNumber(pPlanF.RentSAmt[1]);
			iBiz5C4Year3=getNumber(pPlanF.RentSAmt[2]);

			
			//월매출액
			document.getElementById('sBiz5C3Year1').innerHTML = setComma(iBiz5C3Year1+"");
			document.getElementById('sBiz5C3Year2').innerHTML = setComma(iBiz5C3Year2+"");
			document.getElementById('sBiz5C3Year3').innerHTML = setComma(iBiz5C3Year3+"");
			
			//월임차료
			document.getElementById('sBiz5C4Year1').innerHTML = setComma(iBiz5C4Year1+"");
			document.getElementById('sBiz5C4Year2').innerHTML = setComma(iBiz5C4Year2+"");
			document.getElementById('sBiz5C4Year3').innerHTML = setComma(iBiz5C4Year3+"");
	
			document.getElementById('sBiz5C1Year1').innerHTML = pPlanF.Biz5C1Rate[0];
			document.getElementById('sBiz5C1Year2').innerHTML = pPlanF.Biz5C1Rate[1];
			document.getElementById('sBiz5C1Year3').innerHTML = pPlanF.Biz5C1Rate[2];

			document.getElementById('sBiz5C2Year1').innerHTML = pPlanF.Biz5C2Cd[0];
			document.getElementById('sBiz5C2Year2').innerHTML = pPlanF.Biz5C2Cd[1];
			document.getElementById('sBiz5C2Year3').innerHTML = pPlanF.Biz5C2Cd[2];

			chartDataYear1[4] = getNumber(pPlanF.Biz5C1Rate[0]);
			chartDataYear2[4] = getNumber(pPlanF.Biz5C1Rate[1]);
			chartDataYear3[4] = getNumber(pPlanF.Biz5C1Rate[2]);
			
			//setBizRate(5,iBiz5C4Year1,iBiz5C4Year2,iBiz5C4Year3,iBiz5C3Year1,iBiz5C3Year2,iBiz5C3Year3,1);


			//6.자기자본 비율 
			let iBiz6C3Year1	= 0;
			let iBiz6C3Year2	= 0;
			let iBiz6C3Year3	= 0;

			let iBiz6C4Year1	= 0;
			let iBiz6C4Year2	= 0;
			let iBiz6C4Year3	= 0;

			iBiz6C3Year1 = getNumber(pPlanF.FinancialT11Amt[0]);
			iBiz6C3Year2 = getNumber(pPlanF.FinancialT11Amt[1]);
			iBiz6C3Year3 = getNumber(pPlanF.FinancialT11Amt[2]);

			iBiz6C4Year1 = getNumber(pPlanF.financialT2Amt[0]);
			iBiz6C4Year2 = getNumber(pPlanF.financialT2Amt[1]);
			iBiz6C4Year3 = getNumber(pPlanF.financialT2Amt[2]);
			

			document.getElementById('sBiz6C3Year1').innerHTML = setComma(iBiz6C3Year1+"");
			document.getElementById('sBiz6C3Year2').innerHTML = setComma(iBiz6C3Year2+"");
			document.getElementById('sBiz6C3Year3').innerHTML = setComma(iBiz6C3Year3+"");
			
			document.getElementById('sBiz6C4Year1').innerHTML = setComma(iBiz6C4Year1+"");
			document.getElementById('sBiz6C4Year2').innerHTML = setComma(iBiz6C4Year2+"");
			document.getElementById('sBiz6C4Year3').innerHTML = setComma(iBiz6C4Year3+"");

			document.getElementById('sBiz6C1Year1').innerHTML = pPlanF.Biz6C1Rate[0];
			document.getElementById('sBiz6C1Year2').innerHTML = pPlanF.Biz6C1Rate[1];
			document.getElementById('sBiz6C1Year3').innerHTML = pPlanF.Biz6C1Rate[2];

			document.getElementById('sBiz6C2Year1').innerHTML = pPlanF.Biz6C2Cd[0];
			document.getElementById('sBiz6C2Year2').innerHTML = pPlanF.Biz6C2Cd[1];
			document.getElementById('sBiz6C2Year3').innerHTML = pPlanF.Biz6C2Cd[2];

			chartDataYear1[5] = getNumber(pPlanF.Biz6C1Rate[0]);
			chartDataYear2[5] = getNumber(pPlanF.Biz6C1Rate[1]);
			chartDataYear3[5] = getNumber(pPlanF.Biz6C1Rate[2]);
			
			
			//setBizRate(6,iBiz6C4Year1,iBiz6C4Year2,iBiz6C4Year3,iBiz6C3Year1,iBiz6C3Year2,iBiz6C3Year3,1);

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

			iBiz7C4Year1 = getNumber(pPlanF.BreakPoint6Amt[0]);
			iBiz7C4Year2 = getNumber(pPlanF.BreakPoint6Amt[1]);
			iBiz7C4Year3 = getNumber(pPlanF.BreakPoint6Amt[2]);
			

			document.getElementById('sBiz7C3Year1').innerHTML = setComma(iBiz7C3Year1+"");
			document.getElementById('sBiz7C3Year2').innerHTML = setComma(iBiz7C3Year2+"");
			document.getElementById('sBiz7C3Year3').innerHTML = setComma(iBiz7C3Year3+"");
			
			document.getElementById('sBiz7C4Year1').innerHTML = setComma(iBiz7C4Year1+"");
			document.getElementById('sBiz7C4Year2').innerHTML = setComma(iBiz7C4Year2+"");
			document.getElementById('sBiz7C4Year3').innerHTML = setComma(iBiz7C4Year3+"");


			document.getElementById('sBiz7C1Year1').innerHTML = pPlanF.Biz7C1Rate[0];
			document.getElementById('sBiz7C1Year2').innerHTML = pPlanF.Biz7C1Rate[1];
			document.getElementById('sBiz7C1Year3').innerHTML = pPlanF.Biz7C1Rate[2];

			document.getElementById('sBiz7C2Year1').innerHTML = pPlanF.Biz7C2Cd[0];
			document.getElementById('sBiz7C2Year2').innerHTML = pPlanF.Biz7C2Cd[1];
			document.getElementById('sBiz7C2Year3').innerHTML = pPlanF.Biz7C2Cd[2];

			chartDataYear1[6] = getNumber(pPlanF.Biz7C1Rate[0]);
			chartDataYear2[6] = getNumber(pPlanF.Biz7C1Rate[1]);
			chartDataYear3[6] = getNumber(pPlanF.Biz7C1Rate[2]);

			
			//setBizRate(7,iBiz7C4Year1,iBiz7C4Year2,iBiz7C4Year3,iBiz7C3Year1,iBiz7C3Year2,iBiz7C3Year3,1);
			
			
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
   				$("#btnDivSave").hide();
   				$("#btnDivInit").hide();
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initMenuTable(PLANF_SALE_JSON.SALE);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initCostTable(PLANF_SALE_JSON.COST);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initJobTable(PLANF_SALE_JSON.JOB);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initInvestTable(PLANF_SALE_JSON.INVEST);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initLoanTable(PLANF_SALE_JSON.LOAN);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initMCostTable(PLANF_SALE_JSON.MCOST);		
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
   				$("#btnDivSave").show();
   				$("#btnDivInit").show();
   				initSCostTable(PLANF_SALE_JSON.SCOST);		
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
   				$("#btnDivSave").hide();
   				$("#btnDivInit").hide();
   				
   				fnCalPlanF();
   				//setInComes();
   				//setFinancial();
   				//setBreakPoint();
   				//setBizResult();
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

	     document.getElementById('btnPopMenuNewSave').addEventListener('click',function(e){
				console.log("*****btnPopMenuNewSave*******");
				if (document.getElementById('inputPlanTitle').value == "") {
					alert("수익분석 타이틀을 입력하세요");
					 document.getElementById("inputPlanTitle").focus();
					return;
				}
				document.getElementById('PLANF_ID').value="";
				
				if (document.getElementById('inputPlanTitle').value == document.getElementById('OLD_PLANF_TITLE').value) {
					alert("기존 수익분석 타이틀명과 동일합니다.");
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
						     ,'GROUP_ID' 		    : $('#GROUP_ID').val()
					         ,'MON_TARGET_PROFIT' 	: $('#inTargetPrice').val()
						 	 };
				 var sType 	= "post";
                 console.log("sParam:",sParam);
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
			console.log(PLANF_SALE_JSON);
			document.getElementById('PLANF_SALE').value = JSON.stringify(PLANF_SALE_JSON);
		}	     
	     
		
		//판매관리비 적용금액 가져오기
		function calculateSCostTVaAmt(pSCostCdValue, pInputSCostcdCodeMValue, pInputSCostTVaValue) {
		    var iMCostCdAmt = 0;
		    var iSCostTVaAmt = 0;

		    if (pSCostCdValue !== "") {
		        if (pSCostCdValue === "매출액" || pSCostCdValue === "기준액" || pSCostCdValue === "인건비") {
		            if (pInputSCostcdCodeMValue === "급여") {
		                iMCostCdAmt = aItemAmtMon(PLANF_SALE_JSON.JOB.job02M, 'MJobAmt');
		            } else {
		                if (pSCostCdValue === "매출액") iMCostCdAmt = aItemAmtMon(PLANF_SALE_JSON.SALE.menuM, 'MMenuAmt');
		                if (pSCostCdValue === "기준액") iMCostCdAmt = aInvestUseAmt(2);
		                if (pSCostCdValue === "인건비") iMCostCdAmt = aItemAmtMon(PLANF_SALE_JSON.JOB.job02M, 'MJobAmt');
		            }
		            console.log("iMCostCdAmt", iMCostCdAmt);
		            iSCostTVaAmt = iMCostCdAmt * parseFloat(pInputSCostTVaValue) / 100;
		            iSCostTVaAmt = excelInt(iSCostTVaAmt);
		            //iSCostTVaAmt = getNumberPoint(iSCostTVaAmt, 0);
		            //iSCostTVaAmt = excelIntAmt(iMCostCdAmt,getNumber(pInputSCostTVaValue)/100);
		            
		           
		        } else {
		            iSCostTVaAmt = parseFloat(pInputSCostTVaValue);
		        }
		    }

		    return iSCostTVaAmt;
		}
		
		
		//제조경비 적용금액 가져오기
		function calculateMCostTVaAmt(pSCostCdValue, pInputSCostTVaValue) {
		    var iMCostCdAmt = 0;
		    var iMCostTVaAmt = 0;
			//월정액,기준액,인건비 이면 가격 비율을 곱해서 월 제조경비를 구한다.
			if(pSCostCdValue!=""){
				if(pSCostCdValue =="매출액" || pSCostCdValue =="기준액"|| pSCostCdValue =="인건비"){
					if(pSCostCdValue =="매출액") iMCostCdAmt = aItemAmtMon(PLANF_SALE_JSON.SALE.menuM,'MMenuAmt');
					if(pSCostCdValue =="기준액") iMCostCdAmt = aInvestUseAmt(1);
					if(pSCostCdValue =="인건비") iMCostCdAmt = aItemAmtMon(PLANF_SALE_JSON.JOB.job01M,'MJobAmt');
					
					console.log("iMCostCdAmt",iMCostCdAmt);
					iMCostTVaAmt = (iMCostCdAmt * getNumber(pInputSCostTVaValue)/100);
					iMCostTVaAmt = excelInt(iMCostTVaAmt);
					//iMCostTVaAmt = getNumberPoint(iMCostTVaAmt,0);
					//iMCostTVaAmt = excelIntAmt(iMCostCdAmt,getNumber(pInputSCostTVaValue)/100);
					
				} else {
					iMCostTVaAmt = getNumber(pInputSCostTVaValue);
				}
			}
		    return iMCostTVaAmt;
		}

		//재조경비, 판매 경비 재계산
		function updMScost() {
			updMcost();
			updScost();
		}
		
		function hasTitle(data,pKey, title) {
		    var found = false;
		    data.forEach(function(item) {
		        if (item[pKey] === title) {
		            found = true; // 제목이 존재함
		        }
		    });
		    return found;
		}			

		document.getElementById('btnClearJson').addEventListener('click',function(e){
				console.log("*****btnClearJson*******");
				clearJson()
		});
		 
		$('#btnGetPplan').on('click', function(e) {
			fnListPlanF();
		});
		 
		 //수익성 검토 계산 결과값 가져오기
		 function fnListPlanF(){
			 var sUrl 	= "/front/listPlanFAjax.do";
			 var sParam = {};
			 var sType 	= "post";
			 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
					console.log(pResponse);
				 	var results=pResponse.resultStats.resultList;
				 	if(pResponse.resultStats.resultCode=="ok"){
				 		fnSetPlanFList(pResponse.resultList);
				 	}else{
              		alert("조회중 오류가 생겼습니다");
					}
			 });
		}

		// 수익성 검토 리스트 그리기
		 function fnSetPlanFList(pPlanfList) {
		     var $tbody = $('#trModalPlanList > tbody').empty();

		     if (pPlanfList.length > 0) {
		         pPlanfList.forEach(function(item, index) {
		             addPlanFListTable(item,index);
		         });
		         $('#myPlanFListModal').modal('toggle');
		     } else {
		         var trStr = "<tr><td style=\"text-align:center;\" colspan=\"4\">데이타가 없습니다.</td></tr>";
		         $tbody.append(trStr);
		         alert("저장된 수익분석이 없습니다. \n저장후 불러오기를 사용하세요");
		     }
		 }

		 // 수익성 검토 리스트에 행 추가
		 function addPlanFListTable(pPlanListItem,pIndex) {
		     var trStr = "<tr style=\"cursor:pointer;cursor:hand;\" onclick=\"fnPlanFSelect('" + pPlanListItem.PLANF_ID + "'); return false;\">";
		     trStr += "<td>" + (pIndex+1) + "</td>";
		     trStr += "<td>" + pPlanListItem.PLANF_TITLE + "</td>";
		     trStr += "<td>" + pPlanListItem.USER_ID + "</td>";
		     trStr += "<td>" + pPlanListItem.REGIST_DT + "</td>";
		     trStr += "</tr>";
		     $('#trModalPlanList > tbody').append(trStr);
		 }		 

		 // 수익성 검토 리스트에 행 추가
		 function fnPlanFSelect(pPLANF_ID) {
			$('[name=PLANF_ID]').val(pPLANF_ID);
			$('#aform').attr({ action : '/front/selectPlanF.do', method : 'post' }).submit();
		 }		 
		 
		//초기화 
		function clearJson() {
			if(confirm("초기화 하시겠습니까?")){
				
				PLANF_SALE_JSON = {
					    "SALE": {
					        "menu": [],
					        "menuM": [],
					        "menuY": []
					    },
					    "COST": {
					        "cost": [],
					        "costM": [],
					        "costY": []
					    },
					    "JOB": {
					        "job01": [],
					        "job02": [],
					        "job01M": [],
					        "job02M": [],
					        "job01Y": [],
					        "job02Y": []
					    },
					    "INVEST": {
					        "invest01": [],
					        "invest02": [],
					        "invest03": [],
					        "invest04": [],
					        "invest05": [],
					        "invest06": [],
					        "invest07": [],
					        "invest08": []
					    },
					    "LOAN": {
					        "loan01": [],
					        "loan02": []
					    },
					    "MCOST": {
					        "MCost": [],
					        "MCostM": [],
					        "MCostY": []
					    },
					    "SCOST": {
					        "SCost": [],
					        "SCostM": [],
					        "SCostY": []
					    }
					};
				}
				initJson();
			}
		
		