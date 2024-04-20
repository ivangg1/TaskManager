public class Task {

    private String task;
    private boolean completed;

    public Task(String task) {

        this.task = task;
        completed = false;



    }

    public Task(String task, boolean completed) {

        this.task = task;
        this.completed = completed;



    }

    public String getTask() {
        return task;
    }




    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String taskStatus (boolean sta) {

        String status;
        if (sta)
        {
            status = "Done";
            return status;
        }
        else
        {
            status  = "Not done";
            return status;
        }

    }





    @Override

    public String toString() {



        return getTask().toString() + " | " + taskStatus(this.getCompleted());

    }
    


}
