package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/reactive")
public class WebFluxController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> findAll() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<String> flux = Flux.just("A", "B", "C");
        return Flux.zip(interval, flux)
                            .map(Tuple2::getT2);
    }

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Long> test() {
        return Flux.interval(Duration.ofSeconds(2)).map(aLong -> aLong);
    }
}
