package com.server.orderservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {
    public record User(
            Long id,
            String name,
            String email,
            String password,
            String role,
            String status,
            String phone

    ) {
        public String getName() {return name; }
        public String getEmail() {return email; }

        public String getPhone() {return phone; }
        public Long getId() {return id; }

        public String getStatus() {
            return status;
        }

    }
    @GetExchange("/users/{id}")
    public User getUserById(@PathVariable Long id);
}
