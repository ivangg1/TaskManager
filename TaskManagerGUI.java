import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;



public class TaskManagerGUI extends JFrame{



    private boolean added = false;
    private TaskManager taskManager;   //instancia de task manager

    //metodo que elimina el elemento de la lista grafica
    private boolean removeCaseInsensitive(DefaultListModel<String> model, String element) {
        int index = 0;
        boolean found = false;
    
        // Mientras no se encuentre el elemento y no se haya recorrido toda la lista
        while (!found && index < model.size()) {
            String currentElement = model.getElementAt(index);
            // Si el elemento actual coincide sin distinguir entre mayúsculas y minúsculas, se elimina
            if (currentElement.equalsIgnoreCase(element)) {
                model.removeElementAt(index);
                found = true; // Se marca como encontrado
            } else {
                index++; // Se pasa al siguiente elemento
            }
        }

        return found;
    }                    

    public TaskManagerGUI(TaskManager taskManager) {

        this.taskManager = taskManager;



        

        setTitle("Gestor de Tareas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // Crear componentes de la interfaz
        JTextField taskField = new JTextField(20);
        JButton newTask = new JButton("Agregar tarea");
        JButton remTask = new JButton("Eliminar tarea");
        JButton saveTasks = new JButton("Guardar");

        DefaultListModel taskListModel = new DefaultListModel<>();
        JList taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

//        DefaultListModel statusListModel = new DefaultListModel<>();
//        JList statusList = new JList<>(statusListModel);
//        JScrollPane scrollPane1 = new JScrollPane(statusList);




        // Panel para los botones de agregar y eliminar tarea
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Layout para colocar los botones en línea
        buttonPanel.add(newTask);
        buttonPanel.add(remTask);



        // Agregar componentes a un contenedor
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(taskField, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
      //  container.add(remTask, BorderLayout.SOUTH);



        //inicializa las task en el gui
      for (Task task : taskManager.getTasks()) {
        taskListModel.addElement(task);
      }



      saveTasks.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        
            try 
            {
                taskManager.saveTasksToFile("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt");
                printModal("saved Succesfullly!");  
            }
            catch (IOException f) 
            {
                f.printStackTrace(); 
            }
        }
    });

        // This action add new task to the list
        newTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText().trim();
                if (!task.isEmpty()) {
                    taskManager.addTask(task);



                    taskListModel.addElement(taskManager.getTasks().get(taskManager.indexOfTask(task)));

                    
                    if (!added) 
                    {
                        buttonPanel.add(saveTasks);
                        buttonPanel.revalidate();
                        buttonPanel.repaint();
                        added = true;
                    }
                    taskField.setText("");
                }
            }
        });
        // This action remove written  task of the list
        // remTask.addActionListener(new ActionListener() {
            // @Override
            // public void actionPerformed(ActionEvent e) {
                // String task = taskField.getText().trim();
                // if (!task.isEmpty()) {

                   //taskListModel.removeElement(task);
                    // if (!removeCaseInsensitive(taskListModel, task)){
                        // printModal("Tarea no encontrada");          
                    // }else{
                        // taskManager.remTask(task);
                        // taskField.setText("");
                        // try 
                        // {
                            // taskManager.updateFileAfterTaskRemoval("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt", task);
                        // }
                        // catch (IOException f) 
                        // {
                            // f.printStackTrace();
                        // }                        

                        // printModal("Task deleted succesfullly!");  
                    //   }
                // }
            // }
        // });
        
        remTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex(); //obtiene el indice en el cual se encuentra el click del mouse
                if (selectedIndex != -1) {
                    // Obtiene el objeto seleccionado del modelo de lista
                    Task selectedObject = (Task) taskListModel.getElementAt(selectedIndex);
//                    // Convierte el objeto a String
                   String selectedTask = selectedObject.toString();
                    // Elimina la tarea del modelo de lista y del TaskManager
                    taskListModel.removeElementAt(selectedIndex);
             
                    taskManager.remTask(selectedObject); // Elimina la tarea del TaskManager


                    try 
                    {
                        taskManager.updateFileAfterTaskRemoval("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt", selectedTask);
                    }
                    catch (IOException f) 
                    {
                        f.printStackTrace();
                    }
                }
            }
        });
//        scrollPane.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == e.VK_ENTER) {
//
//                    int selectedIndex = taskList.getSelectedIndex(); //obtiene el indice en el cual se encuentra el click del mouse
//                    if (selectedIndex != -1) {
//                        // Obtiene el objeto seleccionado del modelo de lista
//                        Object selectedObject = taskListModel.getElementAt(selectedIndex);
//                        // Convierte el objeto a String
//                        String selectedTask = selectedObject.toString();
//                        // Elimina la tarea del modelo de lista y del TaskManager
//                        taskListModel.removeElementAt(selectedIndex);
//
//                        taskManager.remTask(selectedTask); // Elimina la tarea del TaskManager
//
//
//                        taskManager.addToPos(selectedTask);
//
//
//
//                        taskListModel.addElement(taskManager.getTasks().get(taskManager.indexOfTask(selectedTask)));
//
//                        try
//                        {
//                            taskManager.updateFileAfterTaskRemoval("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt", selectedTask);
//                        }
//                        catch (IOException f)
//                        {
//                            f.printStackTrace();
//                        }
//                    }
//
//
//                }
//            }
//        });
        taskList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedIndex = taskList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Task selectedTask = (Task) taskListModel.getElementAt(selectedIndex);
                        // Marcar la tarea como completada
                        selectedTask.setCompleted(true);
                        // Actualizar el modelo de lista (opcional si quieres que la tarea completada se muestre de manera diferente)
                        taskListModel.setElementAt(selectedTask, selectedIndex);
                        try {
                            // Actualizar el archivo después de marcar la tarea como completada
                            taskManager.updateFileAfterTaskRemoval("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt", selectedTask.getTask());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        //Add new task from click
        taskField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == e.VK_ENTER) {
                    String task= taskField.getText().trim(); //toma el texto del field

                    if (!task.isEmpty()) {  //si no esta vacio 

                        taskManager.addTask(task);
                        taskListModel.addElement(taskManager.getTasks().get(taskManager.indexOfTask(task)));

                        if (!added) 
                        {
                            buttonPanel.add(saveTasks);
                            buttonPanel.revalidate();
                            buttonPanel.repaint();
                            added = true;
                        }
                        taskField.setText("");
                    }
                }
            }     
        });           

    }
    public void printModal(String alertText) 
    {
       JOptionPane.showMessageDialog(TaskManagerGUI.this, alertText);
    }
}



