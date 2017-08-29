/**   
* @Title: AnalyWork.java
* @Package com.stusys.dao.anyview
* @Description: TODO
* @author xhn 
* @date 2012-11-8 下午07:52:51
* @version V1.0   
*/
package com.anyview.dao;

import java.util.List;

//import com.stusys.entities.anyview.ExerciseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.AWPointInfo;
import com.anyview.entities.AWStuInfo;
import com.anyview.entities.SchemeT;

import com.anyview.entities.CustomExerciseForCTable;
import com.anyview.entities.ExerciseTable;

/**
 * @ClassName: AnalyWork
 * @Description: 批改作业
 * @author xhn
 * @date 2012-11-8 下午07:52:51
 * 
 */
public interface AnalyWorkDAO {
	/**
	 * 
	 * @Title: saveOrUpdateObject 
	 * @Description: 保存或更新对象
	 * @param @param e
	 * @param @return 
	 * @return boolean 
	 * @throws
	 */
	public boolean saveOrUpdateObject(ExerciseTable e);
	
	/**
	 * 
	 * @Title: saveOrUpdateObjectForC 
	 * @Description: 保存或更新对象
	 * @param @param e
	 * @param @return 
	 * @return boolean 
	 * @throws
	 */
	public boolean saveOrUpdateObjectForC(CustomExerciseForCTable e);
	/**
	 * 
	 * @Title: selectById 
	 * @Description: 根据Id查找对应习题
	 * @param @param id
	 * @param @return 
	 * @return ExerciseTable 
	 * @throws
	 */
	public ExerciseTable selectById(int id);
	/**
	 * 
	 * @Title: selectCById 
	 * @Description: 根据Id查找对应习题
	 * @param @param id
	 * @param @return 
	 * @return CustomExerciseForCTable 
	 * @throws
	 */
	public CustomExerciseForCTable selectCById(int id);
	/**
	 * 
	* @Title: getClassesByTeaId
	* @Description: 根据教师登录ID搜索班级列表
	* @param id
	* @return
	* @return List<ClassTable>
	 */
	public List<ClassTable> getClassesByTeaId(int id,int courseId);
	/**
	 * 
	* @Title: getScheme
	* @Description: 根据教师ID、班级id、课程id搜所有作业表
	* @param tid
	* @param cid
	* @param courseid
	* @return List<SchemeT>
	 */
	public List<SchemeT> getScheme(int tid,int cid,int courseid);
	
	/**
	 * 
	* @Title: getTempDataGridSortByExe
	* @Description: 根据班级id,作业表id搜按习题显示小窗口预加载数据
	* @param cid
	* @param vid
	* @return List<AWPointInfo>
	 */
	public List<AWPointInfo> getTempDataGridSortByExe(int cid,int vid);
	
	/**
	 * 
	* @Title: getTempDataGridSortByStu
	* @Description: 根据班级id,作业表id搜按学生显示小窗口预加载数据
	* @param cid
	* @param vid
	* @return List<AWStuInfo>
	 */
	public List<AWStuInfo> getTempDataGridSortByStu(int cid,int vid);
	
	/**
	 * 
	* @Title: getStuDetail
	* @Description: 根据班级ID，作业表ID，题目ID查询对应班级所有学生做题情况
	* @param cid
	* @param vid
	* @param pid
	* @return List<AWStuInfo>
	 */
	public List<AWStuInfo> getStuDetail(int cid,int vid,int pid);
	
	/**
	 * 
	* @Title: getPointDetail
	* @Description: 根据学生ID、作业表ID查找对应某学生所有做题情况
	* @param sid
	* @param vid
	* @return List<AWPointInfo>
	 */
	public List<AWPointInfo> getPointDetail(int sid,int vid);
}
