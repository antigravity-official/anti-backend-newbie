package antigravity.config;

import antigravity.web.argumentresolver.SearchOptionArgumentResolver;
import antigravity.web.argumentresolver.UserInfoArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInfoArgumentResolver userInfoArgumentResolver;
    private final SearchOptionArgumentResolver searchOptionArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoArgumentResolver);
        resolvers.add(searchOptionArgumentResolver);
    }
}
