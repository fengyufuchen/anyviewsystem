package com.anyview.service.function.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anyview.dao.UniversityDao;
import com.anyview.dao.impl.BaseDaoImpl;
import com.anyview.entities.Pagination;
import com.anyview.entities.ProvinceTable;
import com.anyview.entities.ChinaUniversityTable;
import com.anyview.entities.CollegeTable;
import com.anyview.entities.UniversityTable;
import com.anyview.service.function.UniversityManager;
import com.anyview.utils.BinaryUtils;
import com.anyview.utils.TipException;

@Service
public class UniversityManagerImpl implements UniversityManager {
	
	@Autowired
	private UniversityDao universityDao;
	//获取所有学校
	@Override
	public List<UniversityTable> gainAllUniversities() {
		return universityDao.gainAllUniversities();
	}
	
	//根据学校的ID删除学校信息 
	public boolean deleteUniversity(int unId){
		return universityDao.deleteUniversity(unId);
	}
	
	//根据学校的ID获取学校的信息
	public UniversityTable gainUniversityByUnid(int unId){
		return universityDao.getUniversityById(unId);
	}
	//获取学校管理页面数据
	public Pagination<UniversityTable> getUniversityPage(Map param){
		Pagination<UniversityTable> page = new Pagination<UniversityTable>();
		List<UniversityTable> univer = universityDao.getUniversitysPage(param);
		page.setContent(univer);
		page.setCurrentPage((Integer)param.get("pageNum"));
		page.setNumPerPage((Integer)param.get("pageSize"));
		page.setTotalCount(universityDao.getUniversityCount(univer));
		page.calcutePage();
		return page;
	}
	
	//判断学校是否已经存在于数据库当中
	public boolean isUniversityExist(String unName){
		List<UniversityTable> universityList = universityDao.gainAllUniversities();
		for(UniversityTable u : universityList){
			if(u.getUnName().equals(unName))
				return true;
		}
		return false;
	}
	
	//添加学校信息
	public boolean saveUniversity(UniversityTable univer){
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			univer.setCreateTime(createTime);
			if(univer.getAttr()==0){//使用本服务器不保存ip和端口
				univer.setIp(null);
				univer.setPort(null);
			}
			return universityDao.saveUniversity(univer);
		}
	
	//修改学校信息
	public boolean updateUniversity(UniversityTable univer){	
		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		univer.setUpdateTime(updateTime);
		if(univer.getAttr()==0){//使用本服务器不保存ip和端口
			univer.setIp(null);
			univer.setPort(null);
		}
		return universityDao.updateUniversity(univer);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.UniversityManager#gainAllEnabledUniversities()
	 */
	@Override
	public List<UniversityTable> gainAllEnabledUniversities() {
		return universityDao.gainAllEnabledUniversities();
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.UniversityManager#searchUniversity(java.lang.String)
	 */
	@Override
	public List<UniversityTable> searchUniversity(String param) {
		return universityDao.searchUniversity(param);
	}

	/* (non-Javadoc)
	 * @see com.anyview.service.function.UniversityManager#searchUniversityByName(java.lang.String)
	 */
	@Override
	public List<UniversityTable> searchUniversityByName(String param) {
		return universityDao.searchUniversityByName(param);
	}
}
