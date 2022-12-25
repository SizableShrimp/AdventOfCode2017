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

import it.unimi.dsi.fastutil.ints.IntArrayList
import me.sizableshrimp.adventofcode2017.templates.Day

class Day17 : Day() {
    override fun evaluate(): Result {
        val stepSize = this.lines[0].toInt()
        val buffer = IntArrayList()
        buffer.add(0)
        var idx = 0
        var targetIndex = 1
        var part2 = 0

        for (i in 1..50_000_000) {
            idx = (idx + stepSize) % i + 1
            if (idx == targetIndex)
                part2 = i
            if (i > 1 && idx < targetIndex)
                targetIndex++
            if (i <= 2017)
                buffer.add(idx, i)
        }

        return Result.of(buffer.getInt((buffer.indexOf(2017) + 1) % buffer.size), part2)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day17().run()
        }
    }
}