package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;

import com.anyview.dao.CollegeDao;
import com.anyview.dao.CourseDao;
import com.anyview.dao.ProblemDao;
import com.anyview.dao.SchemeDao;
import com.anyview.dao.TeacherDao;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProblemTable;
import com.anyview.entities.SchemeContentTable;
import com.anyview.entities.SchemeTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.SchemeManager;

@Service
public class SchemeManagerImpl implements SchemeManager{
	
	@Autowired
	private SchemeDao schemeDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private TeacherDao teacherDao;

	@Override
	public Pagination<SchemeTable> getSchemePage(Map param) {
		Integer numPerPage = (Integer) param.get("numPerPage");
		Integer currentPage = (Integer) param.get("currentPage");
		TeacherTable teacher = (TeacherTable) param.get("teacher");
		SchemeTable scheme = (SchemeTable) param.get("scheme");
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		DetachedCriteria criteria = DetachedCriteria.forClass(SchemeTable.class)
				.add(Restrictions.eq("teacher.tid", teacher.getTid()))
				.add(Example.create(scheme).enableLike(MatchMode.ANYWHERE));
		
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		Pagination<SchemeTable> page = new Pagination<SchemeTable>();
		page.setTotalCount(schemeDao.getSchemeCount(criteria));
		page.setContent(schemeDao.getSchemeList((currentPage-1)*numPerPage,numPerPage,criteria));
		page.setCurrentPage((Integer)param.get("currentPage"));
		page.setNumPerPage((Integer)param.get("numPerPage"));
		page.calcutePage();
		return page;
	}
	
	public List<SchemeTable> getAllScheme(){
		return schemeDao.getAllScheme();
	}
	
	public Pagination<SchemeContentTable> getSchemeProblemsPage(Map params){
		Pagination<SchemeContentTable> page = new Pagination<SchemeContentTable>();
		page.setContent(schemeDao.getSchemeProblemsList(params));
		page.setTotalCount(schemeDao.getSchemeProblemsCount(params));
		page.setCurrentPage((Integer)params.get("currentPage"));
		page.setNumPerPage((Integer)params.get("numPerPage"));
		page.calcutePage();
		return page;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#saveNewScheme(java.util.Map)
	 */
	@Override
	public void saveNewScheme(Map params) throws Exception{
		String [][] scs = (String[][]) params.get("scs");
		TeacherTable teacher = (TeacherTable) params.get("teacher");
		SchemeTable scheme = (SchemeTable) params.get("scheme");
		//先保存SchemeTable，获取返回的vid
		scheme.setTeacher(teacher);
		scheme.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Integer vid = schemeDao.saveScheme(scheme);
		scheme.setVid(vid);
		//再保存SchemeContentTable   
		List<SchemeContentTable> scList = new ArrayList<SchemeContentTable>();
		for(String[]s:scs){
			SchemeContentTable sct = new SchemeContentTable();
			sct.setScheme(scheme);
			ProblemTable problem = new ProblemTable();
			problem.setPid(Integer.valueOf(s[0]));
			sct.setProblem(problem);
			String vpname = s[1]==null?"":s[1].trim();
			sct.setVpName("".equals(vpname)?problem.getPname():vpname);
			sct.setVchapName(s[2]);
			sct.setStartTime(Timestamp.valueOf(s[3]));
			sct.setFinishTime(Timestamp.valueOf(s[4]));
			sct.setScore(Float.valueOf(s[5]));
			sct.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			scList.add(sct);
		}
		schemeDao.saveSchemeContent(scheme,scList);
		
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#getSchemeByVid(java.lang.Integer)
	 */
	@Override
	public SchemeTable getSchemeByVid(Integer vid) {
		return schemeDao.getSchemeByVid(vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#updateScheme(java.util.Map)
	 */
	@Override
	public void updateScheme(SchemeTable scheme) throws Exception{
		schemeDao.updateScheme(scheme);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#getSchemeProblemList(java.lang.Integer)
	 */
	@Override
	public List<ProblemTable> getSchemeProblemList(Integer vid) {
		return schemeDao.getSchemeProblemList(vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#getSchemeContentList(java.lang.Integer)
	 */
	@Override
	public List<SchemeContentTable> getSchemeContentList(Integer vid) {
		return schemeDao.getSchemeContentList(vid);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#updataSchemePros(java.util.Map)
	 */
	@Override
	public void updataSchemePros(Map params) {
		String [][] scs = (String[][]) params.get("scs");
		SchemeTable scheme = (SchemeTable) params.get("scheme");
		//先删除之前的SchemeContentTable信息，再保存SchemeContentTable   
		schemeDao.deleteAllSchemeContentByVid(scheme.getVid());
		List<SchemeContentTable> scList = new ArrayList<SchemeContentTable>();
		for(String[]s:scs){
			SchemeContentTable sct = new SchemeContentTable();
			sct.setScheme(scheme);
			ProblemTable problem = new ProblemTable();
			problem.setPid(Integer.valueOf(s[0]));
			sct.setProblem(problem);
			String vpname = s[1]==null?"":s[1].trim();
			sct.setVpName("".equals(vpname)?problem.getPname():vpname);
			sct.setVchapName(s[2]);
			sct.setStartTime(Timestamp.valueOf(s[3]));
			sct.setFinishTime(Timestamp.valueOf(s[4]));
			sct.setScore(Float.valueOf(s[5]));
			sct.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			scList.add(sct);
		}
		schemeDao.saveSchemeContent(scheme,scList);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#getSchemeContentById(java.lang.Integer)
	 */
	@Override
	public SchemeContentTable getSchemeContentById(Integer scId) {
		return schemeDao.getSchemeContentById(scId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#updateSchemeContent(com.anyview.entities.SchemeContentTable)
	 */
	@Override
	public void updateSchemeContent(SchemeContentTable sc) throws Exception {
		//更新sc信息
		schemeDao.updateSchemeContent(sc);
		//更新对应scheme的fullScore,updateTime
		schemeDao.updateSchemeFullScore(sc.getScheme().getVid());
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#deleteSchemeContent(java.lang.Integer)
	 */
	@Override
	public void deleteSchemeContent(SchemeContentTable sc) {
		schemeDao.deleteSchemeContent(sc);
		//更新对应scheme的fullScore,updateTime
		schemeDao.updateSchemeFullScore(sc.getScheme().getVid());
		
		
		
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.SchemeManager#getSchemeINFromClassCourseScheme(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Object[]> getSchemeINFromClassCourseScheme(TeacherTable teacher, ClassTable cla, CourseTable course) {
		//查询出同学院的教师id
		List<Integer> tids = teacherDao.getSameCollegeTeacherId(teacher.getTid());
		//查询出scheme_teacher中对应的scheme的vid
		List<Integer> vids = schemeDao.getVidFromSchemeTeacher(teacher.getTid());
		//传入
		return schemeDao.getSchemeINFromClassCourseScheme(teacher, cla, course, tids, vids);
	}
	
    //根据unid，ceid，courseid判断应该加载的作业表
	@Override
	public List<SchemeTable> getSchemeById(Integer unId, Integer ceId,
			Integer courseId,TeacherTable teacher) {
		if(courseId!=null&&!courseId.equals("")){
			return schemeDao.getSchemeListByCourseId(courseId,teacher);
		}
		else if(ceId!=null&&!ceId.equals("")){
			return schemeDao.getSchemeListByCeID(ceId,teacher);
		}
		else if(unId!=null&&!unId.equals("")){
			if(teacher.getUniversity().getUnID().equals(unId))
				return schemeDao.getSchemeListByUnID(unId,teacher);
			else 
				return schemeDao.getSchemeListByOtherUnID(unId, teacher);
			
		}
		else return null;
		
	}

	@Override
	public void saveNScheme(SchemeTable scheme, List<SchemeContentTable> scs)
			throws Exception {
		Integer vid = schemeDao.saveScheme(scheme);
		scheme.setVid(vid);
		schemeDao.saveSchemeContent(scheme,scs);
	}

	@Override
	public List<SchemeTable> getSchemesByCidCourseIdTid(Integer tid,
			Integer cid, Integer courseId) {
		return schemeDao.getSchemesByCidCourseIdTid(tid, cid, courseId);
	}

}
