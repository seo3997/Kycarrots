package com.whomade.kycarrots.framework.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class PlanFUtil {
	
	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long[] getItemYAmtArr(JSONArray itemYArray, String key) {
	    // 결과 배열 초기화
	    Long[] values = new Long[]{0L, 0L, 0L};

	    // menuYArray가 null이거나 크기가 0인 경우 초기화된 값을 바로 반환
	    if (itemYArray == null || itemYArray.size() == 0) {
	        return values;
	    }

	    // menuY 배열 반복
	    for (Iterator i = itemYArray.iterator(); i.hasNext();) {
	        JSONObject ob = (JSONObject) i.next();
	        JSONArray YMenuAmtArr = ob.getJSONArray(key);

	        // YMenuAmt 배열 반복
	        for (int j = 0; j < YMenuAmtArr.size(); j++) {
	            try {
	                Long YMenuAmtValue = YMenuAmtArr.getLong(j);
	                if (j == 0) values[0] += YMenuAmtValue;
	                if (j == 1) values[1] += YMenuAmtValue;
	                if (j == 2) values[2] += YMenuAmtValue;
	            } catch (JSONException e) {
	                // JSONException 처리
	                e.printStackTrace();
	            }
	        }
	    }

	    return values;
	}

	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long getItemSumAmtArr(JSONArray itemYArray, String key) {
	    // 결과 배열 초기화
	    Long values = 0L;

	    // menuYArray가 null이거나 크기가 0인 경우 초기화된 값을 바로 반환
	    if (itemYArray == null || itemYArray.size() == 0) {
	        return values;
	    }

	    Long iAmt = 0L;
	    // menuY 배열 반복
	    for (Iterator i = itemYArray.iterator(); i.hasNext();) {
	        JSONObject ob = (JSONObject) i.next();
	        iAmt =  iAmt + ob.getLong(key);

	    }
	    values = iAmt;
	    return values;
	}
	
	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long[] getItemYAmtAccArr(JSONArray itemYArray, String key) {
	    // 결과 배열 초기화
	    Long[] values = new Long[]{0L, 0L, 0L};
	    Long[] accValues = new Long[]{0L, 0L, 0L};

	    // menuYArray가 null이거나 크기가 0인 경우 초기화된 값을 바로 반환
	    if (itemYArray == null || itemYArray.size() == 0) {
	        return accValues;
	    }

	    // menuY 배열 반복
	    for (Iterator i = itemYArray.iterator(); i.hasNext();) {
	        JSONObject ob = (JSONObject) i.next();
	        JSONArray YMenuAmtArr = ob.getJSONArray(key);

	        // YMenuAmt 배열 반복
	        for (int j = 0; j < YMenuAmtArr.size(); j++) {
	            try {
	                Long YMenuAmtValue = YMenuAmtArr.getLong(j);
	                if (j == 0) values[0] += YMenuAmtValue;
	                if (j == 1) values[1] += YMenuAmtValue;
	                if (j == 2) values[2] += YMenuAmtValue;
	            } catch (JSONException e) {
	                // JSONException 처리
	                e.printStackTrace();
	            }
	        }
	    }

	    accValues[0] = values[0];
	    accValues[1] = values[0]+values[1];
	    accValues[2] = values[0]+values[2];
	    
	    return accValues;
	}

	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	 public static Long[] getItemYAmtFindArr(JSONArray itemArr,String key, String targetCd,JSONArray itemFindArr,String findKey) {
		Long[] values = new Long[]{0L, 0L, 0L};
	    // menuYArray가 null이거나 크기가 0인 경우 초기화된 값을 바로 반환
	    if (itemArr == null || itemArr.size() == 0) {
	        return values;
	    }

	    if (itemFindArr == null || itemFindArr.size() == 0) {
	        return values;
	    }

	    if (itemArr.size() != itemFindArr.size()) {
	        return values;
	    }
	    
	    List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < itemArr.size(); i++) {
            JSONObject item = itemArr.getJSONObject(i);
	        String  MCostCd = item.getString(key);
            
            if(MCostCd.equals(targetCd)) {
	        	indexes.add(i);
	        }
            
        }
        
    	//System.out.println("indexes.size():"+indexes.size());

        for (int index : indexes) {
        	JSONObject findItem = itemFindArr.getJSONObject(index);
        	JSONArray yItemAmtArr = findItem.getJSONArray(findKey);
     	   for (int j = 0; j < yItemAmtArr.size(); j++) {
  	            try {
  	                Long YMenuAmtValue = yItemAmtArr.getLong(j);
  	                values[j] = values[j] + YMenuAmtValue;
  	            } catch (JSONException e) {
  	                // JSONException 처리
  	                e.printStackTrace();
  	            }
  	        }

        	
        }

	    
        return values;
	}
	
	

	
	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long[] getItemYMinusArr(Long[] p1, Long[] p2) {
	    // 결과 배열 초기화
	    Long[] values = new Long[]{0L, 0L, 0L};

	    if (p1 == null || p1.length == 0) {
	        return values;
	    }

	    if (p2 == null || p2.length == 0) {
	        return p1;
	    }

	    values[0] = p1[0]-p2[0];
	    values[1] = p1[1]-p2[1];
	    values[2] = p1[2]-p2[2];
	    
	    return values;
	}

	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long[] getItemYPlusArr(Long[] p1, Long[] p2) {
	    // 결과 배열 초기화
	    Long[] values = new Long[]{0L, 0L, 0L};

	    if (p1 == null || p1.length == 0) {
	        return values;
	    }

	    if (p2 == null || p2.length == 0) {
	        return p1;
	    }

	    values[0] = p1[0]+p2[0];
	    values[1] = p1[1]+p2[1];
	    values[2] = p1[2]+p2[2];
	    
	    return values;
	}
	
	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	
	public static Long[] getItemYPlusAccArr(Long[] p1) {
	    // 결과 배열 초기화
	    Long[] values = new Long[]{0L, 0L, 0L};

	    if (p1 == null || p1.length == 0) {
	        return values;
	    }

	    values[0] = p1[0];
	    values[1] = p1[0]+p1[1];
	    values[2] = p1[0]+p1[1]+p1[2];
	    
	    return values;
	}
	
	 public static List<Long> findIndexesOfItem(JSONArray itemArr,String key, String targetCd,JSONArray itemFindArr,String findKey) {
        List<Integer> indexes = new ArrayList<>();
        
        for (int i = 0; i < itemArr.size(); i++) {
            JSONObject item = itemArr.getJSONObject(i);
	        String  MCostCd = item.getString(key);
            
            if(MCostCd.equals(targetCd)) {
	        	indexes.add(i);
	        }
            
        }

        List<Long> returnArr = new ArrayList<>();
        for (int index : indexes) {
        	JSONObject item = itemArr.getJSONObject(index);
        	returnArr.add(item.getLong("findKey"));
        }

        return returnArr;
	 }

	 
	 
	 
	 public static JSONArray getFindItemArr(JSONArray itemArr, List<Integer> indexes) {
        JSONArray resultArray = new JSONArray();
        for (int index : indexes) {
            resultArray.add(itemArr.get(index));
        }
        return resultArray;
	 }       
	  
	 public static double[] setBizRate(int pBizcd, Long[] pBizC3Years, Long[] pBizC4Years, int pPoint) {
        double[] iBizRates = new double[3];

        for (int i = 0; i < 3; i++) {
               iBizRates[i] = 0;
        }

        for (int i = 0; i < 3; i++) {
            if (pBizC4Years[i] > 0) {
               	iBizRates[i] = (double) pBizC3Years[i] / pBizC4Years[i];
           }
        }

        for (int i = 0; i < iBizRates.length; i++) {
            switch (pBizcd) {
            	case 1:
            		iBizRates[i] = Double.parseDouble(String.format("%." + pPoint + "f", iBizRates[i]));
                break;
            	case 2:
               		iBizRates[i] *= 100;
            		iBizRates[i] = Double.parseDouble(String.format("%." + pPoint + "f", iBizRates[i]));
                break;
            	case 3:
               		iBizRates[i] *= 100;
               		iBizRates[i] = iBizRates[i]/12;
            		iBizRates[i] = Double.parseDouble(String.format("%." + pPoint + "f", iBizRates[i]));
                break;
                default:
                    iBizRates[i] *= 100;
                    iBizRates[i] = Double.parseDouble(String.format("%." + pPoint + "f", iBizRates[i]));
                    break;
            }
        }
        
        return iBizRates;
	 }
	 
	 public static String[] setBizRateAsString(int pBizcd, Long[] pBizC3Years, Long[] pBizC4Years, int pPoint) {
		    double[] iBizRates = setBizRate(pBizcd, pBizC3Years, pBizC4Years, pPoint);
		    String[] result = new String[iBizRates.length];

		    for (int i = 0; i < iBizRates.length; i++) {
		        result[i] = String.format("%." + pPoint + "f", iBizRates[i]);
		    }

		    return result;
		}

	 public static String[] setBizRateAsString(double[] pRate,int pPoint) {
		    String[] result = new String[pRate.length];

		    for (int i = 0; i < 3; i++) {
		    	result[i] = "";
	        }

		    for (int i = 0; i < pRate.length; i++) {
		        result[i] = String.format("%." + pPoint + "f", pRate[i]);
		    }

		    return result;
		}

	 public static String[] setBizTrueString(String workCd, double[] pRate, int pBizcd) {
		    String[] result = new String[pRate.length];

		    for (int i = 0; i < 3; i++) {
		    	result[i] = "?";
	        }

		    for (int i = 0; i < pRate.length; i++) {
		    	if(pBizcd>1)
		    		result[i] = getBizTrue(workCd,pRate[i]/100,pBizcd);
		    	else 
			        result[i] = getBizTrue(workCd,pRate[i],pBizcd);
		    }

		    return result;
	}

	 public static String getBizTrue(String workCd, double pRate, int pBizcd) {
		    String sReturn = "?";
		    
		    if (pBizcd == 1) {
		        if (workCd.equals("1")) {
		            if (pRate >= 1.5) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate >= 2.7) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate >= 1.8) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate >= 0.9) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate >= 1.4) sReturn = "적정";
		        }
		    }
		    
		    if (pBizcd == 2) {
		        if (workCd.equals("1")) {
		            if (pRate >= 0.1) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate >= 0.108) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate >= 0.162) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate >= 0.324) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate >= 0.218) sReturn = "적정";
		        }
		    }
		    
		    if (pBizcd == 3) {
		        if (workCd.equals("1")) {
		            if (pRate >= 0.02) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate >= 0.027) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate >= 0.027) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate >= 0.027) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate >= 0.027) sReturn = "적정";
		        }
		    }
		    
		    if (pBizcd == 4) {
		        if (workCd.equals("1")) {
		            if (pRate <= 0.15) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate <= 0.088) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate <= 0.22) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate <= 0.275) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate <= 0.44) sReturn = "적정";
		        }
		    }
		    
		    if (pBizcd == 5) {
		        if (workCd.equals("1")) {
		            if (pRate <= 0.05) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate <= 0.088) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate <= 0.11) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate <= 0.165) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate <= 0.11) sReturn = "적정";
		        }
		    }
		    
		    if (pBizcd == 6) {
		        if (pRate >= 0.5) sReturn = "적정";
		    }
		    
		    if (pBizcd == 7) {
		        if (workCd.equals("1")) {
		            if (pRate <= 0.66) sReturn = "적정";
		        } else if (workCd.equals("2")) {
		            if (pRate <= 0.66) sReturn = "적정";
		        } else if (workCd.equals("3")) {
		            if (pRate <= 0.736) sReturn = "적정";
		        } else if (workCd.equals("4")) {
		            if (pRate <= 0.66) sReturn = "적정";
		        } else if (workCd.equals("5")) {
		            if (pRate <= 0.80) sReturn = "적정";
		        }
		    }
		    
		    return sReturn;
		}	 
	 
	 public static Long[] getMonthAmt(Long[] pYearAmt) {
		    Long[] iBizAmtReturn = new Long[3];
		    
		    for (int i = 0; i < 3; i++) {
		        iBizAmtReturn[i] = 0L;
		    }
		    
		    for (int i = 0; i < 3; i++) {
		        if (pYearAmt[i] > 0) {
		            BigDecimal monthAmt = BigDecimal.valueOf(pYearAmt[i]).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
		            iBizAmtReturn[i] = monthAmt.setScale(0, RoundingMode.HALF_UP).longValue();
		        }
		    }
		    
		    return iBizAmtReturn;
	}

	public static double getCalRate(Long pNumber1, Long pNumber2, int pPoint) {
	    double iReturn = 0;

	    if (pNumber2 == 0) return iReturn;

	    iReturn = (double) (pNumber1 * 100) / pNumber2;
	    iReturn = Double.parseDouble(String.format("%." + pPoint + "f", iReturn));
	    return iReturn;
	}	 


	public static double[] getRateArr(Long[] p1Arr,Long[] p2Arr,int pPoint) {
		double[] iBizRates = new double[3];
		    
	    for (int i = 0; i < 3; i++) {
	    	iBizRates[i] = 0;
	    }
	    
	    for (int i = 0; i < 3; i++) {
	    	iBizRates[i] = getCalRate (p1Arr[i],p2Arr[i],pPoint);
	    }
	    
	    return iBizRates;
	}

	public static double getDivideAmt(Long pNumber1, double pNumber2, int pPoint) {
	    double iReturn = 0;

	    if (pNumber2 == 0) return iReturn;

	    iReturn = (double) (pNumber1) / pNumber2;
	    iReturn = Double.parseDouble(String.format("%." + pPoint + "f", iReturn));
	    return iReturn;
	}	 

	public static Long[] getRateArr(Long[] p1Arr,double [] p2Arr,int pPoint) {
		
		Long[] iBizRates = new Long[3];
		    
	    for (int i = 0; i < 3; i++) {
	    	iBizRates[i] = 0L;
	    }
	    
	    for (int i = 0; i < 3; i++) {
	    	iBizRates[i] = (long) getDivideAmt (p1Arr[i],p2Arr[i]/100,pPoint);
	    }
	    
	    return iBizRates;
	}

	
	public static Long[] deceAmtArr(Long[] p1Arr, Long[] p2Arr, Long[] p3Arr) {
	    Long[] iBizRates = new Long[3];

	    // 배열이 비어 있는지 체크하고 비어 있다면 모든 값에 0을 설정
	    if (p1Arr == null || p1Arr.length == 0 || p2Arr == null || p2Arr.length == 0 || p3Arr == null || p3Arr.length == 0) {
	        for (int i = 0; i < 3; i++) {
	            iBizRates[i] = 0L;
	        }
	        return iBizRates;
	    }

	    for (int i = 0; i < 3; i++) {
	        iBizRates[i] = deceAmt(p1Arr[i], p2Arr[i], p3Arr[i]);
	    }

	    return iBizRates;
	}
	// 손인분기점, 목표매출액 금액 맞추기
	public static long deceAmt(Long input, Long input1, Long input2) {
	    if (input1 == 0 || input2 == 0) {
	        return 0L;
	    }
	    
	    long result = (long) (Math.floor(input / ((double) input1 / input2) / 100) * 100); // 입력값을 input1로 나눈 후 내림하고, 다시 100을 곱함
	    return result; // 결과 반환
	}
	 
}
