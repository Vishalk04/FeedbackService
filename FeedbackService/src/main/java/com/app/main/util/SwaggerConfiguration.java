package com.app.main.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 
public class SwaggerConfiguration {   
	@Bean
	public Docket produceApi(){
		return new Docket(DocumentationType.SWAGGER_2) 
				.apiInfo(apiInfo()) 
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.app.main.controller"))
				.paths(paths())
				.build(); 
	}    
 
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()  
				.title("Feedback Rest APIs")
				.description("FeedbackService is REST web service application that stores and retrieves the feedback to user with rating, user name(RACF ID) and source(Application Name) details.\r\n" + 
						"It is developed with Spring Boot(2.1.0) connecting to Postgres database(9.6).\r\n" + 
						"\r\n" + 
						"And below is the list of resources\r\n" + 
						"")
				.version("1.0-SNAPSHOT")
				.build();
	}

	private Predicate<String> paths() { 
		return Predicates.and(
				PathSelectors.regex("/feedback.*"),
				Predicates.not(PathSelectors.regex("/error.*")) 
				);
	}
}
 