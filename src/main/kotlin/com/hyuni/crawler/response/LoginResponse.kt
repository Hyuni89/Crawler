package com.hyuni.crawler.response

import org.springframework.stereotype.Component

@Component
class LoginResponse: CommonResponse() {

    private var token: String = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        return token
    }
}
