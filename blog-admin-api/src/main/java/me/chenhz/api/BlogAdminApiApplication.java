package me.chenhz.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//开启事务
@EnableTransactionManagement
// mybatis plus 扫描
@MapperScan("me.chenhz.api.mapper")
@SpringBootApplication
public class BlogAdminApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAdminApiApplication.class, args);
	}

}
