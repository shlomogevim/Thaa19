package com.example.thaa19

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


    class ShareData(val context: Context, val fileNum:Int) : AppCompatActivity() {

        val TALKLIST="talklist"+fileNum.toString()
        lateinit var talkList: ArrayList<Talker>
        lateinit var operateList: ArrayList<List<Int>>

        var myPref: SharedPreferences
        var editor: SharedPreferences.Editor

        init {
            myPref= PreferenceManager.getDefaultSharedPreferences(context)
            editor = myPref.edit()
        }

        private val filepath = "MyFileStorage"
        internal var myExternalFile: File? = null


        fun getTalkingList(ind:Int): ArrayList<Talker> {
            val talkList1: ArrayList<Talker>
            val gson = Gson()
            // val jsonString = myPref.getString(TALKLIST, null)
            val jsonString = myPref.getString(TALKLIST, null)

            if (ind==0 || jsonString == null) {
                talkList1=createTalkListFromTheStart()
                saveData(talkList1)

            } else {
                val type = object : TypeToken<ArrayList<Talker>>() {}.type
                talkList1 = gson.fromJson(jsonString, type)
            }
            //   talkList1=improveTalkList(talkList1)
            return talkList1
        }
        fun saveData(talkingList:ArrayList<Talker>) {
            val gson = Gson()
            val jsonString = gson.toJson(talkingList)
            editor.putString(TALKLIST, jsonString)
            editor.apply()
        }

        fun createTalkListFromTheStart(): ArrayList<Talker> {
            var talkList1 = arrayListOf<Talker>()
            val ADAM = "-אדם-"
            val GOD = "-אלוהים-"
            val CURRENT_FILE = "text/text"+fileNum.toString()+".txt"

            var countItem = 0
            var text = context.assets.open(CURRENT_FILE).bufferedReader().use {
                it.readText()
            }
            text = text.replace("\r", "")
            var list1 = text.split(ADAM)

            var talker = Talker()

            talkList1.add(countItem, talker)
            var i=0

            for (element in list1) {
                //  if (element != "" && element.length > 25) {
                if (element != "") {
                    i++
                    var list2 = element.split(GOD)
                    var st1 = improveString(list2[0])
                    var st2 = improveString(list2[1])
                    if (st1.isNullOrEmpty() || st2.isNullOrEmpty()){
                        return talkList1
                    }
                    countItem++
                    talker = Talker()
                    with(talker) {
                        whoSpeake = "man"
                        taking = st1.trim()
                        numTalker = countItem
                        var arr = st1.split("\n")
                        for (item in arr) {
                            if (item != "") {
                                takingArray.add(item)
                            }
                        }
                        colorText="#000000"
                        colorBack="#ffffff"
                        animNum=10
                    }

                    talkList1.add(talker)

                    countItem++
                    talker = Talker()
                    with (talker) {
                        whoSpeake = "god"
                        talker.taking = st2.trim()
                        talker.numTalker = countItem
                        var arr = st2.split("\n")
                        for (item in arr) {
                            if (item != "") {
                                takingArray.add(item)
                            }
                        }
                        colorText="#000000"
                        colorBack="#ffffff"
                        animNum=10
                    }
                    talkList1.add(talker)
                }
            }
            return talkList1
        }

        private fun improveString(st: String) = st.substring(1, st.length - 1)

        private fun createTalkArray(jsonString: String?) {
            //  Log.d("clima",jsonString)
            talkList= arrayListOf()
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList = gson.fromJson(jsonString, type)
            Log.d("clima","${talkList[19].taking}")

        }

    }

/*private fun retriveDataFromFirebase():String {
    var jsonString=""

    var db = FirebaseFirestore.getInstance()
    Log.d("clima","db="+db.toString())
    db.collection("talker1").document("3").get().addOnCompleteListener { task ->

        Log.d("clima","inside")
        if (task.result?.exists()!!) {
            jsonString = task.result!!.getString("main").toString()

            createTalkArray(jsonString)

            Toast.makeText(this, "Find", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                this,
                "Not Find because ${task.exception?.message} ",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    return jsonString
}*/

/*
           fun getTalkingList(ind:Int): ArrayList<Talker> {
               talkList= arrayListOf()

               Log.d("clima","one")
               val jsonString=retriveDataFromFirebase()

               *//*var talkList1: ArrayList<Talker>
        val gson = Gson()
       // val jsonString = myPref.getString(TALKLIST, null)
        val jsonString = myPref.getString(TALKLIST, null)*//*

        if (ind==0 || jsonString == null) {
            talkList=createTalkListFromTheStart()
            saveData(talkList)
        } else {
            createTalkArray(jsonString)

            *//*val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonString, type)*//*
        }
        return talkList
    }*/

/*  fun saveDataToExternalStorage(talkingList: ArrayList<Talker>) {
           try {
               myExternalFile = File(context.getExternalFilesDir(filepath), TALKLIST)

           } catch (e: Exception) {
               e.printStackTrace()
           }
           val gson = Gson()
           val jsonString = gson.toJson(talkingList)


           try {
               val fileOutPutStream = FileOutputStream(myExternalFile)
               fileOutPutStream.write(jsonString.toByteArray())
               fileOutPutStream.close()
           } catch (e: IOException) {
               e.printStackTrace()
           }
           // Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
       }

       fun getTalkingListFromExternalStorage(ind: Int): ArrayList<Talker> {
           var talkList1: ArrayList<Talker> = arrayListOf()
           var jsonString=""
           val gson = Gson()
           // myExternalFile = File(context.getExternalFilesDir(filepath), TALKLIST)

           //storage/emulated/0/Android/data/com.example.yhaa18/files/MyFileStorage/talklist20
           val st="/storage/emulated/0/Android/data/com.example.yhaa17/files/MyFileStorage"
           val st1=st+"/talklist20"
           myExternalFile = File(st, TALKLIST)


           if (TALKLIST != null) {
               var fileInputStream= FileInputStream(myExternalFile)
               var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
               val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
               val stringBuilder: StringBuilder = StringBuilder()
               var text: String? = null

               while ({ text = bufferedReader.readLine(); text }() != null) {
                   stringBuilder.append(text)
               }
               jsonString=stringBuilder.toString()

               //      while ({ jsonString = bufferedReader.readLine(); text }() != null)

               if (ind == 0 || jsonString == null) {
                   talkList1 = createTalkListFromTheStart()
                   saveData(talkList1)

               } else {
                   val type = object : TypeToken<ArrayList<Talker>>() {}.type
                   talkList1 = gson.fromJson(jsonString, type)
               }

               //           fileInputStream.close()
           }

           return talkList1

       }*/


