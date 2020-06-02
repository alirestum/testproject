package hu.restumali.testProject.config;

import hu.restumali.testProject.model.UserRoleType;
import hu.restumali.testProject.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring security configuration class. Creates instances of custom implementations, sets url patterns according to the
 * specification.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(getAuthFilter())
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/admin/**").hasRole(UserRoleType.Administrator.name())
                .antMatchers("/user/**").hasAnyRole(UserRoleType.LoggedInUser.name(), UserRoleType.Administrator.name())
                .antMatchers("/contentmanager/**").hasAnyRole(UserRoleType.ContentManager.name(), UserRoleType.Administrator.name())
                .antMatchers("/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied")
                .and()
                .formLogin().loginPage("/user/login").failureHandler(getAuthFailureHandler()).successHandler(getAuthSuccessHandler())
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(getLogoutSuccessHandler())
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler getAuthSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler getAuthFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    LogoutSuccessHandler getLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    CustomAuthenticationFilter getAuthFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(getAuthFailureHandler());
        filter.setAuthenticationSuccessHandler(getAuthSuccessHandler());
        return filter;
    }
}
