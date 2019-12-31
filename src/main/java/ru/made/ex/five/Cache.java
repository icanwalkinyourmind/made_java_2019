package ru.made.ex.five;

import ru.made.ex.five.storage.StorageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.made.ex.five.storage.StorageType.IN_MEMORY;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    StorageType storageType() default IN_MEMORY;

    Class[] storeBy() default {};

    int maxListSize() default 0;

    String keyPrefix() default "";

    boolean compress() default false;


}
