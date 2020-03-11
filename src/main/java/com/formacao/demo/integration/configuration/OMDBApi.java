package com.formacao.demo.integration.configuration;

import com.formacao.demo.integration.response.OMDBResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://www.omdbapi.com/", configuration = SerializationConfiguration.class, name = "omdb")
public interface OMDBApi {
    @GetMapping
    OMDBResponse findById(@RequestParam (name = "i") String id, @RequestParam(name = "apikey") String apikey);
}