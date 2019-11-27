package com.example.thaa19

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.thaa19.Helper.Companion.ASSEETS_FILE
import com.example.thaa19.Helper.Companion.CURRENT_SPEAKER
import com.example.thaa19.Helper.Companion.PREFS_NAME
import com.example.thaa19.Helper.Companion.TALKLIST
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


    class ShareData(val context: Context) : AppCompatActivity() {


        lateinit var talkList: ArrayList<Talker>


        /* myPref = getSharedPreferences(PREFS_NAME, 0)
        val jsonString = myPref.getString(TALKLIST, null)
            // val jsonString=shar.getGsonString()
        */







      //  var myPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


        var myPref=context.getSharedPreferences(PREFS_NAME,0)
        var editor= myPref.edit()




        fun savePage(page:Int){
            editor.putInt(CURRENT_SPEAKER, page)
            editor.commit()
        }
        fun getPage():Int = myPref.getInt(CURRENT_SPEAKER, 1)

        fun getGsonString() = myPref.getString(TALKLIST, null)


        fun getTalkingListFromPref(ind:Int): ArrayList<Talker> {
            val talkList1: ArrayList<Talker>
            val gson = Gson()
            // val jsonString = myPref.getString(TALKLIST, null)
            val jsonString = myPref.getString(TALKLIST, null)

            if (ind==0 || jsonString == null) {
                talkList1=createTalkListFromTheStart()
                saveTalkingListInPref(talkList1)

            } else {
                val type = object : TypeToken<ArrayList<Talker>>() {}.type
                talkList1 = gson.fromJson(jsonString, type)
            }
            //   talkList1=improveTalkList(talkList1)
            return talkList1
        }

        fun saveTalkingListInPref(talkingList:ArrayList<Talker>) {
            val gson = Gson()
            val jsonString = gson.toJson(talkingList)
            editor.putString(TALKLIST, jsonString)
            editor.commit()
        }

        fun saveJsonString(jsonS:String?){
            editor.putString(TALKLIST, jsonS)
            editor.commit()
        }



       fun createTalkListFromTheStart(): ArrayList<Talker> {
            var talkList1 = arrayListOf<Talker>()
            val ADAM = "-אדם-"
            val GOD = "-אלוהים-"
            val currenteFile = "text/text"+ ASSEETS_FILE.toString()+".txt"

            var countItem = 0
            var text = context.assets.open(currenteFile).bufferedReader().use {
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

