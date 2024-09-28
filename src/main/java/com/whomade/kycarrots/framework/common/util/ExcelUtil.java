package com.whomade.kycarrots.framework.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ExcelUtil.java
 * 3. Package  : egovframework.framework.common.util
 * 4. Comment  : 엑셀관련 클래스
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 10. 17. 오후 2:44:02
 * </PRE>
 */ 
public class ExcelUtil {

	protected Log log = LogFactory.getLog(this.getClass());

	private MessageSource messageSource;
	
	/**
	 * @return the messageSource
	 */
	protected MessageSource getMessageSource() {
	    return messageSource;
	}
	
	// xls용 
	private HSSFWorkbook wb;
	private HSSFSheet st;
	private HSSFCell c;
	// xlsx용 
	private XSSFWorkbook x_wb;
	private XSSFSheet x_st;
	private XSSFCell x_c;
	
	// 현재 엑셀 문서가 xls, xlsx 인지 결정 
	private String type;
	
	/**
	 * <PRE>
	 * 1. MethodName : ExcelUtil
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   :  MultipartFile 생성자
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:10:50
	 * </PRE>
	 *   @param mFile
	 *   @throws Exception
	 */
	public ExcelUtil() throws Exception {}
	
	public ExcelUtil(MultipartFile mFile) throws Exception { this.loadWorkbook(mFile); }
	
	public ExcelUtil(String filepath, String type) throws Exception { this.loadWorkbook(filepath, type); }
	
	public ExcelUtil(InputStream fileIn, String type) throws Exception { this.loadWorkbook(fileIn, type); }
	
	/**
	 * <PRE>
	 * 1. MethodName : loadWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : MultipartFile로 엑셀파일 불러오는 함수
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:12:34
	 * </PRE>
	 *   @return void
	 *   @param mFile
	 *   @throws Exception
	 */
	public void loadWorkbook(MultipartFile mFile) throws Exception 
	{
		
		if (mFile.getOriginalFilename().toLowerCase().endsWith(".xls"))
		{
			InputStream fis = mFile.getInputStream();
	        this.loadWorkbook(fis, "xls");
	        fis.close();
		}
		if (mFile.getOriginalFilename().toLowerCase().endsWith(".xlsx"))
		{
			InputStream fis = mFile.getInputStream();
	        this.loadWorkbook(fis, "xlsx");
	        fis.close();
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : loadWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : filepath로 엑셀 로드
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:15:36
	 * </PRE>
	 *   @return void
	 *   @param filepath
	 *   @param type (확장자명 소문자)
	 *   @throws Exception
	 */
	public void loadWorkbook(String filepath, String type) throws Exception {
		FileInputStream fileIn = new FileInputStream(filepath);
		type = type.toLowerCase();
		
		this.loadWorkbook(fileIn, type);
	    fileIn.close();
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : loadWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : InputStream 으로 엑셀파일 로드시 확장자명 적어줌.
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:09:07
	 * </PRE>
	 *   @return void
	 *   @param fileIn
	 *   @param type (확장자명 소문자)
	 *   @throws Exception
	 */
	public void loadWorkbook(InputStream fileIn, String type) throws Exception {
	    log.debug("ExcelUtil loadWorkbook ...");
	    
	    if(type.toLowerCase().equals("xls"))
	    {
	        POIFSFileSystem fs = new POIFSFileSystem(fileIn);
	        this.wb = new HSSFWorkbook(fs);
	        this.type = "xls";
	    }
	    if(type.toLowerCase().equals("xlsx"))
	    {
	    	this.x_wb = new XSSFWorkbook(fileIn);
	    	this.type = "xlsx";
	    }
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : loadSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북의 해당 제목의 시트를 셋팅 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:43:48
	 * </PRE>
	 *   @return void
	 *   @param sheet
	 *   @throws Exception
	 */
	public void loadSheet(String sheet) throws Exception {
		if(this.type.equals("xls"))	{ this.st = this.wb.getSheet(sheet); }
		else { this.x_st = this.x_wb.getSheet(sheet); }
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : loadSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북의 해당 index의 시트를 셋팅 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 3:46:58
	 * </PRE>
	 *   @return void
	 *   @param num
	 *   @throws Exception
	 */
	public void loadSheet(int num) throws Exception {
		if(this.type.equals("xls"))	{ this.st = this.wb.getSheetAt(num); }
		else { this.x_st = this.x_wb.getSheetAt(num); }
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : loadData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 설정되어있는 시트의 내용을 가져온다
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:13:25
	 * </PRE>
	 *   @return List
	 *   @return
	 *   @throws Exception
	 */
	public List loadData() throws Exception {
		
		return loadData(0, 0);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : loadData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 설정되어있는 시트의 내용을 가져온다.
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:13:44
	 * </PRE>
	 *   @return List
	 *   @param startRow
	 *   @param startCol
	 *   @return
	 *   @throws Exception
	 */
	public List loadData(int startRow, int startCol) throws Exception {
	
		int endRow = -1;
		int endCol = -1;
		
		if(this.type.equals("xls"))
		{
			// 값이 들어가있는 row의 위치이다(15번째이면 14)
	    	endRow = this.st.getLastRowNum() + 1;
	    	endCol = -1;
	    			
	    	// 시작행부터 끝행까지 반복
			for(int i = startRow; i < endRow; i++)
			{
				// row 가져옴
				HSSFRow row = this.st.getRow(i);
				
				// row 가 있을경우만
				if(row != null)
				{
					// 가장 많은 셀값 가져오기
					if(endCol < (int)row.getLastCellNum()){ endCol = (int)row.getLastCellNum(); }
				}
			}
		}
		else
		{
			// 값이 들어가있는 row의 위치이다(15번째이면 14)
	    	endRow = this.x_st.getLastRowNum() + 1;
	    	endCol = -1;
	    			
	    	// 시작행부터 끝행까지 반복
			for(int i = startRow; i < endRow; i++)
			{
				// row 가져옴
				XSSFRow row = this.x_st.getRow(i);
				
				// row 가 있을경우만
				if(row != null)
				{
					// 가장 많은 셀값 가져오기
					if(endCol < (int)row.getLastCellNum()){ endCol = (int)row.getLastCellNum(); }
				}
			}
		}
		
		return loadData(startRow, startCol, endRow, endCol);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : loadData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 설정되어있는 시트의 내용을 가져온다.
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:14:01
	 * </PRE>
	 *   @return List
	 *   @param startRow
	 *   @param startCol
	 *   @param endRow
	 *   @param endCol
	 *   @return
	 *   @throws Exception
	 */
	public List loadData(int startRow, int startCol, int endRow, int endCol) throws Exception {
		List dataList = new ArrayList();
		
		if(this.type.equals("xls"))
		{
			// 시작행부터 끝행까지 반복
			for(int i = startRow; i < endRow; i++)
			{
				// row정보를 담을 리스트
				List rowInfo = new ArrayList();
				// row 가져옴
				HSSFRow row = st.getRow(i);
				
				// 셀 반복
				for(int j = startCol; j < endCol; j++)
				{
					if(row != null)
					{
						// cell 가져옴
						HSSFCell cell = row.getCell(j);
						
						// cell이 널이 아닌경우
						if(cell != null)
						{
							// cell 타입 확인
							int type = cell.getCellType();
							String value = "";
							
							switch(type)
							{
								case HSSFCell.CELL_TYPE_STRING :
								    value = (cell.getRichStringCellValue().getString()!=null? cell.getRichStringCellValue().getString(): "") ;
								    rowInfo.add(value);
								    break;
								    
								case HSSFCell.CELL_TYPE_NUMERIC :
								    if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								    	value = sdf.format(cell.getDateCellValue());
								    } else {
	    							    value = "" + cell.getNumericCellValue();
	    							    value = value.substring(value.indexOf("."), value.length()).equals(".0") ? value.replace(".0","") : value;
								    }
								    rowInfo.add(value);
								    break;
								case HSSFCell.CELL_TYPE_BOOLEAN :
								    value = "" + cell.getBooleanCellValue();
								    rowInfo.add(value);
								    break;
	
							    case HSSFCell.CELL_TYPE_FORMULA :
								    value = "" + cell.getCellFormula();
								    rowInfo.add(value);
								    break;
	
							    case HSSFCell.CELL_TYPE_BLANK:
							    	rowInfo.add("");
							    	break;
								    
							    case HSSFCell.CELL_TYPE_ERROR:
							    	rowInfo.add("");
							    	break;
							    	
							    default:
							    	rowInfo.add("");
							    	break;
							}
						} 
						// null 일경우 빈값을 넣어준다.
						else { rowInfo.add(""); }
					}
					// row가 널일경우 rowinfo에 해당 개수만큼 빈값을 넣어준다.
					else { rowInfo.add(""); }
				}
				
//				System.out.println(rowInfo);
				// dataList 에 row정보를 담는다
				dataList.add(rowInfo);
			}
		}
		else
		{
			// 시작행부터 끝행까지 반복
			for(int i = startRow; i < endRow; i++)
			{
				// row정보를 담을 리스트
				List rowInfo = new ArrayList();
				// row 가져옴
				XSSFRow row = x_st.getRow(i);
				
				// 셀 반복
				for(int j = startCol; j < endCol; j++)
				{
					if(row != null)
					{
						// cell 가져옴
						XSSFCell cell = row.getCell(j);
						
						// cell이 널이 아닌경우
						if(cell != null)
						{
							// cell 타입 확인
							int type = cell.getCellType();
							String value = "";
							
							switch(type)
							{
								case XSSFCell.CELL_TYPE_STRING :
								    value = (cell.getRichStringCellValue().getString()!=null? cell.getRichStringCellValue().getString(): "") ;
								    rowInfo.add(value);
								    break;
								    
								case XSSFCell.CELL_TYPE_NUMERIC :
								    if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								    	value = sdf.format(cell.getDateCellValue());
								    } else {
	    							    value = "" + cell.getNumericCellValue();
	    							    value = value.substring(value.indexOf("."), value.length()).equals(".0") ? value.replace(".0","") : value;
								    }
								    rowInfo.add(value);
								    break;
								case XSSFCell.CELL_TYPE_BOOLEAN :
								    value = "" + cell.getBooleanCellValue();
								    rowInfo.add(value);
								    break;
	
							    case XSSFCell.CELL_TYPE_FORMULA :
								    value = "" + cell.getCellFormula();
								    rowInfo.add(value);
								    break;
	
							    case XSSFCell.CELL_TYPE_BLANK:
							    	rowInfo.add("");
							    	break;
								    
							    case XSSFCell.CELL_TYPE_ERROR:
							    	rowInfo.add("");
							    	break;
							    	
							    default:
							    	rowInfo.add("");
							    	break;
							}
						} 
						// null 일경우 빈값을 넣어준다.
						else { rowInfo.add(""); }
					}
					// row가 널일경우 rowinfo에 해당 개수만큼 빈값을 넣어준다.
					else { rowInfo.add(""); }
				}
				
//				System.out.println(rowInfo);
				// dataList 에 row정보를 담는다
				dataList.add(rowInfo);
			}
		}
		
		return dataList;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 워크북을 생성한다(xlsx만 생성)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:35:52
	 * </PRE>
	 *   @return void
	 *   @throws Exception
	 */
	public void createWorkbookXLSX() throws Exception {
		this.x_wb = new XSSFWorkbook();
		this.type = "xlsx";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 워크북을 생성한다(xlsx만 생성)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:16
	 * </PRE>
	 *   @return void
	 *   @param data (데이터)
	 *   @throws Exception
	 */
	public void createWorkbookXLSX(List data) throws Exception {
		this.createWorkbookXLSX();
		this.createSheetXLSX();
		this.createDataXLSX(data, null);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북에 시트를 생성한다(xlsx 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:32
	 * </PRE>
	 *   @return void
	 *   @throws Exception
	 */
	public void createSheetXLSX() throws Exception {
		
		try {
			this.x_st = this.x_wb.createSheet();
		} catch (NullPointerException e) {
			log.error("워크북이 존재하지 않습니다");
			e.printStackTrace();
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북에 시트를 생성한다(xlsx 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:50
	 * </PRE>
	 *   @return void
	 *   @param sheetName (시트 명)
	 *   @throws Exception
	 */
	public void createSheetXLSX(String sheetName) throws Exception {
		
		try {
			this.x_st = this.x_wb.createSheet(sheetName);
		} catch (NullPointerException e) {
			log.error("워크북이 존재하지 않습니다");
			e.printStackTrace();
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 데이터를 엑셀에 쓰기(xlsx 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:17:21
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param style (cellstyle)
	 *   @throws Exception
	 */
	public void createDataXLSX(List data, HSSFCellStyle style) throws Exception {
		if(this.type.equals("xlsx"))
		{
			for(int i = 0; i < data.size(); i++)
	    	{
				List rowData = (List)data.get(i);
				XSSFRow r = this.x_st.createRow(i);
				
				for(int j = 0; j < rowData.size(); j++)
				{
					XSSFCell c = r.createCell(j);
					c.setCellStyle(style);
					
					// 스트링, 숫자, 불린 값 여부 확인
					if(String.class.isInstance(rowData.get(j))){
						c.setCellValue((String)rowData.get(j));
					} 
					else if(Integer.class.isInstance(rowData.get(j))){
						c.setCellValue((Integer)rowData.get(j));
					}
					else if(Long.class.isInstance(rowData.get(j))){
						c.setCellValue((Long)rowData.get(j));
					}
					else if(Float.class.isInstance(rowData.get(j))){
						c.setCellValue((Float)rowData.get(j));
					}
					else if(Double.class.isInstance(rowData.get(j))){
						c.setCellValue((Double)rowData.get(j));
					}
					else if(Boolean.class.isInstance(rowData.get(j))){
						c.setCellValue((Boolean)rowData.get(j));
					} else {
						c.setCellValue((String)rowData.get(j));
					}
				}
	    	}
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : addData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 데이터 추가(마지막 줄부터)(xlsx 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:18:38
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param style (cellstyle)
	 *   @throws Exception
	 */
	public void addDataXLSX(List data, XSSFCellStyle style) throws Exception {
		int endRow = -1;
		
		if(this.type.equals("xlsx"))
		{
			// 값이 들어가있는 row의 위치이다(15번째이면 14)
	    	endRow = this.x_st.getLastRowNum() + 1;
		}
		
		this.addDataXLSX(data, endRow, style);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : addData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 해당 row 부터 데이터 추가(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:19:09
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param row
	 *   @param style
	 *   @throws Exception
	 */
	public void addDataXLSX(List data, int row, XSSFCellStyle style) throws Exception {
		
		if(this.type.equals("xlsx"))
		{
			for(int i = 0; i < data.size(); i++)
	    	{
				List rowData = (List)data.get(i);
				XSSFRow r = this.x_st.createRow(row++);
				
				for(int j = 0; j < rowData.size(); j++)
				{
					XSSFCell c = r.createCell(j);
					c.setCellStyle(style);
					
					// 스트링, 숫자, 불린 값 여부 확인
					if(String.class.isInstance(rowData.get(j))){
						c.setCellValue((String)rowData.get(j));
					} 
					else if(Integer.class.isInstance(rowData.get(j))){
						c.setCellValue((Integer)rowData.get(j));
					}
					else if(Long.class.isInstance(rowData.get(j))){
						c.setCellValue((Long)rowData.get(j));
					}
					else if(Float.class.isInstance(rowData.get(j))){
						c.setCellValue((Float)rowData.get(j));
					}
					else if(Double.class.isInstance(rowData.get(j))){
						c.setCellValue((Double)rowData.get(j));
					}
					else if(Boolean.class.isInstance(rowData.get(j))){
						c.setCellValue((Boolean)rowData.get(j));
					} else {
						c.setCellValue((String)rowData.get(j));
					}
				}
	    	}
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 워크북을 생성한다(xls만 생성)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:35:52
	 * </PRE>
	 *   @return void
	 *   @throws Exception
	 */
	public void createWorkbook() throws Exception {
		this.wb = new HSSFWorkbook();
		this.type = "xls";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createWorkbook
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 워크북을 생성한다(xls만 생성)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:16
	 * </PRE>
	 *   @return void
	 *   @param data (데이터)
	 *   @throws Exception
	 */
	public void createWorkbook(List data) throws Exception {
		this.createWorkbook();
		this.createSheet();
		this.createData(data, null);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북에 시트를 생성한다(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:32
	 * </PRE>
	 *   @return void
	 *   @throws Exception
	 */
	public void createSheet() throws Exception {
		
		try {
			this.st = this.wb.createSheet();
		} catch (NullPointerException e) {
			log.error("워크북이 존재하지 않습니다");
			e.printStackTrace();
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createSheet
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 현재 워크북에 시트를 생성한다(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:36:50
	 * </PRE>
	 *   @return void
	 *   @param sheetName (시트 명)
	 *   @throws Exception
	 */
	public void createSheet(String sheetName) throws Exception {
		
		try {
			this.st = this.wb.createSheet(sheetName);
		} catch (NullPointerException e) {
			log.error("워크북이 존재하지 않습니다");
			e.printStackTrace();
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : createData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 데이터를 엑셀에 쓰기(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:17:21
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param style (cellstyle)
	 *   @throws Exception
	 */
	public void createData(List data, HSSFCellStyle style) throws Exception {
		if(this.type.equals("xls"))
		{
			for(int i = 0; i < data.size(); i++)
	    	{
				List rowData = (List)data.get(i);
				HSSFRow r = this.st.createRow(i);
				
				for(int j = 0; j < rowData.size(); j++)
				{
					HSSFCell c = r.createCell(j);
					c.setCellStyle(style);
					
					// 스트링, 숫자, 불린 값 여부 확인
					if(String.class.isInstance(rowData.get(j))){
						c.setCellValue((String)rowData.get(j));
					} 
					else if(Integer.class.isInstance(rowData.get(j))){
						c.setCellValue((Integer)rowData.get(j));
					}
					else if(Long.class.isInstance(rowData.get(j))){
						c.setCellValue((Long)rowData.get(j));
					}
					else if(Float.class.isInstance(rowData.get(j))){
						c.setCellValue((Float)rowData.get(j));
					}
					else if(Double.class.isInstance(rowData.get(j))){
						c.setCellValue((Double)rowData.get(j));
					}
					else if(Boolean.class.isInstance(rowData.get(j))){
						c.setCellValue((Boolean)rowData.get(j));
					} else {
						c.setCellValue((String)rowData.get(j));
					}
				}
	    	}
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : addData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 데이터 추가(마지막 줄부터)(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:18:38
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param style (cellstyle)
	 *   @throws Exception
	 */
	public void addData(List data, HSSFCellStyle style) throws Exception {
		int endRow = -1;
		
		if(this.type.equals("xls"))
		{
			// 값이 들어가있는 row의 위치이다(15번째이면 14)
	    	endRow = this.st.getLastRowNum() + 1;
		}
		
		this.addData(data, endRow, style);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : addData
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : 해당 row 부터 데이터 추가(xls 파일만 가능)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 8:19:09
	 * </PRE>
	 *   @return void
	 *   @param data
	 *   @param row
	 *   @param style
	 *   @throws Exception
	 */
	public void addData(List data, int row, HSSFCellStyle style) throws Exception {
		
		if(this.type.equals("xls"))
		{
			for(int i = 0; i < data.size(); i++)
	    	{
				List rowData = (List)data.get(i);
				HSSFRow r = this.st.createRow(row++);
				
				for(int j = 0; j < rowData.size(); j++)
				{
					HSSFCell c = r.createCell(j);
					c.setCellStyle(style);
					
					// 스트링, 숫자, 불린 값 여부 확인
					if(String.class.isInstance(rowData.get(j))){
						c.setCellValue((String)rowData.get(j));
					} 
					else if(Integer.class.isInstance(rowData.get(j))){
						c.setCellValue((Integer)rowData.get(j));
					}
					else if(Long.class.isInstance(rowData.get(j))){
						c.setCellValue((Long)rowData.get(j));
					}
					else if(Float.class.isInstance(rowData.get(j))){
						c.setCellValue((Float)rowData.get(j));
					}
					else if(Double.class.isInstance(rowData.get(j))){
						c.setCellValue((Double)rowData.get(j));
					}
					else if(Boolean.class.isInstance(rowData.get(j))){
						c.setCellValue((Boolean)rowData.get(j));
					} else {
						c.setCellValue((String)rowData.get(j));
					}
				}
	    	}
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : getWorkbookXLS
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : xls 타입의 워크북
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:53:13
	 * </PRE>
	 *   @return HSSFWorkbook
	 *   @return
	 *   @throws Exception
	 */
	public HSSFWorkbook getWorkbookXLS() throws Exception {
		return wb;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : getWorkbookXLSX
	 * 2. ClassName  : ExcelUtil
	 * 3. Comment   : xlsx 타입의 워크북
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 17. 오후 4:53:26
	 * </PRE>
	 *   @return XSSFWorkbook
	 *   @return
	 *   @throws Exception
	 */
	public XSSFWorkbook getWorkbookXLSX() throws Exception {
		return x_wb;
	}
	
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}
	
	public void setX_wb(XSSFWorkbook x_wb) {
		this.x_wb = x_wb;
	}
	
	public HSSFSheet getSt() {
		return st;
	}
	
	public XSSFSheet getX_st() {
		return x_st;
	}
	
}

