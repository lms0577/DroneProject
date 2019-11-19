package kosa.team3.gcs.service.savePath;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import kosa.team3.gcs.main.GcsMain;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SavePathController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(SavePathController.class);
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private ChoiceBox choiceBox1;
    @FXML private ChoiceBox choiceBox2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setOnAction(btnSaveEventHandler);
        btnCancel.setOnAction(btnCancelEventHandler);
    }

    private String vid;
    private String vpathid;
    private EventHandler<ActionEvent> btnSaveEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String vname = choiceBox1.getValue().toString();
            String vpath = choiceBox2.getValue().toString();
            if(vname.equals("관우 마을")) {
                vid = "v001";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("vid", vid);
                jsonObject.put("path", jsonArray);
                if(vpath.equals("이동 경로")) {
                    vpathid = "sendPath";
                    jsonObject.put("vpathid", vpathid);
                } else {
                    vpathid = "returnPath";
                    jsonObject.put("vpathid", vpathid);
                }
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }else if(vname.equals("인성 마을")) {
                vid = "v002";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("vid", vid);
                jsonObject.put("path", jsonArray);
                if(vpath.equals("이동 경로")) {
                    vpathid = "sendPath";
                    jsonObject.put("vpathid", vpathid);
                } else {
                    vpathid = "returnPath";
                    jsonObject.put("vpathid", vpathid);
                }
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            } else {
                vid = "v003";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("vid", vid);
                jsonObject.put("path", jsonArray);
                if(vpath.equals("이동 경로")) {
                    vpathid = "sendPath";
                    jsonObject.put("vpathid", vpathid);
                } else {
                    vpathid = "returnPath";
                    jsonObject.put("vpathid", vpathid);
                }
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
        }
    };

    private EventHandler<ActionEvent> btnCancelEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }
    };
}
