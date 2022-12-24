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

package me.sizableshrimp.adventofcode2017.days;

import me.sizableshrimp.adventofcode2017.templates.SeparatedDay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day13 extends SeparatedDay {
    private List<Layer> layers;

    public static void main(String[] args) {
        new Day13().run();
    }

    @Override
    protected Object part1() {
        int sum = 0;

        for (Layer layer : this.layers) {
            if (layer.depthMod == 0)
                sum += layer.depth * layer.range;
        }

        return sum;
    }

    @Override
    protected Object part2() {
        int num = 1;
        int step = 1;

        for (Layer layer : this.layers) {
            // If the modulo is exactly double our step size, we can halve our search space.
            // (The modulo must be a power of two because our starting step size is 1.)
            // (This also requires the layers to be sorted based on the ascending order of the modulos.)
            // We can halve our search space because every other number using our current
            // step size and starting number will be caught.
            // This means we can double the step size to skip these known caught numbers.
            // Then, we move the starting number if it was part of the half that would
            // have been caught by the old step size to get it back in the half that is not caught.
            // This provides around a 4x speedup.
            if (layer.modulo / step == 2) {
                int oldStep = step;
                step = layer.modulo;
                if (layer.caught(num))
                    num += oldStep;
            }
        }

        while (true) {
            boolean success = true;
            for (Layer layer : this.layers) {
                if (layer.caught(num)) {
                    success = false;
                    break;
                }
            }
            if (success)
                break;
            num += step;
        }

        return num;
    }

    @Override
    protected void parse() {
        this.layers = new ArrayList<>(this.lines.size());

        for (String line : this.lines) {
            int colonIdx = line.indexOf(':');
            this.layers.add(new Layer(Integer.parseInt(line.substring(0, colonIdx)), Integer.parseInt(line.substring(colonIdx + 2))));
        }

        this.layers.sort(Comparator.comparingInt(l -> l.modulo));
    }

    private static class Layer {
        private final int depth;
        private final int range;
        private final int modulo;
        private final int depthMod;

        public Layer(int depth, int range) {
            this.depth = depth;
            this.range = range;
            this.modulo = (this.range - 1) * 2;
            this.depthMod = depth % this.modulo;
        }

        private boolean caught(int offset) {
            return (this.depthMod + offset) % this.modulo == 0;
        }
    }
}
