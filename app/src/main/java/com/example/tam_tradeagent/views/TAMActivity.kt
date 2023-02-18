package com.example.tam_tradeagent.views


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tam_tradeagent.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TAMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tam)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val fragmentCompetitors = FragmentCompetitors()
        val fragmentGoodsOrder = FragmentGoodsOrder()

        makeCurrentFragment(fragmentGoodsOrder)

        bottom_navigation.setOnItemReselectedListener{
            when(it.itemId){
                R.id.orders ->{
                    makeCurrentFragment(fragmentGoodsOrder)
                    true
                }
                R.id.customer ->{
                    makeCurrentFragment(fragmentCompetitors)
                    true}
                else ->false
            }
        }
    }

    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.nav_controller_view, fragment)
            commit()
        }
}
