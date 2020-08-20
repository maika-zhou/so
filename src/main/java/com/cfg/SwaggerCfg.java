package com.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerCfg
{

	@Bean
	public Docket getDocket() 
	{ 
		// 此类主要是整个的Swagger配置项，利用这个类需要来指派扫描包
		return new Docket(DocumentationType.SWAGGER_2)
									.apiInfo(this.getApiInfo()).select()
									.apis(RequestHandlerSelectors.basePackage("com.api"))
									.paths(PathSelectors.any()).build(); // 设置文档的显示类型
	}
	private ApiInfo getApiInfo() 
	{
		return new ApiInfoBuilder().title(" Stack Overflow ")
									.description("Stack Overflow API Reference")
									.termsOfServiceUrl("http://www.stackoverflow.com")
									.version("1.0").build();
	}

	
	
	
}
