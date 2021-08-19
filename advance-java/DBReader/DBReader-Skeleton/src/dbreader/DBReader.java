package dbreader;

import java.sql.SQLException;
//import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.JDBCController;
import jdbc.builder.MySQLURLBuilder;

import java.util.List;

import common.CommonGUIBuilder;

/**
 * 
 * 
 * @author Shahriar (Shawn) Emami
 */
public class DBReader extends Application {

	/**
	 * width of the scene
	 */
	private static final double WIDTH = 800;
	/**
	 * height of the scene
	 */
	private static final double HEIGHT = 600;
	/**
	 * title of the application
	 */
	private static final String TITLE = "JDBC Viewer";

	/**
	 * {@link BorderPane} is a layout manager that manages all nodes in 5 areas as below:
	 * 
	 * <pre>
	 * -----------------------
	 * |        top          |
	 * -----------------------
	 * |    |          |     |
	 * |left|  center  |right|
	 * |    |          |     |
	 * -----------------------
	 * |       bottom        |
	 * -----------------------
	 * </pre>
	 * 
	 * this object is passed to {@link Scene} object in {@link JDBCViewerV2#start(Stage)} method.
	 */
	private BorderPane root;
	private Label conectionStatus;
	private TableView< Object> table;
	private JDBCController controller;
	private ConnectDialog dialog;

	/**
	 * this method is called at the very beginning of the JavaFX application and can be used to initialize all
	 * components in the application. however, {@link Scene} and {@link Stage} must not be created in this method. this
	 * method does not run JavaFX thread, it runs on JavaFX-Launcher thread.
	 */
	@Override
	public void init() throws Exception {

		//TODO initialize the controller and set the builder to it.
		//TODO initialize the dialog.
		
		controller = new JDBCController(); 
		controller.setURLBuilder(new MySQLURLBuilder());
		dialog = new ConnectDialog(controller);

		//TableView has replaced the old WebView Node as the center of the application.
		//TODO Initialize the table variable.
		//TODO call TableView::setPlaceholder on table and pass to it a new Label with argument "No Data".
		
		table = new TableView<Object>();
		Label label = new Label("No Data");
		table.setPlaceholder(label);

		Region optionsBar = createOptionsBar();
		Region statusBar = createStatusBar();

		root = new BorderPane();
		root.setRight( optionsBar);
		root.setCenter( table);
		root.setBottom( statusBar);
	}

	/**
	 * <p>
	 * this method is called when JavaFX application is started and it is running on JavaFX thread. this method must at
	 * least create {@link Scene} and finish customizing {@link Stage}. these two objects must be on JavaFX thread when
	 * created.
	 * </p>
	 * <p>
	 * {@link Stage} represents the frame of your application, such as minimize, maximize and close buttons.<br>
	 * {@link Scene} represents the holder of all your JavaFX {@link Node}s.<br>
	 * {@link Node} is the super class of every javaFX class.
	 * </p>
	 * 
	 * @param primaryStage - primary stage of your application that will be rendered
	 */
	@Override
	public void start( Stage primaryStage) throws Exception {
		// dialog needs to be initialized in start as it needs to be on JavaFX thread.
		dialog.init();
		// scene holds all JavaFX components that need to be displayed in Stage
		Scene scene = new Scene( root, WIDTH, HEIGHT);
		primaryStage.setScene( scene);
		primaryStage.setTitle( TITLE);
		primaryStage.setResizable( true);
		// when escape key is pressed close the application
		primaryStage.addEventHandler( KeyEvent.KEY_RELEASED, ( KeyEvent event) -> {
			if ( KeyCode.ESCAPE == event.getCode()) {
				primaryStage.hide();
			}
		});
		// display the JavaFX application
		primaryStage.show();
	}

	/**
	 * this method is called at the very end when the application is about to exit. this method is used to stop or
	 * release any resources used during the application.
	 */
	@Override
	public void stop() throws Exception {
		//TODO if controller is not null close it.
		if(!controller.equals(null)) controller.close(); 
	}

	/**
	 * create a {@link VBox} that represent the right side of the application.
	 * 
	 * @return customized {@link VBox} as subclass of {@link Region}.
	 * @throws SQLException
	 */
	private Region createOptionsBar() throws SQLException {

		//To create the TextField, ComboBox, and Button use the static methods
		//from common.CommonGUIBuilder class.

		//TODO create a TextField called searchText and pass to it "" and "Search Text"
		//TODO create a ComboBox< String> called tablesCombo and pass to it controller.getTableNames(), "Table", and 0
		
		TextField searchText = CommonGUIBuilder.createTextField("", "Search Text");
		ComboBox<String> tablesCombo = CommonGUIBuilder.createComboBox(controller.getTableNames(), "Table", 0);

		//TODO on controller call tableInUseProperty() and then call bind and pass to it tablesCombo.getSelectionModel().selectedItemProperty()
		//this line of code binds the selected item in the combo box to the controllers tableInUseProperty.
		//so whenever the user changes the table selection the controller also updates.
		
		controller.tableInUseProperty().bind(tablesCombo.getSelectionModel().selectedItemProperty());

		//TODO create a Button called connectButton and pass to it "Connect" and a lambda to be executed when clicked.
		//call setText on conectionStatus pass to it "connecting".
		//call dialog.showAndWait() if the result is false :
		//call setText on conectionStatus pass to it "cancelled".
		//if the result is true:
		//call connect and then getTableNames on controller. we call getTableNames again just to update the names. the list is already connected to ComboBox.
		//call setText on conectionStatus pass to it "connected".
		
		Button connectButton = CommonGUIBuilder.createButton("Connect", a -> {
			
			conectionStatus.setText("connecting");
			
			if(dialog.showAndWait() == false) {
				this.conectionStatus.setText("cancelled");
			} 
			else {
				try {
					controller.connect().getTableNames();
					this.conectionStatus.setText("connected");
				} catch (SQLException ex) {
					this.conectionStatus.setText("failed: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		
		//don't forget connect throws an exception, so use try and catch.
		//if exception is thrown, setText on conectionStatus pass to it "failed: " + ex.getMessage() and call ex.printStackTrace();

		//TODO create a Button called searchButton and pass to it "Search" and a lambda to be executed when clicked.
		//if controller is not connected call setText on conectionStatus pass to it "must connect first" and get out.
		//call setText on conectionStatus pass to it "searching".
		//call search on controller and pass searchText.getText().trim(). store the result in a variable called list.
		//if list is not null setText on conectionStatus to "populating table" and call populateTextArea( list).
		//finally call setText on conectionStatus and pass to it "finished".
		//don't forget connect throws an exception, so use try and catch.
		//if exception is thrown, setText on conectionStatus pass to it "failed: " + ex.getMessage() and call ex.printStackTrace();

		//TODO call setOnAction on searchText and use rule 3 to execute searchButton.fire().
		
		Button searchButton = CommonGUIBuilder.createButton("Search", a -> {
			
			try {
				if(controller.isConnected() == false) {
					
					conectionStatus.setText("must connect first");
				}
				else {
					conectionStatus.setAccessibleHelp("searching");
					List< List< Object>> list = controller.search(searchText.getText().trim());
					if(list != null) {
						this.conectionStatus.setText("populating table");
						this.populateTable(list);
						this.conectionStatus.setText("finished");
					}
				}
			} catch (SQLException ex) {
				conectionStatus.setText("failed: " + ex.getMessage());
				ex.printStackTrace();
			}
		});

		VBox vbox = new VBox();
		vbox.setSpacing( 3);
		vbox.setPadding( new Insets( 0, 5, 5, 5));
		//TODO uncomment the line below to add all the Nodes to the VBox.
		vbox.getChildren().addAll( connectButton, tablesCombo, searchText, searchButton);

		return vbox;
	}

	/**
	 * create a {@link ToolBar} that will represent the status bar of the application.
	 * 
	 * @return customized {@link ToolBar} as subclass of {@link Region}.
	 */
	private Region createStatusBar() {
		conectionStatus = new Label( "Not Connected");
		return new ToolBar( conectionStatus);
	}

	private void populateTable( List< List< Object>> list) throws SQLException {
		table.getItems().clear();
		table.getItems().addAll( list);
		table.getColumns().clear();
		int i = 0;
		//TODO just read, will have to modify this for bonus.
		//this is where we add all the columns to the table.
		//for each column name provided by controller create a new TableColumn.
		//each TableColumn needs to have a CellValueFactory.
		//method data.getValue() return the list we added in second line of this method.
		for ( String col : controller.getColumnNames()) {
			TableColumn< Object, Object> tc = new TableColumn<>( col);

			int index = i;
			tc.setCellValueFactory( ( CellDataFeatures< Object, Object> data) -> new SimpleObjectProperty<>(
					( (List< ?>) data.getValue()).get( index)));
			i++;
			table.getColumns().add( tc);
		}
	}

	/**
	 * main starting point of the application
	 * 
	 * @param args - arguments provided through command line, if any
	 */
	public static void main( String[] args) {
		// launch( args); method starts the javaFX application.
		// some IDEs are capable of starting JavaFX without this method.
		launch( args);
	}
}
