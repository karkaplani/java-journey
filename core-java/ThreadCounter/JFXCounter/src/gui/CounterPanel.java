package gui;

import binding.CounterBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CounterPanel {

	private static int counterCount = 0;

	private Pane pane;

	private String counterName;
	private String count;

	private CounterBinding binding;

	public CounterPanel() {
		this( "Counter " + ++counterCount);
	}

	public CounterPanel( String name) {
		counterName = name + ": ";
		count = "00:00:00";
	}

	public CounterPanel init() {
		pane = new HBox( 5);
		//		pane.setStyle( "-fx-background-color:PINK");
		Button start = new Button( "Start");
		start.setOnAction( e -> binding.start());
		Button pause = new Button( "Pause");
		pause.setOnAction( e -> binding.pause());
		Button stop = new Button( "Stop");
		stop.setOnAction( e -> binding.stop());
		Label timer = new Label( count);
		timer.textProperty().bind( binding.timeProperty());
		timer.setMaxSize( 1000, 1000);
		HBox.setHgrow( timer, Priority.ALWAYS);
		Label name = new Label( counterName);
		name.setMaxSize( 1000, 1000);
		VBox.setVgrow( name, Priority.ALWAYS);
		pane.getChildren().addAll( name, timer, pause, start, stop);

		return this;
	}

	public Pane getPanel() {
		if ( pane == null)
			init();
		return pane;
	}

	public void stop() {
		binding.stop();
	}

	@Override
	public String toString() {
		return counterName + ": " + count;
	}

	public CounterPanel setBinding( CounterBinding counterBinding) {
		binding = counterBinding;
		return this;
	}
}
