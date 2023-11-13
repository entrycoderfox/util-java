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
 * SubFileLine
 * 给每行添加单引号以及逗号（常用于SQL语句的IN）
 *
 * @date 2023/7/19 9:23
 */
public class SubFileLine {

    public static void main(String[] args) {
        SubFileLine distinctFileLine = new SubFileLine();
        distinctFileLine.sub("D:\\allOrg.txt", "D:\\had.txt", "D:\\subTarget.txt");
    }

    private void sub(String inPath1, String inPath2, String outPath) {
        try{
            if (inPath1 == null || inPath1.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (inPath2 == null || inPath2.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传入的输出路径为空");
            }
            Set<String> strSet = this.getLines(inPath1);
            Set<String> strSet2 = this.getLines(inPath2);
            strSet.removeAll(strSet2);
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

    private Set<String> getLines(String inPath) {
        Set<String> strSet = new HashSet<>();
        try{
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
        } catch (Exception e){
            e.printStackTrace();
        }

        return strSet;
    }
}
