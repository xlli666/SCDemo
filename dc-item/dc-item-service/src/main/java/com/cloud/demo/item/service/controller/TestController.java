package com.cloud.demo.item.service.controller;

import com.cloud.demo.common.LayUIData;
import com.cloud.demo.item.pojo.UserDomain;
import com.cloud.demo.item.pojo.UserRequestParam;
import com.cloud.demo.item.service.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TestController {

    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("hello")
    public ResponseEntity<String> test(){
        System.out.println("-----------------");
        return ResponseEntity.ok("hello");
    }

    @RequestMapping("layUI")
    public ResponseEntity<LayUIData> queryInfo(@RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
                                               @RequestParam(name = "limit", required = false, defaultValue = "1") int pageSize,
                                               UserRequestParam userParam) {
        System.out.println(pageNum + "#" + pageSize + "#" + userParam.getNumberParam() + "-----" + userParam.getTelParam());
        PageInfo<UserDomain> helperResult = userService.findUsersBy(pageNum,pageSize,userParam);
        System.out.println(helperResult.getTotal()+":::"+helperResult.getSize()+":::"+helperResult.getList().size());
        return ResponseEntity.ok(LayUIData.tableResult(helperResult.getTotal(), helperResult.getList()));
    }
}
