package kosa.team3.gcs.service.service2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kosa.team3.gcs.main.GcsMain;

public class Service2 {
    //Field
    private Stage dialog;

    //Constructor
    public Service2() {
        try{
            dialog = new Stage(StageStyle.UTILITY);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(GcsMain.instance.primaryStage);
            BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("Service2.fxml"));
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add(GcsMain.class.getResource("style_dark.css").toExternalForm());
            dialog.setScene(scene);
            dialog.setResizable(false);
        } catch (Exception e) {}
    }

    //Method
    public void show() {
        dialog.show();
    }

}
