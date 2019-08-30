package com.hyuni.crawler

import com.hyuni.crawler.category.page.Page
import com.hyuni.crawler.exception.CrawlerException
import com.hyuni.crawler.exception.InvalidCategoryException
import com.hyuni.crawler.exception.InvalidTokenException
import com.hyuni.crawler.util.constant.ErrorCode.Companion.INVALID_CATEGORY
import com.hyuni.crawler.util.constant.ErrorCode.Companion.INVALID_TOKEN
import org.springframework.stereotype.Service

@Service
class ServiceRouter {

    val router: MutableMap<String, CrawlerService> = mutableMapOf()

    fun register(token: String): Boolean {
        if(router.containsKey(token)) {
            return false
        }

        val newOne = CrawlerService(token)
        newOne.init()
        router[token] = newOne

        return true
    }

    @Throws(CrawlerException::class)
    fun feature(token: String, category: String, page: Int): Page? {
        val service: CrawlerService = router[token] ?: throw InvalidTokenException()
        val cate: Int = parseCategory(category) ?: throw InvalidCategoryException()

        return service.feature(cate, page)
    }

    @Throws(CrawlerException::class)
    fun save(token: String, category: String, images: List<String>) {
        val service: CrawlerService = router[token] ?: throw InvalidTokenException()
        val cate: Int = parseCategory(category) ?: throw InvalidCategoryException()

        service.save(cate, images)
    }

    @Throws(CrawlerException::class)
    fun delete(token: String, category: String, images: List<String>) {
        val service: CrawlerService = router[token] ?: throw InvalidTokenException()
        val cate: Int = parseCategory(category) ?: throw InvalidCategoryException()

        service.delete(cate, images)
    }

    private fun parseCategory(category: String): Int? {
        return when(category) {
            "news" -> 0
            "art" -> 1
            "blog" -> 2
            "music" -> 3
            "sport" -> 4
            else -> null
        }
    }
}
