package com.anyview.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class ReadXml
{
    public static Element getRoot(String filePath) throws Exception{
	Element xmlRoot = null;
	try{
	        File file = new File(filePath);
	        FileInputStream fis = new FileInputStream(file);
		SAXReader read = new SAXReader();//解析XML为Document对象
		Document doc = read.read(fis);
		xmlRoot=doc.getRootElement();
	}catch(Exception e){
		e.printStackTrace();
	}
	return xmlRoot;
    }
    @SuppressWarnings("unchecked")
    public List<String> getPointByChapter(String chapter,String realpath)
    {
	List<String> list = new ArrayList<String>();
	try
	{
	    Element xmlRoot = this.getRoot(realpath);
            Element chapter1 = xmlRoot.element(chapter);
	    if(chapter1 != null)
	    {
		List<Element> sortName = chapter1.elements();
		if(sortName != null)
		{
		    for(Iterator<Element> iter = sortName.iterator();iter.hasNext();)
		    {
			String[] array = iter.next().getText().split("\\$");
			for(int i=0;i<array.length;i++)
			{
			    list.add(array[i]);
			}
		    }
		}
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return list;
    }
    @SuppressWarnings("unchecked")
    public int getChapterCount(String realpath)
    {
	int chaptercount = 0;
	try
	{
	    Element xmlRoot = this.getRoot(realpath);
	    List<Element> chapter = xmlRoot.elements();
	    chaptercount = chapter.size();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return chaptercount;
    }
}
