package com.hyuni.crawler.category

import com.hyuni.crawler.category.page.Page
import com.hyuni.crawler.category.page.UnitPair
import com.hyuni.crawler.exception.InvalidPageException
import com.hyuni.crawler.util.FileGenerator
import com.hyuni.crawler.util.constant.Action
import java.util.*

class Category(
        val CATEGORY: String,
        val PAGE_GENERATE_SEED: Int,
        val CHANGE_IMAGE_SEED: Int,
        val DELAY_SEED: Int
) {

    private var pages: MutableList<Page> = mutableListOf()
    private var saved: MutableSet<String> = mutableSetOf()
    private var deleted: MutableSet<String> = mutableSetOf()
    private val fileGenerator = FileGenerator.getInstance()
    private val answer: MutableMap<String, Enum<Action>> = mutableMapOf()
    private var correctCnt: Int = 0
    private var lostCnt: Int = 0
    private var remainCnt: Int = 0
    private var wrongCnt: Int = 0
    private var genFlag: Boolean = false

    private fun appendPage(): Page {
        val page = Page(CATEGORY, pages.size + 1)
        while(page.add(fileGenerator.generateFileName(CATEGORY), if(Random().nextInt() % 2 == 0) Action.SAVE else Action.DELETE));

        return page
    }

    init {
        pages.add(appendPage())
    }

    fun generate() {
        genFlag = true
        val genPages = Random().nextInt(PAGE_GENERATE_SEED)
        val delay = Random().nextInt(DELAY_SEED)
        val change = Random().nextInt(CHANGE_IMAGE_SEED + 1)
        println("Logging:: genPage[$genPages], delay[$delay], change[$change]")

        Thread(Runnable {
            Thread.sleep((delay * 1000).toLong())
            for(i in 0 until genPages) {
                val page = appendPage()

                for(j in pages) {
                    repeat(change) {
                        val pair = j.getRandom()
                        val index = Random().nextInt(Page.LIMIT_SIZE)
                        val toggleAction = if(pair.action == Action.SAVE) Action.DELETE else Action.SAVE

                        page.unit[index] = UnitPair(pair.name, toggleAction)
                        println("Logging:: page[${pair.name}][${pair.action}] inserted page[$index] toggled")
                    }
                }

                pages.add(page)
            }

            genFlag = false
        }).start()
    }

    fun feature(index: Int): Page {
        val want = index - 1
        if(want == pages.size) {
            if(!genFlag) generate()
            return pages[pages.size - 1]
        } else if(want < 0 || want > pages.size) {
            throw InvalidPageException()
        }

        return pages[want]
    }

    fun save(images: List<String>) {
        images.forEach { saved.add(it) }
    }

    fun delete(images: List<String>) {
        images.forEach { deleted.add(it) }
    }

    fun calScore(): Double {
        for(page in pages) {
            page.unit.forEach { answer[it.name] = it.action }
        }

        saved.forEach {
            when(answer[it]) {
                null -> wrongCnt++
                Action.SAVE -> correctCnt++
                Action.DELETE -> remainCnt++
                else -> wrongCnt++
            }
        }

        deleted.forEach {
            when(answer[it]) {
                null -> wrongCnt++
                Action.SAVE -> lostCnt++
                Action.DELETE -> correctCnt++
                else -> wrongCnt++
            }
        }

        println("Logging:: correctCnt[$correctCnt], lostCnt[$lostCnt], remainCnt[$remainCnt], wrongCnt[$wrongCnt]")
        return NORMAL_WEIGHT * correctCnt - LOST_WEIGHT * lostCnt - REMAIN_WEIGHT * remainCnt - WRONG_WEIGHT * wrongCnt
    }

    companion object {
        const val NORMAL_WEIGHT = 1.0
        const val LOST_WEIGHT = -0.8
        const val REMAIN_WEIGHT = -1.2
        const val WRONG_WEIGHT = -3.0
    }
}
