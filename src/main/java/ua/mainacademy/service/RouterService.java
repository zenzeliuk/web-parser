package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import ua.mainacademy.model.Item;

import java.util.List;

@AllArgsConstructor
public class RouterService extends Thread{

    private List<Item> items;
    private List<Thread> threads;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    public void parsePage(String url) {
        Document document = DocumentExtractorService.getDocument(url);
        if (ItemPageParsingService.isItemPage(url)) {
            ItemPageParsingService itemPageParsingService = new ItemPageParsingService(items, document, url);
            threads.add(itemPageParsingService);
            itemPageParsingService.start();
        }
        if (NavigationPageParsingService.isNavigationPage(url)) {
            NavigationPageParsingService navigationPageParsingService = new NavigationPageParsingService(items, threads, document, url);
            navigationPageParsingService.start();
        }
    }
}
