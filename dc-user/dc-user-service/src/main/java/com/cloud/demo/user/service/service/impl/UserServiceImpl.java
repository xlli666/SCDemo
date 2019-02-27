package com.cloud.demo.user.service.service.impl;

import com.cloud.demo.common.utils.NumberUtils;
import com.cloud.demo.user.pojo.User;
import com.cloud.demo.user.service.dao.UserDao;
import com.cloud.demo.user.service.service.UserService;
import com.cloud.demo.user.util.CodecUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final StringRedisTemplate redisTemplate;

    private final AmqpTemplate amqpTemplate;

    private static final String KEY_PREFIX = "user:code:phone:";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserServiceImpl(UserDao userDao, StringRedisTemplate redisTemplate, AmqpTemplate amqpTemplate) {
        this.userDao = userDao;
        this.redisTemplate = redisTemplate;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1:
                user.setName(data);
                break;
            case 2:
                user.setTel(data);
                break;
            default:
                return null;
        }
        return userDao.selectCount(user) == 0;
    }

    @Override
    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        System.out.println(code);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            //amqpTemplate.convertAndSend("dc.sms.exchange", "sms.verify.code", msg);
            // 将code存入redis
            redisTemplate.opsForValue().set(KEY_PREFIX+phone,code,5,TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        String key = KEY_PREFIX + user.getTel();
        // 从redis取出验证码
        String codeCache = redisTemplate.opsForValue().get(key);
        // 检查验证码是否正确
        if (!code.equals(codeCache)) {
            // 不正确，返回
            return false;
        }
        user.setId(null);
        user.setRoleId("000");
        user.setCreateTime(new Date());
        String salt = CodecUtil.generateSalt();
        user.setNote(salt);
        user.setPwd(CodecUtil.md5Hex(user.getPwd(), salt));
        // 写入数据库
        boolean boo = userDao.insert(user) == 1;
        // 如果注册成功，删除redis中的code
        if (boo) {
            try {
                redisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return boo;
    }

    @Override
    public User queryUser(String username, String password) {
        // 查询
        User record = new User();
        record.setName(username);
        User user = userDao.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPwd().equals(CodecUtil.md5Hex(password, user.getNote()))) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }
}
