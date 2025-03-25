
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test_POI_Working {
    public static void main(String[] args) {
        String inputFilePath = "input.docx";
        String outputFilePath = "output.docx";
        String keyword = "Data";

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Iterate through all paragraphs
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();

                if (text.contains(keyword)) {
                    System.out.println("Keyword found: " + keyword);

                    wrapInContentBoundary(paragraph, keyword);
                }
            }

            // Save the updated document
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                document.write(fos);
                System.out.println("Document protected successfully with content control.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void wrapInContentBoundary(XWPFParagraph paragraph, String keyword) {
        // Clear existing runs in the paragraph
        for (int i = 0; i < paragraph.getRuns().size(); i++) {
            paragraph.removeRun(0);
        }

        // Create Structured Document Tag (SDT)
        CTSdtRun sdtRun = CTSdtRun.Factory.newInstance();
        CTSdtPr sdtPr = sdtRun.addNewSdtPr();

        // Lock the content (non-editable)
        CTLock lock = sdtPr.addNewLock();
        lock.setVal(STLock.SDT_CONTENT_LOCKED);
        sdtPr.addNewTag().setVal("sds");

        // Create the content inside the Content Control
        CTSdtContentRun sdtContent = sdtRun.addNewSdtContent();
        CTR ctr = sdtContent.addNewR();
        ctr.addNewT().setStringValue(keyword);

        // Append the SDT to the paragraph
        paragraph.getCTP().addNewSdt().set(sdtRun);
    }
}
