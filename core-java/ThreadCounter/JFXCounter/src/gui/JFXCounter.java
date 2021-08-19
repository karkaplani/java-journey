package gui;

import java.util.LinkedList;

import binding.BindingManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class JFXCounter extends Application {

	/**
	 * width of the scene
	 */
	private static final double WIDTH = 500;
	/**
	 * height of the scene
	 */
	private static final double HEIGHT = 400;
	/**
	 * title of the application
	 */
	private static final String TITLE = "JavaFX Counter";

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
	 * this object is passed to {@link Scene} object in {@link JFXCounter#start(Stage)} method.
	 */
	private BorderPane root;
	private BindingManager manager;
	private ListView< CounterPanel> listView;
	private ObservableList< CounterPanel> counters;

	/**
	 * this method is called at the very beginning of the JavaFX application and can be used to initialize all
	 * components in the application. however, {@link Scene} and {@link Stage} must not be created in this method. this
	 * method does not run JavaFX thread, it runs on JavaFX-Launcher thread.
	 */
	@Override
	public void init() throws Exception {

		counters = FXCollections.observableList( new LinkedList<>());

		manager = new BindingManager();

		Region optionsBar = createOptionsBar();
		Region center = createCenter();

		root = new BorderPane();
		root.setTop( optionsBar);
		root.setCenter( center);
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
		manager.shutdown();
	}

	private Region createCenter() {
		listView = new ListView<>( counters);
		listView.setCellFactory( v -> new CounterCell());

		return listView;
	}

	/**
	 * create a {@link MenuBar} that represent the menu bar at the top of the application.
	 * 
	 * @return customized {@link MenuBar} as its super class {@link Region}.
	 */
	private Region createOptionsBar() {
		MenuItem addCounter = new MenuItem( "Add Counter");
		addCounter.setOnAction( e -> {
			CounterPanel cp = new CounterPanel();
			cp.setBinding( manager.getNewBinding());
			counters.add( cp);
		});

		Menu remove = new Menu( "Remove");

		counters.addListener( ( Change< ? extends CounterPanel> c) -> {
			while ( c.next() && c.wasAdded()) {
				CounterPanel cp = c.getAddedSubList().get( 0);
				MenuItem item = new MenuItem( cp.toString());
				item.setOnAction( e -> {
					cp.stop();
					counters.remove( cp);
					remove.getItems().remove( item);
				});
				remove.getItems().add( item);
			}
		});

		Menu optionsMenu = new Menu( "Options");
		optionsMenu.getItems().addAll( addCounter, remove);

		return new MenuBar( optionsMenu);
	}

	private class CounterCell extends ListCell< CounterPanel> {

		@Override
		protected void updateItem( CounterPanel item, boolean empty) {
			super.updateItem( item, empty);
			setText( null);
			if ( empty)
				setGraphic( null);
			else
				setGraphic( item.getPanel());
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
