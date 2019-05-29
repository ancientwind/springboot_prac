package com.wayne.sbt2.messaging;
import com.wayne.sbt2.domain.Landmark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author 212331901
 * @date 2018/12/20
 */
public class LandmarkMQClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandmarkMQClient.class);

    @Value("${logging.private.tag}")
    private String logTag;

    @Value("${rabbitmq.firmware.tx}")
    private String firmwareTX;

    @Value("${rabbitmq.firmware.tx.landmark}")
    private String firmwareLandmarkKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * to temporarily store the landmark which is sent to firmware to confirm
     */
    private Landmark landmarkSentOut;

    @RabbitListener(queues = "#{anonyPredictedLandmarkQueue.name}")
    public void receive(Landmark predictedLandmark) {
        if (predictedLandmark != null) {
            this.landmarkSentOut = predictedLandmark;
        }
    }

    public void sendOutConfirmedLandmark() {
        rabbitTemplate.convertAndSend(firmwareTX, firmwareLandmarkKey, landmarkSentOut);
    }

}
