package com.hedvig.repository

import com.hedvig.domain.Event
import java.io.File

class EventsRepository {
    private val eventParser = EventParser();

    fun findAll():List<Event> {
        val events = mutableListOf<Event>()
        File(this.javaClass.getResource("/test-data.txt").toURI()).forEachLine {
            val event = eventParser.parse(it);
            try {
                events.add(event)
            } catch (t: Throwable) {
               System.err.println ("Error when parsing the line. Content: $it")
               t.printStackTrace()
            }
        }

        return events;
    }
}