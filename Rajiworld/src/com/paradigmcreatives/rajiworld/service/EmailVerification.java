package com.paradigmcreatives.rajiworld.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.Entity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@WebService()
@Entity
@Path("/emailverification")
public class EmailVerification {

	@GET
	@Path("/verify/{emailid}")
	@WebMethod(operationName = "verify/{emilid}")
	public String verify(@PathParam("emailid") String emailid) {
		/*
		 * ConnectionPool pool = ConnectionPool.getInstance(); Connection
		 * connection = pool.getConnection(); //PreparedStatement ps = null;
		 * //PreparedStatement ps1 = null;
		 */String useremail = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/rajiworld", "root", "root");

			String query = "select emailAddress from users where emailAddress = \'"
					+ emailid + "\'";
			System.out.println("emailid...." + emailid);
			System.out.println("query..." + query);
			PreparedStatement st = con.prepareStatement(query);
			rs = st.executeQuery();

			/*
			 * PreparedStatement ps = connection.prepareStatement(query); rs =
			 * ps.executeQuery();
			 */
			while (rs.next()) {
				useremail = rs.getString("emailAddress");
			}
			if (useremail.equals(null)) {
				System.out.println("email verification failed");
				return "register";
			} else {
				if (useremail.equals(emailid)) {
					ResultSet res = null;
					boolean status = false;

					String queryString = "select activation from users"
							+ " where emailAddress = \'" + emailid + "\'";
					PreparedStatement pst = con.prepareStatement(queryString);
					res = pst.executeQuery();
					while (res.next()) {
						status = res.getBoolean("activation");

					}
					if (status == true) {
						return "your email already verified";
					}

					String sqlquery = "update users set activation = true "
							+ " where emailAddress = \'" + emailid + "\'";

					PreparedStatement ps1 = con.prepareStatement(sqlquery);
					ps1.executeUpdate();
					System.out.println("email verified");
				}
				return "welcome";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return null;
		}

	}

}
