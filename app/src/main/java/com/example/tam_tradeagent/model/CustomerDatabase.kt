package com.example.tam_tradeagent.model

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = arrayOf(
    Customer::class,
    Goods::class,
    ), version = 10, exportSchema = true)
abstract class CustomerDatabase : RoomDatabase(){

    abstract fun getCustomerDao():CustomerDao
    abstract fun getGoodsDao():GoodsDao

    companion object{
        @Volatile
        private var INSTANCE: CustomerDatabase? = null

        fun getDatabase(context: Context): CustomerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CustomerDatabase::class.java,
                    "Tam_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}