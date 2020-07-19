package com.spring.tutorial.user.api;

import com.spring.tutorial.user.api.model.Project;
import com.spring.tutorial.user.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {Constants.API_BASE_CONTEXT_PATH}, produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ApplicationApi {

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @GetMapping(path = {"/version"})
    public Mono<Project> getVersion() {

        log.debug("Checking Health point...{}", "Clk-miscellaneous");
        return Mono.just(Project.builder()
                .version(version)
                .name(name)
                .build());
    }
}
