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

import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.longs.LongList
import me.sizableshrimp.adventofcode2017.templates.SeparatedDay

class Day18 : SeparatedDay() {
    private lateinit var instructions: List<Instruction>

    override fun part1() = execute(arrayOf(MachineState(LongArray(26), part2 = false)))

    override fun part2(): Any {
        val queueA = LongArrayList()
        val queueB = LongArrayList()

        return execute(
            arrayOf(
                MachineState(LongArray(26), part2 = true, queue = queueA, otherQueue = queueB),
                MachineState(LongArray(26).also { it[15] = 1 }, part2 = true, queue = queueB, otherQueue = queueA)
            )
        )
    }

    private fun execute(machineStates: Array<MachineState>): Long {
        val part2 = machineStates[0].part2
        val instructionCount = this.instructions.size
        var sentCount = 0L
        var terminate = false

        while (!terminate) {
            var deadlock = true
            for (programId in machineStates.indices) {
                val machineState = machineStates[programId]
                val instruction = this.instructions[machineState.instructionIdx]
                if (part2) {
                    if (programId == 1 && instruction.operation == Operation.SEND)
                        sentCount++

                    if (instruction.operation == Operation.RECEIVE && machineState.queue!!.isEmpty()) {
                        continue
                    }
                }

                deadlock = false

                instruction.apply(machineState)

                if (machineState.received)
                    return machineState.frequency

                if (machineState.instructionIdx < 0 || machineState.instructionIdx >= instructionCount) {
                    terminate = true
                    break
                }
            }

            if (deadlock)
                terminate = true
        }

        return sentCount
    }

    override fun parse() {
        this.instructions = this.lines.map {
            val split = it.split(' ')
            val aRegister = split[1][0].isLetter()
            val a = if (aRegister) split[1][0] - 'a' else split[1].toInt()
            val hasB = split.size >= 3
            val bRegister = hasB && split[2][0].isLetter()
            val b = if (!hasB) 0 else (if (bRegister) split[2][0] - 'a' else split[2].toInt())
            Instruction(Operation.ID_MAP[split[0]]!!, a, aRegister, b, bRegister)
        }
    }

    private enum class Operation(val id: String, val function: (MachineState, Int, Long, Int, Long) -> Unit) {
        SEND("snd", { state, _, aVal, _, _ ->
            if (state.part2) {
                state.otherQueue!!.add(aVal)
            } else {
                state.frequency = aVal
            }
        }),
        SET("set", { state, a, _, _, bVal -> state.register[a] = bVal }),
        ADD("add", { state, a, _, _, bVal -> state.register[a] += bVal }),
        MULTIPLY("mul", { state, a, _, _, bVal -> state.register[a] *= bVal }),
        MODULO("mod", { state, a, _, _, bVal -> state.register[a] %= bVal }),
        RECEIVE("rcv", { state, a, aVal, _, _ ->
            if (state.part2) {
                state.register[a] = state.queue!!.removeLong(0)
            } else if (aVal != 0L) {
                state.received = true
            }
        }),
        JUMP("jgz", { state, _, aVal, _, bVal -> if (aVal > 0) state.instructionIdx += bVal.toInt() });

        companion object {
            val ID_MAP: Map<String, Operation> = Operation.values().associateBy { it.id }
        }
    }

    private data class Instruction(var operation: Operation, private val a: Int, private val aRegister: Boolean, private val b: Int, private val bRegister: Boolean) {
        fun apply(machineState: MachineState) {
            val aVal = if (this.aRegister) machineState.register[this.a] else this.a.toLong()
            val bVal = if (this.bRegister) machineState.register[this.b] else this.b.toLong()
            val oldInstructionIdx = machineState.instructionIdx
            this.operation.function(machineState, this.a, aVal, this.b, bVal)
            if (oldInstructionIdx == machineState.instructionIdx)
                machineState.instructionIdx++
        }
    }

    private class MachineState(
        val register: LongArray, var instructionIdx: Int = 0, var frequency: Long = 0, var received: Boolean = false,
        val part2: Boolean, val queue: LongList? = null, val otherQueue: LongList? = null
    )

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day18().run()
        }
    }
}