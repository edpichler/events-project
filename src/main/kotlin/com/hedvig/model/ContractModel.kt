package com.hedvig.model

import com.hedvig.domain.Contract
import com.hedvig.domain.Event
import java.time.Month

class ContractModel(events: List<Event>) {
    val contracts = mutableMapOf<String, Contract>()

    init {
        events.sortedBy { it.atDate }.forEach { this.newEvent(it) }
    }

    fun newEvent(evt: Event) {
        val contract = contracts.getOrPut(evt.contratId) { Contract(evt.contratId)}
        contract.addEvent(evt)
    }

    /**
     * Expected premium for the whole year not considering the premium event updates.
     */
    fun getEGWP(): Map<Month, Int> {
        val agwp = getAGWP()
        val egwp = mutableMapOf<Month, Int>()

        agwp.keys.forEach { month ->
            val currentAGWP          = getAGWP().getValue(month)
            val remainingNextMonths  = Month.DECEMBER.value - month.value
            val activePremiumAt       = sumPremiumInitial(getActiveContracts().getValue(month))
            val forecasted = currentAGWP + (remainingNextMonths * activePremiumAt)
            egwp.put(month, forecasted)
        }
        return egwp
    }

    /**
     * Expected premium for the whole year the premium event updates.
     */
    fun getEGWPFull(): Map<Month, Int> {
        val agwpFull = getAGWPFull()
        val egwpFull = mutableMapOf<Month, Int>()

        agwpFull.keys.forEach { month ->
            val currentAGWPFull          = getAGWPFull().getValue(month)
            val remainingNextMonths      = Month.DECEMBER.value - month.value
            val activePremiumsAt         = sumPremiumUpdated(getActiveContracts().getValue(month), month)
            val forecasted = currentAGWPFull + (remainingNextMonths * activePremiumsAt)
            egwpFull.put(month, forecasted)
        }
        return egwpFull
    }

    private fun sumPremiumInitial(contracts: List<Contract>): Int {
        var sum = 0
        contracts.forEach { sum += it.initialPremium }
        return sum;
    }

    private fun sumPremiumUpdated(contracts: List<Contract>, month: Month): Int {
        var sum = 0
        contracts.forEach { sum += it.premiumAt(month) }
        return sum;
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
            val accumulatedAmount = if (month.equals(Month.JANUARY)) 0 else agwp.getValue(Month.of(month.value - 1))

            var monthAmount =  sumPremiumInitial(getActiveContracts().getValue(month))

            agwp.put(month, monthAmount + accumulatedAmount)
        }
        return agwp;
    }

    /**
     * Accumulated premium considering all events.
     */
    fun getAGWPFull(): Map<Month, Int> {
        val agwpFull = mutableMapOf<Month, Int>()
        Month.values().forEach { month ->
            val accumulatedAmount = if (month.equals(Month.JANUARY)) 0 else agwpFull.getValue(Month.of(month.value - 1))

            var monthAmount =  sumPremiumUpdated(getActiveContracts().getValue(month), month)

            agwpFull.put(month, monthAmount + accumulatedAmount)
        }
        return agwpFull;
    }
}