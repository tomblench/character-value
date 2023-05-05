package org.blench.charactervalue.controller;

import com.lowagie.text.DocumentException;
import org.blench.charactervalue.businesslogic.PdfGenerator;
import org.blench.charactervalue.model.Cv;
import org.blench.charactervalue.repository.CvRepository;
import org.blench.charactervalue.repository.CvRepositoryToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ApiV1 {

    @Autowired
    PdfGenerator pdfGenerator;

    @Autowired
    CvRepository cvRepository;

    @PostMapping(value = "/uploadYaml", consumes = "application/yaml", produces = "text/plain")
    @ResponseBody
    public String uploadYaml(@RequestBody Cv modelFile) throws IOException {
        var token = cvRepository.put(modelFile);
        return token.asString() + "\n";
    }

    @GetMapping(value = "/yamlFromToken", produces = "application/yaml")
    @ResponseBody
    public Cv yamlFromToken(@RequestParam String token) throws DocumentException, IOException {
        return cvRepository.get(new CvRepositoryToken(token));
    }

    @GetMapping(value = "/pdfFromToken", produces = "application/pdf")
    @ResponseBody
    public byte[] pdfFromToken(@RequestParam String token) throws DocumentException, IOException {
        Cv cvModel = cvRepository.get(new CvRepositoryToken(token));
        String output = pdfGenerator.parseThymeleafTemplate(cvModel);
        return pdfGenerator.generatePdfFromHtml(output);
    }
}
