package com.spring.boot.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.spring.boot.entity.User;

public class UserArguementsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		return Stream.of(Arguments.of(User.builder().userName("GHH").password("Iram").build()),

		        Arguments.of(User.builder().userName("Motu").password("").build()));
	}

}
