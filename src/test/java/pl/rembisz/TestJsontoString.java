package pl.rembisz;

import com.fasterxml.jackson.databind.ObjectMapper;


public class TestJsontoString {


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
