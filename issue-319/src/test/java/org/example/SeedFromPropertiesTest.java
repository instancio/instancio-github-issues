package org.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class SeedFromPropertiesTest {

    @Test
    @DisplayName("Should use seed from instancio.properties")
    void globalSeed() {
        String foo = Instancio.create(String.class);

        assertThat(foo).isEqualTo("OMTTHYHV");
        System.out.println(foo);
    }

}
