package com.bubblezombie.game.Util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public final class DefaultValues {
    /**
     * @param clazz the class for which a default value is needed
     * @return A reasonable default value for the given class (the boxed default value for primitives, <code>null</code>
     * otherwise).
     */
    public static Object getDefaultValueForClass(Class clazz) {
        return DEFAULT_VALUES.get(clazz);
    }

    private static final Map<Class, Object> DEFAULT_VALUES = unmodifiableMap(new HashMap<Class, Object>() {
        // Default primitive values
        private boolean b = false;
        private byte by = 0;
        private char c = 0;
        private double d = 0.0;
        private float f = 0f;
        private int i = 0;
        private long l = 0L;
        private short s = 0;

        {
            for (final Field field : getClass().getDeclaredFields()) {
                try {
                    put(field.getType(), field.get(this));
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    });

    private DefaultValues() {
    }
}