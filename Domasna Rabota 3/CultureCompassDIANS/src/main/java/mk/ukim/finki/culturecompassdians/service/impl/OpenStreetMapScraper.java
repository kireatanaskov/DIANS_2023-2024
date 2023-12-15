package mk.ukim.finki.culturecompassdians.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class OpenStreetMapScraper {

    public static String getWikipediaLink(String OSM_URL) {
        try {
            Document document = Jsoup.connect(OSM_URL).get();
            Element wikiLinkElement = document.select("a[title~=Wikipedia]").first();

            return wikiLinkElement != null ? wikiLinkElement.absUrl("href") : "Wikipedia link not found";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static String getFirstParagraph(String WIKI_URL) {
        if(WIKI_URL == null || WIKI_URL.isEmpty() || WIKI_URL.equals("Wikipedia link not found")) {
            return "";
        }

        try {
            Document document = Jsoup.connect(WIKI_URL).get();
            Element firstParagraphEl = document.select("p").first();

            return firstParagraphEl != null ? firstParagraphEl.text() : "First paragraph not found";
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
