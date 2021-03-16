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

        int[][] letters = parseEngAndNumbers(doc.text());

        parseResultDto.setQuotient(sortNumber(letters[2]));

        parseResultDto.setRemainder(sortEnglish(letters[0], letters[1]));

        return parseResultDto;
    }

    public String sortNumber(int[] numbers) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numbers[i]; j++) {
                sb.append((char) (i + '0'));
            }
        }

        return sb.toString();
    }

    public String sortEnglish(int[] capital, int[] small) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < capital[i]; j++) {
                sb.append((char) (i + 'A'));
            }

            for (int j = 0; j < small[i]; j++) {
                sb.append((char) (i + 'a'));
            }
        }

        return sb.toString();
    }

    public int[][] parseEngAndNumbers(String doc) {
        int[] capitalLetters = new int[26];
        int[] smallLetters = new int[26];
        int[] numbers = new int[10];

        for (char c : doc.toCharArray()) {
            if (isCapitalLetter(c)) {
                capitalLetters[c - 'A']++;
            } else if (isSmallLetter(c)) {
                smallLetters[c - 'a']++;
            } else if (isNumber(c)) {
                numbers[c - '0']++;
            }
        }

        return new int[][]{capitalLetters, smallLetters, numbers};
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
