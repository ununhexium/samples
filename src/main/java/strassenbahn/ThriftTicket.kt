package strassenbahn

interface Ticket {
    fun getPrice(area: Int): Float
    val description: String
    val adultCapacity: Int
    val childCapacity: Int
}

data class Wanted(val area: Int, val adults: Int = 0) {
    fun noPassenger() = adults <= 0
}

class TicketImpl(
    override val description: String,
    val prices: Map<Int, Number>,
    override val adultCapacity: Int = 0,
    override val childCapacity: Int = 0
) : Ticket {
    override fun getPrice(area: Int): Float {
        return prices[area]?.toFloat() ?: throw IllegalArgumentException("No price for area $area")
    }
}

data class Proposition(val tickets: Map<Ticket, Int>)

/**
 * Find the cheapest price given a request and an offer
 */
fun cheapestPrice(wanted: Wanted, offer: List<Ticket>): Proposition {
    if (wanted.noPassenger()) {
        throw IllegalArgumentException("Need at least 1 traveler")
    }
    val best = offer.minBy {
        it.getPrice(wanted.area)
    }!!
    return Proposition(
        mapOf(
            best to 1
        )
    )
}
