import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel implements MyTheme{

    public MyLabel(String text) {
        super(text);
        setFont(new Font("Roboto",Font.PLAIN,20));
        setForeground(textColor);
    }
}
