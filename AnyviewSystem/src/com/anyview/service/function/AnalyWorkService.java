/**   
* @Title: AnalyWorkService.java
* @Package com.stusys.service.anyview
* @Description: TODO
* @author xhn 
* @date 2012-11-8 下午09:29:12
* @version V1.0   
*/
package com.anyview.service.function;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.anyview.entities.ClassTable;
import com.anyview.entities.AWPointInfo;
import com.anyview.entities.AWStuInfo;
import com.anyview.entities.SchemeT;

/**
 * @ClassName: AnalyWorkService
 * @Description: 批改作业接口
 * @author xhn
 * @date 2012-11-8 下午09:29:12
 * 
 */
public interface AnalyWorkService {

	/**
	 * 
	* @Title: getClassListById
	* @Description: 根据Anyview教师帐号获取班级课程信息
	* @param id
	* @return
	* @return List<AWClass>
	 */
	public List<ClassTable> getClassListById(int id,int courseId);
	/**
	 * 
	* @Title: getScheme
	* @Description: 根据班级id,课程id,教师id搜作业表
	* @param cid
	* @param courseId
	* @param tid
	* @return List<SchemeT>
	 */
	public List<SchemeT> getScheme(int cid, int courseId,int tid); 
	
	/**
	 * 
	* @Title: getDataGrid
	* @Description: 通过班级ID，作业表ID搜表格预加载内容
	* @param classId
	* @param schemeId
	* @return Map<String,Object> 'scheme'为按习题显示的信息，'stu'为按学生显示的信息
	 */
	public Map<String,Object> getTempDataGrid(int classId,int schemeId);
	
	/**
	 * 
	* @Title: getStuDetail
	* @Description: 根据班级id,作业表id,题目id搜对应班级所有学生做题情况
	* @param classId
	* @param schemeId
	* @param problemId
	* @return List<AWStuInfo>
	 */
	public List<AWStuInfo> getStuDetail(int classId,int schemeId,int problemId);
	
	/**
	 * 
	* @Title: getPointDetail
	* @Description: 根据作业表Id，学生ID查询对应学生的所有做题情况
	* @param schemeId
	* @param stuId
	* @return List<AWPointInfo>
	 */
	public List<AWPointInfo> getPointDetail(int schemeId,int stuId);
	
	/**
	 * 
	 * @Title: updateExercise 
	 * @Description: 更新习题分数和评论
	 * @param exId
	 * @param comment
	 * @param score
	 * @return boolean 
	 * @throws
	 */
	public boolean updateExercise(int exId,String comment,float score);
	
	/**
	 * 
	 * @Title: createFile 
	 * @Description: 根据sortType(1:按习题|2:按学生)生成不同excel文件，存放相对路径path
	 * @param path
	 * @param sortType
	 * @param CID
	 * @param VID
	 * @param proIdSet
	 * @param stuIdSet 
	 * @return void 
	 * @throws
	 */
	public void createFile(String path,int sortType,int CID,int VID,
			Set<Integer> proIdSet,Set<Integer> stuIdSet);
}
