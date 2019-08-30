package com.hyuni.crawler.response

import com.hyuni.crawler.util.constant.ErrorCode
import org.springframework.stereotype.Component

@Component
open class CommonResponse {

    private var status: Int = ErrorCode.STATUS_OK

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getStatus(): Int {
        return status
    }
}
