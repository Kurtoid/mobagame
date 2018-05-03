package mobagame.core;

@Deprecated
public class DebugSettings {
    private static DebugSettings instance;


    // DEBUG PROPERTIES
   public boolean isServerEnabled = true;
    private DebugSettings() {
    }

   static public DebugSettings getInstance() {
        if (instance == null) {
            instance = new DebugSettings();
        }
        return instance;
    }

}
