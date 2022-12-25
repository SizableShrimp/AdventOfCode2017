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

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import me.sizableshrimp.adventofcode2017.templates.Day

class Day24 : Day() {
    private lateinit var components: List<Component>
    private lateinit var componentsMap: Int2ObjectMap<Set<Component>>

    override fun evaluate(): Result {
        val queue = ArrayDeque<Node>()
        val startingNode = Node()
        this.components.forEach {
            if (it.left == 0 || it.right == 0)
                queue.add(startingNode.use(it))
        }

        val usedSet = LongOpenHashSet()
        var maxStrengthPart1 = 0
        var maxLength = 0
        var maxStrengthPart2 = 0

        while (!queue.isEmpty()) {
            val node = queue.removeFirst()

            if (node.strength > maxStrengthPart1)
                maxStrengthPart1 = node.strength

            if (node.usedCount > maxLength) {
                maxLength = node.usedCount
                maxStrengthPart2 = node.strength
            } else if (node.usedCount == maxLength && node.strength > maxStrengthPart2) {
                maxStrengthPart2 = node.strength
            }

            this.componentsMap.get(node.openPort).forEach {
                if (!node.isUsed(it)) {
                    val newNode = node.use(it)
                    if (usedSet.add(newNode.used))
                        queue.add(newNode)
                }
            }
        }

        return Result.of(maxStrengthPart1, maxStrengthPart2)
    }

    override fun parse() {
        this.components = this.lines.mapIndexed { index, line ->
            val (left, right) = line.split('/')
            Component(index, left.toInt(), right.toInt())
        }

        val componentsMap = Int2ObjectOpenHashMap<MutableSet<Component>>()
        @Suppress("UNCHECKED_CAST")
        this.componentsMap = componentsMap as Int2ObjectMap<Set<Component>>
        this.components.forEach {
            componentsMap.computeIfAbsent(it.left, Int2ObjectFunction { HashSet() }).add(it)
            componentsMap.computeIfAbsent(it.right, Int2ObjectFunction { HashSet() }).add(it)
        }
    }

    private data class Node(val strength: Int = 0, val openPort: Int = 0, val used: Long = 0, val usedCount: Int = 0) {
        fun use(component: Component): Node {
            return Node(
                this.strength + component.left + component.right,
                if (component.left == this.openPort) component.right else component.left,
                this.used or (1L shl component.id),
                this.usedCount + 1
            )
        }

        fun isUsed(component: Component): Boolean {
            return (this.used shr component.id) and 1L == 1L
        }
    }

    private data class Component(val id: Int, val left: Int, val right: Int)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day24().run()
        }
    }
}