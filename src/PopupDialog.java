import javax.swing.*;
import java.awt.*;

public class PopupDialog extends JOptionPane implements MyTheme{
        public PopupDialog() {
            guiSetup();
        }


        public void showMessage( Component parentComponent, String message, String title, String type) {
            if (type.equalsIgnoreCase("Info")) {
                showMessageDialog(parentComponent,message,title,JOptionPane.INFORMATION_MESSAGE);
            } else if (type.equalsIgnoreCase("error")) {
                showMessageDialog(parentComponent,message,title,JOptionPane.ERROR_MESSAGE);
            }
        }

        private void guiSetup() {
            UIManager.put("OptionPane.background", backgroundColor);
            UIManager.put("Panel.background", backgroundColor);
            UIManager.put("OptionPane.messageForeground",textColor);
            UIManager.put("Button.background",textColor);
            UIManager.put("Button.foreground",Color.white);
            UIManager.put("OptionPane.messageFont",messageFont);
            UIManager.put("Button.font",buttonFont);
        }
}
