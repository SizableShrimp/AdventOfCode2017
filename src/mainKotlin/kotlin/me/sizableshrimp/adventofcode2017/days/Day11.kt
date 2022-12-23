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

import me.sizableshrimp.adventofcode2017.templates.Day
import me.sizableshrimp.adventofcode2017.templates.ZCoordinate
import kotlin.math.abs

class Day11 : Day() {
    override fun evaluate(): Result {
        val dirs = this.lines[0].split(",").map { DIRECTIONS[it] }
        var coord = ZCoordinate.ORIGIN
        var part2 = 0
        var last = 0

        // https://www.redblobgames.com/grids/hexagons
        dirs.forEach {
            coord = coord.resolve(it)
            last = (abs(coord.x) + abs(coord.y) + abs(coord.z)) / 2
            if (last > part2)
                part2 = last
        }

        return Result.of(last, part2)
    }

    companion object {
        private val DIRECTIONS = mapOf(
            "n" to ZCoordinate.of(1, 0, -1), "ne" to ZCoordinate.of(0, 1, -1), "se" to ZCoordinate.of(-1, 1, 0),
            "s" to ZCoordinate.of(-1, 0, 1), "sw" to ZCoordinate.of(0, -1, 1), "nw" to ZCoordinate.of(1, -1, 0)
        )

        @JvmStatic
        fun main(args: Array<String>) {
            Day11().run()
        }
    }
}