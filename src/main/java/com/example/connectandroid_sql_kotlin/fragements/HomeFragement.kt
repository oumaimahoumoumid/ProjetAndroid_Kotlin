package com.example.connectandroid_sql_kotlin.fragements

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.connectandroid_sql_kotlin.MainActivity
import com.example.connectandroid_sql_kotlin.PlantModel
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.plantList
import com.example.connectandroid_sql_kotlin.R
import com.example.connectandroid_sql_kotlin.fragements.adapter.PlantAdapter
import com.example.connectandroid_sql_kotlin.fragements.adapter.PlantItemDecoration

class HomeFragement(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view= inflater?.inflate(R.layout.fragment_home, container, false)

        // recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context,plantList.filter { !it.liked },R.layout.item_horizontal_plant)

        //recuperer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context,plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }

}