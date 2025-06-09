package org.example.apptienditasql.utils;

import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class createProductUtil {
	public static FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Image File");
		return fileChooser;
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

}
