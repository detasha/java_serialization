import gameProgress.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<File> fileSave = new ArrayList<>();
        addFileListZip(fileSave);

        GameProgress progressFirst = new GameProgress(100, 1, 1, 25.25);
        GameProgress progressSecond = new GameProgress(150, 2, 2, 23.23);
        GameProgress progressThird = new GameProgress(2700, 11, 7, 201.32);

        saveGame(fileSave.get(0).getAbsolutePath(), progressFirst);
        saveGame(fileSave.get(1).getAbsolutePath(), progressSecond);
        saveGame(fileSave.get(2).getAbsolutePath(), progressThird);

        String zipName = "C://Games/saveGames/save.zip";
        zipFile(zipName, fileSave);
        deleteFile(fileSave);
    }

    private static void addFileListZip(List<File> fileSave) {
        fileSave.add(new File("C://Games/saveGames/save1.dat"));
        fileSave.add(new File("C://Games/saveGames/save2.dat"));
        fileSave.add(new File("C://Games/saveGames/save3.dat"));
    }

    private static void saveGame(String fileSave, GameProgress progresses) {
        try (FileOutputStream fos = new FileOutputStream(fileSave);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progresses);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFile(String zipName, List<File> fileSave) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipName))) {
            int count = 1;
            for (File fileName : fileSave) {
                FileInputStream fis = new FileInputStream(fileName.getAbsolutePath());
                ZipEntry entry = new ZipEntry("save" + count++ + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteFile(List<File> fileSave) {
        for (File fileSaves : fileSave) {
            if (fileSaves.delete())
                System.out.println("Удаление файлов сохранения, лежащих вне архива");
        }
    }
}