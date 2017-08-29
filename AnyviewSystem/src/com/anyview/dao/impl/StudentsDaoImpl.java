package com.anyview.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.anyview.dao.StudentsDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ManagerTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;

@Repository
public class StudentsDaoImpl extends BaseDaoImpl implements StudentsDao{

	@Override
	public StudentTable gainStudentBySid(Integer sid) {
		Session session = getSession();
		StudentTable stu = (StudentTable) session.get(StudentTable.class, sid);
		return stu;
	}

	@Override
	public void updateClassStudent(ClassStudentTable cs) {
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		ClassStudentTable cc = (ClassStudentTable) hibernateTemplate.get(ClassStudentTable.class, cs.getId());
		cc.setSattr(cs.getSattr());
		cc.setStatus(cs.getStatus());
		cc.setUpdateTime(curTime);
		StudentTable stu = (StudentTable) hibernateTemplate.get(StudentTable.class, cc.getStudent().getSid());
		stu.setSname(cs.getStudent().getSname());
		stu.setSno(cs.getStudent().getSno());
		stu.setSsex(cs.getStudent().getSsex());
		stu.setUpdateTime(curTime);
		hibernateTemplate.flush();
	}

	@Override
	public StudentTable gainStudentBySnoAndUnid(String sno, Integer unId) {
		String hql = "from StudentTable st left join fetch st.university u where sno=? and u.unID=?";
		List list = hibernateTemplate.find(hql, new Object[]{sno,unId});
		if(list.size() == 0)
			return null;
		StudentTable stu = (StudentTable) list.get(0);
		return stu;
	}

	@Override
	public Integer saveStudent(StudentTable stu) {
		Session session = getSession();
		Integer sid = (Integer) session.save(stu);
		return sid;
	}

	/* (non-Javadoc)
	 * @see com.anyview.dao.StudentsDao#updateStudent(com.anyview.entities.StudentTable)
	 */
	@Override
	public void updateStudent(StudentTable stu) {
		hibernateTemplate.update(stu);
		hibernateTemplate.flush();
	}

	@Override
	public List<StudentTable> getStudentsByCidSname(int cid, String sname) {
		String hql = "";
		if(sname == null){
			hql = "select cs.student from ClassStudentTable cs where cs.cla.cid=? and cs.status = 1 order by cs.student.sname";
			return hibernateTemplate.find(hql, cid);
		}else{
			hql = "select cs.student from ClassStudentTable cs where cs.cla.cid=? and cs.student.sname like '%?%' and cs.status = 1 order by cs.student.sname";
			return hibernateTemplate.find(hql, new Object[]{cid, sname});
		}
	}

}
