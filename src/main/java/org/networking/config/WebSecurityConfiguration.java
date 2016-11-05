package org.networking.config;

import javax.servlet.Filter;

import org.networking.component.ClientResources;
import org.networking.custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring web security config. Extends {@link WebSecurityConfigurerAdapter}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(6)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests().antMatchers("/user/**").authenticated().
		 * anyRequest().permitAll().and()
		 * .exceptionHandling().authenticationEntryPoint(new
		 * LoginUrlAuthenticationEntryPoint("/login")).and()
		 * .formLogin().loginPage("/login").loginProcessingUrl("/login.do").
		 * defaultSuccessUrl("/user/info")
		 * .failureUrl("/login?err=1").permitAll().and().logout()
		 * .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
		 * 
		 * .and().addFilterBefore(githubFilter(),
		 * BasicAuthenticationFilter.class);
		 */
		http.authorizeRequests().antMatchers("/user/**").authenticated().anyRequest().permitAll().and()
				.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and()
				.formLogin().loginPage("/login").loginProcessingUrl("/login.do").defaultSuccessUrl("/products")
				.failureUrl("/login?err=1").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll().and()
				.addFilterBefore(githubFilter(), BasicAuthenticationFilter.class)
				.csrf().disable().headers().frameOptions().disable();
	}

	private Filter githubFilter() {
		OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/github");
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(githubClient().getClient(), oauth2ClientContext);
		githubFilter.setRestTemplate(githubTemplate);
		githubFilter.setTokenServices(new UserInfoTokenServices(githubClient().getResource().getUserInfoUri(),
				githubClient().getClient().getClientId()));
		return githubFilter;
	}

	@Bean
	@ConfigurationProperties("github")
	public ClientResources githubClient() {
		return new ClientResources();
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated();
		}
	}
}
