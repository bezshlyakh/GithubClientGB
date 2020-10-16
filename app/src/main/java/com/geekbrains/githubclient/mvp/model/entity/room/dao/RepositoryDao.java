package com.geekbrains.githubclient.mvp.model.entity.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUserRepo;
import java.util.List;

@Dao
public interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomGithubUserRepo repo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RoomGithubUserRepo> repo);

    @Update
    void update(RoomGithubUserRepo repo);

    @Update
    void update(List<RoomGithubUserRepo> repo);

    @Delete
    void delete(RoomGithubUserRepo repo);

    @Delete
    void delete(List<RoomGithubUserRepo> repo);


    @Query("SELECT * FROM RoomGithubUserRepo")
    List<RoomGithubUserRepo> getAll();

    @Query("SELECT * FROM RoomGithubUserRepo WHERE userId = :id")
    List<RoomGithubUserRepo> findByUser(String id);

}
