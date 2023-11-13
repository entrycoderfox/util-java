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
 * TextSplitBySize
 * 通过文件大小切分文件
 * 
 * @date 2023/7/11 16:04
 */
public class TextFileSplitBySize {

    public static void main(String[] args) {
        TextFileSplitBySize textSplit = new TextFileSplitBySize();
//        textSplit.splitText("D:\\util_test\\SystemOut.log", "D:\\util_test\\splitFiles\\", "out_", ".txt", 100000);
        long subFileSize = 1L << 27;//每个文件最大128MB
        int bufferMaxSize = 1<< 20;//每次读取的buffer最大1MB
        textSplit.splitText("D:\\SystemOut.log", "D:\\split1\\", "out_", ".log", subFileSize, bufferMaxSize);
    }

    public void splitText(String inFilePath, String outPath, String outSuffix, String outFileType, long subFileMaxSize, int bufferMaxSize) {
        try {
            if (inFilePath == null || inFilePath.length() == 0) {
                throw new IllegalArgumentException("传入的读取路径为空");
            }
            if (outPath == null || outPath.length() == 0) {
                throw new IllegalArgumentException("传入的输出路径为空");
            }
            if (subFileMaxSize <= 0) {
                throw new IllegalArgumentException("传入的行数有误");
            }

            File inFile = new File(inFilePath);
            if (!inFile.exists() || !inFile.isFile()) {
                throw new IllegalArgumentException("要读取的文件不存在");
            }

            FileReader fileReader = new FileReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader, bufferMaxSize);

            long fileLength = inFile.length();//文件大小
            long fileHasLength = fileLength;//文件未读大小
            long subFileCount = fileLength / subFileMaxSize;//要切分的文件数量
            if ((fileLength % subFileMaxSize) != 0) {//文件数量需要增加一个
                subFileCount++;
            }

            long subFileNum = 1;
            for (; fileHasLength > 0 && subFileNum <= subFileCount; subFileNum++) {
                File outFile = new File(outPath + outSuffix + subFileNum + outFileType);
                FileWriter fileWriter = new FileWriter(outFile);
                BufferedWriter bw = new BufferedWriter(fileWriter);

                long subFileWriteSize = 0;//当前文件已写的大小
                long subFileWillSize = subFileCount > subFileNum ? subFileMaxSize : fileHasLength;//当前小文件的预计大小
                while (subFileWriteSize < subFileMaxSize && fileHasLength > 0 && subFileWillSize > 0) {
                    int subBufferSize = (subFileWillSize - subFileWriteSize > bufferMaxSize) ? bufferMaxSize : (int) (subFileWillSize - subFileWriteSize);
                    char[] cBuf = new char[subBufferSize];//每次读取的缓存
                    bufferedReader.read(cBuf);
                    bw.write(cBuf);
                    subFileWriteSize += subBufferSize;//已写文件大小变动
                    fileHasLength -= subBufferSize;//未读文件大小变动
                }
                bw.flush();
                bw.close();
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
