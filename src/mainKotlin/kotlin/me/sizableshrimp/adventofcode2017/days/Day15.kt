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

import me.sizableshrimp.adventofcode2017.templates.SeparatedDay

class Day15 : SeparatedDay() {
    private val startingGenA = this.lines[0].substring(24).toInt()
    private val startingGenB = this.lines[1].substring(24).toInt()

    override fun part1(): Any {
        var genA = this.startingGenA
        var genB = this.startingGenB
        var count = 0

        for (round in 1 .. 40_000_000) {
            genA = ((genA * GEN_A_FACTOR) % Integer.MAX_VALUE).toInt()
            genB = ((genB * GEN_B_FACTOR) % Integer.MAX_VALUE).toInt()

            if (isJudgeMatch(genA, genB))
                count++
        }

        return count
    }

    override fun part2(): Any {
        var genA = this.startingGenA
        var genB = this.startingGenB
        var count = 0
        var lastGenA = -1
        var lastGenB = -1

        for (round in 1 .. 5_000_000) {
            while (lastGenA == -1) {
                genA = ((genA * GEN_A_FACTOR) % Integer.MAX_VALUE).toInt()
                if (genA % GEN_A_MULTIPLE == 0)
                    lastGenA = genA
            }
            while (lastGenB == -1) {
                genB = ((genB * GEN_B_FACTOR) % Integer.MAX_VALUE).toInt()
                if (genB % GEN_B_MULTIPLE == 0)
                    lastGenB = genB
            }

            if ((lastGenA and 0xFFFF) == (lastGenB and 0xFFFF)) count++
            lastGenA = -1
            lastGenB = -1
        }

        return count
    }

    private fun isJudgeMatch(a: Int, b: Int): Boolean {
        return (a and 0xFFFF) == (b and 0xFFFF)
    }

    companion object {
        private const val GEN_A_FACTOR = 16807L
        private const val GEN_B_FACTOR = 48271L
        private const val GEN_A_MULTIPLE = 4
        private const val GEN_B_MULTIPLE = 8

        @JvmStatic
        fun main(args: Array<String>) {
            Day15().run()
        }
    }
}