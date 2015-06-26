package com.test;

import gov.nih.nci.cadsr.cadsrpasswordchange.core.ConnectionUtil;
import gov.nih.nci.cadsr.cadsrpasswordchange.core.Result;
import gov.nih.nci.cadsr.cadsrpasswordchange.core.ResultCode;
import gov.nih.nci.cadsr.cadsrpasswordchange.core.UserBean;
import gov.nih.nci.cadsr.cadsrpasswordchange.core.UserSecurityQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


import org.apache.log4j.Logger;

import com.test.mysql.dao.mysql.UserSecurityQuestionDaoImpl;


public class DAO extends UserSecurityQuestionDaoImpl {

    private Logger logger = Logger.getLogger(DAO.class);
	private BasicDataSource datasource;
	
    public DAO(DataSource datasource) throws Exception {
    	super(ConnectionUtil.getPooledConnection());
    	this.datasource = (BasicDataSource) datasource;
    }

    public DAO(Connection conn) {
		super(conn);
	}

	public boolean checkValidUser(String username) {
		boolean retVal = false;
		
		logger.info ("checkValidUser user: " + username);

		PreparedStatement pstmt = null;
		try {
			//select username,account_status from dba_users where lock_date is not null and username = 'cadsrpasswordchange';
			//insert into USER_ACCOUNTS_VIEW (ua_name, date_created) values('root', sysdate());
//			pstmt = conn.prepareStatement("select * from USER_ACCOUNTS_VIEW a, USER_SECURITY_QUESTIONS b where a.UA_NAME = b.UA_NAME and b.UA_NAME = ?");
			pstmt = conn.prepareStatement("select * from USER_ACCOUNTS_VIEW where UA_NAME = ?");
			pstmt.setString(1, username);
			
			ResultSet result = pstmt.executeQuery();
			int count = 0;
			while(result.next()) {
			    count++;
			}
			if(count > 0) {
				//assuming all user Ids are unique/no duplicate
				retVal = true;
			}
	    } catch (Exception ex) {
	    	logger.debug(ex.getMessage());
        }
        finally {
//        	if (conn != null) {
//        		try {
//        			conn.close();
//        		} catch (Exception ex) {
//        			logger.error(ex.getMessage());
//        		}
//	        }
	    }
		
		
        logger.info("checkValidUser(): " + retVal);
        return retVal;
	}
	
	public UserBean checkValidUser(String username, String password) throws Exception {
		boolean retVal = false;
		
		logger.info ("checkValidUser user: " + username);
		UserBean userBean = new UserBean(username);

		PreparedStatement pstmt = null;
		try {
			//--CREATE USER 'jen1'@'localhost' IDENTIFIED BY 'jen1';

			//datasource.getConnection(username, password);	//apache commons dbcp 1.4 does not support this mode of login
//			datasource.setUsername(username);	//workaround
//			datasource.setPassword(password);	//workaround
//			datasource.getConnection();	//workaround			
			pstmt = conn.prepareStatement("select user from mysql.user where user = ? and password = password(?)");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet result = pstmt.executeQuery();
			int count = 0;
			if(result.next()) {
				//assuming all user Ids are unique/no duplicate
				retVal = true;
				logger.debug("connected");

				userBean.setLoggedIn(true);
				userBean.setResult(new Result(ResultCode.NOT_EXPIRED));
			}
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	logger.debug(ex.getMessage());
			Result result = ConnectionUtil.decode(ex);
			
	    	// expired passwords are acceptable as logins
			if (result.getResultCode() == ResultCode.EXPIRED) {
				userBean.setLoggedIn(true);
				logger.debug("considering expired password acceptable login");
				
			}
			
			userBean.setResult(result);
        }
        finally {
//        	if (conn != null) {
//        		try {
//        			conn.close();
//        		} catch (Exception ex) {
//        			logger.error(ex.getMessage());
//        		}
//	        }
	    }
				
        logger.info("checkValidUser(): " + retVal);
        return userBean;
	}	
	
	public Result changePassword(String user, String password, String newPassword) {

		logger.info("changePassword  user " + user );
		
		Result result = new Result(ResultCode.UNKNOWN_ERROR);  // (should get replaced)
		PreparedStatement pstmt = null;
		boolean isConnectionException = true;  // use to modify returned messages when exceptions are system issues instead of password change issues  
		
		try {
//			String alterUser = "SET PASSWORD FOR ?@localhost = PASSWORD('?')";
			String alterUser = "SET PASSWORD FOR "+user+"@localhost = PASSWORD('"+newPassword+"')";
			pstmt = conn.prepareStatement(alterUser);
//			pstmt.setString(1, user);
//			pstmt.setString(2, newPassword);
			logger.debug("attempted to change password for user " + user);
			pstmt.execute();
	        isConnectionException = false;
			result = new Result(ResultCode.PASSWORD_CHANGED);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex.getMessage());
			if (isConnectionException)
				result = new Result(ResultCode.UNKNOWN_ERROR);  // error not related to user, provide a generic error 
			else
				result = ConnectionUtil.decode(ex);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception ex) {
//					logger.error(ex.getMessage());
//				}
//			}
		}

       logger.info("returning ResultCode " + result.getResultCode().toString());        
       return result;
	}	

	public Result resetPassword(String user, String newPassword) {

		logger.info("resetPassword  user " + user );
		
		Result result = new Result(ResultCode.UNKNOWN_ERROR);  // (should get replaced)
		Connection conn = null;
		PreparedStatement pstmt = null;
			boolean isConnectionException = true;  // use to modify returned messages when exceptions are system issues instead of password change issues  
		
		try {
			String alterUser = "SET PASSWORD FOR ?@localhost = PASSWORD('?')";
			pstmt = conn.prepareStatement(alterUser);
			pstmt.setString(1, user);
			pstmt.setString(2, newPassword);
			logger.debug("attempted to reset password for user " + user);
			pstmt.execute();
	        isConnectionException = false;
			result = new Result(ResultCode.PASSWORD_CHANGED);
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
				if (isConnectionException)
					result = new Result(ResultCode.UNKNOWN_ERROR);  // error not related to user, provide a generic error 
				else
					result = ConnectionUtil.decode(ex);
		} finally {
		}

       return result;
	}	
	
}
