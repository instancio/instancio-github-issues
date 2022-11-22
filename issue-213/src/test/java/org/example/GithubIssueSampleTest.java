package org.example;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.Seed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class GithubIssueSampleTest {

    @Test
    @DisplayName("Create Foo from model")
    void createFooFromModel() {
        Model<Foo> fooModel = Factory.commonModel(Foo.class);
        Foo foo = Instancio.create(fooModel);

        assertThat(foo.getLastModified()).isAfterOrEqualTo(foo.getCreatedOn());
    }

    @Test
    @DisplayName("Create Person from model")
    void createPersonFromModel() {
        Model<Person> personModel = Factory.personModel();
        Person person = Instancio.create(personModel);

        assertThat(person.getLastModified()).isAfterOrEqualTo(person.getCreatedOn());
        assertThat(person.getFullName()).startsWith(person.getFirstName() + " ");
        assertThat(person.getHobbies()).isNotEmpty();
    }

    @Test
    @DisplayName("Create a customised Person from model")
    void createCustomPersonFromModel() {
        Model<Person> personModel = Factory.personModel();

        // Customise the person before creating an instance from the model
        Person person = Instancio.of(personModel)
                .generate(field("hobbies"), gen -> gen.collection().with("music", "movies"))
                .create();

        assertThat(person.getLastModified()).isAfterOrEqualTo(person.getCreatedOn());
        assertThat(person.getFullName()).startsWith(person.getFirstName() + " ");
        assertThat(person.getHobbies()).contains("music", "movies");
    }

    /**
     * Since we have multiple 'Instancio.create()' calls in the Factory class,
     * to ensure they all use same the seed, we can set it using the annotation.
     */
    @Seed(1)
    @RepeatedTest(3)
    @DisplayName("Should generate the same person data on each test run")
    void createCustomPersonFromModelWithStaticData_usingAnnotation() {
        Person person = Instancio.of(Factory.personModel())
                .generate(field("hobbies"), gen -> gen.collection().with("travel"))
                .create();

        System.out.println(person);
    }
}
