package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    //Email tocar20181@andinews.com
    //Password = Qwerty1-!
    //postgresql://neondb_owner:npg_1gEcsJI7SfLD@ep-damp-dust-a5pp65ci-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require

    private static final String URL = "jdbc:postgresql://ep-damp-dust-a5pp65ci-pooler.us-east-2.aws.neon.tech:5432/neondb";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_1gEcsJI7SfLD";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void createGenresTable(Connection conn) {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS Genres (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) UNIQUE NOT NULL
                    )
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'Genres' checked/created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void addGenre(String name, Connection conn) {
        String createSql = "INSERT INTO Genres (name) VALUES ('" + name + "')";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createSql);
            System.out.println("Added genre successfully!");
        } catch (SQLException e) {
            System.out.println("Error added genre" + e.getMessage());
        }
    }

    // Отримання всіх жанрів
    public static List<String> getAllGenres(Connection conn) {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                genres.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return genres;
    }

    // Отримання жанру за ID
    public static String getGenreById(int id, Connection conn) {
        String getOneSql = "SELECT name FROM Genres WHERE id = " + id;
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(getOneSql)) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
            System.out.println("Getting one genre successfully!");
        } catch (SQLException e) {
            System.out.println("Error getting one genre" + e.getMessage());
        }
        return null;
    }

    // Оновлення жанру
    public static void updateGenre(int id, String newName, Connection conn) {
        String updateSql = "UPDATE Genres SET name = '" + newName + "' WHERE id = " + id;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateSql);
            System.out.println("Updating successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating genre" + e.getMessage());
        }
    }

    // Видалення жанру
    public static void deleteGenre(int id, Connection conn) {
        String deleteSql = "DELETE FROM Genres WHERE id = " + id;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deleteSql);
            System.out.println("Deleting successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting genre" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var conn = getConnection();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Genre\n2. Show All Genres\n3. Update Genre\n4. Delete Genre\n5. Get Genre by Id\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter genre name: ");
                    String name = scanner.nextLine();
                    addGenre(name, conn);
                }
                case 2 -> {
                    List<String> list = getAllGenres(conn);
                    for(var item:list)
                        System.out.println(item);
                }
                case 3 -> {
                    System.out.print("Enter genre ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new genre name: ");
                    String name = scanner.nextLine();
                    updateGenre(id, name, conn);
                }
                case 4 -> {
                    System.out.print("Enter genre ID to delete: ");
                    int id = scanner.nextInt();
                    deleteGenre(id, conn);
                }
                case 5 ->{
                    System.out.print("Enter genre ID: ");
                    int id = scanner.nextInt();
                    String title = getGenreById(id, conn);
                    System.out.print("Genre name: " + title);
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }
}