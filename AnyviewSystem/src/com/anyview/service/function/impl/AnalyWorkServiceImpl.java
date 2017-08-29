/**   
* @Title: AnalyWorkServiceImpl.java
* @Package com.stusys.service.anyview.impl
* @Description: TODO
* @author xhn 
* @date 2012-11-8 下午09:31:16
* @version V1.0   
*/
package com.anyview.service.function.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.AnalyWorkDAO;
//import com.stusys.entities.anyview.ExerciseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.AWPointInfo;
import com.anyview.entities.AWStuInfo;
import com.anyview.entities.SchemeT;

import com.anyview.entities.CustomExerciseForCTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.service.function.AnalyWorkService;
import com.anyview.utils.CreateExcel;

/**
 * @ClassName: AnalyWorkServiceImpl
 * @Description: 批改作业业务逻辑层具体实现类
 * @author xhn
 * @date 2012-11-8 下午09:31:16
 * 
 */
@Service
public class AnalyWorkServiceImpl implements AnalyWorkService {
	@Autowired
	private AnalyWorkDAO awDao;

	/*
	 * (no  Javadoc)
	* <p>Title: getClassListById</p>
	* <p>Description: </p>
	* @param id
	* @return
	* @see com.stusys.service.anyview.AnalyWorkService#getClassListById(int)
	 */
	@Override
	public List<ClassTable> getClassListById(int id,int courseId) {
		System.out.println("Hello www.dezhi168.co");
		return awDao.getClassesByTeaId(id,courseId);
	}
	/*
	 * (no  Javadoc)
	* <p>Title: getScheme</p>
	* <p>Description: </p>
	* @param cid
	* @param courseId
	* @param tid
	* @return
	* @see com.stusys.service.anyview.AnalyWorkService#getScheme(int, int, int)
	 */
	@Override
	public List<SchemeT> getScheme(int cid, int courseId,int tid) {
		return awDao.getScheme(tid, cid, courseId);
	}
	
	/* (no  Javadoc)
	* <p>Title: getDataGrid</p>
	* <p>Description: </p>
	* @param classId
	* @param schemeId
	* @return
	* @see com.stusys.service.anyview.AnalyWorkService#getDataGrid(int, int)
	*/
	@Override
	public Map<String, Object> getTempDataGrid(int classId, int schemeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<AWPointInfo> point = awDao.getTempDataGridSortByExe(classId, schemeId);
		List<AWStuInfo> stu = awDao.getTempDataGridSortByStu(classId, schemeId);
		if(point != null && stu != null){
			map.put("scheme", point);
			map.put("stu", stu);
		}else{
			map.put("msg", "数据出错");
		}
		return map;
	}
	
	/* (no  Javadoc)
	* <p>Title: getStuDetail</p>
	* <p>Description: </p>
	* @param classId
	* @param schemeId
	* @param problemId
	* @return
	* @see com.stusys.service.anyview.AnalyWorkService#getStuDetail(int, int, int)
	*/
	@Override
	public List<AWStuInfo> getStuDetail(int classId, int schemeId, int problemId) {
		return awDao.getStuDetail(classId, schemeId, problemId);
	}
	/* (no  Javadoc)
	* <p>Title: getPointDetail</p>
	* <p>Description: </p>
	* @param schemeId
	* @param stuId
	* @return
	* @see com.stusys.service.anyview.AnalyWorkService#getPointDetail(int, int)
	*/
	@Override
	public List<AWPointInfo> getPointDetail(int schemeId, int stuId) {
		return awDao.getPointDetail(stuId, schemeId);
	}
	/*
	 * (非 Javadoc) 
	 * <p>Title: updateExercise</p> 
	 * <p>Description: </p> 
	 * @param exId
	 * @param comment
	 * @param score
	 * @return 
	 * @see com.stusys.service.anyview.AnalyWorkService#updateExercise(int, java.lang.String, int)
	 */
	@Override
	public boolean updateExercise(int exId, String comment, float score) {
		
		ExerciseTable ex = awDao.selectById(exId);
		
		//Xml数据修改未处理
		ex.setEcomment(comment);
		ex.setScore(score);
		
		return awDao.saveOrUpdateObject(ex);
	}

	public void setAwDao(AnalyWorkDAO awDao) {
		this.awDao = awDao;
	}

	public AnalyWorkDAO getAwDao() {
		return awDao;
	}
	
	@Override
	public void createFile(String path,int sortType, int CID, int VID,
			Set<Integer> proIdSet,Set<Integer> stuIdSet) {
		List<AWPointInfo> exList = awDao.getTempDataGridSortByExe(CID, VID);
		List<AWStuInfo> stuList = awDao.getTempDataGridSortByStu(CID, VID);
		
		//开始生成文件
		CreateExcel excel = new CreateExcel(path);
		HSSFSheet sheet = excel.addSheet("做题情况");
		List<List<String>> block = new ArrayList<List<String>>();
		List<String> r = null;
		List<String> r1 = null;
		short rowIndex = 1;
		//题目概况
		for(int i = 0;i < exList.size()+2;i++){
			r = new ArrayList<String>();
			AWPointInfo info = null;
			if(i == 0){
				r.add("题目信息");
			}else if(i == 1){
				r.add("序号");
				r.add("章节");
				r.add("习题名");
				r.add("通过人数");
			}else{
				info = exList.get(i-2);
				if(!proIdSet.contains(info.getProblemId())) continue;
				r.add(rowIndex+"");
				r.add(info.getChapName());
				r.add(info.getProName());
				r.add(info.getFinish()+"/"+info.getTotal());
				rowIndex++;
			}
			block.add(r);
		}
		rowIndex = 1;
		//学生概况
		for(int i = 0;i <= stuList.size()+2;i++){
			r = new ArrayList<String>();
			AWStuInfo info = null;
			if(i == 1){
				r.add("学生概况");
			}else if(i == 2){
				r.add("序号");
				r.add("学号");
				r.add("姓名");
				r.add("完成题数");
			}else if(i > 2){
				info = stuList.get(i-3);
				if(!stuIdSet.contains(info.getStuId())) continue;
				r.add(rowIndex+"");
				r.add(info.getStuNo());
				r.add(info.getStuName());
				r.add(info.getFinish()+"/"+info.getTotal());
				rowIndex++;
			}
			block.add(r);
		}
		//按习题显示
		if(sortType == 1){
			rowIndex = 1;
			for(int i = 0;i <= exList.size()+2;i++){
				r = new ArrayList<String>();
				AWPointInfo info = null;
				if(i == 1){
					r.add("题目信息");
				}else if(i == 2){
					r.add("序号");
					r.add("章节");
					r.add("习题名");
					r.add("通过人数");
				}else if(i > 2){
					info = exList.get(i-3);
					if(!proIdSet.contains(info.getProblemId())) continue;
					List<AWStuInfo> stuInfoList = awDao.getStuDetail(CID, VID, info.getProblemId());//获取某题目对应班级所有学生做题情况
					r.add(rowIndex+"");
					r.add(info.getChapName());
					r.add(info.getProName());
					r.add(info.getFinish()+"/"+info.getTotal());
					block.add(r);
					//学生做题信息细明插入
					for(int j = 0,k = 1;j <= stuInfoList.size();j++){
						r1 = new ArrayList<String>();
						AWStuInfo stuInfo = null;
						if(j == 0){
							r1.add("");
							r1.add("序号");
							r1.add("学号");
							r1.add("姓名");
							r1.add("是否通过");
							r1.add("得分");
							r1.add("编译错误次数");
							r1.add("编译通过通过");
							r1.add("运行正确次数");
							r1.add("运行错误次数");
							r1.add("首次通过时间");
							r1.add("最后提交时间");
							r1.add("允许开始时间");
							r1.add("要求完成时间");
							r1.add("题目说明");
						}else if(stuInfoList.get(j-1).getLastSave() == null){
							continue;
						}else{
							stuInfo = stuInfoList.get(j-1);
							if(!stuIdSet.contains(stuInfo.getStuId())) continue;
							Integer temp;
							Date t;
							r1.add("");
							r1.add(k+"");
							r1.add(stuInfo.getStuNo());
							r1.add(stuInfo.getStuName());
							r1.add(stuInfo.getRunPass()>0?"是":"否");
							r1.add(stuInfo.getScore()+"");
							r1.add(stuInfo.getCmpError()+"");
							r1.add(stuInfo.getCmpPass()+"");
							r1.add(stuInfo.getRunPass()+"");
							r1.add(stuInfo.getRunError()+"");
							r1.add(stuInfo.getFirstPass()+"");
							r1.add(stuInfo.getLastSave()+"");
							r1.add(stuInfo.getStartTime()+"");
							r1.add(stuInfo.getDeadline()+"");
							r1.add(stuInfo.getMemo());
							k++;
						}
						block.add(r1);
					}
					r = new ArrayList<String>();
					rowIndex++;
				}
				block.add(r);
			}
		}else{//按学生显示
			
			rowIndex = 1;
			for(int i = 0;i <= stuList.size()+2;i++){
				r = new ArrayList<String>();
				AWStuInfo info = null;
				if(i == 1){
					r.add("学生具体做题信息");
				}else if(i == 2){
					r.add("学生序号");
					r.add("学号");
					r.add("姓名");
					r.add("完成题数");
				}else if(i > 2){//数据
					info = stuList.get(i-3);
					if(!stuIdSet.contains(info.getStuId())) continue;
					List<AWPointInfo> pointInfoList = awDao.getPointDetail(info.getStuId(), VID);//获取某学生所有做题信息
					r.add(rowIndex+"");
					r.add(info.getStuNo());
					r.add(info.getStuName());
					r.add(info.getFinish()+"/"+info.getTotal());
					block.add(r);
					//某学生做题信息细明插入
					for(int j = 0,k = 1;j <= pointInfoList.size();j++){
						r1 = new ArrayList<String>();
						AWPointInfo pointInfo = null;
						if(j == 0){
							r1.add("");
							r1.add("序号");
							r1.add("章名");
							r1.add("题目");
							r1.add("是否通过");
							r1.add("得分");
							r1.add("编译错误次数");
							r1.add("编译通过通过");
							r1.add("运行正确次数");
							r1.add("运行错误次数");
							r1.add("首次通过时间");
							r1.add("最后提交时间");
							r1.add("允许开始时间");
							r1.add("要求完成时间");
							r1.add("题目说明");
						}else if(pointInfoList.get(j-1).getLastSave() == null){
							continue;
						}else{
							pointInfo = pointInfoList.get(j-1);
							if(!proIdSet.contains(pointInfo.getProblemId())) continue;
							Integer temp;
							Date t;
							r1.add("");
							r1.add(k+"");
							r1.add(pointInfo.getChapName());
							r1.add(pointInfo.getProName());
							r1.add(pointInfo.getRunPass()>0?"是":"否");
							r1.add(pointInfo.getScore()+"");
							r1.add(pointInfo.getCmpError()+"");
							r1.add(pointInfo.getCmpPass()+"");
							r1.add(pointInfo.getRunPass()+"");
							r1.add(pointInfo.getRunError()+"");
							r1.add(pointInfo.getFirstPass()+"");
							r1.add(pointInfo.getLastSave()+"");
							r1.add(pointInfo.getStartTime()+"");
							r1.add(pointInfo.getDeadline()+"");
							r1.add(pointInfo.getMemo());
							k++;
						}
						block.add(r1);
					}
					r = new ArrayList<String>();
					rowIndex++;
				}
				block.add(r);
			}
		}
		
		rowIndex = excel.insertBlock(sheet, (short)0, block);
		excel.createOutputStream();
	}

}
