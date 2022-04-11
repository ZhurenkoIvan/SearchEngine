package searchEngineApp.supporting_classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchEngineApp.entity.Page;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveTask;

/**
 * Проходит по всем ссылкам на сайте и парсит их в таблицу page
 */
public class LinkParser extends RecursiveTask<List<Page>> {
    private final Set<String> allUrls;
    private final String URL;
    private String path ="/";
    private final List<Page> pageList;


    public LinkParser(String url) {
        this.URL = url.substring(0, url.length()-1);
        this.pageList = Collections.synchronizedList(new ArrayList<Page>());
        this.allUrls = Collections.synchronizedSet(new HashSet<>());
    }

    private LinkParser(String url, String path, List<Page> pageList, Set<String> allUrls) {
        this.URL = url;
        this.path = path;
        this.pageList = pageList;
        this.allUrls = allUrls;
    }

    @Override
    public List<Page> compute() {
        try {
            Document doc = Jsoup.connect(this.URL + this.path)
                    .get();

            //Заполняю pages
            Elements jsoupURLS = doc.select("a[href]");
            for (Element element : jsoupURLS) {
                String path = element.attr("href");
                if (path.contains(".html") && (!allUrls.contains(path))) {
                    int code = Jsoup.connect(this.URL + this.path)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                                    .execute().statusCode();
                    Page page = new Page();
                    page.setPath(path);
                    page.setCode(code);
                    page.setContent(doc.html());
                    pageList.add(page);
                    allUrls.add(path);
                    LinkParser searcher = new LinkParser(this.URL, path, pageList, allUrls);
                    searcher.fork();
                    searcher.join();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pageList.size());
        return pageList;
    }

    public void showAllURLS () {
        for (Object url : allUrls) {
            System.out.println(url);
        }
        System.out.println(allUrls.size() + " размер");
    }
}
