package kosa.team3.gcs.service.service1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
        webEngine.getLoadWorker().stateProperty().addListener(((observable, oldValue, newValue) -> {
            JSObject window = (JSObject) webEngine.executeScript("window");
            window.setMember("java", this); //얘가 중요! webview 안에서 java. 메소드를 실행하면 this는 Service1Controller 객체 사용
        }));
        webEngine.load("http://localhost:8080/FinalWebProject/delivery");
    }

    public void windowClose() {
        Stage stage = (Stage) service1WebView.getScene().getWindow();
        stage.close();
    }
}
