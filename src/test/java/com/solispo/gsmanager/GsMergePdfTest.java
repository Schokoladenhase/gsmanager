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
public class GsMergePdfTest {

    @Test
    public void TestSuccesfulMergeWithBenchmark() throws IOException {
        GsMergePdf pdfMerger=new GsMergePdf();
        Instant start= Instant.now();
        List<String> inputfiles=Files.readAllLines(Paths.get("./src/test/resources/gs_input_file_list/gsinputfile.lst"));
        log.info("Number of Files: "+inputfiles.size());
        pdfMerger.merge(inputfiles);
        log.info("Merged all files.\n" +
                "Duration: "+ Duration.between(start,Instant.now()));

    }
}