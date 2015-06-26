/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package com.test.mysql.dao;

import java.sql.Date;
import java.sql.Timestamp;

import com.spoledge.audao.db.dao.AbstractDao;
import com.spoledge.audao.db.dao.DaoException;

import com.test.mysql.dto.UserSecurityQuestion;


/**
 * This is the DAO.
 *
 * @author generated
 */
public interface UserSecurityQuestionDao extends AbstractDao {

    /**
     * Finds a record identified by its primary key.
     * @return the record found or null
     */
    public UserSecurityQuestion findByPrimaryKey( String uaName );

    /**
     * Finds a record.
     */
    public UserSecurityQuestion findByUaName( String uaName );

    /**
     * Finds records ordered by ua_name.
     */
    public UserSecurityQuestion[] findAll( );

    /**
     * Deletes a record identified by its primary key.
     * @return true iff the record was really deleted (existed)
     */
    public boolean deleteByPrimaryKey( String uaName ) throws DaoException;

    /**
     * Inserts a new record.
     */
    public void insert( UserSecurityQuestion dto ) throws DaoException;

    /**
     * Updates one record found by primary key.
     * @return true iff the record was really updated (=found and any change was really saved)
     */
    public boolean update( String uaName, UserSecurityQuestion dto ) throws DaoException;

}
