package com.example.tam_tradeagent.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Goods

class GoodsRVAdapter (
    val context: Context,
    private val goodsClickDeleteInterface: GoodsClickDeleteInterface,
    private val goodsClickInterface: GoodsClickInterface
    ): RecyclerView.Adapter<GoodsRVAdapter.ViewHolder>() {

    private val allGoods = ArrayList<Goods>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val goodsDelete = itemView.findViewById<ImageButton>(R.id.order_delete)
        val nameCustomerGoods = itemView.findViewById<TextView>(R.id.name_customer)
        val goodsName = itemView.findViewById<TextView>(R.id.goods)
        val goodsTotal = itemView.findViewById<TextView>(R.id.total)
        val goodsDepartureDate = itemView.findViewById<TextView>(R.id.departure_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_order,
            parent, false
        )
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        allGoods.sortByDescending {it.goods_deliveryDate}
        allGoods.reverse()
        holder.goodsName.setText(allGoods.get(position).goods_name)
        holder.nameCustomerGoods.setText(allGoods.get(position).name)
        holder.goodsTotal.setText((allGoods.get(position).goods_price.toInt() * allGoods.get(position).goods_amount).toString())
        holder.goodsDepartureDate.setText(allGoods.get(position).goods_deliveryDate)

        holder.goodsDelete.setOnClickListener{
            goodsClickDeleteInterface.onDeleteIconClick(allGoods.get(position))
        }

        holder.itemView.setOnClickListener{
            goodsClickInterface.onGoodsClick(allGoods.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allGoods.size
    }

    fun updateGoodsList(newList: List<Goods>){
        allGoods.clear()
        allGoods.addAll(newList)
        notifyDataSetChanged()
    }

    interface GoodsClickDeleteInterface{
        fun onDeleteIconClick(goods: Goods)
    }

    interface GoodsClickInterface{
        fun onGoodsClick(goods: Goods)
    }
}