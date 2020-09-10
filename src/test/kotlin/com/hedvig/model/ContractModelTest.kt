package com.hedvig.model

import com.hedvig.domain.ContractCreatedEvent
import com.hedvig.domain.ContractTerminatedEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.Month

internal class ContractModelTest {

    companion object
    {
        val januaryDate = LocalDate.now().withMonth(Month.JANUARY.value)
        val februaryDate = januaryDate.plusMonths(1)
        val marchDate = februaryDate.plusMonths(1)
        val aprilDate = marchDate.plusMonths(1)
        val mayDate = aprilDate.plusMonths(1)
    }

    private val events = arrayListOf(
            ContractCreatedEvent("1", 100, LocalDate.of(2020, 1, 1)),
            ContractCreatedEvent("2", 100, LocalDate.of(2020, 2, 1)),
            ContractTerminatedEvent("1", LocalDate.of(2020, 2, 27)),
            ContractTerminatedEvent("2", LocalDate.of(2020, 3, 30)),
    )

    @Test
    fun isActiveTest(){
        val contractModel = ContractModel(events)
        val contract1  = contractModel.getActiveContracts().getValue(Month.JANUARY).filter { it.id.equals("1") }.first()
        val contract2  = contractModel.getActiveContracts().getValue(Month.FEBRUARY).filter { it.id.equals("2") }.first()
        assertThat(contract1.wasActiveAtMonth(Month.FEBRUARY)).isTrue
        assertThat(contract1.wasActiveAtMonth(Month.MARCH)).isTrue
        assertThat(contract2.wasActiveAtMonth(Month.MARCH)).isTrue
    }

    @Test
    fun getNumberContracts() {
        val contractModel = ContractModel(events)
        val activeContracts = contractModel.getActiveContracts()
        assertThat(activeContracts.getValue(januaryDate.month).size).isEqualTo(1);
        assertThat(activeContracts.getValue(februaryDate.month).size).isEqualTo(2);
        assertThat(activeContracts.getValue(marchDate.month).size).isEqualTo(2);
        assertThat(activeContracts.getValue(aprilDate.month).size).isEqualTo(1);
        assertThat(activeContracts.getValue(mayDate.month).size).isEqualTo(0);
    }

    @Test
    fun agwp() {
        val contractModel = ContractModel(events)
        val agwp = contractModel.getAGWP()
        assertThat(agwp.getValue(januaryDate.month)).isEqualTo(100);
        assertThat(agwp.getValue(februaryDate.month)).isEqualTo(300);
        assertThat(agwp.getValue(marchDate.month)).isEqualTo(500);
        assertThat(agwp.getValue(aprilDate.month)).isEqualTo(600);
        assertThat(agwp.getValue(mayDate.month)).isEqualTo(600);

    }

    @Test
    fun egwp() {
        val contractModel = ContractModel(events)
        val egwp = contractModel.getEGWP()
        assertThat(egwp.getValue(januaryDate.month)).isEqualTo(1200);
        assertThat(egwp.getValue(februaryDate.month)).isEqualTo(2400);
        assertThat(egwp.getValue(marchDate.month)).isEqualTo(2400);
        assertThat(egwp.getValue(aprilDate.month)).isEqualTo(600);
        assertThat(egwp.getValue(marchDate.month)).isEqualTo(600);
    }
}