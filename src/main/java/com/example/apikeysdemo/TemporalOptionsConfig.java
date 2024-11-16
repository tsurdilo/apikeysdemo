package com.example.apikeysdemo;

import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.temporal.authorization.AuthorizationGrpcMetadataProvider;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLException;

@Configuration
public class TemporalOptionsConfig {
    @Bean
    public TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>
    customServiceStubsOptions() {
        return new TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowServiceStubsOptions.Builder customize(
                    @Nonnull WorkflowServiceStubsOptions.Builder optionsBuilder) {
                try {
                    Metadata.Key<String> TEMPORAL_NAMESPACE_HEADER_KEY =
                            Metadata.Key.of("temporal-namespace", Metadata.ASCII_STRING_MARSHALLER);
                    Metadata metadata = new Metadata();
                    metadata.put(TEMPORAL_NAMESPACE_HEADER_KEY, "tihomirapikeys.a2dd6");

                    optionsBuilder.setChannelInitializer(
                                    (channel) -> {
                                        channel.intercept(MetadataUtils.newAttachHeadersInterceptor(metadata));
                                    })
                            .addGrpcMetadataProvider(
                                    new AuthorizationGrpcMetadataProvider(() -> "Bearer " + "<API_KEY>"))
                            .setTarget("us-east-1.aws.api.temporal.io:7233");
                    optionsBuilder.setSslContext(SimpleSslContextBuilder.noKeyOrCertChain().setUseInsecureTrustManager(false).build());
                } catch (SSLException e) {
                    System.out.println("*************** ERROR IN CUSTOMIZATION : " + e.getMessage());
                    return null;
                }
                return optionsBuilder;
            }
        };
    }
}
