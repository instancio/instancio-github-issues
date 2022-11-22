package org.example;

import lombok.Value;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class Person {
    String firstName;
    String fullName;
    List<String> hobbies;
    LocalDateTime lastModified;
    LocalDateTime createdOn;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}