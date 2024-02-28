package com.competa.competademo.config;

import com.competa.competademo.dto.ErrorResponseDto;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import static com.competa.competademo.security.config.SecurityConfig.LOGIN_ENDPOINT;
import static com.competa.competademo.security.config.SecurityConfig.LOGOUT_ENDPOINT;


/**
 * @author Oleg Karimov
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(ErrorResponseDto.class).resolveAsRef(false));

        return new OpenAPI()
                .components(new Components()
                        .addSchemas("EmailAndPassword", emailAndPassword())
                        .addSecuritySchemes("cookieAuth", securityScheme())
                        .addSchemas("AuthResponse", resolvedSchema.schema.description("AuthResponse")))
                .addSecurityItem(buildSecurity())
                .paths(buildAuthenticationPath())
                .info(new Info().title("Competa Service API").version("0.1"));
    }

    static Paths buildAuthenticationPath() {
        return new Paths()
                .addPathItem(LOGIN_ENDPOINT, buildAuthenticationPathItem())
                .addPathItem(LOGOUT_ENDPOINT, buildLogoutPathItem());
    }

    private static PathItem buildLogoutPathItem() {
        return new PathItem().post(
                new Operation()
                        .addTagsItem("Authentication")
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse().description("Logout successful"))));
    }

    private static PathItem buildAuthenticationPathItem() {
        return new PathItem().post(
                new Operation()
                        .addTagsItem("Authentication")
                        .requestBody(buildAuthenticationRequestBody())
                        .responses(new ApiResponses()
                                .addApiResponse("200",
                                        new ApiResponse()
                                                .description("Login successful")
                                                .content(new Content().addMediaType("application/json",
                                                        new MediaType().schema(new Schema<>().$ref("AuthResponse"))))
                                                .headers(
                                                        Collections
                                                                .singletonMap("Set-Cookie",
                                                                        new Header()
                                                                                .example("JSESSIONID=1234")
                                                                                .description("Session identifier"))))
                                .addApiResponse("401",
                                        new ApiResponse()
                                                .description("Incorrect username or password")
                                                .content(new Content()
                                                        .addMediaType("application/json",
                                                                new MediaType()
                                                                        .schema(new Schema<>().$ref("AuthResponse")))))));
    }

    static RequestBody buildAuthenticationRequestBody() {
        return new RequestBody().content(
                new Content()
                        .addMediaType("application/x-www-form-urlencoded",
                                new MediaType()
                                        .schema(new Schema<>()
                                                .$ref("EmailAndPassword"))));
    }

    static SecurityRequirement buildSecurity() {
        return new SecurityRequirement().addList("CookieAuthentication");
    }

    static Schema<?> emailAndPassword() {
        return new Schema<>()
                .type("object")
                .description("User's email and password")
                .addProperty("username", new Schema<>().type("string"))
                .addProperty("password", new Schema<>().type("string"));
    }

    static SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("cookieAuth")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID");
    }
}
