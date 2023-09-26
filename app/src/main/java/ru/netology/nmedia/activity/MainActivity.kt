package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCount.text = post.likes.toString()

                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                likeCount.text = post.likes.toString()

                share.setOnClickListener {
                    post.share += 100
                    shareCount.text = reductionNumber(post.share)
                }

                views.setOnClickListener {
                    post.views += 100
                    viewsCount.text = reductionNumber(post.views)
                }
            }
        }

        binding.like.setOnClickListener {
            viewModel.like()
        }
    }
}
private fun reductionNumber(number: Int): String {
    return when {
        number < 1000 -> "$number"
        number < 10000 && number % 1000 == 0 -> number.toString()[0] + "К"
        number < 10000 -> number.toString()[0] + "." + number.toString()[1] + "К"
        number < 1000000 -> number.toString()[0] + "" + number.toString()[1] + "К"
        number < 10000000 -> number.toString()[0] + "." + number.toString()[1] + "M"
        else -> number.toString()[0] + "" + number.toString()[1] + "M"
    }
}