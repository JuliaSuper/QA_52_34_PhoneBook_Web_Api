package utils;

import dto.ContactDto;
import net.datafaker.Faker;

public class ContactFactory {
    static Faker faker = new Faker();
    public static ContactDto positiveContact(){
        return ContactDto.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.number().digits(10))
                .address(faker.address().fullAddress())
                .description("My family")
                .build();
    }
}
