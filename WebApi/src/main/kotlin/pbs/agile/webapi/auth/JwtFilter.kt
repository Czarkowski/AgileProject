package pbs.agile.webapi.auth

import pbs.agile.webapi.services.AuthUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: AuthUserDetailsService
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authHeader = httpRequest.getHeader("Authorization")

        if (authHeader != null && authHeader.subSequence(0,7).toString() == "Bearer ") {
            val token = authHeader.subSequence(7, authHeader.length).toString()
            val username = jwtUtil.extractUsername(token)
            val authorities = jwtUtil.extractAuthorities(token).stream().map { SimpleGrantedAuthority(it) }.toList()

            if (username != null && SecurityContextHolder.getContext().authentication == null) {
//                val userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtUtil.isTokenValid(token)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        username, null, authorities  // Możesz dodać uprawnienia jeśli są zawarte w tokenie
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        chain.doFilter(request, response)
    }
}
