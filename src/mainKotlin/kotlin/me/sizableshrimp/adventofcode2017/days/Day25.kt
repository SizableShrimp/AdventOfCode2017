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

import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import me.sizableshrimp.adventofcode2017.templates.SeparatedDay
import me.sizableshrimp.adventofcode2017.util.splitOnBlankLines

class Day25 : SeparatedDay() {
    private var startingState: Int = 0
    private var maxSteps: Int = 0
    private lateinit var states: Array<State>

    override fun part1(): Any {
        var state = this.states[this.startingState]
        val tape = IntOpenHashSet()
        var cursor = 0

        for (step in 1..this.maxSteps) {
            val action = if (tape.contains(cursor)) state.actionOne else state.actionZero

            if (action.write == 1) tape.add(cursor) else tape.remove(cursor)
            cursor += action.move
            state = this.states[action.newState]
        }

        return tape.size
    }

    // No Part 2 :)
    override fun part2() = null

    override fun parse() {
        this.startingState = this.lines[0][15].code - 'A'.code
        this.maxSteps = this.lines[1].let { it.substring(36, it.indexOf(' ', startIndex = 36)).toInt() }

        val split = this.lines.splitOnBlankLines()
        this.states = Array(split.size - 1) { i ->
            val chunk = split[i + 1]
            val (actionZero, actionOne) = intArrayOf(2, 6).map { Action(chunk[it][22].digitToInt(), if (chunk[it + 1][27] == 'r') 1 else -1, chunk[it + 2][26].code - 'A'.code) }
            State(actionZero, actionOne)
        }
    }

    private data class Action(val write: Int, val move: Int, val newState: Int)

    private data class State(val actionZero: Action, val actionOne: Action)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day25().run()
        }
    }
}