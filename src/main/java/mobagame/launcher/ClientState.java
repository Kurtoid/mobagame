package mobagame.launcher;

public class ClientState {
    private static ClientState instance;


    // DEBUG PROPERTIES
    boolean isServerEnabled = false;
    private ClientState() {
    }

   static public ClientState getInstance() {
        if (instance == null) {
            instance = new ClientState();
        }
        return instance;
    }

}
