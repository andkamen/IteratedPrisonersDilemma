package com.ipdweb.configuration;

import com.ipdweb.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BasicUserService basicUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.basicUserService).passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/register/**", "/strategies/**", "/bootstrap/**", "/jquery/**", "/connect/**", "/tether/**").permitAll()
                    .antMatchers("/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                //TODO secutiry problem doesnt redirect to exception handeler
                //http://stackoverflow.com/questions/39789408/how-to-handle-spring-security-internalauthenticationserviceexception-thrown-in-s
                //possible solution
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .rememberMe()
                    .rememberMeCookieName("RememberMe")
                    .rememberMeParameter("rememberMe")
                    .key("HashThisShit")
                    .tokenValiditySeconds(100000)
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                    .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
