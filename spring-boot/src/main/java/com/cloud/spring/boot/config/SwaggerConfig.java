/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.spring.boot.config;

import com.cloud.spring.boot.common.util.Constants;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Quy Duong
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/api").setViewName(
                        "forward:/swagger-ui.html");
            }
        };
    }

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cloud.spring.boot"))
                .paths(apiPaths())
                .build()
                .globalOperationParameters(apiHeader());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Restful API")
                .description("Documents with Swagger 2")
                .termsOfServiceUrl("https://www.689cloud.com")
                .contact("http://springfox.io")
                .license("")
                .licenseUrl("")
                .version("1.0")
                .build();
    }

    private Predicate<String> apiPaths() {
        return Predicates.or(
                regex("/manage.*"),
                regex("/api.*"));
    }
    
    private List<Parameter> apiHeader (){
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name(Constants.HEADER_TOKEN)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build());
        
        return parameters;
    }
}
