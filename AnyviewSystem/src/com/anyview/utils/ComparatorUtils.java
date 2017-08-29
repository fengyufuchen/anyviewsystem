package com.anyview.utils;

import java.util.Comparator;

import com.anyview.entities.SchemeContentVO;

/**
 * 比较器工具类
* @ClassName: ComparatorUtils 
* @author 何凡 <piaobo749@qq.com>
* @date 2016年4月18日 下午8:28:32 
*
 */
public class ComparatorUtils {

	/**
	 * SchemeContentVO比较器
	 * 根据vchapName排序
	* @ClassName: SchemeContentCompare 
	* @author 何凡 <piaobo749@qq.com>
	* @date 2016年4月18日 下午8:31:03 
	*
	 */
	static class SchemeContentVOCompare implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			SchemeContentVO sc1 = (SchemeContentVO) arg0;
			SchemeContentVO sc2 = (SchemeContentVO) arg1;
			Integer vn1 = Integer.valueOf(sc1.getSchemeContent().getVchapName());
			Integer vn2 = Integer.valueOf(sc2.getSchemeContent().getVchapName());
			return vn1 > vn2 ? 1 : -1;
		}
		
	}
	
}
