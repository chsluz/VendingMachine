package com.vendingmachine.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class CustomAuthorizationFilter extends OncePerRequestFilter{
	
	private final Environment environment;
	
	public CustomAuthorizationFilter(Environment environment) {
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/login") ||  request.getServletPath().equals("/api/users")) {
			filterChain.doFilter(request, response);
		} else {
			String authorization = request.getHeader("Authorization");
			if (authorization != null && authorization.startsWith("Bearer")) {
				String token = authorization.replace("Bearer ", "");
				Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token);
				
				String[] roles = parseClaimsJws
						.getBody()
						.get("roles")
						.toString()
						.trim()
						.replace("[", "")
						.replace("]", "")
						.split(",");
				String username = parseClaimsJws.getBody().getSubject();
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				if(roles[0] != "") {
					Arrays.asList(roles).stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
				}
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authToken);
				filterChain.doFilter(request, response);
			}else {
				filterChain.doFilter(request, response);
			}
		}
		
	}
	
//	private boolean validateToken(String token) {
//		SignatureAlgorithm sa = SignatureAlgorithm.HS512;
//		SecretKeySpec secretKeySpec = new SecretKeySpec(environment.getProperty("token.secret").getBytes(), sa.getJcaName());
//		DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);
//		return validator.isValid(token, environment.getProperty("token.secret"));
//	}

}
