package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = true,
            likes = 90,
            share = 999,
            views = 999
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = post.likes.toString()
            shareCount.text = reductionNumber(post.share)
            viewsCount.text = reductionNumber(post.views)

            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_liked_24)
            }
            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                post.likes += if (post.likedByMe) 1 else -1
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likeCount.text = post.likes.toString()
            }

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
}