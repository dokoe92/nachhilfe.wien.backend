package codersbay.vienna.nachhilfe.wien.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(
        info = @Info(title = "nachhilfe.wien")
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class SwaggerConfig {

}


