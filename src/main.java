public class main {

    private static String serverName = "titan.csse.rose-hulman.edu";
    private static String databaseName = "PokémonDB";
    private static String username = "";
    private static String password = "";

    public static void main(String[] args){
        Frame frame = new Frame();
        DatabaseConnection db = new DatabaseConnection(serverName, databaseName);
        db.connect(username, password);
    }

}
