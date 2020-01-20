package webCrawler;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HtmlParser {
    private int time;
    private int numberOfThreads;
    private List<Link> linkList;
    private boolean timeIsEnabled;
    private volatile boolean stopped;
    private TaskManager taskManager;

    public HtmlParser(String startUrl, int numberOfThreads){
        timeIsEnabled=false;
        this.numberOfThreads=numberOfThreads;
        stopped=false;
        linkList =new LinkedList<>();
        taskManager= new TaskManager();
        taskManager.addTask(new Task(startUrl,0));
    }

    public void setDepth(int depth){
        taskManager.setDepth(depth);
    }

    public void setTimeIsEnabled(int seconds){
        timeIsEnabled=true;
        this.time=seconds;
    }

    public void setStopped(){
        System.out.println(Thread.currentThread().getName()+" stop set");
        stopped=true;
    }

    public void startParsing() throws InterruptedException {
        Thread.currentThread().setPriority(10);
        System.out.println("Parsing started by"+Thread.currentThread().getName());
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for(int i=0;i<numberOfThreads;i++){
            executorService.submit(new Worker(taskManager,linkList));
        }
        while(!stopped){

        }
        System.out.println("html parser stopped");
        while(!executorService.isTerminated()){
            executorService.shutdownNow();
            executorService.awaitTermination(50, TimeUnit.MILLISECONDS);
        }
        System.out.println("Threads are terminated");
        int maximumDepth = taskManager.getTasks().stream().map(Task::getDepth).max(Comparator.naturalOrder()).orElse(0);
        System.out.println("Maximum depth reahed: "+maximumDepth);

    }

    public List<Link> getLinks(){
        return linkList;
    }

    public long getPagesParsed() {
        return taskManager.size();
    }

    public boolean isStopped(){
        return stopped;
    }

    public boolean isTimeIsEnabled() {
        return timeIsEnabled;
    }

    public int getTime() {
        return time;
    }
}
