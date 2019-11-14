package kosa.team3.gcs.service.service1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Service1Controller implements Initializable {
    @FXML private WebView service1WebView;
    private WebEngine webEngine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initWebView();
    }

    public void initWebView() {
        webEngine = service1WebView.getEngine();
        webEngine.load("http://localhost:8080/FinalWebProject/droneManagement");
    }
}
