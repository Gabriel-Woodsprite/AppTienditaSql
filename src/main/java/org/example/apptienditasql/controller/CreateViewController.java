package org.example.apptienditasql.controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;
import org.example.apptienditasql.utils.UserMessage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.example.apptienditasql.utils.UserMessage.message;

public class CreateViewController {
	private MainViewController mainViewController;
	ProductsDao productsDao = null;
	public SplitMenuButton categoria;
	@FXML
	private Button saveButton;
	@FXML
	private Pane rootPane;

	@FXML
	public <T> void initialize() throws SQLException {
		productsDao = new ProductsDao(DatabaseConnection.getConnection());
		saveButton.setOnAction(event -> {
			System.out.println("hola?");
			Set<Node> inputs = rootPane.lookupAll(".input-field");
			List<T> inputValues = new ArrayList<>();
			for (Node node : inputs) {
				if (node instanceof TextField tf) {
					System.out.println("TextField: " + tf.getText());
					inputValues.add((T) tf.getText());
				} else if (node instanceof RadioButton rb) {
					System.out.println("RadioButton: " + rb.isSelected());
					inputValues.add((T) rb.selectedProperty().getValue());
				} else if (node instanceof SplitMenuButton sp) {
					System.out.println("SplitMenuButton: " + sp.getId() + sp.getText());
					inputValues.add((T) sp.getText());
				} else if (node instanceof TextArea ta) {
					System.out.println("TextArea: " + ta.getText());
					inputValues.add((T) ta.getText());
				} else if (node instanceof DatePicker dp) {
					System.out.println("Date: " + dp.getValue());
					inputValues.add((T) dp.getValue());
				}
			}
			inputValues.forEach(System.out::println);

			objectParse(inputValues);
		});
	}

	private <T> void objectParse(List<T> inputValues) {
		Product product = new Product();
		product.setBarcode((String) inputValues.get(0));
		product.setName((String) inputValues.get(1));
		product.setBrand((String) inputValues.get(2));
		product.setDescription((String) inputValues.get(3));
		product.setContent((String) inputValues.get(4));
		product.setAvilable((Boolean) inputValues.get(5));
		product.setImage((String) inputValues.get(6));
		product.setCategory((String) inputValues.get(7));
		product.setMeasurementUnit((String) inputValues.get(8));
		product.setPresentation((String) inputValues.get(9));
		product.setMinStock((String) inputValues.get(10));
		product.setMaxStock((String) inputValues.get(11));
		product.setRegisterDate((LocalDate) inputValues.get(12));
		product.setExpiryDate((LocalDate) inputValues.get(13));
		product.setProductLocation((String) inputValues.get(14));
		try{
		productsDao.create(product);
			if(mainViewController != null) {
				mainViewController.insertProductList();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}
}
