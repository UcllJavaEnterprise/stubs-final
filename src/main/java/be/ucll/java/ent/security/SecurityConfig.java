package be.ucll.java.ent.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /* *** Define lists of Security FILTERS based on URL's *** */

    /* Ter info dit is de default Spring Security configuratie:
       EVERYTHING is closed Except BASIC authenticated HTTP posts (Forms)
       CSRF - Cross Site Request Forgery prevention is enabled
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }
    */

    @Configuration
    @Order(1)
    /*
      1. List 'resources' or URL's requiring no authentication or Spring Security
      2. Define Spring Security for API Rest Web Service calls => Http GET Fully open to the world
    */
    public static class ResourcesAndWebServicesSecurityConfig extends WebSecurityConfigurerAdapter {
        /*
        // FOR TESTING PURPOSES ONLY! SWITCH OFF ALL SECURITY CHECKS.
        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/**");
        }
        */

        @Override
        public void configure(WebSecurity web) {
            // BYPASS Spring Security for all these:
            web.ignoring().antMatchers(
                    // HTML files (index, swagger-ui, offline, accessdenied, ...) located at the root
                    "/*.html",
                    // For Vaadin assets.
                    "/VAADIN/**",
                    "/favicon.ico",
                    "/robots.txt",
                    "/manifest.webmanifest",
                    "/sw.js",
                    "/icons/**",
                    "/img/**",
                    "/styles/**",
                    "/frontend/**",
                    "/webjars/**",
                    // Health checks
                    "/health",
                    "/health/**",
                    // For Swagger/OpenAPI V3
                    "/v3/api-docs/**",
                    "/swagger-ui/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // Security rules for REST web services, authorize the following requests
            http.requestMatchers().antMatchers("/rest/**").and()
                    .authorizeRequests()

                    // Every HTTP Get operation on our REST web services is open to the whole world
                    .antMatchers(HttpMethod.GET, "/rest/**")
                        .permitAll()

            // Security rules for SOAP web services, authorize the following requests
            .and().requestMatchers().antMatchers("/soap/**").and()
                    .csrf().disable()
                    .authorizeRequests()

                    // Only the HTTP Get operation for WSDL files is open to the whole world
                    .antMatchers(HttpMethod.GET, "/soap/*.wsdl")
                        .permitAll()

                    // For other SOAP calls a BASIC authenticated user is required
                    .antMatchers("/soap/v1/**")
                        .authenticated()
                        .and()
                        .httpBasic();
        }
    }

    @Configuration
    /*
      Default order = Last
      Specify Spring Security for the remainder, mainly the User Interface => Form based login (Vaadin compatible)
    */
    public static class UIWebSecurityConfig extends WebSecurityConfigurerAdapter {

        private static final String LOGIN_URL = "/login";
        private static final String LOGIN_FAILURE_URL = "/login?error";
        private static final String LOGOUT_SUCCESS_URL = "/login";

        private static final String HOME_URL = "/main";

        /**
         * Method to block unauthenticated HTTP requests to all pages, except the login & register page
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // Security rules for Vaadin UI
            http.csrf().disable() // The CSRF token is created/used/handled by Vaadin so disable it in Spring Security
                .authorizeRequests()
                .requestMatchers(SecurityUtils::isVaadinFrameworkInternalRequest).permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                        .loginPage(LOGIN_URL).permitAll()
                        .loginProcessingUrl(LOGIN_URL)
                        .failureUrl(LOGIN_FAILURE_URL)
                        .successForwardUrl(HOME_URL)
                    .and()
                    .logout()
                        .logoutSuccessUrl(LOGOUT_SUCCESS_URL);

        }
    }

  /* *** Define type of encryption to use *** */

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
