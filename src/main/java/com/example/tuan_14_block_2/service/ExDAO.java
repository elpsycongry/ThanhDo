package com.example.tuan_14_block_2.service;

import com.example.tuan_14_block_2.model.ExObj;

import java.sql.*;
import java.util.List;

public class ExDAO implements iExDAO{
    private final String URL = "jdbc:mysql://localhost:3306/exData";
    private final String USER = "tung";
    private final String PASS = "tung";

    public Connection getConnection(){
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
    @Override
    public List<ExObj> getAllObj() {
        Connection con  = getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("select * from exobj");
            ResultSet rs =  ps.executeQuery();
            con.commit();
            printRs(rs);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public ExObj getById(int id) {
        Connection connection = getConnection();
        ExObj exObj = null;
        String query = "{CALL getObj(?)}";
        try {
            CallableStatement callableStatement  = connection.prepareCall(query);
            callableStatement.setInt(1,id);
            ResultSet rs  = callableStatement.executeQuery();
            while (rs.next()){
                exObj = new ExObj(rs.getString("name"),rs.getInt("id"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exObj;
    }

    @Override
    public void addObj(ExObj obj) {
        Connection connection  = getConnection();
        try {
            CallableStatement callableStatement  = connection.prepareCall("{call addObj(?)}");
            callableStatement.setString(1,obj.getName());
            callableStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void addObjTransaction(ExObj obj, List<Integer> permissions) {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO exobj (name) value (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,obj.getName());
            int rowChanged = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            int objId  = 0;
            if (rs.next()){
                objId = rs.getInt(1);
            }

            if (rowChanged == 1){
                for (int permission: permissions
                     ) {
                    PreparedStatement psPer = connection.prepareStatement("insert into objpermission values (?,?);");
                    psPer.setInt(1,objId);
                    psPer.setInt(2,permission);
                    psPer.executeUpdate();
                }
                connection.commit();
            }
            connection.rollback();
            connection.close();

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
