package com.example.tam_tradeagent.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Customer
import com.example.tam_tradeagent.model.Goods
import com.example.tam_tradeagent.viewmodels.CustomerViewModal
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentGoodsOrder: Fragment(), GoodsRVAdapter.GoodsClickInterface,
    GoodsRVAdapter.GoodsClickDeleteInterface {

    lateinit var viewModel: CustomerViewModal
    lateinit var goodsRV: RecyclerView
    lateinit var addFABGoods: FloatingActionButton
    lateinit var layoutManager: LinearLayoutManager
    lateinit var goodsRVAdapter:GoodsRVAdapter
    lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.recyclerview_orders, container, false)
        goodsRV = v.findViewById(R.id.recycle_orders)
        addFABGoods = v.findViewById(R.id.idFABGoods)
        searchView = v.findViewById(R.id.search_data_recycler)

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0 != null){
                    searchDatabaseGoods(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0 != null){
                    searchDatabaseGoods(p0)
                }
                return true
            }
        })

        layoutManager = LinearLayoutManager(context)
        goodsRV.layoutManager = layoutManager
        goodsRVAdapter = GoodsRVAdapter(requireContext(), this, this)
        goodsRV.adapter =goodsRVAdapter
        viewModel = ViewModelProvider(
            this
        ).get(CustomerViewModal::class.java)
        viewModel.allGoods.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                goodsRVAdapter.updateGoodsList(it)
            }
        })

        goodsRV.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )

        addFABGoods.setOnClickListener {
            val intent = Intent(context, FragmentAddEditGoods::class.java)
            startActivity(intent)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }
        return v
    }

    override fun onGoodsClick(goods: Goods) {
        val intent = Intent(context, FragmentUpdateGoods::class.java)
        intent.putExtra("type", "Edit")
        intent.putExtra("goodsName", goods.goods_name)
        intent.putExtra("amountGoods", goods.goods_amount)
        intent.putExtra("customerName", goods.name)
        intent.putExtra("customerPhone", goods.phone)
        intent.putExtra("currentData", goods.goods_currentDate)
        intent.putExtra("deliveryData", goods.goods_deliveryDate)
        intent.putExtra("address", goods.goods_address)
        intent.putExtra("deliveryType", goods.goods_deliveryType)
        intent.putExtra("priceType", goods.goods_priceType)
        intent.putExtra("price", goods.goods_price)
        intent.putExtra("goodsNote", goods.goods_note)
        intent.putExtra("idGoods", goods.id_goods)
        intent.putExtra("idCustomer", goods.goodsCustomer)
        startActivity(intent)
    }

    override fun onDeleteIconClick(goods: Goods) {
        viewModel.deleteGoods(goods)
        Toast.makeText(context, "${goods.goods_name} Delete", Toast.LENGTH_LONG).show()
    }

    fun searchDatabaseGoods(query: String){
        val searchQuery = "%$query%"

        viewModel.getSearchDataGoods(searchQuery).observe(this, Observer{list->
            list.let{
                goodsRVAdapter.updateGoodsList(it)
            }
        })
    }
}