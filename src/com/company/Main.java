package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        String dirUnzip = "D://Нетология//Games//extract//";
        String fileName = "SaveGame2.txt";
        if (openZip("D://Нетология//Games//savegames//zipArch.zip", dirUnzip)) {
            System.out.println(openProgress(dirUnzip + fileName));
        }
        ;
        // write your code here
    }

    public static boolean openZip(String source, String receiver) {
        File dir = new File(receiver);
        // есть ли каталог для разархивации, создаем если нет
        if (!dir.isDirectory()) {
            if (dir.mkdir()) System.out.printf("\nКаталог для разархивирования создан\n", receiver);
        }
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(source))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                String[] fileNames = entry.getName().split("//"); // берем именно имя файла,т.к. распаковываем в новый каталог
                FileOutputStream out = new FileOutputStream(receiver + fileNames[fileNames.length - 1]);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                byte[] buffer = new byte[zin.available()];
                buffer = zin.readAllBytes();
                bos.write(buffer, 0, buffer.length);
                //bos.flush(); нужно, не нужно? почему не работает вместь строки bos.write (buffer ****)
                zin.closeEntry();
                bos.close();
                out.close();
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameP = null;
        try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameP = (GameProgress) ois.readObject();// десериализуем объект
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameP;
    }
}
