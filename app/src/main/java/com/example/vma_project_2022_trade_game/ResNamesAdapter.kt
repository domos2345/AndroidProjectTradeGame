package com.example.vma_project_2022_trade_game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView


class ResNamesAdapter() : RecyclerView.Adapter<ResNamesAdapter.ResNamesViewHolder>() {

    var cachedResNames : List<EditText> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResNamesViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.res_names_list_item,parent,false)

        return ResNamesAdapter.ResNamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResNamesViewHolder, position: Int) {
        holder.editText.hint = "surovina$position"
    }

    override fun getItemCount(): Int {
        return  cachedResNames.size
    }

    class ResNamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editText: EditText = itemView.findViewById(R.id.resNameTextField)
    }
}