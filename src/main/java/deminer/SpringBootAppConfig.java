package deminer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringBootAppConfig extends WebMvcConfigurerAdapter
{
    // To be able to use static resources such as: CSS, JS, IMG
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) 
    {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    } 
}
