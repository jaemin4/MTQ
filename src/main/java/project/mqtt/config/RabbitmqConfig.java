package project.mqtt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.mqtt.supprot.constants.RabbitmqConstants;
import project.mqtt.supprot.properties.RabbitmqProperties;

@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    private final RabbitmqProperties rabbitMqProperties;

    // === Mqtt 메세지 테스트 전용 ===
    @Bean
    public DirectExchange exchangeMqttMessage(){
        return new DirectExchange(RabbitmqConstants.EXCHANGE_MQTT_MESSAGE);
    }
    @Bean
    public Queue queueMqttMessage(){
        return new Queue(RabbitmqConstants.QUEUE_MQTT_MESSAGE, false);
    }

    @Bean
    public Binding bindingMqttMessage(Queue queueMqttMessage, DirectExchange exchangeMqttMessage){
        return BindingBuilder.bind(queueMqttMessage)
                .to(exchangeMqttMessage)
                .with(RabbitmqConstants.ROUTING_MQTT_MESSAGE);

    }


    // === 공통 RabbitMQ 설정 ===
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMqProperties.getHost());
        connectionFactory.setPort(rabbitMqProperties.getPort());
        connectionFactory.setUsername(rabbitMqProperties.getUsername());
        connectionFactory.setPassword(rabbitMqProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
