package com.server.orderservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.math.BigDecimal;

@HttpExchange
public interface ItemClient {
    public record Item(
            Long id,
            String name,
            Long quantity,
            BigDecimal price,
            String description,
            String image,
            String category
    ) {
        public Long getQuantity() {
            return quantity;
        }
        public String getName() {
            return name;
        }
        public Item withQuantity(Long newQuantity) {
            return new Item(this.id, this.name, newQuantity, this.price, this.description, this.image, this.category);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ", description='" + description + '\'' +
                    ", image='" + image + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
    @GetExchange("/items/{id}")
    public Item getItemById(@PathVariable Long id);

    @PutExchange("/items/{id}")
    public Item updateItem(@RequestBody Item item, @PathVariable Long id);


}


