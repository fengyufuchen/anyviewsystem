package com.anyview.service.function.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.AdminStudentDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminStudentManager;
import com.anyview.utils.TipException;

/**
 * @Description 学生管理控制器类
 * @author DenyunFang
 * @time 2015年8月29日
 * @version 1.0
 */

@Service
public class AdminStudentManagerImpl implements AdminStudentManager{

	@Autowired
	private AdminStudentDao adminStudentDao;
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return Pagination<StudentTable> (封装了学生信息与页面信息的集合)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:26:18
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pagination<ClassStudentTable> getStudentsPage(Map param) {
		Pagination<ClassStudentTable> page = new Pagination<ClassStudentTable>();
		List<ClassStudentTable> stus = adminStudentDao.getStudentsPage(param);
		page.setContent(stus);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(adminStudentDao.getStudentCount(param));
		page.calcutePage();
		return page;
	}
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return Pagination<StudentTable> (封装了学生信息与页面信息的集合)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:26:18
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pagination<StudentTable> getListStudentsPage(Map param) {
		Pagination<StudentTable> page = new Pagination<StudentTable>();
		List<StudentTable> stus = adminStudentDao.getListStudentsPage(param);
		page.setContent(stus);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(adminStudentDao.getListStudentCount(param));
		page.calcutePage();
		return page;
	}
	
	/**
	 * 
	 * @Description: 获取所有学校
	 * @return List<UniversityTable> (返回所有学校列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:48:20
	 */
	@Override
	public List<UniversityTable> selectAllUniversity() {
		return adminStudentDao.selectAllUniversity();
	}
	
	/**
	 * 
	 * @Description: 根据学校ID获取所有学院
	 * @param unID
	 * @return List<ClassTable> (返回该学校的所有学院列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:50:52
	 */
	@Override
	public List<CollegeTable> selectAllCollegeByUnID(Integer unID) {
		return adminStudentDao.selectAllCollegeByUnID(unID);
	}
	
	/**
	 * 
	 * @Description: 根据学院ID获取所有班级
	 * @param ceID
	 * @return List<ClassTable> (返回该学院的所有班级列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:48:49
	 */
	@Override
	public List<ClassTable> selectAllClassByCeId(Integer ceID) {
		return adminStudentDao.selectAllClassByCeId(ceID);
	}

	/**
	 * 
	 * @Description: TODO(根据学生学号Sno和所在学校判断该学生是否存在于数据库) 
	 * @param stu
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:54:08
	 */
	@Override
	public boolean isStudentexist(StudentTable stu) {
		List<StudentTable> studentList = adminStudentDao.getAllStudentByUnId(stu.getUniversity().getUnID());
		for(StudentTable stus : studentList){
			if(stus.getSno().equals(stu.getSno()))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @Description: TODO(保存学生信息到数据库中) 
	 * @param stu
	 * @return boolean (true表示保存成功，false表示保存失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:54:27
	 */
	@Override
	public boolean saveStudent(StudentTable stu) throws TipException {
		return adminStudentDao.saveStudent(stu);
	}

	/**
	 * 
	 * @Description: TODO(根据学生ID删除数据库的学生信息) 
	 * @param sid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:28:24
	 */
	@Override
	public boolean deleteStudentBySid(Integer sid) {
		return adminStudentDao.deleteStudentBySid(sid);
	}

	/**
	 * 
	 * @Description: TODO(根据学生ID、班级ID与相关属性将学生与班级关联) 
	 * @param sid
	 * @param cid
	 * @return boolean (true表示关联成功，false表示关联失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午11:23:27
	 */
	public boolean addStudentInClass(Integer sid, Integer cid, ClassStudentTable cs){
		return adminStudentDao.addStudentInClass(sid, cid, cs);
	}
	
	/**
	 * 
	 * @Description: TODO(批量根据学生ID与班级ID将学生与班级关联) 
	 * @param sid
	 * @param cid
	 * @return boolean (true表示关联成功，false表示关联失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午11:23:27
	 */
	public boolean batAddStudentInClass(Integer sid, Integer cid){
		return adminStudentDao.batAddStudentInClass(sid, cid);
	}
	
	/**
	 * 
	 * @Description: TODO(根据学生ID获取该学生所有信息) 
	 * @param sid
	 * @return List<StudentTable> (返回该学生的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:43:01
	 */
	@Override
	public StudentTable gainStudentBySid(Integer sid) {
		return adminStudentDao.gainStudentBySid(sid);
	}

	/**
	 * 
	 * @Description: TODO(修改数据库的学生信息) 
	 * @param stu
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @throws TipException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:43:13
	 */
	@Override
	public boolean updateStudent(StudentTable stu) throws TipException {
		return adminStudentDao.updateStudent(stu);
	}

	/**
	 * 
	 * @Description: TODO(根据学生id、学生学号Sno和所在学校判断该学生是否存在于数据库) 
	 * @param stu
	 * @return boolean (true表示该学生存在，false表示该学生不存在)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午5:07:26
	 */
	@Override
	public boolean isStudentexistBysid(StudentTable stu) {
		StudentTable s = adminStudentDao.gainStudentBySid(stu.getSid());
		if(s.getSno().equals(stu.getSno()) == false){	//修改页面对学号进行了修改
			List<StudentTable> studentList = adminStudentDao.getAllStudentByUnId(s.getUniversity().getUnID());
			for(StudentTable stus : studentList){
				if(stus.getSno().equals(stu.getSno()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @Description: TODO(根据学生id初始化学生密码) 
	 * @param sid
	 * @return boolean (true表示初始化成功，false表示初始化失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午5:34:00
	 */
	@Override
	public boolean initPassword(Integer sid) {
		return adminStudentDao.initPassword(sid);
	}

	/**
	 * 
	 * @Description: TODO(根据学校ID获取该学校的所有信息列表) 
	 * @param unID
	 * @return List<UniversityTable> (返回该学校的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午7:37:29
	 */
	@Override
	public List<UniversityTable> getUniversityByUnId(Integer unID) {
		return adminStudentDao.getUniversityByUnId(unID);
	}

	/**
	 * 
	 * @Description: TODO(批量添加学生) 
	 * @param stuList
	 * @return List<StudentTable> (返回状态值为"已提交"的学生列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午9:01:04
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List batAddStudent(List stuList) {
		for (int i = 0; i < stuList.size(); ++i) {		
			Map map = (Map) stuList.get(i);
			stuList.set(i, map);
		}
		return adminStudentDao.batAddStudent(stuList);
	}

	/**
	 * 
	 * @Description: TODO(获取和解析excel表内容) 
	 * @param 读取的excel文件
	 * @param theClass
	 * @param claid
	 * @return
	 * @throws InvalidFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午9:16:54
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Map getFileData(File file, String theClass, String claid) throws InvalidFormatException, FileNotFoundException, IOException {
		Map returnMap = new HashMap();
		List<Map> list = new ArrayList<Map>();
		String message = null;
		String[] keys = { "studentNo", "studentName", "studentSex", "passWord" };
		
		HSSFWorkbook wb = null;
		
		try{
			wb = new HSSFWorkbook(new FileInputStream(file));
		}
		catch(IOException e){
			e.printStackTrace();
			returnMap.put("rows", list);
			returnMap.put("message", "文件读取错误");
			return returnMap;
		}
		
		if (wb == null){
			returnMap.put("rows", list);
			returnMap.put("message", "文件读取错误");
			return returnMap;
		}

		int sheets = wb.getNumberOfSheets();

		for (int i = 0; i < sheets; ++i) {

			HSSFSheet sheet = wb.getSheetAt(i);
			if(sheet.getRow(0) == null)continue;
			
			if(!(theClass.equals(sheet.getRow(0).getCell(1).getStringCellValue()))){
				returnMap.put("rows", list);
				returnMap.put("message", "学校不一致,请检查文件中的学校是否与页面选择的学校一致");
				return returnMap;
			}

			int rowNum = sheet.getPhysicalNumberOfRows();
			for (int j = 1; j < rowNum; ++j) {

				HSSFRow row = sheet.getRow(j);
				DecimalFormat df = new DecimalFormat("0");
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("className", sheet.getRow(0).getCell(1).getStringCellValue());
				
				int k = 0;
				int colNum = row.getPhysicalNumberOfCells();
				while(k < colNum) {

					String value = null;
					HSSFCell cell = row.getCell(k);
					switch (cell.getCellType()) {

					case HSSFCell.CELL_TYPE_NUMERIC:
						value = df.format(cell.getNumericCellValue());  
						break;

					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;

					}
					map.put(keys[k++], value);

				}
				map.put("classId", claid);
				map.put("SAttr", "正常");
				list.add(map);
			}

		}
		returnMap.put("rows", list);
		returnMap.put("message", message);
		return returnMap;
	}

}
