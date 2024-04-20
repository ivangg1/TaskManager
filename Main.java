

import javax.swing.SwingUtilities;



public class Main {
    


    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager(); 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TaskManagerGUI taskManagerGUI = new TaskManagerGUI(taskManager);
                taskManagerGUI.setVisible(true);
            }
        });
    }


}