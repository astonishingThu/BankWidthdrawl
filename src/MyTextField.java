import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField implements MyTheme {

    public MyTextField(String text, int columns) {
        super(text,columns);
        setFont(new Font("Roboto",Font.PLAIN,20));
        setForeground(textColor);
    }

    public MyTextField(int columns) {
        super();
        setFont(new Font("Roboto",Font.PLAIN,20));
        setForeground(textColor);
    }
}
