package dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ContactsDto {
    private List<ContactDto> contacts;
}
