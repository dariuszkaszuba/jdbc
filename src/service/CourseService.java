package service;

import configuration.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Courses;

import java.sql.*;
import java.time.LocalDate;

public class CourseService {

    private Connection connection;

    public CourseService() throws SQLException {
        DBConnector db = new DBConnector();
        connection = db.initConnection();
    }

    public int getCountAllCourses() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select count(*) from courses");
        if (resultSet.next()) {

            return resultSet.getInt(1);
        }
        resultSet.close();
        stmt.close();
        return 0;
    }

    public int getMyCourses(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(
                "select count(*) from submission where id_u =" + id);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        resultSet.close();
        stmt.close();
        return 0;
    }

    public ObservableList<Courses> getAllCourses() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
                "select * from courses");
        // wprowadzanie rekordow z DB do listy obiektow klasy modelu - Courses
        ObservableList<Courses> courses_list = FXCollections.observableArrayList();
        while (rs.next()) {
            Courses c = new Courses(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4).toLocalDate(),
                    rs.getString(5),
                    rs.getDouble(6),
                    rs.getInt(7));
            courses_list.add(c);
        }
        return courses_list;
    }

}
