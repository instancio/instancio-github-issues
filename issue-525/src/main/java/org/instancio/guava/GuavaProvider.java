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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;
import org.instancio.Node;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorContext;
import org.instancio.generators.Generators;
import org.instancio.internal.generator.util.CollectionGenerator;
import org.instancio.internal.generator.util.MapGenerator;
import org.instancio.spi.InstancioServiceProvider;
import org.instancio.spi.ServiceProviderContext;
import org.instancio.support.DefaultRandom;

import java.util.HashMap;
import java.util.Map;

public class GuavaProvider implements InstancioServiceProvider {

    private GeneratorContext generatorContext;

    @Override
    public void init(final ServiceProviderContext providerContext) {
        this.generatorContext = new GeneratorContext(
                providerContext.getSettings(),
                new DefaultRandom());
    }

    @Override
    public GeneratorProvider getGeneratorProvider() {
        final Map<Class<?>, Generator<?>> map = new HashMap<>();
        map.put(Table.class, new GuavaTableGenerator<>(generatorContext));
        map.put(ListMultimap.class, new GuavaListMultimapGenerator<>(generatorContext));
        map.put(ImmutableListMultimap.class, new GuavaListMultimapGenerator<>(generatorContext));
        map.put(ImmutableMap.class, new MapGenerator<>(generatorContext));
        map.put(ImmutableList.class, new CollectionGenerator<>(generatorContext));

        return (Node node, Generators gen) -> map.get(node.getTargetClass());
    }
}
