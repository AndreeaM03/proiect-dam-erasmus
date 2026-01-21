package org.example.proiectdamerasmusclient.api;

import org.example.proiectdamerasmusclient.api.AppBackendProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(AppBackendProperties.class)
public class WebClientConfig {

    @Bean
    public WebClient backendWebClient(AppBackendProperties props) {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .build();
    }
}
