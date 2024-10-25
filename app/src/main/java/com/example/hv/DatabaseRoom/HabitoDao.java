package com.example.hv.DatabaseRoom;

import com.example.hv.Habito;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HabitoDao {

    @Insert
    void insert(Habito habito);

    @Delete
    void delete(Habito habito);

    @Query("SELECT* FROM Habito")
    List<Habito> getAllHabitos();

    @Update
    public void update(Habito habitos);

}
