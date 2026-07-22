package project.mqtt.supprot.constants;

public final class RabbitmqConstants {
    //AccessLog
    public static final String EXCHANGE_ACCESS_LOG = "exchange.access.log";
    public static final String QUEUE_ACCESS_LOG_SAVE = "queue.access.log.save";
    public static final String ROUTING_ACCESS_LOG_SAVE = "route.access.log.save";

    //MqttMessage
    public static final String EXCHANGE_MQTT_MESSAGE = "exchange.mqtt.message";
    public static final String QUEUE_MQTT_MESSAGE = "queue.mqtt.message";
    public static final String ROUTING_MQTT_MESSAGE = "routing.mqtt.message";

    private RabbitmqConstants() {
    }
}
