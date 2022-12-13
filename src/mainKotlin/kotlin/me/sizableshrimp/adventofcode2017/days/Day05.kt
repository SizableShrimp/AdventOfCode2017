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

class Day05 : SeparatedDay() {
    private lateinit var jumps: IntArray

    override fun part1() = simulate(false)

    override fun part2() = simulate(true)

    private fun simulate(part2: Boolean): Int {
        var steps = 0
        val jumps = this.jumps.clone()
        var i = 0

        while (i >= 0 && i < jumps.size) {
            val newI = jumps[i]
            if (part2 && newI >= 3)
                jumps[i]--
            else
                jumps[i]++
            i += newI
            steps++
        }

        return steps
    }

    override fun parse() {
        this.jumps = IntArray(this.lines.size)
        this.lines.forEachIndexed { i, line -> this.jumps[i] = line.toInt() }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day05().run()
        }
    }
}