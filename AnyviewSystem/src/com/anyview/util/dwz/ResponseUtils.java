package com.anyview.util.dwz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.TeacherTable;

public class ResponseUtils {
	
	public static void outJson(HttpServletResponse response, String jsonStr) throws IOException{
//		response.setCharacterEncoding("utf-8");
		//若不设置返回类型，前台转化json对象会出错
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.close();
	}
	
	public static String getCollegeIdAndNameJson(Collection<CollegeTable> coles){
		if(coles==null || coles.size()==0)
			return "[]";
		String jsonArray = "[";
		for(CollegeTable c : coles){
			jsonArray += "{\"ceID\":\""+c.getCeID()+"\",\"ceName\":\""+c.getCeName()+"\"},";
		}
		jsonArray = jsonArray.substring(0, jsonArray.length()-1);
		return jsonArray+"]";
	}
	
	public static String getTeacherIdAndNameJson(Collection<TeacherTable> teas){
		if(teas == null || teas.size() == 0)
			return "[]";
		String jsonArray = "[";
		for(TeacherTable t : teas){
			jsonArray += "{\"tid\":\""+t.getTid()+"\",\"tname\":\""+t.getTname()+"\"},";
		}
		jsonArray = jsonArray.substring(0, jsonArray.length()-1);
		return jsonArray+"]";
	}
	public static String getClassIdAndNameJson(Collection<ClassTable> clas){
		if(clas==null || clas.size()==0)
			return "[]";
		String jsonArray = "[";
		for(ClassTable c : clas){
			jsonArray += "{\"cID\":\""+c.getCid()+"\",\"cName\":\""+c.getCname()+"\"},";
		}
		jsonArray = jsonArray.substring(0, jsonArray.length()-1);
		return jsonArray+"]";
	}
	
	/**
	 * 参数为Object[]的list;[0]为id,[1]为name
	 * @return id+name的json数组
	 */
	public static String getIdAndNameJson(List<Object[]> objs){
		if(objs == null || objs.size() == 0)
			return "[]";
		String jsonArray = "[";
		for(Object[] os:objs){
			jsonArray += "{\"id\":\""+os[0]+"\",\"name\":\""+os[1]+"\"},";
		}
		jsonArray = jsonArray.substring(0, jsonArray.length()-1);
		return jsonArray+"]";
	}
	
	/**
	 * 
	 * @Description: TODO(获取dwz的select级联所需格式) 
	 * @param objs Object[]的list;[0]为id,[1]为name
	 * @return 返回格式如下
	 * [
	 *     ["id1", "name1"],
	 *     ["id2", "name2"]
	 * ]
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年11月11日 上午2:20:53
	 */
	public static String getIdAndNameDwzComboxJson(List<Object[]> objs){
		if(objs == null || objs.size() == 0)
			return "[]";
		String jsonStr = "[";
		for(Object[] os:objs){
			jsonStr += "[\""+os[0]+"\",\""+os[1]+"\"],";
		}
		jsonStr = jsonStr.substring(0, jsonStr.length()-1);
		return jsonStr+"]";
	}
	
	/**
	 * 返回题库id,name字符串，isParen=true
	 * @Description: TODO() 
	 * @param objs
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年8月31日 上午10:19:21
	 */
	public static String getIdAndNameParentJson(List<Object[]> objs){
		JSONArray arr = new JSONArray();
		for(int i=0;i<objs.size();i++){
			JSONObject json = new JSONObject();
			json.accumulate("id", -1);
			json.accumulate("lid", objs.get(i)[0]);
			json.accumulate("name", objs.get(i)[1]);
			json.accumulate("isParent", true);
			arr.add(json);
		}
		return arr.toString();
	}
	/**
	 * 
	 * @Description: TODO(获取题目基本信息的json) 
	 * @param pros
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月1日 下午12:45:48
	 */
	public static String getProblemBaseJson(List<ProblemTable> pros){
		JSONArray arr = new JSONArray();
		for(ProblemTable p:pros){
			JSONObject json = new JSONObject();
			json.accumulate("pid", p.getPid());
			json.accumulate("pname", p.getPname());
			json.accumulate("lib", p.getProblemChap().getProblemLib().getLname());
			String proDir="";
			ProblemChapTable chap = p.getProblemChap();
			while(chap.getChId()!=-1){
				proDir+=chap.getChName()+"/";
				chap = chap.getParentChap();
			}
			json.accumulate("pdir", proDir);
			String kindStr="";
			switch(p.getKind()){
			case 0:kindStr="程序题";break;
			case 1:kindStr="例题";break;
			case 2:kindStr="填空题";break;
			case 3:kindStr="单项选择题";break;
			case 4:kindStr="多项选择题";break;
			case 5:kindStr="判断题";break;
			}
			json.accumulate("kind", kindStr);
			json.accumulate("visit", p.getVisit()==0?"私有":"公开");
			json.accumulate("degree", p.getDegree());
			String statusStr="";
			switch(p.getStatus()){
			case 0:statusStr="停用";break;
			case 1:statusStr="测试";break;
			case 2:statusStr="正式";break;
			}
			json.accumulate("status", statusStr);
			arr.add(json);
		}
		return arr.toString();
	}
	
	public static String getSchemeContentJson(List<SchemeContentTable> scList){
		JSONArray arr = new JSONArray();
		for(SchemeContentTable sc:scList){
			JSONObject json = new JSONObject();
			json.accumulate("pid", sc.getProblem().getPid());
			json.accumulate("pname", sc.getProblem().getPname());
			json.accumulate("lib", sc.getProblem().getProblemChap().getProblemLib().getLname());
			String proDir="";
			ProblemChapTable chap = sc.getProblem().getProblemChap();
			while(chap.getChId()!=-1){
				proDir+=chap.getChName()+"/";
				chap = chap.getParentChap();
			}
			json.accumulate("pdir", proDir);
			String kindStr="";
			switch(sc.getProblem().getKind()){
			case 0:kindStr="程序题";break;
			case 1:kindStr="例题";break;
			case 2:kindStr="填空题";break;
			case 3:kindStr="单项选择题";break;
			case 4:kindStr="多项选择题";break;
			case 5:kindStr="判断题";break;
			}
			json.accumulate("kind", kindStr);
			json.accumulate("visit", sc.getProblem().getVisit()==0?"私有":"公开");
			json.accumulate("degree", sc.getProblem().getDegree());
			String statusStr="";
			switch(sc.getProblem().getStatus()){
			case 0:statusStr="停用";break;
			case 1:statusStr="测试";break;
			case 2:statusStr="正式";break;
			}
			json.accumulate("status", statusStr);
			json.accumulate("score", sc.getScore());
			json.accumulate("vpName", sc.getVpName());
			json.accumulate("vchapName", sc.getVchapName());
			json.accumulate("startTime", sc.getStartTime().toString());
			json.accumulate("finishTime", sc.getFinishTime().toString());
			arr.add(json);
		}
		return arr.toString();
	}
	/**
	 * 
	 * @Description: TODO(获取id='',text=''格式的json数组，select2使用) 
	 * @param objs
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月13日 下午7:16:41
	 */
	public static String getIdAndTextJson(List<Object[]> objs){
		JSONArray arr = new JSONArray();
		for(Object[] o : objs){
			JSONObject json = new JSONObject();
			json.accumulate("id", o[0]);
			json.accumulate("text", o[1]);
			arr.add(json);
		}
		return arr.toString();
	}
	/**
	 * 
	 * @Description: TODO(将String[]转换为Integer[]) 
	 * @param strArr
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 上午1:45:33
	 */
	public static Integer [] transformIntegerArr(String []strArr){
		Integer[] idsArr = new Integer[strArr.length];
		for(int i=0;i<strArr.length;i++){
			idsArr[i] = Integer.valueOf(strArr[i]);
		}
		return idsArr;
	}
	/**
	 * 通用
	 * @Description: TODO(获得带optgroup的json,select2通用) 
	 * @param objs
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2015年9月15日 上午1:53:22
	 */
	public static String getOptGroupIdAndText(List<Object[]> objs){
		JSONArray arr = new JSONArray();
		for(Object[] o : objs){
			JSONObject json = new JSONObject();
			json.accumulate("gid", o[0]);
			json.accumulate("gtext", o[1]);
			json.accumulate("id", o[2]);
			json.accumulate("text", o[3]);
			arr.add(json);
		}
		return arr.toString();
	}
	
	/**返回对应学生的所有题目的分数，分值和运行结果的json
	 * @author 杨坚新
	 * @param objs
	 * @return
	 */
	public static String getScoreAndRunResultJson(List<Object[]> objs){
		JSONArray arr = new JSONArray();
		for(Object[] o : objs){
			JSONObject json = new JSONObject();
			//分数
			json.accumulate("score1", o[0]);
			//运行结果
			json.accumulate("runResult", o[1]);
			//分值
			json.accumulate("score2", o[2]);
			arr.add(json);
		}
		return arr.toString();
	}
}
