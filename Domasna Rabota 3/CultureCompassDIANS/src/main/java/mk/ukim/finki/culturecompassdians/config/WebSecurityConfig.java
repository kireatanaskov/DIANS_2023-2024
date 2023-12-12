package mk.ukim.finki.culturecompassdians.config;

import mk.ukim.finki.culturecompassdians.config.CustomUsernamePasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthProvider customProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomUsernamePasswordAuthProvider customProvider) {
        this.passwordEncoder = passwordEncoder;
        this.customProvider = customProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().permitAll()

                ).formLogin(
                        form -> form
                                .loginPage("/login").permitAll()
                                .failureUrl("/login?error=BadCredentials")
                                .defaultSuccessUrl("/node/all", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/node/all")
                                .permitAll()
                );
        return http.build();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customProvider);
    }
}
