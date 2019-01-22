package com.cloud.demo.item.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feign")
public class FeignController {

    @RequestMapping("/demo")
    public ResponseEntity<List<String>> queryForFeign(){
        List<String> test = new ArrayList<>();
        test.add("aaa");
        test.add("bbb");
        test.add("ccc");
        return ResponseEntity.ok(test);
    }
}
