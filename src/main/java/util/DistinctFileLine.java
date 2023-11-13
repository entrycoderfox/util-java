/**
 * licenses:AGPL v3
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * DistinctFileLine
 * 去除多行文字中的重复行
 *
 * @date 2023/7/19 9:23
 */
public class DistinctFileLine {

    public static void main(String[] args) {
        DistinctFileLine distinctFileLine = new DistinctFileLine();
        distinctFileLine.distinct("D:\\ids.txt", "D:\\idUnique.txt");
    }

    private void distinct(String inPath, String outPath) {
        try{
            if (inPath == null || inPath.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传入的输出路径为空");
            }
            Set<String> strSet = new HashSet<>();
            File inFile = new File(inPath);
            FileReader inReader = new FileReader(inFile);
            BufferedReader buffReader = new BufferedReader(inReader);
            String line = null;
            while ((line = buffReader.readLine()) != null) {
                if("".equals(line.trim())) {
                    continue;
                }
                strSet.add(line);
            }
            buffReader.close();
            if (strSet.size() <= 0) {
                return;
            }
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(new File(outPath)));
            for (String eachLine : strSet) {
                buffWriter.write(",'");
                buffWriter.write(eachLine);
                buffWriter.write("'");
                buffWriter.newLine();
            }
            buffWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
