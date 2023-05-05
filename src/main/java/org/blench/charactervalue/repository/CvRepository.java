package org.blench.charactervalue.repository;

import org.blench.charactervalue.model.Cv;

import java.util.HashMap;
import java.util.Map;

public class CvRepository {

    // cvs, keyed by token
    private Map<CvRepositoryToken, Cv> cvs;

    public CvRepository() {
        cvs = new HashMap<>();
    }

    public Cv get(CvRepositoryToken token) {
        return cvs.get(token);
    }

    public CvRepositoryToken put(Cv cv) {
        var token = new CvRepositoryToken();
        cvs.put(token, cv);
        return token;
    }

    public CvRepositoryToken put(Cv cv, CvRepositoryToken token) {
        cvs.put(token, cv);
        return token;
    }

}
