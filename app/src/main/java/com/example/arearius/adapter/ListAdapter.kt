package com.example.arearius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.AnalysisData
import com.example.arearius.databinding.ItemListBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var coinList = listOf<AnalysisData>()

    inner class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(analysisData: AnalysisData) {
            binding.text01.text = analysisData.data.attributes.htmlMeta.toString()
            binding.text02.text = analysisData.data.id
            binding.text02.text = analysisData.data.attributes.totalVotes.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(coinList[position]) // 해당 위치의 데이터로 bind 메서드 호출
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setList(list: List<AnalysisData>) {
        coinList = list
        notifyDataSetChanged() // 데이터가 변경될 때 RecyclerView에 알리도록 추가
    }
}
