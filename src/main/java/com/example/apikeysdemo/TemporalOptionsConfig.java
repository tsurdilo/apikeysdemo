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

                System.out.println("*************** IN CUSTOMIZATION CONFIG!!");
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
                                    new AuthorizationGrpcMetadataProvider(() -> "Bearer " + "eyJhbGciOiJFUzI1NiIsICJraWQiOiJXdnR3YUEifQ.eyJhY2NvdW50X2lkIjoiYTJkZDYiLCAiYXVkIjpbInRlbXBvcmFsLmlvIl0sICJleHAiOjE3Mzk1MzEwNDksICJpc3MiOiJ0ZW1wb3JhbC5pbyIsICJqdGkiOiJtcjUxVlc1RUtEbkVrV1Uza1RJQzRNeUFwT0tpZHpnZCIsICJrZXlfaWQiOiJtcjUxVlc1RUtEbkVrV1Uza1RJQzRNeUFwT0tpZHpnZCIsICJzdWIiOiI0YTA2MWE0MjU2ZGE0NzY5YmY2ZTU1ZTVhY2Q1Mzc5ZSJ9.DmnANyf938TB1FoNX9sOw15BC2D0ojo07Ms8F67OUFabWMOUY66s9G1IP_iPjn8-WuUMMtXy5DQ5GOC9pJu5-w"))
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
