package com.baeldug.client;

import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.web.reactive.function.client.WebClient;

import static java.lang.System.out;

@SpringBootApplication
public class Client {

    @Bean
    public HttpInvokerProxyFactoryBean invoker(HttpInvokerRequestExecutor httpInvokerRequestExecutor) {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/booking");
        invoker.setServiceInterface(CabBookingService.class);
        invoker.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor);
        return invoker;
    }

    @Bean
    public HttpInvokerRequestExecutor httpInvokerRequestExecutor(WebClient webClient) {
        return new WebClientHttpInvokerRequestExecutor(webClient);
    }

    public static void main(String[] args) throws BookingException {
        SpringApplication.run(Client.class, args);
    }
}
