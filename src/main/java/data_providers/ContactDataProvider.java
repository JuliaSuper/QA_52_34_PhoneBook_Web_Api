package data_providers;

import dto.ContactDto;
import dto.UserLombok;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactDataProvider {
    @DataProvider
    public Iterator<ContactDto>
    dataProviderWrongContact() {
        List<ContactDto> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader
                (new FileReader("src/test/resources" +
                        "/Wrong_contact.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] sprintLine = line.split(",", -1);
                list.add(ContactDto.builder()
                        .name(sprintLine[0])
                        .lastName(sprintLine[1])
                                .email(sprintLine[2])
                                .phone(sprintLine[3])
                                .address(sprintLine[4])
                                .description(sprintLine[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("created exception");
        }
        return list.iterator();
    }
}
