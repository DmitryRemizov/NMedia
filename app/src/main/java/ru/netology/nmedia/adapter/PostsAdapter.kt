package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias LikeListener = (post: Post) -> Unit
typealias ShareListener = (post: Post) -> Unit
typealias ViewListener = (post: Post) -> Unit


class PostsAdapter(
    private val listener: LikeListener,
    private val shareListener: ShareListener,
    private val viewListener: ViewListener,
) : ListAdapter<Post, PostViewHolder>(PostViewHolder.PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, listener, shareListener, viewListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(

    private val binding: CardPostBinding,
    private val listener: LikeListener,
    private val shareListener: ShareListener,
    private val viewListener: ViewListener

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            likeCount.text = post.likes.toString()
            shareCount.text = reductionNumber(post.share)
            viewsCount.text = reductionNumber(post.views)

            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            likeCount.text = post.likes.toString()

            like.setOnClickListener {
                listener(post)
            }

            share.setOnClickListener {
                shareListener(post)
            }
            views.setOnClickListener {
                viewListener(post)
            }
        }
    }

    object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    fun reductionNumber(number: Int): String {
        return when {
            number < 1000 -> "$number"
            number < 10000 && number % 1000 == 0 -> number.toString()[0] + "К"
            number < 10000 -> number.toString()[0] + "." + number.toString()[1] + "К"
            number < 1000000 -> number.toString()[0] + "" + number.toString()[1] + "К"
            number < 10000000 -> number.toString()[0] + "." + number.toString()[1] + "M"
            else -> number.toString()[0] + "" + number.toString()[1] + "M"
        }
    }
}