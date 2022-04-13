package searchEngineApp.supporting_classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchEngineApp.entity.Page;
import searchEngineApp.entity.Site;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Проходит по всем ссылкам на сайте и парсит их в таблицу page
 */
public class LinkParser extends RecursiveTask<Set<Page>> {
    private final Set<String> allUrls;
    private String path = "/";
    private final Set<Page> pageList;
    private final Site entity;
    private final String REGEX = "[A-z-\\/0-9]*\\/[A-z-0-9]*(\\.html)*";


    public LinkParser(Site entity) {

        this.pageList = Collections.synchronizedSet(new HashSet<>());
        this.allUrls = Collections.synchronizedSet(new HashSet<>());
        this.entity = entity;
    }

    private LinkParser(String path, Set<Page> pageList, Set<String> allUrls, Site entity) {
        this.path = path;
        this.pageList = pageList;
        this.allUrls = allUrls;
        this.entity = entity;
    }

    @Override
    public Set<Page> compute() {
        try {
            Document doc = Jsoup.connect(this.entity.getUrl() + this.path).get();
            //Заполняю pages /img/product/real/1121542_1
            Elements jsoupURLS = doc.select("a[href]");
            Pattern pattern = Pattern.compile(REGEX);
            for (Element element : jsoupURLS) {
                String path = element.attr("href");
                Matcher matcher = pattern.matcher(path);
                String extendedPath = null;
                if (matcher.find()) extendedPath = matcher.group();
                if (extendedPath != null && (!allUrls.contains(path)) && !path.contains("/404") && !path.contains("auth") && !(path.contains("http"))) {
                    addPath(path, doc);
                }
            }
        } catch (IOException e) {
            System.out.println("Хуевый путь - " + path);
            allUrls.add(path);
            return pageList;
        }
        return pageList;
    }

    private synchronized void addPath(String path, Document doc) throws IOException {
        int code = Jsoup.connect(this.entity.getUrl() + this.path).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").execute().statusCode();
        try {
            Document destinationTest = Jsoup.connect(this.entity.getUrl() + path).get();
        } catch (IOException e) {
            allUrls.add(path);
            return;
        }
        Page page = new Page();
        page.setPath(path);
        page.setCode(code);
        page.setContent(doc.html());
        page.setSiteId(entity.getId());
        pageList.add(page);
        allUrls.add(path);
        LinkParser searcher = new LinkParser(path, pageList, allUrls, entity);
        System.out.println("ПЕЙДЖ ЧЛИСТ " + entity.getName() + " размер= " + pageList.size());
        searcher.fork();
        searcher.join();
    }
}
