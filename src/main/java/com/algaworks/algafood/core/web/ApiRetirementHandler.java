package com.algaworks.algafood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiRetirementHandler extends HandlerInterceptorAdapter {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        if (request.getRequestURI().startsWith("/v1/")) {
//            response.setStatus(HttpStatus.GONE.value());
//            return false;
//        }
//        return true;
//    }

}
