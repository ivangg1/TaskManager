
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;




public class TaskManager {

    private List<Task> tasks;

    public TaskManager () {

        tasks = new ArrayList<>();
        try {
            loadTasksFromFile("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    public TaskManager (List<Task> tasks) {

        this.tasks = tasks;
        try {
            loadTasksFromFile("C:\\Users\\ivang\\Desktop\\ING. SALUD\\Primero\\pruebas\\demo\\src\\main\\java\\Task.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask (String t) {

        Task task = new Task(t);


        tasks.add(task);

    }

    public void addToPos (String t)
    {


        Task tasky = new Task(t, true);

        tasks.add( indexOfTask(t), tasky );
    }





    public int indexOfTask(String t) {


        
        boolean enc = false;
        int count = 0;

        while (!enc && count < tasks.size()) {
            
            Task tasky = tasks.get(count);

            if (tasky.getTask().equalsIgnoreCase(t)) {

                enc = true;
            }
            else {
                count++;
            }

        }
        return count;


    }

    public void remTask (Task t) {


        tasks.remove(t);

    }

    public void remTask (String t) {

        int index = indexOfTask(t);

        if (index > 0) {
            
            tasks.remove(index);
          
            
        }

        // else {

        //    System.out.println("No task with that name found @_@");

        // }        

    }
    public void saveTasksToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                String taskString = task.getTask() + "," + task.getCompleted();
                writer.write(taskString);
                writer.newLine();
            }
        }
    }
    
    // public void saveTaskToFile(String filename, String task) throws IOException {
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

                // int index = indexOfTask(task);
                // Task task_object = tasks.get(index);

                // String taskString = task_object.getTask() + "," + task_object.getCompleted();
                // writer.write(taskString);
                // writer.newLine();
            // 
        // }
    // }

    public void loadTasksFromFile(String filename) throws IOException {
        tasks.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String taskName = parts[0];
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    Task task = new Task(taskName);
                    task.setCompleted(completed);
                    tasks.add(task);
                }
            }
        }
    }

    public void updateFileAfterTaskRemoval(String filename, String taskNameToRemove) throws IOException {
        // Crea una lista temporal para almacenar las tareas que no serán eliminadas
        List<Task> remainingTasks = new ArrayList<>(tasks);
        // Elimina la tarea de la lista temporal
        remainingTasks.removeIf(task -> task.getTask().equalsIgnoreCase(taskNameToRemove));
    
        // Guarda las tareas restantes en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : remainingTasks) {
                String taskString = task.getTask() + "," + task.getCompleted();
                writer.write(taskString);
                writer.newLine();
            }
        }
    }

}




