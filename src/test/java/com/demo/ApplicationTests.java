package com.demo;

import com.demo.utils.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private RedisUtil redisUtil;

//	@Test
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
