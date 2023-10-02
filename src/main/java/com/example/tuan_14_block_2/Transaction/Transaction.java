package com.example.tuan_14_block_2.Transaction;

import java.sql.*;

public class Transaction {
    private static final String URL = "jdbc:mysql://localhost:3306/exdata";
    private static final String USER = "tung";
    private static final String PASS = "tung";

    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL,USER,PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            conn.setAutoCommit(false);

            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            String SQL = "INSERT INTO exobj (name) VALUES ('Quang')";

            Savepoint savepoint = conn.setSavepoint("Add quang");
            stmt.executeUpdate(SQL);
            stmt.executeUpdate(SQL);
            System.out.println("Uy Thac");
            conn.rollback(savepoint);
//            conn.commit();
            String query = "SELECT * FROM exobj";

            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Liet ke");
            printRs(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printRs(ResultSet rs) throws SQLException {
        while (rs.next()){
            System.out.println(rs.getString("name"));
            System.out.println(rs.getInt("id"));
        }

    }
}
