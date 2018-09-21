package io.github.nortthon.poc.domains;

import java.util.Arrays;

public enum CollectionType {
    USER("user", User.class),
    ANY("any", Object.class);

    private final String collectionName;
    private final Class<?> clazz;

    CollectionType(String collectionName, Class<?> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
    }

    public static Class<?> getClazz(final String collectionName) {
        return Arrays.stream(CollectionType.values())
                .filter(c -> c.collectionName.equals(collectionName))
                .map(collectionType -> collectionType.clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(collectionName));
    }
}