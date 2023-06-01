package com.vincentcodes.jishoapi.sterotype;

import java.lang.annotation.*;

/**
 * Indicate an entity that it also serve as DTO (not good practice though)
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DtoAsWell {
}
