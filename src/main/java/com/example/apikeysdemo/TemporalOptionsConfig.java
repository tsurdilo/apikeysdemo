package com.example.apikeysdemo;

import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.temporal.authorization.AuthorizationGrpcMetadataProvider;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLException;

@Configuration
public class TemporalOptionsConfig {

    @Value("${tmprl.cloud.key}")
    private String apiKey;

    @Value("${tmprl.cloud.target}")
    private String target;

    @Value("${tmprl.cloud.namespace}")
    private String namespace;

    @Bean
    public TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>
    customServiceStubsOptions() {
        return new TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowServiceStubsOptions.Builder customize(
                    @Nonnull WorkflowServiceStubsOptions.Builder optionsBuilder) {
                return optionsBuilder;
            }
        };
    }
}
