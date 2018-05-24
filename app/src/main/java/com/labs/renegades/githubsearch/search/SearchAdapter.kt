package com.labs.renegades.githubsearch.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labs.renegades.githubsearch.R
import com.labs.renegades.githubsearch.datasource.remote.api.Repository
import com.labs.renegades.githubsearch.util.DateDelegate
import kotlinx.android.synthetic.main.item_list_search.view.*


class SearchAdapter(context: Context) : ListAdapter<Repository, SearchAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val inflater: LayoutInflater by lazy {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = inflater.inflate(R.layout.item_list_search, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(repo: Repository) {
            this.itemView.apply {
                textName.text = repo.name
                textDescription.text = repo.description
                textUpdated.text = DateDelegate().formatDate(repo.updatedAt)

                setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
                    startActivity(itemView.context, browserIntent, null)
                }
            }
        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Repository> = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository?, newItem: Repository?): Boolean {
                return oldItem?.url == newItem?.url
            }

            override fun areContentsTheSame(oldItem: Repository?, newItem: Repository?): Boolean {
                return oldItem?.equals(newItem) ?: false
            }
        }
    }
}
