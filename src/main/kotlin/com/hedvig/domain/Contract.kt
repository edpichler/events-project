package com.hedvig.domain

import java.time.LocalDate
import java.time.Month

data class Contract (val id: String) {

    private var events = mutableListOf<Event>()
    var initialPremium = 0
    var activationDate   : LocalDate? = null
    var expirationdate   : LocalDate? = null

    fun addEvent(evt: Event) {
        events.add(evt)
        when(evt::class) {
            ContractCreatedEvent::class -> {
                activationDate = evt.atDate
                initialPremium = evt.amount
            }
            ContractTerminatedEvent::class -> {
                expirationdate = evt.atDate
            }
        }
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
        return premium;
    }

    fun wasActiveAtMonth(month: Month) : Boolean{
        return activationDate != null && activationDate!!.monthValue <= month.value
                && (expirationdate == null || expirationdate!!.monthValue > month.value);
    }
}