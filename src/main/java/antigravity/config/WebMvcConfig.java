package antigravity.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import antigravity.common.resolver.UserIdArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final UserIdArgumentResolver userIdArgumentResolver;

	public WebMvcConfig(UserIdArgumentResolver userIdArgumentResolver) {
		this.userIdArgumentResolver = userIdArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userIdArgumentResolver);
	}
}
