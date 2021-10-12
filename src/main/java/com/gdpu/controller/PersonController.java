package com.gdpu.controller;


import com.gdpu.entity.Person;
import com.gdpu.service.PersonService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("getAll")
    public R getAll(){
        System.out.println("进入getAll");
        List<Person> personList = personService.list();
        return R.ok().data("list",personList);
    }

    @GetMapping("findPersonById/{id}/{username}")
    public R findPersonById(@PathVariable String id,@PathVariable String username){
        System.out.println("进入findPersonById");
        Person person = personService.getById(id);
        if(person==null){
            return R.error().message("登录失败");
        }else{
            return R.ok().success(true).message("登录成功");
        }
    }

}

