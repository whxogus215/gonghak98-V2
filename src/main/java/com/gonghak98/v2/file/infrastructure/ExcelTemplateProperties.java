package com.gonghak98.v2.file.infrastructure;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "excel.template.validation")
@Getter
@Setter
public class ExcelTemplateProperties {

    private String title;
    private int headerRowIndex;
    private List<String> defaultHeaders;
}
