package org.myapp.myavito20.dialogs


import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import org.myapp.myavito20.R
import org.myapp.myavito20.utils.CountyHelper

class SpinnrDialogHelper {

    fun showSpinnerDialog(context: Context,arrayList: ArrayList<String>){
        val dialogBuilder = AlertDialog.Builder(context)
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_search,null)
        val adapter = SpinnerRecyclerAdapter()
        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view_spinner)
        val searchView = rootView.findViewById<SearchView>(R.id.search_view)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
        dialogBuilder.setView(rootView)
        adapter.updateAdapter(arrayList)
        setSeachView(adapter,arrayList,searchView)
        dialogBuilder.show()
    }

    private fun setSeachView(adapter:SpinnerRecyclerAdapter,arrayList:ArrayList<String>,searchView:SearchView?){
        searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                 return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CountyHelper.filterSearchText(arrayList,newText)
                adapter.updateAdapter(tempList)
               return true
            }

        })
    }
}