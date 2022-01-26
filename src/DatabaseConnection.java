import java.sql.Connection;

public class DatabaseConnection {
    private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";
    private Connection connection = null;

    private String databaseName;
    private String serverName;

    public DatabaseConnection(String serverName, String databaseName) {
        this.serverName = serverName;
        this.databaseName = databaseName;
    }


}
