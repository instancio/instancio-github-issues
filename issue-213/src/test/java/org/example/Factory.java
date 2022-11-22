package org.example;

import org.instancio.Instancio;
import org.instancio.Model;

import java.time.LocalDateTime;

import static org.instancio.Select.all;
import static org.instancio.Select.field;

/**
 * Constructs test object Models in a meaningful state.
 * The returned models can be customised further, if required.
 */
class Factory {

    /**
     * A model for classes that have 'lastModified' and 'createdOn' fields.
     * Note: passed in class must have these fields or an error will be thrown.
     */
    static <T> Model<T> commonModel(Class<T> klass) {
        LocalDateTime lastModified = Instancio.of(LocalDateTime.class)
                .generate(all(LocalDateTime.class), gen -> gen.temporal().localDateTime().past())
                .create();

        return Instancio.of(klass)
                .set(field("lastModified"), lastModified)
                .generate(field("createdOn"), gen -> gen.temporal().localDateTime().range(LocalDateTime.MIN, lastModified))
                .toModel();
    }

    static Model<Person> personModel() {
        String firstName = Instancio.create(String.class);

        // Use the common model as a template for the Person class
        Model<Person> personModel = commonModel(Person.class);

        // Create a Person model and customise it further.
        return Instancio.of(personModel)
                .set(field("firstName"), firstName)
                .generate(field("fullName"), gen -> gen.string().prefix(firstName + " "))
                .toModel();
    }
}
