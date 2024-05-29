package org.example.capstonebackend.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {
    @Autowired
    ModelMapper modelMapper;

    public <D, E> E map(D dto, Class<E> entityType) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
        return modelMapper.map(dto, entityType);
    }
}
