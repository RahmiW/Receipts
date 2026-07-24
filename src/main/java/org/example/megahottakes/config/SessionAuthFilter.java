package org.example.megahottakes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SessionAuthFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    public SessionAuthFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        boolean isSignup = "POST".equals(method) && "/users".equals(path);
        boolean isLogin = "POST".equals(method) && "/users/login".equals(path);
        return isSignup || isLogin;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = header != null && header.startsWith("Bearer ") ? header.substring(7) : header;

        Optional<User> user = (token == null || token.isBlank())
                ? Optional.empty()
                : userRepository.findBySessionToken(token);

        if (user.isEmpty()) {
            respondUnauthorized(response, "Missing or invalid session token");
            return;
        }

        request.setAttribute("authUserId", user.get().getId());
        filterChain.doFilter(request, response);
    }

    private void respondUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String escaped = message.replace("\"", "\\\"");
        response.getWriter().write("{\"message\":\"" + escaped + "\"}");
    }
}
