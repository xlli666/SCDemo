package com.cloud.demo.user.service.controller;

import com.cloud.demo.common.LayUIData;
import com.cloud.demo.user.pojo.User;
import com.cloud.demo.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("check/{data}/{type}")
    public ResponseEntity<LayUIData> checkUserData(@PathVariable("data") String data,
                                                 @PathVariable("type") Integer type){
        Boolean boo = userService.checkData(data, type);
        if (boo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(LayUIData.commResult(String.valueOf(boo)));
        //return ResponseEntity.ok(boo);
    }

    @RequestMapping("code")
    public ResponseEntity<LayUIData> sendVerifyCode(@RequestParam("tel") String phone) {
        Boolean boo = userService.sendVerifyCode(phone);
        if (boo==null || !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(LayUIData.commResult(String.valueOf(boo)));
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("register")
    public ResponseEntity<LayUIData> register(@Valid User user, @RequestParam("code") String code) {
        Boolean boo = userService.register(user, code);
        if (boo==null || !boo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(LayUIData.commResult(String.valueOf(boo)));
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("query")
    public ResponseEntity<User> queryUser(String username, String password){
        User user = userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
