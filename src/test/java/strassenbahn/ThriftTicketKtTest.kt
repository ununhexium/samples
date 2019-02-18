package strassenbahn

import org.assertj.core.api.Assertions.assertThat
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
            "Adult one way",
            mapOf(
                1 to 3.00,
                2 to 3.50,
                3 to 4.80,
                4 to 5.50
            ),
            1
        )

        val dailyTicketSingleAdult = TicketImpl(
            "Adult daily",
            mapOf(
                1 to 5.00,
                2 to 6.00,
                3 to 8.60,
                4 to 10.00
            ),
            adultCapacity = 1,
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
    fun `1 person and 1 offer`() {
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
    fun `4 people and 2 offers`() {
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
    fun `3 people and 2 offers`() {
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
    fun `1 person 2 trips with daily ticket`() {
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
}
