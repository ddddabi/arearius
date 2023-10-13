package com.example.arearius

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MainListAdapter (val context: Context, val itemlist : ArrayList<item>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.main_lv_item, null)
        val processname = view.findViewById<TextView>(R.id.processname)
        val itemIcon = view.findViewById<ImageView>(R.id.AppImg)

        val positem = itemlist[p0]
        processname.text = positem.name
        itemIcon.setImageDrawable(positem.image)
        return view
    }

    override fun getItem(p0: Int): Any {
        return itemlist[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return itemlist.size
    }
}
