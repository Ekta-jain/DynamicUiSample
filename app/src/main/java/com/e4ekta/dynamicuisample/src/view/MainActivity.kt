package com.e4ekta.dynamicuisample.src.view


import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.e4ekta.dynamicuisample.R
import com.e4ekta.dynamicuisample.databinding.ActivityMainBinding
import com.e4ekta.dynamicuisample.src.UiType
import com.e4ekta.dynamicuisample.src.viewmodel.LaunchViewModel
import com.e4ekta.network_module.model.UiData
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by  viewModels()
    private lateinit var  mBinding: ActivityMainBinding
    private var uiDataList = ArrayList<UiData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        setObserver()


    }

//    "uidata": [
//    {
//        "uitype": "label",
//        "value": "Your Name",
//        "key": "label_name"
//    },
//    {
//        "uitype": "edittext",
//        "key": "text_name",
//        "hint": "Enter your name"
//    },
//    {
//        "uitype": "label",
//        "value": "Your Phone",
//        "key": "label_phone"
//    },
//    {
//        "uitype": "edittext",
//        "key": "text_phone",
//        "hint": "Enter your phone no"
//    },
//    {
//        "uitype": "label",
//        "value": "Your City",
//        "key": "label_city"
//    },
//    {
//        "uitype": "edittext",
//        "key": "text_city",
//        "hint": "Enter your city"
//    },
//    {
//        "uitype": "button",
//        "value": "Submit"
//    }
//    ]

    private fun setUiData(uiDataList: List<UiData>) {
        //mBinding.linearContainer.removeAllViews()
        uiDataList.forEach {

            when (it.uitype.uppercase()) {
                UiType.LABEL.toString() -> {
                    val inflater =
                        LayoutInflater.from(applicationContext) // context is now available in the receiver scope
                    val textView = inflater.inflate(
                        R.layout.item_text_view,
                        mBinding.linearContainer,
                        false
                    )
                    mBinding.linearContainer.addView(textView)
                }
                UiType.EDITTEXT.toString() -> {
                    val inflater =
                        LayoutInflater.from(applicationContext) // context is now available in the receiver scope
                    val textView = inflater.inflate(
                        R.layout.item_edit_text,
                        mBinding.linearContainer,
                        false
                    )
                    mBinding.linearContainer.addView(textView)
                }
                UiType.BUTTON.toString() -> {
                    val inflater =
                        LayoutInflater.from(applicationContext) // context is now available in the receiver scope
                    val textView = inflater.inflate(
                        com.e4ekta.dynamicuisample.R.layout.item_text_view,
                        mBinding.linearContainer,
                        false
                    )
                    mBinding.linearContainer.addView(textView)
                }
            }
        }
    }

    private fun setObserver() {
        launchViewModel.fetchCustomUI().observe(this, Observer {
            Toast.makeText(this,"Show Data"+it, Toast.LENGTH_LONG).show()
            //uiDataList.addAll(it.uidata)
            when(it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let {
                        loadImagefromUrl(mBinding.ivHeaderImage,it.logoUrl)
                        mBinding.tvHeaderTitle.text = it.headingText
                        uiDataList.addAll(it.uidata)
                        setUiData(uiDataList)
                        Toast.makeText(applicationContext,"Ui List"+uiDataList.size,Toast.LENGTH_LONG).show()
                    }

                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.LOADING -> {

                }
            }
        })
    }

    fun loadImagefromUrl(imageView:AppCompatImageView, url :String){
        Glide.with(applicationContext)
            .load(url)
            .into(imageView);
    }
}