package com.hyuni.crawler

import com.hyuni.crawler.exception.CrawlerException
import com.hyuni.crawler.exception.InvalidCategoryException
import com.hyuni.crawler.exception.InvalidTokenException
import com.hyuni.crawler.response.FeatureResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ServiceRouter {

    val router: MutableMap<String, CrawlerService> = mutableMapOf()

    @Autowired
    lateinit var constants: Constants

    fun register(token: String): Boolean {
        if(router.containsKey(token)) {
            return false
        }

        val newOne = CrawlerService(token)
        newOne.init(constants)
        router[token] = newOne

        return true
    }

    @Throws(CrawlerException::class)
    fun feature(token: String, category: String, page: Int): FeatureResponse {
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
