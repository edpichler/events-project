package com.hedvig.model

import com.hedvig.domain.Contract
import com.hedvig.domain.ContractCreatedEvent
import com.hedvig.domain.Event
import java.time.Month

class ContractModel {
    val contracts = mutableMapOf<String, Contract>()

    fun newEvent(evt: Event) {
        val contract = contracts.getOrPut(evt.contratId) { Contract(evt.contratId)}
        contract.addEvent(evt)
    }
}