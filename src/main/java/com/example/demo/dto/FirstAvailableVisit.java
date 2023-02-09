package com.example.demo.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class FirstAvailableVisit {

    LocalDateTime localDateTime;
    List<VetName> vetName;
}
