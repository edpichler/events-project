package com.hedvig.domain

import java.time.LocalDate
import java.time.Month

data class Contract (val id: String) {

    private var events = mutableListOf<Event>()
    var premium = 0
    var activationDate   : LocalDate? = null
    var expirationdate   : LocalDate? = null

    fun addEvent(evt: Event) {
        events.add(evt)
        when(evt::class) {
            ContractCreatedEvent::class -> {
                activationDate = evt.atDate
                premium = evt.amount
            }
            ContractTerminatedEvent::class -> {
                expirationdate = evt.atDate
            }
        }
    }

    fun wasActiveAtMonth(month: Month) : Boolean{
        return activationDate != null && activationDate!!.monthValue <= month.value
                && (expirationdate == null || expirationdate!!.monthValue > month.value);
    }
}