package presentationLayer;

import java.awt.event.MouseEvent;

public interface TableActionEvent {

    public void onEdit(int row);

    public void onDelete(int row);

    public void onView(MouseEvent e, int row);
    
    public void onDone(int row);
}
