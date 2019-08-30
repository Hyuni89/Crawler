package com.hyuni.crawler.exception

import java.lang.RuntimeException

abstract class CrawlerException: RuntimeException() {
    abstract fun errorCode(): Int
}
