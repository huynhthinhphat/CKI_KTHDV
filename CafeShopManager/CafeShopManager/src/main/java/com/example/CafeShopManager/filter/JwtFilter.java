package com.example.CafeShopManager.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.CafeShopManager.jwt.JwtUtil;

public class JwtFilter implements Filter {

    private final JwtUtil JwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.JwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();

        // Lấy token từ session
        String token = (String) session.getAttribute("token");
        
        if (token == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"message\": \"Token does not exist or is invalid\"}");
            return;
        }

        // Loại bỏ "Bearer " nếu token có tiền tố này
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            // Kiểm tra và xác thực token
            if (JwtUtil.introspect(token)) {

                // Lấy role từ token để kiểm tra quyền
                String role = JwtUtil.getRoleFromToken(token);

                // Kiểm tra quyền truy cập dựa trên role
                if (role != null && role.equals("admin")) {
                    // Nếu là admin, cho phép truy cập
                    chain.doFilter(request, response);
                } else {
                    // Nếu không phải admin, từ chối truy cập
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("{\"message\": \"You do not have permission to access this resource\"}");
                }
            } else {
                // Token không hợp lệ
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("{\"message\": \"Token invalid\"}");
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có
            e.printStackTrace();
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.getWriter().write("{\"message\": \"Error processing token: " + e.getMessage() + "\"}");
        }
    }
}
