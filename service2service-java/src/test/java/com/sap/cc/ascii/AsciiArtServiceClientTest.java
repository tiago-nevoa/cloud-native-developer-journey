package com.sap.cc.ascii;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cc.InvalidRequestException;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

public class AsciiArtServiceClientTest {

    public static final AsciiArtRequest WITH_VALID_ARGS = new AsciiArtRequest("HelloWorld", "3");
    public static final AsciiArtRequest WITH_UNKNOWN_FONT_ID = new AsciiArtRequest("handleThis", "9");
    public static MockWebServer mockBackEnd;
    private final AsciiArtServiceUrlProvider asciiArtServiceUrlProvider = Mockito.mock(AsciiArtServiceUrlProvider.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AsciiArtServiceClient asciiArtServiceClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String serviceUrl = String.format("http://localhost:%s/api/v1/asciiArt/", mockBackEnd.getPort());
        Mockito.when(asciiArtServiceUrlProvider.getServiceUrl()).thenReturn(serviceUrl);

        asciiArtServiceClient = new AsciiArtServiceClient(WebClient.create(), asciiArtServiceUrlProvider);
    }

    @Test
    void whenCallingGetAsciiString_thenClientMakesCorrectCallToService() {

        // Create response and enqueue it
        AsciiArtResponse response = new AsciiArtResponse("'Hello World' as ascii art", "comic");
        try {
            mockBackEnd.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(response))
                    .addHeader(org.springframework.http.HttpHeaders.CONTENT_TYPE,
                            org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
        } catch (Exception e) {
            Assert.fail("Failed to setup mock response: " + e.getMessage());
        }

        var res = asciiArtServiceClient.getAsciiString(WITH_VALID_ARGS);

        assertEquals("'Hello World' as ascii art", res);
    }

    @Test
    void whenRequestingWithInvalidRequest_thenInvalidRequestExceptionIsThrown() {
        mockBackEnd.enqueue(new MockResponse().setResponseCode(400));

        assertThatThrownBy(() -> asciiArtServiceClient.getAsciiString(WITH_UNKNOWN_FONT_ID)).isInstanceOf(InvalidRequestException.class);
    }
}
