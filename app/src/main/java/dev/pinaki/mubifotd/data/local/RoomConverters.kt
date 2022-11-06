package dev.pinaki.mubifotd.data.local

import androidx.room.TypeConverter

class RoomConverters {

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString()

    @TypeConverter
    fun toStringList(string: String): List<String> = string.split(", ")
}