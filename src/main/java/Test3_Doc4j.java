
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


public class Test3_Doc4j {

    public static void main(String[] args) throws Exception {

        String regex = "\\{\\{[ ]*dt_[a-zA-Z0-9_.]+[ ]*\\}\\}";
        String filePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_Template.docx"; // Replace with your DOCX file path
        String outputFilePath = "/Users/adarsh.singh/Downloads/Spike Data True up/New gpt tests/Inp_OP.docx";
        String keyword="Data";

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filePath));

        //Find and Wrap
        findAndWrapKeyword(wordMLPackage, keyword);


        // Save the modified DOCX
        File outputFile = new File(outputFilePath);
        Docx4J.save(wordMLPackage, outputFile);

        System.out.println("Keyword '" + regex + "' wrapped and saved to: " + outputFilePath);


    }


    public static void findAndWrapKeyword(WordprocessingMLPackage wordMLPackage, String keyword) throws Exception {

        List<Object> allParagraphs = getAllElementFromDocument(wordMLPackage, P.class);


        for (Object pObject : allParagraphs) {
            P paragraph = (P) pObject;
            List<Object> runs = getAllElementFromParagraph(paragraph, R.class); // Extract runs (R elements)


            //Reconstruct text for searching using full paragraph contents
            StringBuilder paragraphTextBuilder = new StringBuilder();
            for (Object runObj : runs) {
                R run = (R) runObj;

                for (Object textObj : getAllElementFromParagraph(run, Text.class)) {
                    Text text = (Text) textObj;
                    paragraphTextBuilder.append(text.getValue());
                }
            }

            String paragraphText = paragraphTextBuilder.toString();
//            Pattern pattern = Pattern.compile(regex);
            int startIndex = paragraphText.indexOf(keyword);

            if (startIndex != -1) {
                // Keyword found in this paragraph
                int endIndex = startIndex + keyword.length();

                //Perform split runs. Because DOCX are complex.
                //If it spans across multiple Run items( R element). Split appropriately.
                List<Object> newRuns = splitRunsAroundKeyword(paragraph, runs, keyword, startIndex, endIndex, wordMLPackage);

                //replace runs on parent (Paragraph)

                paragraph.getContent().clear();
                paragraph.getContent().addAll(newRuns);  //Replace Runs

                break; // stop after first found, remove the break, and iterate entire paragraph in Document if you need


            }

        }
    }


    private static List<Object> splitRunsAroundKeyword(P paragraph, List<Object> runs, String keyword, int startIndex, int endIndex, WordprocessingMLPackage wordMLPackage) throws Exception {
        List<Object> newRuns = new ArrayList<>();
        int currentPosition = 0;
        int originalStartIndex = startIndex;
        int originalEndIndex = endIndex;


        for (Object runObj : runs) {
            R run = (R) runObj;


            StringBuilder runTextBuilder = new StringBuilder();

            List<Object> textElements = getAllElementFromParagraph(run, Text.class);
            if (textElements == null || textElements.isEmpty()) {
                //Not have text in run keep current
                newRuns.add(run);
                continue; // to avoid process on empty runs

            }

            //Reconstruct text on a Run for processing splits
            Text textObj = (Text) textElements.get(0); //For now, each R is handled only a single text (normal usage);
            String runText = textObj.getValue();
            int runLength = runText.length();


            if (startIndex >= currentPosition && startIndex < currentPosition + runLength) {

                if (startIndex > currentPosition) {
                    //1) Prefix

                    R prefixRun = (R) XmlUtils.deepCopy(run); // Cloning
                    List<Object> prefixTextElements = getAllElementFromParagraph(prefixRun, Text.class);
                    Text prefixText = (Text) prefixTextElements.get(0);

                    prefixText.setValue(runText.substring(0, startIndex - currentPosition)); // setting new value
                    newRuns.add(prefixRun);
                }

                //2)  Wrap Keyword and mark its boundary by Markup
                R keywordRun = createProtectedKeywordRun(run, runText.substring(startIndex - currentPosition, Math.min(endIndex, currentPosition + runLength) - currentPosition), wordMLPackage);
                newRuns.add(keywordRun);


                if (endIndex <= currentPosition + runLength) {
                    // 3)  Suffix remain in original Run
                    R suffixRun = (R) XmlUtils.deepCopy(run);
                    List<Object> suffixTextElements = getAllElementFromParagraph(suffixRun, Text.class);
                    Text suffixText = (Text) suffixTextElements.get(0);


                    suffixText.setValue(runText.substring(endIndex - currentPosition));
                    newRuns.add(suffixRun);
                    startIndex = originalEndIndex + 1; //reset to over end , avoid again
                    break; // to skip  all paragraph check, will continue process on later Run if it has text that matches;
                } else {

                    startIndex = currentPosition + runLength; //update location


                }


            } else {


                //Add all others text from Runs

                newRuns.add(run);

            }


            currentPosition += runLength;


        }//Loop RUN


        return newRuns;
    }

    private static R createProtectedKeywordRun(R originalRun, String keyword, WordprocessingMLPackage wordMLPackage) throws Exception {
        R keywordRun = (R) XmlUtils.deepCopy(originalRun);

        List<Object> keywordTextElements = getAllElementFromParagraph(keywordRun, Text.class);
        Text keywordText = (Text) keywordTextElements.get(0);
        keywordText.setValue(keyword);

        //wrap and make protect for EDITING_RESTRICTION

        keywordRun = addContentControl(keywordRun, wordMLPackage);


        return keywordRun;


    }

    public static R addContentControl(R keywordRun, WordprocessingMLPackage wordMLPackage) throws Exception {


        // Use Content Control rather than legacy formfield to be editing restriction
        // 1.  Create a structured document tag (SDT).

        //Create SCT
        org.docx4j.wml.SdtBlock sdt = org.docx4j.jaxb.Context.getWmlObjectFactory().createSdtBlock();
        org.docx4j.wml.SdtPr sdtPr = org.docx4j.jaxb.Context.getWmlObjectFactory().createSdtPr();
        org.docx4j.wml.CTDataBinding ctdatabinding = org.docx4j.jaxb.Context.getWmlObjectFactory().createCTDataBinding();
        org.docx4j.wml.Tag tag = org.docx4j.jaxb.Context.getWmlObjectFactory().createTag();

        //Create the SCT properties and tagging data
        tag.setVal("UneditableProtectedKeyword_" + UUID.randomUUID().toString()); // you may modify/add to be unique per documents

        //tag.setValue("UneditableProtectedKeyword_" + System.nanoTime() +new Random().nextInt(1000));

        sdtPr.setTag(tag);

        SdtContent sdtContent = org.docx4j.jaxb.Context.getWmlObjectFactory().createSdtContentBlock();
        sdt.setSdtPr(sdtPr);


        P p = org.docx4j.jaxb.Context.getWmlObjectFactory().createP();
        p.getContent().add(keywordRun);

        sdtContent.getContent().add(p);
        sdt.setSdtContent(sdtContent);

        //Insert the SCT into the document, then remove run on the document.
        return keywordRun;
    }

    private static List<Object> getAllElementFromDocument(WordprocessingMLPackage wordMLPackage, Class<?> target) {
        List<Object> results = new ArrayList<>();
        List<Object> allDocumentObjects = wordMLPackage.getMainDocumentPart().getContent();

        getAllElementFromObject(allDocumentObjects, target, results);

        return results;
    }


    //Helper method, use in spliting runs and wrap
    private static List<Object> getAllElementFromParagraph(Object paragraph, Class<?> target) {
        List<Object> results = new ArrayList<>();
        if (paragraph instanceof org.docx4j.wml.P || paragraph instanceof org.docx4j.wml.R) {

            List<Object> paragraphObjects = new ArrayList<>();

            if (paragraph instanceof org.docx4j.wml.P) {
                paragraphObjects = ((org.docx4j.wml.P) paragraph).getContent();
            } else {

                paragraphObjects = ((org.docx4j.wml.R) paragraph).getContent();
            }
            getAllElementFromObject(paragraphObjects, target, results); // Recursive helper, this
        }
        return results;
    }


    private static void getAllElementFromObject(List<Object> objList, Class<?> target, List<Object> results) {
        for (Object obj : objList) {
            obj = XmlUtils.unwrap(obj);

            if (obj.getClass().equals(target)) {
                results.add(obj);
            } else if (obj instanceof org.docx4j.wml.P ||  //Checking instances instead of classes is crucial, DOCX is complex
                    obj instanceof org.docx4j.wml.R) {

                List<Object> nextLevelObjects = new ArrayList<>(); //Dynamic
                if (obj instanceof org.docx4j.wml.P) {
                    nextLevelObjects = ((org.docx4j.wml.P) obj).getContent();
                } else {
                    nextLevelObjects = ((org.docx4j.wml.R) obj).getContent();

                }

                getAllElementFromObject(nextLevelObjects, target, results);
            }

            //Check content controls recursivelly.  << Important as ofen used SCT Tag in Document.
            if (obj instanceof org.docx4j.wml.SdtBlock) {

                SdtBlock sdtBlock = (SdtBlock) obj;
                List<Object> nextLevelObjects = new ArrayList<>();
                if (sdtBlock != null) {
                    SdtContent sdtContent = sdtBlock.getSdtContent();
                    if (sdtContent != null) {
                        nextLevelObjects = sdtContent.getContent();

                        getAllElementFromObject(nextLevelObjects, target, results);
                    }


                }


            }
            if (obj instanceof org.docx4j.wml.SdtRun) {
                SdtRun sdtRun = (SdtRun) obj;

                List<Object> nextLevelObjects = new ArrayList<>();
                if (sdtRun != null) {
                    SdtContent sdtContent = sdtRun.getSdtContent();
                    if (sdtContent != null) {
                        nextLevelObjects = sdtContent.getContent();

                        getAllElementFromObject(nextLevelObjects, target, results);

                    }


                }
            }
        }
    }

}
