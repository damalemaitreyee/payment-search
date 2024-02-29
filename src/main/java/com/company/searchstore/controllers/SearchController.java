package com.company.searchstore.controllers;

import com.company.searchstore.dto.PaymentSearchRequest;
import com.company.searchstore.dto.PaymentSearchResponse;
import com.company.searchstore.dto.Property;
import com.company.searchstore.services.SearchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"api/payments"})
@Slf4j
@AllArgsConstructor
public class SearchController {

    private final SearchService service;

    @PostMapping
    public ResponseEntity<PaymentSearchResponse> searchPayments(@RequestBody @Valid PaymentSearchRequest searchRequest,
            @RequestParam("limit") Long limit, @RequestParam("offset") Long offset) {
        PaymentSearchResponse response = service.searchPayments(searchRequest, limit, offset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-fields")
    public ResponseEntity<List<String>> getSearchFields() {
        List<String> response = new ArrayList<>();
        for (Property p: Property.values()) {
            response.add(p.name());
        };
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
