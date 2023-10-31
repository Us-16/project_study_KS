package com.example.project_study.config.session_security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customLoginSuccessHandler: CustomLoginSuccessHandler,
    private val customLoginFailureHandler: CustomLoginFailureHandler,
) {
    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration):AuthenticationManager{
        return authenticationConfiguration.authenticationManager
    }
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() } //범인
        http.authorizeHttpRequests {
                it
                    .requestMatchers("/", "/javascript/**", "/css/**", "/img/**", "/logout", "/gall/**").permitAll()
                    .requestMatchers("/account/**", "/login/**", "/sms/**").anonymous()
                    .anyRequest().fullyAuthenticated()
            } //이것이 코틀린스러움인건가?
        http.formLogin {
                it
                    .loginPage("/login")
                    .loginProcessingUrl("/login/action")
                    .successHandler(customLoginSuccessHandler)
                    .failureHandler(customLoginFailureHandler)
            }
        http.logout{
            it
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutRequestMatcher(AntPathRequestMatcher("/logout")) //3버전은 2버전과 완전히 다릅니다;;
                .logoutSuccessUrl("/")
        }
        return http.build()
    }
}