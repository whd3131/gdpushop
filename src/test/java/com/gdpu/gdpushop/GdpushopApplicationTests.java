package com.gdpu.gdpushop;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.entity.Person;
import com.gdpu.mapper.PersonMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class GdpushopApplicationTests {

    @Autowired
    private PersonMapper personMapper;

    /* 测试全表查询 */
    @Test
    void test01() {
        List<Person> people = personMapper.selectList(null);
        people.forEach(System.out::println);
    }

    /* 测试分页查询 */
    @Test
    void test02() {
        Page<Person> page = new Page<>(1,3);
        //page.addOrder(OrderItem.asc("age"));
        Page<Person> personPage = personMapper.selectPage(page, null);
        for (Person person : personPage.getRecords()) {
            System.out.println(person);
        }
    }

    //逻辑删除测试
    @Test
    void test03() {
        int count = personMapper.deleteById(2);
        System.out.println("影响行数：" + count);
    }

    //测试创建时间自动填充

    @Test
    void test04() {
        Person person = new Person();
        person.setName("XX");
        int res = personMapper.insert(person);
        System.out.println("res = " + res);
    }
}
