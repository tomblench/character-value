package org.blench.charactervalue.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.blench.charactervalue.businesslogic.PdfGenerator;
import org.blench.charactervalue.model.Cv;
import org.blench.charactervalue.repository.CvRepository;
import org.blench.charactervalue.repository.CvRepositoryToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import java.io.IOException;

/**
 * Configuration class to construct and inject main Beans
 */
@Configuration
public class Beans {

    @Value("classpath:demo-cv-data.yaml")
    Resource demoCvData;

    @Value("classpath:Palatino.ttc")
    Resource font1;

    @Value("${template.name}")
    String templateName;

    @Value("${flip.demo.cv.data}")
    Boolean flipDemoCvData;

    @Bean
    PdfGenerator templating(@Autowired SpringResourceTemplateResolver templateResolver,
                            @Autowired SpringTemplateEngine templateEngine) {
        return new PdfGenerator(templateResolver, templateEngine, templateName, font1.getFilename());
    }

    @Bean
    CvRepository cvRepository() {
        var cvRepo = new CvRepository();
        if (flipDemoCvData && demoCvData != null) {
            try {
                var mapper = new ObjectMapper(new YAMLFactory());
                Cv cvModel = mapper.readValue(demoCvData.getFile(), Cv.class);
                cvRepo.put(cvModel, CvRepositoryToken.DEFAULT);
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
        return cvRepo;
    }
}