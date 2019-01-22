package com.cloud.demo.es.create;

import com.cloud.demo.es.DcSearchService;
import com.cloud.demo.es.pojo.ItemESDemo;
import com.cloud.demo.es.repository.ItemESDemoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DcSearchService.class)
public class ElasticsearchApplicationTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemESDemoRepository itemESDemoRepository;

    @Test
    public void testCreate(){
        elasticsearchTemplate.createIndex(ItemESDemo.class);
        elasticsearchTemplate.putMapping(ItemESDemo.class);
    }

    @Test
    public void testDelete(){
        elasticsearchTemplate.deleteIndex("test");
    }

    @Test
    public void dataInit(){
        ItemESDemo demo = new ItemESDemo(1L,"phone mi 3","phone","mi",1999.00,"htsp.jpg");
        itemESDemoRepository.save(demo);
    }
}
