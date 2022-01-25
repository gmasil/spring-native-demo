package de.gmasil.demo.springnative;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/secure")
    public String secure() {
        return "This is secure!";
    }
}
