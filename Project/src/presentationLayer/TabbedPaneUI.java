package presentationLayer;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class TabbedPaneUI
        extends FlatTabbedPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new TabbedPaneUI();
    }

    @Override
    protected void paintCardTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h) {
        float lineWidth = UIScale.scale(1f);
        float arc = UIScale.scale(10);

        Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
        path.append(FlatUIUtils.createRoundRectanglePath(x, y, w, h, arc, arc, 0, 0), false);
        path.append(FlatUIUtils.createRoundRectanglePath(x + lineWidth, y + lineWidth, w - (lineWidth * 2), h - lineWidth,
                arc - lineWidth, arc - lineWidth, 0, 0), false);

        g.setColor(Color.red);
        ((Graphics2D) g).fill(path);
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
        // paint tab background 
        Color background = getTabBackground(tabPlacement, tabIndex, isSelected);
        g.setColor(FlatUIUtils.deriveColor(background, tabPane.getBackground()));
        g.fillRect(x, y, w, h);
    }
}
