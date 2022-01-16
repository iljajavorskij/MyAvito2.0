package org.myapp.myavito20.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import org.myapp.myavito20.databinding.ActivityEditNewAdBinding
import org.myapp.myavito20.dialogs.SpinnrDialogHelper
import org.myapp.myavito20.utils.CountyHelper

class EditNewAd : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditNewAdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEditNewAdBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val countryList = CountyHelper.getAllCity(this)
        val dialog = SpinnrDialogHelper()
        dialog.showSpinnerDialog(this,countryList)



        /* val adapter = ArrayAdapter(this
            ,android.R.layout.simple_spinner_item
            ,CountyHelper.getAllCity(this))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spinner.adapter = adapter */
    }
}