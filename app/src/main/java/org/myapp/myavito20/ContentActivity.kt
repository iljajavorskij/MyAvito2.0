package org.myapp.myavito20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.myapp.myavito20.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}