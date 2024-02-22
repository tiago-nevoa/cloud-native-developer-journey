package com.sap.cc.ascii;

import com.sap.cc.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AsciiArtServiceClient {

    private final WebClient webClient;
    private final AsciiArtServiceUrlProvider asciiArtServiceUrlProvider;
    @Value("${service.ascii.username}")
    private final String asciiServiceUsername = "";
    @Value("${service.ascii.password}")
    private final String asciiServicePassword = "";

    public AsciiArtServiceClient(WebClient webClient, AsciiArtServiceUrlProvider asciiArtServiceUrlProvider) {
        this.webClient = webClient;
        this.asciiArtServiceUrlProvider = asciiArtServiceUrlProvider;
    }

    public String getAsciiString(AsciiArtRequest asciiArtRequest) {
        try {
            AsciiArtResponse response = webClient.post().uri(asciiArtServiceUrlProvider.getServiceUrl())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(asciiServiceUsername, asciiServicePassword)).bodyValue(asciiArtRequest)
                    .retrieve().bodyToMono(AsciiArtResponse.class).block();

            return response != null ? response.getBeautifiedText() : null;
        } catch (WebClientResponseException.BadRequest e) {
            throw new InvalidRequestException("Invalid request parameters", e);
        }
    }
}
