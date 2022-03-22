package com.out_focusing.out_focusing_backend.global.security

import com.out_focusing.out_focusing_backend.global.error.ExceptionHandlerFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val customUserDetailService: CustomUserDetailService
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailService)
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {

        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true)
        http.addFilterBefore(filter, CsrfFilter::class.java)

        http.csrf().disable()
            .authorizeRequests().antMatchers("/h2-console/**" ,"/v1/auth/register", "/v1/auth/login", "/v1/auth/token").permitAll().anyRequest().authenticated()
            .and().exceptionHandling().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().headers().frameOptions().disable()

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(exceptionHandlerFilter, JwtFilter::class.java)
    }

}
