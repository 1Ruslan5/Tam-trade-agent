package com.example.tam_tradeagent.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.temporal.TemporalAmount

@Entity(tableName = "goods_table", foreignKeys = arrayOf(
    ForeignKey(
    entity = Customer::class,
    parentColumns = arrayOf("id_customer"),
    childColumns = arrayOf("goodsCustomer"),

    onDelete = CASCADE)))

data class Goods(
    @NotNull @ColumnInfo(name = "goods_name") var goods_name: String,
    @NotNull @ColumnInfo(name = "amount_of_goods") var goods_amount: Int,
    @NotNull @ColumnInfo(name = "customer_goods") var name: String,
    @NotNull @ColumnInfo(name = "customer_phone-goods") var phone: String,
    @NotNull @ColumnInfo(name = "current_date_goods") var goods_currentDate: String,
    @NotNull @ColumnInfo(name = "delivery_date_goods") var goods_deliveryDate: String,
    @NotNull @ColumnInfo(name = "address_goods") var goods_address: String,
    @NotNull @ColumnInfo(name = "type_of_delivery_goods") var goods_deliveryType: String,
    @NotNull @ColumnInfo(name = "type_of_price_goods") var goods_priceType: String,
    @NotNull @ColumnInfo(name = "price_goods") var goods_price: String,
    @ColumnInfo(name = "note_goods") var goods_note: String?,
    @ColumnInfo(index = true)var goodsCustomer: Int
) {
    @PrimaryKey(autoGenerate = true) var id_goods:Int = 0
}