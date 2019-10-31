package com.springcloud.book.decompression;

import com.springcloud.book.decompression.util.ZIPUtill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DecompressionApplicationTests {

    @Test
    public void test() {
    }

    public static void main(String[] args) throws Exception {
        Set<String> catalogList = new HashSet<>();
        File file = new File("D:\\unzip\\2019\\00110640R\\12-NA\\11609742.xml");
        String path = file.getCanonicalPath();
        Path paths = Paths.get(path);
        List<String> pathList = new ArrayList<>();
        for(Iterator<Path> it = paths.iterator(); it.hasNext();) {
            pathList.add(it.next().toString());
        }
        if (pathList.size() >= 3){
            catalogList.add(pathList.get(pathList.size() - 3));
        }
        System.out.println(catalogList);
    }

}
