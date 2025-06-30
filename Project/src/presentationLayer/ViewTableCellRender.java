package presentationLayer;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ViewTableCellRender extends DefaultTableCellRenderer {

    private ImageIcon icon;
    public ViewTableCellRender(ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        ViewPanel action = new ViewPanel(icon);
        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(com.getBackground());
        } else {
            action.setBackground(com.getBackground());
        }
        return action;
    }
}
