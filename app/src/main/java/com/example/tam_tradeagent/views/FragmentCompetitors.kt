package com.example.tam_tradeagent.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Customer
import com.example.tam_tradeagent.viewmodels.CustomerViewModal
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FragmentCompetitors : Fragment(), CustomerRVAdapter.CustomerClickInterface,
    CustomerRVAdapter.CustomerClickDeleteInterface {

    lateinit var viewModel: CustomerViewModal
    lateinit var customerRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var layoutManager: LinearLayoutManager
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var customerRVAdapter:CustomerRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_customer, container, false)
        customerRV = v.findViewById(R.id.recycle_customer)
        addFAB = v.findViewById(R.id.idFAB)
        searchView = v.findViewById(R.id.search_data_customer)


        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0 != null){
                    searchDatabase(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0 != null){
                    searchDatabase(p0)
                }
                return true
            }
        })

        layoutManager = LinearLayoutManager(context)

        customerRV.layoutManager = layoutManager
        customerRVAdapter = CustomerRVAdapter(requireContext(), this, this)
        customerRV.adapter = customerRVAdapter
        viewModel = ViewModelProvider(
            this
        ).get(CustomerViewModal::class.java)
        viewModel.allCustomers.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                customerRVAdapter.updateList(it)
            }
        })



        customerRV.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )

        addFAB.setOnClickListener {
            val intent = Intent(context, FragmentAddEditCustomer::class.java)
            startActivity(intent)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
        }
        return v
    }

    override fun onCustomerClick(customer: Customer) {
        val intent = Intent(context, FragmentAddEditCustomer::class.java)
        intent.putExtra("type", "Edit")
        intent.putExtra("name", customer.name)
        intent.putExtra("email", customer.email)
        intent.putExtra("phone", customer.phone)
        intent.putExtra("note", customer.note)
        intent.putExtra("id_customer", customer.id_customer)
        startActivity(intent)
    }

    override fun onDeleteIconClick(customer: Customer) {
        viewModel.getGoodsId(customer.id_customer).observe(this, Observer {
                list ->list.let{
            if(it.size >0){
                Toast.makeText(context, "${customer.name} is using", Toast.LENGTH_LONG).show()
            }else {
                viewModel.deleteCustomer(customer)
                Toast.makeText(context, "${customer.name} Delete", Toast.LENGTH_LONG).show()
            }
        }
        })
    }

    fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        viewModel.getSearchData(searchQuery).observe(this, Observer{list->
            list.let{
                customerRVAdapter.updateList(it)
            }
        })
    }
}
