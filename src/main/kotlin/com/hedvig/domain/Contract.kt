package com.hedvig.domain

import java.time.LocalDate
import java.time.Month

data class Contract (private val evt: ContractCreatedEvent) {
    val id : String = evt.contractId
    val initialPremium : Int = evt.premium
    private val activationDate  : LocalDate = evt.startDate
    private var events = mutableListOf<Event>()

    init {
        addEvent(evt)
    }

    private var expirationDate   : LocalDate? = null

    fun addEvent(evt: Event) {

        when(evt::class) {
            ContractCreatedEvent::class -> {
                if (events.isNotEmpty()) throw Exception("This contract already has a ${evt::class} event.")
            }
            ContractTerminatedEvent::class -> {
                expirationDate = evt.atDate
            }
        }
        events.add(evt)
    }

    fun premiumAt(month: Month) : Int {
        var premium = initialPremium
        events.filter{ it.atDate.monthValue <= month.value
                       && (it is PriceIncreasedEvent || it is PriceDecreaseEvent )}.forEach {
            when(it::class) {
                PriceIncreasedEvent::class -> {
                   premium += it.amount
                }
                PriceDecreaseEvent::class -> {
                  premium -= it.amount
                }
            }
        }
        return premium
    }

    fun wasActiveAtMonth(month: Month) : Boolean{
        return  activationDate.monthValue <= month.value
                && (expirationDate == null || expirationDate!!.monthValue > month.value)
    }
}