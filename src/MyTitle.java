import javax.swing.*;
import java.awt.*;

public class MyTitle extends JLabel implements MyTheme{
    public MyTitle() {
        super("Welcome to Revel Bank");
        setFont(new Font("Roboto",Font.BOLD,40));
        setForeground(textColor);
    }
}
