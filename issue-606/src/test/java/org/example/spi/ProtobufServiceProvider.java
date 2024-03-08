package org.example.spi;

import com.google.protobuf.ByteString;
import org.instancio.Node;
import org.instancio.Random;
import org.instancio.generator.AfterGenerate;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorSpec;
import org.instancio.generator.Hints;
import org.instancio.generators.Generators;
import org.instancio.settings.Keys;
import org.instancio.spi.InstancioServiceProvider;

public class ProtobufServiceProvider implements InstancioServiceProvider {

    @Override
    public GeneratorProvider getGeneratorProvider() {
        return new ProtobufGeneratorProviderImpl();
    }

    private static class ProtobufGeneratorProviderImpl implements GeneratorProvider {
        @Override
        public GeneratorSpec<?> getGenerator(final Node node, final Generators generators) {
            if (node.getTargetClass().isAssignableFrom(ByteString.class)) {
                return new ByteStringGenerator();
            }

            return null;
        }
    }

    public static class ByteStringGenerator implements Generator<ByteString> {
        @Override
        public ByteString generate(final Random random) {
            final int length = random.intRange(
                    Keys.STRING_MIN_LENGTH.defaultValue(),
                    Keys.STRING_MAX_LENGTH.defaultValue());

            return ByteString.copyFromUtf8(random.upperCaseAlphabetic(length));
        }

        @Override
        public Hints hints() {
            return Hints.afterGenerate(AfterGenerate.DO_NOT_MODIFY);
        }
    }
}
