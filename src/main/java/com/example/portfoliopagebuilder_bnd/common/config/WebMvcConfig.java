package com.example.portfoliopagebuilder_bnd.common.config;

import com.example.portfoliopagebuilder_bnd.aop.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private SecurityInterceptor interceptor;


	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.excludePathPatterns(
						"/",
						"/login", // 로그인
						"/swagger-ui.html**", //swagger
						"/webjars/**",
						"/swagger-resources",
						"/swagger-resources/configuration/security",
						"/swagger-resources/configuration/ui",
						"/v2/api-docs",
						"/configuration/**",
						"/resource/**",
						"/favicon.ico"
						);
	}

//	@Bean(name = "filterMultipartResolver")
//	public CommonsMultipartResolver createMultipartResolver() {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding("utf-8");
//		return resolver;
//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.exposedHeaders("content-type","sessionkey", "refreshkey") // 응답시 허용할 response header
				.maxAge(3600);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");

		registry.viewResolver(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/","classpath:/other-resources/");
	}

}
