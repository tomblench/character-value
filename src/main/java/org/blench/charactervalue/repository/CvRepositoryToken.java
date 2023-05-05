package org.blench.charactervalue.repository;

import java.util.Objects;
import java.util.Random;

public class CvRepositoryToken {

    private final String token;

    private static final Random random = new Random();

    public static final CvRepositoryToken DEFAULT = new CvRepositoryToken("_default");

    private static final int LENGTH = 20;
    public CvRepositoryToken() {
        var temp = new StringBuffer();
        for (int i=0; i<LENGTH; i++) {
            temp.append((char) ('a' + random.nextInt(26)));
        }
        token = temp.toString();
    }

    public CvRepositoryToken(String token) {
        this.token = token;
    }

    public String asString() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CvRepositoryToken that = (CvRepositoryToken) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
