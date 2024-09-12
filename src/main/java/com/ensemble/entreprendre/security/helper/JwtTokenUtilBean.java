package com.ensemble.entreprendre.security.helper;

import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenUtilBean {

	private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 18000l;
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String HEADER_STRING = "Authorization";

	private String key;

	private String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(UserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put("scopes", userDetails.getAuthorities());

		return Jwts.builder().setClaims(claims).setIssuer("https://www.modisfrance.fr/")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
				.signWith(SignatureAlgorithm.HS256, this.key).compact();
	}

	public boolean validateToken(HttpServletRequest req, UserDetails userDetails) {
		if (!hasToken(req)) {
			return false;
		}
		String header = req.getHeader(JwtTokenUtilBean.HEADER_STRING);
		String token = header.replace(JwtTokenUtilBean.TOKEN_PREFIX, "");
		return validateToken(token, userDetails);
	}

	public boolean hasToken(HttpServletRequest req) {
		String header = req.getHeader(JwtTokenUtilBean.HEADER_STRING);
		return header != null && header.startsWith(JwtTokenUtilBean.TOKEN_PREFIX)
				&& !header.replace(JwtTokenUtilBean.TOKEN_PREFIX, "").isBlank();
	}

	public String getUsername(HttpServletRequest req) {
		if (!hasToken(req)) {
			return null;
		}
		String header = req.getHeader(JwtTokenUtilBean.HEADER_STRING);
		String token = header.replace(JwtTokenUtilBean.TOKEN_PREFIX, "");
		return getUsernameFromToken(token);
	}

}
