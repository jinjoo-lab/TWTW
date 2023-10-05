package com.twtw.backend.domain.path.controller;

import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentRequest;
import static com.twtw.backend.support.docs.ApiDocsUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twtw.backend.domain.path.dto.client.car.*;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.domain.path.service.PathService;
import com.twtw.backend.support.docs.RestDocsTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

@DisplayName("PathController의")
@WebMvcTest(PathController.class)
class PathControllerTest extends RestDocsTest {
    @MockBean private PathService pathService;

    @Test
    @DisplayName("자동차 경로 검색 API가 수행되는가")
    void searchCarPath() throws Exception {
        // given
        final SearchCarPathResponse expected =
                new SearchCarPathResponse(
                        0,
                        "",
                        "",
                        Map.of(
                                "",
                                new RouteUnitEnt[] {
                                    new RouteUnitEnt(
                                            new Summary(),
                                            List.of(
                                                    new Double[] {0.0, 0.0},
                                                    new Double[] {0.0, 0.0}))
                                }));

        given(pathService.searchCarPath(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/paths/search/car")
                                .content(
                                        toRequestBody(
                                                new SearchCarPathRequest(
                                                        "",
                                                        "",
                                                        "",
                                                        SearchPathOption.TRAFAST,
                                                        SearchPathFuel.DIESEL,
                                                        0)))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post search car path",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("보행자 경로 검색 API가 수행되는가")
    void searchPedPath() throws Exception {
        // given
        final SearchPedPathResponse expected = new SearchPedPathResponse("", List.of());

        given(pathService.searchPedPath(any())).willReturn(expected);

        // when
        final ResultActions perform =
                mockMvc.perform(
                        post("/paths/search/car")
                                .content(
                                        toRequestBody(
                                                new SearchCarPathRequest(
                                                        "",
                                                        "",
                                                        "",
                                                        SearchPathOption.TRAFAST,
                                                        SearchPathFuel.DIESEL,
                                                        0)))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(
                        document(
                                "post search ped path",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
