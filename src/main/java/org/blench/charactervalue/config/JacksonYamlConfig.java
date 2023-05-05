package org.blench.charactervalue.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.blench.charactervalue.converters.MappingJackson2YamlHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://stackoverflow.com/questions/58414715/generate-yaml-format-response-in-springboot
@Configuration
public class JacksonYamlConfig {
    @Bean
    public MappingJackson2YamlHttpMessageConverter yamlHttpMessageConverter() {
        YAMLMapper mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return new MappingJackson2YamlHttpMessageConverter(mapper);
    }
}