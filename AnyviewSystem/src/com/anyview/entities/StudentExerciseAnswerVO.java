/**   
* @Title: StudentExerciseAnswerVO.java 
* @Package com.anyview.entities 
* @author 何凡 <piaobo749@qq.com>   
* @date 2015年12月8日 上午12:26:55 
* @version V1.0   
*/
package com.anyview.entities;

import java.io.Serializable;

/** 
 * 封装题目与对应答案
 * @ClassName: StudentExerciseAnswerVO 
 * @author 何凡 <piaobo749@qq.com>
 * @date 2015年12月8日 上午12:26:55 
 *  
 */
public class StudentExerciseAnswerVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ProblemTable problem;//题目
	private ExerciseTable exercise;//答案
//	private SchemeContentTable schemeContent;//作业表题目
	
	public ProblemTable getProblem() {
		return problem;
	}
	public void setProblem(ProblemTable problem) {
		this.problem = problem;
	}
	public ExerciseTable getExercise() {
		return exercise;
	}
	public void setExercise(ExerciseTable exercise) {
		this.exercise = exercise;
	}
	
//	public SchemeContentTable getSchemeContent() {
//		return schemeContent;
//	}
//	public void setSchemeContent(SchemeContentTable schemeContent) {
//		this.schemeContent = schemeContent;
//	}
}
