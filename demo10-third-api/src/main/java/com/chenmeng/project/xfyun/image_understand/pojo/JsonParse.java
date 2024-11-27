package com.chenmeng.project.xfyun.image_understand.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenmeng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonParse {

    Header header;
    Payload payload;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        int code;
        int status;
        String sid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payload {
        Choices choices;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choices {
        List<Text> text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Text {
        String role;
        String content;
    }
}
