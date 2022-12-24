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

class Day13 : SeparatedDay() {
    private lateinit var layers: List<Layer>

    override fun part1() = this.layers.sumOf { if (it.depthMod == 0) it.depth * it.range else 0 }

    override fun part2(): Any {
        var num = 1
        var step = 1

        this.layers.forEach {
            // If the modulo is exactly double our step size, we can halve our search space.
            // (The modulo must be a power of two because our starting step size is 1.)
            // (This also requires the layers to be sorted based on the ascending order of the modulos.)
            // We can halve our search space because every other number using our current
            // step size and starting number will be caught.
            // This means we can double the step size to skip these known caught numbers.
            // Then, we move the starting number if it was part of the half that would
            // have been caught by the old step size to get it back in the half that is not caught.
            // This provides around a 4x speedup.
            if (it.modulo / step == 2) {
                val oldStep = step
                step = it.modulo
                if (it.caught(num))
                    num += oldStep
            }
        }

        while (true) {
            val success = this.layers.none { it.caught(num) }
            if (success)
                break
            num += step
        }

        return num
    }

    override fun parse() {
        this.layers = this.lines.map {
            val colonIdx = it.indexOf(':')
            Layer(it.substring(0, colonIdx).toInt(), it.substring(colonIdx + 2).toInt())
        }.sortedWith(Comparator.comparingInt { it.modulo })
    }

    private data class Layer(val depth: Int, val range: Int, val modulo: Int = (range - 1) * 2, val depthMod: Int = depth % modulo) {
        fun caught(offset: Int): Boolean {
            return (this.depthMod + offset) % this.modulo == 0
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day13().run()
        }
    }
}