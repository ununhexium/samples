package strassenbahn

interface Ticket {
    fun getPrice(area: Int): Float
    val description: String
    val adultCapacity: Int
}

data class Wanted {
    val adults = 0;
}

class TicketImpl(
    override val description: String,
    override val adultCapacity: Int,
    val prices: Map<Int, Float>
) : Ticket {
    override fun getPrice(area: Int): Float {
        return prices[area] ?: throw IllegalArgumentException("No price for area $area")
    }
}

/**
 * Find the cheapest price given a request and an offer
 */
fun cheapestPrice(adult: Wanted, offer: List<Ticket>): Float {
    return 0f
}
