package pbs.agile.webapi.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secret = "superSecretKey" // Zmień na lepszy sekret

    fun generateToken(username: String): String {
        val claims: Map<String, Any> = HashMap()
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 godzina
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return extractClaims(token).subject
    }

    fun isTokenValid(token: String, userDetails: org.springframework.security.core.userdetails.UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaims(token).expiration.before(Date())
    }
}
