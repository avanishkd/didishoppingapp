package com.jv.didi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Sets;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	private final Logger log = LoggerFactory.getLogger(SpringFoxConfig.class);

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().protocols(Sets.newHashSet("http", "https"));
	}

	/*
	 * @Bean public Docket newsApi() { return new
	 * Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
	 * ())
	 * .paths(PathSelectors.any()).build().securitySchemes(Lists.newArrayList(apiKey
	 * ())).apiInfo(apiInfo());
	 * 
	 * }
	 *//*
		 * 
		 * @Bean public Docket swaggerSpringfoxDocket() { StopWatch watch = new
		 * StopWatch(); watch.start(); log.debug("Starting Swagger"); Contact contact =
		 * new Contact("Matyas Albert-Nagy", "https://justrocket.de",
		 * "matyas@justrocket.de");
		 * 
		 * List<VendorExtension> vext = new ArrayList<>(); ApiInfo apiInfo = new
		 * ApiInfo("Backend API", "This is the best stuff since sliced bread - API",
		 * "6.6.6", "https://justrocket.de", contact, "MIT", "https://justrocket.de",
		 * vext);
		 * 
		 * Docket docket = new
		 * Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).pathMapping("/")
		 * .apiInfo(ApiInfo.DEFAULT).forCodeGeneration(true).genericModelSubstitutes(
		 * ResponseEntity.class)
		 * .ignoredParameterTypes(Pageable.class).ignoredParameterTypes(java.sql.Date.
		 * class) .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
		 * .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
		 * .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
		 * .securityContexts(Lists.newArrayList(securityContext())).securitySchemes(
		 * Lists.newArrayList(apiKey())) .useDefaultResponseMessages(false);
		 * 
		 * docket =
		 * docket.select().paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
		 * watch.stop(); log.debug("Started Swagger in {} ms",
		 * watch.getTotalTimeMillis()); return docket;
		 * 
		 * }
		 * 
		 * @Bean SecurityConfiguration security() { return new
		 * SecurityConfiguration("test-app-client-id", "test-app-client-secret",
		 * "test-app-realm", "test-app", "", ApiKeyVehicle.HEADER, "Authorization", ","
		 * scope separator ); }
		 * 
		 * 
		 * SecurityScheme apiKey() { return new ApiKey("JWT", "Authorization",
		 * "header"); }
		 * 
		 * 
		 * private ApiInfo apiInfo() { return new
		 * ApiInfoBuilder().title("Spring REST Sample with Swagger")
		 * .description("Spring REST Sample with Swagger").version("2.0").build(); }
		 * 
		 * 
		 * private SecurityContext securityContext() { return
		 * SecurityContext.builder().securityReferences(defaultAuth()).forPaths(
		 * PathSelectors.regex("/api/.*")) .build(); }
		 * 
		 * List<SecurityReference> defaultAuth() { AuthorizationScope authorizationScope
		 * = new AuthorizationScope("global", "accessEverything"); AuthorizationScope[]
		 * authorizationScopes = new AuthorizationScope[1]; authorizationScopes[0] =
		 * authorizationScope; return Lists.newArrayList(new SecurityReference("JWT",
		 * authorizationScopes)); }
		 */

}
