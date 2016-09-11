package org.networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by Gino on 8/28/2015.
 */
@SpringBootApplication
//I don't use Jmx and web socket, so I comment them.
@Import({
     DispatcherServletAutoConfiguration.class,
     EmbeddedServletContainerAutoConfiguration.class,
     ErrorMvcAutoConfiguration.class,
     HttpEncodingAutoConfiguration.class,
     HttpMessageConvertersAutoConfiguration.class,
     JacksonAutoConfiguration.class,
//     JmxAutoConfiguration.class,
     MultipartAutoConfiguration.class,
     ServerPropertiesAutoConfiguration.class,
     PropertyPlaceholderAutoConfiguration.class,
     ThymeleafAutoConfiguration.class,
     WebMvcAutoConfiguration.class,
//     WebSocketAutoConfiguration.class,
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
