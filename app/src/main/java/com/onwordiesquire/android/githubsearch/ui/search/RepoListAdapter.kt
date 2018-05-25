package com.onwordiesquire.android.githubsearch.ui.search

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.githubsearch.R
import com.onwordiesquire.android.githubsearch.domain.Repo
import com.onwordiesquire.android.githubsearch.utils.hide
import com.onwordiesquire.android.githubsearch.utils.inflate
import com.onwordiesquire.android.githubsearch.utils.load
import com.onwordiesquire.android.githubsearch.utils.show
import kotlinx.android.synthetic.main.layout_search_result_item.view.*


class RepoListAdapter : ListAdapter<Repo, RepoListAdapter.RepoViewHolder>(RepoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
            parent.inflate(R.layout.layout_search_result_item).run { RepoViewHolder(this) }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Repo?) {
            item?.let {
                with(itemView) {
                    repoNameTxt.text = it.name
                    descTxt.text = it.description
                    it.forksCount.apply {
                        if (this >= 1) {
                            imgForks.show()
                            noOfForksTxt.text = "$this"
                        } else {
                            imgForks.hide()
                            noOfForksTxt.hide()
                        }
                    }
                    imgAvatar.load(it.owner.avatarUrl, R.mipmap.ic_launcher)
                }
            }
        }
    }
}

class RepoDiffUtil : DiffUtil.ItemCallback<Repo>() {
    override fun areContentsTheSame(oldItem: Repo?, newItem: Repo?) = oldItem?.equals(newItem) ?: false

    override fun areItemsTheSame(oldItem: Repo?, newItem: Repo?) = oldItem?.id == newItem?.id ?: false
}

