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

class Day09 : Day() {
    override fun evaluate(): Result {
        var score = 0
        var nest = 0
        var totalGarbage = 0
        var garbage = false
        var skip = false

        for (c in this.lines[0]) {
            if (skip) {
                skip = false
                continue
            }
            if (garbage) {
                when (c) {
                    '!' -> skip = true
                    '>' -> garbage = false
                    else -> totalGarbage++
                }
                continue
            }

            when (c) {
                '{' -> nest++
                '}' -> {
                    score += nest
                    nest--
                }
                '<' -> garbage = true
            }
        }

        return Result.of(score, totalGarbage)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day09().run()
        }
    }
}