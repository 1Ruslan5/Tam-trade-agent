package com.example.tam_tradeagent.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_goods(goods: Goods)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert_customer(customer: Customer)

    @Update
    suspend fun update_goods(goods: Goods)

    @Delete
    suspend fun delete_goods(goods: Goods)

    @Query("Select * from goods_table")
    fun getAllGoods(): LiveData<List<Goods>>

    @Query("SELECT phone_customer from customer_table where name_customer = :name_customer")
    fun getPhoneCustomer(name_customer: String): LiveData<List<String>>

    @Query("SELECT name_customer from customer_table")
    fun getNameCustomer(): LiveData<List<String>>

    @Query("SELECT id_customer from customer_table where name_customer = :name_customer and phone_customer = :phone_customer")
    fun getNameIdCustomer(name_customer: String, phone_customer: String): LiveData<Int>

    @Transaction
    @Query("Select * from customer_table where id_customer = :id_customer")
    fun getCustomerWithGoods(id_customer: Int): LiveData<List<CustomerWithGoods>>

    @Query("Select * from goods_table where goods_name like :searchQuery or customer_goods like :searchQuery")
    fun searchGoods(searchQuery: String) : LiveData<List<Goods>>
}
