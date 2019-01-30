package com.baeldug.client;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.System.out;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class RunController {

    private final WebClient webClient;

    private final String uri;

    public RunController(WebClient webClient, @Value("${resource-uri}") String uri) {
        this.webClient = webClient;
        this.uri = uri;
    }

    @Autowired
    private CabBookingService cabBookingService;

    @GetMapping("/run")
    public Mono<String> run(@RegisteredOAuth2AuthorizedClient("client-id") OAuth2AuthorizedClient authorizedClient) throws BookingException {
//        return this.webClient
//                .get()
//                .uri("http://localhost:8080/hello")
//                .exchange()
//                .flatMap(response -> response.bodyToMono(String.class));
        Booking x = cabBookingService.bookRide("13 Seagate Blvd, Key Largo, FL 33037");
        out.println(x);
        return Mono.justOrEmpty(x.toString());
    }
}
