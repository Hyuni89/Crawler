package com.hyuni.crawler

import com.hyuni.crawler.category.Category
import com.hyuni.crawler.category.page.Page
import com.hyuni.crawler.exception.CrawlerException
import com.hyuni.crawler.exception.ExpiredTokenException
import java.lang.Double.max
import java.util.*
import kotlin.concurrent.schedule

class CrawlerService(
        val token: String
) {

//    private val LIFETIME = 300000L // 5 * 60 * 1000
    private val LIFETIME = 30000L
    private var isRunning = false
    private var queryCnt = 0
    private val categories: MutableList<Category> = mutableListOf()
    private var score: Double? = null

    fun init() {
        categories.add(Category("news", 3, 1, 5))
//        categories.add(Category("art", 3, 1, 10))
//        categories.add(Category("blog", 10, 2, 1))
//        categories.add(Category("music", 3, 1, 5))
//        categories.add(Category("sport", 3, 1, 5))

        isRunning = true
        Timer().schedule(LIFETIME) {
            println("Logging:: Token[$token] Timeout")

            var sum = 0.0
            categories.forEach { sum += it.calScore() }
            sum = max(sum, 0.0)
            sum += 512 - 0.01 * queryCnt

            score = sum
            isRunning = false
        }
    }

    @Throws(CrawlerException::class)
    fun feature(category: Int, page: Int): Page? {
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
