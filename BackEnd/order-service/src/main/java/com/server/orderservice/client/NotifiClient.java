package com.server.orderservice.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface NotifiClient {
    public record Email(
            String to,
            String subject,
            String text
    ) {
        public String getTo() {
            return to;
        }
        public String getSubject() {
            return subject;
        }
        public String getBody() {
            return text;
        }
    }
    @PostExchange("/notifi/send-email")
    public void sendEmail(@RequestBody  Email email);

    @GetExchange("/notifi/user-finish")
    public void notiUserFinish();

    @GetExchange("/notifi/item-finish")
    public void notiItemFinish();

    @GetExchange("/notifi/order-finish")
    public void notiOrderFinish();

    @GetExchange("/notifi/notifi-finish")
    public void notiNotifiFinish();
}
