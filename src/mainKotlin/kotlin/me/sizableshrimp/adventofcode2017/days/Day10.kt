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

import it.unimi.dsi.fastutil.ints.IntList
import me.sizableshrimp.adventofcode2017.templates.SeparatedDay
import me.sizableshrimp.adventofcode2017.util.toIntList
import kotlin.math.min

class Day10 : SeparatedDay() {
    override fun part1() = this.simulate(this.lines[0].split(",").map { it.trim().toInt() }.toIntArray(), 1).let {
        it.getInt(0) * it.getInt(1)
    }

    override fun part2() = this.simulate(this.lines[0].map { it.code }.toIntArray() + intArrayOf(17, 31, 73, 47, 23), 64).windowed(16, 16).joinToString("") {
        it.reduce { num: Int, knot -> num xor knot }.toString(radix = 16).padStart(2, padChar = '0')
    }

    private fun simulate(lengths: IntArray, rounds: Int): IntList {
        val knots = (0..255).toIntList()
        var pos = 0
        var skipSize = 0

        for (round in 0 until rounds) {
            for (length in lengths) {
                val subList = ArrayList(knots.subList(pos, min(knots.size, pos + length)))
                if (pos + length > knots.size) {
                    subList.addAll(knots.subList(0, (pos + length) % knots.size))
                }
                for (j in subList.indices) {
                    knots.set((pos + j) % knots.size, subList[subList.size - j - 1])
                }
                pos = (pos + length + skipSize) % knots.size
                skipSize++
            }
        }

        return knots
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day10().run()
        }
    }
}