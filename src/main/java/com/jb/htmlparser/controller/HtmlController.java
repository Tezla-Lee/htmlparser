package com.jb.htmlparser.controller;

import com.jb.htmlparser.service.HtmlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    private final HtmlService htmlService;

    @GetMapping("/")
    public String home() {

        return "/result";
    }

    @GetMapping({"/result"})
    public String getResult(Model model, @RequestParam String url, @RequestParam String type, @RequestParam(required = false) String divisor) {

        model.addAttribute("result", htmlService.getResult(url, type, divisor));
        model.addAttribute("url", url);
        model.addAttribute("type", type);
        model.addAttribute("divisor", divisor);

        return "/result";
    }
}