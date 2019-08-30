package com.hyuni.crawler.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenGenerator {

    private final val tokenSize: Int = 32
    val token: CharArray = CharArray(tokenSize)
    val history: MutableSet<String> = mutableSetOf()
    val component: Array<Char> = arrayOf('#', '$', '%', '&', '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '=', '?', '@',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    fun generateToken(): String {
        var result: String? = null
        do {
            for(i in 0 until tokenSize) {
                token[i] = component[Random().nextInt(component.size)]
            }
            result = token.joinToString("")
        } while(history.contains(result))

        result?.let { history.add(it) }

        return result!!
    }

    fun isValidToken(token: String): Boolean = history.contains(token)
}
