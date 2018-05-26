package c.srs41.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by user on 24-05-2018.
 */
@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> getAllContacts();
    @Delete
    void delete(Contact contact);
    @Update
    void update(Contact contact);

}

