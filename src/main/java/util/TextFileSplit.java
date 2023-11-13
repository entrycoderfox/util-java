/**
 * licenses:AGPL v3
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * TextSplit
 * 将大文本文件切按照最大行数，切分为不同的小文本
 *
 * @date 2023/7/11 16:04
 */
public class TextFileSplit {

    public static void main(String[] args) {
        TextFileSplit textSplit = new TextFileSplit();
//        textSplit.splitText("D:\\util_test\\SystemOut.log", "D:\\util_test\\splitFiles\\", "out_", ".txt", 100000);
        textSplit.splitText("D:\\SystemOut.log", "D:\\split\\", "out_", ".log", 100000);
    }

    public void splitText(String inFilePath, String outPath, String outSuffix, String outFileType, int maxLineCount) {
        try {
            if (inFilePath == null || inFilePath.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传入的输出路径为空");
            }
            if (maxLineCount <= 0) {
                throw new IllegalArgumentException("传入的行数有误");
            }
            File inFile = new File(inFilePath);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Stream<String> lineStream = bufferedReader.lines();
            long allLineCount = lineStream.count();
            int outFileCount = (int) (allLineCount / maxLineCount);
            if (allLineCount % maxLineCount != 0) {//最后一个文件不是刚好填满
                outFileCount++;//需要多一个文件
            }

            //需要重新读取一遍
            fileReader = new FileReader(inFile);
            bufferedReader = new BufferedReader(fileReader);

            for (int i = 0; i < outFileCount; i++) {//生成文件
                //先生成一下文件
                File outFile = new File(outPath + outSuffix + (i + 1) + outFileType);
                FileWriter fileWriter = new FileWriter(outFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (int j = 0; j < maxLineCount; j++) {//读取数据并写入新文件
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        continue;
                    }
                    if (j != 0) {
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.write(readLine);
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
