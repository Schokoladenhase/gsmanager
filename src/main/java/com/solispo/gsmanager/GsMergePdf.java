package com.solispo.gsmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Created by joerg on 26.05.2018.
 */
public class GsMergePdf implements MergePdf{
    public void merge(List<String> inputfiles) throws IOException {
        int process_count=1;
        ExecutorService gsExec = Executors.newSingleThreadExecutor();
        List<Callable<String>> gsInstances=new ArrayList<>();
        for (int i = 0; i < process_count; i++) {
            //String runGsString = "C:\\Program Files\\gs\\gs9.23\\bin\\gswin64c -dNOPAUSE -sDEVICE=pdfwrite -sOutputFile=gs_merged_output_"+i+".pdf -dBATCH @C:\\Temp\\gsinputfile.lst";
            String runGsString = "C:\\Program Files\\gs\\gs9.23\\bin\\gswin64c -dNOPAUSE -sDEVICE=pdfwrite -sOutputFile=gs_merged_output_"+i+".pdf -";
            ProcessTaskRunner processTaskRunner =
                    new ProcessTaskRunner(runGsString, System.out::println);
            gsInstances.add(processTaskRunner);
        }
        try {
            gsExec.invokeAll(gsInstances);
            ((ProcessTaskRunner)gsInstances.get(0)).process.getOutputStream().write(Files.readAllBytes(Paths.get("C:\\Temp\\example3.pdf")));
            ((ProcessTaskRunner)gsInstances.get(0)).process.getOutputStream().write("quit".getBytes());
            gsExec.shutdown();
            if (!gsExec.awaitTermination(800, TimeUnit.SECONDS)) {
                gsExec.shutdownNow();
            }
        } catch (InterruptedException e) {
            gsExec.shutdownNow();
        }
        int exitCode = 0;
        assert exitCode == 0;
    }

    private class ProcessTaskRunner implements Callable<String> {
        private InputStream inputStream;
        private Consumer<String> consumer;
        private String commandString;
        private Integer exitcode;
        public Process process;

        public ProcessTaskRunner(String commandString, Consumer<String> consumer) {
            this.commandString=commandString;
            try {
                this.process = Runtime.getRuntime().exec(this.commandString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = process.getInputStream();
            this.consumer = consumer;
        }

        @Override
        public String call() throws Exception {
            try {
                this.exitcode = process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return process.getOutputStream().toString();
        }
    }
}
