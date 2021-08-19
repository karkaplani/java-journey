package common;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * A set of common method for creating some GUI components.
 * This is bad practice we usually don't want to do this.
 * By using FXML we can remove this class. However, for our need
 * this is sufficient.
 * 
 * This is a utility class with bunch of static methods for creating
 * some common JavaFX nodes. 
 * 
 * @author Shariar (Shawn) Emami
 *
 */
public abstract class CommonGUIBuilder {

	private CommonGUIBuilder() {
	}
	
	public static Button createButton( String name, EventHandler< ActionEvent> onClick) {
		Button node = new Button( name);
		node.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE);
		node.setOnAction( onClick);
		GridPane.setHgrow( node, Priority.ALWAYS);
		GridPane.setVgrow( node, Priority.ALWAYS);
		return node;
	}

	public static TextField createTextField( String value, String name) {
		TextField node = new TextField( value);
		node.setPromptText( name);
		GridPane.setHgrow( node, Priority.ALWAYS);
		return node;
	}

	public static <T> ComboBox< T> createComboBox( ObservableList< T> value, String name, int selectedIndex) {
		ComboBox< T> node = new ComboBox<>( value);
		node.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE);
		node.setPromptText( name);
		node.getSelectionModel().select( selectedIndex);
		GridPane.setHgrow( node, Priority.ALWAYS);
		return node;
	}
	
	public static <T> ListView< T> createListView( ObservableList< T> list, double height){
		ListView< T> node = new ListView<>( list);
		node.setMaxHeight( height);
		GridPane.setHgrow( node, Priority.ALWAYS);
		return node;
	}

	public static PasswordField createPasswordField( String value, String name) {
		PasswordField node = new PasswordField();
		node.setText( value);
		node.setPromptText( name);
		GridPane.setHgrow( node, Priority.ALWAYS);
		return node;
	}
}
