import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel implements MyTheme{

    public MyPanel(LayoutManager layout) {
        super(layout);
        setBackground(backgroundColor);
    }

    public MyPanel() {
        super();
        setBackground(backgroundColor);
    }

    public MyPanel(LayoutManager layout, String main) {
        super(layout);
        setBorder(BorderFactory.createEmptyBorder(50,150,50,150));
        setBackground(backgroundColor);
    }
}

class SouthPanel extends MyPanel {
    public SouthPanel(LayoutManager layout) {
        super(layout);
        setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
    }

    public SouthPanel() {
        setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
    }

    public SouthPanel(LayoutManager layout, String main) {
        super(layout, main);
        setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
    }
}
