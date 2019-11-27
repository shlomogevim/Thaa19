package com.example.thaa19

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thaa19.Helper.Companion.CURRENT_VERSIA
import com.example.thaa19.Helper.Companion.FILE_NUM
import com.example.thaa19.Helper.Companion.JSONSTRING
import com.example.thaa19.Helper.Companion.PREFS_NAME
import com.example.thaa19.Helper.Companion.REQEST_CODE
import com.example.thaa19.Helper.Companion.TALKLIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    // val TALKLIST="talklist20"

    private var convList: ArrayList<Conversation>? = null
    private var adapter: ConvListAtapter? = null
    private var layoutManger: RecyclerView.LayoutManager? = null
    var talkList = ArrayList<Talker>()

    lateinit var shar: ShareData

    //val PREFS_NAME = "myPrefs"
    //lateinit var myPref: SharedPreferences
    // lateinit var helper:Helper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_main)

        shar = ShareData(this)

/*
         myPref = getSharedPreferences(PREFS_NAME, 0)
         val jsonString = myPref.getString(TALKLIST, null)
*/


       var jsonS = shar.getGsonString()

       // jsonString=null


        if (jsonS != null) {
            createJustFirstTalk(jsonS)
        } else {
            jsonS=retriveDataFromFirebase()

            Handler().postDelayed(
                {
                    shar.saveJsonString(jsonS)
                    createJustFirstTalk(jsonS)
                }, 5000
            )
        }

       // initPara()
    }

    private fun createJustFirstTalk(jsonS: String?) {
        val intent = Intent(this, AnimationScreen::class.java)
        intent.putExtra(JSONSTRING, jsonS)
        startActivityForResult(intent, REQEST_CODE)
    }

    private fun retriveDataFromFirebase():String {
        var jsonS=""

        var db = FirebaseFirestore.getInstance()
        db.collection("talker1").document("3").get().addOnCompleteListener { task ->

            if (task.result?.exists()!!) {
                jsonS = task.result!!.getString("main")!!

            } else {
                jsonS = "none"
                Toast.makeText(
                    this,
                    "Not Find because ${task.exception?.message} ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return jsonS
    }





    private fun initPara() {
        initAll()
        recyclerView.layoutManager = layoutManger
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()

        operateConverastion(Conversation(1,"stam","stam"))
    }
    private fun createTalkArray(jsonS: String?) {
        //  Log.d("clima",jsonS)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Talker>>() {}.type
        talkList = gson.fromJson(jsonS, type)
        Log.d("clima", "${talkList[17].taking}")

    }

    private fun initAll() {
        convList = arrayListOf()

        var st1 = ""
        var st2 = ""
        val st3 = "\n"
        for (i in 1..30) {

            when (i) {

                1 -> {
                    st1 = "כן ולא"
                    val st20 = "ישנם שלש תשובות"
                    val st21 = "כן, לא"
                    val st22 = "והתשובה השלישית היא כן ולא"
                    val st23 = "עליה השיחה"
                    st2 = st20 + st3 + st21 + st3 + st22 + st3 + st23
                }
                else -> {
                    st1 = "talk num. $i"
                    st2 = "still not prepared"
                }
            }
            val conv = Conversation(i, st1, st2)
            convList?.add(conv)
        }

        layoutManger = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = ConvListAtapter(convList!!, onClickListener =
        { conversation ->
            operateConverastion(conversation)

            // Toast.makeText(this, "You press on Item no ${conversation.numC}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun operateConverastion(conversation: Conversation) {
        var CURRENT_NUM = 0

        when (conversation.numC) {
            1 -> CURRENT_NUM = 20


        }

        val intent = Intent(this, AnimationScreen::class.java)
        intent.putExtra(FILE_NUM, CURRENT_NUM)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val jsonS=""

        val message = data?.getStringExtra("mes")
        Log.d("clima", "Reqest code = " + requestCode)
        Log.d("clima", "Result code = " + resultCode)
        Log.d("clima", "Message returned = " + message)


        if ((requestCode == REQEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            val versia = data?.extras?.getInt(CURRENT_VERSIA, 0)
            val jsonS = data?.getStringExtra(JSONSTRING)
            versia?.let { storeJsonStringInTheFirebase(it, jsonS) }
        }


    }


    /*
    * @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String messageReturned = data.getStringExtra("message_return");

        System.out.println("Result code = " + resultCode);
        System.out.println("Message returned = " + messageReturned);

    }*/

    private fun storeJsonStringInTheFirebase(versia: Int, jsonS: String?) {


        var db = FirebaseFirestore.getInstance()
        var talker = HashMap<String, Any>()


        talker.put("index", versia.toString())
        jsonS?.let { talker.put("main", it) }
        db.collection("talker1").document(versia.toString()).set(talker)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Saving is succsses", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this,
                        "Not Save because \${task.exception?.message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


    }
}


/* val CURRENT_NUM = 20

 private var convList: ArrayList<Conversation>? = null
 private var adapter: ConvListAtapter? = null
 private var layoutManger: RecyclerView.LayoutManager? = null


 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)
     //initAll()
     selectConversation()
     recyclerView.layoutManager = layoutManger
     recyclerView.adapter = adapter
     adapter!!.notifyDataSetChanged()
}


 private fun initAll() {
     convList = arrayListOf()

     var st1 = ""
     var st2 = ""
     val st3 = "\n"
     for (i in 1..30) {

         when (i) {

             1 -> {
                 st1 = "כן ולא"
                 val st20 = "ישנם שלש תשובות"
                 val st21 = "כן, לא"
                 val st22 = "והתשובה השלישית היא כן ולא"
                 val st23 = "עליה השיחה"
                 st2 = st20 + st3 + st21 + st3 + st22 + st3 + st23
             }
             else -> {
                 st1 = "talk num. $i"
                 st2 = "still not prepared"
             }
         }
         val conv = Conversation(i, st1, st2)
         convList?.add(conv)
     }

     layoutManger = LinearLayoutManager(this)
     adapter = ConvListAtapter(convList!!, onClickListener =
     { conversation ->
         operateConverastion(conversation)

         // Toast.makeText(this, "You press on Item no ${conversation.numC}", Toast.LENGTH_SHORT).show()
     })
 }

 private fun operateConverastion(conversation: Conversation) {
     var CURRENT_NUM = 0

     when (conversation.numC) {
         1 -> CURRENT_NUM = 20

     }


     val intent=Intent(this,AnimationScreen::class.java)
     intent.putExtra(FILE_NUM, CURRENT_NUM)
     startActivity(intent)
 }

 private fun selectConversation() {
     val  CURRENT_NUM = 20
     val intent=Intent(this,AnimationScreen::class.java)
     intent.putExtra(FILE_NUM, CURRENT_NUM)
     startActivity(intent)
 }

}
*/

