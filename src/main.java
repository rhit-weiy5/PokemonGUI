import Database.DatabaseConnection;

public class main {

    private static String serverName = "titan.csse.rose-hulman.edu";
    private static String databaseName = "PokemonDB";
    private static String username = "weiy5";
    private static String password = "Womeiyoumima123";

    public static void main(String[] args){
        DatabaseConnection db = new DatabaseConnection(serverName, databaseName);
        db.connect(username, password);
        Frame frame = new Frame(db);

    }

}
