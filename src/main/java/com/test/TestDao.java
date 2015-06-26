package com.test;

import gov.nih.nci.cadsr.cadsrpasswordchange.core.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.test.DAO;

//import gov.nih.nci.cadsr.cadsrpasswordchange.core.UserSecurityQuestion;

//import com.test.oracle.dao.oracle.UserSecurityQuestionDaoImpl;
//import com.test.oracle.dto.UserSecurityQuestion;
import com.test.mysql.dao.mysql.UserSecurityQuestionDaoImpl;
import com.test.mysql.dto.UserSecurityQuestion;


public class TestDao {

//	private String dbtype = "oracle";  AbstractDao dao;
	private String dbtype = "mysql";	DAO dao;
//	private String adminUser = "cadsrpasswordchange"; private String adminPassword = "cadsrpasswordchange";
	 private String adminUser = "root"; private String adminPassword = "root";
	private String guuidUserId = "tanj";
	private Connection conn;

	public void connect() {
		ConnectionUtil cu = new ConnectionUtil();
		cu.setUserName(adminUser);
		cu.setPassword(adminPassword);
		if ("oracle".equals(dbtype)) {
			cu.setDbms("oracle");
			cu.setDbName("DSRDEV");
			cu.setServerName("137.187.181.4");
			cu.setPortNumber(1551);
		} else {
			cu.setDbms("mysql");
			cu.setDbName("SBR");
			cu.setServerName("localhost");
			cu.setPortNumber(3306);
		}
		try {
			conn = cu.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    System.out.println("Connected to database " + dbtype);
//		dao = new DAO(conn);
		dao = new DAO(conn);
		
	}

	public void disconnect() {
		try {
			conn.close();
		    System.out.println("Disconnected from database");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Before
	public void setUp() {
		connect();
	}

	@After
	public void tearDown() {
		disconnect();
	}

	@Test
	public void testValidUser() {
		boolean retVal = dao.checkValidUser(guuidUserId);
		Assert.assertTrue(retVal);
	}

	@Test
	public void testInValidUser() {
		boolean retVal = dao.checkValidUser("whoeverthatdoesnotexist261t262");
		Assert.assertFalse(retVal);
	}

	@Test
	public void testUserSecurityQuestionInsert() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		qna.setUaName(guuidUserId);
		qna.setQuestion1("question 1 from dao");
		qna.setAnswer1("answer for question 1 of dao");
		qna.setQuestion2("question 2 from dao");
		qna.setAnswer2("answer for question 2 of dao");
		qna.setQuestion3("question 3 from dao");
		qna.setAnswer3("answer for question 3 of dao");
		try {
			dao.insert(qna);
			showUserSecurityQuestionList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private void showUserSecurityQuestionList() {
		UserSecurityQuestion[] results = dao.findAll();
		if (results.length > 0) {
			for (UserSecurityQuestion e : results) {
				System.out.println("User [" + e.getUaName() + "] updated ["
						+ new Date() + "] question [" + e.getQuestion1()
						+ "] answer [" + e.getAnswer1() + "]");
			}
		} else {
			System.out.println("no question");
		}
	}

	@Test
	public void testUserSecurityQuestionUpdate() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		try {
			qna = dao.findByUaName(guuidUserId);
			qna.setQuestion2("question 2 from dao *updated*");
			qna.setAnswer2("answer for question 2 of dao *updated*");
			//dao.update(qna.getId(), qna);
			dao.update(qna.getUaName(), qna);
			showUserSecurityQuestionList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void testUserQuestionsExists() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		boolean retVal = false;
		try {
			qna = dao.findByUaName(guuidUserId);
			if(qna != null) retVal = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(retVal);
	}

	@Test
	public void testUserSecurityQuestionDelete() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		try {
			qna = dao.findByUaName(guuidUserId);
			//dao.deleteByPrimaryKey(qna.getId());
			dao.deleteByPrimaryKey(qna.getUaName());
			showUserSecurityQuestionList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void testUserQuestionsNotExists() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		boolean retVal = false;
		try {
			qna = dao.findByUaName(guuidUserId);
			if(qna != null) retVal = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertFalse(retVal);
	}
}
