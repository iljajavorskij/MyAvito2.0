package org.myapp.myavito20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.myapp.myavito20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvHeader: TextView
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private val dialog = DialogHelper(this)
    lateinit var navView:NavigationView
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initView()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }
    private fun initView(){
        navView = mBinding.navigateView
        drawerLayout = mBinding.drawerMenu
        toolbar = findViewById(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        tvHeader = navView.getHeaderView(0).findViewById(R.id.account_email_header)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.adds -> {

            }
            R.id.my_adds -> {
                Toast.makeText(this@MainActivity,"ilja",Toast.LENGTH_LONG).show()
            }
            R.id.automobile -> {}
            R.id.phone -> {}
            R.id.photo -> {}
            R.id.sing_in -> {
                dialog.createDialog(DialogHelper.SING_IN_STATUS)
            }
            R.id.sing_up -> {
                dialog.createDialog(DialogHelper.SING_UP_STATUS )
            }
            R.id.sing_out -> {
                uiUpdate(null)
                mAuth.signOut()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user:FirebaseUser?){
        tvHeader.text = if (user == null){
            resources.getString(R.string.header_text_view)
        }else{
            user.email
        }
    }


}