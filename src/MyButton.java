import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton implements MyTheme{
    public MyButton(String text) {
        super(text);
        setForeground(Color.white);
        setFont(new Font("Roboto",Font.PLAIN,20));
        setBackground(textColor);
    }
}
