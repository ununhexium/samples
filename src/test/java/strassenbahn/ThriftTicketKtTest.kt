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
                ),
                area
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
                ),
                area
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
                ),
                area
            )
        )
    }
}
