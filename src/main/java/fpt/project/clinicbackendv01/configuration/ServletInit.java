package fpt.project.clinicbackendv01.configuration;

import fpt.project.clinicbackendv01.ClinicbackendV01Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInit extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ClinicbackendV01Application.class);
    }
}
