package com.anyview.service.function.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassTeacherCourseDao;
import com.anyview.dao.impl.ClassCourseSchemeDaoImpl;
import com.anyview.dao.impl.ClassTeacherCourseDaoImpl;
import com.anyview.dao.impl.ClassTeacherDaoImpl;
import com.anyview.dao.impl.ExerciseDaoImpl;
import com.anyview.dao.impl.SchemeDaoImpl;
import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.ClassTeacherCourseTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.ExerciseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.StudentTable;
import com.anyview.service.function.SimilarityDetection;

@Service
public class SimilarityDetectionImpl implements SimilarityDetection,Runnable{
	
	@Autowired
	private ClassTeacherCourseDaoImpl classTeacherCourseDaoImpl;
	@Autowired
	private ClassCourseSchemeDaoImpl classCourseSchemeDaoImpl;
	@Autowired
	private ClassTeacherDaoImpl classTeacherDaoImpl;
	@Autowired
	private SchemeDaoImpl schemeDaoImpl;
	@Autowired
	private ExerciseDaoImpl exerciseDaoImpl;
	
	private Integer cid;
	private String[] ids;
	private String path;
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程id) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午4:19:52
	 */
	@Override
	public  List<Object[]> getCourseByTIdandClaId(Integer tid, Integer claid){
		return classTeacherCourseDaoImpl.getCourseByTIdandClaId(tid, claid);
	}
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午5:03:21
	 */
	@Override
	public  List<Object[]> getSchemeByClaIdandCouId(Integer claid, Integer couid){
		return classCourseSchemeDaoImpl.getSchemeByClaIdandCouId(claid, couid);
	}
	
	/**
	 * 
	 * @Description: TODO(根据教师id获取班级) 
	 * @param tid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月20日 下午7:33:28
	 */
	@Override
	public  List<ClassTeacherTable> getClassByTId(Integer tid){
		return classTeacherDaoImpl.getClassByTId(tid);
	}
	
	/**
	 * 
	 * @Description: TODO(根据tid和claid获取课程) 
	 * @param tid
	 * @param claid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午4:34:11
	 */
	@Override
	public List<ClassTeacherCourseTable> getCourseByTIdAndClaId(Integer tid, Integer claid){
		return classTeacherCourseDaoImpl.getCourseByTIdAndClaId(tid, claid);
	}
	
	/**
	 * 
	 * @Description: TODO(根据claid和couid获取作业表) 
	 * @param claid
	 * @param couid
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午4:36:52
	 */
	@Override
	public  List<ClassCourseSchemeTable> getSchemeByClaIdAndCouId(Integer claid, Integer couid){
		return classCourseSchemeDaoImpl.getSchemeByClaIdAndCouId(claid, couid);
	}
	
	/**
	 * 
	 * @Description: TODO() 
	 * @param param
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月21日 下午7:34:06
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pagination<SchemeContentTable> getSchemeContentPage(Map param) {
		Pagination<SchemeContentTable> page = new Pagination<SchemeContentTable>();
		List<SchemeContentTable> sst = schemeDaoImpl.getSchemeContentPage(param);
		page.setContent(sst);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(schemeDaoImpl.getListSchemeContentCount(param));
		page.calcutePage();
		return page;
	}
	
	/**
	 * 
	 * @Description: TODO(下载学生作业答案) 
	 * @param cid
	 * @param ids
	 * @return
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年1月23日 下午8:17:30
	 */
	public boolean downloadAnswer(Integer cid, String[] ids, String path){
		
		this.cid = cid;
		this.ids = ids;
		this.path = path;
		
		run();

//		for(int i = 0; i < ids.length; i++)
//		{
//			int intids = Integer.valueOf(ids[i]);
//			List<ExerciseTable> et = exerciseDaoImpl.getExerciseAnswer(cid, intids);
//			if(et.isEmpty() != true)
//			{
//				List<SchemeContentTable> sc = schemeDaoImpl.getSchemeContentList(et.get(0).getScheme().getVid(), et.get(0).getProblem().getPid());
//				String FolderPath = path+"/"+ et.get(0).getScheme().getVname() + "_" + sc.get(0).getVchapName() + "_" + sc.get(0).getVpName();
//				File folder = new File(FolderPath);
//				folder.mkdirs();
//				for(int j = 0; j < et.size(); j++)
//				{
//					File file = new File(FolderPath +"/" + et.get(j).getStudent().getSno() + "_" + et.get(j).getStudent().getSname() + ".c");
//					try {
////						if(!file.exists())
////						{
////						    file.createNewFile();
////						}
////						FileWriter fw = new FileWriter(file.getAbsoluteFile());
////						BufferedWriter bw = new BufferedWriter(fw);
////						bw.write(et.get(j).getEcontent());
////						bw.close();
//						
//						FileOutputStream fos = new FileOutputStream(file);
//						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//						BufferedWriter bw = new BufferedWriter(osw);
//						
//						bw.write(et.get(j).getEcontent());
//								
//						bw.close();
//						osw.close();
//						fos.close();
//						
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		String zipFileName = path + ".zip"; //打包后文件名字
//		try {
//			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
//			File inputFileName = new File(path);
//			zip(out, inputFileName, "");
//			out.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//删除文件夹
//		File deleteFile = new File(path);
//		deleteAllFilesOfDir(deleteFile);  
		return true;
	}
	
	/**
	 * 
	 * @Description: TODO(zip压缩) 
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年2月23日 下午8:50:03
	 */
	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory())
		{
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) 
			{
				zip(out, fl[i], base + fl[i].getName());
			}
		}
		else
		{
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ( (b = in.read()) != -1) 
			{
				out.write(b);
			}
			in.close();
		}
	}
	
	/**
	 * 
	 * @Description: TODO(删除文件夹) 
	 * @param path
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年2月23日 下午9:04:06
	 */
	private void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	}
	

	/**
	 * 
	 * @Description: TODO(多线程打包下载文件) 
	 * @author 方典禹 <846396179@qq.com>
	 * @date 2016年2月24日 上午1:08:37
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0; i < ids.length; i++)
		{
			int intids = Integer.valueOf(ids[i]);
			List<ExerciseTable> et = exerciseDaoImpl.getExerciseAnswer(cid, intids);
			if(et.isEmpty() != true)
			{
				List<SchemeContentTable> sc = schemeDaoImpl.getSchemeContentList(et.get(0).getScheme().getVid(), et.get(0).getProblem().getPid());
				String FolderPath = path+"/"+ et.get(0).getScheme().getVname() + "_" + sc.get(0).getVchapName() + "_" + sc.get(0).getVpName();
				File folder = new File(FolderPath);
				folder.mkdirs();
				for(int j = 0; j < et.size(); j++)
				{
					File file = new File(FolderPath +"/" + et.get(j).getStudent().getSno() + "_" + et.get(j).getStudent().getSname() + ".c");
					try {
//						if(!file.exists())
//						{
//						    file.createNewFile();
//						}
//						FileWriter fw = new FileWriter(file.getAbsoluteFile());
//						BufferedWriter bw = new BufferedWriter(fw);
//						bw.write(et.get(j).getEcontent());
//						bw.close();
						
						FileOutputStream fos = new FileOutputStream(file);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
						BufferedWriter bw = new BufferedWriter(osw);
						
						bw.write(et.get(j).getEcontent());
								
						bw.close();
						osw.close();
						fos.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		String zipFileName = path + ".zip"; //打包后文件名字
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			File inputFileName = new File(path);
			zip(out, inputFileName, "");
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//删除文件夹
		File deleteFile = new File(path);
		deleteAllFilesOfDir(deleteFile);  
	}
	
}
