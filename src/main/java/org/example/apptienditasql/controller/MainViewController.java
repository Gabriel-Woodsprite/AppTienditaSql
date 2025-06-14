package org.example.apptienditasql.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainViewController {
	Logger logger = Logger.getLogger(MainViewController.class.getName());
	@FXML
	private TabPane mainTabPane;

	private void loadTabContent(Tab tab, String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		Parent content = loader.load();
		tab.setContent(content);
	}

	@FXML
	public void initialize() {
		try {
			loadTabContent(mainTabPane.getTabs().get(1), "/org/example/apptienditasql/view/tab-catalogo.fxml");
			loadTabContent(mainTabPane.getTabs().get(0), "/org/example/apptienditasql/view/tab-venta.fxml");
			loadTabContent(mainTabPane.getTabs().get(2), "/org/example/apptienditasql/view/tab-inventario.fxml");
//			loadTabContent(mainTabPane.getTabs().get(3), "/org/example/apptienditasql/view/tab-clientes.fxml");
			loadTabContent(mainTabPane.getTabs().get(4), "/org/example/apptienditasql/view/tab-proveedores.fxml");
//			loadTabContent(mainTabPane.getTabs().get(5), "/org/example/apptienditasql/view/tab-reportes.fxml");
			loadTabContent(mainTabPane.getTabs().get(6), "/org/example/apptienditasql/view/tab-configuracion.fxml");
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
}