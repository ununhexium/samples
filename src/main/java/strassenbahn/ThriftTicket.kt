package strassenbahn

interface Ticket {
    fun getPrice(area: Int): Float
    val description: String
    val adultCapacity: Int
    val childCapacity: Int
    val allowedTrips: Int
}

data class Wanted(
    val area: Int,
    val adults: Int = 0,
    val children: Int = 0,
    val tripsPerPeople: Int = 1
) {
    fun noPassenger() = adults <= 0
}

class TicketImpl(
    override val description: String,
    val prices: Map<Int, Number>,
    override val adultCapacity: Int = 0,
    override val childCapacity: Int = 0,
    override val allowedTrips: Int = 1
) : Ticket {
    override fun getPrice(area: Int): Float {
        return prices[area]?.toFloat() ?: throw IllegalArgumentException("No price for area $area")
    }

    override fun toString() = description
}

data class Proposition(val tickets: Map<Ticket, Int>) {
    companion object {
      val EMPTY = Proposition(mapOf())
    }
    fun getPrice(area:Int, trips:Int = 1) =
        tickets.map {
            // are many tickets are needed to make the desired number of trips
            val ticketsCount = Math.ceil(trips / it.key.allowedTrips.toDouble()).toInt()
            it.key.getPrice(area) * it.value * ticketsCount
        }.sum()

    val flatTickets: List<Ticket> by lazy {
        tickets.flatMap { entry ->
            List(entry.value) { entry.key }
        }
    }

    val adultSeats =
        flatTickets.sumBy {
            it.adultCapacity
        }

    operator fun plus(ticket: Ticket): Proposition {
        val new = this.tickets.toMutableMap()
        new[ticket] = (this.tickets[ticket] ?: 0) + 1
        return Proposition(new)
    }
}

/**
 * Find the cheapest price given a request and an offer
 */
fun cheapestPrice(wanted: Wanted, offer: List<Ticket>): Proposition {
    if (wanted.noPassenger()) {
        throw IllegalArgumentException("Need at least 1 traveler")
    }
    val initial = Proposition.EMPTY

    return browseAllPropositions(
        initial,
        wanted,
        offer.sortedByDescending { it.adultCapacity }
    )
}

fun browseAllPropositions(
    bestSoFar: Proposition,
    wanted: Wanted,
    offer: List<Ticket>
): Proposition {
    if (bestSoFar.adultSeats >= wanted.adults) {
        return bestSoFar
    }

    return offer.map { it ->
        val new = bestSoFar + it
        browseAllPropositions(new, wanted, offer)
    }.sortedBy {
        it.getPrice(wanted.area, wanted.tripsPerPeople)
    }.first()
}


