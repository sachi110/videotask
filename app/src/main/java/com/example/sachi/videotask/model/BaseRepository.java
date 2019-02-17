/*******************************************************************************
 * Copyright (c) 2011 FieldEZ Technologies
 * All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * FieldEZ Technologies ("Confidential Information").  Thou shalt not
 * disclose such Confidential Information and shalt use it only in
 * accordance with the terms of the license agreement you entered into
 * with FieldEZ.
 ******************************************************************************/
package com.example.sachi.videotask.model;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class BaseRepository<K extends BaseModel> {
    private static final String TAG = BaseRepository.class.getSimpleName();
    public Class<K> type;

    DatabaseHelper dbHelper;
    Dao<K, Integer> dao;

    public BaseRepository(Class<K> type) {
        this.type = type;

        try {
            if(dbHelper==null)
                dbHelper = ORMLiteDataHelper.getInstance().getDatabaseHelper();
            if(dao==null)
                dao = DaoManager.createDao(dbHelper.getConnectionSource(), type);
        } catch (SQLException e) {
            Log.e(TAG, "Exception in Creating DAO", e);
        }
    }

    public K getSingleEntityByProperty(String propertyName, Object value) {
        List<K> list;
        try {
            QueryBuilder<K, Integer> qb = getDao(type).queryBuilder();
            SelectArg selectArg = new SelectArg();
            qb.where().eq(propertyName, selectArg);
            PreparedQuery<K> preparedQuery = qb.prepare();
            selectArg.setValue(value);
            list = getDao(type).query(preparedQuery);
            //list = (List<K>) getDao(type).queryForEq(propertyName, value);
            if (list != null && list.size() > 0)
                return list.get(0);
            else return null;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<K> getEntityByProperty(String propertyName, Object value) {
        List<K> list;
        try {
            QueryBuilder<K, Integer> qb = getDao(type).queryBuilder();
            SelectArg selectArg = new SelectArg();
            qb.where().eq(propertyName, selectArg);
            PreparedQuery<K> preparedQuery = qb.prepare();
            selectArg.setValue(value);
            list = getDao(type).query(preparedQuery);
            //list = (List<K>) getDao(type).queryForEq(propertyName, value);
            return list;
        } catch (SQLException e) {
        }
        return null;
    }

    public int persist(K k) {

        try {
            return createDao(type).create(k);
        } catch (SQLException e) {

        } catch (SQLiteConstraintException e) {

        } catch (Exception e) {

        }
        return 0;
    }

    public List<K> getDirty() {
        try {
            QueryBuilder<K, Integer> qb = getDao(type).queryBuilder();
            qb.orderBy("id", true);
            qb.where().eq("dirty", true);
            return qb.query();
        } catch (SQLException e) {

        }
        return null;
    }

    public int update(K k) {
        try {
            return getDao(type).update(k);
        } catch (SQLException e) {

        }
        return 0;
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(dbHelper.getConnectionSource(), type);
        } catch (SQLException e) {
            Log.e(TAG, "Exception" + e.getMessage());
        }
    }

    public K getEntity(int id) throws SQLException {
        return getDao(type).queryForId(id);
    }

    public int saveOrUpdate(K k) {
        try {
            getDao(type).createOrUpdate(k);
            return k.getId();
        } catch (Exception e) {


        }
        return 0;
    }

    public K getById(int id) {
        try {
            return getDao(type).queryForId(id);
        } catch (SQLException e) {

        }
        return null;
    }

    protected Dao<K, Integer> getDao(Class<K> k) throws SQLException {
        return dbHelper.getDao(k);
    }

    protected Dao<K, Integer> createDao(Class<K> k) {
        return dao;
    }





    public List<K> getAll() {
        try {
            return getDao(type).queryForAll();
        } catch (SQLException e) {

        }
        return null;
    }

    public K getSingle() {
        try {
            List<K> l = getDao(type).queryForAll();
            if (l != null && l.size() > 0)
                return l.get(0);
        } catch (SQLException e) {

        }
        return null;
    }





}
