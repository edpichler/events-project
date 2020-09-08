package com.hedvig

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableAutoConfiguration
open class EventsApplication

fun main(args: Array<String>) {
    runApplication<EventsApplication>(*args)
}

