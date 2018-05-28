package com.solispo.gsmanager;

import com.itextpdf.text.DocumentException;
import lombok.extern.java.Log;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by joerg on 26.05.2018.
 */
@Log
public class ItextMergePdfTest {

    @Test
    public void TestSuccesfulMergeWithBenchmark() throws IOException, DocumentException {
        ItextMergePdf pdfMerger=new ItextMergePdf();
        Integer iterations=4;
        List<String> inputfiles = new ArrayList<>();
        List<String> onetimefiles=Files
                .list(
                        Paths.get("./src/test/resources/gs_chunks"))
                .map(p->p.toAbsolutePath().toString())
                .collect(Collectors.toList());
        for (int i = 0; i < iterations; i++) {
            inputfiles.addAll(onetimefiles);
        }
        Instant start= Instant.now();
        pdfMerger.merge(inputfiles);
        log.info("Merged all files.\n" +
                "Total Files: " + inputfiles.size()+"\n"+
                "Duration: "+ Duration.between(start,Instant.now()));

    }
}