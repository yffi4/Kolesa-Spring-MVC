package kz.kuleshov.springStart.config;

import kz.kuleshov.springStart.servises.MyUserServise;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public MyUserServise userServise(){
        return new MyUserServise();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userServise()).passwordEncoder(passwordEncoder());
        http.formLogin(formLogin -> formLogin.loginPage("/car/login").loginProcessingUrl("/sign-in").usernameParameter("user-email").passwordParameter("user-password").defaultSuccessUrl("/car/home").failureUrl("/car/login?error"));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
