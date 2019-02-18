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
                3 to 14.5
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
            )
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
    fun `can find the cheapest ticket when there is only 1 person and 1 offer`() {
        assertThat(
            cheapestPrice(Wanted(1, adults = 1), listOf(oneWayAdult))
        ).isEqualTo(
            5.50
        )
    }
}
