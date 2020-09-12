package com.hedvig.model

import com.hedvig.domain.ContractCreatedEvent
import com.hedvig.domain.ContractTerminatedEvent
import com.hedvig.domain.PriceDecreaseEvent
import com.hedvig.domain.PriceIncreasedEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.Month

internal class ContractModelTest {

    private val events = arrayListOf(

            ContractCreatedEvent("1", 100, createDate(Month.JANUARY, 1)),
            ContractTerminatedEvent("1", createDate(Month.FEBRUARY, 27)),

            ContractCreatedEvent("2", 100, createDate(Month.FEBRUARY, 1)),
            ContractTerminatedEvent("2", createDate(Month.MARCH, 30)),

            ContractCreatedEvent("3", 100, createDate(Month.APRIL, 1)),
            PriceIncreasedEvent("3", 50, createDate(Month.MAY, 1)),
            PriceDecreaseEvent("3", 50, createDate(Month.JUNE, 1)),
            ContractTerminatedEvent("3", createDate(Month.OCTOBER, 1)),

    )
    init {
        Month.values().forEach { dates.put(it, createDate(it)) }
    }
    private val contractModel = ContractModel(events)

    @Test
    fun isActiveTest() {

        val contract1 = contractModel.getActiveContracts().getValue(Month.JANUARY).filter { it.id.equals("1") }.first()
        val contract2 = contractModel.getActiveContracts().getValue(Month.FEBRUARY).filter { it.id.equals("2") }.first()
        assertThat(contract1.wasActiveAtMonth(Month.FEBRUARY)).isFalse
        assertThat(contract2.wasActiveAtMonth(Month.FEBRUARY)).isTrue
        assertThat(contract1.wasActiveAtMonth(Month.MARCH)).isFalse
        assertThat(contract2.wasActiveAtMonth(Month.MARCH)).isFalse
    }

    @Test
    fun getNumberContractsTest() {

        val activeContracts = contractModel.getActiveContracts()
        assertThat(activeContracts.getValue(Month.JANUARY).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.FEBRUARY).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.MARCH).size).isEqualTo(0)
        assertThat(activeContracts.getValue(Month.APRIL).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.MAY).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.JUNE).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.JULY).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.AUGUST).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.SEPTEMBER).size).isEqualTo(1)
        assertThat(activeContracts.getValue(Month.OCTOBER).size).isEqualTo(0)
    }

    @Test
    fun agwpTest() {

        val agwp = contractModel.getAGWP()
        assertThat(agwp.getValue(Month.JANUARY)).isEqualTo(100)
        assertThat(agwp.getValue(Month.FEBRUARY)).isEqualTo(200)
        assertThat(agwp.getValue(Month.MARCH)).isEqualTo(200)
        assertThat(agwp.getValue(Month.APRIL)).isEqualTo(300)
        assertThat(agwp.getValue(Month.MAY)).isEqualTo(400)

    }

    @Test
    fun agwpFullTest() {

        val agwpFull = contractModel.getAGWPFull()
        assertThat(agwpFull.getValue(Month.JANUARY)).isEqualTo(100)
        assertThat(agwpFull.getValue(Month.FEBRUARY)).isEqualTo(200)
        assertThat(agwpFull.getValue(Month.MARCH)).isEqualTo(200)
        assertThat(agwpFull.getValue(Month.APRIL)).isEqualTo(300)
        assertThat(agwpFull.getValue(Month.MAY)).isEqualTo(450)
        assertThat(agwpFull.getValue(Month.JUNE)).isEqualTo(550)
        assertThat(agwpFull.getValue(Month.JULY)).isEqualTo(650)

    }

    @Test
    fun egwpTest() {

        val egwp = contractModel.getEGWP()
        assertThat(egwp.getValue(Month.JANUARY)).isEqualTo(1200)
        assertThat(egwp.getValue(Month.FEBRUARY)).isEqualTo(1200)
        assertThat(egwp.getValue(Month.MARCH)).isEqualTo(200)
        assertThat(egwp.getValue(Month.APRIL)).isEqualTo(1100)
        assertThat(egwp.getValue(Month.MAY)).isEqualTo(1100)
    }

    companion object {
        val dates = mutableMapOf<Month, LocalDate>()

        private fun createDate(month: Month): LocalDate {
            return LocalDate.now().withMonth(month.value)
        }
        private fun createDate(month: Month, day: Int): LocalDate {
            return createDate(month).withDayOfMonth(day);
        }

    }
}