package com.hyuni.crawler.util

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [TokenGeneratorTest.Config::class])
class TokenGeneratorTest {

    @Autowired
    lateinit var tokenGenerator: TokenGenerator

    @Test
    fun test() {
        println(tokenGenerator.generateToken())
        println(tokenGenerator.generateToken())
        println(tokenGenerator.generateToken())
        println(tokenGenerator.generateToken())
        println(tokenGenerator.generateToken())
    }

    @Configuration
    class Config {
        @Bean
        fun tokenGenerator(): TokenGenerator {
            return TokenGenerator()
        }
    }
}
