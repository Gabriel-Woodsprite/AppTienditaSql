module org.example.apptienditasql {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires jdk.compiler;
	requires java.desktop;

	exports org.example.apptienditasql.controller;
	opens org.example.apptienditasql.controller to javafx.fxml;
	exports org.example.apptienditasql.view;
	opens org.example.apptienditasql.view to javafx.fxml;
}