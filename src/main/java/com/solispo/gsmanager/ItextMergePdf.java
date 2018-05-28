package com.solispo.gsmanager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfACopy;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ItextMergePdf implements MergePdf {

    public void merge(List<String> inputfiles) throws IOException {
        Document document = new Document();
        PdfCopy copy = null;
        try {
            copy = new PdfCopy(document, new FileOutputStream("merge-pdf-result.pdf"));
        } catch (DocumentException e) {
            throw new IOException(e);
        }
        document.open();
        PdfReader.unethicalreading=true;
        for (String file : inputfiles){
            PdfReader reader = new PdfReader(file);
            try {
                copy.addDocument(reader);
            } catch (DocumentException e) {
                throw new IOException(e);
            }
            copy.freeReader(reader);
            reader.close();
        }
        document.close();
    }
}