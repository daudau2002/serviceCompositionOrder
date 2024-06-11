package com.server.orderservice.controller;

import com.server.orderservice.client.ItemClient;
import com.server.orderservice.client.NotifiClient;
import com.server.orderservice.client.PaymentClient;
import com.server.orderservice.client.UserClient;
import com.server.orderservice.dto.OrderDTO;
import com.server.orderservice.dto.OrderItemDTO;
import com.server.orderservice.service.impl.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RequestMapping("/orders")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private NotifiClient notifiClient;

    @Autowired
    private PaymentClient paymentClient;

    @Operation(summary = "Get an order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders"),
            @ApiResponse(responseCode = "404", description = "Orders not found")
    })
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
    @Operation(summary = "Delete an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @Operation(summary = "Update an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Invalid order supplied")
    })
    @PutMapping
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

    @Operation(summary = "Update an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid item supplied")
    })
    @PutMapping("/items/{id}")
    public void updateItem(@RequestBody ItemClient.Item item, @PathVariable Long id) {
        itemClient.updateItem(item, id);
    }

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order supplied"),
            @ApiResponse(responseCode = "404", description = "User not found or item does not exist or out of stock"),
            @ApiResponse(responseCode = "500", description = "Failed to add order due to internal server error")
    })
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderDTO order) {
        try {
            List<OrderItemDTO> orderItems = order.getOrderItems();
            for (OrderItemDTO orderItem : orderItems) {
                if (!checkItemExist(orderItem.getItemId(), orderItem.getQuantity())) {
                    ItemClient.Item item = itemClient.getItemById(orderItem.getItemId());
                    return new ResponseEntity<>("Item with name " + item.getName() + " does not exist or out of stock", HttpStatus.BAD_REQUEST);
                }
            }
            delay(2);
            notifiClient.notiItemFinish();


            ResponseEntity<String> userCheckResponse = checkInformationUser(order.getUserId());
            if (!userCheckResponse.getStatusCode().equals(HttpStatus.OK)) {
                return userCheckResponse;
            }
            delay(2);
            notifiClient.notiUserFinish();

//            BigDecimal amount = order.getPriceTotal();
//
//            URI uriPayment = paymentClient.createPayment(amount.longValue());


            OrderDTO newOrder = orderService.addOrder(order);

            if (newOrder == null) {
                return new ResponseEntity<>("Failed to add order", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                delay(2);
                notifiClient.notiOrderFinish();
            }

            for (OrderItemDTO orderItem : orderItems) {
                ItemClient.Item existingItem = itemClient.getItemById(orderItem.getItemId());
                if (existingItem != null) {
                    Long newQuantity = existingItem.getQuantity() - orderItem.getQuantity();
                    ItemClient.Item updatedItem = existingItem.withQuantity(newQuantity);
                    itemClient.updateItem(updatedItem, orderItem.getItemId());
                }
            }

            String userEmail = userClient.getUserById(order.getUserId()).getEmail();
            String emailBody = "<!DOCTYPE html><html lang='en'><head><meta " +
                    "charset='UTF-8'><meta name='viewport' content='width=device-width, " +
                    "initial-scale=1.0'><title>Thông báo Đặt hàng Thành công</title><style>" +
                    "body{font-family:Arial,sans-serif;background-color:#f4f4f4;margin:0;paddin" +
                    "g:0;}.container{max-width:600px;margin:20px auto;padding:20px;background-c" +
                    "olor:#fff;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.1);}.container " +
                    "h1{color:#333;text-align:center;}.container p{color:#555;font-size:16px;" +
                    "line-height:1.6;margin-bottom:20px;}.button{display:inline-block;" +
                    "padding:10px 20px;background-color:#007bff;color:#fff;text-decoration:none;" +
                    "border-radius:5px;font-size:16px;}.button:hover{background-color:#0056b3;}" +
                    ".illustration{display:block;margin:0 auto;width:80%;max-width:500px;}" +
                    "</style></head><body><div class='container'>" +
                    "<img src='https://reviewgiaoduc.com/public/files/upload/images/Tham-khao-nhung-loi-cam-on-bang-tieng-Anh-hay-nhat.jpg' alt='Thank You Illustration' class='illustration'><h1>Thông báo Đặt hàng Thành công" +
                    "</h1><p>Xin chào,</p><p>Cảm ơn bạn đã đặt hàng của chúng tôi. Đơn hàng của bạn đã được xác nhận và đang được xử lý.</p><p>Mã đơn hàng của bạn là: <strong>#123456</strong>" +
                    "</p><p>Chúng tôi sẽ thông báo cho bạn khi đơn hàng của bạn được vận chuyển.</p>" +
                    "<p>Xin vui lòng liên hệ với chúng tôi nếu bạn có bất kỳ câu hỏi hoặc yêu cầu nào." +
                    "</p><p>Xin cảm ơn!</p><p><a href='#' class='button'>Trang chủ</a></p></div></body></html>";
            NotifiClient.Email email = new NotifiClient.Email(
                    userEmail,
                    "Thông báo Đặt hàng Thành công",
                    emailBody
            );

            notifiClient.sendEmail(email);
            delay(2);
            notifiClient.notiNotifiFinish();

            return new ResponseEntity<>("Hoàn tất đặt hàng thành công", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/end")
//    public ResponseEntity<String> addOrderAfterPaySuccess(@RequestBody OrderDTO order) {
//        List<OrderItemDTO> orderItems = order.getOrderItems();
//        OrderDTO newOrder = orderService.addOrder(order);
//        for (OrderItemDTO orderItem : orderItems) {
//            ItemClient.Item existingItem = itemClient.getItemById(orderItem.getItemId());
//            if (existingItem != null) {
//                Long newQuantity = existingItem.getQuantity() - orderItem.getQuantity();
//                ItemClient.Item updatedItem = existingItem.withQuantity(newQuantity);
//                itemClient.updateItem(updatedItem, orderItem.getItemId());
//            }
//        }
//        String userEmail = userClient.getUserById(order.getUserId()).getEmail();
//        String emailBody = "<!DOCTYPE html><html lang='en'><head><meta " +
//                "charset='UTF-8'><meta name='viewport' content='width=device-width, " +
//                "initial-scale=1.0'><title>Thông báo Đặt hàng Thành công</title><style>" +
//                "body{font-family:Arial,sans-serif;background-color:#f4f4f4;margin:0;paddin" +
//                "g:0;}.container{max-width:600px;margin:20px auto;padding:20px;background-c" +
//                "olor:#fff;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.1);}.container " +
//                "h1{color:#333;text-align:center;}.container p{color:#555;font-size:16px;" +
//                "line-height:1.6;margin-bottom:20px;}.button{display:inline-block;" +
//                "padding:10px 20px;background-color:#007bff;color:#fff;text-decoration:none;" +
//                "border-radius:5px;font-size:16px;}.button:hover{background-color:#0056b3;}" +
//                ".illustration{display:block;margin:0 auto;width:80%;max-width:500px;}" +
//                "</style></head><body><div class='container'>" +
//                "<img src='https://reviewgiaoduc.com/public/files/upload/images/Tham-khao-nhung-loi-cam-on-bang-tieng-Anh-hay-nhat.jpg' alt='Thank You Illustration' class='illustration'><h1>Thông báo Đặt hàng Thành công" +
//                "</h1><p>Xin chào,</p><p>Cảm ơn bạn đã đặt hàng của chúng tôi. Đơn hàng của bạn đã được xác nhận và đang được xử lý.</p><p>Mã đơn hàng của bạn là: <strong>#123456</strong>" +
//                "</p><p>Chúng tôi sẽ thông báo cho bạn khi đơn hàng của bạn được vận chuyển.</p>" +
//                "<p>Xin vui lòng liên hệ với chúng tôi nếu bạn có bất kỳ câu hỏi hoặc yêu cầu nào." +
//                "</p><p>Xin cảm ơn!</p><p><a href='#' class='button'>Trang chủ</a></p></div></body></html>";
//        NotifiClient.Email email = new NotifiClient.Email(
//                userEmail,
//                "Thông báo Đặt hàng Thành công",
//                emailBody
//        );
//        notifiClient.sendEmail(email);
//        return new ResponseEntity<>("Hoàn tất đặt hàng thành công", HttpStatus.OK);
//    }

    public  boolean checkItemExist(Long id, Long n) {
        try {
            ItemClient.Item item = itemClient.getItemById(id);
            return item != null && item.getQuantity() > 0  && n <= item.getQuantity();
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> checkInformationUser(Long id) {
        try {
            UserClient.User user = userClient.getUserById(id);

            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            if (user.getName() == null || user.getName().isEmpty()) {
                return new ResponseEntity<>("User name is missing", HttpStatus.BAD_REQUEST);
            }

            if (user.getPhone() == null || user.getPhone().isEmpty()) {
                return new ResponseEntity<>("User phone is missing", HttpStatus.BAD_REQUEST);
            }

            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return new ResponseEntity<>("User email is missing", HttpStatus.BAD_REQUEST);
            }

            if ("offline".equals(user.getStatus()) || user.getStatus().isEmpty()) {
                return new ResponseEntity<>("User status is missing", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("User information is valid", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to validate user information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void delay(int second){
        try {
            Thread.sleep(second* 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

