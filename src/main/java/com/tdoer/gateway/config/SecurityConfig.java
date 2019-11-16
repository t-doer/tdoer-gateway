package com.tdoer.gateway.config;

import com.tdoer.security.configure.EnableGatewayService;
import com.tdoer.security.configure.EnableManagementProtection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

/**
 * Default web security configuration with lowest priority than other configurations
 */

@EnableGatewayService
@EnableManagementProtection
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Zuul filters will check access, permit all here
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);

        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
        web.ignoring()
                // basic
                .antMatchers("/error")
                // swagger
                .antMatchers("/swagger-ui.html", "/swagger-resources.**", "/webjars/**", "/v2/api-docs**")
                // static html/js/icon etc.
                .antMatchers("/webjars/**", "/static/**", "/public/**", "/resource/**", "/favicon.ico");
    }
}
