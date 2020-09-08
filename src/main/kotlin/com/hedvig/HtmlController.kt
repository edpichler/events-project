package com.hedvig

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HtmlController {

    @GetMapping("/task1")
    fun task1(): String {
//    fun blog(model: Model): String {
//        model["title"] = "Blog"
        return "oi"
    }

    @GetMapping("/task2")
    fun task2(): String {
//    fun blog(model: Model): String {
//        model["title"] = "Blog"
        return "oi"
    }

}