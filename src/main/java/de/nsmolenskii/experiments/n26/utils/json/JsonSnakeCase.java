
package de.nsmolenskii.experiments.n26.utils.json;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.immutables.value.Value.Style;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.immutables.value.Value.Style.ValidationMethod.NONE;

@Style(
        jdkOnly = true,
        depluralize = true,
        validationMethod = NONE,
        forceJacksonPropertyNames = false
)
@Target(TYPE)
@Retention(RUNTIME)
@JacksonAnnotation
@JacksonAnnotationsInside
@JsonInclude(ALWAYS)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public @interface JsonSnakeCase {
}
