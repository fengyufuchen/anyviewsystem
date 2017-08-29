package com.anyview.utils;

/**
 * 路径操作的工具类
* @ClassName: PathTool 
* @author 何凡 <piaobo749@qq.com>
* @date 2016年4月10日 下午8:14:40 
*
 */
public class PathTool {
	
	/**
	 * 返回应用的根目录的绝对路径
	 * 
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月10日 下午8:15:11
	 */
	public static String getWebRootPath(){
		return Thread.currentThread().getContextClassLoader().getResource(".").getPath();
	}

}
