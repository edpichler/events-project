package com.hedvig.repository

import com.hedvig.domain.ContractCreatedEvent
import com.hedvig.domain.ContractTerminatedEvent
import com.hedvig.domain.Event
import com.hedvig.domain.PriceIncreasedEvent
import java.time.LocalDate

class EventParser {

    fun parse(line: String): Event {

        val value = parseString("name", line)

        when(value) {
            "ContractCreatedEvent" -> {
                return ContractCreatedEvent(parseString("contractId", line),
                        parseInt("premium", line),
                        parseLocalDate("startDate", line))
            }
            "ContractTerminatedEvent" -> {
                return ContractTerminatedEvent(parseString("contractId", line),
                        parseLocalDate("terminationDate", line))
            }
            "PriceIncreasedEvent" -> {
                return PriceIncreasedEvent(parseString("contractId", line),
                        parseInt("premiumIncrease", line),
                        parseLocalDate("atDate", line))
            }
            "PriceDecreasedEvent" -> {
                return PriceIncreasedEvent(parseString("contractId", line),
                        parseInt("premiumReduction", line),
                        parseLocalDate("atDate", line))
            }
        }

        return ContractCreatedEvent("", 0, LocalDate.now());
    }

    private fun parseLocalDate(attribute: String, line: String): LocalDate {
        val pattern = "$attribute\" *: *\"([\\d\\-]*)"
        val regex = Regex(pattern)
        return LocalDate.parse(regex.find(line)!!.groups[1]!!.value)
    }

    private fun parseInt(attribute : String, line: String): Int
    {
        val pattern = "$attribute\": *(\\w*)"
        val regex = Regex(pattern)
        return Integer.parseInt(regex.find(line)!!.groups[1]!!.value)
    }

    private fun parseString(attribute : String, line: String): String {
        val pattern = "$attribute\":\"(\\w*)"
        val regex = Regex(pattern)
        return regex.find(line)!!.groups[1]!!.value
    }
}