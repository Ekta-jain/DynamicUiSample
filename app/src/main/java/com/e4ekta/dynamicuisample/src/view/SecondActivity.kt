package com.e4ekta.dynamicuisample.src.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.e4ekta.dynamicuisample.R
import com.e4ekta.dynamicuisample.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySecondBinding
    private lateinit var showDatahashMap:HashMap<String,String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        getDataFromBundle()
        inflateDynamicView()
    }

    /*
    * Inflated Scalable UI to show InputType Data
    * */
    private fun inflateDynamicView() {
        mBinding.llContainer.removeAllViews()
        val inflater = LayoutInflater.from(applicationContext)
        if(showDatahashMap.isEmpty().not() == true){
            for (key in showDatahashMap.entries) {
                val textView  = inflater.inflate(
                    R.layout.item_text_view,
                    mBinding.llContainer,
                    false
                )

                (textView as TextView).text = key.key+": "+key.value
                mBinding.llContainer.addView(textView)
            }

        }

    }

    /*
    * Get data from Intent here
    * */
    private fun getDataFromBundle() {
        showDatahashMap = intent.getSerializableExtra(INPUT_DATA_MAP) as HashMap<String, String>
    }

    override fun onBackPressed() {
        finish()
    }
    companion object {
        const val INPUT_DATA_MAP = "INPUT_DATA_MAP"
    }
}