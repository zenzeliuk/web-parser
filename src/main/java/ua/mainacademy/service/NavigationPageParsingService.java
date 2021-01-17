package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ua.mainacademy.model.Item;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NavigationPageParsingService extends Thread {

    private List<Item> items;
    private List<Thread> threads;
    private Document document;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    private void parsePage(String url) {

        // extract item links
        List<String> itemLinks = new ArrayList<>();
        /*
        TODO: extract item links
         */

        for (String itemLink : itemLinks) {
            if (threads.size() > 5) {
                continue;
            }
            RouterService routerService = new RouterService(items, threads, itemLink);
            threads.add(routerService);
            routerService.start();
        }

        // pagination
        if (!url.contains("&page=")) {
            // TODO: extract last page number
            if (threads.size() > 2) {
                return;
            }
            int lastPage = 7;
            for (int i = 1; i <= lastPage; i++) {
                String nextPageUrl = url + "&page=" + i;
                RouterService routerService = new RouterService(items, threads, nextPageUrl);
                threads.add(routerService);
                routerService.start();
            }
        }


    }

    public static boolean isNavigationPage(String url) {
        return !url.contains("/dp/");
    }
}

