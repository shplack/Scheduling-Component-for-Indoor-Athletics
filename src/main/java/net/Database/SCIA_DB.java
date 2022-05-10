package net.Database;

import Athlete.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SCIA_DB {
    private static Connection conn;

    public static void init(Connection _conn) {
        Objects.requireNonNull(_conn);
        SCIA_DB.conn = _conn;
    }

    public static Set<Athlete> getAthletes() {
        Objects.requireNonNull(SCIA_DB.conn);

        Set<Athlete> athletes = new HashSet<>();
        String query = "SELECT * FROM Athletes";

        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String club = resultSet.getString("club");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Gender gender = Gender.values()[resultSet.getInt("gender")];
                int age = resultSet.getInt("age");
                boolean participating = resultSet.getInt("participating") > 0;
                athletes.add(new Athlete(club, name, surname, gender, age, participating));
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
            return null;
        }
        return athletes;
    }
}
