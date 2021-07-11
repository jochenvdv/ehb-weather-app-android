package be.ehb.weather.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import be.ehb.weather.database.entities.SavedLocation;

@Dao
public interface SavedLocationDao {
    @Insert
    void create(SavedLocation savedLocation);

    @Delete
    void delete(SavedLocation savedLocation);

    @Query("SELECT * FROM saved_locations")
    LiveData<List<SavedLocation>> getAll();
}
