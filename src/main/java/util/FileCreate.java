/**
 * licenses:AGPL v3
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileCreateUtil
 * 创建指定大小的测试文件
 *
 * @date 2023/9/28 17:39
 */
public class FileCreate {


    public static void main(String[] args) {
        FileCreate fileCreate = new FileCreate();
        long fileSize = (1L << 20) << 10;//文件大小（Byte）
        fileCreate.distinct("D:\\", "1gb.pdf", fileSize);
    }

    private void distinct(String filePath, String fileName, long fileSize) {
        try{
            if (filePath == null || filePath.length() == 0) {
                throw new IllegalArgumentException("传入的路径为空");
            }
            if (fileName == null || fileName.length() == 0) {
                throw new IllegalArgumentException("传入的文件名为空");
            }
            File file = new File(filePath);
            String fileFullName = filePath + fileName;
            if (!file.exists()) {
                if (file.mkdirs()) {
                    throw new IOException("创建路径失败：" + filePath);
                }
            }
            long hasFileSize = 0L;
            FileOutputStream fos = new FileOutputStream(new File(fileFullName));
            int tempSize = 1024;
            int writeSize = tempSize;
            while (writeSize > 0) {
                byte[] writeBytes = new byte[writeSize];
                fos.write(writeBytes);
                hasFileSize += writeSize;
                writeSize = (fileSize - hasFileSize) > tempSize ? tempSize : (int) (fileSize - hasFileSize);
            }
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
