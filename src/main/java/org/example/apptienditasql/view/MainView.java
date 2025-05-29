package org.example.apptienditasql.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainView extends Application {

    double screenWidth = Screen.getPrimary().getBounds().getWidth();
    double screenHeight = Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("stage = " + stage);

        /*___FXML___*/
        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("main-view.fxml"));

        /*___FUENTE___*/
        InputStream fontStream = getClass().getResourceAsStream("/org/example/apptienditasql/view/fonts/LibreBarcode39-Regular.ttf");
        if (fontStream != null) {
            Font font = Font.loadFont(fontStream, 10);
            System.out.println("✅ Fuente cargada: " + font.getName());
        } else {
            System.out.println("❌ No se encontró el archivo de fuente.");
        }
/*

        ___FUENTES DEL SISTEMA___
        Font.getFamilies().forEach(System.out::println);
*/

        /*___WINDOW___*/
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Gabo's Store");
        stage.setScene(scene);
        stage.setResizable(false);

        Platform.runLater(stage::show);
    }

    public static void main(String[] args) {
        launch();
    }
}
