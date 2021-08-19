module dbreader {

	opens dbreader to javafx.graphics;

	requires java.desktop;
	requires java.sql;
	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.web;
}