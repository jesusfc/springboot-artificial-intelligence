package com.jesusfc.springbootartificialintelligence.iaFunctions;

import com.jesusfc.springbootartificialintelligence.config.NinjaApiProperties;
import com.jesusfc.springbootartificialintelligence.model.CelebrityRQ;
import com.jesusfc.springbootartificialintelligence.model.CelebrityRS;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on sept - 2024
 */
@RequiredArgsConstructor
public class CelebrityServiceFunction implements Function<CelebrityRQ, CelebrityRS> {

    private final NinjaApiProperties ninjaApiProperties;

    /**
     * Applies this function to the given argument.
     *
     * @param celebrityRQ the function argument
     * @return the function result
     */
    @Override
    public CelebrityRS apply(CelebrityRQ celebrityRQ) {
        RestClient restClient = RestClient.builder()
                .baseUrl(ninjaApiProperties.getApiUrl())
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", ninjaApiProperties.getApiKey());
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();
/*
        uriBuilder -> {


            uriBuilder.queryParam("name", celebrityRQ.celebrityName());

            if (celebrityRQ.() != null && !celebrityRQ.state().isBlank()) {
                uriBuilder.queryParam("nationality", celebrityRQ.state());
            }
            if (celebrityRQ.country() != null && !celebrityRQ.country().isBlank()) {
                uriBuilder.queryParam("gender", celebrityRQ.country());
            }
*/
        CelebrityRS[] result = restClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", celebrityRQ.celebrityName()).build())
                .retrieve()
                .body(CelebrityRS[].class);

        assert result != null;
        return result[0];
    }
}
