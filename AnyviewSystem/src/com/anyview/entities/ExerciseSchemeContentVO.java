package com.anyview.entities;
/**
 * 封装作业表中的题目信息和对应的答案
* @ClassName: ExerciseSchemeContentVO 
* @author 何凡 <piaobo749@qq.com>
* @date 2016年8月13日 下午4:59:58 
*
 */
public class ExerciseSchemeContentVO {

	private ExerciseTable exercise;
    private SchemeContentTable schemeContent;
    private SchemeContentVO sv;
    
    public ExerciseSchemeContentVO(){
    	
    }
    
    public ExerciseSchemeContentVO(ExerciseTable exercise, SchemeContentTable schemeContent){
    	this.exercise = exercise;
    	this.schemeContent = schemeContent;
    }

	public ExerciseTable getExercise() {
		return exercise;
	}

	public void setExercise(ExerciseTable exercise) {
		this.exercise = exercise;
	}

	public SchemeContentTable getSchemeContent() {
		return schemeContent;
	}

	public void setSchemeContent(SchemeContentTable schemeContent) {
		this.schemeContent = schemeContent;
	}  
	public SchemeContentVO getSv() {
		return sv;
	}

	public void setSv(SchemeContentVO sv) {
		this.sv = sv;
	}
    
    
}
