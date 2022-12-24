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
import me.sizableshrimp.adventofcode2017.templates.ZCoordinate
import me.sizableshrimp.adventofcode2017.templates.ZDirection

class Day20 : SeparatedDay() {
    private lateinit var particles: List<Particle>

    override fun part1() = this.particles.minBy { it.acceleration.distanceToOrigin() }.id

    override fun part2(): Any {
        val particlesCopy = ArrayList(this.particles)
        var round = 0

        while (true) {
            round++
            for (particle in particlesCopy) {
                particle.velocity = particle.velocity.resolve(particle.acceleration)
                particle.pos = particle.pos.resolve(particle.velocity)
            }
            val dead = HashSet<ZCoordinate>()

            particlesCopy.removeIf {
                if (dead.contains(it.pos))
                    return@removeIf true

                var isDead = false
                for (otherParticle in particlesCopy) {
                    if (it === otherParticle) continue

                    if (it.pos == otherParticle.pos) {
                        dead.add(it.pos)
                        isDead = true
                        break
                    }
                }

                return@removeIf isDead
            }

            var done = true

            for (axis in ZDirection.Axis.values()) {
                val velSorted = particlesCopy.sortedWith(Comparator.comparingInt { it.velocity.getAxis(axis) })
                particlesCopy.sortWith { a, b ->
                    val aInt = a.acceleration.getAxis(axis)
                    val bInt = b.acceleration.getAxis(axis)
                    val comp = aInt.compareTo(bInt)

                    if (comp == 0) {
                        return@sortWith a.velocity.getAxis(axis).compareTo(b.velocity.getAxis(axis))
                    }

                    return@sortWith comp
                }

                if (velSorted != particlesCopy) {
                    done = false
                    break
                }
            }

            if (done)
                break
        }

        return particlesCopy.size
    }

    override fun parse() {
        this.particles = this.lines.withIndex().map { (idx, line) ->
            val (pos, velocity, acceleration) = line.split(", ").map { ZCoordinate.parse(it.substring(3, it.length - 1).trim()) }
            Particle(idx, pos, velocity, acceleration)
        }
    }

    private data class Particle(val id: Int, var pos: ZCoordinate, var velocity: ZCoordinate, val acceleration: ZCoordinate)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day20().run()
        }
    }
}