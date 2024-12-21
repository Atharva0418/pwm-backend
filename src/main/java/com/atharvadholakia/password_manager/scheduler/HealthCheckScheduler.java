package com.atharvadholakia.password_manager.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class HealthCheckScheduler {

  private final WebClient webClient;

  @Value("${healthcheck.baseurl}")
  private String baseUrl;

  public HealthCheckScheduler() {
    this.webClient = WebClient.builder().build();
  }

  @Scheduled(fixedRate = 12 * 60 * 1000)
  public void sendHealthCheck() {
    String healthCheckUrl = baseUrl + "/api/health";

    webClient
        .get()
        .uri(healthCheckUrl)
        .retrieve()
        .bodyToMono(String.class)
        .doOnNext(response -> log.info("Health Check Successful : {}", response))
        .doOnError(error -> log.error("Health check failed : {}", error))
        .subscribe();
  }
}
