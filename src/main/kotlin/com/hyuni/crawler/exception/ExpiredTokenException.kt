package com.hyuni.crawler.exception

import com.hyuni.crawler.util.constant.ErrorCode

class ExpiredTokenException(
        val score: Double
): CrawlerException() {

    override fun errorCode(): Int {
        return ErrorCode.EXPIRED_TOKEN
    }
}
