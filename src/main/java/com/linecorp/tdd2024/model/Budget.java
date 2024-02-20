package com.linecorp.tdd2024.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

    private String yearMonth;

    private Integer amount;

}
