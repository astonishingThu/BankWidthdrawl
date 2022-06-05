import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame implements MyTheme {

    public MyFrame(String title) throws HeadlessException {
        super(title);
        setBounds(500,300,1000,600);
    }
}
