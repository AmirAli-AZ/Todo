package com.amirali.todo.utils;

import com.amirali.todo.model.Todo;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DBManager {

    private static DBManager manager;

    private final Connection connection;

    private DBManager() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + Environment.getAppFolder() + File.separator + "Todo.db");

        var statement = connection.createStatement();
        statement.executeUpdate(
                "create table if not exists todos(id integer primary key autoincrement, title text not null, description text, date long not null, done integer not null);"
        );
        statement.close();
    }

    public static DBManager getInstance() {
        if (manager == null) {
            try {
                manager = new DBManager();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return manager;
    }

    public Todo insertNewTodo(@NotNull String title, String description) {
        try {
            var statement = connection.createStatement();

            var result = statement.executeQuery(
                    "insert into todos values (null, \"" + title + "\", \"" + description + "\", " + System.currentTimeMillis() + ", 0) returning id,title,description,date;"
            );
            var todo = new Todo();
            while (result.next()) {
                todo.setId(result.getInt("id"));
                todo.setTitle(result.getString("title"));
                todo.setDescription(result.getString("description"));
                todo.setDate(result.getLong("date"));
            }
            result.close();
            statement.close();

            return todo;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Todo updateTodo(@NotNull Todo todo) {
        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(
                    "update todos set title = \"" + todo.getTitle() + "\", description = \"" + todo.getDescription() + "\", date = " + todo.getDate() + ", done = " + (todo.isDone() ? 1 : 0) + " where id == " + todo.getId()+ " returning id,title,description,date,done;"
            );
            var updatedTodo = new Todo();
            while (result.next()) {
                updatedTodo.setId(result.getInt("id"));
                updatedTodo.setTitle(result.getString("title"));
                updatedTodo.setDescription(result.getString("description"));
                updatedTodo.setDate(result.getLong("date"));
                updatedTodo.setDone(result.getInt("done") == 1);
            }
            result.close();
            statement.close();

            return updatedTodo;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Todo remove(@NotNull Todo todo) {
        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery(
                    "delete from todos where id == " + todo.getId() + " returning id,title,description,date,done;"
            );
            var removedTodo = new Todo();
            while (result.next()) {
                removedTodo.setId(result.getInt("id"));
                removedTodo.setTitle(result.getString("title"));
                removedTodo.setDescription(result.getString("description"));
                removedTodo.setDate(result.getLong("date"));
                removedTodo.setDone(result.getInt("done") == 1);
            }
            result.close();
            statement.close();

            return removedTodo;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Todo> loadTodos() {
        try {
            var statement = connection.createStatement();
            var result = statement.executeQuery("select * from todos order by date desc;");
            List<Todo> todos = new ArrayList<>();
            while (result.next()) {
                todos.add(new Todo(
                     result.getInt("id"),
                     result.getString("title"),
                     result.getString("description"),
                     result.getLong("date"),
                        result.getInt("done") == 1
                ));
            }
            result.close();
            statement.close();
            return todos;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Connection getConnection() {
        return connection;
    }
}
