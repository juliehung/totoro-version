package io.dentall.totoro.config;

import io.dentall.totoro.handler.SettingWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SettingWebSocketHandler(), "/topic/setting").setAllowedOrigins("*");
    }
}
