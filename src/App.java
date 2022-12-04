
import javax.swing.UIManager;

import Interface.Window;

public class App {
    public static void main(String[] args) throws Exception {
        
       UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
          
        Window W = new Window();
        W.setVisible(true);

    }
}
