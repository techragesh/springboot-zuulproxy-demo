package com.techragesh.dbservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Quotes {

    private String userName;
    private List<String> quotes;
}
