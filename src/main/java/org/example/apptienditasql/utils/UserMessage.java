package org.example.apptienditasql.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class UserMessage {
	public static Alert message(String titulo, String message, Alert.AlertType alertType){
		Alert alerta = new Alert(alertType);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(message);
		alerta.showAndWait();
		return alerta;
	}
	public static String userInput(String titulo, String message){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(titulo);
		dialog.setHeaderText(null);
		dialog.setContentText(message);

		dialog.showAndWait();
		return dialog.getEditor().getText();
	}
}
