package org.example;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.junit.InstancioExtension;
import org.instancio.test.prototobuf.Proto;
import org.instancio.test.prototobuf.Proto.Person;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class PersonProtoTest {

    /**
     * Ignoring {@link com.google.protobuf.MapField} because generating it
     * results in an error (possible to create a custom generator for it?)
     *
     * <p>Do we need to ignore internal proto fields? e.g.
     *
     * <pre>
     *  - bitField0_
     *  - memoized.*
     *  - hash
     * </pre>
     */
    private final Model<Proto.Person> personModel = Instancio.of(Proto.Person.class)
            .ignore(field(Proto.Person.class, "attributes_"))
            .toModel();

    @RepeatedTest(10)
    void createPerson() {
        final Proto.Person person = Instancio.create(personModel);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNotBlank();
        assertThat(person.getAge()).isPositive();

        // Protobuf enums cannot be generated
        assertThat(person.getGender()).isEqualTo(Proto.Gender.UNRECOGNIZED);

        final Proto.Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isNotBlank();
        assertThat(address.getCity()).isNotBlank();
        assertThat(address.getCountry()).isNotBlank();

        assertThat(address.getPhoneNumbersList())
                .isNotEmpty()
                .allSatisfy(phone -> {
                    assertThat(phone.getCountryCode()).isNotBlank();
                    assertThat(phone.getNumber()).isNotBlank();
                });
    }

    @Test
    void customisePerson() {
        final Proto.Person person = Instancio.of(personModel)
                .set(field(Person.class, "name_"), "Homer Simpson")
                .set(field(Person.class, "gender_"), Proto.Gender.FEMALE.getNumber())
                .generate(field(Proto.Address.class, "phoneNumbers_"), gen -> gen.collection().size(3))
                .create();

        assertThat(person.getName()).isEqualTo("Homer Simpson");
        assertThat(person.getGender()).isEqualTo(Proto.Gender.FEMALE);
        assertThat(person.getAddress().getPhoneNumbersList()).hasSize(3);
    }
}
