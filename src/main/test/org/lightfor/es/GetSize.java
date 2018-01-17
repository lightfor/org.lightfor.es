package org.lightfor.es;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.List;

/**
 * @author Light
 * @date 2018-01-17
 */
public class GetSize {

    @Test
    public void test() {
        List<Integer> sizes = JsonPath.read(HttpClient.get("http://localhost:9200/_stats"), "$.indices.*.primaries.store.size_in_bytes");
        System.out.println(sizes);
    }
}
