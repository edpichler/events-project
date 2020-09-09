package com.hedvig.model

import com.hedvig.domain.Event
import java.time.Month

class Task1Records (val events : ArrayList<Event>){
    init {
        events.forEach { add(it) }
    }

    val numberContracts = mutableMapOf<Month, Int>()

    fun add(event: Event) {
        this.numberContracts.put(event.atDate.month, x)

    }

}