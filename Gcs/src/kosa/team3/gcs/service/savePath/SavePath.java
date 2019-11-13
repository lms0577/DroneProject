package kosa.team3.gcs.service.savePath;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kosa.team3.gcs.main.GcsMain;

public class SavePath {
    private Stage dialog;

    public SavePath() {
        try{
            dialog = new Stage(StageStyle.UTILITY);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(GcsMain.instance.primaryStage);
            BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("SavePath.fxml"));
            Scene scene = new Scene(borderPane);
            scene.getStylesheets().add(GcsMain.class.getResource("style_dark.css").toExternalForm());
            dialog.setScene(scene);
            dialog.setTitle("경로 저장");

            //dialog.setWidth(500);
            //dialog.setHeight(500);
            dialog.setResizable(false);
        } catch (Exception e) {}
    }

    public void show() {
        dialog.show();
    }
}
