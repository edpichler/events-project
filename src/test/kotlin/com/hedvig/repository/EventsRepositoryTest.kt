package com.hedvig.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EventsRepositoryTest {
    private val eventsRepository = com.hedvig.repository.EventsRepository();

    @Test
    fun findAll() {
        assertThat(eventsRepository.findAll().size).isEqualTo(2376)
    }
}