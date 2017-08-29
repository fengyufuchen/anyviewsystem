package com.anyview.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.anyview.dao.AdminStudentDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.UniversityTable;
import com.anyview.utils.encryption.MD5Util;

/**
 * @Description 学生管理数据访问接口实现类
 * @author DenyunFang
 * @time 2015年8月29日
 * @version 1.0
 */

@Component
public class AdminStudentDaoImpl extends BaseDaoImpl implements AdminStudentDao{
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return List<StudentTable> (封装了学生信息与页面信息的列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:28:26
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ClassStudentTable> getStudentsPage(Map param) {
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		
		ManagerTable mana = (ManagerTable) param.get("Manager");
		ClassTable condition = (ClassTable) param.get("condition");
		StudentTable conditionstu = (StudentTable) param.get("conditionstu");
			
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class);
		criteria = criteria.createAlias("student", "s").createAlias("cla", "c");
		
		//获取页面内容
		if(mana.getMiden() == 1){
			//校级管理员
			//根据学校id获取学校里的所有学生
			
			criteria = criteria.add(Restrictions.eq("s.university.unID", mana.getUniversity().getUnID()));
			
			//根据学院id获取学院里的所有学生
			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
			{
				String hqlce = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
				List<Integer> stuce = hibernateTemplate.find(hqlce, condition.getCollege().getCeID());
				if(stuce.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stuce));
				else if(stuce.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
				
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hqlcla = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hqlcla, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
		}
		else if(mana.getMiden() == 0){
			//院级管理员
			//根据学院id获取学院里的所有学生
			String hqlce = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
			List<Integer> stuce = hibernateTemplate.find(hqlce, mana.getCollege().getCeID());
			if(stuce.size() > 0)	
				criteria = criteria.add(Restrictions.in("s.sid", stuce));
			else if(stuce.size() <= 0)
				criteria = criteria.add(Restrictions.eq("s.sid", -1));
			
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hqlcla = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hqlcla, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
			
		}	
		else if(mana.getMiden() == -1){
			//超级管理员
			//根据学校id获取学校里的所有学生
			if(condition.getCollege() != null)
				if(condition.getCollege().getUniversity() != null && condition.getCollege().getUniversity().getUnID() != null)
					criteria = criteria.add(Restrictions.eq("s.university.unID", condition.getCollege().getUniversity().getUnID()));
			
			//根据学院id获取学院里的所有学生
			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
			{
				String hql = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
				List<Integer> stuce = hibernateTemplate.find(hql, condition.getCollege().getCeID());
				if(stuce.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stuce));
				else if(stuce.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
				
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hql = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hql, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
		}
		
		//所有管理员共有学号和姓名查询
		if(conditionstu.getSno() != null)
			criteria = criteria.add(Restrictions.like("s.sno", "%" + conditionstu.getSno() + "%"));
		if(conditionstu.getSname() != null)
			criteria = criteria.add(Restrictions.like("s.sname", "%" + conditionstu.getSname() + "%"));
	    criteria.addOrder(Order.asc("c.cname"));
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		List<ClassStudentTable> stus = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return stus;
	}
	
	/**
	 * 
	 * @Description: 将Map集合中的学生信息进行封装 
	 * @param param
	 * @return List<StudentTable> (封装了学生信息与页面信息的列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:28:26
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<StudentTable> getListStudentsPage(Map param) {
		System.out.println("------------进来了getListStudentsPage-----------");
		Integer pageNum = Integer.valueOf(param.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(param.get("pageSize").toString());
		
		String orderField = (String) param.get("orderField");
		String orderDirection = (String) param.get("orderDirection");
		
		StudentTable conditionstu = (StudentTable) param.get("conditionstu");
		Integer cids = Integer.valueOf(param.get("cids").toString());
		System.out.println("------------adminstudentdaoimpl cids-----------" + cids);
		Session session = getSession();
		ClassTable cla = (ClassTable) session.get(ClassTable.class, cids);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentTable.class);
		criteria = criteria.createAlias("university", "u")
				.add(Restrictions.eq("u.unID", cla.getCollege().getUniversity().getUnID()));
		//所有管理员共有学号和姓名查询
		if(conditionstu.getSno() != null)
			criteria = criteria.add(Restrictions.like("sno", "%" + conditionstu.getSno() + "%"));
		if(conditionstu.getSname() != null)
			criteria = criteria.add(Restrictions.like("sname", "%" + conditionstu.getSname() + "%"));
	
		if("asc".equalsIgnoreCase(orderDirection))
			criteria.addOrder(Order.asc(orderField));
		else
			criteria.addOrder(Order.desc(orderField));
		List<StudentTable> stus = hibernateTemplate.findByCriteria(criteria, (pageNum-1)*pageSize, pageSize);
		return stus;
	}

	/**
	 * 
	 * @Description: 获取数据库中的学生总数
	 * @param param
	 * @return count(返回数据库中的学生总数)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:28:41
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public Integer getStudentCount(Map param) {
		ManagerTable mana = (ManagerTable) param.get("Manager");
		ClassTable condition = (ClassTable) param.get("condition");
		StudentTable conditionstu = (StudentTable) param.get("conditionstu");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class);
		criteria = criteria.createAlias("student", "s").createAlias("cla", "c");
		
		//获取页面内容
		if(mana.getMiden() == 1){
			//校级管理员
			//根据学校id获取学校里的所有学生
			
			criteria = criteria.add(Restrictions.eq("s.university.unID", mana.getUniversity().getUnID()));
			
			//根据学院id获取学院里的所有学生
			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
			{
				String hqlce = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
				List<Integer> stuce = hibernateTemplate.find(hqlce, condition.getCollege().getCeID());
				if(stuce.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stuce));
				else if(stuce.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
				
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hqlcla = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hqlcla, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
		}
		else if(mana.getMiden() == 0){
			//院级管理员
			//根据学院id获取学院里的所有学生
			String hqlce = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
			List<Integer> stuce = hibernateTemplate.find(hqlce, mana.getCollege().getCeID());
			if(stuce.size() > 0)	
				criteria = criteria.add(Restrictions.in("s.sid", stuce));
			else if(stuce.size() <= 0)
				criteria = criteria.add(Restrictions.eq("s.sid", -1));
			
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hqlcla = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hqlcla, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
			
		}	
		else if(mana.getMiden() == -1){
			//超级管理员
			//根据学校id获取学校里的所有学生
			if(condition.getCollege() != null)
				if(condition.getCollege().getUniversity() != null && condition.getCollege().getUniversity().getUnID() != null)
					criteria = criteria.add(Restrictions.eq("s.university.unID", condition.getCollege().getUniversity().getUnID()));
			
			//根据学院id获取学院里的所有学生
			if(condition.getCollege() != null && condition.getCollege().getCeID() != null)
			{
				String hql = "select c.student.sid from ClassStudentTable c where c.cla.college.ceID=?";
				List<Integer> stuce = hibernateTemplate.find(hql, condition.getCollege().getCeID());
				if(stuce.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stuce));
				else if(stuce.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
				
			//根据班级id获取班级里的所有学生
			if(condition.getCid() != null)
			{
				String hql = "select c.student.sid from ClassStudentTable c where c.cla.cid=?";
				List<Integer> stucla = hibernateTemplate.find(hql, condition.getCid());
				if(stucla.size() > 0)	
					criteria = criteria.add(Restrictions.in("s.sid", stucla));
				else if(stucla.size() <= 0)
					criteria = criteria.add(Restrictions.eq("s.sid", -1));
			}
			
		}
		criteria.addOrder(Order.asc("c.cname"));
		
		//所有管理员共有学号和姓名查询
		if(conditionstu.getSno() != null)
			criteria = criteria.add(Restrictions.like("s.sno", "%" + conditionstu.getSno() + "%"));
		if(conditionstu.getSname() != null)
			criteria = criteria.add(Restrictions.like("s.sname", "%" + conditionstu.getSname() + "%"));
				
		criteria =  criteria.setProjection(Projections.countDistinct("s.sid"));
		Integer count = (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
		return count;
	}

	/**
	 * 
	 * @Description: 获取数据库中的学生总数
	 * @param param
	 * @return count(返回数据库中的学生总数)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:28:41
	 */
	@SuppressWarnings({"rawtypes"})
	@Override
	public Integer getListStudentCount(Map param) {
		
		StudentTable conditionstu = (StudentTable) param.get("conditionstu");
		Integer cids = Integer.valueOf(param.get("cids").toString());
		
		Session session = getSession();
		ClassTable cla = (ClassTable) session.get(ClassTable.class, cids);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(StudentTable.class);
//		DetachedCriteria criteria = DetachedCriteria.forClass(ClassStudentTable.class);
		
		//根据班级id获取学校id，并作为限制条件
		criteria = criteria.createAlias("university", "u")
				.add(Restrictions.eq("u.unID", cla.getCollege().getUniversity().getUnID()));
		
		//所有管理员共有学号和姓名查询
		if(conditionstu.getSno() != null)
			criteria = criteria.add(Restrictions.like("sno", "%" + conditionstu.getSno() + "%"));
		if(conditionstu.getSname() != null)
			criteria = criteria.add(Restrictions.like("sname", "%" + conditionstu.getSname() + "%"));
				
		criteria =  criteria.setProjection(Projections.countDistinct("sid"));
		Integer count = (Integer) hibernateTemplate.findByCriteria(criteria).get(0);
		return count;
	}
	
	/**
	 * 
	 * @Description: 获取所有学校 
	 * @return List<UniversityTable> (返回所有学校列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:56:01
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UniversityTable> selectAllUniversity() {
		try{
			String hql = "from UniversityTable where enabled=1 and unID>0";
			return hibernateTemplate.find(hql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据学校ID获取所有学院
	 * @param unID
	 * @return List<ClassTable> (返回该学校的所有学院列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:50:52
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeTable> selectAllCollegeByUnID(Integer unID) {
		try{
			String hql = "from CollegeTable c where c.enabled=1 and c.university.unID=?";
			return hibernateTemplate.find(hql, unID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据学院ID获取所有班级
	 * @param ceID
	 * @return List<ClassTable> (返回该学院的所有班级列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月17日 下午9:48:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassTable> selectAllClassByCeId(Integer ceID) {
		try{
			String hql = "from ClassTable c where c.enabled=1 and c.college.ceID=?";
			return hibernateTemplate.find(hql,ceID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: TODO(根据学校ID获取所有学生) 
	 * @param unID
	 * @return List<StudentTable> (返回所有学生列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:03:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentTable> getAllStudentByUnId(Integer unID) {
		try{
			String hql = "from StudentTable where university.unID=?";
			return hibernateTemplate.find(hql,unID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: TODO(保存学生信息到数据库中) 
	 * @param stu
	 * @return boolean (true表示保存成功，false表示保存失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午3:57:15
	 */
	@Override
	public boolean saveStudent(StudentTable stu) {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		stu.setCreateTime(t);
		stu.setLoginStatus(0);//登陆状态，默认未登录
		stu.setEnabled(1);//学生表的有效状态：0 停用 1正常
		stu.setSaccumTime(0);//累积做题时间
		try {
			stu.setSpsw(MD5Util.getEncryptedPwd(stu.getSno()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}//学生默认密码为学号
		return saveObject(stu);
	}
	
	/**
	 * 
	 * @Description: TODO(根据学生ID删除数据库的学生信息) 
	 * @param sid
	 * @return boolean (true表示删除成功，false表示删除失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:30:18
	 */
	@Override
	public boolean deleteStudentBySid(Integer sid) {
		try {
			//先清空ClassStudentTable下的所有ID为sid的学生
			String hql = "delete from ClassStudentTable where student.sid=?";
			hibernateTemplate.bulkUpdate(hql,sid);
			//再删除学生表里的该学生
			hql = "delete from StudentTable where sid = ?";
			hibernateTemplate.bulkUpdate(hql,sid);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	@SuppressWarnings("unchecked")
	public boolean addStudentInClass(Integer sid, Integer cid, ClassStudentTable cs){
		System.out.println("-------adminstudentdaoimpl--addStudentInClass cid:" + cid);
		try {
			System.out.println("-------进来了addStudentInClass cid:" + cid);
			//检测该学生是否已经与班级关联
			String hql = "from ClassStudentTable where student.sid=?";	
			
			List<ClassStudentTable> csList = hibernateTemplate.find(hql, sid);
			if(csList.size() > 0)
			{
				for(int i = 0; i < csList.size(); i++)
					if(csList.get(i).getCla().getCid().equals(cid))	//已存在则更新
					{
						Session session = getSession();
						ClassStudentTable ycs = (ClassStudentTable) session.get(ClassStudentTable.class, csList.get(i).getId());
						ycs.setSattr(cs.getSattr());
						ycs.setStatus(cs.getStatus());
						Timestamp t = new Timestamp(System.currentTimeMillis());
						ycs.setUpdateTime(t);
						return saveOrUpdateObject(ycs);
					}
			}
			System.out.println("-------adminstudentdaoimpl--addStudentInClass");
			//若没有则添加关联
			Timestamp t = new Timestamp(System.currentTimeMillis());
			ClassStudentTable ncs = new ClassStudentTable();
			
			Session sessionstu = getSession();
			StudentTable stu = (StudentTable) sessionstu.get(StudentTable.class, sid);
			
			Session sessioncla = getSession();
			System.out.println("-------ClassTable cid:" + cid);
			ClassTable cla = (ClassTable) sessioncla.get(ClassTable.class, cid);

			ncs.setCla(cla);
			ncs.setStudent(stu);
			ncs.setSattr(cs.getSattr());
			ncs.setStatus(cs.getStatus());
			ncs.setUpdateTime(t);
			
			return saveObject(ncs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	@SuppressWarnings("unchecked")
	public boolean batAddStudentInClass(Integer sid, Integer cid){
		try {
			//检测该学生是否已经与班级关联
			String hql = "from ClassStudentTable where student.sid=?";	
			List<ClassStudentTable> csList = hibernateTemplate.find(hql, sid);
			if(csList.size() > 0)
			{
				for(int i = 0; i < csList.size(); i++)
					if(csList.get(i).getCla().getCid().equals(cid))	//已存在，不用添加
						return true;
			}
			
			//若没有则添加关联
			Timestamp t = new Timestamp(System.currentTimeMillis());
			ClassStudentTable cs = new ClassStudentTable();
			
			Session sessionstu = getSession();
			StudentTable stu = (StudentTable) sessionstu.get(StudentTable.class, sid);
			
			Session sessioncla = getSession();
			ClassTable cla = (ClassTable) sessioncla.get(ClassTable.class, cid);

			cs.setCla(cla);
			cs.setStudent(stu);
			cs.setSattr(1);
			cs.setStatus(1);
			cs.setUpdateTime(t);
			
			return saveObject(cs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 
	 * @Description: TODO(根据学生ID获取该学生所有信息) 
	 * @param sid
	 * @return List<StudentTable> (返回该学生的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:45:56
	 */
	@Override
	public StudentTable gainStudentBySid(Integer sid) {
		Session session = getSession();
		StudentTable stu = (StudentTable) session.get(StudentTable.class, sid);
		return stu;
	}

	/**
	 * 
	 * @Description: TODO(修改数据库的学生信息) 
	 * @param stu
	 * @return boolean (true表示修改成功，false表示修改失败)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午4:46:10
	 */
	@Override
	public boolean updateStudent(StudentTable stu) {
		Session session = getSession();
		StudentTable nstu = (StudentTable) session.get(StudentTable.class, stu.getSid());
		nstu.setSname(stu.getSname());
		nstu.setSno(stu.getSno());
		nstu.setSsex(stu.getSsex());
		nstu.setEnabled(stu.getEnabled());
		Timestamp t = new Timestamp(System.currentTimeMillis());
		nstu.setUpdateTime(t);
		return saveOrUpdateObject(nstu);
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
		Session session = getSession();
		StudentTable stu = (StudentTable) session.get(StudentTable.class, sid);
		try {
			stu.setSpsw(MD5Util.getEncryptedPwd(stu.getSno()));
			Timestamp t = new Timestamp(System.currentTimeMillis());
			stu.setUpdateTime(t);
			return saveOrUpdateObject(stu);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}//学生默认密码为学号
		return false;
	}

	/**
	 * 
	 * @Description: TODO(根据学校ID获取该学校的所有信息列表) 
	 * @param unID
	 * @return List<UniversityTable> (返回该学校的所有信息列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午7:37:29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UniversityTable> getUniversityByUnId(Integer unID) {
		try{
			String hql = "from UniversityTable where unID=?";
			return hibernateTemplate.find(hql,unID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: TODO(批量添加学生) 
	 * @param stuList
	 * @return List<StudentTable> (返回状态值为"已提交"的学生列表)
	 * @author DenyunFang<846396179@qq.com>
	 * @date 2015年10月19日 下午9:04:37
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List batAddStudent(List stuList) {
		Map map = null;
		boolean flag = false;
		
		for(int i = 0; i < stuList.size(); ++i){
			map = (Map) stuList.get(i);
			int unid = Integer.parseInt((String)map.get("classId"));
			int senabled = (Integer)map.get("SAttr");
			String sname =((String)map.get("studentName") == null)?"":(String)map.get("studentName");
			String sno = ((String)map.get("studentNo") == null)?"":(String)map.get("studentNo");
			String ssex =((String)map.get("studentSex") == null)?"M":(String)map.get("studentSex");
			flag = false;
			UniversityTable uni = (UniversityTable) getSession().get(UniversityTable.class, unid);
			
			Timestamp creatTime = new Timestamp(System.currentTimeMillis());
			Timestamp updateTime = new Timestamp(System.currentTimeMillis());

			try{
				List<StudentTable> studentList = getAllStudentByUnId(unid);
				for(StudentTable stus : studentList){
					if(stus.getSno().equals(sno))
					{
						flag = true;
						break;
					}
				}
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			StudentTable stu = new StudentTable();
			stu.setSname(sname);
			stu.setSno(sno);
			stu.setSsex(ssex);
			stu.setUniversity(uni);
			stu.setEnabled(senabled);
			stu.setLoginStatus(0);//登陆状态，默认未登录
			stu.setSaccumTime(0);//累积做题时间
			
			try {
				stu.setSpsw(MD5Util.getEncryptedPwd(stu.getSno()));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}//学生默认密码为学号
			
			if(flag == true)
			{
				map.put("submit",0);//0表示数据库已存在该学生，修改
				stu.setCreateTime(updateTime);
				updateObject(stu);
			}
			else
			{
				map.put("submit",1);//1表示数据库不存在该学生，添加
				stu.setCreateTime(creatTime);
				saveObject(stu);
			}	
			stuList.set(i,map);
		}

		return stuList;
	}
	
}
