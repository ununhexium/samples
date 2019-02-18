package strassenbahn

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
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

        val dailyTicketSingleAdult = TicketImpl(
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

    @Test
    fun `1 adults and 1 offer`() {
        val area = 1
        assertThat(
            cheapestPrice(Wanted(area, adults = 1), listOf(oneWayAdult))
        ).isEqualTo(
            Proposition(
                mapOf(
                    oneWayAdult to 1
                )
            )
        )
    }

    @Test
    fun `4 adults and 2 offers`() {
        val area = 1
        assertThat(
            cheapestPrice(Wanted(area, adults = 4), listOf(oneWayAdult, groupTicket))
        ).isEqualTo(
            // group ticket: 10
            // individual ticket: 4*3 = 12
            Proposition(
                mapOf(
                    groupTicket to 1
                )
            )
        )
    }

    @Test
    fun `3 adults and 2 offers`() {
        val area = 1
        assertThat(
            cheapestPrice(Wanted(area, adults = 3), listOf(oneWayAdult, groupTicket))
        ).isEqualTo(
            // group ticket: 10
            // individual ticket: 3*3 = 9
            Proposition(
                mapOf(
                    oneWayAdult to 3
                )
            )
        )
    }

    @Test
    fun `1 adult 2 trips with daily ticket`() {
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, adults = 1, tripsPerPeople = 2),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    dailyTicketSingleAdult to 1
                )
            )
        )
    }

    @Test
    fun `1 child`() {
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, children = 1, tripsPerPeople = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult, oneWayChild)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    oneWayChild to 1
                )
            )
        )
    }

    @Test
    fun `4 adults and 1 child`() {
        // in this case, getting a group ticket is cheaper than adult + child tickets
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, adults = 4, children = 1, tripsPerPeople = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult, oneWayChild)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    groupTicket to 1
                )
            )
        )
    }

    @Test
    fun `more monkeys`() {
        // in this case, getting a group ticket is cheaper than adult + child tickets
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, adults = 12, children = 3, tripsPerPeople = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult, oneWayChild)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    dailyTicketSingleAdult to 1,
                    groupTicket to 2,
                    oneWayAdult to 1
                )
            )
        )
    }

    @Test
    fun `many monkeys`() {
        // in this case, getting a group ticket is cheaper than adult + child tickets
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, adults = 24, children = 6, tripsPerPeople = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult, oneWayChild)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    dailyTicketSingleAdult to 2,
                    groupTicket to 5
                )
            )
        )
    }

    @Disabled
    @Test
    fun `lotsa monkeys`() {
        // in this case, getting a group ticket is cheaper than adult + child tickets
        val area = 1
        assertThat(
            cheapestPrice(
                Wanted(area, adults = 60, children = 15, tripsPerPeople = 1),
                listOf(oneWayAdult, groupTicket, dailyTicketSingleAdult, oneWayChild)
            )
        ).isEqualTo(
            // daily: 5.00
            // 2 tickets: 6.00
            Proposition(
                mapOf(
                    dailyTicketSingleAdult to 5,
                    groupTicket to 11
                )
            )
        )
    }
}
