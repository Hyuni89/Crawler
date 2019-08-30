package com.hyuni.crawler

import com.hyuni.crawler.category.page.Page
import com.hyuni.crawler.exception.CrawlerException
import com.hyuni.crawler.exception.ExpiredTokenException
import com.hyuni.crawler.request.CommonRequest
import com.hyuni.crawler.response.CommonResponse
import com.hyuni.crawler.response.ExpiredResponse
import com.hyuni.crawler.response.FeatureResponse
import com.hyuni.crawler.response.LoginResponse
import com.hyuni.crawler.util.TokenGenerator
import com.hyuni.crawler.util.constant.ErrorCode.Companion.INVALID_TOKEN
import com.hyuni.crawler.util.constant.ErrorCode.Companion.STATUS_OK
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CrawlerController {

    @Autowired
    lateinit var tokenGenerator: TokenGenerator

    @Autowired
    lateinit var serviceRouter: ServiceRouter

    @Autowired
    lateinit var commonResponse: CommonResponse

    @Autowired
    lateinit var loginResponse: LoginResponse

    @Autowired
    lateinit var featureResponse: FeatureResponse

    @Autowired
    lateinit var expiredResponse: ExpiredResponse

    @GetMapping("/login")
    @ResponseBody
    fun login(): CommonResponse {
        val token = tokenGenerator.generateToken()
        val result = serviceRouter.register(token)
        val ret: CommonResponse

        if(result) {
            loginResponse.setToken(token)
            loginResponse.setStatus(STATUS_OK)
            ret = loginResponse
        } else {
            commonResponse.setStatus(INVALID_TOKEN)
            ret = commonResponse
        }

        return ret
    }

    @GetMapping("/{category}/feature/{page}")
    @ResponseBody
    fun feature(@RequestHeader(value = "token") token: String, @PathVariable("category") category: String, @PathVariable("page") page: Int): CommonResponse {
        if(!tokenGenerator.isValidToken(token)) {
            commonResponse.setStatus(INVALID_TOKEN)
            return commonResponse
        }

        val result: Page?
        try {
            result = serviceRouter.feature(token, category, page)
            featureResponse.setResponse(result)
        } catch(e: ExpiredTokenException) {
            expiredResponse.setScore(e.score)
            expiredResponse.setStatus(e.errorCode())
            return expiredResponse
        } catch(e: CrawlerException) {
            commonResponse.setStatus(e.errorCode())
            return commonResponse
        }

        return featureResponse
    }

    @PostMapping("/{category}/save")
    @ResponseBody
    fun save(@RequestHeader(value = "token") token: String, @PathVariable("category") category: String, @RequestBody images: CommonRequest): CommonResponse {
        if(!tokenGenerator.isValidToken(token)) {
            commonResponse.setStatus(INVALID_TOKEN)
            return commonResponse
        }

        try {
            serviceRouter.save(token, category, images.getImages())
        } catch(e: ExpiredTokenException) {
            expiredResponse.setScore(e.score)
            expiredResponse.setStatus(e.errorCode())
            return expiredResponse
        } catch(e: CrawlerException) {
            commonResponse.setStatus(e.errorCode())
            return commonResponse
        }

        commonResponse.setStatus(STATUS_OK)
        return commonResponse
    }

    @PostMapping("/{category}/delete")
    @ResponseBody
    fun delete(@RequestHeader(value = "token") token: String, @PathVariable("category") category: String, @RequestBody images: CommonRequest): CommonResponse {
        if(!tokenGenerator.isValidToken(token)) {
            commonResponse.setStatus(INVALID_TOKEN)
            return commonResponse
        }

        try {
            serviceRouter.delete(token, category, images.getImages())
        } catch(e: ExpiredTokenException) {
            expiredResponse.setScore(e.score)
            expiredResponse.setStatus(e.errorCode())
            return expiredResponse
        } catch(e: CrawlerException) {
            commonResponse.setStatus(e.errorCode())
            return commonResponse
        }

        commonResponse.setStatus(STATUS_OK)
        return commonResponse
    }

}
