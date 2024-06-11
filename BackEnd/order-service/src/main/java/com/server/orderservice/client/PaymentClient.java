package com.server.orderservice.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@HttpExchange
public interface PaymentClient {

    @GetExchange("/payment")
    public URI createPayment(@RequestParam(name = "priceTotal") Long priceTotal) throws UnsupportedEncodingException;
}
