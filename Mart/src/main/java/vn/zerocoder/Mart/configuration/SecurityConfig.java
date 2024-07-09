package vn.zerocoder.Mart.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final PasswordEncoderConfig passwordEncoderConfig;

    private String[] AuthUrl = new String[] {
            "/admin/**",
            "/favourite/**",
            "/profile/**",
            "/cart/**",
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeHttpRequests(
                         auth -> auth
                             .requestMatchers(AuthUrl).authenticated()
                             .requestMatchers("/**").permitAll()
                 )
                 .formLogin(
                         form -> form
                             .loginPage("/login")
                             .loginProcessingUrl("/login")
                             .failureHandler(failureHandler)
                             .permitAll()
                 )
                 .logout(
                         logout -> logout
                                 .logoutUrl("/logout")
                                 .logoutSuccessUrl("/login?logout=true")

                 );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }
}
