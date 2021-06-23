package app;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class ApplicationMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder().main(ApplicationMain.class).sources(ApplicationConfiguration.class).run(args);
    }

}
