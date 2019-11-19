package kosa.team3.gcs.web;

import javafx.application.Platform;
import javafx.stage.Stage;
import kosa.team3.gcs.main.GcsMain;
import kosa.team3.gcs.service.service1.Service1Controller;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syk.gcs.dialog.AlertDialog;
import syk.gcs.mapview.FlightMapController;

public class WebNetwork {
    private static Logger logger = LoggerFactory.getLogger(WebNetwork.class);
    //Field
    private MqttClient mqttClient;
    private String pubTopic;
    private String subTopic;
    private JSONArray jsonArray;

    private Service1Controller service1Controller;
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
                String msgid = jsonObject.getString("msgid");
                if(msgid.equals("path")) {
                    JSONArray path = jsonObject.getJSONArray("path");
                    GcsMain.instance.controller.flightMap.controller.setPath(path);
                    GcsMain.instance.controller.flightMap.controller.showInfoLabel("경로 불러오기 성공");
                } else if(msgid.equals("requestDrone")) {
                    logger.info("실행");
                    alertMessage(msgid);
                } else if(msgid.equals("requestDelivery")) {
                    alertMessage(msgid);
                } else if(msgid.equals("attachFinish")) {
                    alertMessage(msgid);
                } else if(msgid.equals("requestCancel")) {
                    alertMessage(msgid);
                }

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

    public void alertMessage(String msgid) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(msgid.equals("requestDrone")) {
                    AlertDialog.showOkButton("알림", "드론 요청이 들어왔습니다.");
                } else if(msgid.equals("requestDelivery")) {
                    AlertDialog.showOkButton("알림", "배송 요청이 들어왔습니다.");
                } else if(msgid.equals("attachFinish")) {
                    AlertDialog.showOkButton("알림", "우편 박스 부착을 완료하였습니다.");
                } else if(msgid.equals("requestCancel")) {
                    AlertDialog.showOkButton("알림", "드론 요청이 취소되었습니다.");
                }
            }
        });
    }

}
