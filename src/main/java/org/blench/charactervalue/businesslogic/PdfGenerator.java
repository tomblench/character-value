package org.blench.charactervalue.businesslogic;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.blench.charactervalue.model.Cv;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.List;

public class PdfGenerator {

    SpringResourceTemplateResolver templateResolver;

    SpringTemplateEngine templateEngine;

    String templateName;

    String fonts[];

    public PdfGenerator(SpringResourceTemplateResolver templateResolver,
                        SpringTemplateEngine templateEngine,
                        String templateName,
                        String... fonts) {
        this.templateResolver = templateResolver;
        this.templateEngine = templateEngine;
        this.templateName = templateName;
        this.fonts = fonts;
    }

    public String parseThymeleafTemplate(Cv cvModel) {
        var context = new Context();
        context.setVariable("cv", cvModel);
        return templateEngine.process(templateName, context);
    }

    public byte[] generatePdfFromHtml(String html) throws DocumentException, IOException {
        var outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        for (String f : this.fonts) {
            renderer.getFontResolver().addFont(f,
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED);
        }
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        return outputStream.toByteArray();
    }

}
