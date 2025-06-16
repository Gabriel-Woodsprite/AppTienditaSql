package org.example.apptienditasql.utils;

import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.util.List;

import static org.example.apptienditasql.utils.UserMessage.message;

public class ControllerUtils {
	public static FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Image File");
		return fileChooser;
	}

	public static boolean validate(List<Control> requiredFields) {
		for (Control requiredField : requiredFields) {
			if (isFieldEmpty(requiredField)) {
				requiredField.setStyle("-fx-border-color: red;");
			} else {
				requiredField.setStyle("-fx-border-color: none;");
			}
		}
		for (Control requiredField : requiredFields) {
			if (isFieldEmpty(requiredField)) {
				message("Falta Información", "Debe llenar todos los campos", Alert.AlertType.WARNING);
				return true;
			}
		}
		return false;
	}

	public static boolean isFieldEmpty(Control control) {
		if (control instanceof TextInputControl) {
			return ((TextInputControl) control).getText().isBlank();
		} else if (control instanceof ComboBox<?> cb) {
			return cb.getValue() == null;
		} else if (control instanceof DatePicker dp) {
			return dp.getValue() == null;
		} else if (control instanceof Label label) {
			return label.getText().isBlank();
		}
		return false;
	}

	public static boolean isAllEmpty(List<TextField> inputs) {
		int c = 0;
		for (TextField inputField : inputs) {
			if (inputField.getText().isBlank()) {
				c++;
			}
		}
		return c == inputs.size();
	}

}
