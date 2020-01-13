package cn.lilq.baiduai.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "cn.lilq.baiduai.controller")
public class Swagger2Configuration {



    private boolean swaggerShow=true;

    /**
     * 设置swagger跨域，提供给service调用
     */

    @Bean
    public WebMvcConfigurer crosConfigurer() {
        return new WebMvcConfigurerAdapter() {
            public void addCrosMapping(CorsRegistry registry) {
//                registry.addMapping("/v2/api-docs");
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("PUT", "DELETE", "GET", "POST");
            }
        };
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerShow)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.vizhuo"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用Swagger2构建RESTful APIs")
                .description("相关文章请关注：https://github.com/swagger-api/swagger-ui")
                .termsOfServiceUrl("http://localhost:8503/swagger-ui.html")
                .contact("maq")
                .version("1.0")
                .build();
    }

}