import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.Vector;
import org.json.JSONObject;
import org.json.JSONArray;


public class GetData {

    static String prefix = "project3.";

    // You must use the following variable as the JDBC connection
    Connection oracleConnection = null;

    // You must refer to the following variables for the corresponding 
    // tables in your database
    String userTableName = null;
    String friendsTableName = null;
    String cityTableName = null;
    String currentCityTableName = null;
    String hometownCityTableName = null;

    // DO NOT modify this constructor
    public GetData(String u, Connection c) {
        super();
        String dataType = u;
        oracleConnection = c;
        userTableName = prefix + dataType + "_USERS";
        friendsTableName = prefix + dataType + "_FRIENDS";
        cityTableName = prefix + dataType + "_CITIES";
        currentCityTableName = prefix + dataType + "_USER_CURRENT_CITIES";
        hometownCityTableName = prefix + dataType + "_USER_HOMETOWN_CITIES";
    }

    // TODO: Implement this function
    @SuppressWarnings("unchecked")
    public JSONArray toJSON() throws SQLException {

        // This is the data structure to store all users' information
        JSONArray users_info = new JSONArray();
        try (Statement stmt = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            // Your implementation goes here....
            String queryAllUsers = "select distinct user_id from " + userTableName;
            ResultSet rst1 = stmt.executeQuery(queryAllUsers);
           

            while(rst1.next()){
                Long currUser_id = rst1.getLong(1);
                JSONObject userObject = new JSONObject();

                String userDetailsQuery = "Select first_name, last_name, gender, year_of_birth, month_of_birth, day_of_birth from "+
                userTableName + " Where user_id = " + currUser_id;
              
                


                
                Statement stmt2  = oracleConnection.createStatement();
                ResultSet rst2 = stmt2.executeQuery(userDetailsQuery);
                while(rst2.next()){
                    userObject.put("MOB", rst2.getLong(5));
                    userObject.put("gender", rst2.getString(3));
                    userObject.put("user_id", currUser_id);
                    userObject.put("DOB", rst2.getLong(6));
                    userObject.put("last_name", rst2.getString(2));
                    userObject.put("first_name", rst2.getString(1));
                    userObject.put("YOB", rst2.getLong(4));
                    //System.out.println("printing users data");
                    //System.out.println(userObject);
                    
                }
                

                String userHomeCityQuery = "Select city_name, state_name, country_name from " +
                cityTableName + " C Join " + hometownCityTableName +
                " HC ON C.city_id = HC.hometown_city_id WHERE HC.user_id ="+currUser_id;
                Statement stmt4  = oracleConnection.createStatement();
            
                ResultSet rst4 = stmt4.executeQuery(userHomeCityQuery);
            
               
                while(rst4.next()){
                    JSONObject hometownCityObject = new JSONObject();
                    hometownCityObject.put("country", rst4.getString(3));
                    hometownCityObject.put("city", rst4.getString(1));
                    hometownCityObject.put("state", rst4.getString(2));
                    userObject.put("hometown", hometownCityObject);
                  
                    break;
                }


                String userCurrCityQuery = "Select city_name, state_name, country_name from " +
                cityTableName + " C Join " + currentCityTableName +
                " CC ON C.city_id = CC.current_city_id WHERE CC.user_id ="+currUser_id;
                Statement stmt3  = oracleConnection.createStatement();
                ResultSet rst3 = stmt3.executeQuery(userCurrCityQuery);
            
                while(rst3.next()){
                    JSONObject currCityObject = new JSONObject();
                    currCityObject.put("country", rst3.getString(3));
                    currCityObject.put("city", rst3.getString(1));
                    currCityObject.put("state", rst3.getString(2));
                    userObject.put("current", currCityObject);
              
                }


            

                String userFriendsQuery = "Select user2_id from " + friendsTableName + 
                " where user1_id =" + currUser_id + " and user2_id > user1_id";
                Statement stmt5  = oracleConnection.createStatement();
                ResultSet rst5 = stmt5.executeQuery(userFriendsQuery);
              
                JSONArray friends = new JSONArray();
                while(rst5.next()){
                    friends.put(rst5.getLong(1));
                }
                userObject.put("friends",friends);

            

                 //insert object
                users_info.put(userObject);

                rst5.close();
                stmt5.close();
                rst4.close();
                stmt4.close();
                rst3.close();
                stmt3.close();
                rst2.close();
                stmt2.close();
               // rst1.close();
                //stmt.close();
                



            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       

        return users_info;
    }

    // This outputs to a file "output.json"
    // DO NOT MODIFY this function
    public void writeJSON(JSONArray users_info) {
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "/output.json");
            file.write(users_info.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
