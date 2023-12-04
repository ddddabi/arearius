package com.example.arearius.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.data.ScanResult
import com.example.arearius.databinding.ItemListBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MetaListAdapter : RecyclerView.Adapter<MetaListAdapter.MyViewHolder>(){
    private var dataList: List<ScanResult> = emptyList()
    inner class MyViewHolder(val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun setList(list: List<ScanResult>) {
        dataList = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = dataList[position]
        holder.binding.apkLabel.text = currentData.fileInfo.displayName
        holder.binding.packLabel.visibility = View.GONE
        holder.binding.sizeLabel.text = formatFileSize(currentData.fileInfo.fileSize)
        holder.binding.md5Label.text = currentData.fileInfo.md5
        holder.binding.permissionLabel.visibility = View.GONE
        holder.binding.resuldetailttxt.text = currentData.scanResults.totalDetectedAvs.toString() + " / " + currentData.scanResults.totalAvs.toString()

        if(currentData.scanResults.totalDetectedAvs > 5){
            holder.binding.resulttxt.text = "악성"
        }else{
            holder.binding.resulttxt.text = "정상"
        }
    }

}

    private fun formatTimestamp(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val parsedDate = inputFormat.parse(timestamp)
        return outputFormat.format(parsedDate)
    }
    private fun formatFileSize(sizeInBytes: Int): String {
        val kiloByte = sizeInBytes / 1024.0
        val megaByte = kiloByte / 1024.0
        val gigaByte = megaByte / 1024.0

        return when {
            gigaByte >= 1.0 -> String.format("%.2f GB", gigaByte)
            megaByte >= 1.0 -> String.format("%.2f MB", megaByte)
            kiloByte >= 1.0 -> String.format("%.2f KB", kiloByte)
            else -> String.format("%d Bytes", sizeInBytes)
        }
    }
