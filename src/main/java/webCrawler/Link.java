package webCrawler;

public class Link {
    private String address;
    private String title;

    public Link(String address, String title) {
        this.address = address;
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Link{" +
                "address='" + address + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
