package com.spring.tutorial.user.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.net.URI;

@Service
@Slf4j
public class RestClient {

    @Autowired
    private WebClient webClient;

    @Bean
    WebClient getWebClient() {
        return WebClient.builder().clientConnector(getClientHttpConnector())
                .exchangeStrategies(getExchangeStrategies())
                .build();
    }

    public <T> Mono<ClientResponse> post(String baseURL, HttpHeaders headers, T body, Class bodyClazz) {
        log.info("Executing REST POST method for URL : {}", baseURL);

        return webClient.post()
                .uri(URI.create(baseURL))
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(body), bodyClazz)
                .exchange();
    }


    public <T> Mono<ClientResponse> postMultiPartForm(String baseURL, HttpHeaders headers, MultipartBodyBuilder bodyBuilder) {
        log.info("Executing REST POST MultiPart file method for URL : {}", baseURL);

        return webClient.post()
                .uri(URI.create(baseURL))
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(bodyBuilder.build())
                .exchange();
    }

    private SslContext getSslContext() throws SSLException {
        return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    }

    private ClientHttpConnector getClientHttpConnector() {
        return new ReactorClientHttpConnector(HttpClient.create().secure(t -> {
            try {
                t.sslContext(getSslContext());
            } catch (SSLException se) {
                log.error("Exception in building clientHttp connector from SSL: {}", se.getMessage());
            }
        }));
    }

    private ExchangeStrategies getExchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
    }
}
