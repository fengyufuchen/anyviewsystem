package com.anyview.dao;

import java.util.List;

import com.anyview.entities.CCTable;
import com.anyview.entities.ClassTable;

public interface CCDao {
	
	public List<CCTable> getInfo(String tName);

}
