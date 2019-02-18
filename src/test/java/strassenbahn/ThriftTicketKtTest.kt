package strassenbahn

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

internal class ThriftTicketKtTest {

    companion object {
        val groupTicket = TicketImpl(
            "Group ticket",
            mapOf(
                1 to 10,
                2 to 12,
                3 to 14.5,
                4 to 20
            ),
            5,
            0
        )

        val oneWayAdult = TicketImpl(
            "Adult: one way",
            mapOf(
                1 to 3.00,
                2 to 3.50,
                3 to 4.80,
                4 to 5.50
            ),
            1
        )

        val oneWayChild = TicketImpl(
            "Child: one way",
            mapOf(
                1 to 3.00 / 2,
                2 to 3.50 / 2,
                3 to 4.80 / 2,
                4 to 5.50 / 2
            ),
            childrenCapacity = 1
        )

        val dailyTicketSingleAdultWith3Children = TicketImpl(
            "Adult: daily (+3 children)",
            mapOf(
                1 to 5.00,
                2 to 6.00,
                3 to 8.60,
                4 to 10.00
            ),
            adultCapacity = 1,
            childrenCapacity = 3,
            allowedTrips = Int.MAX_VALUE
        )
    }

    @Test
    fun `throws an exception when there is noone on the ride`() {
        assertThat(
            assertThrows<IllegalArgumentException> {
                cheapestPrice(Wanted(1), listOf(oneWayAdult))
            }.message
        ).isEqualTo(
            "Need at least 1 traveler"
        )
    }

    @TestFactory
    fun factory(): Iterable<DynamicTest> {

        data class Case(
            val name: String,
            val wanted: Wanted,
            val offer: List<Ticket>,
            val proposition: Proposition
        )

        val area = 1

        return listOf(
            Case(
                "1 adults and 1 offer",
                Wanted(area, adults = 1),
                listOf(oneWayAdult),
                Proposition(
                    mapOf(
                        oneWayAdult to 1
                    )
                )
            ),
            Case(
                "4 adults and 2 offers",
                Wanted(area, adults = 4),
                listOf(oneWayAdult, groupTicket),
                Proposition(
                    mapOf(
                        groupTicket to 1
                    )
                )
            ),
            Case(
                "3 adults and 2 offers",
                Wanted(area, adults = 3),
                listOf(oneWayAdult, groupTicket),
                Proposition(
                    mapOf(
                        oneWayAdult to 3
                    )
                )
            ),
            Case(
                "1 adult 2 trips with daily ticket",
                Wanted(area, adults = 1, children = 0, tripsPerPeople = 2),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdultWith3Children, oneWayChild),
                Proposition(
                    mapOf(
                        dailyTicketSingleAdultWith3Children to 1
                    )
                )
            ),
            Case(
                "1 child",
                Wanted(area, children = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdultWith3Children, oneWayChild),
                Proposition(
                    mapOf(
                        oneWayChild to 1
                    )
                )
            ),
            Case(
                "4 adults and 1 child - child can use adult seat",
                Wanted(area, adults = 4, children = 1),
                listOf(oneWayAdult, groupTicket),
                Proposition(
                    mapOf(
                        groupTicket to 1
                    )
                )
            ),
            Case(
                "more monkeys",
                Wanted(area, adults = 12, children = 3),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdultWith3Children, oneWayChild),
                Proposition(
                    mapOf(
                        dailyTicketSingleAdultWith3Children to 1,
                        groupTicket to 2,
                        oneWayAdult to 1
                    )
                )
            ),
            Case(
                "many monkeys",
                Wanted(area, adults = 24, children = 6),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdultWith3Children, oneWayChild),
                Proposition(
                    mapOf(
                        dailyTicketSingleAdultWith3Children to 2,
                        groupTicket to 4,
                        oneWayAdult to 2
                    )
                )
            ),
            Case(
                "lotsa monkeys",
                Wanted(area, adults = 60, children = 15),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdultWith3Children, oneWayChild),
                Proposition(
                    mapOf(
                        dailyTicketSingleAdultWith3Children to 5,
                        groupTicket to 11
                    )
                )
            )
        ).map { case ->
            DynamicTest.dynamicTest("${case.name}") {
                with(case) {
                    val cheapestPrice = cheapestPrice(wanted, offer)

                    assertThat(
                        proposition.canAccommodate(wanted)
                    ).describedAs(
                        "check that clever monkey didn't screw up"
                    ).isTrue()

                    assertThat(
                        cheapestPrice.canAccommodate(wanted)
                    ).describedAs(
                        "basic check that the machine didn't return an invalid result"
                    ).isTrue()

                    /*
                     * if the below assertion is false,
                     * and the rest of the assertions are true,
                     * then monkey didn't find the best answer
                     */
                    assertThat(
                        cheapestPrice.getPrice(wanted.area, wanted.tripsPerPeople)
                    ).describedAs(
                        "Not the best option"
                    ).isGreaterThanOrEqualTo(
                        proposition.getPrice(wanted.area, wanted.tripsPerPeople)
                    )

                    assertThat(
                        cheapestPrice
                    ).describedAs(
                        "check that monkey and machine agree with each other"
                    ).isEqualTo(
                        proposition
                    )
                }
            }
        }
    }
}
