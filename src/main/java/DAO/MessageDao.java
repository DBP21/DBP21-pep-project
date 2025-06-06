package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    //Inserts a new message into the database.

    public Message createMessage(Message message) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generatedId = rs.getInt(1);
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Retrieves all messages from the database.
   
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    //Retrieves a specific message by its ID.

    public Message getMessageById(int messageId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Deletes a message from the database.

    public int deleteMessageById(int messageId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    //Updates the text of a specific message.
    
    public int updateMessage(int messageId, String newMessageText) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newMessageText);
            ps.setInt(2, messageId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    //Retrieves all messages posted by a specific user.
    
    public List<Message> getMessagesByAccountId(int accountId) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}