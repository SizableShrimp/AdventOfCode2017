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
import it.unimi.dsi.fastutil.ints.IntList
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.ints.IntSet
import me.sizableshrimp.adventofcode2017.templates.Day

class Day12 : Day() {
    private lateinit var programs: Array<Program>

    override fun evaluate(): Result {
        var part1 = 0
        var groups = 0
        val taken = IntOpenHashSet()

        for (program in this.programs) {
            if (!taken.contains(program.id)) {
                groups++
                val group = IntOpenHashSet()
                program.fillGroup(this.programs, group)
                taken.addAll(group)
                if (group.contains(0))
                    part1 = group.size
            }
        }

        return Result.of(part1, groups)
    }

    override fun parse() {
        this.programs = Array(this.lines.size) { id ->
            val line = this.lines[id]
            val canReach = IntArrayList()
            line.substring(line.indexOf(" <-> ") + 5).split(", ").forEach { canReach.add(it.toInt()) }
            Program(id, canReach)
        }
    }

    private class Program(val id: Int, val canReach: IntList) {
        fun fillGroup(programs: Array<Program>, group: IntSet) {
            if (group.add(this.id))
                this.canReach.forEach { programs[it].fillGroup(programs, group) }
        }

        override fun toString() = this.id.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day12().run()
        }
    }
}