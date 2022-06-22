module com.amirali.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;

    opens com.amirali.todo to javafx.fxml;
    exports com.amirali.todo;
    exports com.amirali.todo.model;
    exports com.amirali.todo.utils;
}