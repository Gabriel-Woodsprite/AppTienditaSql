package org.example.apptienditasql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;
import org.example.apptienditasql.view.MainView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainViewController {
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
//			loadTabContent(mainTabPane.getTabs().get(2), "/org/example/apptienditasql/view/tab-inventario.fxml");
//			loadTabContent(mainTabPane.getTabs().get(3), "/org/example/apptienditasql/view/tab-clientes.fxml");
			loadTabContent(mainTabPane.getTabs().get(4), "/org/example/apptienditasql/view/tab-proveedores.fxml");
//			loadTabContent(mainTabPane.getTabs().get(5), "/org/example/apptienditasql/view/tab-reportes.fxml");
			loadTabContent(mainTabPane.getTabs().get(6), "/org/example/apptienditasql/view/tab-configuracion.fxml");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}