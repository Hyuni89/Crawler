package com.hyuni.crawler

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class Constants(
        @Value("\${news.generate}") var NEWS_GENERATE: Int,
        @Value("\${news.change}") var NEWS_CHANGE: Int,
        @Value("\${news.delay}") var NEWS_DELAY: Int,
        @Value("\${art.generate}") var ART_GENERATE: Int,
        @Value("\${art.change}") var ART_CHANGE: Int,
        @Value("\${art.delay}") var ART_DELAY: Int,
        @Value("\${blog.generate}") var BLOG_GENERATE: Int,
        @Value("\${blog.change}") var BLOG_CHANGE: Int,
        @Value("\${blog.delay}") var BLOG_DELAY: Int,
        @Value("\${music.generate}") var MUSIC_GENERATE: Int,
        @Value("\${music.change}") var MUSIC_CHANGE: Int,
        @Value("\${music.delay}") var MUSIC_DELAY: Int,
        @Value("\${sport.generate}") var SPORT_GENERATE: Int,
        @Value("\${sport.change}") var SPORT_CHANGE: Int,
        @Value("\${sport.delay}") var SPORT_DELAY: Int
)
