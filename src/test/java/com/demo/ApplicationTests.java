package com.demo;

import com.demo.controller.UserController;
import com.demo.utils.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void redisTest(){
		String key="tudou";
		boolean flag=redisUtil.set(key,"18600198020");
		System.out.println("执行set方法："+flag);
		String mobile= (String) redisUtil.get(key);
		System.out.println("mobile:"+mobile);
		flag=redisUtil.set(key,"123123123");
		System.out.println("执行reset："+flag);
		String value= (String) redisUtil.get(key);
		System.out.println("value:"+value);
	}


}
