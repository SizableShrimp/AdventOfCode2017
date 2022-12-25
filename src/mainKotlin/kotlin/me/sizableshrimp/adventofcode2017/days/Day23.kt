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
import kotlin.math.sqrt

class Day23 : SeparatedDay() {
    private val inputNum = this.lines[0].substring(6).toInt()

    override fun part1() = (this.inputNum - 2).let { it * it }

    override fun part2(): Any {
        var result = 0
        var b = this.inputNum * 100 + 100000

        for (round in 0..1000) {
            if (isComposite(b))
                result++
            b += 17
        }

        return result
    }

    private fun isComposite(num: Int): Boolean {
        if (num <= 3)
            return num == 1

        if (num % 2 == 0 || num % 3 == 0)
            return true

        val limit = sqrt(num.toDouble()).toInt()

        for (i in 5..limit step 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return true
        }

        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day23().run()
        }
    }
}