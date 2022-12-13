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

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import me.sizableshrimp.adventofcode2017.templates.Coordinate
import me.sizableshrimp.adventofcode2017.templates.Direction
import me.sizableshrimp.adventofcode2017.templates.SeparatedDay
import kotlin.math.abs
import kotlin.math.max

class Day03 : SeparatedDay() {
    private var target: Int = 0

    override fun part1(): Int {
        var total = 1
        var num = 0

        while (total <= this.target) {
            total += 8 * num
            num++
        }

        num--
        total -= 8 * num - 1

        val width = num * 2 + 1
        val left = max(0, this.target + 1 - total)
        val distance = when(width) {
            1 -> 0
            3 -> 1 + this.target % 2
            else -> num + abs(width / 2 - left % (width - 1))
        }

        return distance
    }

    override fun part2(): Int {
        val valuesMap = Object2IntOpenHashMap<Coordinate>()
        valuesMap.put(Coordinate.ORIGIN, 1)

        var found = 0
        var currWidth = 3
        var currIdx = -1
        var max = 7
        var pos = Coordinate.ORIGIN
        var dir = Direction.EAST

        while (found <= this.target) {
            if (currIdx == 0)
                dir = Direction.NORTH
            pos = pos.resolve(dir)
            found = 0
            for (adjDir in Direction.cardinalOrdinalDirections()) {
                found += valuesMap.getInt(pos.resolve(adjDir))
            }
            valuesMap.put(pos, found)
            currIdx++
            if ((currIdx + 1) % (currWidth - 1) == 0) {
                if (currIdx == max) {
                    currWidth += 2
                    max += 8
                    currIdx = -1
                } else {
                    dir = dir.counterClockwise()
                }
            }
        }

        return found
    }

    override fun parse() {
        this.target = this.lines[0].toInt()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day03().run()
        }
    }
}