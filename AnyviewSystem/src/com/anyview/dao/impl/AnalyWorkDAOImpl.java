/**   
* @Title: AnalyWorkDAOImpl.java
* @Package com.stusys.dao.anyview.impl
* @Description: TODO
* @author xhn 
* @date 2012-11-8 下午08:07:45
* @version V1.0   
*/
package com.anyview.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.AnalyWorkDAO;
//import com.stusys.entities.anyview.ExerciseTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.AWPointInfo;
import com.anyview.entities.AWStuInfo;
import com.anyview.entities.SchemeT;
import com.anyview.entities.CustomExerciseForCTable;
import com.anyview.entities.ExerciseTable;

/**
 * @ClassName: AnalyWorkDAOImpl
 * @Description: TODO
 * @author sjt
 * @date 2015-8-1 下午08:07:45
 * 
 */
@Repository
public class AnalyWorkDAOImpl extends BaseDaoImpl implements AnalyWorkDAO {
	
	@Override
	public ExerciseTable selectById(int id) {
		return (ExerciseTable)this.hibernateTemplate.get(ExerciseTable.class, id);
	}

	@Override
	public CustomExerciseForCTable selectCById(int id) {
		return (CustomExerciseForCTable)this.hibernateTemplate.get(CustomExerciseForCTable.class, id);
	}
	
	@Override
	public List<ClassTable> getClassesByTeaId(int id,int courseId) {
		final String sql = "select c.cid,c.cname "+ 
				  "from Class_Teacher_CourseTable c_t_c " +
				  ",ClassTable c  " +
				  "where c_t_c.tid= ? and c_t_c.cid= ?  and c_t_c.cid=c.cid";
		Query query = getSession().createSQLQuery(sql);
		query.setInteger(0, id);
		query.setInteger(1, courseId);
		List list=query.list();
		
        List<ClassTable> clas=new ArrayList<ClassTable>();
        for(int i=0;i<list.size();i++){
	       ClassTable c = new ClassTable();
	       c.setCid((Integer)((Object[])(list.get(i)))[0]);
	       c.setCname((String)((Object[])(list.get(i)))[1]);
	       clas.add(c);
        }
		return clas;
	}

	
	@Override
	public List<SchemeT> getScheme(int tid, int cid, int courseid) {
		final String sql = "select s.vid,s.vname "+ 
				" from SchemeTable s"+ 
				" where s.vid in"+ 
				"(select c_c_s.vid"+ 
				" from Class_Course_SchemeTable c_c_s"+
				" where c_c_s.cid = ? and c_c_s.courseid = ?)";
		Query query = getSession().createSQLQuery(sql);
		query.setInteger(0, cid);
		query.setInteger(1, courseid);
		List list=query.list();
	    List<SchemeT> clas=new ArrayList<SchemeT>();
	    for(int i=0;i<list.size();i++){
	    	SchemeT c = new SchemeT();
		    c.setVID((Integer)((Object[])(list.get(i)))[0]);
		    c.setVName((String)((Object[])(list.get(i)))[1]);
		    clas.add(c);
	    }
	    return clas;
	}

	@Override
	public List<AWPointInfo> getTempDataGridSortByExe(final int cid,final int vid) {
		/*final String sql = "select sc.VID schemeId,sc.PID problemId,info.ChName chapName,info.PName proName,info.PContent topic,sc.Score score,ex.finish,st.total"+
				" from SchemeContentTable sc,"+ 
				"(select count(*) total from StudentTable s where s.CID = :cid) st,"+
				"(select p.PID,pc.ChName,p.PName,p.PContent from"+ 
					" ProblemTable p,ProblemChapTable pc,SchemeContentTable sc"+ 
					" where p.ChID = pc.ChID and sc.PID = p.PID and sc.VID = :vid) info"+ 
					" left join("+ 
						"select _ex.PID,count(*) finish from ExerciseTable _ex"+ 
							" join StudentTable s"+ 
							" on s.SID = _ex.SID"+ 
						" where _ex.RunResult > 0 and s.CID = :cid and _ex.VID = :vid group by _ex.PID) ex"+ 
					" on ex.PID = info.PID"+ 
				" where info.PID = sc.PID and VID = :vid"+
				" order by ChName,PName";*/
		/*final String sql = "select sc.VID ,sc.PID ,info.ChName ,info.PName,sc.Score,cast(info.PContent as CHAR)"+
				" from SchemeContentTable sc,"+ 
				"(select count(*) from StudentTable s where s.CID = ?) st,"+
				"(select p.PID,pc.ChName,p.PName,p.PContent from"+ 
					" ProblemTable p,ProblemChapTable pc,SchemeContentTable sc"+ 
					" where p.ChID = pc.ChID and sc.PID = p.PID and sc.VID = ?) info"+ 
					" left join("+ 
						"select _ex.PID,count(*) finish from ExerciseTable _ex"+ 
							" join StudentTable s"+ 
							" on s.SID = _ex.SID"+ 
						" where _ex.RunResult > 0 and s.CID = ? and _ex.VID = ? group by _ex.PID) ex"+ 
					" on ex.PID = info.PID"+ 
				" where info.PID = sc.PID and VID = ?"+
				" order by ChName,PName";*/
		final String sql = "select sc.VID schemeId,sc.PID problemId,info.ChName chapName,info.PName proName,info.PContent topic,sc.Score score,ex.finish,st.total"+
				" from SchemeContentTable sc,"+ 
				"(select count(*) total from StudentTable s where s.CID = :cid) st,"+
				"(select p.PID,pc.ChName,p.PName,p.PContent from"+ 
					" ProblemTable p,ProblemChapTable pc,SchemeContentTable sc"+ 
					" where p.ChID = pc.ChID and sc.PID = p.PID and sc.VID = :vid) info"+ 
					" left join("+ 
						"select _ex.PID,count(*) finish from ExerciseTable _ex"+ 
							" join StudentTable s"+ 
							" on s.SID = _ex.SID"+ 
						" where _ex.RunResult > 0 and s.CID = :cid and _ex.VID = :vid group by _ex.PID) ex"+ 
					" on ex.PID = info.PID"+ 
				" where info.PID = sc.PID and VID = :vid"+
				" order by ChName,PName";
		return getHibernateTemplate().executeFind(
				new  HibernateCallback() {
		           public Object doInHibernate(Session session)
		             throws HibernateException, SQLException {
		        	   return session.createSQLQuery(sql)
		        	   	.addScalar("schemeId", Hibernate.INTEGER)
		        	   	.addScalar("problemId", Hibernate.INTEGER)
		        	   	.addScalar("chapName", Hibernate.STRING)
		        	   	.addScalar("proName", Hibernate.STRING)
		        	   	.addScalar("topic", Hibernate.STRING)
		        	   	.addScalar("score", Hibernate.FLOAT)
		        		.addScalar("finish", Hibernate.INTEGER)
		        	   	.addScalar("total", Hibernate.INTEGER)
		        	   	.setInteger("cid", cid).setInteger("vid", vid)
		            	.setResultTransformer(Transformers.aliasToBean(AWPointInfo.class))
		            	.list();
		           }
		          });
		/*Query query = getSession().createSQLQuery(sql);
		query.setInteger(0, cid);
		query.setInteger(1, vid);
		query.setInteger(2, cid);
		query.setInteger(3, vid);
		query.setInteger(4, vid);
		List list=query.list();
	    List<AWPointInfo> clas=new ArrayList<AWPointInfo>();
	    for(int i=0;i<list.size();i++){
	    	AWPointInfo c = new AWPointInfo();
		    c.setSchemeId((Integer)((Object[])(list.get(i)))[0]);
		    c.setProblemId((Integer)((Object[])(list.get(i)))[1]);
		    c.setChapName((String)((Object[])(list.get(i)))[2]);
		    c.setProName((String)((Object[])(list.get(i)))[3]);
		    c.setScore((float)((Object[])(list.get(i)))[4]);
		    c.setTopic((String)((Object[])(list.get(i)))[5]);
		    //c.setProName((String)((Object[])(list.get(i)))[3]);
		   // c.setFinish((Integer)((Object[])(list.get(i)))[3]);
		   // c.setTotal((Integer)((Object[])(list.get(i)))[4]);
		    
		    //c.setTopic((String)((Object[])(list.get(i)))[1]);
		    clas.add(c);
	    }
	    return clas;*/
		
	}

	@Override
	public List<AWStuInfo> getTempDataGridSortByStu(final int cid, final int vid) {
		/*final String sql ="select s.SID ,s.SNO ,s.SName"+ 
				" from StudentTable s"+ 
				" join"+ 
				"(select count(*) total from ProblemTable pt where pt.pid"+
				" in(select pid from SchemeContentTable where vid = ?)) sc"+ 
				" on 1=1"+ 
				" left join"+ 
				"(select _e.SID,count(*) finish"+ 
				" from ExerciseTable _e,StudentTable _s"+ 
				" where _s.CID = ? and _e.VID = ? and _e.SID = _s.SID and _e.RunResult > 0"+ 
				" group by _e.SID) e"+ 
				" on e.SID = s.SID"+ 
				" where s.CID = ? order by s.SNO";
		Query query = getSession().createSQLQuery(sql);
		query.setInteger(0, vid);
		query.setInteger(1, cid);
		query.setInteger(2, vid);
		query.setInteger(3, cid);
		List list=query.list();
	    List<AWStuInfo> clas=new ArrayList<AWStuInfo>();
	    for(int i=0;i<list.size();i++){
	    	AWStuInfo c = new AWStuInfo();
		    c.setStuId((Integer)((Object[])(list.get(i)))[0]);
		    c.setStuNo((String)((Object[])(list.get(i)))[1]);
		    c.setStuName((String)((Object[])(list.get(i)))[2]);
		   // c.setTotal((Integer)((Object[])(list.get(i)))[3]);
		    clas.add(c);
	    }
	    return clas;*/
		final String sql ="select s.SID stuId,s.SNO stuNo,s.SName stuName,e.finish,sc.total"+ 
				" from StudentTable s"+ 
				" join"+ 
				"(select count(*) total from ProblemTable pt where pt.pid"+
				" in(select pid from SchemeContentTable where vid = :vid)) sc"+ 
				" on 1=1"+ 
				" left join"+ 
				"(select _e.SID,count(*) finish"+ 
				" from ExerciseTable _e,StudentTable _s"+ 
				" where _s.CID = :cid and _e.VID = :vid and _e.SID = _s.SID and _e.RunResult > 0"+ 
				" group by _e.SID) e"+ 
				" on e.SID = s.SID"+ 
				" where s.CID = :cid order by s.SNO";
		return getHibernateTemplate().executeFind(
				new  HibernateCallback() {
		           public Object doInHibernate(Session session)
		             throws HibernateException, SQLException {
		        	   return session.createSQLQuery(sql)
		        	   	.addScalar("stuId", Hibernate.INTEGER)
		        	   	.addScalar("stuNo", Hibernate.STRING)
		        	   	.addScalar("stuName", Hibernate.STRING)
		        	   	.addScalar("finish", Hibernate.INTEGER)
		        	   	.addScalar("total", Hibernate.INTEGER)
		            	.setInteger("cid", cid).setInteger("vid", vid)
		            	.setResultTransformer(Transformers.aliasToBean(AWStuInfo.class))
		            	.list();
		           }
		          });
	}

	@Override
	public List<AWPointInfo> getPointDetail(final int sid,final int vid) {
//		final String sql = "select ex.EID exId,info.PID problemId,info.ChName chapName,info.PName proName,ex.Score score," +
//				"ex.RunResult runPass,ex.CmpRightCount cmpPass,ex.CmpErrorCount cmpError,ex.RunErrCount runError,ex.FirstPastTime firstPass," +
//				"ex.LastTime lastSave,info.StartTime startTime,info.FinishTime deadline,info.PMemo memo,ex.EContent ansContent,ex.EComment commContent,info.PContent pInfo " +
//				"from(" +
//				"select p.PID,ChName,PName,p.PMemo,sc.StartTime,sc.FinishTime,p.PContent " +
//				"from ProblemTable p,ProblemChapTable pc,SchemeContentTable sc " +
//				"where p.ChID = pc.ChID and sc.PID = p.PID and sc.VID = :vid) info " +
//				"left join  " +
//				"(select * from ExerciseTable _ex where _ex.SID = :sid and _ex.VID = :vid) ex " +
//				"on ex.PID = info.PID " +
//				"order by info.ChName,info.PName";
		final String sql = "select ex.EID exId,info.PID problemId,info.ChName chapName,info.PName proName,ex.Score escore,"+
					"ex.RunResult runPass,ex.CmpRightCount cmpPass,ex.CmpErrorCount cmpError,ex.RunErrCount runError,ex.FirstPastTime firstPass,"+
					"ex.LastTime lastSave,info.StartTime startTime,info.FinishTime deadline,info.PMemo memo,info.PContent topic," +
					"ex.EContent ansContent,ex.EComment commContent"+
					" from("+
					"select p.PID,pc.ChName,p.PName,p.PMemo,s_p.StartTime,p.PContent,s_p.FinishTime"+
					" from ProblemTable p,ProblemChapTable pc,SchemeContentTable s_p"+ 
					" where p.ChID = pc.ChID and s_p.PID = p.PID and s_p.VID = :vid) info"+ 
					" left join"+  
					"(select * from ExerciseTable _ex where _ex.SID = :sid and _ex.VID = :vid) ex"+ 
					" on ex.PID = info.PID"+ 
					" order by info.ChName,info.PName";
		return getHibernateTemplate().executeFind(
				new  HibernateCallback() {
		           public Object doInHibernate(Session session)
		             throws HibernateException, SQLException {
		        	   return session.createSQLQuery(sql)
		        	   	.addScalar("exId", Hibernate.INTEGER)
		        	   	.addScalar("problemId", Hibernate.INTEGER).addScalar("chapName", Hibernate.STRING)
		        	   	.addScalar("proName", Hibernate.STRING).addScalar("escore", Hibernate.FLOAT)
		        	   	.addScalar("runPass", Hibernate.INTEGER).addScalar("cmpPass", Hibernate.INTEGER)
		        	   	.addScalar("cmpError", Hibernate.INTEGER).addScalar("runError", Hibernate.INTEGER)
		        	   	.addScalar("firstPass", Hibernate.DATE).addScalar("lastSave", Hibernate.DATE)
		        	   	.addScalar("startTime", Hibernate.DATE).addScalar("deadline", Hibernate.DATE)
		        	   	.addScalar("memo", Hibernate.STRING).addScalar("topic", Hibernate.STRING)
		        	   	.addScalar("ansContent", Hibernate.STRING)
		        	   	.addScalar("commContent", Hibernate.STRING)
		        	   	.setInteger("vid", vid).setInteger("sid", sid)
		            	.setResultTransformer(Transformers.aliasToBean(AWPointInfo.class))
		            	.list();
		           }
		          });
	}

	@Override
	public List<AWStuInfo> getStuDetail(final int cid, final int vid, final int pid) {
		//final String sql ="select s.SID ,ex.EID ,s.SNO ,s.SName ,ex.RunResult ,ex.Score,"+
				//"ex.CmpCount, ex.CmpErrorCount, ex.RunErrCount, ex.FirstPastTime,"+
				//"ex.LastTime,sc.startTime,sc.FinishTime,p.PMemo,ex.EContent,ex.EComment"+ 
		final String sql ="select s.SID stuId,ex.EID exId,s.SNO stuNo,s.SName stuName,ex.RunResult runPass,ex.Score score,"+
				"ex.CmpRightCount cmpPass,ex.CmpErrorCount cmpError,ex.RunErrCount runError,ex.FirstPastTime firstPass,"+
				"ex.LastTime lastSave,s_p.StartTime startTime,s_p.FinishTime deadline,p.PMemo memo,ex.EContent ansContent,ex.EComment commContent"+ 
				" from StudentTable s"+ 
				" left join"+ 
				"(select * from ExerciseTable _ex where _ex.VID = :vid and _ex.PID = :pid) ex"+ 
				" on ex.SID = s.SID"+ 
				" join SchemeContentTable s_p"+ 
				" on s_p.VID = :vid and s_p.PID = :pid"+ 
				" join ProblemTable p"+ 
				" on s_p.PID = p.PID"+ 
				" where s.CID = :cid"+ 
				" order by s.SNO";
		System.out.println("vid:"+vid);
		System.out.println("pid:"+pid);
		System.out.println("cid:"+cid);
		return getHibernateTemplate().executeFind(
				new  HibernateCallback() {
		           public Object doInHibernate(Session session)
		             throws HibernateException, SQLException {
		        	   return session.createSQLQuery(sql)
		        	   	.addScalar("stuId", Hibernate.INTEGER).addScalar("exId", Hibernate.INTEGER)
		        	   	.addScalar("stuNo", Hibernate.STRING)
		        	   	.addScalar("stuName", Hibernate.STRING).addScalar("runPass", Hibernate.INTEGER)
		        	   	.addScalar("score", Hibernate.FLOAT).addScalar("cmpPass", Hibernate.INTEGER)
		        	   	.addScalar("cmpError", Hibernate.INTEGER).addScalar("runError", Hibernate.INTEGER)
		        	   	.addScalar("firstPass", Hibernate.DATE).addScalar("lastSave", Hibernate.DATE)
		        	   	.addScalar("startTime", Hibernate.DATE).addScalar("deadline", Hibernate.DATE)
		        	   	.addScalar("memo", Hibernate.STRING).addScalar("ansContent", Hibernate.STRING)
		        	   	.addScalar("commContent", Hibernate.STRING)
		            	.setInteger("cid", cid).setInteger("vid", vid).setInteger("pid",pid)
		            	.setResultTransformer(Transformers.aliasToBean(AWStuInfo.class))
		            	.list();
		           }
				});
		/*final String sql ="select s.SID ,s.SNO ,s.SName ,ex.RunResult,ex.CmpCount,ex.CmpErrorCount,ex.RunErrCount"+
				" from StudentTable s"+ 
				" left join"+ 
				"(select * from ExerciseTable _ex where _ex.VID = ? and _ex.PID = ?) ex"+ 
				" on ex.SID = s.SID"+ 
				" join SchemeContentTable sc"+ 
				" on sc.VID = ? and sc.PID = ?"+ 
				" join ProblemTable p"+ 
				" on sc.PID = p.PID"+ 
				" where s.CID = ?"+ 
				" order by s.SNO";
		System.out.println("vid:"+vid);
		Query query = getSession().createSQLQuery(sql);
		query.setInteger(0, vid);
		query.setInteger(1, pid);
		query.setInteger(2, vid);
		query.setInteger(3, pid);
		query.setInteger(4, cid);
		List list=query.list();
	    List<AWStuInfo> clas=new ArrayList<AWStuInfo>();
	    for(int i=0;i<list.size();i++){
	    	AWStuInfo c = new AWStuInfo();
		    c.setStuId((Integer)((Object[])(list.get(i)))[0]);
		    c.setStuNo((String)((Object[])(list.get(i)))[1]);
		    c.setStuName((String)((Object[])(list.get(i)))[2]);
		   // c.setTotal((Integer)((Object[])(list.get(i)))[3]);
		    c.setRunPass((Integer)((Object[])(list.get(i)))[3]);
		    c.setCmpPass((Integer)((Object[])(list.get(i)))[4]);
		    c.setCmpError((Integer)((Object[])(list.get(i)))[5]);
		    c.setRunError((Integer)((Object[])(list.get(i)))[6]);
		    clas.add(c);
	    }
	    System.out.println(clas);
	    return clas;*/
	}
	
	@Override
	public boolean saveOrUpdateObject(ExerciseTable ex) {
		try {
			this.getHibernateTemplate().saveOrUpdate(ex);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}

	
	@Override
	public boolean saveOrUpdateObjectForC(CustomExerciseForCTable Cex) {
		try {
			this.getHibernateTemplate().saveOrUpdate(Cex);
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}
}
