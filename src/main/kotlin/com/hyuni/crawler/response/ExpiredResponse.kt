package com.hyuni.crawler.response

import org.springframework.stereotype.Component

@Component
class ExpiredResponse: CommonResponse() {

    private var score: Double = 0.0

    fun getScore(): Double {
        return score
    }

    fun setScore(score: Double) {
        this.score = score
    }
}
