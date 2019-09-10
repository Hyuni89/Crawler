package com.hyuni.crawler

import com.hyuni.crawler.category.Category
import com.hyuni.crawler.exception.CrawlerException
import com.hyuni.crawler.exception.ExpiredTokenException
import com.hyuni.crawler.response.FeatureResponse
import java.lang.Double.max
import java.util.*
import kotlin.concurrent.schedule

class CrawlerService(
        val token: String
) {

    private val LIFETIME = 300000L // 5 * 60 * 1000
//    private val LIFETIME = 30000L
    private var isRunning = false
    private var queryCnt = 0
    private val categories: MutableList<Category> = mutableListOf()
    private var score: Double? = null

    fun init(constants: Constants) {
        categories.add(Category("news", constants.NEWS_GENERATE, constants.NEWS_CHANGE, constants.NEWS_DELAY))
        categories.add(Category("art", constants.ART_GENERATE, constants.ART_CHANGE, constants.ART_DELAY))
        categories.add(Category("blog", constants.BLOG_GENERATE, constants.BLOG_CHANGE, constants.BLOG_DELAY))
        categories.add(Category("music", constants.MUSIC_GENERATE, constants.MUSIC_CHANGE, constants.MUSIC_DELAY))
        categories.add(Category("sport", constants.SPORT_GENERATE, constants.SPORT_CHANGE, constants.SPORT_DELAY))

        isRunning = true
        Timer().schedule(LIFETIME) {
            var sum = 0.0
            categories.forEach { sum += it.calScore() }
            sum = max(sum, 0.0)
            sum += 512 - 0.01 * queryCnt

            score = String.format("%.2f", sum).toDouble()
            isRunning = false
            println("Logging:: Token[$token] Timeout, Score[$score]")
        }
    }

    @Throws(CrawlerException::class)
    fun feature(category: Int, page: Int): FeatureResponse {
        if(!isRunning) throw ExpiredTokenException(score!!)

        countQuery()
        return categories[category].feature(page)
    }

    fun save(category: Int, images: List<String>) {
        if(!isRunning) throw ExpiredTokenException(score!!)

        countQuery()
        categories[category].save(images)
    }

    fun delete(category: Int, images: List<String>) {
        if(!isRunning) throw ExpiredTokenException(score!!)

        countQuery()
        categories[category].delete(images)
    }

    @Synchronized
    private fun countQuery() {
        queryCnt++
    }
}
