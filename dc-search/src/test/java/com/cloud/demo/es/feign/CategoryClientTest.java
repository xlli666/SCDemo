package com.cloud.demo.es.feign;

import com.cloud.demo.es.DcSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DcSearchService.class)
public class CategoryClientTest {
    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategories() {
        List<String> categoryList =
                this.categoryClient.queryCategoryNamesByIds(Arrays.asList(1L, 2L, 3L));
        for (String category : categoryList) {
            System.out.println("category = " + category);
        }
    }
}
