package com.example.tam_tradeagent.viewmodels

import android.app.Application
import android.database.CursorJoiner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tam_tradeagent.model.*
import com.example.tam_tradeagent.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CustomerViewModal(application: Application) : AndroidViewModel(application) {
    val allCustomers : LiveData<List<Customer>>
    val allGoods: LiveData<List<Goods>>
    val allName: LiveData<List<String>>
    val repository: CustomerRepository

    init {
        val customerDao = CustomerDatabase.getDatabase(application).getCustomerDao()
        val goodsDao = CustomerDatabase.getDatabase(application).getGoodsDao()
        repository = CustomerRepository(customerDao, goodsDao)
        allCustomers = repository.allCustomer
        allGoods = repository.allGoods
        allName = repository.allName
    }

    fun deleteCustomer(customer: Customer) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(customer)
    }
    fun updateCustomer(customer: Customer) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(customer)
    }

    fun addCustomer(customer: Customer) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(customer)
    }

    fun addGoods(goods: Goods) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert_goods(goods)
    }

    fun updateGoods(goods: Goods) = viewModelScope.launch(Dispatchers.IO) {
        repository.update_goods(goods)
    }

    fun deleteGoods(goods: Goods) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete_goods(goods)
    }

    fun getPhoneCustomer(name_customer: String):LiveData<List<String>> {
        return repository.getPhoneCustomer(name_customer)
    }

    fun getNameIdCustomer(name_customer: String,phone_customer: String):LiveData<Int>{
        return repository.getNameIdCustomer(name_customer, phone_customer)
    }

    fun getSearchData(searchQuery: String):LiveData<List<Customer>>{
        return  repository.getSearchData(searchQuery)
    }

    fun getGoodsId(customer_id: Int):LiveData<List<Int>>{
        return repository.getListGoodsId(customer_id)
    }

    fun getSearchDataGoods(searchQuery: String):LiveData<List<Goods>>{
        return repository.getSearchDataGoods(searchQuery)
    }
}