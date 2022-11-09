package com.e4ekta.dynamicuisample.src.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.e4ekta.dynamicuisample.R
import com.e4ekta.dynamicuisample.databinding.ActivityMainBinding
import com.e4ekta.dynamicuisample.src.utils.UiTypeEnum
import com.e4ekta.dynamicuisample.src.view.SecondActivity.Companion.INPUT_DATA_MAP
import com.e4ekta.dynamicuisample.src.viewmodel.MainViewModel
import com.e4ekta.network_module.model.UiData
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var uiDataList = ArrayList<UiData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setObserver()
    }

    /*
    * setUiData is used to inflate dynamic Ui according to json
    * */
    private fun setUiData(uiDataList: List<UiData>) {
        mBinding.includeMainSection.linearContainer.removeAllViews()
        val inflater = LayoutInflater.from(applicationContext)
        uiDataList.forEach {
            when (it.uitype.lowercase()) {
                  UiTypeEnum.label.toString() -> {
                    val textView  = inflater.inflate(R.layout.item_text_view, mBinding.includeMainSection.linearContainer, false)
                    (textView as TextView).text = it.value
                    textView.setTag(it.key)
                    mBinding.includeMainSection.linearContainer.addView(textView)
                }
                UiTypeEnum.edittext.toString() -> {
                    val editText = inflater.inflate(R.layout.item_edit_text, mBinding.includeMainSection.linearContainer, false)
                    (editText as EditText).hint = it.hint
                    editText.clearFocus()
                    editText.setTag(it.key)
                    mBinding.includeMainSection.linearContainer.addView(editText)
                }
                UiTypeEnum.button.toString() -> {
                    val button = inflater.inflate(R.layout.item_button, mBinding.includeMainSection.linearContainer, false)
                    (button as Button).text = it.value
                    button.setTag(it.key)
                    mBinding.includeMainSection.linearContainer.addView(button)
                    button.setOnClickListener {
                        submitForm()
                    }
                }
            }
        }
    }

    private fun submitForm() {
        val sendInputDataMap = HashMap<String, String>()
        val childCount: Int = mBinding.includeMainSection.linearContainer.childCount
        // here we are traverse over childCount existing in linear container
        var showError = false
        for (i in 0 until childCount) {
            val view: View = mBinding.includeMainSection.linearContainer.getChildAt(i)
            if (view is EditText) {
               /* if view is edittext and Add that value in HashMap
                HashMap key = View Tag
                HashMap Value = Input Data entered by user
               * */
                if (view.text.isBlank() && view.text.isEmpty()) {
                    showError = true
                }else{
                    sendInputDataMap[view.getTag().toString()] = view.text.toString()
                }
            }
        }
        if(showError){
            Toast.makeText(applicationContext,getString(R.string.validation_msg),Toast.LENGTH_LONG).show()
        }else{
            navigateToSecondActivity(sendInputDataMap)
        }
    }

    private fun navigateToSecondActivity(sendInputDataMap: HashMap<String, String>) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(INPUT_DATA_MAP, sendInputDataMap)
        startActivity(intent)
    }

    private fun setObserver() {
        mainViewModel.getFetchUsers().observe(this) {
            when (it.status) {

                Resource.Status.SUCCESS -> {
                    mBinding.includeLoaderSection.root.isVisible = false
                    mBinding.includeMainSection.root.isVisible = true
                    it.data?.let {
                        loadImageFromUrl(mBinding.includeMainSection.ivHeaderImage, it.logoUrl)
                        mBinding.includeMainSection.tvHeaderTitle.text = it.headingText
                        uiDataList.addAll(it.uidata)
                        setUiData(uiDataList)
                    }
                }

                Resource.Status.ERROR -> {
                    mBinding.includeErrorSection.root.isVisible = true
                    mBinding.includeErrorSection.tvMessage.text = it.message
                }

                Resource.Status.LOADING -> {
                    mBinding.includeLoaderSection.root.isVisible = true
                    mBinding.includeMainSection.root.isVisible = false
                }
            }
        }
    }

    private fun loadImageFromUrl(imageView:AppCompatImageView, url :String){
        Glide.with(applicationContext)
            .load(url)
            .into(imageView);
    }
}