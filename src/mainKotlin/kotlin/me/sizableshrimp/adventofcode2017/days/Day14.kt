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
import me.sizableshrimp.adventofcode2017.templates.Day
import me.sizableshrimp.adventofcode2017.templates.Direction

class Day14 : Day() {
    override fun evaluate(): Result {
        val grid = Array(128) { BooleanArray(128) }
        var part1 = 0

        for (row in 0 .. 127) {
            val hash = Day10.knotHash("${this.lines[0]}-$row")

            for (i in 0 until 128) {
                if (hash.testBit(i)) {
                    grid[row][i] = true
                    part1++
                }
            }
        }

        var regions = 0
        val seen = HashSet<Coordinate>()
        val queue = ArrayDeque<Coordinate>()

        for ((y, row) in grid.withIndex()) {
            for ((x, filled) in row.withIndex()) {
                if (!filled)
                    continue

                val coord = Coordinate.of(x, y)
                if (!seen.add(coord))
                    continue

                queue.clear()
                queue.add(coord)
                regions++

                while (!queue.isEmpty()) {
                    val node = queue.removeFirst()

                    for (dir in Direction.cardinalDirections()) {
                        val neighbor = node.resolve(dir)
                        if (GridHelper.isValid(grid, neighbor) && grid[neighbor.y][neighbor.x] && seen.add(neighbor))
                            queue.add(neighbor)
                    }
                }
            }
        }

        return Result.of(part1, regions)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day14().run()
        }
    }
}