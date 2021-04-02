package com.example.connectandroid_sql_kotlin.fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.connectandroid_sql_kotlin.MainActivity
import com.example.connectandroid_sql_kotlin.PlantRepository.Singleton.plantList
import com.example.connectandroid_sql_kotlin.R
import com.example.connectandroid_sql_kotlin.fragements.adapter.PlantAdapter
import com.example.connectandroid_sql_kotlin.fragements.adapter.PlantItemDecoration

class CollectionFragment(
        private val context:MainActivity
) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater?.inflate(R.layout.fragment_collection,container,false)

        //recuperer ma recyclerview
        val collectionRecylrtView=view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecylrtView.adapter=PlantAdapter(context,plantList.filter { it.liked }, R.layout.item_vertical_plant)
        collectionRecylrtView.layoutManager=LinearLayoutManager(context)
        collectionRecylrtView.addItemDecoration(PlantItemDecoration())
        return view
    }
}