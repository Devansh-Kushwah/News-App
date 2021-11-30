package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAapter( private val listener: NewsItemsclicked): RecyclerView.Adapter<newsviewholder>() {
    private val items: ArrayList<newsdata> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsitems,parent,false)
        val viewholder = newsviewholder(view)
        view.setOnClickListener{
            listener.onitemclicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {
        val currentitem = items[position]
        holder.titleView.text = currentitem.title
        holder.author.text = currentitem.author
        Glide.with(holder.itemView.context).load(currentitem.urlToImage).into(holder.image)    }

    fun updateNews(updateNews: ArrayList<newsdata>){
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class newsviewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)}

interface NewsItemsclicked{
    fun onitemclicked(item: newsdata)
}