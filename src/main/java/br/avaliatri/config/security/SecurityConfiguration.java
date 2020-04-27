package br.avaliatri.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;


    private static final String[] PUBLIC_MATCHES = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHES_GET = {
            "/webjars/springfox-swagger-ui/**",
            "/swagger*",
            "/v2/api-docs",
            "/configuration/**",
            "/swagger*/**",
            "/webjars/**"
    };

    private static final String[] PUBLIC_MATCHES_POST = {
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http.headers().frameOptions().disable();
            http.authorizeRequests().antMatchers("/**").permitAll();
            http.cors().and().csrf().disable();
            http.authorizeRequests()
                    .antMatchers( HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll()
                    .antMatchers( HttpMethod.POST, PUBLIC_MATCHES_POST).permitAll()
                    .antMatchers(PUBLIC_MATCHES).permitAll()
                    .anyRequest().authenticated();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration );
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}