package propertyexample;

import java.util.function.BiConsumer;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyExample {

	public static void main( String[] args) {
		//simple lambda to print the value and state of a property
		BiConsumer< String, Property< ?>> printProperty = ( name, p) -> System.out
				.printf( "%s:\tState: %s%n\tValue: %s%n", name, p, p.getValue());

		//create a StringProperty using SimpleStringProperty.
		//there are equivalent versions for int, double, object, and etc.
		StringProperty p1 = new SimpleStringProperty( "Hello from P1");
		StringProperty p2 = new SimpleStringProperty( "Hello from P2");

		//print the basic state of each property
		System.out.println( "before binding two properties, each have their own value");
		printProperty.accept( "P1", p1);
		printProperty.accept( "P2", p2);
		System.out.println();

		//p1 is binded to p2, meaning p1 will reflect p2. one directional.
		p1.bind( p2);

		//print the state of each property
		System.out.println( "after binding p1 to p2, p1 now show the value of p2");
		printProperty.accept( "P1", p1);
		printProperty.accept( "P2", p2);
		System.out.println();

		//change the value of p2
		p2.set( "P2 Changed");

		//print the state of each property
		System.out.println( "after changing p2, p1 now shows the same value as changed p2");
		printProperty.accept( "P1", p1);
		printProperty.accept( "P2", p2);
		System.out.println();

		//p1 cannot be changed anymore as it is just the reflection of p2.
		//if p1 is changed a RuntimeException is thrown.
		try {
			p1.set( "P1 Changed");
		} catch ( RuntimeException ex) {
			System.err.printf( "ERROR - if p1 is binded to p2, p1 cannot be changed directly%n%n");
		}

		//we can also add listeners to be executed if the value of the property is changed.
		//this will work on the bound (p1) or binding (p2) property.
		p1.addListener( ( value, oldValue, newValue) -> System.out.printf( "P1:\tChanging from \"%s\" to \"%s\"%n",
				oldValue, newValue));
		p2.addListener( ( value, oldValue, newValue) -> System.out.printf( "P2:\tChanging from \"%s\" to \"%s\"%n",
				oldValue, newValue));

		//change the value of p2
		p2.set( "P2 Changed again");
	}
}
