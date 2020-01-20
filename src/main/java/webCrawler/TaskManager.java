package webCrawler;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskManager {

    private ConcurrentLinkedDeque<Task> tasks;
    private int depth=50;

    public TaskManager(){
        this.tasks=new ConcurrentLinkedDeque<>();
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public synchronized Task getTask() throws InterruptedException {

            while(tasks.size()<1){
            }
            return tasks.removeFirst();

    }

    public ConcurrentLinkedDeque<Task> getTasks(){
        return tasks;
    }

    public void addTask(Task task){
        if(!tasks.contains(task)&&!(task.getDepth()>depth)){
            tasks.addLast(task);
        }
    }

    public int size(){
        return tasks.size();
    }
}
