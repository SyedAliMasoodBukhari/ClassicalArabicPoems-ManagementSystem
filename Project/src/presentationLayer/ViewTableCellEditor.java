package presentationLayer;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class ViewTableCellEditor extends DefaultCellEditor {

    private ViewTableEvent event;
    private ImageIcon icon;

    public ViewTableCellEditor(ViewTableEvent event, ImageIcon icon) {
        super(new JCheckBox());
        this.event = event;
        this.icon = icon;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        ViewPanel action = new ViewPanel(icon);
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
