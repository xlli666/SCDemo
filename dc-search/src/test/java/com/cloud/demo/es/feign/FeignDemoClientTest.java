package com.cloud.demo.es.feign;

import com.cloud.demo.es.DcSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DcSearchService.class)
public class FeignDemoClientTest {
    @Autowired
    private FeignDemoClient feignDemoClient;

    @Test
    public void testQuery(){
        List<String> testStr = feignDemoClient.queryList();
        for (String outStr:testStr) {
            System.out.println(outStr);
        }
    }
}