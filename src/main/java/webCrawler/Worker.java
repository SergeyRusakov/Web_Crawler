package webCrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Worker implements Runnable {

    TaskManager manager;
    List<Link> links;
    volatile int depth;

    public Worker(TaskManager manager, List<Link> links) {
        this.manager = manager;
        this.links = links;
    }


    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " started");
            while (!Thread.interrupted()) {
                Task task = manager.getTask();
                Connection connection = Jsoup.connect(task.getAddress()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0\"");
                connection.timeout(5000);
                Document document = connection.get();
                String title = document.title().replaceAll("\r\n", "");
                links.add(new Link(task.getAddress(), title));
                Elements links = document.select("a[href]");
                for (int i = 0; i < links.size(); i++) {
                    Thread.sleep(50);
                    Element k = links.get(i);
                    String address = k.attr("abs:href");
                    if(isHtmlPage(address)) {
                        manager.addTask(new Task(address, task.getDepth() + 1));
//                        System.out.println(task.getDepth());
//                        System.out.println("working");
                    }
                }
            }
        } catch (InterruptedException | IOException e) {

        }

    }

    private boolean isHtmlPage(String address) throws IOException, InterruptedException {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            if (contentType != null) {
                return contentType.contains("text/html");
            }
            return false;
    }
}