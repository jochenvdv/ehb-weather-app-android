package be.ehb.weather.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.ehb.weather.database.daos.SavedLocationDao;
import be.ehb.weather.database.entities.SavedLocation;

@Database(entities = { SavedLocation.class }, version = 4)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract SavedLocationDao savedLocationDao();

    public static WeatherDatabase getDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                WeatherDatabase.class,
                "weather_database"
        )
            .addMigrations(MIGRATIONS.toArray(new Migration[0]))
            .build();
    }

    private final static List<Migration> MIGRATIONS = new ArrayList<>(Collections.emptyList());
}
