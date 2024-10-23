package com.cineLog.cineLog.service;

import com.cineLog.cineLog.entity.UserEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
               Arguments.of( UserEntity.builder().username("Shyam").password("shyam").build()),
               Arguments.of( UserEntity.builder().username("suraj").password("").build()));
    }
}
