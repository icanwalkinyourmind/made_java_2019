package ru.made.ex.five.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.made.ex.five.Cache;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DiskStorageStrategy implements StorageStrategy {
    public static final String DEFAULT_ROOT_DIR = "/tmp/";
    private final Path rootDir;

    public DiskStorageStrategy(String root_dir) {
        rootDir = Paths.get(root_dir);
    }

    private String getFilePath(String key) {
        return Paths.get(rootDir.toString(), key).toString();
    }


    private void saveFile(String key, Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (FileOutputStream out = new FileOutputStream(getFilePath(key))) {
            mapper.writeValue(out, o);
        }
    }

    private String getZipFilePath(String key) {
        return getFilePath(key) + ".zip";
    }

    private void saveCompressedFile(String key, Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(getZipFilePath(key)))) {
            out.putNextEntry(new ZipEntry("object.json"));
            mapper.writeValue(out, o);
        }
    }


    @Override
    public void put(String key, Object o, Cache annotation) throws StorageException {
        try {
            if (annotation.compress()) {
                saveCompressedFile(key, o);
            } else {
                saveFile(key, o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object readFile(FileInputStream fileInputStream, Class<?> clazz) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(fileInputStream, clazz);
    }

    private Object readCompressedFile(FileInputStream fileInputStream, Class<?> clazz) throws IOException {
        ZipInputStream in = new ZipInputStream(fileInputStream);
        try (FileInputStream fis = new FileInputStream(String.valueOf(in.getNextEntry()))) {
            return readFile(fis, clazz);
        }
    }

    private boolean isZip(String path) {
        return path.endsWith(".zip");
    }

    @Override
    public Object get(String key, Class<?> clazz) throws StorageException {
        String filePath = getFilePath(key);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            if (isZip(filePath)) {
                return readCompressedFile(fileInputStream, clazz);
            } else {
                return readFile(fileInputStream, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException();
        }
    }

    @Override
    public boolean contains(String key) {
        return new File(getFilePath(key)).exists();
    }
}
