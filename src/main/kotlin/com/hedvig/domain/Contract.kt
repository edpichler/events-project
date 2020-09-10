package com.hedvig.domain

import java.time.LocalDate
import java.time.Month

data class Contract (val id: String) {

    private var events = mutableListOf<Event>()
    var active = false
    var premium = 0
    var activationDate   : LocalDate? = null
    var deactivationDate : LocalDate? = null

    fun addEvent(evt: Event) {
        events.add(evt)
        when(evt::class) {
            ContractCreatedEvent::class -> {
                active = true
                activationDate = evt.atDate
                premium = evt.amount
            }
            ContractTerminatedEvent::class -> {
                active = false
                deactivationDate = evt.atDate
            }
        }
    }

    fun wasActiveAtMonth(month: Month) : Boolean{
        return activationDate != null
                && activationDate!!.monthValue <= month.value && (deactivationDate == null || deactivationDate!!.monthValue > month.value);
    }
}