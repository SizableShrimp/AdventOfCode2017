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

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import me.sizableshrimp.adventofcode2017.templates.Day
import me.sizableshrimp.adventofcode2017.util.max

class Day08 : Day() {
    override fun evaluate(): Result {
        val registers = Object2IntOpenHashMap<String>()
        var allMax = Int.MIN_VALUE

        for (line in this.lines) {
            val (insn, condition) = line.split(" if ")
            val (regName, op, num) = insn.split(" ")
            val splitCondition = condition.split(" ")
            val (checkReg, operation) = splitCondition

            val checkNum = registers.getInt(checkReg)
            val targetNum = splitCondition[2].toInt()

            if (when (operation) {
                    ">" -> checkNum > targetNum
                    ">=" -> checkNum >= targetNum
                    "<=" -> checkNum <= targetNum
                    "<" -> checkNum < targetNum
                    "==" -> checkNum == targetNum
                    "!=" -> checkNum != targetNum
                    else -> throw IllegalStateException()
                }
            ) {
                registers.addTo(regName, (if (op == "inc") 1 else -1) * num.toInt())
                val int = registers.getInt(regName)
                if (int > allMax)
                    allMax = int
            }
        }

        return Result.of(registers.values.max(), allMax)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day08().run()
        }
    }
}