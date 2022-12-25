/*
 * AdventOfCode2017
 * Copyright (C) 2022 SizableShrimp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.sizableshrimp.adventofcode2017.days

import me.sizableshrimp.adventofcode2017.helper.GridHelper
import me.sizableshrimp.adventofcode2017.templates.Coordinate
import me.sizableshrimp.adventofcode2017.templates.Direction
import me.sizableshrimp.adventofcode2017.templates.SeparatedDay

class Day22 : SeparatedDay() {
    private lateinit var infections: Map<Coordinate, State>

    override fun part1() = simulate(false, 10_000)

    override fun part2() = simulate(true, 10_000_000)

    private fun simulate(part2: Boolean, burstCount: Int): Int {
        val infections = this.infections.toMap(HashMap())
        var coord = Coordinate.of(this.lines[0].length / 2, this.lines.size / 2)
        var dir = Direction.NORTH
        var infectionCount = 0
        val skipCount = if (part2) 1 else 2

        for (burst in 1..burstCount) {
            val state = infections[coord] ?: State.CLEAN
            dir = when (state) {
                State.CLEAN -> dir.counterClockwise()
                State.WEAKENED -> dir
                State.INFECTED -> dir.clockwise()
                State.FLAGGED -> dir.opposite()
            }
            val newState = State.values()[(state.ordinal + skipCount) % 4]
            infections[coord] = newState
            coord = coord.resolve(dir)
            if (newState == State.INFECTED)
                infectionCount++
        }
        return infectionCount
    }

    override fun parse() {
        this.infections = GridHelper.convertToSet(this.lines) { c -> c == '#' }.associateWith { State.INFECTED }
    }

    private enum class State {
        CLEAN, WEAKENED, INFECTED, FLAGGED
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day22().run()
        }
    }
}

