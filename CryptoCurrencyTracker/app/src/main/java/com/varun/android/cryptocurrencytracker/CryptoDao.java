package com.varun.android.cryptocurrencytracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Varun on 2/1/2018.
 */

@Dao
public interface CryptoDao {

    @Query("select * from crypto")
    List<CryptoTable> getAll();

    @Query("select * from crypto where id = :id")
    CryptoTable getById(String id);

    @Query("select count(*) from crypto")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CryptoTable> cryptoTables);

    @Delete
    void deleteAll(List<CryptoTable> cryptoTables);

    @Update
    void updateAll(List<CryptoTable> cryptoTables);
}
