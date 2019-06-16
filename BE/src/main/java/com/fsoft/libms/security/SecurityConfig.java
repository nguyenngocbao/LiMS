package com.fsoft.libms.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fsoft.libms.security.handler.CustomAccessDeniedHandler;
import com.fsoft.libms.security.handler.RestAuthenticationSuccessHandler;
import com.fsoft.libms.security.token.JWTAuthenticationFilter;

/**
 * 
 * The main purpose of WebSecurityConfig to configure spring security
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JWTAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailService;

    //    private final TokenProvider tokenProvider;

    public SecurityConfig() {
	super();
	SecurityContextHolder.setStrategyName( SecurityContextHolder.MODE_INHERITABLETHREADLOCAL );
    }

    /**
     * <li>search user</li>
     * <li>search roles for user would return the single granted authority The
     * role prefix that will be prepended to each role name (defaults to
     * "ROLE_").</li>
     */
    @Override
    @Autowired
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
	auth.userDetailsService( userDetailService ).passwordEncoder( passwordEncoder() );
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
	// @formatter:off
	http
		// if Spring MVC is on classpath and no CorsConfigurationSource is provided,
		// Spring Security will use CORS configuration provided to Spring MVC
		// refer: https://docs.spring.io/spring-security/site/docs/current/reference/html/cors.html
		// since there is a bug with login
	.cors()
	.and()
        		.csrf().disable()
        		.exceptionHandling()
        		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
			.authorizeRequests().anyRequest().authenticated()
//			.and()
//			.apply( securityConfigurerAdapter() )
		.and()
			.formLogin()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler())
		.and()
			.logout()
			.deleteCookies("JSESSIONID")
		.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
//		.and().
//			// let un-authen user can create account
//			authorizeRequests().antMatchers("/user/create").permitAll();
	// @formatter:on
	http.addFilterBefore( tokenAuthenticationFilter, BasicAuthenticationFilter.class );

    }

    /**
     * Create bean to process CORS <br/>
     * Refer
     * {@link https:docs.spring.io/spring-security/site/docs/current/reference/html/cors.html}
     * 
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	configuration.setAllowCredentials( true );
	configuration.addAllowedOrigin( "*" );
	configuration.addAllowedHeader( "*" );
	configuration.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "DELETE" ) );
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration( "/**", configuration );
	return source;
    }

    @Override
    public void configure( WebSecurity web ) throws Exception {
	//let unauthenticated user can create account
	web.ignoring().antMatchers( "/api/user/create", "/api/user/detail/**", "/api/upload/**", "/api/user/list" );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
}