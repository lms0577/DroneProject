package kosa.team3.gcs.service.service2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kosa.team3.gcs.main.GcsMain;
import kosa.team3.gcs.network.Drone;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class Service2Controller implements Initializable{

    //Field
    @FXML private Button btnAttach;
    @FXML private Button btnDetach;
    @FXML private Button btnClose;
    @FXML private Label ElectromagnetStatus;
    private String status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getElectromagnetStatus();
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

    private void getElectromagnetStatus() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        status = GcsMain.instance.controller.drone.electromagnet.getStatus();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ElectromagnetStatus.setText(status);
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }




}
