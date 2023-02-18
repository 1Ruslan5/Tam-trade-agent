package com.example.tam_tradeagent.repository

import androidx.lifecycle.LiveData
import com.example.tam_tradeagent.model.*

class CustomerRepository(private val customerDao: CustomerDao, private val goodsDao: GoodsDao ) {

    val allCustomer: LiveData<List<Customer>> = customerDao.getAllCustomer()
    val allGoods: LiveData<List<Goods>> = goodsDao.getAllGoods()
    val allName: LiveData<List<String>> = goodsDao.getNameCustomer()

    suspend fun insert(customer: Customer) {
        customerDao.insert_customer(customer)
    }

    suspend fun insert_goods(goods: Goods){
        goodsDao.insert_goods(goods)
    }

    suspend fun delete(customer: Customer){
        customerDao.delete_customer(customer)
    }

    suspend fun delete_goods(goods: Goods){
        goodsDao.delete_goods(goods)
    }

    suspend fun update(customer: Customer){
        customerDao.update_customer(customer)
    }

    suspend fun update_goods(goods: Goods){
        goodsDao.update_goods(goods)
    }

    fun getPhoneCustomer(name_customer: String): LiveData<List<String>> = goodsDao.getPhoneCustomer(name_customer)

    fun getNameIdCustomer(name_customer: String,phone_customer: String): LiveData<Int> = goodsDao.getNameIdCustomer(name_customer, phone_customer)

    fun getSearchData(searchQuery: String):LiveData<List<Customer>> = customerDao.searchDatabase(searchQuery)

    fun getListGoodsId(customer_id: Int):LiveData<List<Int>> = customerDao.getListGoodsId(customer_id)

    fun getSearchDataGoods(searchQuery: String):LiveData<List<Goods>> = goodsDao.searchGoods(searchQuery)
}