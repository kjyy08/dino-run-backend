package com.juyb99.dinorun;

import com.juyb99.dinorun.config.AppConfig;
import jakarta.servlet.*;
import com.juyb99.dinorun.filter.EncodingFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CustomWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");
        servletRegistration.setMultipartConfig(new MultipartConfigElement("/"));

        FilterRegistration encodingFilterRegistration = servletContext.addFilter("encodingFilter", new EncodingFilter());
        encodingFilterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }
}