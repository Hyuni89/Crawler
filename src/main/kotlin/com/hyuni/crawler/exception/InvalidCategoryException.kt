package com.hyuni.crawler.exception

import com.hyuni.crawler.util.constant.ErrorCode

class InvalidCategoryException: CrawlerException() {
    override fun errorCode(): Int {
        return ErrorCode.INVALID_TOKEN
    }
}
