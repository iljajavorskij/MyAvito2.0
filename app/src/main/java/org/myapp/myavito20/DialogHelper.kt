package org.myapp.myavito20

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.myapp.myavito20.accountHelper.AccountHelper
import org.myapp.myavito20.databinding.SingDialogBinding

class DialogHelper(activity1:MainActivity) {
    val activity = activity1
    val accountHelper = AccountHelper(activity1)


    fun createDialog(index:Int){
        val builder = AlertDialog.Builder(activity)
        val rootElement = SingDialogBinding.inflate(activity.layoutInflater)
        builder.setView(rootElement.root)
        checkDialogSate(index,rootElement)
        val dialog = builder.create()
        rootElement.buttonSingIn.setOnClickListener {
            setOnClickButtonSign(index,rootElement,dialog)
        }
        rootElement.buttonForgetPass.setOnClickListener {
            setOnClickButtonForget(rootElement,dialog)
        }
        rootElement.buttonSignInWithGoogle.setOnClickListener {
            accountHelper.signInWithGoogle()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setOnClickButtonForget(rootElement: SingDialogBinding, dialog: AlertDialog) {
        if (rootElement.editTextTextEmailAddress.text.isNotEmpty()){
            activity.mAuth.sendPasswordResetEmail(rootElement
                .editTextTextEmailAddress
                .text.toString())
                .addOnCompleteListener {
                task -> if (task.isSuccessful){
                    Toast.makeText(activity,R.string.reset_password_send_message,Toast.LENGTH_LONG).show()
                }
            }
        }else{
            rootElement.textViewForgetEmailFprReset.visibility = View.VISIBLE
        }
    }


    private fun setOnClickButtonSign(index: Int, rootElement: SingDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
        if (index == SING_UP_STATUS){
            accountHelper.initialMail(rootElement.editTextTextEmailAddress.text.toString(),
                rootElement.editTextTextPassword.text.toString())
        }else{
            accountHelper.singInWidthMail(rootElement.editTextTextEmailAddress.text.toString(),
                rootElement.editTextTextPassword.text.toString())
        }
    }

    private fun checkDialogSate(index: Int, rootElement: SingDialogBinding) {
        if (index == SING_IN_STATUS){
            rootElement.textView.text = activity.resources.getString(R.string.sign_in)
            rootElement.buttonSingIn.text = activity.resources.getString(R.string.sing_in_action)
            rootElement.buttonForgetPass.visibility = View.VISIBLE
        }else{
            rootElement.textView.text = activity.resources.getString(R.string.sign_up)
            rootElement.buttonSingIn.text = activity.resources.getString(R.string.sing_up_action)
        }
    }

    companion object{
        const val SING_IN_STATUS = 0
        const val SING_UP_STATUS = 1
    }
}