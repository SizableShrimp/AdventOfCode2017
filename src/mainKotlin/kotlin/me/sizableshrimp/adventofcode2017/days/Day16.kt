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

class Day16 : Day() {
    override fun evaluate(): Result {
        val dancerCount = 16
        val dancers = (0 until dancerCount).map { Char('a'.code + it) }.toMutableList()
        val seen = ArrayList<List<Char>>()

        val instructions = this.lines[0].split(",")
        var round = 1
        while (true) {
            for (insn in instructions) {
                val slashIdx = insn.indexOf('/')
                when (insn[0]) {
                    's' -> {
                        val count = insn.substring(1).toInt()
                        val sub = dancers.subList(dancerCount - count, dancerCount)
                        val subCopy = sub.toList()
                        sub.clear()
                        dancers.addAll(0, subCopy)
                    }

                    'x' -> {
                        val left = insn.substring(1, slashIdx).toInt()
                        val right = insn.substring(slashIdx + 1).toInt()
                        val temp = dancers[left]
                        dancers[left] = dancers[right]
                        dancers[right] = temp
                    }

                    'p' -> {
                        val left = dancers.indexOf(insn[1])
                        val right = dancers.indexOf(insn[3])
                        val temp = dancers[left]
                        dancers[left] = dancers[right]
                        dancers[right] = temp
                    }
                }
            }

            val seenIdx = seen.indexOf(dancers)
            if (seenIdx != -1) {
                val cycleLength = round - (seenIdx + 1)
                val loopedIdx = (1_000_000_000 - round) % cycleLength
                return Result.of(seen[0].joinToString(""), seen[loopedIdx].joinToString(""))
            }

            seen.add(dancers.toList())

            round++
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day16().run()
        }
    }
}