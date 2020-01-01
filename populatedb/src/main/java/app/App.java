package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.util.Scanner;

public class App {

    public static Connection connectToDB() throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //Getting the connection
        System.out.println("Please enter your MySQL database host address (e.g. localhost): ");
        Scanner host = new Scanner(System.in);
        String mysqlHost = host.nextLine();
        System.out.println("Please enter your MySQL database port number: ");
        Scanner port = new Scanner(System.in);
        String mysqlPortNumber = port.nextLine();
        System.out.println("Please enter your MySQL database username: ");
        Scanner userName = new Scanner(System.in);
        String username = userName.nextLine();
        System.out.println("Please enter your MySQL database password: ");
        Scanner pass = new Scanner(System.in);
        String password = pass.nextLine();


        Connection conn = null;
        Statement stmt;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + mysqlHost + ":" + mysqlPortNumber + "/", username, password);
            System.out.println("Creating database...");
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE doodle_polls ";
            stmt.executeUpdate(sql);
            System.out.println("Database 'doodle_polls' created successfully...");
           // conn.setCatalog(mysqlSchema);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        Connection con = null;
        try {
            final String uri = "https://boiling-tor-31289.herokuapp.com/users/me/polls";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            Object object;
            JSONArray jsonArray;
            object = jsonParser.parse(result);
            jsonArray = (JSONArray) object;

            con = connectToDB();
            //Create DB tables
            PreparedStatement createTablePolls = con.prepareStatement("CREATE TABLE doodle_polls.polls (" +
                    "id VARCHAR(255) NOT NULL ," +
                    "admin_key VARCHAR(255) NOT NULL ," +
                    "latest_change DATE NOT NULL ," +
                    "initiated DATE NOT NULL ," +
                    "participants_count INT ," +
                    "invitees_count INT ," +
                    "data_type VARCHAR(255) ," +
                    "hidden INT NOT NULL," +
                    "preferences_type VARCHAR(255) ," +
                    "state VARCHAR(255) ," +
                    "locale VARCHAR(255) ," +
                    "title VARCHAR(255) ," +
                    "description BLOB ," +
                    "options_hash VARCHAR(255) ," +
                    "device VARCHAR(255) ," +
                    "levels VARCHAR(255) ," +
                    "initiator_id INT NOT NULL)");

            PreparedStatement createTableParticipants = con.prepareStatement("CREATE TABLE doodle_polls.participants (" +
                    "id INT NOT NULL ," +
                    "name VARCHAR(255) NOT NULL ," +
                    "preferences VARCHAR(255) ," +
                    "poll_id VARCHAR(255))");

            PreparedStatement createTableOptions = con.prepareStatement("CREATE TABLE doodle_polls.options (" +
                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "text VARCHAR(255) ," +
                    "available INT ," +
                    "all_day INT ," +
                    "start DATE ," +
                    "options_date DATE ," +
                    "poll_id VARCHAR(255) NOT NULL)");

            PreparedStatement createTableInvitees = con.prepareStatement("CREATE TABLE doodle_polls.invitees (" +
                    "id INT NOT NULL ," +
                    "poll_id VARCHAR(255))");


            PreparedStatement createTableInitiators = con.prepareStatement("CREATE TABLE doodle_polls.initiators (" +
                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ," +
                    "initiator_name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL ," +
                    "notify INT ," +
                    "time_zone VARCHAR(255) ," +
                    "poll_id VARCHAR(255) " +
                    ")");

            createTablePolls.execute();
            createTableInitiators.execute();
            createTableInvitees.execute();
            createTableOptions.execute();
            createTableParticipants.execute();

            PreparedStatement insertTablePolls = con.prepareStatement(
                    "INSERT INTO doodle_polls.polls (id,admin_key,latest_change,initiated,participants_count,invitees_count," +
                            "data_type,hidden,preferences_type,state,locale,title,description,options_hash,device,levels,initiator_id)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

            PreparedStatement insertTableInitiators = con.prepareStatement("" +
                    "INSERT INTO doodle_polls.initiators (initiator_name,email,notify,time_zone,poll_id)" +
                    " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            PreparedStatement insertTableOptions = con.prepareStatement("" +
                    "INSERT INTO doodle_polls.options (text,available,all_day,start,options_date,poll_id)" +
                    " VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            PreparedStatement insertTableParticipants = con.prepareStatement("" +
                    "INSERT INTO doodle_polls.participants (id,name,preferences,poll_id)" +
                    " VALUES (?,?,?,?)");

            PreparedStatement insertTableInvitees = con.prepareStatement("" +
                    "INSERT INTO doodle_polls.invitees (id,poll_id)" +
                    " VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);

            for (Object o : jsonArray) {
                JSONObject record = (JSONObject) o;

                String id = (String) record.get("id");
                String adminKey = (String) record.get("adminKey");
                Long latestChange = (Long) record.get("latestChange");
                Long initiated = (Long) record.get("initiated");
                Long participantsCount = (Long) record.get("participantsCount");
                Long inviteesCount = (Long) record.get("inviteesCount");
                String dataType = (String) record.get("type");
                Boolean hidden = (Boolean) record.get("hidden");
                String preferencesType = (String) record.get("preferencesType");
                String state = (String) record.get("state");
                String locale = (String) record.get("locale");
                String title = (String) record.get("title");
                String description = (String) record.get("description");
                String optionsHash = (String) record.get("optionsHash");
                String device = (String) record.get("device");
                String levels = (String) record.get("levels");

                insertTablePolls.setString(1, id);
                insertTablePolls.setString(2, adminKey);
                insertTablePolls.setTimestamp(3, new Timestamp(latestChange));
                insertTablePolls.setTimestamp(4, new Timestamp(initiated));
                insertTablePolls.setLong(5, participantsCount);
                insertTablePolls.setLong(6, inviteesCount);
                insertTablePolls.setString(7, dataType);
                if (hidden != null) {
                    insertTablePolls.setBoolean(8, hidden);
                } else {
                    insertTablePolls.setBoolean(8, false);
                }
                insertTablePolls.setString(9, preferencesType);
                insertTablePolls.setString(10, state);
                insertTablePolls.setString(11, locale);
                insertTablePolls.setString(12, title);
                if (description != null) {
                    insertTablePolls.setString(13, description);
                } else {
                    insertTablePolls.setString(13, null);
                }
                insertTablePolls.setString(14, optionsHash);
                insertTablePolls.setString(15, device);
                insertTablePolls.setString(16, levels);

                if (record.containsKey("initiator")) {
                    Object o1 = jsonParser.parse(record.get("initiator").toString());
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.add(o1);
                    for (Object o2 : jsonArray1) {
                        JSONObject initiatorRecord = (JSONObject) o2;
                        String initiatorName = (String) initiatorRecord.get("name");
                        String email = (String) initiatorRecord.get("email");
                        Boolean notify = (Boolean) initiatorRecord.get("notify");
                        String timeZone = (String) initiatorRecord.get("timeZone");

                        insertTableInitiators.setString(1, initiatorName);
                        insertTableInitiators.setString(2, email);
                        if (notify != null) {
                            insertTableInitiators.setBoolean(3, notify);
                        } else {
                            insertTableInitiators.setBoolean(3, false);
                        }
                        if (timeZone != null) {
                            insertTableInitiators.setString(4, timeZone);
                        } else {
                            insertTableInitiators.setString(4, null);
                        }
                        insertTableInitiators.setString(5, id);

                        insertTableInitiators.executeUpdate();

                        ResultSet generatedKeysResultSet = insertTableInitiators.getGeneratedKeys();
                        generatedKeysResultSet.next();

                        insertTablePolls.setInt(17, generatedKeysResultSet.getInt(1));
                    }
                }

                if (record.containsKey("options")) {
                    Object o1 = jsonParser.parse(record.get("options").toString());
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.add(o1);
                    for (Object o2 : jsonArray1) {
                        JSONArray jsonArray2 = (JSONArray) o2;
                        for (Object o3 : jsonArray2) {
                            JSONObject initiatorRecord = (JSONObject) o3;
                            String text = (String) initiatorRecord.get("text");
                            Boolean available = (Boolean) initiatorRecord.get("available");
                            Long start = (Long) initiatorRecord.get("start");
                            Boolean allDay = (Boolean) initiatorRecord.get("allDay");
                            Long optionDate = (Long) initiatorRecord.get("date");

                            if (text != null) {
                                insertTableOptions.setString(1, text);
                            } else {
                                insertTableOptions.setString(1, null);
                            }
                            if (available != null) {
                                insertTableOptions.setBoolean(2, available);
                            } else {
                                insertTableOptions.setBoolean(2, false);
                            }
                            if (allDay != null) {
                                insertTableOptions.setBoolean(3, allDay);
                            } else {
                                insertTableOptions.setBoolean(3, false);
                            }
                            if (start != null) {
                                insertTableOptions.setTimestamp(4, new Timestamp(start));
                            } else {
                                insertTableOptions.setTimestamp(4, null);
                            }
                            if (optionDate != null) {
                                insertTableOptions.setTimestamp(5, new Timestamp(optionDate));
                            } else {
                                insertTableOptions.setTimestamp(5, null);
                            }
                            insertTableOptions.setString(6, id);

                            insertTableOptions.executeUpdate();

                            ResultSet generatedKeysResultSet = insertTableOptions.getGeneratedKeys();
                            generatedKeysResultSet.next();
                        }
                    }
                }

                if (record.containsKey("participants")) {
                    Object o1 = jsonParser.parse(record.get("participants").toString());
                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.add(o1);
                    for (Object o2 : jsonArray1) {
                        JSONArray jsonArray2 = (JSONArray) o2;
                        for (Object o3 : jsonArray2) {
                            JSONObject participantRecord = (JSONObject) o3;
                            Object o4 = jsonParser.parse(participantRecord.get("preferences").toString());
                            Long participantId = (Long) participantRecord.get("id");
                            String name = (String) participantRecord.get("name");
                            JSONArray preferenceRecord = (JSONArray) o4;
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Object l : preferenceRecord) {
                                stringBuilder.append(l);
                                stringBuilder.append(",");
                            }
                            insertTableParticipants.setLong(1, participantId);
                            insertTableParticipants.setString(2, name);
                            insertTableParticipants.setString(3, stringBuilder.toString());
                            insertTableParticipants.setString(4, id);
                            insertTableParticipants.executeUpdate();
                        }
                    }
                }

                if (inviteesCount > 0) {
                    if (record.containsKey("invitees")) {
                        Object o1 = jsonParser.parse(record.get("invitees").toString());
                        JSONArray jsonArray1 = new JSONArray();
                        jsonArray1.add(o1);
                        for (Object o2 : jsonArray1) {
                            JSONArray jsonArray2 = (JSONArray) o2;
                            for (Object o3 : jsonArray2) {
                                // TODO populate with invitees data, here is empty because no one invitees exists in JSON
                                insertTableInvitees.setString(1, id);
                                insertTableInvitees.executeUpdate();

                                ResultSet generatedKeysResultSet = insertTableInvitees.getGeneratedKeys();
                                generatedKeysResultSet.next();
                            }
                        }
                    }
                }
                insertTablePolls.executeUpdate();
            }

            System.out.println("Records inserted.....");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
