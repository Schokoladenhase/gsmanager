package com.solispo.gsmanager;

import java.io.IOException;
import java.util.List;

/**
 * Created by joerg on 26.05.2018.
 */
public interface MergePdf {
    void merge(List<String> inputfiles) throws IOException;
}
