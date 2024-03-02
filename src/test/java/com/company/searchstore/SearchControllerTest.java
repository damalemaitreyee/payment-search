package com.company.searchstore;

import com.company.searchstore.controllers.SearchController;
import com.company.searchstore.dto.Property;
import com.company.searchstore.services.SearchService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    @Mock
    private SearchService searchService;

    @Test
    public void testSearchField() {
        List<String> fields = searchController.getSearchFields().getBody();
        Assert.assertNotNull(fields);
        val thislist = Stream.of(Property.values()).map(Objects::toString).collect(Collectors.toList());
        Assert.assertTrue(fields.containsAll(thislist));
    }
}
