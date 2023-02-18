package com.example.tam_tradeagent.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import retrofit2.http.GET

@Entity(tableName = "customer_table")
data class Customer(
    @NotNull @ColumnInfo(name = "name_customer") var name:String,
    @NotNull @ColumnInfo(name = "email_customer") var email:String,
    @NotNull @ColumnInfo(name = "phone_customer") var phone:String,
    @ColumnInfo(name = "note_customer")var note:String?,
){
    @PrimaryKey(autoGenerate = true) var id_customer = 0
}
