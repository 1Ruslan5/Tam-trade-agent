package com.example.tam_tradeagent.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Customer

class CustomerRVAdapter(
    val context: Context,
    private val customerClickDeleteInterface: CustomerClickDeleteInterface,
    private val customerClickInterface: CustomerClickInterface
): RecyclerView.Adapter<CustomerRVAdapter.ViewHolder>() {

    private val allCustomer = ArrayList<Customer>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val customer_delete = itemView.findViewById<ImageButton>(R.id.customer_delete)
        val customer_name = itemView.findViewById<TextView>(R.id.customer_name)
        val customer_phone = itemView.findViewById<TextView>(R.id.customer_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_customer,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customer_name.setText(allCustomer.get(position).name)
        holder.customer_phone.setText(allCustomer.get(position).phone)

        holder.customer_delete.setOnClickListener {
            customerClickDeleteInterface.onDeleteIconClick(allCustomer.get(position))
        }

        holder.itemView.setOnClickListener{
                customerClickInterface.onCustomerClick(allCustomer.get(position))
            }
        }

    override fun getItemCount(): Int {
        return allCustomer.size
    }

    fun updateList(newList: List<Customer>){
        allCustomer.clear()
        allCustomer.addAll(newList)
        notifyDataSetChanged()
    }

    interface CustomerClickDeleteInterface{
        fun onDeleteIconClick(customer: Customer)
    }

    interface  CustomerClickInterface{
        fun onCustomerClick(customer: Customer)
    }
}