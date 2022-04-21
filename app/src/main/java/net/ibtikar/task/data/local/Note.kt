package net.ibtikar.task.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var discription: String,
    @ColumnInfo(name = "date")
    var date: String//= SimpleDateFormat("EEE, d MMM yyyy").format(Date())
) : Parcelable
