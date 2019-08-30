package com.hyuni.crawler.category.page

import com.hyuni.crawler.util.constant.Action
import java.util.*

class Page(private val category: String, private val index: Int) {

    var unit: MutableList<UnitPair> = mutableListOf()

    fun add(name: String, action: Action): Boolean {
        if(unit.size >= LIMIT_SIZE) return false

        unit.add(UnitPair(name, action))
        return true
    }

    fun getNextPage(): String {
        return "/$category/feature/${index + 1}"
    }

    fun getPrevPage(): String {
        return if(index > 1) "/$category/feature/${index - 1}" else ""
    }

    fun getRandom(): UnitPair {
        return unit[Random().nextInt(LIMIT_SIZE)]
    }

    companion object {
        const val LIMIT_SIZE = 30
    }
}
