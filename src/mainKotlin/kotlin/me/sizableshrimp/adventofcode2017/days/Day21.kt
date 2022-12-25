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

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import me.sizableshrimp.adventofcode2017.helper.GridHelper
import me.sizableshrimp.adventofcode2017.templates.Coordinate
import me.sizableshrimp.adventofcode2017.templates.Day
import me.sizableshrimp.adventofcode2017.util.deepCopy

class Day21 : Day() {
    private lateinit var patterns: List<Int2ObjectMap<Set<Coordinate>>>

    override fun evaluate(): Result {
        var grid = STARTING_GRID.deepCopy()
        var part1 = 0

        for (round in 1..18) {
            val enhancementType = if (grid.size % 2 == 0) 0 else 1
            val currChunkSize = if (enhancementType == 0) 2 else 3
            val newChunkSize = if (enhancementType == 0) 3 else 4
            val chunks = grid.size / currChunkSize
            val newSize = chunks * newChunkSize
            val newGrid = Array(newSize) { BooleanArray(newSize) }

            for (yChunk in 0 until chunks) {
                val startY = yChunk * currChunkSize
                val newStartY = yChunk * newChunkSize

                for (xChunk in 0 until chunks) {
                    val startX = xChunk * currChunkSize
                    val newStartX = xChunk * newChunkSize
                    var input = 0

                    for (yOff in 0 until currChunkSize) {
                        for (xOff in 0 until currChunkSize) {
                            if (grid[startY + yOff][startX + xOff])
                                input = input or (1 shl (yOff * currChunkSize + xOff))
                        }
                    }

                    val newCoords = this.patterns[enhancementType][input]
                    newCoords!!.forEach { coord -> newGrid[newStartY + coord.y][newStartX + coord.x] = true }
                }
            }

            grid = newGrid
            if (round == 5)
                part1 = GridHelper.countOccurrences(grid, true)
        }

        return Result.of(part1, GridHelper.countOccurrences(grid, true))
    }

    override fun parse() {
        this.patterns = listOf(Int2ObjectOpenHashMap(), Int2ObjectOpenHashMap())

        for (line in this.lines) {
            val enhancementType = if (line.length == 20) 0 else 1
            val pattern = this.patterns[enhancementType]
            val inputSize = if (enhancementType == 0) 2 else 3
            val (input, output) = line.split(" => ").map { GridHelper.convertToSet(it.split('/')) { c -> c == '#' } }

            for (base in listOf(input, GridHelper.reflectX(input, inputSize))) {
                for (rotation in 0..3) {
                    val rotated = GridHelper.rotate(base, inputSize, rotation * 90)
                    pattern.put(packInt(rotated, inputSize), output)
                }
            }
        }
    }

    private fun packInt(coords: Set<Coordinate>, size: Int): Int {
        var result = 0

        coords.forEach { result = result or (1 shl (it.y * size + it.x)) }

        return result
    }

    companion object {
        private val STARTING_GRID = arrayOf(booleanArrayOf(false, true, false), booleanArrayOf(false, false, true), booleanArrayOf(true, true, true))

        @JvmStatic
        fun main(args: Array<String>) {
            Day21().run()
        }
    }
}