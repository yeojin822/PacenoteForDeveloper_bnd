package com.example.portfoliopagebuilder_bnd.common.config;

import com.example.portfoliopagebuilder_bnd.aop.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private SecurityInterceptor interceptor;


	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/")
				.excludePathPatterns(
						"/login", // 로그인
						"/swagger-ui.html**", //swagger
						"/webjars/**",
						"/swagger-resources",
						"/swagger-resources/configuration/security",
						"/swagger-resources/configuration/ui"
						);
	}

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
}
