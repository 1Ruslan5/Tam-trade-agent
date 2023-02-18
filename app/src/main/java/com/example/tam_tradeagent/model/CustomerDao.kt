package com.example.tam_tradeagent.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.nio.channels.SelectionKey

@Dao
interface CustomerDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_customer(customer: Customer)

    @Update
    suspend fun update_customer(customer: Customer)

    @Delete
    suspend fun delete_customer(customer: Customer)

    @Query("Select * from customer_table")
    fun getAllCustomer():LiveData<List<Customer>>

    @Query("Select * from customer_table where name_customer like :searchQuery or phone_customer like :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<Customer>>

    @Query("Select id_goods from goods_table where goodsCustomer = :customer_id")
    fun getListGoodsId(customer_id:Int): LiveData<List<Int>>


//    @Query("Select address from customer_table where name_customer = :name_customer")
//    fun getAddress(name_customer: String):LiveData<List<String>>
//    @Query("Select name_customer from customer_table")
//    fun getNameCustomer():LiveData<List<String>>
}