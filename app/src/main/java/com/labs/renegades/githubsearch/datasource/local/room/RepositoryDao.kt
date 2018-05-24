package com.labs.renegades.githubsearch.datasource.local.room

import android.arch.persistence.room.*
import io.reactivex.Flowable


@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository WHERE search_query LIKE :query LIMIT 10")
    fun searchByQuery(query: String): Flowable<List<RepositoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<RepositoryEntity>)

    @Delete
    fun delete(repo: RepositoryEntity)

}