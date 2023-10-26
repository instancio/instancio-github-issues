package org.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class SampleTest {

    static class A<T> {
        private T name;

        T getName() {
            return name;
        }
    }

    static class B extends A<String> {}

    static class C extends B {}

    @Test
    void shouldPopulateC() {
        C result = Instancio.create(C.class);

        assertThat(result.getName()).isNotBlank();
    }
}
