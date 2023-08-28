package com.twtw.backend.domain.path.client;

import com.twtw.backend.domain.path.dto.client.SearchPathRequest;
import com.twtw.backend.domain.path.dto.client.SearchPathResponse;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.global.client.PathClient;
import com.twtw.backend.global.exception.WebClientResponseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;


@Component
public class SearchPathClient implements PathClient<SearchPathRequest, SearchPathResponse> {
    @Qualifier("NaverWebClient")
    private final WebClient webClient;

    public SearchPathClient(WebClient webClient){
        this.webClient = webClient;
    }

    /*상세 검색을 위한 변경 필요*/
    private URI getPathUri(final SearchPathRequest request, final UriBuilder uriBuilder){
        final UriBuilder builder =
                uriBuilder.
                        path("driving")
                        .queryParam("start",request.getStart())
                        .queryParam("goal",request.getEnd());

        return builder.build();
    }

    @Override
    public String request(final SearchPathRequest request) {
        return webClient
                .get()
                .uri(uri -> getPathUri(request,uri))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional()
                .orElseThrow(WebClientResponseException::new);
    }
}
