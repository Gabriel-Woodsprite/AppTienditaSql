package org.example.apptienditasql.utils;

import javafx.stage.FileChooser;

import java.io.File;

public class fileChooserCreator {
	public static FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Image File");
		return fileChooser;
	}
}
