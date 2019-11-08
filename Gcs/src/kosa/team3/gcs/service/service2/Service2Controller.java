package kosa.team3.gcs.service.service2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import kosa.team3.gcs.main.GcsMain;
import kosa.team3.gcs.network.Drone;

import java.net.URL;
import java.util.ResourceBundle;

public class Service2Controller implements Initializable {

    //Field
    @FXML private Button btnAttach;
    @FXML private Button btnDetach;
    @FXML private Button btnClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAttach.setOnAction(btnAttachEventHandler);
        btnDetach.setOnAction(btnDetachEventHandler);
        btnClose.setOnAction(btnCloseEventHandler);
    }

    private EventHandler<ActionEvent> btnAttachEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            GcsMain.instance.controller.drone.electromagnet.mqttSendToElectromagnetPool("attach");
        }
    };

    private EventHandler<ActionEvent> btnDetachEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            GcsMain.instance.controller.drone.electromagnet.mqttSendToElectromagnetPool("detach");
        }
    };

    private EventHandler<ActionEvent> btnCloseEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        }
    };


}
