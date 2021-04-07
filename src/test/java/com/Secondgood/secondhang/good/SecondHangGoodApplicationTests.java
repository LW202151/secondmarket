package com.Secondgood.secondhang.good;

import com.Secondgood.secondhang.good.service.CarService;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import com.Secondgood.secondhang.good.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondHangGoodApplicationTests {

	@Autowired
	private SeegoodsService seegoodsService;

	private static void L(Object p) {
		System.out.println(p);
	}


	@Test
	public void contextLoads() {


	}

	@Test
	public void test() {
		List<String> list = new ArrayList<>();

		list.add(null);

		L(list);
	}

}
