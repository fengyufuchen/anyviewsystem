/**   
* @Title: CommunionAction.java 
* @Package com.anyview.action.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年10月15日 上午10:02:25 
* @version V1.0   
*/
package com.anyview.action.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.entities.ChinaUniversityTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.ProvinceTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.AdminClassManager;
import com.anyview.service.function.CollegeManager;
import com.anyview.service.function.CourseManager;
import com.anyview.service.function.ExerciseManager;
import com.anyview.service.function.SimilarityDetection;
import com.anyview.service.function.TeacherManager;
import com.anyview.service.function.UniversityManager;
import com.anyview.util.dwz.ResponseUtils;
import com.anyview.utils.TipException;
import com.anyview.utils.XmlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: CommunionAction
 * @Description: TODO(共享的Action,负责教师和管理员共同的操作)
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年10月15日 上午10:02:25
 * 
 */
@SuppressWarnings("serial")
@Namespace("/communion")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CommunionAction extends CommonAction {
	private static final Log log = LogFactory.getLog(CommunionAction.class);
	private String jsonStr;
	@Autowired
	private CollegeManager collegeManager;
	@Autowired
	private UniversityManager universityManager;
	@Autowired
	private AdminClassManager adminclassManager;
	@Autowired
	private TeacherManager teacherManager;
	@Autowired
	private SimilarityDetection similarityDetection;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private ExerciseManager exerciseManager;
//	@Autowired
	private XmlUtil xmlutil;

	private File tempFile;
	private String tempFileContentType;
	private String tempFileFileName;

	@Action(value = "gainCollegeByUnIdAjax")
	public void gainCollegeByUnIdAjax() throws IOException {
		Integer unId = Integer.valueOf(request.getParameter("unId"));
		List<Object[]> ces = collegeManager.getEnabledCollegesByUnId(unId);
		jsonStr = ResponseUtils.getIdAndTextJson(ces);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainUniversityBySearchAjax")
	public void gainUniversityBySearchAjax() throws IOException {
		String param = request.getParameter("q").trim();
		List<UniversityTable> uns = universityManager.searchUniversityByName(param);
		List<Object[]> objs = new ArrayList<Object[]>();
		for (UniversityTable u : uns) {
			Object[] o = new Object[2];
			o[0] = u.getUnID();
			o[1] = u.getUnName();
			objs.add(o);
		}
		jsonStr = ResponseUtils.getIdAndTextJson(objs);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	@Action(value = "gainChinaUniversityBySearchAjax")
	public void gainChinaUniversityBySearchAjax() throws IOException {
		xmlutil = new XmlUtil();
		List<ChinaUniversityTable> uns = xmlutil.getChinaUniversityTable();
		List<Object[]> objs = new ArrayList<Object[]>();
		for (ChinaUniversityTable u : uns) {
			Object[] o = new Object[2];
			o[0] = u.getSchool_id();
			o[1] = u.getSchool_name();
			objs.add(o);
		}
		jsonStr = ResponseUtils.getIdAndTextJson(objs);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainCollegeByUnIdsAjax")
	public void gainCollegeByUnIdsAjax() throws IOException {
		String ids = request.getParameter("unIds");
		List<Object[]> objs = new ArrayList<Object[]>();
		if (ids.length() > 0) {
			
			// 转换类型，检查参数是否为整形
			Integer[] idsArr = ResponseUtils.transformIntegerArr(ids.split(","));
			List<CollegeTable> ces = collegeManager.getCollegesByUnIds(idsArr);
			for (CollegeTable c : ces) {
				Object[] o = new Object[4];
				o[0] = c.getUniversity().getUnID();
				o[1] = c.getUniversity().getUnName();
				o[2] = c.getCeID();
				o[3] = c.getCeName();
				objs.add(o);
			}
		}
		jsonStr = ResponseUtils.getOptGroupIdAndText(objs);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainTeacherByCeIdsAjax")
	public void gainTeacherByCeIdsAjax() throws IOException {
		String ids = request.getParameter("ceIds");
		List<Object[]> objs = new ArrayList<Object[]>();
		if (ids.length() > 0) {
			Integer[] idsArr = ResponseUtils.transformIntegerArr(ids.split(","));
			objs = teacherManager.getEnabledTeachersByCeIds(idsArr);
		}
		jsonStr = ResponseUtils.getOptGroupIdAndText(objs);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainClassByCeIdAjax")
	public void gainClassByCeIdAjax() throws IOException {
		Integer ceId = Integer.valueOf(request.getParameter("ceId"));
		List<Object[]> cla = adminclassManager.getEnabledClassByCeId(ceId);
		jsonStr = ResponseUtils.getIdAndTextJson(cla);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainCourseByTidAndClaidAjax")
	public void gainCourseByTidAndClaidAjax() throws IOException {
		TeacherTable teacher = (TeacherTable) session.get("User");
		Integer ClaId = Integer.valueOf(request.getParameter("ClaId"));
		List<Object[]> cour = similarityDetection.getCourseByTIdandClaId(teacher.getTid(), ClaId);
		jsonStr = ResponseUtils.getIdAndTextJson(cour);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainSchemeByClaIdAndCouIdAjax")
	public void gainSchemeByClaIdAndCouIdAjax() throws IOException {
		Integer ClaId = Integer.valueOf(request.getParameter("ClaId"));
		Integer CouId = Integer.valueOf(request.getParameter("CouId"));
		List<Object[]> cour = similarityDetection.getSchemeByClaIdandCouId(ClaId, CouId);
		jsonStr = ResponseUtils.getIdAndTextJson(cour);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "gainCourseByCeIdAjax")
	public void gainCourseByCeIdAjax() throws IOException {
		Integer ceId = Integer.valueOf(request.getParameter("ceId"));
		List<Object[]> cs = null;
		try {
			cs = courseManager.getCourseINByCeId(ceId);
		} catch (TipException e) {
			cs = new ArrayList<Object[]>();
		}
		jsonStr = ResponseUtils.getIdAndTextJson(cs);
		ResponseUtils.outJson(response, jsonStr);
	}

	@Action(value = "parseFileAjax")
	public void parseFileAjax() throws IOException {
		String content = "";
		char[] tempchars = new char[100];
		// String name = tempFile.getName();
		int charread = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(tempFile));
			while ((charread = reader.read(tempchars)) != -1) {
				content += tempchars.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		jsonStr = "{name:" + tempFileFileName + ",content:" + content + "}";
		ResponseUtils.outJson(response, jsonStr);

	}

	/**
	 * 获取某个计划表，所有学生的所有题目的分值，得分和通过情况
	 * @author 杨坚新
	 * @throws IOException
	 */
	@Action(value = "gainExerciseAjax")
	public void gainExerciseByCeIdAjax() throws IOException {
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		String[] sidArr = request.getParameter("sid").split(",");
		// 每个学生，每道题的分值，教师批改分值，通过状态
		for(String sid : sidArr){
			System.out.println(sid);
		}
		JSONObject result=new JSONObject();//最终的JSONArray
		result.accumulate("statusCode", "200");
		result.accumulate("message", "计算成功");
		result.accumulate("navTabId", "");
		result.accumulate("rel", "");
		result.accumulate("callbackType", "closeCurrent");
		result.accumulate("forwardUrl", "");
		JSONArray allMsg=new JSONArray();	//存储一个学生的全部成绩信息
		for (String sid : sidArr) {
			
			//遍历一个学生的所有题目
			List<Object[]> list = exerciseManager.getExerciseScoreAjax(cid, vid, Integer.valueOf(sid));
			//JSONObject allScore=new JSONObject();
			JSONObject sidAndScore=new JSONObject();
			JSONArray scoreArr=new JSONArray();	//存储一个学生成绩信息，除了sid
			for(Object[] o : list){
				JSONObject scoreMsg=new JSONObject();	//存储一个学生成绩信息，除了sid
				scoreMsg.accumulate("score1", o[0]);	//教师对该题的给分
				scoreMsg.accumulate("runResult", o[1]);	//运行结果，是否通过
				scoreMsg.accumulate("score2", o[2]);	//习题的分值
				scoreArr.add(scoreMsg);
			}
			//allScore.accumulate("allScore", scoreArr);
			sidAndScore.accumulate("sid", sid);
			sidAndScore.accumulate("allScore", scoreArr);
			allMsg.add(sidAndScore);
		}
		result.accumulate("allMsg", allMsg);
		jsonStr=result.toString();
		System.out.println("json:"+jsonStr);
		ResponseUtils.outJson(response, jsonStr);
	}
	
	/**获取一个班级，一个计划表中，一个学生，每道题目的截止时间，通过时间，最分数
	 * @author 杨坚新
	 * @throws IOException 
	 * 
	 */
	@Action(value = "gainScoreFinishtimeFirstpasttime")
	public void gainScoreFinishtimeFirstpasttime() throws IOException{
		Integer cid = Integer.valueOf(request.getParameter("cid"));
		Integer vid = Integer.valueOf(request.getParameter("vid"));
		String[] sidArr = request.getParameter("sid").split(",");
		String max=request.getParameter("max");
		String min=request.getParameter("min");
		System.out.println("cid:"+cid);
		System.out.println("vid:"+vid);
		System.out.println("max:"+max);
		System.out.println("min:"+min);
		
		JSONObject result=new JSONObject();//最终的JSONArray
		result.accumulate("statusCode", "200");
		result.accumulate("message", "计算成功");
		result.accumulate("navTabId", "");
		result.accumulate("rel", "");
		result.accumulate("callbackType", "closeCurrent");
		result.accumulate("forwardUrl", "");
		result.accumulate("max", max);
		result.accumulate("min", min);
		JSONArray allMsg=new JSONArray();
		for(String sid : sidArr){
			List<Object[]> list1=exerciseManager.getFinishTimeAndScore(cid, vid, Integer.valueOf(sid));
			JSONArray scoreArr=new JSONArray();
			JSONObject student=new JSONObject();
			for(Object[] o:list1){
				List<Object[]> list2=exerciseManager.getMinScoreAndEarliestFinishTime((Integer)o[0], vid, Integer.valueOf(sid), cid);
				Object[] obj=list2.get(0);
				JSONObject score=	new JSONObject();
				score.accumulate("score", o[1]+"");
				score.accumulate("finishTime", o[2]+"");
				score.accumulate("firstPastTime", o[3]+"");
				score.accumulate("minScore", obj[1]+"");
				score.accumulate("earliestFinishTime", obj[0]+"");
				scoreArr.add(score);
			}
			student.accumulate("sid", sid);
			student.accumulate("scoreArr", scoreArr);
			allMsg.add(student);
		}
		result.accumulate("allMsg", allMsg);
		
		//存储jsonStr字符串
//		StringBuilder sb=new StringBuilder("{");
//		for(String sid : sidArr){
//			JSONArray arr = new JSONArray();
//			sb.append("\""+sid+"\""+":");
//			System.out.println("======开始遍历，学生sid："+sid);
//			List<Object[]> list1=exerciseManager.getFinishTimeAndScore(cid, vid, Integer.valueOf(sid));
//			System.out.println(list1.size());
//			for(Object[] o:list1){
//				JSONObject json = new JSONObject();
//				List<Object[]> list2=exerciseManager.getMinScoreAndEarliestFinishTime((Integer)o[0], vid, Integer.valueOf(sid), cid);
//				Object[] obj=list2.get(0);
//				json.accumulate("score", o[1]+"");
//				json.accumulate("finishTime", o[2]+"");
//				json.accumulate("firstPastTime", o[3]+"");
//				json.accumulate("minScore", obj[1]+"");
//				json.accumulate("earliestFinishTime", obj[0]+"");
//				arr.add(json);
//			}
//			sb.append(arr.toString()+",");
//		}
//		sb.replace(sb.length()-1, sb.length(), "");
//		sb.append("}");
//		jsonStr=sb.toString();
		jsonStr=result.toString();
		System.out.println(result.toString());
		ResponseUtils.outJson(response, jsonStr);
	}
	
	/**直接返回操作成功的json,空操作
	 * @author 杨坚新
	 * @throws IOException
	 */
	@Action(value = "submitSuccess")
	public void submitSuccess() throws IOException{
		JSONObject result=new JSONObject();//最终的JSONArray
		result.accumulate("statusCode", "200");
		result.accumulate("message", "计算成功");
		result.accumulate("navTabId", "");
		result.accumulate("rel", "");
		result.accumulate("callbackType", "closeCurrent");
		result.accumulate("forwardUrl", "");
		response.getWriter().write(result.toString());
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public File getTempFile() {
		return tempFile;
	}

	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	public String getTempFileContentType() {
		return tempFileContentType;
	}

	public void setTempFileContentType(String tempFileContentType) {
		this.tempFileContentType = tempFileContentType;
	}

	public String getTempFileFileName() {
		return tempFileFileName;
	}

	public void setTempFileFileName(String tempFileFileName) {
		this.tempFileFileName = tempFileFileName;
	}
}
