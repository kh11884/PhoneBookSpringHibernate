package ru.academits.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<S, D> implements Converter<S, D> {
    @Override
    public List<D> convert(List<S> source) {
        return source.stream().map(source1 -> convert(source1)).collect(Collectors.toList());
    }
}
