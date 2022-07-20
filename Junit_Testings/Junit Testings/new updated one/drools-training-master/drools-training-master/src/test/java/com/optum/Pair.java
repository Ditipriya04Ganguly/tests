package com.optum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pair {
    int pid;
    Set<String> disease;

    public Pair(int pid, String... diseases) {
        this.pid = pid;

        this.disease = new HashSet<String>();
        if (diseases!=null){
            disease.addAll(Arrays.asList(diseases));
        }
    }
}
