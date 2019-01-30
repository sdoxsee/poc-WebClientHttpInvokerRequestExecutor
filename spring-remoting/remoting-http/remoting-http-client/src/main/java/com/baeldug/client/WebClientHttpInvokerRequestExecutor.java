package com.baeldug.client;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.remoting.httpinvoker.AbstractHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WebClientHttpInvokerRequestExecutor extends AbstractHttpInvokerRequestExecutor {

    private final WebClient webClient;

    public WebClientHttpInvokerRequestExecutor(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    protected RemoteInvocationResult doExecuteRequest(HttpInvokerClientConfiguration config, ByteArrayOutputStream baos) throws IOException, ClassNotFoundException {
        InputStreamResource inputStreamResource = this.webClient
                .post()
                .uri(config.getServiceUrl())
                .header(HTTP_HEADER_CONTENT_TYPE, CONTENT_TYPE_SERIALIZED_OBJECT)
                .syncBody(new ByteArrayResource(baos.toByteArray()))
                .exchange()
                .flatMap(response -> response.bodyToMono(InputStreamResource.class))
                .block();

        return readRemoteInvocationResult(inputStreamResource.getInputStream(), config.getCodebaseUrl());
    }
}
