package com.atharvadholakia.password_manager.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class HealthCheckScheduler {

  private final WebClient webClient;

  public HealthCheckScheduler() {
    this.webClient = WebClient.builder().build();
  }

  @Scheduled(fixedRate = 12 * 60 * 1000)
  public void sendHealthCheck() {
    String healthCheckUrl = "http://localhost:3000/api/health";

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
