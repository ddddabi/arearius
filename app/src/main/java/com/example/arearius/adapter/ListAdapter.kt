package com.example.arearius.adapter

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.arearius.data.FileAnalysisData
import com.example.arearius.databinding.ItemListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var fileList: List<FileAnalysisData> = emptyList()
    inner class MyViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = fileList[position]
        val intent = (holder.itemView.context as? Activity)?.intent
        val appNameExtra = intent?.getStringExtra("appname")
        if (appNameExtra != null) {
            holder.binding.appname.text = appNameExtra
        } else {
            holder.binding.appname.text = currentData.data.attributes.meaningfulName
        }
        holder.binding.apkLabel.text = currentData.data.attributes.meaningfulName
        holder.binding.packLabel.text = currentData.data.attributes.androguard.Package
        holder.binding.sizeLabel.text = formatFileSize(currentData.data.attributes.size)
        holder.binding.permissionLabel.text = currentData.data.attributes.androguard.permissionDetails.keys.joinToString(System.lineSeparator())
        holder.binding.md5Label.text = currentData.data.attributes.md5
        holder.binding.sha1Label.text = currentData.data.attributes.sha1
        holder.binding.sha256Label.text = currentData.data.attributes.sha256
        holder.binding.resuldetailttxt.text = (currentData.data.attributes.lastAnalysisStats.harmless + currentData.data.attributes.lastAnalysisStats.malicious).toString() +
                " / " + (currentData.data.attributes.lastAnalysisStats.malicious +currentData.data.attributes.lastAnalysisStats.harmless +currentData.data.attributes.lastAnalysisStats.suspicious +currentData.data.attributes.lastAnalysisStats.undetected).toString()
        val totalSum = currentData.data.attributes.lastAnalysisStats.malicious +
                currentData.data.attributes.lastAnalysisStats.harmless +
                currentData.data.attributes.lastAnalysisStats.suspicious +
                currentData.data.attributes.lastAnalysisStats.undetected

        val maliciousHarmlessSum = currentData.data.attributes.lastAnalysisStats.malicious +
                currentData.data.attributes.lastAnalysisStats.harmless

        if (maliciousHarmlessSum > 5) {
            holder.binding.resulttxt.text = "악성"
            holder.binding.resulttxt.setTextColor(
                ContextCompat.getColor(holder.itemView.context, com.example.arearius.R.color.red)
            )
            holder.binding.resultBar.progress = (maliciousHarmlessSum.toFloat() / totalSum * 100).toInt()
            /*holder.binding.resultBar.progressDrawable =
                ContextCompat.getDrawable(holder.itemView.context, com.example.arearius.R.drawable.circular_progress)*/
        } else {
            holder.binding.resulttxt.text = "정상"
            holder.binding.resulttxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    com.example.arearius.R.color.green
                )
            )
            holder.binding.resultBar.progressDrawable = ContextCompat.getDrawable(
                holder.itemView.context,
                com.example.arearius.R.drawable.circular_progress_normal
            )
        }

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
}

