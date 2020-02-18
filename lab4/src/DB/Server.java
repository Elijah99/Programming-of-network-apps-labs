package DB;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import static DB.DBInfo.DB_URL;
import static DB.DBInfo.DB_USER;

public class Server {

    private Connection connection;
    private Statement statement;
    private ServerSocket socket;

    public static void main(String[] args) {

        Server server = new Server();
        server.initializeServer();
        server.listenConnections();
    }

    private void initializeServer() {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER,
                    DB_USER);
            statement = connection.createStatement();
            socket = new ServerSocket(1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenConnections() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket client = socket.accept();
                new Thread(() -> {
                    try {
                        OutputStream outputStream = client.getOutputStream();
                        InputStream inputStream = client.getInputStream();
                        String clientString;

                        boolean flag = true;

                        while (flag) {
                            byte[] clientMessage = new byte[1024];
                            int k = inputStream.read(clientMessage);
                            clientString = new String(clientMessage, 0, k);
                            clientString = clientString.trim();

                            if (clientString.equalsIgnoreCase("end")) {
                                flag = false;
                            } else if (clientString.equalsIgnoreCase("Select")) {
                                String res = getDataFromDb(statement);
                                outputStream.write(res.getBytes());
                            } else {
                                insertValueToDb(clientString);
                                outputStream.write("Added".getBytes());
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertValueToDb(String row) {
        String[] array = row.split(" ");
        String name = array[0];
        Double salary = Double.parseDouble(array[1]);

        try {
            statement.executeUpdate("INSERT INTO public.\"Profession\"(" +
                    "\"Name\", \"Salary\") VALUES ('" + name + "', " + salary + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDataFromDb(Statement statement) {
        ResultSet resultSet = null;
        StringBuilder res = new StringBuilder();
        try {
            resultSet = statement.executeQuery("SELECT * FROM public.\"Profession\"");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                res.append(id).append(" ").append(name).append(" ").append(salary).append('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(res.toString());
        return res.toString();
    }

}