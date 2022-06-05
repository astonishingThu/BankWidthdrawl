import javax.swing.*;
import java.awt.*;

public class MyPasswordField extends JPasswordField implements MyTheme {
    public MyPasswordField(int columns) {
        super(columns);
        setFont(new Font("Roboto",Font.PLAIN,20));
        setForeground(textColor);
    }
}
