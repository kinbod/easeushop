package org.networking.config;

import java.util.Collections;
import java.util.List;

import org.networking.constant.ResourceURL;
import org.networking.constant.VersionConstant;
import org.networking.custom.CustomClientDetailsService;
import org.networking.custom.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


/**
 * OAuth2 server configuration.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/30/15
 * @since JDK1.8
 */
//@Configuration
//@EnableResourceServer
public class OAuth2ServerConfig {

    /**
     * Resource of api
     *
     * @return      {@link ResourceServerConfiguration}
     */
    @Bean
    protected ResourceServerConfiguration adminResources() {

        ResourceServerConfiguration resource = new org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Collections.<ResourceServerConfigurer>singletonList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                    http.csrf().disable()
                        .authorizeRequests()
                        .antMatchers(WELCOME_URL).authenticated()
                        .antMatchers(CLIENT_URL).hasAnyAuthority("root", "client")
                        .antMatchers(USER_URL).hasAnyAuthority("root", "user")
                        .antMatchers(ROLE_URL).hasAnyAuthority("root", "role")
                        .antMatchers(GROUP_URL).hasAnyAuthority("root", "group")
                        .antMatchers(RESOURCE_URL).hasAnyAuthority("root", "resource");
            }

        }));

        resource.setOrder(1);

        return resource;
    }

    /**
     * OAuth2 authorization server configuration.
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        // Token store type.
        private TokenStore tokenStore = new InMemoryTokenStore();

        @Autowired
        private AuthenticationManager authenticationManager;

        // When you use memory client, you can comment the custom client details service.
        @Autowired
        private CustomClientDetailsService clientDetailsService;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // Use JDBC client.
            // If you have many clients, you can use JDBC client.
            clients.withClientDetails(clientDetailsService);

            // Use memory client.
            // Or you can config them here use memory client.
            /*
            clients
                    .inMemory()
                    .withClient("ios_app")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
            */
            // You can add other clients like:
            /*
            clients
                    .inMemory()
                    .withClient("android_app")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID)
                    .secret("654321");
            */
        }
    }

    private static final String RESOURCE_ID = "api";
    private static final String WELCOME_URL = getURL(ResourceURL.WELCOME);
    private static final String CLIENT_URL = getURL(ResourceURL.CLIENTS);
    private static final String USER_URL = getURL(ResourceURL.USERS);
    private static final String ROLE_URL = getURL(ResourceURL.ROLES);
    private static final String GROUP_URL = getURL(ResourceURL.GROUPS);
    private static final String RESOURCE_URL = getURL(ResourceURL.RESOURCES);

    private static String getURL(CharSequence element) {
        return String.join("", ResourceURL.FIX, ResourceURL.RESOURCES, VersionConstant.V1, element, ResourceURL.FIX);
    }
}
