package org.blench.charactervalue.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class MappingJackson2YamlHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public MappingJackson2YamlHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.parseMediaType("application/yaml"));
    }
}
