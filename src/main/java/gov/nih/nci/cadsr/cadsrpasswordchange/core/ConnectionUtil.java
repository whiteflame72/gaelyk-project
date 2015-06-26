package gov.nih.nci.cadsr.cadsrpasswordchange.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//dev only
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class ConnectionUtil {

    private static Logger logger = Logger.getLogger(ConnectionUtil.class.getName());

//	private static Context envContext;
	private static DataSource ds;
	private static Connection pooledConnection;

    private String dbms;
	private String userName;
	private String password;
	private String serverName;
	private int portNumber = -1;
	private String dbName;
	
	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public static Connection getPooledConnection() {
		return pooledConnection;
	}

	public Connection getConnection() throws Exception {
		if(dbms == null ||
		userName == null ||
		password == null ||
		serverName == null ||
		portNumber == -1 ||
		dbName == null) {
			throw new Exception("All parameters must be set.");
		}

	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.userName);
	    connectionProps.put("password", this.password);

	    if (this.dbms.equals("oracle")) {
	    	Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	    	//SID only, not service name (ref: http://www.orafaq.com/wiki/JDBC)
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + ":thin:" +
	                    "@" +
	                   this.serverName +
	                   ":" + this.portNumber + ":" +
	                   this.dbName,
	                   connectionProps);        
	    } else if (this.dbms.equals("mysql")) {
	    	Class.forName ("com.mysql.jdbc.Driver").newInstance(); 
	    	String jdbcUrl = 
	                   "jdbc:" + this.dbms + "://" +
	                   this.serverName +
	                   ":" + this.portNumber + "/" + this.dbName;
	    	logger.info("jdbc url = '" + jdbcUrl + "'");
	        conn = DriverManager.getConnection(jdbcUrl,
	                   connectionProps);
	    } else if (this.dbms.equals("derby")) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.dbms + ":" +
	                   this.dbName +
	                   ";create=true",
	                   connectionProps);
	    }
	    return conn;
	}

	public static Result decode(Exception ex) {
		
//		logger.info("decode");
		
		Result result = null;
		
		if (!(ex instanceof java.sql.SQLException)) {
			result = new Result(ResultCode.UNKNOWN_ERROR);
		}
		
		int found = -1;
		String errorMessage = ex.getMessage();
//		logger.debug("errorMessage: " + errorMessage);
		
		// SQLException error code is vendor-specific and was just returning a zero
		// have to parse message for codes

		// Oracle codes
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-01017");  // invalid username/password
			if (found != -1)
				result = new Result(ResultCode.INVALID_LOGIN);
		}

		if (result == null) {
			found = errorMessage.indexOf("ORA-00988");  // missing or invalid password(s)
			if (found != -1)
				result = new Result(ResultCode.MISSING_OR_INVALID);
		}

		if (result == null) {		
			found = errorMessage.indexOf("ORA-28000");  // locked
			if (found != -1)
				result = new Result(ResultCode.LOCKED_OUT);
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-28001");  // expired password
			if (found != -1)
				result = new Result(ResultCode.EXPIRED);
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-28007");
			if (found != -1)
				result = new Result(ResultCode.PASSWORD_REUSED);
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-00972");
			if (found != -1)
				result = new Result(ResultCode.PASSWORD_TOO_LONG);
		}		
		
		// custom codes (from stored procedure)
		if (result == null) {		
			found = errorMessage.indexOf("ORA-20000");
			if (found != -1)
				result = new Result(ResultCode.PASSWORD_LENGTH, errorMessage.substring(found + 11));
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-20001");
			if (found != -1)
				result = new Result(ResultCode.UNSUPPORTED_CHARACTERS, errorMessage.substring(found + 11));
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-20002");
			if (found != -1)
				result = new Result(ResultCode.NOT_ENOUGH_GROUPS, errorMessage.substring(found + 11));
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-20003");
			if (found != -1)
				result = new Result(ResultCode.TOO_RECENT, errorMessage.substring(found + 11));		
		}
		
		if (result == null) {
			found = errorMessage.indexOf("ORA-20004");
			if (found != -1)
				result = new Result(ResultCode.START_NOT_LETTER, errorMessage.substring(found + 11));		
		}
		
		// check for unspecified custom error (range is -20000 to -20999)
		if (result == null) {	
			found = errorMessage.indexOf("ORA-20");
			if (found != -1) {
				// check that the next three characters are digits (resulting in 000-999)
				if ( Character.isDigit(errorMessage.charAt(found + 6))
						&& Character.isDigit(errorMessage.charAt(found + 7))
							&& Character.isDigit(errorMessage.charAt(found + 8)) )
					result = new Result(ResultCode.UNKNOWN_CUSTOM, errorMessage.substring(found + 11));
			}
		}

		if (result == null)
			result = new Result(ResultCode.UNKNOWN_ERROR);

//		logger.info("returning ResultCode " + result.getResultCode().toString() + " " + result.getMessage());
		return result;
	}
//	
//	public static DataSource getDS(String _jndiUser) throws Exception {
//		Context envContext = new InitialContext();
//		DataSource ds = (DataSource)envContext.lookup(_jndiUser);
//		return ds;
//	}

	//dev only
	public static DataSource getTestDS(String username, String password) throws Exception {
		String dbtype = "mysql";
		String dbserver = "localhost";
		String dbname = "SBR";
//		String username = "root";
//		String password = "root";
		int port = 3306;
		ConnectionUtil cu = new ConnectionUtil();
		cu.setUserName(username);
		cu.setPassword(password);
		cu.setDbms(dbtype);
		cu.setDbName(dbname);
		cu.setServerName(dbserver);
		cu.setPortNumber(port);
		pooledConnection = cu.getConnection();
		Properties properties = new Properties();
		properties.setProperty("url", "jdbc:mysql://"+dbserver+":"+port +"/"+ dbname);
		properties.setProperty("maxActive", "10");
		properties.setProperty("maxIdle", "8");
		properties.setProperty("minIdle", "10");
		properties.setProperty("maxWait", "10");
		properties.setProperty("testOnBorrow", "true");
		properties.setProperty("username", username);
		properties.setProperty("password", password);
		properties.setProperty("validationQuery", "SELECT 1");
		properties.setProperty("removeAbandoned", "true");
		properties.setProperty("removeAbandonedTimeout", "1");
		properties.setProperty("logAbandoned", "true");
		DataSource ds = BasicDataSourceFactory.createDataSource(properties);		
//		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
//		builder.bind("java:/comp/env/jdbc/testDB", ds);
//		builder.activate();
		return ds;
		
		// Construct PerUserPoolDataSource reference
//		  Reference ref = new Reference("org.apache.commons.dbcp.datasources.PerUserPoolDataSource",
//		    "org.apache.commons.dbcp.datasources.PerUserPoolDataSourceFactory", null);
//		  ref.add(new StringRefAddr("url", "jdbc:mysql://"+dbserver+"/"+ dbname));
//		  ref.add(new StringRefAddr("dataSourceName", "jdbc/cpds"));
//		  ref.add(new StringRefAddr("defaultMaxActive", "100"));
//		  ref.add(new StringRefAddr("defaultMaxIdle", "30"));
//		  ref.add(new StringRefAddr("defaultMaxWait", "10000"));
//		  ic.rebind("jdbc/peruser", ref);
		  // Use
//		  InitialContext ic2 = new InitialContext();
//		  DataSource ds = (DataSource) ic2.lookup("jdbc/peruser");	  
//		datasource = (DataSource) ref;
	}

	public static DataSource getDS(String _jndiUser) throws Exception {
		Context envContext = new InitialContext();
		DataSource ds = (DataSource)envContext.lookup(_jndiUser);
		return ds;
	}

}
