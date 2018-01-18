package com.aold619.shawn.filmhelloworld;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void jsonTest() throws Exception {
        assertTrue(fileTest());
    }

    private boolean fileTest() throws Exception {
//        System.out.println(System.getProperty("user.dir"));
        File file = new File("/movie.json");

        try {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getPath());
                InputStreamReader inputStreamReader = new InputStreamReader(
                        new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String jsonStr = bufferedReader.readLine();
                System.out.println(jsonStr);

                JSONObject jsonObject = new JSONObject(jsonStr);
                System.out.println();
//                JSONArray jsonArray = jsonObject.toJSONArray();

                bufferedReader.close();
                inputStreamReader.close();
                return true;
            } else {
                System.out.println("文件路径错误");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}