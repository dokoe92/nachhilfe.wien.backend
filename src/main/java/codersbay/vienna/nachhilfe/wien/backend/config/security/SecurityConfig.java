package codersbay.vienna.nachhilfe.wien.backend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                // csrf and headers need to be disabled in order the H2 console works
                //.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.disable())
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        /*
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers("/v3/api-docs/swagger-config").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers("h2-console/**").permitAll()

                         */

                        .requestMatchers("/auth").permitAll()
                        .requestMatchers("/auth/get-auth").permitAll()
                        .requestMatchers("/auth/create-teacher").permitAll()
                        .requestMatchers("/auth/create-student").permitAll()

                        .requestMatchers("/teacher/update-districts/**").hasRole("TEACHER")
                        .requestMatchers("/teacher/updateTeacher/**").hasRole("TEACHER")
                        .requestMatchers("/teacher/deleteTeacher/**").hasRole("TEACHER")


                        .requestMatchers("/student/updateStudent/**").hasRole("STUDENT")
                        .requestMatchers("/student/deleteStudent/**").hasRole("STUDENT")

                        .requestMatchers("/coaching/offer-coaching/**").hasRole("TEACHER")
                        .requestMatchers("/coaching/update-coaching/**").hasRole("TEACHER")

                        .requestMatchers("/create-conversation/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        .requestMatchers("/send-message/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        .requestMatchers("/appointment/send-appointment/**").hasRole("STUDENT")
                        .requestMatchers("/appointment/update-status/**").hasRole("TEACHER")
                        .requestMatchers("/appointment/get-appointments/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers("/appointment/get-appointments-date/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        .requestMatchers("/feedback").hasAnyRole("STUDENT")

                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/admin/updateAdmin/**").hasRole("ADMIN")
                        .requestMatchers("/admin/find-user").hasRole("ADMIN")
                        .requestMatchers("/admin/edit-user/**").hasRole("ADMIN")
                        .requestMatchers("/admin/active-inactive/**").hasRole("ADMIN")
                        .requestMatchers("/admin/delete-image/**").hasRole("ADMIN")
                        .requestMatchers("/admin/delete-feedback/**").hasRole("ADMIN")
                        .requestMatchers("/admin/deleteAdmin/**").hasRole("ADMIN")
                        .requestMatchers("/admin/deleteTeacher/**").hasRole("ADMIN")
                        .requestMatchers("/admin/deleteStudent/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers((header) -> header.frameOptions().sameOrigin())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:5174", "https://honest-blow-production.up.railway.app"
 ));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }






}
