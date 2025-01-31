package com.journal.util;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class GenericMapperUtil {

	private static final ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static <E, D> List<D> mapListToDto(List<E> entityList, Class<D> dtoClass) {
		return entityList.stream().map(entity -> modelMapper.map(entity, dtoClass)).toList();
	}

	public static <D, E> List<E> mapListToEntity(List<D> dtoList, Class<E> entityClass) {
		return dtoList.stream().map(dto -> modelMapper.map(dto, entityClass)).toList();
	}

	public static <E, D> D mapToDto(E entity, Class<D> dtoClass) {
		return modelMapper.map(entity, dtoClass);
	}

	public static <D, E> E mapToEntity(D dto, Class<E> entityClass) {
		return modelMapper.map(dto, entityClass);
	}

	private GenericMapperUtil() {
		throw new IllegalStateException("Utility class");
	}

}
