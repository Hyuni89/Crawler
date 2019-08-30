package com.hyuni.crawler.request

class CommonRequest {

    private var images: List<String> = emptyList()

    fun getImages(): List<String> {
        return images
    }

    fun setImages(images: List<String>) {
        this.images = images
    }
}
