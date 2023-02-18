package com.example.tam_tradeagent.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tam_tradeagent.R
import com.example.tam_tradeagent.model.Customer
import com.example.tam_tradeagent.viewmodels.CustomerViewModal


class FragmentAddEditCustomer:AppCompatActivity() {

    lateinit var customerName: EditText
    lateinit var customerEmail: EditText
    lateinit var customerPhone: EditText
    lateinit var customerNote: EditText
    lateinit var saveBtn: Button

    lateinit var viewModal: CustomerViewModal
    var customerId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_customer)
        customerName = findViewById(R.id.name_customer_add)
        customerEmail = findViewById(R.id.email_customer_add)
        customerPhone = findViewById(R.id.phone_customer_add)
        customerNote = findViewById(R.id.notes_customer_add)
        saveBtn = findViewById(R.id.button_customer_add)

        viewModal = ViewModelProvider(
            this
        ).get(CustomerViewModal::class.java)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_customer)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val customerType = intent.getStringExtra("type")
        if (customerType.equals("Edit")) {
            val customerTitle = intent.getStringExtra("name")
            val customerMail = intent.getStringExtra("email")
            val customerNumber = intent.getStringExtra("phone")
            val customerDescription = intent.getStringExtra("note")
            customerId = intent.getIntExtra("id_customer", -1)
            saveBtn.setText("Update data")
            customerName.setText(customerTitle)
            customerNote.setText(customerDescription)
            customerEmail.setText(customerMail)
            customerPhone.setText(customerNumber)
        } else {
            saveBtn.setText("Save data")
        }

        saveBtn.setOnClickListener {

            val customerTitle = customerName.text.toString()
            val customerMail = customerEmail.text.toString()
            val customerNumber = customerPhone.text.toString()
            val customerDescription = customerNote.text.toString()

            if (customerType.equals("Edit")) {
                if (ExceptionEmpty(customerTitle, "Customer name") && ExceptionMail(customerMail, "Mail") && ExceptionPhoneNumber(customerNumber)) {
                    val updateCustomer =
                        Customer(customerTitle, customerMail, customerNumber, customerDescription)
                    updateCustomer.id_customer = customerId
                    viewModal.updateCustomer(updateCustomer)
                    Toast.makeText(applicationContext, "${customerTitle} edited", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                if (ExceptionEmpty(customerTitle, "Customer name") && ExceptionMail(customerMail, "Mail") && ExceptionPhoneNumber(customerNumber)) {
                    viewModal.addCustomer(
                        Customer(
                            customerTitle,
                            customerMail,
                            customerNumber,
                            customerDescription
                        )
                    )
                    Toast.makeText(applicationContext, "${customerTitle} Added", Toast.LENGTH_LONG).show()
                    this.finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun ExceptionEmpty(text:String, name:String): Boolean{
        if(text.isNotEmpty()){
            return true
        }else{
            Toast.makeText(applicationContext, "${name} is empty", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun ExceptionMail(text:String, name:String):Boolean {
        if (text.isEmpty()) {
            Toast.makeText(applicationContext, "Mail is empty", Toast.LENGTH_LONG).show()
            return false
        } else {
            if (text.contains("@") && text.indexOf("@") !== text.length && text.indexOf("@") !== 0) {
                return true
            } else {
                Toast.makeText(applicationContext, "${name} isn't mail", Toast.LENGTH_LONG).show()
                return false
            }
        }
    }

    fun ExceptionPhoneNumber(text:String):Boolean {

        if (text.isEmpty()) {
            Toast.makeText(applicationContext, "PhoneNumber is empty", Toast.LENGTH_LONG).show()
            return false
        } else {
            if (text.contains("^\\+[0-9]{8,20}\$".toRegex())) {
                return true
            } else {
                Toast.makeText(
                    applicationContext,
                    "Phone number isn't correspond requirements",
                    Toast.LENGTH_LONG
                ).show()
                return false
            }
        }
    }
}