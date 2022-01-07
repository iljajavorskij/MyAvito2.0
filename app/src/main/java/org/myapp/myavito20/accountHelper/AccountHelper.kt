package org.myapp.myavito20.accountHelper

import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import org.myapp.myavito20.MainActivity
import org.myapp.myavito20.R

class AccountHelper(act:MainActivity) {
    private val activity = act
    fun initialMail(mail:String, password:String){
        if (mail.isNotEmpty() && password.isNotEmpty()){
            activity.mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    sendVerificationMessage(task.result?.user!!)
                    activity.uiUpdate(task.result?.user)
                }else{
                    Toast.makeText(activity,activity.resources.getString(R.string.error_sing_up),Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    fun singInWidthMail(mail:String, password:String){
        if (mail.isNotEmpty() && password.isNotEmpty()){
            activity.mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    activity.uiUpdate(task.result?.user)
                }else{
                    Toast.makeText(activity,activity.resources.getString(R.string.error_sing_in),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun sendVerificationMessage(user:FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener { 
            if (it.isSuccessful){
                Toast.makeText(activity,activity.resources.getString(R.string.verification_message_send),Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity,activity.resources.getString(R.string.verification_message_not_send ),Toast.LENGTH_LONG).show()
            }
        }
    }
}