package com.hyuni.crawler.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [FileGeneratorTest.Config::class])
class FileGeneratorTest {

    @Autowired
    lateinit var fileGenerator: FileGenerator

    @Test
    fun test() {
        println(fileGenerator.generateFileName("news"))
        println(fileGenerator.generateFileName("arts"))
        println(fileGenerator.generateFileName("blog"))
        println(fileGenerator.generateFileName("sports"))
        println(fileGenerator.generateFileName("music"))
    }

    @Test
    fun validate() {
        Assert.assertFalse(fileGenerator.isValidName("image", "image_e.jpg"))
        Assert.assertFalse(fileGenerator.isValidName("image", "news_.jpg"))
        Assert.assertFalse(fileGenerator.isValidName("image", "image_e1fkJEHie3heGieL"))
        Assert.assertTrue(fileGenerator.isValidName("image", "image_e1fkJEHie3heGieL.jpg"))
        Assert.assertFalse(fileGenerator.isValidName("image", "image_e1fkJEHie3he*ieL.jpg"))
    }

    @Configuration
    class Config {
        @Bean
        fun fileGenerator(): FileGenerator {
            return FileGenerator()
        }
    }
}
