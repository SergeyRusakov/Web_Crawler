package webCrawler;

public class Task {
    private String address;
    private int depth;

    public Task(String address) {
        this.address = address;
        depth=1;
    }

    public Task(String address, int depth) {
        this.address = address;
        this.depth = depth;
    }

    public String getAddress() {
        return address;
    }

    public int getDepth() {
        return depth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
