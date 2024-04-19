package fr.nkri.wizapi.databases;

import java.sql.*;

public class DataBase {

    private Connection connection;
    private String host, user, password, dbName;
    private int port;

    public DataBase(final String host, final String user, final String password, final String dbName, final int port){
        this.host = host;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        this.port = port;
    }

    public Connection getConnection() throws SQLException {
        if(connection != null && !connection.isClosed()){
            return this.connection;
        }

        openConnection();
        return connection;
    }

    public void closeConnection() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

    //Retourne un boolean en fonction de si la table existe, gère l'erreur avec un printStackTrace et renvoi false si la table n'exite pas.
    public boolean tableExist(final String tableName){
        try {
            return tableExists(tableName);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Retour un boulean en fonction de si la table existe ou non. (ne gère pas l'erreur)
    public boolean tableExists(final String tableName) throws SQLException {
        final DatabaseMetaData databaseMetaData = connection.getMetaData();
        final ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    private void openConnection(){
        try {
            this.connection = DriverManager.getConnection(toURL(), getUser(), getPassword());
            //LOGS SUCCES
        }
        catch (SQLException e) {
            //LOGS ERROR
            e.printStackTrace();
        }
    }

    public String toURL(){
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:mysql://").append(getHost()).append(":").append(getPort()).append("/").append(getDbName());

        return stringBuilder.toString();
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPort() {
        return port;
    }
}
