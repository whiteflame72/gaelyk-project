package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.test.gae.dao.*;
import com.test.gae.dao.gae.DaoFactoryImpl;
import com.test.gae.dto.*;
import com.google.appengine.api.datastore.Transaction;

//import com.spoledge.audao.db.dao.DaoException;

public class TestGaeDAO {

	private static SecurityQnADao dao = (new DaoFactoryImpl())
			.createSecurityQnADao(Datastore.getDS());
	private List<SecurityQnA> myBeans = getSecurityQnAs();

	public List<SecurityQnA> getSecurityQnAs() {
		Transaction tx = null;
		tx = Datastore.getDS().beginTransaction();
		SecurityQnA[] g = (SecurityQnA[]) dao.findAll();
		List retVal = new ArrayList(Arrays.asList(g));
		tx.commit();

		return retVal;
	}

	public void setSecurityQnA(List<SecurityQnA> myBeans) {
		this.myBeans = myBeans;
	}

	public SecurityQnADao getDao() {
		return dao;
	}

	public void setDao(SecurityQnADao dao) {
		this.dao = dao;
	}

	public void delete(Long id) {
		Transaction tx = null;
		try {
			tx = Datastore.getDS().beginTransaction();
			dao.deleteByPrimaryKey(id);
			myBeans = getSecurityQnAs();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	public void save(SecurityQnA myBean) throws Exception {
		Transaction tx = null;
		tx = Datastore.getDS().beginTransaction();
		if (myBeans.indexOf(myBean) > -1) {
			// update
			myBeans.set(myBeans.indexOf(myBean), myBean);
		} else {
			myBeans.add(myBean);
		}
		try {
			if (myBean != null) {
				SecurityQnA g = getSecurityQnA(myBean.getId());
				if (g != null) {
					dao.update(g.getId(), myBean);
					tx.commit();
				} else {
					dao.insert(myBean);
					tx.commit();
				}
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public SecurityQnA getSecurityQnA(Long id) {
		SecurityQnA retVal = null;
		Transaction tx = null;
		tx = Datastore.getDS().beginTransaction();

		// for (SecurityQnA myBean : myBeans) {
		// if (myBean.getId() != null && myBean.getId().equals(id)) {
		// retVal = myBean;
		// }
		// }
		retVal = (SecurityQnA) dao.findByPrimaryKey(id);
		tx.commit();
		return retVal;
	}

	@Override
	public String toString() {
		return myBeans.toString();
	}

}
