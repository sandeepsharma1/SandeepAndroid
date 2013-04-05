package com.paradigmcreatives.rajiworld.entities;

import java.io.Serializable;

public class Answers implements Serializable{
	
	private String answer;
	private int quesId;
	private int ansid;
	private String ausername;
	public Answers(){
		
	}
	public Answers(String answer, int quesId,String ausername,int ansid) {
		super();
		this.answer = answer;
		this.quesId = quesId;
		this.ausername = ausername;
		this.ansid = ansid;
	}
	/**
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return quesId
	 */
	public int getQuesId() {
		return quesId;
	}
	/**
	 * @param quesId
	 *            the quesId to set
	 */
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public String getAusername() {
		return ausername;
	}
	public void setAusername(String ausername) {
		this.ausername = ausername;
	}
	public int getAnsid() {
		return ansid;
	}
	public void setAnsid(int ansid) {
		this.ansid = ansid;
	}

}
