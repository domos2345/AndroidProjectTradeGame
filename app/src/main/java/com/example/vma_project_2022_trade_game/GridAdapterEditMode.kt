package com.example.vma_project_2022_trade_game

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.R.color

class GridAdapterEditMode(val context: Context, var texts : List<String>) : BaseAdapter() {


    override fun getCount(): Int {
       return texts.size
    }

    override fun getItem(p0: Int): Any {
       return texts[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val text = texts[p0]
        var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.grid_item,null)
        view.findViewById<TextView>(R.id.gridItemText).text = text

        //view.findViewById<TextView>(R.id.gridItemText).setBackgroundResource(R.color.black)
        return view


    }
}