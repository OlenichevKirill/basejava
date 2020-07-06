package ru.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ДЗ вывод каталогов и фалов через рекурсию
        MainFile recursGetFileAndDir = new MainFile();
        recursGetFileAndDir.getFileAndDirectory("D:\\basejava\\src", "");
    }

    public void getFileAndDirectory(String path, String indent) {
        File dir = new File(path);

        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    System.out.println(indent + "\t" + "File: " + file.getName());
                }
            }

            if (dir.listFiles() != null) {
                indent += "  ";
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        System.out.println(indent + "Dir: " + file.getName());
                        getFileAndDirectory(file.getAbsolutePath(), indent);
                    }
                }
            }
        }
    }
}
