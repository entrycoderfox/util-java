/**
 * licenses:AGPL v3
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * TextSplit
 * 将过长的行转换为多行的拼接（Oracle的CLOB使用）
 *
 * @date 2023/7/11 16:04
 */
public class TextSplit {

    public static void main(String[] args) {
        TextSplit textSplit = new TextSplit();
        textSplit.splitText("D:\\in.json", "D:\\out.json", 100);
    }

    public void splitText(String inPath, String outPath, int lengthPerLine) {
        try {
            if (inPath == null || inPath.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传出的输出路径为空");
            }
            if (outPath.equals(inPath)) {
                throw new IllegalArgumentException("传入、输出的路径相同");
            }
            if (lengthPerLine <= 0) {
                throw new IllegalArgumentException("传入的每行长度有误");
            }
            File inFile = new File(inPath);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            File outFile = new File(outPath);
            FileWriter fileWriter = new FileWriter(outFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String readLine = bufferedReader.readLine();
            int readLineLength = readLine.length();
            for (int i = 0; (i * lengthPerLine) < readLineLength; i++) {
                if (i != 0) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("\t\t\t");
                if (i != 0) {
                    bufferedWriter.write("||");
                }
                int offset = i * lengthPerLine;
                int readLen = lengthPerLine;
                if (lengthPerLine > (readLineLength - offset)) {
                    readLen = readLineLength - offset;
                }
                bufferedWriter.write("TO_CLOB('");
                bufferedWriter.write(readLine, i * lengthPerLine, readLen);
                bufferedWriter.write("')");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
