package com.example.arearius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.ApiData
import com.example.arearius.databinding.ItemListBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var coinList = listOf<ApiData>()

    inner class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.text01.text = coinList[pos].korean_name
            binding.text02.text = coinList[pos].english_name
            binding.text03.text = coinList[pos].market
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setList(list: List<ApiData>) {
        coinList = list
        notifyDataSetChanged() // 데이터가 변경될 때 RecyclerView에 알리도록 추가
    }
}
