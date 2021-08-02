package cn.aethli.mls.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JacksonConfiguration {
    @Bean("defaultMapper")
    public ObjectMapper getDefaultMapper() {
        return new ObjectMapper();
    }
}
