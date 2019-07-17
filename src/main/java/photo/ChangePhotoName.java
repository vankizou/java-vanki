package photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by vanki on 2018/11/21 17:24.
 */
public class ChangePhotoName {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    private static final Pattern p = Pattern.compile("[-|$|_|\\w+].+");
    private static final Pattern p2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}.*");
    private static final String basePath = "/Volumes/vanki_/百度云同步盘/vanki's photos";
    //    private static final String basePath = "/Volumes/vanki_/百度云同步盘/vanki's photos/2015/2015-网络";
    private static int count = 0;
    private static String folderPrefix = "20181227";

    public static void main(String[] args) throws IOException {
        File baseFile = new File(basePath);

        count(baseFile);
        System.out.println("总数：" + count);

        changeName(baseFile);
    }

    private static void count(File parentFile) {
        File[] files = parentFile.listFiles();

        for (File f : files) {
            String name = f.getName();
            if (name.startsWith(".")) {
                continue;
            }

            if (f.isDirectory()) {
                count(f);
            } else {
                count++;
            }
        }
    }

    private static void changeName(File parentFile) throws IOException {
        File[] files = parentFile.listFiles();

        for (File f : files) {
            String name = f.getName();
            if (name.startsWith(".")) {
                continue;
            }

            if (f.isDirectory()) {
                changeName(f);
            } else {
                if (parentFile.getName().startsWith(folderPrefix)) {
                    if (isNeedChange(name)) {
                        File changedFile = getChangedFile(f);
                        System.out.println(name + " - " + changedFile);
                        if (changedFile != null) {
                            f.renameTo(changedFile);
                        }
                    }
                }
            }
        }
    }

    private static File getChangedFile(File f) throws IOException {
        String oldAllName = f.getName();
        int i = oldAllName.lastIndexOf(".");
        if (i == -1) {
            return null;
        }
        String suffix = oldAllName.substring(i);

        BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
        String name = new StringBuilder()
                .append(df.format(new Date(attr.creationTime().toMillis())))
                .toString();
        File nFile = new File(f.getParent(), name + suffix);
        if (!nFile.exists()) {
            return nFile;
        }

        for (int repeatCount = 1; ; ) {
            nFile = new File(f.getParent(), name + "(" + (repeatCount++) + ")" + suffix);
            if (!nFile.exists()) {
                return nFile;
            }
        }
    }

    private static boolean isNeedChange(String name) {
        return !p2.matcher(name).matches() && p.matcher(name).matches();
    }
}
