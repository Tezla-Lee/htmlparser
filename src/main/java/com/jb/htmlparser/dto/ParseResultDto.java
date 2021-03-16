package com.jb.htmlparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ParseResultDto {
    private String quotient;

    private String remainder;

    @NonNull
    private String errorMessage;
}
