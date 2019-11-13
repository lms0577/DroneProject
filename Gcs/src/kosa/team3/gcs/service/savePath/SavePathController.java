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
    @FXML private ChoiceBox choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setOnAction(btnSaveEventHandler);
        btnCancel.setOnAction(btnCancelEventHandler);
    }

    private String vid;
    private EventHandler<ActionEvent> btnSaveEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String vname = choiceBox.getValue().toString();
            if(vname.equals("관우 마을")) {
                vid = "v001";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(vid, jsonArray);
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
            }else if(vname.equals("인성 마을")) {
                vid = "v002";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(vid, jsonArray);
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
            } else {
                vid = "v003";
                logger.info(vid);
                JSONArray jsonArray = GcsMain.instance.controller.drone.webNetwork.getJsonArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(vid, jsonArray);
                GcsMain.instance.controller.drone.webNetwork.mqttSendToWebPool(jsonObject);
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
