package org.apache.camel.demo;

import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.citrusframework.kafka.endpoint.KafkaEndpoint;
import org.citrusframework.mail.server.MailServer;
import org.citrusframework.spi.BindToRegistry;

import static org.citrusframework.kafka.endpoint.builder.KafkaEndpoints.kafka;
import static org.citrusframework.mail.endpoint.builder.MailEndpoints.mail;

public class CitrusEndpointConfig {

    @BindToRegistry
    public KafkaEndpoint products() {
        return kafka()
                .asynchronous()
                .topic("products")
                .build();
    }

    @BindToRegistry
    public KafkaEndpoint bookings() {
        return kafka()
                .asynchronous()
                .topic("bookings")
                .build();
    }

    @BindToRegistry
    public KafkaEndpoint supplies() {
        return kafka()
                .asynchronous()
                .topic("supplies")
                .build();
    }

    @BindToRegistry
    public KafkaEndpoint shipping() {
        return kafka()
                .asynchronous()
                .topic("shipping")
                .timeout(10000L)
                .build();
    }

    @BindToRegistry
    public KafkaEndpoint completed() {
        return kafka()
                .asynchronous()
                .topic("completed")
                .timeout(10000L)
                .build();
    }

    @BindToRegistry
    public MailServer mailServer() {
        return mail().server()
                .port(2222)
                .knownUsers(Collections.singletonList("foodmarket@quarkus.io:foodmarket:secr3t"))
                .autoAccept(true)
                .autoStart(true)
                .build();
    }

    @BindToRegistry
    public ObjectMapper mapper() {
        return JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .disable(JsonParser.Feature.AUTO_CLOSE_SOURCE)
                .enable(MapperFeature.BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES)
                .build()
                .setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_EMPTY, JsonInclude.Include.NON_EMPTY));
    }

}
