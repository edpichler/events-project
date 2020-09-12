package com.hedvig.controller

import com.hedvig.model.ContractModel
import com.hedvig.repository.EventsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Month

@RestController
class HtmlController {

    val contractModel = ContractModel(EventsRepository().findAll());

    @GetMapping("/")
    fun render() : String{
        return tableTask1() + "<p/>" + tableTask2()
    }

    fun tableTask1(): String {
        val activeContracts = contractModel.getActiveContracts()
        val agwp = contractModel.getAGWP()
        val egwp = contractModel.getEGWP()
        return """
            <table>
                <thead> 
                    <tr>
                        <th colspan="13">Task 1</th>
                    </tr>
                </thead>
                <tbody>
                    <tr style="text-align: center;">
                        <td></td>
                        <td>January</td>
                        <td>February</td>
                        <td>March</td>
                        <td>April</td>
                        <td>May</td>
                        <td>June</td>
                        <td>July</td>
                        <td>August</td>
                        <td>September</td>
                        <td>October</td>
                        <td>November</td>
                        <td>December</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>Number of contracts</td>
                        <td>${activeContracts.getValue(Month.JANUARY).size}</td>
                        <td>${activeContracts.getValue(Month.FEBRUARY).size}</td>
                        <td>${activeContracts.getValue(Month.MARCH).size}</td>
                        <td>${activeContracts.getValue(Month.APRIL).size}</td>
                        <td>${activeContracts.getValue(Month.MAY).size}</td>
                        <td>${activeContracts.getValue(Month.JUNE).size}</td>
                        <td>${activeContracts.getValue(Month.JULY).size}</td>
                        <td>${activeContracts.getValue(Month.AUGUST).size}</td>
                        <td>${activeContracts.getValue(Month.SEPTEMBER).size}</td>
                        <td>${activeContracts.getValue(Month.OCTOBER).size}</td>
                        <td>${activeContracts.getValue(Month.NOVEMBER).size}</td>
                        <td>${activeContracts.getValue(Month.DECEMBER).size}</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>AGWP</td>
                        <td>${agwp.getValue(Month.JANUARY)}</td>
                        <td>${agwp.getValue(Month.FEBRUARY)}</td>
                        <td>${agwp.getValue(Month.MARCH)}</td>
                        <td>${agwp.getValue(Month.APRIL)}</td>
                        <td>${agwp.getValue(Month.MAY)}</td>
                        <td>${agwp.getValue(Month.JUNE)}</td>
                        <td>${agwp.getValue(Month.JULY)}</td>
                        <td>${agwp.getValue(Month.AUGUST)}</td>
                        <td>${agwp.getValue(Month.SEPTEMBER)}</td>
                        <td>${agwp.getValue(Month.OCTOBER)}</td>
                        <td>${agwp.getValue(Month.NOVEMBER)}</td>
                        <td>${agwp.getValue(Month.DECEMBER)}</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>EGWP</td>
                        <td>${egwp.getValue(Month.JANUARY)}</td>
                        <td>${egwp.getValue(Month.FEBRUARY)}</td>
                        <td>${egwp.getValue(Month.MARCH)}</td>
                        <td>${egwp.getValue(Month.APRIL)}</td>
                        <td>${egwp.getValue(Month.MAY)}</td>
                        <td>${egwp.getValue(Month.JUNE)}</td>
                        <td>${egwp.getValue(Month.JULY)}</td>
                        <td>${egwp.getValue(Month.AUGUST)}</td>
                        <td>${egwp.getValue(Month.SEPTEMBER)}</td>
                        <td>${egwp.getValue(Month.OCTOBER)}</td>
                        <td>${egwp.getValue(Month.NOVEMBER)}</td>
                        <td>${egwp.getValue(Month.DECEMBER)}</td>
                    </tr>
                </tbody>
            </table>
        """
    }

    fun tableTask2(): String {
        val activeContracts = contractModel.getActiveContracts()
        val agwpFull = contractModel.getAGWPFull()
        val egwpFull = contractModel.getEGWPFull()
        return """
            <table>
                <thead> 
                    <tr>
                        <th colspan="13">Task 2</th>
                    </tr>
                </thead>
                <tbody>
                    <tr style="text-align: center;">
                        <td></td>
                        <td>January</td>
                        <td>February</td>
                        <td>March</td>
                        <td>April</td>
                        <td>May</td>
                        <td>June</td>
                        <td>July</td>
                        <td>August</td>
                        <td>September</td>
                        <td>October</td>
                        <td>November</td>
                        <td>December</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>Number of contracts</td>
                        <td>${activeContracts.getValue(Month.JANUARY).size}</td>
                        <td>${activeContracts.getValue(Month.FEBRUARY).size}</td>
                        <td>${activeContracts.getValue(Month.MARCH).size}</td>
                        <td>${activeContracts.getValue(Month.APRIL).size}</td>
                        <td>${activeContracts.getValue(Month.MAY).size}</td>
                        <td>${activeContracts.getValue(Month.JUNE).size}</td>
                        <td>${activeContracts.getValue(Month.JULY).size}</td>
                        <td>${activeContracts.getValue(Month.AUGUST).size}</td>
                        <td>${activeContracts.getValue(Month.SEPTEMBER).size}</td>
                        <td>${activeContracts.getValue(Month.OCTOBER).size}</td>
                        <td>${activeContracts.getValue(Month.NOVEMBER).size}</td>
                        <td>${activeContracts.getValue(Month.DECEMBER).size}</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>AGWP</td>
                        <td>${agwpFull.getValue(Month.JANUARY)}</td>
                        <td>${agwpFull.getValue(Month.FEBRUARY)}</td>
                        <td>${agwpFull.getValue(Month.MARCH)}</td>
                        <td>${agwpFull.getValue(Month.APRIL)}</td>
                        <td>${agwpFull.getValue(Month.MAY)}</td>
                        <td>${agwpFull.getValue(Month.JUNE)}</td>
                        <td>${agwpFull.getValue(Month.JULY)}</td>
                        <td>${agwpFull.getValue(Month.AUGUST)}</td>
                        <td>${agwpFull.getValue(Month.SEPTEMBER)}</td>
                        <td>${agwpFull.getValue(Month.OCTOBER)}</td>
                        <td>${agwpFull.getValue(Month.NOVEMBER)}</td>
                        <td>${agwpFull.getValue(Month.DECEMBER)}</td>
                    </tr>
                    <tr style="text-align: center;">
                        <td>EGWP</td>
                        <td>${egwpFull.getValue(Month.JANUARY)}</td>
                        <td>${egwpFull.getValue(Month.FEBRUARY)}</td>
                        <td>${egwpFull.getValue(Month.MARCH)}</td>
                        <td>${egwpFull.getValue(Month.APRIL)}</td>
                        <td>${egwpFull.getValue(Month.MAY)}</td>
                        <td>${egwpFull.getValue(Month.JUNE)}</td>
                        <td>${egwpFull.getValue(Month.JULY)}</td>
                        <td>${egwpFull.getValue(Month.AUGUST)}</td>
                        <td>${egwpFull.getValue(Month.SEPTEMBER)}</td>
                        <td>${egwpFull.getValue(Month.OCTOBER)}</td>
                        <td>${egwpFull.getValue(Month.NOVEMBER)}</td>
                        <td>${egwpFull.getValue(Month.DECEMBER)}</td>
                    </tr>
                </tbody>
            </table>
        """
    }

}