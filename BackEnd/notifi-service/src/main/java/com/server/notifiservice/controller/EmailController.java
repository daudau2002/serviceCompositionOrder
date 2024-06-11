package com.server.notifiservice.controller;

import com.server.notifiservice.dto.Email;
import com.server.notifiservice.service.EmailService;
import com.server.notifiservice.websocket.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/notifi")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Send an email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied")
    })
    @PostMapping("/send-email")
    public void sendEmail(@RequestBody Email email) {
        emailService.sendEmail(email);
    }


    @Autowired
    WebSocketService webSocketService;

    @GetMapping("/user-finish")
    public void notiUserFinish(){
        webSocketService.sendDataToClient("/topic","ok");
    }

    @GetMapping("/item-finish")
    public void notiItemFinish(){
        webSocketService.sendDataToClient("/topic","ok");
        System.out.println("siuuu");
    }

    @GetMapping("/order-finish")
    public void notiOrderFinish(){
        webSocketService.sendDataToClient("/topic","ok");
    }

    @GetMapping("/notifi-finish")
    public void notiNotifiFinish(){
        webSocketService.sendDataToClient("/topic","ok");
    }
}