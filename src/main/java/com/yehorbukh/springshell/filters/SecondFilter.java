package com.yehorbukh.springshell.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

@Order(2)
@Slf4j
public class SecondFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("The second filter is logging {}", "something");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
