package com.anyview.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * 
 * @ClassName: CreateExcel 
 * @Description: 生成excel工具�?
 * @date 2013-1-23 下午01:55:49
 * @author XHN
 */
public class CreateExcel {

	private String path;//生成文件路径和文件名
	private HSSFWorkbook workbook;
	private List<HSSFSheet> sheetList;
	
	/**
	 * 
	 * <p>Title: </p> 
	 * <p>Description: 参数path是文件的绝对路径</p> 
	 * @param path
	 */
	public CreateExcel(String path) {
		this.setPath(path);
		this.workbook = new HSSFWorkbook();
		this.sheetList = new LinkedList<HSSFSheet>();
	}
	
	/**
	 * 
	 * @Title: addSheet 
	 * @Description: 生成excel工作表，名字为null则为缺省�?
	 * @param index
	 * @param name
	 * @return void 
	 * @throws
	 */
	public HSSFSheet addSheet(String name){
		HSSFSheet sheet = null;
		if(name == null)
			sheet = this.workbook.createSheet();
		else
			sheet = this.workbook.createSheet(name);
		sheetList.add(sheet);
		return sheet;
	}
	/**
	 * 
	 * @Title: getSheet 
	 * @Description: 获取某个工作�?
	 * @param index
	 * @return 
	 * @return HSSFSheet 
	 * @throws
	 */
	public HSSFSheet getSheet(int index){
		return sheetList.get(index);
	}
	/**
	 * 
	 * @Title: insertRow 
	 * @Description: 在某工作表中某行添加行数据并返回此行
	 * @param sheet
	 * @param indexRow
	 * @param data
	 * @return 
	 * @return HSSFRow 
	 * @throws
	 */
	public HSSFRow insertRow(HSSFSheet sheet,short indexRow,List<String> data){
		HSSFRow row = sheet.createRow((short)indexRow);
		for(int i = 0;i<data.size();i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(data.get(i));
		}
		return row;
	}
	/**
	 * 
	 * @Title: insertBlock 
	 * @Description: 添加�?组数据到excel表，返回下一�?
	 * @param sheet
	 * @param indexRow
	 * @param block(子元素表示一行数据，子元素的子元素是�?个格内容)
	 * @return short 
	 * @throws
	 */
	public short insertBlock(HSSFSheet sheet,short indexRow,List<List<String>> block){
		short i = indexRow;
		for(List<String> row : block){
			insertRow(sheet,i,row);
			i++;
		}
		return i;
	}
	
	/**
	 * 
	 * @Title: createOutputStream 
	 * @Description: 文件输入
	 * @return boolean 
	 * @throws
	 */
	public boolean createOutputStream(){
		try {
			FileOutputStream fOut = new FileOutputStream(path);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	} 
	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
