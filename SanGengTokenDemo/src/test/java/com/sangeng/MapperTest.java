package com.sangeng;

import com.sangeng.domain.User;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void TestBCryptPasswordEncoder() {

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /*String encode1 = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode1);
        System.out.println(encode2);*/
        //测试校验密码
        System.out.println(passwordEncoder.
                matches("1234",
                        "$2a$10$k4rzSzQ6tNk87GVr68.gKuF6DRpwklXGex7ntEy/FOyhh2rtDv6Pu"));
    }

    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(2L);
        System.out.println(list);
    }

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}
