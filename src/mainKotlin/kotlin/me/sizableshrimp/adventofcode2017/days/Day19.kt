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

import me.sizableshrimp.adventofcode2017.templates.Coordinate
import me.sizableshrimp.adventofcode2017.templates.Day
import me.sizableshrimp.adventofcode2017.templates.Direction

class Day19 : Day() {
    override fun evaluate(): Result {
        var prevCoord: Coordinate? = null
        var coord = Coordinate.of(this.lines[0].indexOf('|'), 0)
        var dir = Direction.SOUTH
        val result = StringBuilder()
        var steps = 1

        while (true) {
            val c = this.lines[coord.y][coord.x]
            if (c.isLetter()) {
                result.append(c)
            } else if (c == '+') {
                for (nextDir in Direction.cardinalDirections()) {
                    val neighbor = coord.resolve(nextDir)
                    if (neighbor == prevCoord || neighbor.y < 0 || neighbor.y >= this.lines.size)
                        continue

                    val line = this.lines[neighbor.y]
                    if (neighbor.x < 0 || neighbor.x >= line.length || line[neighbor.x] == ' ')
                        continue

                    prevCoord = coord
                    coord = neighbor
                    dir = nextDir
                    steps++
                    break
                }
                continue
            }

            prevCoord = coord
            coord = coord.resolve(dir)
            if (coord.y < 0 || coord.y >= this.lines.size)
                break

            val line = this.lines[coord.y]
            if (coord.x < 0 || coord.x >= line.length || line[coord.x] == ' ') break

            steps++
        }

        return Result.of(result.toString(), steps)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day19().run()
        }
    }
}