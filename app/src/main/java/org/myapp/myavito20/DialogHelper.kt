package org.myapp.myavito20

import androidx.appcompat.app.AlertDialog
import org.myapp.myavito20.databinding.SingDialogBinding

class DialogHelper(activity1:MainActivity) {
    val activity = activity1
    fun createDialog(index:Int){
        val builder = AlertDialog.Builder(activity)
        val rootElement = SingDialogBinding.inflate(activity.layoutInflater)

        if (index == SING_IN_STATUS){
            rootElement.textView.text = activity.resources.getString(R.string.sign_in)
            rootElement.buttonSingIn.text = activity.resources.getString(R.string.sing_in_action)
        }else{
            rootElement.textView.text = activity.resources.getString(R.string.sign_up)
            rootElement.buttonSingIn.text = activity.resources.getString(R.string.sing_up_action)
        }
        builder.setView(rootElement.root)
        builder.show()
    }
    companion object{
        const val SING_IN_STATUS = 0
        const val SING_UP_STATUS = 1
    }
}