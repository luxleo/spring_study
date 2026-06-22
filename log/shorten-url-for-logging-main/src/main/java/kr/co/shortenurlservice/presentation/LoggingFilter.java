package kr.co.shortenurlservice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            // 요청을 CachedBodyHttpServletRequest로 래핑
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpServletRequest);

            // URL, 메서드 및 요청 바디 로깅
            String url = wrappedRequest.getRequestURI();
            String method = wrappedRequest.getMethod();
            String body = wrappedRequest.getReader().lines().reduce("", String::concat);

            Map<String, String> requestDetails = Map.of(
                    "url", url,
                    "method", method,
                    "body", body
            );

            try {
                String jsonRequest = objectMapper.writeValueAsString(requestDetails);
                log.trace("Incoming Request: {}", jsonRequest);
            } catch (IOException e) {
                log.error("Error converting request details to JSON", e);
            }

            // 래핑된 요청 객체를 다음 필터 체인으로 전달
            chain.doFilter(wrappedRequest, response);
        } else {
            // HttpServletRequest가 아닌 경우 그대로 전달
            chain.doFilter(request, response);
        }
    }
}