package com.example.thaa19

import android.content.Context
import android.graphics.Typeface

class Helper(context: Context) {

    val context1 = context



    companion object {
        const val TALKLIST="talklist20"
        const val ASSEETS_FILE=20
        const val FILE_NUM = "file_num"
        const val JSONSTRING = "jsonString"
        const val STARTALK = "start"
        const val REQEST_CODE = 12
        const val CURRENT_VERSIA = "currentversia"
        const val PREFS_NAME = "myPrefs"
        const val CURRENT_SPEAKER = "stam_speaker"
    }

    fun getTypeFace(ind: Int) =
        when {
            ind == 0 -> Typeface.SANS_SERIF
            ind == 1 -> Typeface.createFromAsset(context1.assets, "fonts/anka.ttf")
            ind == 2 -> Typeface.createFromAsset(context1.assets, "fonts/dana.otf")
            ind == 3 -> Typeface.createFromAsset(context1.assets, "fonts/shmulik.ttf")
            ind == 4 -> Typeface.createFromAsset(context1.assets, "fonts/stam.ttf")
            ind == 5 -> Typeface.createFromAsset(context1.assets, "fonts/drug.ttf")
            ind == 6 -> Typeface.createFromAsset(context1.assets, "fonts/drug.ttf")
            else -> Typeface.MONOSPACE
        }
}


