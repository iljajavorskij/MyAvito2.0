package org.myapp.myavito20.accountHelper

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.myapp.myavito20.MainActivity
import org.myapp.myavito20.R
import org.myapp.myavito20.constants.FirebaseAuthEror

class AccountHelper(act:MainActivity) {
    private val activity = act
    lateinit var googleClient:GoogleSignInClient


    fun initialMail(mail:String, password:String){
        if (mail.isNotEmpty() && password.isNotEmpty()){
            activity.mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener { task ->
                    checkErrorSignUp(task,mail,password)
            }
        }
    }




    fun singInWidthMail(mail:String, password:String){
        if (mail.isNotEmpty() && password.isNotEmpty()){
            activity.mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener { task ->
                checkErrorSignIn(task)
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

    private fun linkEmailToGoogle(email:String, password: String){
        val credential = EmailAuthProvider.getCredential(email,password)
        activity.mAuth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(activity,activity.resources.getString(R.string.link_done ),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getSignInGoogleClient():GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("47833848483-ruinsv43o3bdhmn4s6deo00epi2p6bnl.apps.googleusercontent.com")
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity,gso)
    }

    fun signInWithGoogle(){
        googleClient = getSignInGoogleClient()
        val intent = googleClient.signInIntent
        activity.startActivityForResult(intent, REQ_CODE)
    }

   fun signOutGoogle(){
        getSignInGoogleClient().signOut()
    }

    fun signInFirebaseWithGoogle(token:String){
        val credential = GoogleAuthProvider.getCredential(token,null)
        activity.mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(activity,"Sign In done",Toast.LENGTH_LONG).show()
                activity.uiUpdate(it.result?.user)
            }
        }
    }

    private fun checkErrorSignIn(task: Task<AuthResult>) {
        if (task.isSuccessful){
            activity.uiUpdate(task.result?.user)
        }else{
            Log.d("ilja","Message :${task.exception}")
            Toast.makeText(activity,activity.resources.getString(R.string.error_sing_in)
                ,Toast.LENGTH_LONG).show()
            if (task.exception is FirebaseAuthInvalidCredentialsException){
                val exception = task.exception as FirebaseAuthInvalidCredentialsException

                when(exception.errorCode){
                    FirebaseAuthEror.ERROR_WRONG_PASSWORD ->{}
                    FirebaseAuthEror.ERROR_INVALID_EMAIL ->{}
                }
            }else if (task.exception is FirebaseAuthInvalidUserException){
                val exception = task.exception as FirebaseAuthInvalidUserException
                if (exception.errorCode == FirebaseAuthEror.ERROR_USER_NOT_FOUND){

                }
            }
            if (task.exception is FirebaseAuthInvalidCredentialsException){
                val exception = task.exception as FirebaseAuthInvalidCredentialsException
                if (exception.errorCode == FirebaseAuthEror.ERROR_WRONG_PASSWORD){

                }
            }
        }
    }

    private fun checkErrorSignUp(task: Task<AuthResult>,mail: String,password: String) {
        if (task.isSuccessful){
            sendVerificationMessage(task.result?.user!!)
            activity.uiUpdate(task.result?.user)
        }else{
            if (task.exception is FirebaseAuthWeakPasswordException){
                val exception = task.exception as FirebaseAuthWeakPasswordException
                //Log.d("ilja","Message :${exception.errorCode}")
                if (exception.errorCode == FirebaseAuthEror.ERROR_WEAK_PASSWORD){
                }
            }
            Log.d("ilja","Message :${task.exception}")
            Toast.makeText(activity,
                activity.resources.getString(R.string.error_sing_up),
                Toast.LENGTH_LONG).show()
            if (task.exception is FirebaseAuthUserCollisionException){
                val exception = task.exception as FirebaseAuthUserCollisionException
                if (exception.errorCode == FirebaseAuthEror.ERROR_EMAIL_ALREADY_IN_USE){
                    linkEmailToGoogle(mail,password)
                }
            }else if (task.exception is FirebaseAuthInvalidCredentialsException){
                val exception = task.exception as FirebaseAuthInvalidCredentialsException
                if (exception.errorCode == FirebaseAuthEror.ERROR_INVALID_EMAIL){

                    Toast.makeText(activity,"Sign In done",Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    companion object{
        const val REQ_CODE = 1
    }
}