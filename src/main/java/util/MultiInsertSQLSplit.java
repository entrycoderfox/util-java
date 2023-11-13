/**
 * licenses:AGPL v3
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MultiInsertSQLSplit
 * 多行SQL中插入COMMIT
 *
 * @date 2023/7/11 16:04
 */
public class MultiInsertSQLSplit {

    public static void main(String[] args) {
        MultiInsertSQLSplit obj = new MultiInsertSQLSplit();
        obj.multiSplit("D:\\in.sql", "D:\\out.sql", 500);
    }

    public void multiSplit(String inPath, String outPath, int lengthPerGroup) {
        try {
            if (inPath == null || inPath.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传入的输出路径为空");
            }
            if (lengthPerGroup <= 0) {
                throw new IllegalArgumentException("传入的每行长度有误");
            }
            File inFile = new File(inPath);
            FileReader fileReader = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fileReader);

            File outFile = new File(outPath);
            FileWriter fileWriter = new FileWriter(outFile);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            List<String> lines = br.lines().collect(Collectors.toList());
            int lineCount = lines.size();
//            String line;
            int lineGroupCount = 0;
            for (String line : lines) {
                lineGroupCount++;
                lineGroupCount %= lengthPerGroup;
                if (lineGroupCount == 0) {
                    bw.write("COMMIT;");
                    bw.newLine();
                }
                bw.write(line);
                bw.newLine();
            }
//            while(lineCount >= 0 && (line = br.readLine()) != null) {
//                lineGroupCount++;
//                lineGroupCount %= lengthPerGroup;
//                bw.newLine();
//                if (lineGroupCount == 0) {
//                    bw.write("COMMIT;");
//                }
//                bw.write(line);
//            }
            bw.write("COMMIT;");
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
