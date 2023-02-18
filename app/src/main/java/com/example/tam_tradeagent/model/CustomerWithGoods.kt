package com.example.tam_tradeagent.model

import androidx.room.Embedded
import androidx.room.Relation

class CustomerWithGoods (
    @Embedded val customer:Customer,
    @Relation(
        parentColumn = "id_customer",
        entityColumn = "goodsCustomer"
    )
    val goodsCustomer: List<Goods>
)
