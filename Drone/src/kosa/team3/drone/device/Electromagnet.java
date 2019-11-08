package kosa.team3.drone.device;

import com.pi4j.io.gpio.RaspiPin;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kosa.team3.drone.device.actuator.ElectroMagnet;

public class Electromagnet {
    private static Logger logger = LoggerFactory.getLogger(Electromagnet.class);
    //Field
    private MqttClient mqttClient;
    private String pubTopic;
    private String subTopic;
    private ElectroMagnet electroMagnet1 = new ElectroMagnet(RaspiPin.GPIO_24);
    private ElectroMagnet electroMagnet2 = new ElectroMagnet(RaspiPin.GPIO_25);
    private String status = "detach";
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
        mqttReceiveFromGcs();
        mqttSendToGcs();
    }

    public void mqttReceiveFromGcs() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                byte[] arr = mqttMessage.getPayload();
                String json = new String(arr);
                System.out.println(json);
                JSONObject jsonObject = new JSONObject(json);
                String action = jsonObject.getString("action");
                if(action.equals("attach")) {
                    electroMagnet1.attach();
                    electroMagnet2.attach();
                    status = electroMagnet1.getStatus();
                    /*Thread thread = new Thread() {
                        @Override
                        public void run() {
                            mqttSendToGcs(status);
                        }
                    };
                    thread.start();*/
                } else if(action.equals("detach")){
                    electroMagnet1.detach();
                    electroMagnet2.detach();
                    status = electroMagnet1.getStatus();
                    /*Thread thread = new Thread() {
                        @Override
                        public void run() {
                            mqttSendToGcs(status);
                        }
                    };
                    thread.start();*/
                }
            }
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
        });

        try {
            mqttClient.subscribe(subTopic);
            logger.info("Electromagnet MQTT subscribed: " + subTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mqttSendToGcs() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("status", status);
                        String json = jsonObject.toString();
                        mqttClient.publish(pubTopic, json.getBytes(), 0, false);
                        logger.info("Electromagnet MQTT publish: " + pubTopic);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

    }
}
