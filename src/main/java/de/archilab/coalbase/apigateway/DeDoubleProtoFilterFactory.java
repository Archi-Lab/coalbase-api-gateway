package de.archilab.coalbase.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DeDoubleProtoFilterFactory extends
    AbstractGatewayFilterFactory<DeDoubleProtoFilterFactory.Config> {

  public DeDoubleProtoFilterFactory() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    // grab configuration from Config object
    return (exchange, chain) -> {
      //If you want to build a "pre" filter you need to manipulate the
      //request before calling change.filter
      ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
      //use builder to manipulate the request

      List<String> headerValue = new ArrayList<>(Arrays.asList("https"));
      builder.headers(httpHeaders -> httpHeaders.replace("X-Forwarded-Proto", headerValue));

      return chain.filter(exchange.mutate().request(builder.build()).build());
    };
  }


  public static class Config {

  }

}
