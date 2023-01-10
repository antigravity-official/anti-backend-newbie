package antigravity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import antigravity.util.HeaderInterceptor;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HeaderInterceptor())
			.addPathPatterns("/products/*");
	}

}
