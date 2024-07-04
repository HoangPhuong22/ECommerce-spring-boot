package vn.zerocoder.Mart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests(configurer -> configurer
                 .requestMatchers("/**").permitAll()
                 .requestMatchers("/admin").authenticated()
         ).formLogin(form -> form
                         .loginPage("/login")
                         .loginProcessingUrl("/authenticate")
                         .permitAll()
         );

        return http.build();
    }
}
