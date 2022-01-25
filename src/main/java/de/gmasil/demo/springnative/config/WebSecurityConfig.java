package de.gmasil.demo.springnative.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.nativex.hint.AotProxyHint;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.gmasil.demo.springnative.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AotProxyHint(targetClass = UserService.class, targetClassName = "de.gmasil.demo.springnative.service.UserService")
@AotProxyHint(targetClass = DaoAuthenticationProvider.class, targetClassName = "org.springframework.security.authentication.dao.DaoAuthenticationProvider")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests() //
                .antMatchers("/secure").authenticated() //
                .anyRequest().permitAll();

        http.formLogin().loginPage("/login.html").failureUrl("/login?error") //
                .loginProcessingUrl("/performlogin") //
                .usernameParameter("username") //
                .passwordParameter("password");
        http.logout() //
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
                .logoutSuccessUrl("/login.html?logout") //
                .deleteCookies("JSESSIONID", "rememberme");
        http.rememberMe() //
                .key("uniqueAndSecret") //
                .userDetailsService(userService) //
                .rememberMeParameter("rememberme");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
