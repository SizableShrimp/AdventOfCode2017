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

class Day06 : Day() {
    override fun evaluate(): Result {
        val banks = IntArrayList(this.lines[0].split('\t').map { it.toInt() })
        val numBanks = banks.size
        val seen = ArrayList<IntArrayList>()

        seen.add(IntArrayList(banks))

        while (true) {
            var (i, toGive) = banks.withIndex().maxBy { it.value }
            banks[i] = 0

            while (toGive > 0) {
                i++
                if (i == numBanks)
                    i = 0

                toGive--
                banks.set(i, banks.getInt(i) + 1)
            }

            val idx = seen.indexOf(banks)
            if (idx > -1)
                return Result.of(seen.size, seen.size - idx)

            seen.add(IntArrayList(banks))
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day06().run()
        }
    }
}