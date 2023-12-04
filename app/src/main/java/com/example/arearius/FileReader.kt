package com.example.arearius

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileReader {
    companion object {
        fun readTextFileString(assetManager: AssetManager, fileName: String) : String {
            val stringBuilder = StringBuilder()
            try{
                val inputStream = assetManager.open(fileName)
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while(bufferedReader.readLine().also { line = it } != null){
                    stringBuilder.append(line)
                }
                bufferedReader.close()
            } catch (e: IOException){
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

        fun readTextFileArray(assetManager: AssetManager, fileName : String): Pair<Array<String>, Array<String>>{
            val nameList = mutableListOf<String>()
            val moneyList = mutableListOf<String>()

            try {
                val inputStream = assetManager.open(fileName)
                val reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
                while (reader.readLine().also { line = it } != null){
                    val parts = line!!.split(" ")
                    if(parts.size == 2){
                        val name = parts[0]
                        var money = parts[1]
                        nameList.add(name)
                        moneyList.add(money)
                    }
                }

                reader.close()

            } catch (e : IOException){
                e.printStackTrace()
            }
            return Pair(nameList.toTypedArray(), moneyList.toTypedArray())
        }
    }
}