package com.hedvig.domain

import java.time.LocalDate

interface Event : Comparable<Event> {
    val contratId: String
    val atDate: LocalDate
    val amount:  Int

    override fun compareTo(other: Event): Int {
        return this.atDate.compareTo(other.atDate)
    }
}

data class ContractCreatedEvent(
        override val contratId: String,
        val premium: Int,
        var startDate: LocalDate): Event{

    override val atDate: LocalDate
        get() = startDate
    override val amount: Int
        get() = premium

}

data class PriceIncreasedEvent(
        override val contratId: String,
        val premiumIncrease: Int,
        override val atDate: LocalDate): Event{

    override val amount: Int
        get() = premiumIncrease
}

data class PriceDecreaseEvent(
        override val contratId: String,
        val premiumReduction: Int,
        override val atDate: LocalDate): Event{

    override val amount: Int
        get() = premiumReduction
}

data class ContractTerminatedEvent(
        override val contratId: String,
        val terminationDate: LocalDate): Event {

    override val atDate: LocalDate
        get() = terminationDate
    override val amount: Int
        get() = 0
}