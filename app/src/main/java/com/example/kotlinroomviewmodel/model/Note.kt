package com.example.kotlinroomviewmodel.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
class Note(
    @ColumnInfo(name = "title") var title: String
    , @ColumnInfo(name = "description") var description: String
    , @ColumnInfo(name = "priority") var priority: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}