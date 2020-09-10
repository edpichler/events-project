package com.hedvig.model

import com.hedvig.domain.Contract
import com.hedvig.domain.Event
import java.time.Month

class ContractModel(events: ArrayList<Event>) {
    val contracts = mutableMapOf<String, Contract>()

    init {
        events.forEach { this.newEvent(it) }
    }

    fun newEvent(evt: Event) {
        val contract = contracts.getOrPut(evt.contratId) { Contract(evt.contratId)}
        contract.addEvent(evt)
    }

    /**
     * Expected premium for the whole year.
     */
    fun getEGWP(): Map<Month, Int> {
        val agwp = getAGWP()
        val egwp = mutableMapOf<Month, Int>()

        agwp.keys.forEach { month ->
            val currentAGWP          = getAGWP().getValue(month)
            val remainingNextMonths  = Month.DECEMBER.value - month.value
            val forecasted           = sumPremium(getActiveContracts().getValue(month))
            val value = currentAGWP + (remainingNextMonths * forecasted)
            egwp.put(month, value)
        }
        return egwp
    }

    private fun sumPremium(contracts: List<Contract>): Int {
        var sum = 0
        contracts.map { sum += it.premium }
        return sum;
    }

    fun getAccumulatedAGWP(month: Month): Int {
        var sum = 0
        getAGWP().filter { it.key.value <= month.value }.forEach {
            sum += it.value
        }
        return sum
    }

    fun getActiveContracts(): Map <Month, List<Contract>> {

        val activesMap = mutableMapOf<Month, List<Contract>>()
        Month.values().forEach { month ->
            val actives = this.contracts.filter{ it.value.wasActiveAtMonth(month)}.mapValues { it.value }.values

            activesMap.put(month, actives.toList())
        }
        return activesMap;
    }

    /**
     * Accumulated premium.
     */
    fun getAGWP(): Map<Month, Int> {
        val agwp = mutableMapOf<Month, Int>()
        Month.values().forEach { month ->
            var monthAmount = 0
            getActiveContracts()[month]?.forEach { monthAmount += it.premium}

            if (!month.equals( Month.JANUARY)) {
                monthAmount += agwp.get(Month.of(month.value - 1))!!
            }
            agwp.put(month, monthAmount)
        }
        return agwp;
    }
}