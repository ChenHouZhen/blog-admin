package me.chenhz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
