package strassenbahn

import java.util.LinkedList
import kotlin.math.max

interface Ticket {
    fun getPrice(area: Int): Float
    val description: String
    val adultCapacity: Int
    val childrenCapacity: Int
    val allowedTrips: Int
}

data class Wanted(
    val area: Int,
    val adults: Int = 0,
    val children: Int = 0,
    val tripsPerPeople: Int = 1
) {
    fun noPassenger() = (adults + children) <= 0
}

class TicketImpl(
    override val description: String,
    val prices: Map<Int, Number>,
    override val adultCapacity: Int = 0,
    override val childrenCapacity: Int = 0,
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

    fun getPrice(area: Int, trips: Int = 1) =
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
    val childrenSeats =
        flatTickets.sumBy {
            it.childrenCapacity
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
    return browseAllPropositions(
        wanted,
        offer.sortedByDescending { it.adultCapacity }
    )
}

fun browseAllPropositions(
    wanted: Wanted,
    offer: List<Ticket>
): Proposition {
    var bestSoFar = Proposition.EMPTY
    var bestPriceSoFar = Float.MAX_VALUE
    val queue = LinkedList<Proposition>(listOf(bestSoFar))

    /**
     * we may have to look for solutions which don't use all the seats
     * but we must not look for too long in that direction as this is:
     * 1. leading to OutOfMemory because a solution which can accomodate only chlidren isn't suited for adults
     * TODO: tune ratios
     */
    val maxChildrenToAdultRatio = offer.maxCAratio()
    val maxAdultToChildrenRatio = offer.maxACratio()

    val extraAdultsCapacity = offer.maxBy {
        it.adultCapacity
    }!!.adultCapacity + wanted.adults * maxAdultToChildrenRatio

    val extraChildrenCapacity = offer.maxBy {
        it.childrenCapacity
    }!!.childrenCapacity + wanted.children * maxChildrenToAdultRatio

    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        val price = current.getPrice(wanted.area, wanted.tripsPerPeople)
        val canAccomodateEverybody = current.adultSeats >= wanted.adults && current.childrenSeats >= wanted.children
        if (canAccomodateEverybody) {
            if (price < bestPriceSoFar) {
                bestSoFar = current
                bestPriceSoFar = price
            }
        }

        for (it in offer) {
            val new = current + it
            val noExcessAdultsCapacity = new.adultSeats <= wanted.adults + extraAdultsCapacity
            val noExcessChildrenCapacity = new.childrenSeats <= wanted.children + extraChildrenCapacity
            val newPrice = new.getPrice(wanted.area, wanted.tripsPerPeople)
            if (noExcessAdultsCapacity && noExcessChildrenCapacity && newPrice < bestPriceSoFar) {
                queue.add(new)
            }
        }
    }

    return bestSoFar
}

private fun <E : Ticket> List<E>.maxCAratio(): Double {
    val found = this.map {
        if (it.adultCapacity > 0) it.childrenCapacity / it.adultCapacity else 0
    }.max() ?: 1

    // don't return 0
    return max(1.0, found.toDouble())
}

private fun <E : Ticket> List<E>.maxACratio(): Double {
    val found = this.map {
        if (it.childrenCapacity > 0) it.adultCapacity / it.childrenCapacity else 0
    }.max() ?: 1

    // don't return 0
    return max(1.0, found.toDouble())
}


