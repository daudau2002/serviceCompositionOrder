package com.server.orderservice.config;

import com.server.orderservice.client.ItemClient;
import com.server.orderservice.client.NotifiClient;
import com.server.orderservice.client.PaymentClient;
import com.server.orderservice.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction; // that why we create this

    @Bean
    public WebClient itemWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://item-service") // need to tell that it a load balance -> do configuration
                .filter(filterFunction) // tell
                .build();
    }

    @Bean
    public ItemClient itemClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(
                        WebClientAdapter.create(itemWebClient())
                )
                .build();
        return httpServiceProxyFactory.createClient(ItemClient.class);
    }

    @Bean
    public WebClient userWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://user-service") // need to tell that it a load balance -> do configuration
                .filter(filterFunction) // tell
                .build();
    }

    @Bean
    public UserClient userClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(
                        WebClientAdapter.create(userWebClient())
                )
                .build();
        return httpServiceProxyFactory.createClient(UserClient.class);
    }

    @Bean
    public WebClient notifiWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://notifi-service") // need to tell that it a load balance -> do configuration
                .filter(filterFunction) // tell
                .build();
    }

    @Bean
    public NotifiClient notifiClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(
                        WebClientAdapter.create(notifiWebClient())
                )
                .build();
        return httpServiceProxyFactory.createClient(NotifiClient.class);
    }

    @Bean
    public WebClient payWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://payment-service") // need to tell that it a load balance -> do configuration
                .filter(filterFunction) // tell
                .build();
    }

    @Bean
    public PaymentClient paymentClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(
                        WebClientAdapter.create(payWebClient())
                )
                .build();
        return httpServiceProxyFactory.createClient(PaymentClient.class);
    }
}
