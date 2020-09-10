package com.hedvig.model

import com.hedvig.controller.Task1
import com.hedvig.domain.ContractCreatedEvent
import com.hedvig.domain.ContractTerminatedEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.Month

internal class Task1Test {

    companion object
    {
        val januaryDate = LocalDate.now().withMonth(Month.JANUARY.value)
        val februaryDate = januaryDate.plusMonths(1)
        val marchDate = februaryDate.plusMonths(1)
        val aprilDate = marchDate.plusMonths(1)
    }

    private val events = arrayListOf(
            ContractCreatedEvent("1", 100, januaryDate),
            ContractCreatedEvent("2", 100, februaryDate),
            ContractTerminatedEvent("1", marchDate))

    @Test
    fun getNumberContracts() {
        val task1Model = Task1(events)
        val activeContracts = task1Model.getActiveContracts()
        assertThat(activeContracts.getValue(januaryDate.month).size).isEqualTo(1);
        assertThat(activeContracts.getValue(februaryDate.month).size).isEqualTo(2);
        assertThat(activeContracts.getValue(marchDate.month).size).isEqualTo(1);
    }

    @Test
    fun agwp() {
        val task1Model = Task1(events)
        val agwp = task1Model.getAGWP()
        assertThat(agwp.getValue(januaryDate.month)).isEqualTo(100);
        assertThat(agwp.getValue(februaryDate.month)).isEqualTo(300);
        assertThat(agwp.getValue(marchDate.month)).isEqualTo(400);
        assertThat(agwp.getValue(aprilDate.month)).isEqualTo(500);

    }

    @Test
    fun egwp() {
        val task1Model = Task1(events)
        val egwp = task1Model.getEGWP()
        assertThat(egwp.getValue(januaryDate.month)).isEqualTo(1200);
        assertThat(egwp.getValue(februaryDate.month)).isEqualTo(2300);
        assertThat(egwp.getValue(marchDate.month)).isEqualTo(400 + 900);
        assertThat(egwp.getValue(aprilDate.month)).isEqualTo(400 + 900);
    }
}