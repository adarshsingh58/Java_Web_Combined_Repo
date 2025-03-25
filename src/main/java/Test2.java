/*
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.List;

public class Test2 {

    public static void main(String[] args) {
        String inputFile = "input.docx";
        String outputFile = "output.docx";
        String keyword = "Data";

        try {
            // Load the Word document
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(inputFile));
            List<Object> paragraphs = wordMLPackage.getMainDocumentPart().getContent();

           */
/* for (int i = 0; i < paragraphs.size(); i++) {
                Object obj = paragraphs.get(i);
                if (obj instanceof JAXBElement) {
                    obj = ((JAXBElement<?>) obj).getValue();
                }

                if (obj instanceof P) {
                    P paragraph = (P) obj;
                    String paragraphText = getText(paragraph);

                    if (paragraphText.contains(keyword)) {
                        System.out.println("Keyword found: " + keyword);

                       *//*
*/
/* // Wrap keyword with content control
                        SdtBlock contentControl = createContentControl(keyword);
                        paragraphs.set(i, contentControl);*//*
*/
/*
                    }
                }
            }*//*


            // Save the modified document
            wordMLPackage.save(new File(outputFile));
            System.out.println("Document created successfully with non-editable sections!");

        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    private static SdtBlock createContentControl(String text) {
        ObjectFactory factory = new ObjectFactory();
        SdtBlock sdtBlock = factory.createSdtBlock();

        // Set Lock to make the section non-editable
        SdtPr sdtPr = factory.createSdtPr();
        CTLock lock = factory.createCTLock();
        lock.setVal(STLock.SDT_CONTENT_LOCKED);
        sdtPr.getRPrOrAliasOrLock().add(lock);
        sdtBlock.setSdtPr(sdtPr);

        // Add the text inside content control
        SdtContentBlock contentBlock = factory.createSdtContentBlock();
        P paragraph = factory.createP();
        R run = factory.createR();
        Text textElement = factory.createText();
        textElement.setValue(text);
        run.getContent().add(factory.createRT(textElement));
        paragraph.getContent().add(run);
        contentBlock.getContent().add(paragraph);
        sdtBlock.setSdtContent(contentBlock);

        return sdtBlock;
    }

    private static String getText(P paragraph) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : paragraph.getContent()) {
            if (obj instanceof R) {
                R run = (R) obj;
                for (Object content : run.getContent()) {
                    if (content instanceof JAXBElement) {
                        Object contentValue = ((JAXBElement<?>) content).getValue();
                        if (contentValue instanceof Text) {
                            sb.append(((Text) contentValue).getValue());
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}
*/
