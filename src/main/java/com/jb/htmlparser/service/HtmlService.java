package com.jb.htmlparser.service;

import com.jb.htmlparser.dto.ParseResultDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlService {

    public ParseResultDto getResult(String url, String type, Integer divisor) throws IOException {
        ParseResultDto parseResultDto = new ParseResultDto();
        Document doc = Jsoup.connect(url).get();

        return parseResultDto;
    }

    public boolean isCapitalLetter(char c) {
        return c >= 65 && c <= 90;
    }

    public boolean isSmallLetter(char c) {
        return c >= 97 && c <= 122;
    }

    public boolean isNumber(char c) {
        return c >= 48 && c <= 57;
    }
}
