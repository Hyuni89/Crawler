package com.hyuni.crawler.exception

import com.hyuni.crawler.util.constant.ErrorCode

class InvalidTokenException: CrawlerException() {
    override fun errorCode(): Int {
        return ErrorCode.INVALID_CATEGORY
    }
}
