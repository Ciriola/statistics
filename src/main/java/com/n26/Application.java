package com.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Swagger for REST endpoints.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.n26.transaction"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo())
                .useDefaultResponseMessages(false)
                .directModelSubstitute(ResponseEntity.class, HttpStatus.class);

    }

    @SuppressWarnings("deprecation")
    private ApiInfo generateApiInfo() {
        return new ApiInfo("N26 TEST",
                "N26 test", "1.0",
                "", "", "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0");
    }
}
