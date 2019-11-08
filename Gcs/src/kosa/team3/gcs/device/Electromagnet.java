package kosa.team3.gcs.device;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Electromagnet {
    private static Logger logger = LoggerFactory.getLogger(Electromagnet.class);
    //Field
    private MqttClient mqttClient;
    private String pubTopic;
    private String subTopic;
    //Constructor

    //method
    public void mqttConnect(String mqttBrokerConnStr, String pubTopic, String subTopic) {
        this.pubTopic = pubTopic;
        this.subTopic = subTopic;
        while(true) {
            try {
                mqttClient = new MqttClient(mqttBrokerConnStr, MqttClient.generateClientId(), null);
                mqttClient.connect();
                logger.info("Electromagnet MQTT Connected: " + mqttBrokerConnStr);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mqttReceiveFromElectromagnetPool();
    }

    public void mqttDisconnect() {
        try {
            mqttClient.disconnect();
            mqttClient.close();
            mqttClient = null;
        } catch (Exception e) {
        }
    }

    public void mqttReceiveFromElectromagnetPool() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                byte[] arr = mqttMessage.getPayload();
                String json = new String(arr);
                JSONObject jsonObject = new JSONObject(json);
                String status = jsonObject.getString("status");
                logger.info(status);
            }
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
        });

        try {
            mqttClient.subscribe(pubTopic);
            logger.info("Electromagnet MQTT subscribed: " + pubTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mqttSendToElectromagnetPool(String action) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("action", action);
            String json = jsonObject.toString();
            mqttClient.publish(subTopic, json.getBytes(), 0, false);
            logger.info("Electromagnet MQTT publish: " + subTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
