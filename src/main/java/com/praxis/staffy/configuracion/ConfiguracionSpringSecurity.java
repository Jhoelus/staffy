package com.praxis.staffy.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.praxis.staffy.service.usuarios.impl.UsuarioServiceImpl;
import com.praxis.staffy.utilerias.LoginFilter;
import com.praxis.staffy.utilerias.filtros.JwtFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ConfiguracionSpringSecurity extends WebSecurityConfigurerAdapter {

	private UsuarioServiceImpl servicioUsuario;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
	    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().xssProtection().xssProtectionEnabled(true).block(true);
		http.cors()
			.and().csrf().disable()
			.authorizeRequests()
				.antMatchers("/swagger-ui.html/**").permitAll()
				.antMatchers("/movil/login/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/movil/**").authenticated()
				.anyRequest().authenticated()
				.and()
//	            // Las peticiones /login pasaran previamente por este filtro
	            .addFilterAfter(new LoginFilter("/movil/login", authenticationManager()),
	                    UsernamePasswordAuthenticationFilter.class)         
//	            // Las demás peticiones pasarán por este filtro para validar el token
	            .addFilter(new JwtFilter(authenticationManager())); 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService((UserDetailsService) servicioUsuario);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
}