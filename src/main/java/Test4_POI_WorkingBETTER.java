import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.io.*;

public class Test4_POI_WorkingBETTER {

    public static void main(String[] args) {
        String inputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_Template.docx"; // Input file path
        String outputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_OP.docx"; // Output file path
        String searchString = "dt_Contract.StartDate"; // String to search and wrap

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            XWPFDocument document = new XWPFDocument(fis);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                int runCount = paragraph.getRuns().size();
                for (int i = 0; i < runCount; i++) {
                    XWPFRun run = paragraph.getRuns().get(i);
                    String text = run.getText(0);
                    if (text != null && text.contains(searchString)) {
                        String before = text.substring(0, text.indexOf(searchString));
                        String after = text.substring(text.indexOf(searchString) + searchString.length());

                        run.setText(before, 0); // Set text before the search string

                        // Create structured document tag run
                        paragraph= wrapInContentBoundary(paragraph,searchString);

                        // Add remaining text
                        paragraph.insertNewRun(i + 2).setText(after);

                    }
                }
            }

            document.write(fos);
            System.out.println("Document processed successfully with content boundaries.");

        } catch (IOException e) {
            System.err.println("Error processing the document: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static XWPFParagraph wrapInContentBoundary(XWPFParagraph paragraph, String keyword) {

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
        return paragraph;
    }
}
