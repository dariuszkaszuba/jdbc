package service;

import configuration.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Courses;
import model.SubmissionView;

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

    public ObservableList<SubmissionView> getAllSubmissions(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
                "select * from submission_view where email=(select email from users where id_u="+id+")");
        ObservableList<SubmissionView> submission_list = FXCollections.observableArrayList();
        while (rs.next()) {
            SubmissionView sv = new SubmissionView(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDate(7).toLocalDate()
            );
            submission_list.add(sv);
        }
        return submission_list;
    }

    public void saveUserOnCourse(int id_u, int id_c) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into submission values (default , ?,?)"
        );
        ps.setInt(1,id_u);
        ps.setInt(2,id_c);
        ps.executeUpdate();
    }

    public void deleteSubmission(int id_s) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "delete from submission where id_s=?");
        ps.setInt(1,id_s);
        ps.executeUpdate();
    }

    public void updateSubmission(int id_s, int id_c) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "update submission SET id_c=? where id_s=?");
        ps.setInt(1,id_c);
        ps.setInt(2,id_s);
        ps.executeUpdate();
    }

}