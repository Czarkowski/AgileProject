package pbs.agile.webapi.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secret = "superSecretKey" // Zmień na lepszy sekret
    private val accessTokenValidity = 1000 * 60 * 15 // 15 minut
    private val refreshTokenValidity = 1000 * 60 * 60 * 24 * 7 // 7 dni

    fun generateToken(username: String, authorities: List<String>): String {
        val claims: HashMap<String, Any> = HashMap()
        claims.put("roles", authorities)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + accessTokenValidity)) // 1 godzina
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenValidity))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return extractClaims(token).subject
    }


    fun isTokenValid(token: String): Boolean {
        return try {
            !isTokenExpired(token)
        } catch (e: Exception) {
            false
        }
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

    fun extractAuthorities(token: String): List<String> {
        val claims = extractClaims(token)  // Funkcja do pobierania claims z tokenu
        return claims["roles"] as List<String>  // Odczytujemy listę ról z claims
    }
}
