package kosa.team3.gcs.service.service3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Service3Controller implements Initializable {
    @FXML private Button btnOK;
    @FXML private Button btnCancel;
    @FXML private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOK.setOnAction(btnOKEventHandler);
        btnCancel.setOnAction(btnCancelEventHandler);
        initVBox();
    }

    private void initVBox() {
        try {
            HBox hboxTitle = (HBox) FXMLLoader.load(Service3.class.getResource("vbox_title.fxml"));
            vbox.getChildren().add(hboxTitle);
            for (int i = 0; i < 3; i++) {
                HBox hboxItem = (HBox) FXMLLoader.load(Service3.class.getResource("vbox_item.fxml"));
                vbox.getChildren().add(hboxItem);
                Label lblNo = (Label) hboxItem.lookup("#lblNo");
                lblNo.setText(String.valueOf(1));
                Label lblLat = (Label) hboxItem.lookup("#lblLat");
                lblLat.setText(String.valueOf(37.123456));
                Label lblLng = (Label) hboxItem.lookup("#lblLng");
                lblLng.setText(String.valueOf(125.123456));
                Button btnMap = (Button) hboxItem.lookup("#btnMap");
                btnMap.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(lblLat.getText());
                    }
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private EventHandler<ActionEvent> btnOKEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
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
