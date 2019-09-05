package com.hyuni.crawler.response

import com.hyuni.crawler.category.page.Page
import com.hyuni.crawler.exception.InvalidPageException
import org.springframework.stereotype.Component

class FeatureResponse: CommonResponse() {

    private var prev_url: String = ""
    private var next_url: String = ""
    private var images: List<String> = emptyList()

    fun setResponse(page: Page?) {
        val p = page ?: throw InvalidPageException()
        prev_url = p.getPrevPage()
        next_url = p.getNextPage()

        val image = mutableListOf<String>()
        p.unit.forEach { image.add(it.name + " " + it.action) }
        images = image
    }

    fun getPrev_url(): String {
        return prev_url
    }

    fun getNext_url(): String {
        return next_url
    }

    fun getImages(): List<String> {
        return images
    }
}
