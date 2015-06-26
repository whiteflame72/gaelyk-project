/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package com.test.gae.dao;

import java.sql.Date;
import java.sql.Timestamp;

import com.spoledge.audao.db.dao.AbstractDao;
import com.spoledge.audao.db.dao.DaoException;

import com.test.gae.dto.SecurityQnA;


/**
 * This is the DAO.
 *
 * @author generated
 */
public interface SecurityQnADao extends AbstractDao {

    /**
     * Finds a record identified by its primary key.
     * @return the record found or null
     */
    public SecurityQnA findByPrimaryKey( long id );

    /**
     * Finds a record.
     */
    public SecurityQnA findByQuestion( String question );

    /**
     * Finds records ordered by question.
     */
    public SecurityQnA[] findAll( );

    /**
     * Deletes a record identified by its primary key.
     * @return true iff the record was really deleted (existed)
     */
    public boolean deleteByPrimaryKey( long id ) throws DaoException;

    /**
     * Inserts a new record.
     * @return the generated primary key - id
     */
    public long insert( SecurityQnA dto ) throws DaoException;

    /**
     * Updates one record found by primary key.
     * @return true iff the record was really updated (=found and any change was really saved)
     */
    public boolean update( long id, SecurityQnA dto ) throws DaoException;

}