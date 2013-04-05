package com.paradigmcreatives.rajiworld.entities;

import java.io.Serializable;

public class QuestionsAnswers implements Serializable{
	private int quserid;
	private String title;
	private String question;
	private String qusername;

	private int qid;

	public QuestionsAnswers() {

	}

	public QuestionsAnswers(int qid, int quserid, String title,
			String question, String qusername) {
		super();
		this.qid = qid;
		this.quserid = quserid;
		this.title = title;
		this.question = question;
		this.qusername = qusername;

	}

	/**
	 * @return quserid
	 */
	public int getQuserid() {
		return quserid;
	}

	/**
	 * @param quserid
	 *            the quserid to set
	 */
	public void setQuserid(int quserid) {
		this.quserid = quserid;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return qusername
	 */
	public String getQusername() {
		return qusername;
	}

	/**
	 * @param qusername
	 *            the qusername to set
	 */
	public void setQusername(String qusername) {
		this.qusername = qusername;
	}

	/**
	 * @return qid
	 */
	public int getQid() {
		return qid;
	}

	/**
	 * @param qid
	 *            the qid to set
	 */

	public void setQid(int qid) {
		this.qid = qid;
	}

}
