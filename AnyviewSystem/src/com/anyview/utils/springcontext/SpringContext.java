package com.anyview.utils.springcontext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;

import org.springframework.context.ApplicationContext;
/**
 * 
* @ClassName: SpringContext
* @Description: 动态获取IoC容器中管理的Bean
* @author xhn
* @date 2012-10-20 下午09:03:10
*
 */
public class SpringContext implements ApplicationContextAware {

	
	protected static ApplicationContext context;

	@Override
	 public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
