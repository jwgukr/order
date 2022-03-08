package mall;

import mall.config.kafka.KafkaProcessor;

import java.util.Optional;

import javax.persistence.PostUpdate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShipped_UpdateStatus(@Payload Shipped shipped){

        if(shipped.isMe()){
            //Optional<Order> optionalOrder = orderRepository.findById(shipped.getOrderId());
            Optional<Order> optionalOrder = orderRepository.findById(shipped.getOrderId());
            Order order = optionalOrder.get();

            order.setStatus(shipped.getStatus());
            orderRepository.save(order);
        }
    }

    @PostUpdate
    public void onPostUpdate() {
        //.........
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCancelled_UpdateStatus(@Payload DeliveryCancelled deliveryCancelled){

        if(deliveryCancelled.isMe()){
            System.out.println("##### listener UpdateStatus : " + deliveryCancelled.toJson());
            System.out.println();
            System.out.println();
        }
    }

}
