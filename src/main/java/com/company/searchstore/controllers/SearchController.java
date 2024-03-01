package com.company.searchstore.controllers;

import com.company.searchstore.dto.PaymentSearchRequest;
import com.company.searchstore.dto.PaymentSearchResponse;
import com.company.searchstore.dto.Property;
import com.company.searchstore.services.SearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
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
            @RequestParam("limit") Long limit, @RequestParam("offset") Long offset) throws IOException {
        PaymentSearchResponse response = service.searchPayments(searchRequest, limit, offset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/export", produces = "text/csv")
    public ResponseEntity<String> searchAndExportPayments(HttpServletResponse response,
            @RequestBody @Valid PaymentSearchRequest searchRequest,
            @RequestParam("limit") Long limit, @RequestParam("offset") Long offset) throws IOException {
        PaymentSearchResponse payments = service.searchPayments(searchRequest, limit, offset);

        String jsonString = null;

        jsonString = new ObjectMapper().writeValueAsString(payments);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray docs = jsonObject.getJSONArray("payments");
        String csvString = CDL.toString(docs);

        return ResponseEntity.ok()
                .headers(exportCsvResponseHeaders())
                .body(csvString);
    }

    private HttpHeaders exportCsvResponseHeaders() {
        val headers = new HttpHeaders();
        headers.setAccessControlExposeHeaders(Collections.singletonList(HttpHeaders.CONTENT_DISPOSITION));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        return headers;
    }

    @GetMapping("/search-fields")
    public ResponseEntity<List<String>> getSearchFields() {
        List<String> response = new ArrayList<>();
        for (Property p : Property.values()) {
            response.add(p.name());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
