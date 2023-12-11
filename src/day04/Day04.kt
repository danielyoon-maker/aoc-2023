package day04

import Day
import kotlin.math.pow

data class Card(val cardNumber: Int, val winningSet: Set<Int>, val cards: List<Int>) {
    fun winCount(): Int {
        return cards.filter(winningSet::contains).size
    }
}

object Today : Day {
    override val day: Int
        get() = 4

    private fun parseRow(row: String): Card {
        val cards =
            row.split(":").last().split("|")
                .map { parsed -> parsed.trim().split("\\s+".toRegex()).map { card -> card.toInt() }.toList() }
        return Card(row.split(":").first().split(" ").last().toInt(), cards.first().toSet(), cards.last())
    }

    private fun getCardValue(card: Card): Long {
        return 2.0.pow(card.winCount() - 1).toLong()
    }

    override fun part1(input: List<String>): Long {
        return input.map(this::parseRow).sumOf(this::getCardValue)
    }

    override fun part2(input: List<String>): Long {
        val cardInstances = mutableMapOf<Int, Long>()
        input.map(this::parseRow).forEach { card ->
            run {
                cardInstances[card.cardNumber] = cardInstances.getOrDefault(card.cardNumber, 0L) + 1L
                for (winNum in 1..card.winCount()) {
                    cardInstances[card.cardNumber + winNum] =
                        cardInstances.getOrDefault(
                            card.cardNumber + winNum,
                            0L
                        ) + cardInstances.getOrDefault(card.cardNumber, 1L)
                }
            }
        }
        return cardInstances.values.sum()
    }
}

fun main() {
    Today.solve1("test1", 13)
    Today.solve2("test1", 30)
}