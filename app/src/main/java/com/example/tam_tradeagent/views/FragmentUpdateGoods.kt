package com.example.tam_tradeagent.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Goods
import com.example.tam_tradeagent.viewmodels.CustomerViewModal
import org.w3c.dom.Text
import java.lang.Double.parseDouble
import java.text.DateFormatSymbols
import java.util.*

class FragmentUpdateGoods:AppCompatActivity() {

    lateinit var viewModal: CustomerViewModal

    lateinit var goodsName: EditText
    lateinit var goodsAmount: EditText
    lateinit var goodsCustomerName: TextView
    lateinit var goodsCustomerPhone: TextView
    lateinit var goodsCurrentData:TextView
    lateinit var goodsDeliveryData: EditText
    lateinit var goodsAddress: EditText
    lateinit var goodsPrice: EditText
    lateinit var goodsNote: EditText

    lateinit var goodsDeliveryTypes: Spinner
    lateinit var goodsPriceTypes: Spinner

    lateinit var itemPrice: Array<String>
    lateinit var itemDelivery: Array<String>

    lateinit var styleDeliveryAdapter: ArrayAdapter<Any>
    lateinit var stylePriceTypeAdapter: ArrayAdapter<Any>

    lateinit var goodsDeliveryTypesString: String
    lateinit var goodsPriceTypesString:String
    lateinit var goodsDelivery :String
    lateinit var goodsPriceType :String

    lateinit var saveBtn: Button

    var goodsId = -1
    var customerId = -1

    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_goods_update)
        viewModal = ViewModelProvider(this).get(CustomerViewModal::class.java)

        goodsName = findViewById(R.id.goods_name_update)
        goodsAmount = findViewById(R.id.goods_amount_update)
        goodsCustomerName = findViewById(R.id.goods_goodsNameCustomer_update)
        goodsCustomerPhone = findViewById(R.id.goods_goodsPhoneCustomer_update)
        goodsCurrentData = findViewById(R.id.currentDate_goods_update)
        goodsDeliveryData = findViewById(R.id.deliveryDate_goods_update)
        goodsAddress = findViewById(R.id.address_goods_update)
        goodsPrice = findViewById(R.id.price_goods_update)
        goodsNote = findViewById(R.id.notes_goods_update)
        goodsPriceTypes = findViewById(R.id.typeOf_price_goods_update)
        goodsDeliveryTypes = findViewById(R.id.typeOf_delivery_goods_update)
        saveBtn = findViewById(R.id.button_goods_update)

        itemPrice = arrayOf("Retail", "Wholesales")
        itemDelivery = arrayOf("Driver", "Post office", "Pickup")

        stylePriceTypeAdapter = ArrayAdapter(applicationContext, R.layout.textview_spinner, itemPrice)
        styleDeliveryAdapter = ArrayAdapter(applicationContext, R.layout.textview_spinner, itemDelivery)

        stylePriceTypeAdapter.setDropDownViewResource(R.layout.spinner_textview)
        styleDeliveryAdapter.setDropDownViewResource(R.layout.spinner_textview)

        goodsPriceTypes.adapter = stylePriceTypeAdapter
        goodsDeliveryTypes.adapter = styleDeliveryAdapter


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_goods_update)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)


        calendar.add(Calendar.YEAR, 0)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        goodsDeliveryData.setOnClickListener {
            val calendarPopUp = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayofMonth ->

                    val months = getMonth((monthOfYear + 1))
                    goodsDeliveryData.setText("$dayofMonth/$months/$year")

                },
                year,
                month,
                day
            )
            calendarPopUp.datePicker.minDate = calendar.timeInMillis
            calendarPopUp.show()
        }

        val goodsType = intent.getStringExtra("type")
        if (goodsType.equals("Edit")) {
            val goodsTitle = intent.getStringExtra("goodsName")
            val goodsQuantity = intent.getIntExtra("amountGoods", -1)
            val goodsNameCust = intent.getStringExtra("customerName")
            val goodsPhoneCust = intent.getStringExtra("customerPhone")
            val goodsCurrent = intent.getStringExtra("currentData")
            val goodsData = intent.getStringExtra("deliveryData")
            val goodsAddres = intent.getStringExtra("address")
            goodsDelivery = intent.getStringExtra("deliveryType")!!.trim()
            goodsPriceType = intent.getStringExtra("priceType")!!.trim()
            val goodsPrices = intent.getStringExtra("price")
            val goodsDescription = intent.getStringExtra("goodsNote")
            goodsId = intent.getIntExtra("idGoods", -1)
            customerId = intent.getIntExtra("idCustomer", -1)
            saveBtn.text = "Update data"
            goodsName.setText(goodsTitle)
            goodsAmount.setText(goodsQuantity.toString())
            goodsCustomerName.text = goodsNameCust
            goodsCustomerPhone.text = goodsPhoneCust
            goodsCurrentData.text = goodsCurrent
            goodsDeliveryData.setText(goodsData)
            goodsAddress.setText(goodsAddres)
            goodsPrice.setText(goodsPrices)
            goodsNote.setText(goodsDescription)
            goodsDeliveryTypes.setSelection(getIngex(goodsDeliveryTypes, goodsDelivery))
            goodsPriceTypes.setSelection(getIngex(goodsPriceTypes, goodsPriceType))
        } else {
            saveBtn.text = "Save data"
        }

        goodsDeliveryTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                goodsDeliveryTypesString = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        goodsPriceTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                goodsPriceTypesString = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        saveBtn.setOnClickListener {

            val goodsTitle = goodsName.text.toString()
            val goodsQuantity = goodsAmount.text.toString().toInt()
            val goodsNameCust = goodsCustomerName.text.toString()
            val goodsPhoneCust = goodsCustomerPhone.text.toString()
            val goodsCurrent = goodsCurrentData.text.toString()
            val goodsData = goodsDeliveryData.text.toString()
            val goodsAddres = goodsAddress.text.toString()
            val goodsPrices = goodsPrice.text.toString()
            val goodsDescription = goodsNote.text.toString()

            if(ExceptionEmpty(goodsTitle, "Goods name") && (ExceptionEmpty(goodsQuantity.toString(), "Amount of the goods") &&
                        ExceptionNumber(goodsQuantity.toString(), "Amount of the goods")) && ExceptionEmpty(goodsData, "Delivery date") &&
                        ExceptionEmpty(goodsAddres, "Address") && (ExceptionEmpty(goodsPrices, "Price of the goods") && ExceptionNumber(goodsPrices, "Price of the goods"))) {
                val updateGoods = Goods(
                    goodsTitle, goodsQuantity, goodsNameCust, goodsPhoneCust, goodsCurrent, goodsData,
                    goodsAddres, goodsDeliveryTypesString, goodsPriceTypesString, goodsPrices, goodsDescription, customerId
                )
                updateGoods.id_goods = goodsId
                viewModal.updateGoods(updateGoods)
                this.finish()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getMonth(month: Int): String? {
        return DateFormatSymbols().getMonths().get(month - 1)
    }

    fun getIngex(spinner: Spinner, s:String):Int{
        for(i:Int in 0..spinner.count){
            if(spinner.getItemAtPosition(i).toString().equals(s,false)){
                return i
            }
        }
        return 0
    }

    fun ExceptionEmpty(text:String, name:String): Boolean{
        if(text.isNotEmpty()){
            return true
        }else{
            Toast.makeText(applicationContext, "${name} is empty", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun ExceptionNumber(text:String, name:String): Boolean {
        var answer = true
        try {
            parseDouble(text)
        }catch (e:NumberFormatException){
            Toast.makeText(applicationContext, "${name} should contains only numbers", Toast.LENGTH_LONG).show()
            answer = false
        }
        return answer
    }
}