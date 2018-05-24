package com.labs.renegades.githubsearch.datasource.local.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "repository")
class RepositoryEntity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "search_query") var searchQuery: String? = "",
        @ColumnInfo(name = "name") var name: String? = "",
        @ColumnInfo(name = "description") var description: String? = "",
        @ColumnInfo(name = "url") var url: String? = "",
        @ColumnInfo(name = "updatedAt") var updatedAt: String? = "")