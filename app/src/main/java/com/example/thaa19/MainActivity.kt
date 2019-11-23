package com.example.thaa19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thaa19.AnimationScreen.Companion.FILE_NUM
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {


    val CURRENT_NUM = 20

    private var convList: ArrayList<Conversation>? = null
    private var adapter: ConvListAtapter? = null
    private var layoutManger: RecyclerView.LayoutManager? = null
    var talkList=ArrayList<Talker>()
    var jsonString=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_main)

        retriveDataFromFirebase()
        Handler().postDelayed(
            {
                createJustFirstTalk()
            },5000
        )

        /*     initAll()
                  recyclerView.layoutManager = layoutManger
                 recyclerView.adapter = adapter
                 adapter!!.notifyDataSetChanged()

                 operateConverastion(Conversation(1,"stam","stam")) */
    }


    private fun retriveDataFromFirebase() {
        talkList= arrayListOf()


        var db = FirebaseFirestore.getInstance()
        // Log.d("clima","db="+db.toString())
        db.collection("talker1").document("3").get().addOnCompleteListener { task ->

            // Log.d("clima","inside")
            if (task.result?.exists()!!) {
                jsonString = task.result!!.getString("main")!!

                // createTalkArray(jsonString)

            } else {
                Toast.makeText(
                    this,
                    "Not Find because ${task.exception?.message} ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun createTalkArray(jsonString: String?) {
        //  Log.d("clima",jsonString)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Talker>>() {}.type
        talkList = gson.fromJson(jsonString, type)
        Log.d("clima","${talkList[17].taking}")

    }


    private fun createJustFirstTalk() {
        val intent = Intent(this, AnimationScreen::class.java)
        intent.putExtra(FILE_NUM, 20)
        intent.putExtra("jsonString",jsonString)
        // Log.d("clima",jsonString)
        startActivity(intent)

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

