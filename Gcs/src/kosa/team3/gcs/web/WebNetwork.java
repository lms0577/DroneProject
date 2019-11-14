package kosa.team3.gcs.web;

import kosa.team3.gcs.main.GcsMain;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syk.gcs.mapview.FlightMapController;

public class WebNetwork {
    private static Logger logger = LoggerFactory.getLogger(WebNetwork.class);
    //Field
    private MqttClient mqttClient;
    private String pubTopic;
    private String subTopic;
    private JSONArray jsonArray;
    //Constructor

    //method
    public void mqttConnect(String mqttBrokerConnStr, String pubTopic, String subTopic) {
        this.pubTopic = pubTopic;
        this.subTopic = subTopic;
        while(true) {
            try {
                mqttClient = new MqttClient(mqttBrokerConnStr, MqttClient.generateClientId(), null);
                mqttClient.connect();
                logger.info("WebNetwork MQTT Connected: " + mqttBrokerConnStr);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mqttReceiveFromWebPool();
    }

    public void mqttDisconnect() {
        try {
            mqttClient.disconnect();
            mqttClient.close();
            mqttClient = null;
        } catch (Exception e) {
        }
    }

    public void mqttReceiveFromWebPool() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                logger.info("실행");
                byte[] bytes = mqttMessage.getPayload();
                String json = new String(bytes);
                JSONObject jsonObject = new JSONObject(json);
                JSONArray path = jsonObject.getJSONArray("path");
                GcsMain.instance.controller.flightMap.controller.setPath(path);
            }
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
        });

        try {
            mqttClient.subscribe(pubTopic);
            logger.info("WebNetwork MQTT subscribed: " + pubTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mqttSendToWebPool(JSONObject jsonObject) {
        try{
            String json = jsonObject.toString();
            mqttClient.publish(subTopic, json.getBytes(), 0, false);
            logger.info("WebNetwork MQTT publish: " + subTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

}
