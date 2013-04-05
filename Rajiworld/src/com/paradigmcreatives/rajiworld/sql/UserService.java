package com.paradigmcreatives.rajiworld.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.paradigmcreatives.rajiworld.entities.Answers;
import com.paradigmcreatives.rajiworld.entities.Login;
import com.paradigmcreatives.rajiworld.entities.QuestionsAnswers;
/**
 * 
 * @author Rajeswari
 *
 */
public class UserService {

	public static HttpSession session;
	private Login login = null;
	private QuestionsAnswers qa = null;

	public UserService() {

	}
/**
 * 
 * @param login
 * @return 
 */
	
	public int registerUser(Login login) {
		// TODO Auto-generated method stub

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		int x=0;
		String query = "INSERT INTO users "
				+ "(emailAddress,firstName,lastname,username,password,activation) VALUES "
				+ "(?,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, login.getEmailAddress());
			ps.setString(2, login.getFirstName());
			ps.setString(3, login.getLastName());
			ps.setString(4, login.getUsername());
			ps.setString(5, login.getPassword());
			ps.setBoolean(6, login.isActivation());
			x=  ps.executeUpdate();
		}

		catch (SQLException e) {
			e.printStackTrace();

		} finally {

			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return x;

		
	}
/**
 * 
 * @param qa
 * @param log
 * @param currentUser
 * @return
 */
	public String postquestion(QuestionsAnswers qa, Login log,
			String currentUser) {
		// TODO Auto-generated method stub
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		String query = "INSERT INTO questionsanswers "
				+ "(title,question,qusername) VALUES " + "(?,?,?)";
		try {
			ps = connection.prepareStatement(query);
			// ps.setInt(1, log.getUserId());
			// System.out.println("uid"+session.getAttribute("username"));
			ps.setString(1, qa.getTitle());
			System.out.println("title: " + qa.getTitle());
			ps.setString(2, qa.getQuestion());
			ps.setString(3, currentUser);
			// ps.setString(4, log.getUsername());

			ps.executeUpdate();
		}

		catch (SQLException e) {
			e.printStackTrace();

		} finally {

			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return query;

	}

	public Collection<QuestionsAnswers> getAllTitles() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		Subject subject = SecurityUtils.getSubject();
		Collection<QuestionsAnswers> devList = new ArrayList<QuestionsAnswers>();

		String query = "select qid,quserid,title,question,qusername from questionsanswers ";
		try {
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				QuestionsAnswers qa = new QuestionsAnswers();

				qa.setQid(rs.getInt("qid"));
				qa.setQuserid(rs.getInt("quserid"));
				qa.setTitle(rs.getString("title"));
				// System.out.println("title..."+rs.getString("title"));
				qa.setQuestion(rs.getString("question"));
				// System.out.println("question...."+rs.getString("question"));
				// System.out.println("sessions........"+(String)
				// session.getAttribute("username"));
				// qa.setQusername((String) session.getAttribute("username"));
				qa.setQusername(rs.getString("qusername"));
				/*
				 * qa.setAnswer(rs.getString("answer"));
				 * qa.setAusername(rs.getString("ausername"));
				 * qa.setAuserid(rs.getInt("auserid"));
				 */

				devList.add(qa);
			}

			return devList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}

	}

	public String postAnswer(Answers ans, int activeid,
			String currentUser) {
		// TODO Auto-generated method stub
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		String answer = ans.getAnswer();
		System.out.println("answer........." + answer);

		System.out.println("question id ..." + activeid);
		// String query = "update answers set answer = \'" + answer +
		// "\'"+" where qid = " + activeid;
		String query = "INSERT INTO answers "
				+ "(answer,qid,ausername) VALUES " + "(?,?,?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, answer);
			ps.setInt(2, activeid);
			ps.setString(3, currentUser);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return query;

	}

	public Collection<Answers> getAllAnswers(int activeid) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String answer = null;

		Subject subject = SecurityUtils.getSubject();
		Collection<Answers> devList = new ArrayList<Answers>();
		// ArrayList<String> arr =new ArrayList<String>();

		String query = "select ansid,answer,ausername from answers where qid = "
				+ activeid;
		try {
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				Answers qa = new Answers();

				 qa.setAnsid(rs.getInt("ansid"));
				qa.setAnswer(rs.getString("answer"));
				qa.setAusername(rs.getString("ausername"));

				devList.add(qa);

				// devList.add(qa);
			}

			return devList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public boolean verifyActivation(String toMailID) {

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		boolean status = false;
		ResultSet rs = null;

		String query = "select activation from users where emailAddress = \'"
				+ toMailID + "\'";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Login log = new Login();
				status = rs.getBoolean("activation");
			}
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}

	}

	public boolean veryActivationStatus(String username) {
		// TODO Auto-generated method stub
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		boolean status = false;
		ResultSet rs = null;

		String query = "select activation from users where username= \'"
				+ username + "\'";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Login log = new Login();
				status = rs.getBoolean("activation");
			}
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public String verifylogincredentials(String username, String password) {
		// TODO Auto-generated method stub
		/*
		 * FacesContext context = FacesContext.getCurrentInstance(); session =
		 * (HttpSession)context.getExternalContext().getSession(true);
		 * session.setAttribute("username", username);
		 */

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		String uname = null;
		ResultSet rs = null;

		String query = "select username from users where username= \'"
				+ username + "\'" + " and password = \'" + password + "\'";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Login log = new Login();
				uname = rs.getString("username");
			}
			return uname;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public String checkuser(int activeid) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String answereduser = null;

		Subject subject = SecurityUtils.getSubject();
		// Collection<Answers> devList = new ArrayList<Answers>();
		// ArrayList<String> arr =new ArrayList<String>();

		String query = "select ausername from answers where qid = " + activeid;
		try {
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			while (rs.next()) {
				Answers qa = new Answers();

				answereduser = rs.getString("ausername");

				// devList.add(qa);
			}

			return answereduser;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public void editAnswer(Answers answer, String eans) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String name = "";

		String query = "update answers set answer =\'" + answer.getAnswer()
				+ "\'" + " where answer = " + eans;

		try {
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}

	}

}
