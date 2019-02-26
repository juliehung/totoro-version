package io.dentall.totoro.handler;

import java.util.HashMap;
import java.util.Map;

public interface BroadcastWebSocket {

    int SETTING = 0;

    int NOTIFICATION = 1;

    String APPOINTMENT_NOT_ARRIVED = " 預約未到";

    String APPOINTMENT_COMING_SOON = " 預約即將到來";

    String REGISTRATION_PENDING = " 已掛號";

    String REGISTRATION_FINISHED = " 完成治療";

    default Map<String, Object> payloadTemplate(int type, String name, String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put("message", name + message);

        return payload;
    }

    void broadcast(Map<String, Object> payload);
}
