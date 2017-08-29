package com.anyview.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anyview.action.teacher.ProblemManageAction;
import com.anyview.entities.ChoiceOptionsVO;
import com.anyview.entities.FileMsgVO;
import com.anyview.entities.ProblemChapTable;
import com.anyview.entities.ProblemContentVO;
import com.anyview.entities.ProblemLibTable;
import com.anyview.entities.ProblemTable;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 读取excel并生成ProblemContentVO对象
 * 
 * @ClassName: ExcelToObject
 * @author 何凡 <piaobo749@qq.com>
 * @date 2016年4月11日 下午9:33:58
 *
 */
public class ExcelToObject {

	private static final Log log = LogFactory.getLog(ExcelToObject.class);

	/**
	 * 程序题最小列数 题目名称+难度+状态+访问级别+备注+提示+题目内容+主文件+用户文件+答案文件+头文件+题目文档
	 */
	public static int PROGRAM_MIN_COL = 14;
	
	/**
	 * 选择题最小列数 题目名称+难度+状态+访问级别+备注+提示+题目内容+最少1个选项
	 */
	public static int CHOOSE_MIN_COL = 8;
	/**
	 * 选择题最大列数 题目名称+难度+状态+访问级别+备注+提示+题目内容+最多10个选项
	 */
	public static int CHOOSE_MAX_COL = 17;
	/**
	 * 判断题列数 题目名称+难度+状态+访问级别+备注+提示+题目内容+答案
	 */
	public static int JUDGEMENT_COL = 8;
	/**
	 * 默认难度为0
	 */
	public static float DEFAULT_DEGREE = 0f;
	/**
	 * 默认状态为1（测试）
	 */
	public static int DEFAULT_STATUS = 1;
	/**
	 * 默认访问级别为0（私有）
	 */
	public static int DEFAULT_VISIT = 0;
	/**
	 * 默认选择题正确选项的前缀
	 */
	public static String DEFAULT_TRUE_PREFIX = "_TRUE_";

	/**
	 * 封装题目公共部分到ProblemTable的List中 统一第一列到第6列都相同，分别为： 第一列：题目名称 第二列：难度 第三列：状态
	 * 第四列：访问级别 第五列：备注 第六列：提示
	 * 
	 * @param readwb
	 * @param type
	 * @param chId
	 * @param lid
	 * @return
	 * @author 何凡 <piaobo749@qq.com>
	 * @date 2016年4月12日 下午10:04:00
	 */
	public static List<ProblemTable> BaseProblemToObject(Workbook readwb, Integer type, Integer chId, Integer lid)
			throws Exception {
		List<ProblemTable> list = new ArrayList<ProblemTable>();
		// 获取第一个sheet，仅支持一个sheet
		Sheet readsheet = readwb.getSheet(0);
		int rsRows = readsheet.getRows();
		// 从第一行开始读，忽略第0行
		for (int i = 1; i < rsRows; i++) {
			ProblemTable pro = new ProblemTable();
			// pname
			Cell cell0 = readsheet.getCell(0, i);
			String pname = cell0.getContents().trim();
			if (pname.length() <= 0)
				throw new TipException("第" + i + "行，第1列，题目名称不能为空");
			if (pname.length() > 20)
				throw new TipException("第" + i + "行，第1列，题目名不能超过20个字符");
			pro.setPname(pname);

			// degree
			Cell cell1 = readsheet.getCell(1, i);
			String degreeStr = cell1.getContents().trim();
			if (degreeStr.length() <= 0)
				pro.setDegree(DEFAULT_DEGREE);
			else {
				if (cell1.getType() != CellType.NUMBER)
					throw new TipException("第" + i + "行，第2列，难度只能为数字");
				pro.setDegree(Float.valueOf(degreeStr));
			}

			// status
			Cell cell2 = readsheet.getCell(2, i);
			String statusStr = cell2.getContents().trim();
			if (statusStr.length() <= 0)
				pro.setStatus(DEFAULT_STATUS);
			else {
				if (cell2.getType() != CellType.NUMBER)
					throw new TipException("第" + i + "行，第3列，状态只能是0或者1或者2");
				if (!Arrays.asList((new String[] { "0", "1", "2" })).contains(statusStr))
					throw new TipException("第" + i + "行，第3列，状态只能是0或者1或者2");
				pro.setStatus(Integer.valueOf(statusStr));
			}
			// visit
			Cell cell3 = readsheet.getCell(3, i);
			String visitStr = cell3.getContents().trim();
			if (visitStr.length() <= 0)
				pro.setStatus(DEFAULT_VISIT);
			else {
				if (cell3.getType() != CellType.NUMBER)
					throw new TipException("第" + i + "行，第4列，访问级别只能是0或者1");
				if (!Arrays.asList((new String[] { "0", "1" })).contains(visitStr))
					throw new TipException("第" + i + "行，第4列，访问级别只能是0或者1");
				pro.setVisit(Integer.valueOf(visitStr));
			}
			// pmemo
			Cell cell4 = readsheet.getCell(4, i);
			pro.setPmemo(cell4.getContents().trim());
			// ptip
			Cell cell5 = readsheet.getCell(5, i);
			pro.setPtip(cell5.getContents().trim());
			pro.setKind(type);
			pro.setCache("");
			pro.setCacheSync(0);
			pro.setProblemChap(new ProblemChapTable(chId, new ProblemLibTable(lid)));
			pro.setCreateTime(new Timestamp(System.currentTimeMillis()));
			list.add(pro);
		}
		return list;
	}
	
	/**
	 * 将Excel内容转换为程序题
	 * 
	 * @param readwb
	 *            只读Workbook
	 * @author 杨坚新
	 * @throws Exception
	 * @date 2017年2月28日 
	 */
	public static List<ProblemTable> ProgramToObject(Workbook readwb, Integer type, Integer chId, Integer lid)
			throws Exception {
		log.info("解析选程序题Excel开始,chId:" + chId);
		List<ProblemTable> list = BaseProblemToObject(readwb, type, chId, lid);
		// 获取第一个sheet，仅支持一个sheet
		Sheet readsheet = readwb.getSheet(0);
		int rsRows = readsheet.getRows();
		int rsColumns = readsheet.getColumns();
		if (rsColumns < PROGRAM_MIN_COL)
			throw new TipException("程序题的列数至少为14列");
		// 从第一行开始读，忽略第0行
		for (int i = 1; i < rsRows; i++) {
			ProblemTable pro = list.get(i - 1);
			// 先封装到ProblemContentVO然后转换为XML
			// 第7列为题干，之后为选项
			ProblemContentVO pcv = new ProblemContentVO();
			//判断必填选项是否有为空的情况
			for(int j=6;j<=13;j++){
				if (readsheet.getCell(j, i).getContents().trim().length() <= 0)
					throw new TipException("第" + i + "行，第"+j+"列，主文件不能为空");
				System.out.println("第"+i+"行"+"第"+j+"列："+ readsheet.getCell(j, i).getContents().trim());
			}
			String programContent = readsheet.getCell(6, i).getContents().trim();	//程序题内容
			String document = readsheet.getCell(7, i).getContents().trim();	//题目文档
			String mainFileName = readsheet.getCell(8, i).getContents().trim();	//主文件名
			String mainFileContent = readsheet.getCell(9, i).getContents().trim();	//主文件内容
			String userFileName = readsheet.getCell(10, i).getContents().trim();	//用户文件名
			String userFileContent = readsheet.getCell(11, i).getContents().trim();	//用户文件内容
			String answerFileName = readsheet.getCell(12, i).getContents().trim();	//答案文件名
			String answerFileContent = readsheet.getCell(13, i).getContents().trim();	//答案文件内容
			pcv.setMainFile(new FileMsgVO(mainFileName, mainFileContent));
			pcv.setUserFile(new FileMsgVO(userFileName, userFileContent));
			pcv.setProgramContent(programContent);
			pcv.setDocument(document);
			pcv.setAnswerFile(new FileMsgVO(answerFileName, answerFileContent));
			String firstHeadFilesName = readsheet.getCell(14, i).getContents().trim();	//首个头文件名
			String firstHeadFilesContent=readsheet.getCell(15, i).getContents().trim();	//首个头文件内容
			List<FileMsgVO> headFiles = new ArrayList<FileMsgVO>();
			if (firstHeadFilesName.length() <= 0)
				throw new TipException("第" + i + "行，第14列，第一个选项不能为空");
			if (firstHeadFilesContent.length() <= 0)
				throw new TipException("第" + i + "行，第15列，第一个选项不能为空");
			headFiles.add(new FileMsgVO(firstHeadFilesName,firstHeadFilesContent));
			for (int j = 16; j < rsColumns; j+=2) {
				String headFilesName = readsheet.getCell(j, i).getContents().trim();
				String headFilesContent = readsheet.getCell(j+1, i).getContents().trim();
				if (headFilesName.length() <= 0&&headFilesContent.length()<0)	//如果最后一个头文件名和文件内容均为空，则结束
					break;
				headFiles.add(new FileMsgVO(headFilesName,headFilesContent));
			}
			pcv.setHeadFiles(headFiles);
			pro.setPcontent(XmlUtil.createProgramXML(pcv).asXML());
		}
		log.info("解析选择题Excel完成，共" + list.size() + "道题");
		return list;

	}

	/**
	 * 将Excel内容转换为选择题
	 * 
	 * @param readwb
	 *            只读Workbook
	 * @author 何凡 <piaobo749@qq.com>
	 * @throws Exception
	 * @date 2016年4月11日 下午9:35:34
	 */
	public static List<ProblemTable> ChooseToObject(Workbook readwb, Integer type, Integer chId, Integer lid)
			throws Exception {
		log.info("解析选择题Excel开始,chId:" + chId);
		List<ProblemTable> list = BaseProblemToObject(readwb, type, chId, lid);
		// 获取第一个sheet，仅支持一个sheet
		Sheet readsheet = readwb.getSheet(0);
		int rsRows = readsheet.getRows();
		int rsColumns = readsheet.getColumns();
		if (rsColumns > CHOOSE_MAX_COL || rsColumns < CHOOSE_MIN_COL)
			throw new TipException("选择题的列数应该在8列和17列之间");
		// 从第一行开始读，忽略第0行
		for (int i = 1; i < rsRows; i++) {
			ProblemTable pro = list.get(i - 1);
			// 先封装到ProblemContentVO然后转换为XML
			// 第7列为题干，之后为选项
			ProblemContentVO pcv = new ProblemContentVO();
			Cell cell6 = readsheet.getCell(6, i);
			String contentStr = cell6.getContents().trim();
			if (contentStr.length() <= 0)
				throw new TipException("第" + i + "行，第7列，题干不能为空");
			pcv.setChoiceContent(contentStr);
			Cell cell7 = readsheet.getCell(7, i);
			Integer sequence = 1;// 选项的序号，1,2,3,...
			String firstOptStr = cell7.getContents().trim();
			List<ChoiceOptionsVO> opts = new ArrayList<ChoiceOptionsVO>();
			if (firstOptStr.length() <= 0)
				throw new TipException("第" + i + "行，第8列，第一个选项不能为空");
			opts.add(new ChoiceOptionsVO((sequence++).toString(), firstOptStr,
					firstOptStr.startsWith(DEFAULT_TRUE_PREFIX)));
			for (int j = 8; j < rsColumns; j++) {
				Cell c = readsheet.getCell(j, i);
				String oo = c.getContents().trim();
				if (oo.length() <= 0)
					break;
				opts.add(new ChoiceOptionsVO((sequence++).toString(), oo, oo.startsWith(DEFAULT_TRUE_PREFIX)));
			}
			pcv.setOptions(opts);
			pro.setPcontent(XmlUtil.createChoiceXML(pcv).asXML());
		}
		log.info("解析选择题Excel完成，共" + list.size() + "道题");
		return list;
	}

	public static List<ProblemTable> JudgmentToObject(Workbook readwb, Integer type, Integer chId, Integer lid)
			throws Exception {
		log.info("解析判断题Excel开始,chId:" + chId);
		List<ProblemTable> list = BaseProblemToObject(readwb, type, chId, lid);
		// 获取第一个sheet，仅支持一个sheet
		Sheet readsheet = readwb.getSheet(0);
		int rsRows = readsheet.getRows();
		int rsColumns = readsheet.getColumns();
		if (rsColumns != JUDGEMENT_COL)
			throw new TipException("判断题应该有8列");
		// 从第一行开始读，忽略第0行
		for (int i = 1; i < rsRows; i++) {
			ProblemTable pro = list.get(i - 1);
			// 先封装到ProblemContentVO然后转换为XML
			// 第7列为题干
			ProblemContentVO pcv = new ProblemContentVO();
			Cell cell6 = readsheet.getCell(6, i);
			String contentStr = cell6.getContents().trim();
			if (contentStr.length() <= 0)
				throw new TipException("第" + i + "行，第7列，题干不能为空");
			pcv.setJudgmentContent(contentStr);
			Cell cell7 = readsheet.getCell(7, i);
			String ansStr = cell7.getContents().trim();
			if (ansStr.length() <= 0)
				throw new TipException("第" + i + "行，第8列，答案不能为空");
			if ("true".equalsIgnoreCase(ansStr))
				pcv.setIsRight(true);
			else if ("false".equalsIgnoreCase(ansStr))
				pcv.setIsRight(false);
			else
				throw new TipException("第" + i + "行，第8列，答案只能为true或者false");
			pro.setPcontent(XmlUtil.createJudgmentXML(pcv).asXML());
		}
		log.info("解析判断题Excel完成，共" + list.size() + "道题");
		return list;
	}
}
