import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField implements MyTheme {
    public MyTextField(int columns) {
        super();
        setFont(new Font("Roboto",Font.PLAIN,20));
        setForeground(textColor);
    }
}
