package org.example.apptienditasql.unitTests;

import javafx.stage.FileChooser;

import java.io.File;

public class imageUnitTest {
	public static void main(String[] args) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Image File");
		File file = fileChooser.showOpenDialog(null);
	}
}
