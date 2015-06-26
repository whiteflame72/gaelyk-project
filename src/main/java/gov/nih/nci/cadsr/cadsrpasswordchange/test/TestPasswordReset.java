package gov.nih.nci.cadsr.cadsrpasswordchange.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import gov.nih.nci.cadsr.cadsrpasswordchange.core.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPasswordReset {

	// private static Connection connection = null;
	private static DataSource datasource = null;
	private static DAO dao;
	public static String ADMIN_ID = "root";
	public static String ADMIN_PASSWORD = "root";
	public static String USER_ID = "TEST111";	//this user has to exist, otherwise test will fail
//	private static OracleObfuscation x;
//	static {
//		try {
//			x = new OracleObfuscation("$_12345&");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}
	
	// @Test
	public void testBadUserNameInLogin() {
		String username = "@hongj"; // bad
		String password = "test123";
		String errorMessage = PasswordChangeHelper.validateLogin(username,
				password);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testBadPasswordInLogin() {
		String username = "chongj";
		String password = "@7esT123"; // bad
		String errorMessage = PasswordChangeHelper.validateLogin(username,
				password);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testBadUserIdInChangePassword() {
		String username = "@hongj"; // bad
		String oldPassword = "test123";
		String newPassword = "test456";
		String newPassword2 = "test456";
		String sessionUsername = username;
		String httpRequestNewPassword2 = newPassword2;
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testBadOldPasswordInChangePassword() {
		String username = "chongj";
		String oldPassword = "@7esT123"; // bad
		String newPassword = "test456";
		String newPassword2 = "test456";
		String sessionUsername = username;
		String httpRequestNewPassword2 = newPassword2;
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testBadNewPasswordInChangePassword() {
		String username = "chongj";
		String oldPassword = "test123";
		String newPassword = "@7esT123"; // bad
		String newPassword2 = "@7esT123";
		String sessionUsername = username;
		String httpRequestNewPassword2 = newPassword2;
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testNewPasswordNotMatchingInChangePassword() {
		String username = "chongj";
		String oldPassword = "test123";
		String newPassword = "test456";
		String newPassword2 = "test123"; // bad
		String sessionUsername = username;
		String httpRequestNewPassword2 = newPassword2;
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testLoginIdNotMatchingInChangePassword() {
		String username = "chongj";
		String oldPassword = "test123";
		String newPassword = "test456";
		String newPassword2 = "test456";
		String sessionUsername = username + "xs"; // bad
		String httpRequestNewPassword2 = newPassword2;
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	// @Test
	public void testNewRequestedPasswordNotMatchingInChangePassword() {
		String username = "chongj";
		String oldPassword = "test123";
		String newPassword = "test456";
		String newPassword2 = "test456";
		String sessionUsername = username;
		String httpRequestNewPassword2 = newPassword2 + "yy"; // bad
		String errorMessage = PasswordChangeHelper.validateChangePassword(
				username, oldPassword, newPassword, newPassword2,
				sessionUsername, httpRequestNewPassword2);
		assertNotNull(errorMessage, errorMessage);
	}

	public static Connection getConnection(String username, String password)
			throws Exception {
		String dbtype = "mysql";
		String dbserver = "localhost";
		String dbname = "SBR";
		// String username = "root";
		// String password = "root";
		int port = 3306;
		ConnectionUtil cu = new ConnectionUtil();
		cu.setUserName(username);
		cu.setPassword(password);
		cu.setDbms(dbtype);
		cu.setDbName(dbname);
		cu.setServerName(dbserver);
		cu.setPortNumber(port);
		Connection conn = cu.getConnection();
		return conn;
	}

	// @Test
	// public void testPasswordReset() {
	// String username = "GUEST";
	// String newPassword = "GUEST";
	// Result returned = null;
	// try {
	// Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
	// dao = new DAO(conn);
	// returned = dao.resetPassword(username, newPassword);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// Result expected = new Result(ResultCode.PASSWORD_CHANGED);
	// assertEquals(expected.getResultCode(), returned.getResultCode());
	// }

	// @Test
	public void testPasswordChange() {
		String username = "GUEST";
		String oldPassword = "GUEST";
		String newPassword = "test@Lie777";
		Result returned = null;
		try {
			Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			returned = dao.changePassword(username, oldPassword, newPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Result expected = new Result(ResultCode.PASSWORD_CHANGED);
		assertNotSame(expected.getResultCode(), returned.getResultCode());
	}

	public byte[] toBytes(InputStream in) throws Exception {
		OutputStream out = null;
		// Read bytes, decrypt, and write them out.
		int bufferSize = 2048;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[bufferSize];

		while ((nRead = in.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();		
	}

	//@Test
	public void testEncryptionDecryptionWithOracleFunctions() {
		//TBD - this approach is currently not used, we had issue with "java.sql.SQLException: ORA-28232: invalid input length for obfuscation toolkit
		//ORA-06512: at "SYS.DBMS_OBFUSCATION_TOOLKIT_FFI", line 84
		//ORA-06512: at "SYS.DBMS_OBFUSCATION_TOOLKIT", line 255
		//ORA-06512: at "SBREXT.DECRYPT", line 7"
		//while invoking the database functions
		String key = "1234567890123456";
		String text = "testtest";	//data has to be in a multiples of 8 bytes
		String encryptedText = null;
		byte[] encryptedBytes = null;
		String decryptedText = null;
		byte[] decryptedBytes = null;
		PreparedStatement stmt = null;
        ResultSet rs = null;
        ByteArrayInputStream in = null;
        try {
        	Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			String sql = "select sbrext.encrypt('"+key+"', ?) from dual";
			stmt = conn.prepareStatement(sql);
			stmt.setBytes(1, text.getBytes());
			rs = stmt.executeQuery();
			while(rs.next()) {
//				encryptedText = rs.getString(1);
				in = (ByteArrayInputStream) rs.getBinaryStream(1);
				encryptedBytes = toBytes(in);
			}
			
			String sql2 = "select sbrext.decrypt('"+key+"', ?) from dual";
			stmt = conn.prepareStatement(sql2);
			stmt.setBytes(1, encryptedBytes);
			rs = stmt.executeQuery();
			while(rs.next()) {
//				decryptedText = rs.getString(1);
				in = (ByteArrayInputStream) rs.getBinaryStream(1);
				decryptedBytes = toBytes(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertEquals(text.getBytes(), decryptedBytes);
	}

	private void showUserSecurityQuestionList() throws Exception {
		Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
		dao = new DAO(conn);
		UserSecurityQuestion[] results = dao.findAll();
		if (results.length > 0) {
			for (UserSecurityQuestion e : results) {
				System.out.println("User [" + e.getUaName() + "] updated ["
						+ new Date() + "] question1 [" + e.getQuestion1()
						+ "] answer1 [" + CommonUtil.decode(e.getAnswer1()) + "]" + "] question2 [" + e.getQuestion2()
						+ "] answer2 [" + CommonUtil.decode(e.getAnswer2()) + "]" + "] question3 [" + e.getQuestion3()
						+ "] answer3 [" + CommonUtil.decode(e.getAnswer3()) + "]");
			}
		} else {
			System.out.println("no question");
		}
	}

//	@Test
	public void testUserSecurityQuestionSave() throws Exception {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		qna.setUaName(USER_ID);
		qna.setQuestion1("question 1 from dao");
//		qna.setAnswer1(new String(Hex.encodeHex(x.encrypt((CommonUtil.pad("answer for question 1 of dao", DAO.MAX_ANSWER_LENGTH).getBytes())))));
		qna.setAnswer1(CommonUtil.encode("answer for question 1 of dao"));
		qna.setQuestion2("question 2 from dao");
//		qna.setAnswer2(new String(Hex.encodeHex(x.encrypt((CommonUtil.pad("answer for question 2 of dao", DAO.MAX_ANSWER_LENGTH).getBytes())))));
		qna.setAnswer2(CommonUtil.encode("answer for question 2 of dao"));
		qna.setQuestion3("question 3 from dao");
//		qna.setAnswer3(new String(Hex.encodeHex(x.encrypt((CommonUtil.pad("answer for question 3 of dao", DAO.MAX_ANSWER_LENGTH).getBytes())))));
		qna.setAnswer3(CommonUtil.encode("answer for question 3 of dao"));
		try {
			Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			UserSecurityQuestion qna1 = dao.findByPrimaryKey(USER_ID);
			conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			if(qna1 == null) {
				dao.insert(qna);
			} else {
				dao.update(USER_ID, qna);
			}
			showUserSecurityQuestionList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
//	@Test
	public void testUserSecurityQuestionUpdate() {
		UserSecurityQuestion qna = new UserSecurityQuestion();
		try {
			Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			qna = dao.findByUaName(USER_ID);
			if(qna == null) {
				throw new Exception("No questions found. Have it been setup?");
			}
			qna.setQuestion2("question 2 from dao *updated*");
//			qna.setAnswer2(new String(Hex.encodeHex(x.encrypt((CommonUtil.pad("answer for question 2 of dao *updated*", DAO.MAX_ANSWER_LENGTH).getBytes())))));
			qna.setAnswer2(CommonUtil.encode("answer for question 2 of dao"));
			//dao.update(qna.getId(), qna);
			conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			dao.update(qna.getUaName(), qna);
			showUserSecurityQuestionList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	private String UPPERCASE_USER_ID = "TEST111";
	private String LOWERCASE_USER_ID = "test111";
	private String PROPERCASE_USER_ID = "Test111";
	private String PASSWORD = "Te$t1235";
	
//	@Test
	public void testUserIDUpperCase() {
		boolean status = false;
		try {
			Connection conn = getConnection(UPPERCASE_USER_ID, PASSWORD);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		assertTrue(status);
	}

//	@Test
	public void testUserIDLowerCase() {
		boolean status = false;
		try {
			Connection conn = getConnection(LOWERCASE_USER_ID, PASSWORD);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		assertTrue(status);
	}

//	@Test
	public void testUserIDProperCaseInSetup() {
		boolean status = false;
		try {
			Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			status = dao.checkValidUser(PROPERCASE_USER_ID);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		assertTrue(status);
	}

//	@Test
	public void testUserIDProperCaseInPasswordChange() {
		UserBean status = null;
		try {
			Connection conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			status = dao.checkValidUser(PROPERCASE_USER_ID, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
//		assertTrue(status.getResult().equals(ResultCode.));
	}
	
	@Test
	public void testUserAttemptedCountUpdate() {
		Connection conn = null;
		String userID = "root";
		try {
			conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
			dao = new DAO(conn);
			UserSecurityQuestion qna = dao.findByPrimaryKey(userID);
			if(qna != null) {
				qna = new UserSecurityQuestion();
				qna.setAttemptedCount(0l);
				conn = getConnection(ADMIN_ID, ADMIN_PASSWORD);
				dao = new DAO(conn);
				dao.update(userID, qna);
			} else {
				throw new Exception(userID + " does not exist.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}