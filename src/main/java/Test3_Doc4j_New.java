import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3_Doc4j_New {

    public static void main(String[] args) throws Exception {
        // Regular expression pattern (Only matches `{{dt_...}}` tokens)
        String regex = "\\{\\{\\s*dt_[a-zA-Z0-9_.]+\\s*\\}\\}";

        String filePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_Template.docx";
        String outputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_OP.docx";

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filePath));

        // Find and wrap occurrences matching regex
        findAndWrapRegex(wordMLPackage, regex);

        // Save the modified document
        File outputFile = new File(outputFilePath);
        Docx4J.save(wordMLPackage, outputFile);

        System.out.println("Regex '" + regex + "' wrapped and saved to: " + outputFilePath);
    }

    public static void findAndWrapRegex(WordprocessingMLPackage wordMLPackage, String regex) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        List<Object> allParagraphs = getAllElementFromDocument(wordMLPackage, P.class);

        for (Object pObject : allParagraphs) {
            P paragraph = (P) pObject;
            List<Object> runs = getAllElementFromParagraph(paragraph, R.class);

            // Reconstruct full paragraph text
            StringBuilder paragraphTextBuilder = new StringBuilder();
            for (Object runObj : runs) {
                R run = (R) runObj;
                for (Object textObj : getAllElementFromParagraph(run, Text.class)) {
                    Text text = (Text) textObj;
                    paragraphTextBuilder.append(text.getValue());
                }
            }

            String paragraphText = paragraphTextBuilder.toString();
            Matcher matcher = pattern.matcher(paragraphText);

            if (matcher.find()) {
                List<Object> newRuns = processRegexMatches(paragraph, runs, matcher, wordMLPackage);

                // Replace paragraph content with updated runs
                paragraph.getContent().clear();
                paragraph.getContent().addAll(newRuns);
            }
        }
    }

    private static List<Object> processRegexMatches(P paragraph, List<Object> runs, Matcher matcher, WordprocessingMLPackage wordMLPackage) throws Exception {
        List<Object> newRuns = new ArrayList<>();
        int currentPosition = 0;

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            String matchText = matcher.group();

            newRuns.addAll(splitRunsAndWrap(paragraph, runs, matchText, startIndex, endIndex, wordMLPackage));
            currentPosition = endIndex;
        }

        return newRuns;
    }

    private static List<Object> splitRunsAndWrap(P paragraph, List<Object> runs, String matchText, int startIndex, int endIndex, WordprocessingMLPackage wordMLPackage) throws Exception {
        List<Object> newRuns = new ArrayList<>();
        int currentPosition = 0;

        for (Object runObj : runs) {
            R run = (R) runObj;
            List<Object> textElements = getAllElementFromParagraph(run, Text.class);

            if (textElements.isEmpty()) {
                newRuns.add(run);
                continue;
            }

            Text textObj = (Text) textElements.get(0);
            String runText = textObj.getValue();
            int runLength = runText.length();

            if (startIndex >= currentPosition && startIndex < currentPosition + runLength) {
                if (startIndex > currentPosition) {
                    R prefixRun = (R) XmlUtils.deepCopy(run);
                    ((Text) getAllElementFromParagraph(prefixRun, Text.class).get(0))
                            .setValue(runText.substring(0, startIndex - currentPosition));
                    newRuns.add(prefixRun);
                }
                int safeStartIndex = Math.max(0, startIndex - currentPosition);
                int safeEndIndex = Math.min(runText.length(), endIndex - currentPosition);

// Only proceed if valid substring range
                if (safeStartIndex < safeEndIndex) {
                    SdtRun wrappedRun = createProtectedKeywordRun(run, runText.substring(safeStartIndex, safeEndIndex), wordMLPackage);
                    newRuns.add(wrappedRun);
                }

                if (endIndex <= currentPosition + runLength) {
                    R suffixRun = (R) XmlUtils.deepCopy(run);
                    ((Text) getAllElementFromParagraph(suffixRun, Text.class).get(0))
                            .setValue(runText.substring(endIndex - currentPosition));
                    newRuns.add(suffixRun);
                    break;
                } else {
                    startIndex = currentPosition + runLength;
                }
            } else {
                newRuns.add(run);
            }

            currentPosition += runLength;
        }

        return newRuns;
    }

    private static SdtRun createProtectedKeywordRun(R originalRun, String matchText, WordprocessingMLPackage wordMLPackage) throws Exception {
        R keywordRun = (R) XmlUtils.deepCopy(originalRun);
        List<Object> keywordTextElements = getAllElementFromParagraph(keywordRun, Text.class);
        if (!keywordTextElements.isEmpty()) {
            ((Text) keywordTextElements.get(0)).setValue(matchText);
        }
        return addContentControl(keywordRun, wordMLPackage);
    }

    public static SdtRun addContentControl(R keywordRun, WordprocessingMLPackage wordMLPackage) throws Exception {
        SdtRun sdtRun = Context.getWmlObjectFactory().createSdtRun();
        SdtPr sdtPr = Context.getWmlObjectFactory().createSdtPr();
        Tag tag = Context.getWmlObjectFactory().createTag();
        tag.setVal("Protected_" + UUID.randomUUID().toString());
        sdtPr.setTag(tag);
        sdtRun.setSdtPr(sdtPr);

        CTSdtContentRun sdtContentRun = Context.getWmlObjectFactory().createCTSdtContentRun();
        sdtContentRun.getContent().add(keywordRun);
        sdtRun.setSdtContent(sdtContentRun);

        return sdtRun;
    }

    private static List<Object> getAllElementFromDocument(WordprocessingMLPackage wordMLPackage, Class<?> target) {
        List<Object> results = new ArrayList<>();
        getAllElementFromObject(wordMLPackage.getMainDocumentPart().getContent(), target, results);
        return results;
    }

    private static List<Object> getAllElementFromParagraph(Object paragraph, Class<?> target) {
        List<Object> results = new ArrayList<>();
        if (paragraph instanceof ContentAccessor) {
            getAllElementFromObject(((ContentAccessor) paragraph).getContent(), target, results);
        }
        return results;
    }

    private static void getAllElementFromObject(List<Object> objList, Class<?> target, List<Object> results) {
        for (Object obj : objList) {
            obj = XmlUtils.unwrap(obj);
            if (target.isInstance(obj)) {
                results.add(obj);
            } else if (obj instanceof ContentAccessor) {
                getAllElementFromObject(((ContentAccessor) obj).getContent(), target, results);
            }
        }
    }
}
