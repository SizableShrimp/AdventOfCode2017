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

import me.sizableshrimp.adventofcode2017.helper.OccurrenceHelper
import me.sizableshrimp.adventofcode2017.templates.Day

class Day07 : Day() {
    override fun evaluate(): Result {
        val nodes = HashMap<String, Node>()
        val getNode: (String) -> Node = { id -> nodes.computeIfAbsent(id) { Node(it) } }

        for (line in this.lines) {
            val split = line.split(" ")
            val weight = split[1].substring(1, split[1].length - 1).toInt()
            val node = getNode(split[0]).apply { this.weight = weight }

            for (child in line.substring(line.indexOf('>') + 2).split(",")) {
                node.children.add(getNode(child.trim()).apply { this.parent = node })
            }
        }

        val root = nodes.values.find { it.parent == null }!!
        var node = root

        while (true) {
            val occurrences = OccurrenceHelper.getOccurrences(node.children.map { it.totalWeight })
            if (occurrences.size == 1) {
                return Result.of(root.id, node.weight + node.parent!!.children.find { it !== node }!!.totalWeight - node.totalWeight)
            }

            node = node.children.find { n -> n.totalWeight == OccurrenceHelper.getLeastOccurring(occurrences) }!!
        }
    }

    data class Node(val id: String, var weight: Int = 0, val children: MutableList<Node> = ArrayList(), var parent: Node? = null) {
        // Doing it this way prevents Integer boxing
        private var totalWeightInternal: Int = -1
        val totalWeight: Int
            get() {
                if (this.totalWeightInternal == -1)
                    this.totalWeightInternal = this.weight + this.children.sumOf(Node::totalWeight)

                return this.totalWeightInternal
            }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day07().run()
        }
    }
}