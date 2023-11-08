package com.example.arearius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.databinding.ItemListBinding
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var fileList: List<FileAnalysisData> = emptyList()

    inner class MyViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = fileList[position]
        holder.binding.apkLabel.text = currentData.data.attributes.meaningfulName
        holder.binding.packLabel.text = currentData.data.attributes.androguard.Package
        holder.binding.sizeLabel.text = currentData.data.attributes.size.toString()
        holder.binding.permissionLabel.text = currentData.data.attributes.androguard.permissionDetails.toString()
        holder.binding.md5Label.text = currentData.data.attributes.md5
        holder.binding.analysisLabel.text = formatDate(currentData.data.attributes.lastAnalysisDate)
        holder.binding.submissionLabel.text = formatDate(currentData.data.attributes.lastSubmissionDate)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    fun setList(list: List<FileAnalysisData>) {
        fileList = list
        notifyDataSetChanged()
    }

    private fun formatDate(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val parsedDate = Date(date)
        return simpleDateFormat.format(parsedDate)
    }
}

