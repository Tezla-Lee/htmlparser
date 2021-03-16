package com.jb.htmlparser.service;

import com.jb.htmlparser.dto.ParseResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HtmlServiceTest {

    @Autowired
    private HtmlService htmlService;

    @DisplayName("정상 작동")
    @Test
    void getResult() {
        ParseResultDto result = htmlService.getResult("https://front.wemakeprice.com/main", "exclude tag", "1");

        assertThat(result.getErrorMessage()).isEqualTo(null);
        assertThat(result.getQuotient()).isNotEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }

    @DisplayName("url 없음")
    @Test
    void getResult2() {
        ParseResultDto result = htmlService.getResult("", "exclude tag", "1");

        assertThat(result.getErrorMessage()).isEqualTo("url을 입력해 주세요.");
        assertThat(result.getQuotient()).isEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }

    @DisplayName("잘못된 url 입력")
    @Test
    void getResult3() {
        ParseResultDto result = htmlService.getResult("abc", "exclude tag", "1");

        assertThat(result.getErrorMessage()).isEqualTo("잘못된 url 입니다.");
        assertThat(result.getQuotient()).isEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }

    @DisplayName("잘못된 출력 묶음 단위")
    @Test
    void getResult4() {
        ParseResultDto result = htmlService.getResult("https://front.wemakeprice.com/main", "exclude tag", "0");

        assertThat(result.getErrorMessage()).isEqualTo("출력 단위 묶음은 2,147,483,647 이하의 자연수이어야 합니다.");
        assertThat(result.getQuotient()).isEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }

    @DisplayName("범위 밖의 출력 단위 묶음 입력")
    @Test
    void getResult5() {
        ParseResultDto result = htmlService.getResult("https://front.wemakeprice.com/main", "all", "546789367957348");

        assertThat(result.getErrorMessage()).isEqualTo("출력 단위 묶음은 2,147,483,647 이하의 자연수이어야 합니다.");
        assertThat(result.getQuotient()).isEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }

    @DisplayName("잘못된 Type")
    @Test
    void getResult6() {
        ParseResultDto result = htmlService.getResult("https://front.wemakeprice.com/main", "타입", "54");

        assertThat(result.getErrorMessage()).isEqualTo("잘못된 Type 입니다.");
        assertThat(result.getQuotient()).isEqualTo(null);
        assertThat(result.getRemainder()).isEqualTo(null);
    }
}