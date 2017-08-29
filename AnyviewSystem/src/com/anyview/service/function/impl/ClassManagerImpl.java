package com.anyview.service.function.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.stereotype.Service;

import com.anyview.dao.ClassDao;
import com.anyview.entities.ClassStudentTable;
import com.anyview.entities.ClassTable;
import com.anyview.entities.ClassTeacherTable;
import com.anyview.entities.Pagination;
import com.anyview.entities.StudentTable;
import com.anyview.entities.TeacherTable;
import com.anyview.service.function.ClassManager;
import com.anyview.utils.BinaryUtils;
import com.anyview.utils.TipException;

@Service
public class ClassManagerImpl implements ClassManager{
	
	@Autowired
	private ClassDao classDao;

	public List<ClassTable> getClassesByTid(Integer tid) {
		List<ClassTable> classes = classDao.getClassesByTid(tid);
		return classes;
	}
	
	@Override
	public Pagination<ClassTable> getClassesPage(Map param) {
		Pagination<ClassTable> page = new Pagination<ClassTable>();
		page.setContent(classDao.getClasses(param));
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(classDao.getClassCountByTid((Integer)param.get("tid")));
		page.calcutePage();
		return page;
	}

	@Override
	public Pagination<ClassTable> getClassPageByTea(Integer pageSize,
			Integer pageNum, TeacherTable tea) {
		Pagination<ClassTable> page = new Pagination<ClassTable>();
		List<ClassTable> clas = classDao.getClassesByTea(pageSize,pageNum,tea);
		page.setContent(clas);
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		//working
//		page.setTotalCount(classDao.getClassCountByTea(tea));
		page.calcutePage();
		return page;
	}

	@Override
	public Pagination<StudentTable> getStudentsPageByCid(Integer pageSize,
			Integer pageNum, Integer cid) {
		Pagination<StudentTable> page = new Pagination<StudentTable>();
		List<StudentTable> stus = classDao.getStudentsPageByCid(pageSize,pageNum,cid);
		page.setContent(stus);
		page.setCurrentPage(pageNum);
		page.setNumPerPage(pageSize);
		page.setTotalCount(classDao.getStudentCountByCid(cid));
		page.calcutePage();
		return page;
	}
	
	@Override
	public Pagination<ClassStudentTable> getStudentsPage(Map param) {
		Pagination<ClassStudentTable> page = new Pagination<ClassStudentTable>();
		List<ClassStudentTable> stus = classDao.getStudentsPage(param);
		page.setContent(stus);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(classDao.getStudentCount(param));
		page.calcutePage();
		return page;
	}

	@Override
	public ClassTable getClassByCid(Integer cid) {
		return classDao.getClassByCid(cid);
	}

	@Override
	public void updateClass(ClassTable cla, Integer tcRight) throws Exception{
		//获取旧班级信息
		ClassTable old = classDao.getClassByCid(cla.getCid());
		byte right[] = new byte[4];
		int flag = 1;
		for(int i=0;i<4;i++){
			if((tcRight & flag) != 0)
				right[i] = 1;
			flag = flag << 1;
		}
		//这里对2权限进行判断
		if(right[1]==0 && ( !old.getKind().equals(cla.getKind()) || !old.getStatus().equals(cla.getStatus()) || !old.getEnabled().equals(cla.getEnabled())))
			throw new TipException("您没有设置班级状态的权限!");
		//更新班级信息
		classDao.updateClass(cla);
	}

	
	/**
	 * 查询所有的班级的班号信息
	 */
	public List<ClassTable> getAllClasses(){
		return classDao.getAllClasses();
	}
	
	@Override
	public void deleteStudent(Integer sid, Integer tcRight) throws Exception {
		if(!BinaryUtils.manageStu(tcRight))
			throw new TipException("您没有管理此班级的权限");
		classDao.deleteStudent(sid);//会级联删除ClassStudentTable表中的记录
	}

	//获取学院ceID拥有的班级数量
	public Integer gainClassCountByCeid(Integer ceId){
		Integer count = 0;
        count = classDao.gainClassCountByCeid(ceId);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.ClassManager#getClassFromClassTeacherCourse(java.lang.Integer)
	 */
	@Override
	public List<ClassTable> getClassFromClassTeacherCourse(Integer tid) {
		return classDao.getClassFromClassTeacherCourse(tid);
	}
}
