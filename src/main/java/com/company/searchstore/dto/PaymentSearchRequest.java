package com.company.searchstore.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSearchRequest {

    private List<Property> property;
    private Operator operator;
    private String searchText;
}
