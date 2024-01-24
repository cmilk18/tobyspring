package com.example.tobyspring.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal)throws IOException{
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
            String line = null;
            while((line = br.readLine())!=null){
                res = callback.doSomethingWithLine(line,res);
            }
            return res;
        }
        catch (IOException e){
            System.out.println("e.getMessage() = " + e.getMessage());
            throw e;
        }
        finally {
            if(br != null){
                try {
                    br.close();
                }
                catch(IOException e){
                    System.out.println("e.getMessage() = " + e.getMessage());
                    throw e;
                }
            }
        }

    }
    public Integer calSum(String filepath)throws IOException {
        LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line,Integer value) {
                return value + Integer.valueOf(line);
            }

        };
        return lineReadTemplate(filepath,sumCallback,0);
    }
    public Integer calMultiply(String filepath)throws IOException {
        LineCallback<Integer> mulCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line,Integer value) {
                return value * Integer.valueOf(line);
            }

        };
        return lineReadTemplate(filepath,mulCallback,1);
    }

    public String concatenate(String filepath)throws IOException{
        LineCallback<String> concatenateCallback = new LineCallback<String>() {
            @Override
            public String doSomethingWithLine(String line,String value) {
                return value + line;
            }

        };
        return lineReadTemplate(filepath,concatenateCallback,"");
    }
}
