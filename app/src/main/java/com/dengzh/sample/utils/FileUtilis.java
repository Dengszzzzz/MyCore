package com.dengzh.sample.utils;

import com.dengzh.core.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dengzh on 2018/2/28.
 * 文件工具类
 * 1.SDcard
 * 2.File
 * 3.IO流操作
 * 以Reader结尾都是字符流，操作字符类型；以Stream结尾的都是字节流，操作byte数据
 *
 */

public class FileUtilis {

    private static String TAG = "FileUtilis";

    /**
     * 创建文件
     * @param path      路径
     * @param fileName  文件名
     * @return
     */
    public static boolean createFile(String path,String fileName){
        File file = new File(path + File.separator + fileName);
        if(file.exists()){
            LogUtils.e(TAG,"新建文件失败,同名文件已存在");
            return false;
        }else{
            boolean isCreated = false;
            try {
                isCreated = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isCreated;
        }
    }

    /**
     * 删除单个文件
     * @param file 文件
     * @return
     */
    public static boolean deleteFile(File file){
        if(file.exists()){
            boolean isDeleted = file.delete();
            LogUtils.e(TAG,file.getName() + "删除结果：" + isDeleted);
            return isDeleted;
        }else{
            LogUtils.e(TAG,"文件删除失败：文件不存在");
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param path 文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    public static boolean deleteFile(String path, String fileName)
    {
        File file = new File(path + File.separator + fileName);
        if(file.exists())
        {
            boolean isDeleted = file.delete();
            return isDeleted;
        }else{
            return false;
        }
    }

    /**
     * 复制文件
     * @param srcPath
     * @param destDir
     * @return
     */
    public static boolean copyFile(String srcPath,String destDir){
        boolean flag = false;
        File srcFile = new File(srcPath);
        if(!srcFile.exists()){
            LogUtils.e(TAG,"源文件不存在");
            return false;
        }
        //获取待复制文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if(srcPath.equals(destPath)){
            LogUtils.e(TAG,"源文件路径和目标文件路径重复!");
            return false;
        }
        File destFile = new File(destPath);  //目标文件
        if(destFile.exists() && destFile.isFile()){
            LogUtils.e(TAG,"目标目录下已有同名文件!");
            return false;
        }
        //创建文件目录
        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        //开始读写
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf))!=-1){
                fos.write(buf,0,len);
            }
            fis.close();
            fos.close();
            flag = true;
            LogUtils.e(TAG,"文件复制成功");
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 复制大文件 使用带缓冲区的输入输出流
     * @param srcPath
     * @param destDir
     * @return
     */
    public static boolean copyFileLarge(String srcPath,String destDir){
        boolean flag = false;
        File srcFile = new File(srcPath);
        if(!srcFile.exists()){
            LogUtils.e(TAG,"源文件不存在");
            return false;
        }
        //获取待复制文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if(srcPath.equals(destPath)){
            LogUtils.e(TAG,"源文件路径和目标文件路径重复!");
            return false;
        }
        File destFile = new File(destPath);  //目标文件
        if(destFile.exists() && destFile.isFile()){
            LogUtils.e(TAG,"目标目录下已有同名文件!");
            return false;
        }
        //创建文件目录
        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        //开始读写
        try {
            //和copyFile的区别是，使用了带缓冲区的输入输出流，其他都一致
            //BufferedInputStream 这些缓存区默认是8192，比如下面的buf设置成1024，则8192/1024 = 8，即8次才调用一次IO，减少了IO访问
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

            byte[] buf = new byte[1024];
            int len;
            while ((len = bis.read(buf))!=-1){
                bos.write(buf,0,len);
            }
            bis.close();
            bos.close();
            flag = true;
            LogUtils.e(TAG,"文件复制成功");
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据文件名获得文件的扩展名
     * @param fileName       文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(String fileName)
    {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return suffix;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象
     * @param newFile 新文件对象
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, File newFile)
    {
        if (oldFile.equals(newFile)){
            LogUtils.e(TAG,"文件重命名失败：旧文件对象和新文件对象相同！");
            return false;
        }
        boolean isSuccess = oldFile.renameTo(newFile);
        LogUtils.e(TAG,"文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 获取某个路径下的文件列表
     *
     * @param path
     *            文件路径
     * @return 文件列表File[] files
     */
    public static File[] getFileList(String path)
    {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 新建目录
     *
     * @param path 目录的绝对路径
     * @return 创建成功则返回true
     */
    public static boolean createFolder(String path) {
        File file = new File(path);
        boolean isCreated = file.mkdir();
        return isCreated;
    }

    /**
     * 删除文件夹及其包含的所有文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFolder(File file) {
        boolean flag = false;
        File files[] = file.listFiles();
        if (files != null && files.length >= 0) // 目录下存在文件列表
        {
            for (int i = 0; i < files.length; i++)
            {
                File f = files[i];
                if (f.isFile()) {
                    // 删除子文件
                    flag = deleteFile(f);
                    if (flag == false) {
                        return flag;
                    }
                }
                else {
                    // 删除子目录
                    flag = deleteFolder(f);
                    if (flag == false) {
                        return flag;
                    }
                }
            }
        }
        flag = file.delete();
        if (flag == false) {
            return flag;
        } else {
            return true;
        }
    }


    /**
     * 复制目录
     *
     * @param srcPath
     *            源文件夹路径
     * @param destDir
     *            目标文件夹所在目录
     * @return 复制成功则返回true
     */
    public static boolean copyFolder(String srcPath, String destDir)
    {
        LogUtils.e(TAG,"复制文件夹开始!");
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            // 源文件夹不存在
            LogUtils.e(TAG,"源文件夹不存在");
            return false;
        }
        String dirName = getDirName(srcPath); // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"

        String destPath = destDir + File.separator + dirName; // 目标文件夹的完整路径
        if (destPath.equals(srcPath)) {
            LogUtils.e(TAG,"目标文件夹与源文件夹重复");
            return false;
        }
        File destDirFile = new File(destPath);
        if (destDirFile.exists()) {
            // 目标位置有一个同名文件夹
            LogUtils.e(TAG,"目标位置已有同名文件夹!");
            return false;
        }
        destDirFile.mkdirs(); // 生成目录

        File[] files = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (files.length == 0) {
            // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        } else {
            for (File temp : files) {
                if (temp.isFile()) {
                    // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) {
                    // 文件夹
                    flag = copyFolder(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            LogUtils.e(TAG,"复制文件夹成功!");
        }
        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    public static String getDirName(String dir)
    {
        if (dir.endsWith(File.separator)) {
            // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }


    /**
     * 举例 FileReader 和 FileWriter
     * 可以用来读写文件，具体效率如何未知
     */
    public static void readAndWrite(String srcPath,String desPath) {
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(srcPath);
            writer = new FileWriter(desPath);

            //1.读一个字符，写一个字符方法
            int ch = 0;
            while ((ch=reader.read())!=-1){
                writer.write(ch);
            }
            //2.读一个数组大小，写一个数组大小方法
            char []buf = new char[1024];
            int len = 0;
            while ((len = reader.read(buf))!=-1){
                writer.write(buf,0,len);
            }

            //3.直接写一个字符串
            writer.write("hello world!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 举例 BufferedReader,BufferedWriter
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readWithBufferedReader(String srcPath,String desPath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(new File(srcPath)));
            writer = new BufferedWriter(new FileWriter(new File(desPath)));

            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                LogUtils.e(TAG,"line " + line + ": " + tempString);
                line++;
                writer.write(tempString);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
