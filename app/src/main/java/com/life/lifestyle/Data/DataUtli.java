package com.life.lifestyle.Data;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

public class DataUtli {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    public static void getConnection() {

        String url = "jdbc:mysql://192.168.123.65:3306/shenghuowuyu?&user=root&password=Mishiweilai123&useSSL=false&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean addUsers(final User user){
        boolean success = false;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
                pstmt.setString(1, user.getUserID());
                pstmt.setString(2, user.getUserpassword());
                pstmt.setString(3, user.getUsername());
                pstmt.setString(4, user.getUseraddress());
                pstmt.setString(5, user.getUserphone());
                if (user.getBirthDate() != null)
                    pstmt.setDate(6, Date.valueOf(user.getBirthDate()));
                else {
                    pstmt.setDate(6, null);
                }
                success = pstmt.execute();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }


    public static boolean addIdea(final Idea idea){
        boolean success = false;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("insert into ideas values(?,?,?,?)");
                pstmt.setString(1, String.valueOf(new Random(System.currentTimeMillis()).nextInt()));
                pstmt.setString(2, idea.getUserID());
                pstmt.setString(3, idea.getDate());
                pstmt.setBlob(4, idea.getInformation());
                success = pstmt.execute();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }


    public static boolean addBill(final Bill bill){
        boolean success = false;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("insert into ideas values(?,?,?,?,?)");
                pstmt.setString(1, String.valueOf(new Random(System.currentTimeMillis()).nextInt()));
                pstmt.setString(2, bill.getUserID());
                pstmt.setString(3, bill.getTime());
                pstmt.setDouble(4, bill.getPrice());
                pstmt.setBoolean(5, bill.isBit());
                success = pstmt.execute();
                pstmt.close();
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }


    public static boolean addMessage(final Message message){
        int success = 0;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("insert into Messages values(?,?,?,?)");
                pstmt.setString(1, message.getMessID());
                pstmt.setString(2, message.getUserId());
                pstmt.setString(3, message.getSendTime());
                pstmt.setString(4, message.getInformation());
                success = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(success==1)
        return true;
        return false;
    }


    public static boolean addSchedule(final Schedule schedule) {
        int success = 0;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("insert into Schedules values(?,?,?,?)");
                pstmt.setString(1, schedule.getScheID());
                pstmt.setString(2, schedule.getUserID());
                pstmt.setString(3, schedule.getDate());
                pstmt.setString(4, schedule.getInformation());
                success = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(success>=1)
        {
            return true;
        }
        return false;
    }

    public static boolean updateUsers(final User user) {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {

                pstmt = conn.prepareStatement("update Users set username = ?,useraddress =?, userphone =?,birthdate =?");
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getUseraddress());
                pstmt.setString(3, user.getUserphone());
                pstmt.setString(4, user.getBirthDate());
                success = pstmt.execute();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }


    public static boolean removeSchedule(final Schedule schedule) {
        int success = 0;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                pstmt = conn.prepareStatement("delete from Schedules where scheID = ?");
                pstmt.setString(1, schedule.getScheID());
                success = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(success>=0)
        {
            return true;
        }
        return false;
    }

    public static boolean updateSchedule(final Schedule schedule) {
        int success = 0;
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {

                pstmt = conn.prepareStatement("update Schedules set information=? where scheID=?");
                pstmt.setString(1, schedule.getInformation());
                pstmt.setString(2, schedule.getScheID());
                success = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if(success>=1)
        {
            return true;
        }
        return false;
    }



    public static Vector<User> getUsers(final Tuple... data) {
        final Vector<User> vector = new Vector<>();
        final User user = new User();
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                StringBuilder sql = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    sql.append(data[i].name);
                    sql.append("=");
                    sql.append(data[i].value);
                    if (i != data.length - 1) {
                        sql.append(" and");
                    }
                }
                pstmt = conn.prepareStatement("SELECT * FROM users where " + sql.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {

                    user.setUserID(rs.getString(1));
                    user.setUserpassword(rs.getString(2));
                    user.setUsername(rs.getString(3));
                    user.setUseraddress(rs.getString(4));
                    user.setUserphone(rs.getString(5));
                    user.setBirthDate(rs.getString(6));
                    vector.add(user);

                }
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return vector;
        }
        if (vector.size() >= 1) {
            return vector;
        }
        return vector;
    }

    public static Vector<Schedule> getSchedule(final Tuple... data) {
        final Vector<Schedule> vector = new Vector<>();
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                StringBuilder sql = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    sql.append(data[i].name);
                    sql.append("=");
                    sql.append(data[i].value);
                    if (i != data.length - 1) {
                        sql.append(" and");
                    }
                }
                pstmt = conn.prepareStatement("SELECT * FROM schedules where " + sql.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setScheID(rs.getString(1));
                    schedule.setUserID(rs.getString(2));
                    schedule.setDate(rs.getDate(3).toString());
                    schedule.setInformation(rs.getString(4));
                    vector.add(schedule);
                }
                rs.close();
                pstmt.close();
                conn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return vector;
        }
        return vector;
    }

    public static Vector<Message> getMessage(final Tuple... data) {
        final Vector<Message> vector = new Vector<>();
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                if (data == null) {
                    pstmt = conn.prepareStatement("SELECT * FROM Messages");
                } else {
                    StringBuilder sql = new StringBuilder();
                    for (int i = 0; i < data.length; i++) {
                        sql.append(data[i].name);
                        sql.append("=");
                        sql.append(data[i].value);
                        if (i != data.length - 1) {
                            sql.append(" and");
                        }
                    }
                    pstmt = conn.prepareStatement("SELECT * FROM Messages where " + sql.toString());
                }
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Message message = new Message();
                    message.setMessID(rs.getString(1));
                    message.setUserId(rs.getString(2));
                    message.setSendTime(rs.getString(3));
                    message.setInformation(rs.getString(4));
                    vector.add(message);
                }
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return vector;
        }
        return vector;
    }

    public static Vector<Bill> getBill(final Tuple... data) {
        final Vector<Bill> vector = new Vector<>();
        try {
            if (conn == null || conn.isClosed())
                getConnection();
            while (conn != null && !conn.isClosed()) {
                StringBuilder sql = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    sql.append(data[i].name);
                    sql.append("=");
                    sql.append(data[i].value);
                    if (i != data.length - 1) {
                        sql.append(" and");
                    }
                }
                pstmt = conn.prepareStatement("SELECT * FROM bills where " + sql.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillID(rs.getString(1));
                    bill.setUserID(rs.getString(2));
                    bill.setTime(rs.getString(3));
                    bill.setPrice(Double.parseDouble(rs.getString(4)));
                    bill.setBit(Boolean.parseBoolean(rs.getString(5)));
                    vector.add(bill);
                }
                rs.close();
                pstmt.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return vector;
    }
}
