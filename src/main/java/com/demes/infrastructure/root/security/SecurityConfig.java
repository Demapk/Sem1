package com.demes.infrastructure.root.security;


import com.demes.constants.Routes;
import com.demes.entity.enums.RoleEnumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${cookie.remember_me_key}")
    private String rememberMeKey;

    @Value("${cookie.remember_me_age}")
    private int rememberMeAge;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(Routes.LOGIN_INFO_URI + "/**").permitAll()
                .antMatchers(Routes.LOGIN_NEW_PASSWORD_URI + "/**").hasRole(RoleEnumeration.TEMPORARY_ACCESS.name())
                .antMatchers(Routes.LOGIN_URI + "/**").anonymous()
                .antMatchers(Routes.REGISTRATION_URI + "/**").anonymous()
                .antMatchers(Routes.ACCOUNT_SHOW_URI+"/**").hasRole(RoleEnumeration.CUSTOMER.name())
                .antMatchers(Routes.ADMIN_URI+"/**").hasRole(RoleEnumeration.ADMIN.name())
                .and()
                .formLogin().loginPage(Routes.LOGIN_URI).usernameParameter("name")
                .passwordParameter("password").failureUrl(Routes.LOGIN_URI + "?error")
                .loginProcessingUrl(Routes.LOGIN_PROCESSING_URI)
                .defaultSuccessUrl(Routes.ROOT_URI, false)
                .and()
                .logout().logoutUrl(Routes.LOGOUT_URI).logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .httpBasic()
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService).key("uniqueAndSecret")
                .rememberMeCookieName("remember-me").rememberMeParameter("remember-me")
                .tokenValiditySeconds(rememberMeAge)
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

}
