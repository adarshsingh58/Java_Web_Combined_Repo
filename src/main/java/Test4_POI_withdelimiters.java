import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test4_POI_withdelimiters {
    public static void main(String[] args) {
        String inputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_Template.docx"; // Input file path
        String outputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/output1.docx"; // Output file path
        String regex = "\\{{2}[^}]*\\}{2}"; // Regex pattern to match {{placeholder}}

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            XWPFDocument document = new XWPFDocument(fis);
            Pattern pattern = Pattern.compile(regex);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                if (!runs.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    List<XWPFRun> runList = new ArrayList<>();

                    // Concatenate all text in runs and map each index to its corresponding run
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            sb.append(text);
                            runList.add(run);
                        }
                    }

                    Matcher matcher = pattern.matcher(sb.toString());
                    List<String> matches = new ArrayList<>();
                    List<Integer> matchStarts = new ArrayList<>();
                    List<Integer> matchEnds = new ArrayList<>();

                    while (matcher.find()) {
                        matches.add(matcher.group());
                        matchStarts.add(matcher.start());
                        matchEnds.add(matcher.end());
                    }

                    if (!matches.isEmpty()) {
                        // Remove all existing runs
                        for (int i = runs.size() - 1; i >= 0; i--) {
                            paragraph.removeRun(i);
                        }

                        int currentPos = 0;
                        for (int i = 0; i < matches.size(); i++) {
                            int start = matchStarts.get(i);
                            int end = matchEnds.get(i);

                            // Insert normal text before placeholder
                            if (currentPos < start) {
                                XWPFRun normalRun = paragraph.createRun();
                                normalRun.setText(sb.substring(currentPos, start));
                            }

                            // Insert placeholder wrapped in content boundary
//                            XWPFRun sdtRun = paragraph.createRun();
                            wrapInContentBoundary(paragraph, matches.get(i));

                            currentPos = end;
                        }

                        // Insert remaining text after last match
                        if (currentPos < sb.length()) {
                            XWPFRun finalRun = paragraph.createRun();
                            finalRun.setText(sb.substring(currentPos));
                        }
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
