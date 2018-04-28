package mobagame.core;

public class DebugSettings {
    private static DebugSettings instance;


    // DEBUG PROPERTIES
   public boolean isServerEnabled = false;
    private DebugSettings() {
    }

   static public DebugSettings getInstance() {
        if (instance == null) {
            instance = new DebugSettings();
        }
        return instance;
    }

}