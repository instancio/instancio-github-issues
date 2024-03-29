/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instancio.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.instancio.Random;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorContext;
import org.instancio.generator.Hints;
import org.instancio.internal.generator.InternalContainerHint;
import org.instancio.internal.util.Constants;

public class GuavaListMultimapGenerator<K, V> implements Generator<ListMultimap<K, V>> {

    private final GeneratorContext context;

    public GuavaListMultimapGenerator(final GeneratorContext context) {
        this.context = context;
    }

    @Override
    public ListMultimap<K, V> generate(final Random random) {
        return ArrayListMultimap.create();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Hints hints() {
        final int generateEntries = context.random().intRange(Constants.MIN_SIZE, Constants.MAX_SIZE);
        return Hints.builder()
                .with(InternalContainerHint.builder()
                        .generateEntries(generateEntries)
                        .addFunction((ListMultimap<K, V> lm, Object... args) ->
                                lm.put(
                                        (K) args[0],
                                        (V) args[1]))
                        .build())
                .build();
    }
}
