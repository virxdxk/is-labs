package ru.itmo.se.is.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonLazyResponseDto implements Serializable {
    List<PersonResponseDto> data;
    Long totalRecords;
}
