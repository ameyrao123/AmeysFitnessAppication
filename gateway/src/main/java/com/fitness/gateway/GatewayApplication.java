package com.fitness.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()

				.route("userService", r -> r.path("/api/users/**")
						.uri("lb://USERSERVICE")) // CHANGED: lb:// instead of http://localhost
				.route("activityService", r -> r.path("/api/activities/**")
						.uri("lb://ACTIVITYSERVICE"))
				.route("aiService", r -> r.path("/api/recommendation/**")
						.uri("lb://AISERVICE"))
				.build();
	}

}
