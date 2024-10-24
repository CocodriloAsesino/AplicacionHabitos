package com.example.hv.DatabaseRoom;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hv.Habito;

@Database(entities = {Habito.class}, version = 1)
public abstract class HabitoDatabase extends RoomDatabase {
    public abstract HabitoDao habitoDao();
}
