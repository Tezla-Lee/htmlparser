package com.jb.htmlparser.service;

import com.jb.htmlparser.dto.ParseResultDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class HtmlService {

    public ParseResultDto getResult(String url, String type, String divisor) {
        if (url == null || url.equals("")) {
            return new ParseResultDto("url을 입력해 주세요.");
        }

        int div;

        if (divisor == null || divisor.equals("")) {
            return new ParseResultDto("출력 단위 묶음을 입력해 주세요.");
        } else {
            try {
                div = Integer.parseInt(divisor);
            } catch (NumberFormatException e) {
                return new ParseResultDto("출력 단위 묶음은 2,147,483,647 이하의 자연수이어야 합니다.");
            }
        }

        if (div < 0) {
            return new ParseResultDto("출력 단위 묶음은 2,147,483,647 이하의 자연수이어야 합니다.");
        }

        Document doc;

        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            return new ParseResultDto("잘못된 url 입니다.");
        }

        int[][] letters;

        if (type.equals("exclude tag")) {
            letters = parseEngAndNumbers(doc.text());
        } else if (type.equals("all")) {
            letters = parseEngAndNumbers(doc.toString());
        } else {
            return new ParseResultDto("잘못된 Type 입니다.");
        }

        return divide(combineNumberAndEnglish(sortNumber(letters[2]), sortEnglish(letters[0], letters[1])), div);
    }

    public ParseResultDto divide(String s, int divisor) {
        ParseResultDto parseResultDto = new ParseResultDto();

        int remainder = s.length() % divisor;

        parseResultDto.setQuotient(s.substring(0, s.length() - remainder));

        if (remainder > 0) {
            parseResultDto.setRemainder(s.substring(s.length() - remainder));
        }

        return parseResultDto;
    }

    public String combineNumberAndEnglish(String english, String number) {
        StringBuilder sb = new StringBuilder();
        char[] eng = english.toCharArray();
        char[] numbers = number.toCharArray();

        for (int i = 0; i < Math.min(eng.length, numbers.length); i++) {
            sb.append(eng[i]).append(numbers[i]);
        }

        if (english.length() > number.length()) {
            sb.append(english.substring(number.length()));
        } else {
            sb.append(number.substring(english.length()));
        }

        return sb.toString();
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