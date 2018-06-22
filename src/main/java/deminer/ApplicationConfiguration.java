package deminer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import deminer.application.Game;

@Configuration
public class ApplicationConfiguration 
{
    @Bean
    @Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Game game()
    {
        return new Game();
    }
}
