package com.hyuni.crawler.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class FileGenerator private constructor() {

    private val postfixSize = 16
    private val postfix: CharArray = CharArray(postfixSize)
    private val component: Array<Char> = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    fun generateFileName(prefix: String): String {
        var result = prefix + "_"

        for(i in 0 until postfixSize) {
            postfix[i] = component[Random().nextInt(component.size)]
        }

        result += postfix.joinToString("") + ".jpg"

        return result
    }

    fun isValidName(prefix: String, name: String): Boolean {
        val sp = name.split("_")

        if(sp.size != 2) return false
        if(prefix != sp[0]) return false

        val spp = sp[1].split(".")

        if(spp.size != 2) return false
        if(spp[1] != "jpg") return false
        if(spp[0].length != postfixSize) return false
        spp[0].forEach {
            if((it.toInt() < '0'.toInt() || it.toInt() > '9'.toInt()) &&
                    (it.toInt() < 'a'.toInt() || it.toInt() > 'z'.toInt()) &&
                    (it.toInt() < 'A'.toInt() || it.toInt() > 'Z'.toInt())) return false
        }

        return true
    }

    companion object {

        private var instance: FileGenerator? = null

        fun getInstance(): FileGenerator = instance ?: synchronized(this) {
            instance ?: FileGenerator().also { instance = it }
        }
    }
}

