package com.example.tam_tradeagent.views

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.R.id
import com.example.tam_tradeagent.model.Goods
import com.example.tam_tradeagent.viewmodels.CustomerViewModal
import java.lang.Double
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class FragmentAddEditGoods:AppCompatActivity() {

    lateinit var goodsName: EditText
    lateinit var goodsAmount: EditText
    lateinit var goodsCurrentData: EditText
    lateinit var goodsDeliveryData: EditText
    lateinit var goodsAddress: EditText
    lateinit var goodsPrice: EditText
    lateinit var goodsNote: EditText

    lateinit var spinnerName: Spinner
    lateinit var spinnerPhone: Spinner
    lateinit var goodsDeliveryTypes: Spinner
    lateinit var goodsPriceTypes: Spinner

    lateinit var saveBtn: Button
    lateinit var nameButton: ImageButton
    lateinit var phoneButton: ImageButton

    lateinit var customerNameList: List<String>
    lateinit var customerNameMutableList: MutableList<String>
    lateinit var customerPhoneList: List<String>
    lateinit var customerPhoneMutableList: MutableList<String>

    lateinit var itemPrice: Array<String>
    lateinit var itemDelivery: Array<String>
    lateinit var nameCustomer: String
    lateinit var phoneCustomer: String
    lateinit var goodsCurrentDatas: String
    lateinit var goodsPriceTypesString: String
    lateinit var goodsDeliveryTypesString: String

    lateinit var nameAdapter: ArrayAdapter<Any>
    lateinit var phoneAdapter: ArrayAdapter<Any>
    lateinit var styleDeliveryAdapter: ArrayAdapter<Any>
    lateinit var stylePriceTypeAdapter: ArrayAdapter<Any>

    lateinit var viewModal: CustomerViewModal
    var customerId = -1
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_goods)
        viewModal = ViewModelProvider(this).get(CustomerViewModal::class.java)

        goodsName = findViewById(id.goods_name)
        goodsAmount = findViewById(id.goods_amount)
        spinnerName = findViewById(id.goods_goodsNameCustomer)
        spinnerPhone = findViewById(id.goods_goodsPhoneCustomer)
        goodsCurrentData = findViewById(id.currentDate_goods)
        goodsDeliveryData = findViewById(id.deliveryDate_goods)
        goodsAddress = findViewById(id.address_goods)
        goodsDeliveryTypes = findViewById(id.typeOf_delivery_goods)
        goodsPriceTypes = findViewById(id.typeOf_price_goods)
        goodsPrice = findViewById(id.price_goods)
        goodsNote = findViewById(id.notes_goods)
        saveBtn = findViewById(id.button_goods)
        nameButton = findViewById(id.update_deliveryName_goods)
        phoneButton = findViewById(id.update_deliveryPhone_goods)
        phoneButton.isEnabled = false

        customerNameList = emptyList()
        customerNameMutableList = emptyList<String>().toMutableList()
        customerPhoneList = emptyList()
        customerPhoneMutableList = emptyList<String>().toMutableList()

        itemPrice = arrayOf("Retail", "Wholesales")
        itemDelivery = arrayOf("Driver", "Post office", "Pickup")
        nameCustomer = "null"
        phoneCustomer = "null"

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(id.toolbar_goods)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        nameAdapter = ArrayAdapter(applicationContext, R.layout.textview_spinner, customerNameList)
        phoneAdapter =
            ArrayAdapter(applicationContext, R.layout.textview_spinner, customerPhoneList)
        stylePriceTypeAdapter =
            ArrayAdapter(applicationContext, R.layout.textview_spinner, itemPrice)
        styleDeliveryAdapter =
            ArrayAdapter(applicationContext, R.layout.textview_spinner, itemDelivery)

        nameAdapter.setDropDownViewResource(R.layout.spinner_textview)
        phoneAdapter.setDropDownViewResource(R.layout.spinner_textview)
        stylePriceTypeAdapter.setDropDownViewResource(R.layout.spinner_textview)
        styleDeliveryAdapter.setDropDownViewResource(R.layout.spinner_textview)

        spinnerName.adapter = nameAdapter
        spinnerPhone.adapter = phoneAdapter
        goodsPriceTypes.adapter = stylePriceTypeAdapter
        goodsDeliveryTypes.adapter = styleDeliveryAdapter

        goodsDeliveryTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                goodsDeliveryTypesString = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        goodsPriceTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                goodsPriceTypesString = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        spinnerName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                nameCustomer = p0!!.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        spinnerPhone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                phoneCustomer = p0!!.getItemAtPosition(p2).toString()
                println(getIdCustomer(nameCustomer, phoneCustomer))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        nameButton.setOnClickListener {
            viewModal.allName.observe(this, Observer { list ->
                list?.let {
                    if (customerNameList != it && !customerNameList.isNotEmpty()) {
                        customerNameList = it
                        nameAdapter = ArrayAdapter(
                            applicationContext,
                            R.layout.textview_spinner,
                            customerNameList
                        )
                        spinnerName.adapter = nameAdapter
                        phoneButton.isEnabled = true
                    } else {
                        Toast.makeText(this, "List is same or empty", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        phoneButton.setOnClickListener {
            viewModal.getPhoneCustomer(nameCustomer).observe(this, Observer { list ->
                list?.let {
                    if (customerPhoneList != it){
                        customerPhoneList = it
                        phoneAdapter = ArrayAdapter(
                            applicationContext,
                            R.layout.textview_spinner,
                            customerPhoneList
                        )
                        spinnerPhone.adapter = phoneAdapter
                    } else {
                        Toast.makeText(this, "List is same or empty", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        val sdf = SimpleDateFormat("dd/MMMM/yyyy")
        goodsCurrentDatas = sdf.format(Date())
        goodsCurrentData.setText(goodsCurrentDatas)

        goodsDeliveryData.setOnClickListener {

            calendar.add(Calendar.YEAR, 0)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

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

        saveBtn.setOnClickListener {
            val goodsNames = goodsName.text.toString()
            val goodsAmounts = goodsAmount.text.toString()
            val goodsAddress = goodsAddress.text.toString()
            val goodsNotes = goodsNote.text.toString()
            val goodsPrices = goodsPrice.text.toString()

            if (ExceptionEmpty(goodsNames, "Name") && (ExceptionEmpty(
                    goodsAmounts,
                    "Amount"
                ) && ExceptionNumber(goodsAmounts, "Amount")) &&
                ExceptionEmpty(nameCustomer, "Customer name") && ExceptionEmpty(
                    phoneCustomer,
                    "Customer phone"
                ) &&
                ExceptionEmpty(
                    goodsDeliveryData.text.toString(),
                    "Delivery date"
                ) && ExceptionEmpty(goodsAddress, "Address") && (ExceptionEmpty(
                    goodsPrices,
                    "Price"
                ) && ExceptionNumber(goodsPrices, "Price"))
            ) {
                viewModal.addGoods(
                    Goods(goodsNames, goodsAmounts.toInt(), nameCustomer, phoneCustomer,
                        goodsCurrentDatas, goodsDeliveryData.text.toString(), goodsAddress,
                        goodsDeliveryTypesString, goodsPriceTypesString, goodsPrices, goodsNotes, customerId)
                )
                this.finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_addgoods, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_addcustomer){
            moveToAddCustomer()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun moveToAddCustomer(){
        startActivity(Intent(this, FragmentAddEditCustomer::class.java ))
    }

    fun ExceptionEmpty(text: String, name: String): Boolean {
        if (text.isEmpty()) {
            Toast.makeText(applicationContext, "${name} is empty", Toast.LENGTH_LONG).show()
            return false
        } else {
            if(text.equals("null")){
                Toast.makeText(applicationContext, "${name} is empty", Toast.LENGTH_LONG).show()
                return false
            }else {
                return true
            }
        }
    }

    fun ExceptionNumber(text: String, name: String): Boolean {
        var answer = true
        try {
            Double.parseDouble(text)
        } catch (e: NumberFormatException) {
            Toast.makeText(
                applicationContext,
                "${name} should contains only numbers",
                Toast.LENGTH_LONG
            ).show()
            answer = false
        }
        return answer
    }

    fun getIdCustomer(name: String, phone: String) {
        viewModal.getNameIdCustomer(name, phone).observe(this, Observer {
            customerId = it
        })
    }

    fun getMonth(month: Int): String? {
        return DateFormatSymbols().getMonths().get(month - 1)
    }
}




