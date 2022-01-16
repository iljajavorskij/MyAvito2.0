package org.myapp.myavito20.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.myapp.myavito20.R
import java.util.ArrayList

class SpinnerRecyclerAdapter:RecyclerView.Adapter<SpinnerRecyclerAdapter.SpinnerViewHolder>() {

    val mineList = ArrayList<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_spinner_item,parent,false)
        return SpinnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpinnerViewHolder, position: Int) {
        holder.setData(mineList[position])
    }

    override fun getItemCount(): Int {
        return mineList.size
    }


    class SpinnerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tv = itemView.findViewById<TextView>(R.id.textView_spinner_item_recycler)
        fun setData(text:String){
            tv.text = text
        }

    }

    fun updateAdapter(list: ArrayList<String>){
        mineList.clear()
        mineList.addAll(list)
        notifyDataSetChanged()
    }
}