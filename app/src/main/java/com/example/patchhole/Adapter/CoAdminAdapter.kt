package com.example.patchhole.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.patchhole.CoAdmin
import com.example.patchhole.R

class CoAdminAdapter(internal var context: Context):
    RecyclerView.Adapter<CoAdminAdapter.CoAdminViewHolder>()
{
    internal var coAdminList:MutableList<CoAdmin>

    val lastItemId:String?
        get() = coAdminList[coAdminList.size-1].Email

    fun addAll(newUsers:List<CoAdmin>)
    {
        val init = coAdminList.size
        coAdminList.addAll(newUsers)
        notifyItemRangeChanged(init,newUsers.size)
    }

    fun removeLastItem()
    {
        coAdminList.removeAt(coAdminList.size-1)
    }


    init{
        this.coAdminList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoAdminViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.coadmin_list_layout,parent,false)
        return CoAdminViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return coAdminList.size
    }

    override fun onBindViewHolder(holder: CoAdminViewHolder, position: Int) {
        holder.txt_Email.text = coAdminList[position].Email
        holder.txt_FirstName.text = coAdminList[position].FirstName
        holder.txt_LastName.text = coAdminList[position].LastName
        holder.txt_MobileNumber.text = coAdminList[position].MobileNumber.toString()
        holder.txt_Taluka.text = coAdminList[position].Taluka
    }

    inner class CoAdminViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var txt_Email:TextView
        internal var txt_FirstName:TextView
        internal var txt_LastName:TextView
        internal var txt_MobileNumber:TextView
        internal var txt_Taluka: TextView

        init{
            txt_Email = itemView.findViewById(R.id.CoAdminEmail)
            txt_FirstName = itemView.findViewById<TextView>(R.id.CoAdminFirstName)
            txt_LastName = itemView.findViewById<TextView>(R.id.CoAdminLastName)
            txt_MobileNumber = itemView.findViewById<TextView>(R.id.CoAdminMobileNumber)
            txt_Taluka = itemView.findViewById<TextView>(R.id.CoAdminTaluka)
        }
    }



}