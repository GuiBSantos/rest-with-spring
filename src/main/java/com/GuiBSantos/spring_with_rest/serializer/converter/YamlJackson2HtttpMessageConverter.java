package com.GuiBSantos.spring_with_rest.serializer.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public final class YamlJackson2HtttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    YamlJackson2HtttpMessageConverter() {
        super(new YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL), MediaType.parseMediaType("aplication/yaml"));
    }
}
