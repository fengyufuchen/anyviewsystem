package com.anyview.service.function.impl;

import java.nio.charset.CodingErrorAction;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.CollegeDao;
import com.anyview.dao.UniversityDao;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.CourseTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.CollegeManager;
import com.anyview.utils.TipException;

@Service
public class CollegeManagerImpl implements CollegeManager{
	
	@Autowired
	private CollegeDao collegeDao;
	@Autowired
	private UniversityDao universityDao;

	@Override
	public Set<CollegeTable> getColleges(Map param) {
//		Integer unId = (Integer) param.get("unID");
//		if(unId==null) return null;
//		UniversityTable un = universityDao.getUniversityById(unId);
//		return un.getColleges();
		return null;
	}
	
	//通过学校ID获取该学校的下属学院的数量
	public Integer getCollegesByUnid(Integer unId){
		Integer count = collegeDao.getCollegesByUnid(unId).size();
		return count;
	}

	@Override
	public List<Object[]> getCollegesINByUnId(Integer unID)throws Exception {
		List<Object[]> obs = collegeDao.getCollegesINByUnId(unID);
		if(obs==null || obs.isEmpty())
			throw new TipException("此学校无下属学院");
		return obs;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CollegeManager#getEnabledCollegesByUnId(java.lang.Integer)
	 */
	@Override
	public List<Object[]> getEnabledCollegesByUnId(Integer unId) {
		return collegeDao.getEnabledCollegesByUnId(unId);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.CollegeManager#getCollegesByUnIds(java.lang.Integer[])
	 */
	@Override
	public List<CollegeTable> getCollegesByUnIds(Integer[] ids) {
		return collegeDao.getCollegesByUnIds(ids);
	}
   
	//获取学院管理页面数据 
	public Pagination<CollegeTable> getCollegePage(Map param){
		ManagerTable admin = (ManagerTable) param.get("admin");
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		CollegeTable condition = (CollegeTable) param.get("condition");
		Timestamp createDateStart = (Timestamp) param.get("createDateStart");
		Timestamp createDateEnd = (Timestamp) param.get("createDateEnd");
		Timestamp updateDateStart = (Timestamp) param.get("updateDateStart");
		Timestamp updateDateEnd = (Timestamp) param.get("updateDateEnd");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CollegeTable.class)
				.add(Example.create(condition).enableLike(MatchMode.ANYWHERE));
		
		if(createDateStart!=null && createDateEnd!=null)
			criteria = criteria.add(Restrictions.between("createTime", createDateStart, createDateEnd));
		if(updateDateStart!=null && updateDateEnd!=null)
			criteria = criteria.add(Restrictions.between("updateTime", updateDateStart, updateDateEnd));
		if(condition!=null&&condition.getEnabled()!=null)
			criteria = criteria.add(Restrictions.eq("enabled", condition.getEnabled()));
		Pagination<CollegeTable> page = new Pagination<CollegeTable>();
		if(admin.getMiden()==-1){//超级管理员
			  if(condition.getUniversity()==null) {
				  criteria = criteria.add(Restrictions.lt("university.unID", -1));
			  }else {
				  if(condition.getUniversity().getUnID()!=null)
				    criteria = criteria.add(Restrictions.eq("university.unID", condition.getUniversity().getUnID()));
				  if(condition.getCeName()!=null)
				    criteria = criteria.add(Restrictions.ilike("ceName", condition.getCeName(),MatchMode.ANYWHERE));
			      
			  }
		}
        if(admin.getMiden()==1){//校级管理员查询出院级
			criteria = criteria.add(Restrictions.eq("university.unID",admin.getUniversity().getUnID()));
			if(condition.getCeName()!=null){
			    criteria = criteria.add(Restrictions.ilike("ceName", condition.getCeName(),MatchMode.ANYWHERE));
			}
		}
		page.setTotalCount(collegeDao.getCollegeCount(criteria));
        List<CollegeTable> content = collegeDao.getCollegesPage((pageNum-1)*pageSize, pageSize, criteria);
		page.setContent(content);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.calcutePage();
		return page;
	}
	
	//添加学院信息
	public boolean addCollege(CollegeTable college){
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		college.setCreateTime(createTime);
		return collegeDao.saveCollege(college);
	}
	
	//判断学院是否已经存在于数据库当中
	public boolean isCollegeExist(Integer unID,String ceName){
		List<CollegeTable> collegeList = collegeDao.getCollegesByUnid(unID);
		for(CollegeTable c : collegeList){
			if(c.getCeName().equals(ceName))   
				return true;
		}
		return false;
	   }
	
	//修改学院信息
	public boolean updateCollege(CollegeTable college) throws Exception{
		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		college.setUpdateTime(updateTime);		
		return collegeDao.updateCollege(college);
	   }
	//通过学院编号ceID获取该学院的详细信息
	public CollegeTable gainCollegeByCeid(Integer ceId){
		return collegeDao.getCollegeById(ceId);
	}
	
	//删除学院信息
	public boolean deleteCollege(int ceId)
	{
		return collegeDao.deleteCollege(ceId);
	}
}
