package com.anyview.service.function;

import java.util.Map;

import com.anyview.entities.ClassCourseSchemeTable;
import com.anyview.entities.Pagination;

public interface ClassCourseSchemeManager {
	public Pagination<ClassCourseSchemeTable> getCombinedGradePage(Map param);
}
