package com.example.arearius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.databinding.ItemListBinding
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var fileList = listOf<FileAnalysisData>()

    inner class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileanalysisData : FileAnalysisData){
            binding.apkLabel.text = fileanalysisData.data.attributes.meaningfulName
            binding.packLabel.text = fileanalysisData.data.attributes.androguard.Package
            binding.sizeLabel.text = fileanalysisData.data.attributes.size.toString()
            binding.permissionLabel.text = fileanalysisData.data.attributes.androguard.permissionDetails.toString()
            binding.md5Label.text = fileanalysisData.data.attributes.md5
            binding.analysisLabel.text = formatDate(fileanalysisData.data.attributes.lastAnalysisDate)
            binding.submissionLabel.text = formatDate(fileanalysisData.data.attributes.lastSubmissionDate)
        }
        private fun formatDate(date: Long): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val parsedDate = simpleDateFormat.parse(date.toString())
            return parsedDate?.let { simpleDateFormat.format(it) } ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 해당 위치의 데이터로 bind 메서드 호출
        holder.bind(fileList[position])

    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    fun setList(list: List<FileAnalysisData>) {
        fileList = list
        notifyDataSetChanged() // 데이터가 변경될 때 RecyclerView에 알리도록 추가
    }
}

