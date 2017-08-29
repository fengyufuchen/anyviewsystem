package com.anyview.service.function.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassDao;
import com.anyview.dao.ClassStudentDao;
import com.anyview.dao.ClassTeacherDao;
import com.anyview.dao.StudentsDao;
import com.anyview.dao.UniversityDao;
import com.anyview.dao.impl.BaseDaoImpl;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.StudentsManager;
import com.anyview.utils.BinaryUtils;
import com.anyview.utils.TipException;
import com.anyview.utils.encryption.MD5Util;

@Service
public class StudentsManagerImpl implements StudentsManager {
	
	@Autowired
	private StudentsDao studentsDao;
	@Autowired
	private ClassTeacherDao classTeacherDao;
	@Autowired
	private UniversityDao universityDao;
	@Autowired
	private ClassDao classDao;
	@Autowired
	private ClassStudentDao classStudentDao;

	@Override
	public StudentTable gainStudentBySid(Integer sid) {
		StudentTable stu = studentsDao.gainStudentBySid(sid);
		return stu;
	}

	@Override
	public void updateStudent(TeacherTable tea, ClassStudentTable cs) throws TipException {
		int tcRight = classTeacherDao.getTCRight(tea.getTid(), cs.getCla().getCid());
		byte []right = BinaryUtils.getTCRgihtArray(tcRight);
		if(right[3] != 1)
			throw new TipException("您没有管理此班级学生的权限！");
		studentsDao.updateClassStudent(cs);
	}

	@Override
	public void saveStudentForClass(TeacherTable tea, ClassStudentTable cs)
			throws TipException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		//判断权限
		int tcRight = classTeacherDao.getTCRight(tea.getTid(), cs.getCla().getCid());
		if(!BinaryUtils.manageStu(tcRight))
			throw new TipException("您没有管理此班级的权限！");
		//先保存学生信息
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		cs.getStudent().setUniversity(tea.getUniversity());
		cs.getStudent().setCreateTime(curTime);
		cs.getStudent().setLoginStatus(0);//登陆状态，默认未登录
		cs.getStudent().setEnabled(1);//学生表的有效状态：0 停用 1正常
		cs.getStudent().setSaccumTime(0);//累积做题时间
		cs.getStudent().setSpsw(MD5Util.getEncryptedPwd(cs.getStudent().getSno()));//学生默认密码为学号
		Integer sid = studentsDao.saveStudent(cs.getStudent());
		//再保存班级学生信息
		cs.getStudent().setSid(sid);
		cs.setUpdateTime(curTime);
		classStudentDao.saveClassStudent(cs);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.StudentsManager#resetStudentPwd(com.anyview.entities.TeacherTable, com.anyview.entities.ClassStudentTable)
	 */
	@Override
	public void resetStudentPwd(TeacherTable tea, ClassStudentTable cs)
			throws TipException, NoSuchAlgorithmException, UnsupportedEncodingException {
		//判断权限
		int tcRight = classTeacherDao.getTCRight(tea.getTid(), cs.getCla().getCid());
		if(!BinaryUtils.resetStuPsw(tcRight))
			throw new TipException("您没有重置此班级学生密码的权限！");
		StudentTable stu = studentsDao.gainStudentBySid(cs.getStudent().getSid());
		stu.setSpsw(MD5Util.getEncryptedPwd(stu.getSno()));
		studentsDao.updateStudent(stu);
	}

	@Override
	public List<StudentTable> getStudentsByCidSname(int cid, String sname) {
		return studentsDao.getStudentsByCidSname(cid, sname);
	}


}
