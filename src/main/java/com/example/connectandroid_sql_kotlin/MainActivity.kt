package com.example.connectandroid_sql_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.connectandroid_sql_kotlin.fragements.AddPlantFragment
import com.example.connectandroid_sql_kotlin.fragements.CollectionFragment
import com.example.connectandroid_sql_kotlin.fragements.HomeFragement
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragement(this), R.string.home_page_title)
        //importer le bottomnavigationview
        val navigationView=findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home_page ->{
                    loadFragment(HomeFragement(this), R.string.home_page_title)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.collection_page ->{
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.add_plant_page ->{
                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemReselectedListener
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragement: Fragment, string: Int) {
        // charger notre repository
        val repo=PlantRepository()

        //actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text=resources.getString(string)

        //mettre à jour la liste de plantes
        repo.upodateData {
            val transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragement)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}