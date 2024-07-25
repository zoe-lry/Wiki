package com.zoe.wiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ComponentScan({"com.zoe", "com.test"}) //way of scan more packages (won't use here)
@ComponentScan("com.zoe")  //用来扫描整个com.zoe下面的文件， 因为👇
@SpringBootApplication		//SpringBootApplication自带的扫描只扫描同一个子文件里面的文件
@MapperScan("com.zoe.wiki.mapper") //让整个项目知道有持久层
@EnableScheduling
@EnableAsync
public class WikiApplication {
	private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);
	public static void main(String[] args) {


		SpringApplication app = new SpringApplication(WikiApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("Succeed！！");
		LOG.info("Address: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}
}


