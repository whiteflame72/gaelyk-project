package com.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import com.test.gae.dto.*;
import com.test.oracle.dto.*;
//import com.google.appengine.api.datastore.Transaction;
import com.test.oracle.dao.UserSecurityQuestionDao;
import com.test.oracle.dao.UserSecurityQuestionDao;
import com.test.oracle.dao.oracle.DaoFactoryImpl;

public class OracleDAO {
	
	private static Connection connection;

	private static UserSecurityQuestionDao dao = (new DaoFactoryImpl())
			.createUserSecurityQuestionDao(getConnection());
	private List<UserSecurityQuestion> myBeans = getUserSecurityQuestions();

	public static Connection getConnection() {
		return connection;
	}
	
	public static void setConnection(Connection connection) {
		OracleDAO.connection = connection;
	}

	public List<UserSecurityQuestion> getUserSecurityQuestions() {
		UserSecurityQuestion[] g = (UserSecurityQuestion[]) dao.findAll();
		List retVal = new ArrayList(Arrays.asList(g));

		return retVal;
	}

	public void setUserSecurityQuestion(List<UserSecurityQuestion> myBeans) {
		this.myBeans = myBeans;
	}

	public UserSecurityQuestionDao getDao() {
		return dao;
	}

	public void setDao(UserSecurityQuestionDao dao) {
		this.dao = dao;
	}

//	public void delete(Long id) {
//		try {
//			dao.deleteByPrimaryKey(id);
//			myBeans = getUserSecurityQuestions();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void save(UserSecurityQuestion myBean) throws Exception {
		if (myBeans.indexOf(myBean) > -1) {
			// update
			myBeans.set(myBeans.indexOf(myBean), myBean);
		} else {
			myBeans.add(myBean);
		}
		try {
			if (myBean != null) {
//				UserSecurityQuestion g = getUserSecurityQuestion(myBean.getId());
//				if (g != null) {
//					dao.update(g.getId(), myBean);
//				} else {
					dao.insert(myBean);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public UserSecurityQuestion getUserSecurityQuestion(Long id) {
		UserSecurityQuestion retVal = null;
		// for (UserSecurityQuestion myBean : myBeans) {
		// if (myBean.getId() != null && myBean.getId().equals(id)) {
		// retVal = myBean;
		// }
		// }
//		retVal = (UserSecurityQuestion) dao.findByPrimaryKey(id);
		return retVal;
	}

	@Override
	public String toString() {
		return myBeans.toString();
	}

}
