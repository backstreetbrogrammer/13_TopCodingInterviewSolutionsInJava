package com.backstreetbrogrammer.Q9_UnixFind;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JFind {

    private File directory;
    private Pattern namePattern;

    public void setDirectoryName(final String directoryName) {
        boolean isValidDir = false;
        if (directoryName != null && !directoryName.isEmpty()) {
            final File directoryFile = new File(directoryName);
            if (directoryFile.isDirectory()) {
                this.directory = directoryFile;
                isValidDir = true;
            }
        }
        if (!isValidDir) {
            throw new IllegalArgumentException(String.format("%s is not a valid directory", directoryName));
        }
    }

    public void setNamePattern(final String pattern) {
        boolean isValidPattern = false;
        if (pattern != null && !pattern.isEmpty()) {
            setNameFilter(pattern);
            isValidPattern = true;
        }
        if (!isValidPattern || (namePattern == null)) {
            throw new IllegalArgumentException(String.format("%s is not a valid file name pattern", pattern));
        }
    }

    public static void main(final String[] args) {
        if (args.length < 2) {
            usage();
            System.exit(1);
        }

        final JFind finder = new JFind();
        finder.setDirectoryName(args[0]);
        finder.setNamePattern(args[1]);

        finder.findFiles();
    }

    private static void usage() {
        System.err.println("Usage: java JFind <directory> <filename-pattern>");
    }

    public void findFiles() {
        try {
            final List<File> files = new ArrayList<>();
            findFiles(directory, files);

            for (final File file : files) {
                System.out.println(file.getAbsolutePath());
            }
        } catch (final Exception e) {
            usage();
            e.printStackTrace();
        }
    }

    private void findFiles(final File directory, final List<File> files) {
        final File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (final File file : fileList) {
                if (file.isDirectory()) {
                    findFiles(file, files);
                } else if (namePattern.matcher(file.getName()).matches()) {
                    files.add(file);
                }
            }
        }
    }

    private void setNameFilter(final String nameFilter) {
        final StringBuilder sb = new StringBuilder("^");
        for (final char c : nameFilter.toCharArray()) {
            switch (c) {
                case '.':
                    sb.append("\\.");
                    break;
                case '*':
                    sb.append(".*");
                    break;
                case '?':
                    sb.append('.');
                    break;
                case '[':
                    sb.append("[\\[");
                    break;
                case ']':
                    sb.append("\\]]");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        sb.append('$');
        System.out.printf("RE=[%s]%n", sb);
        namePattern = Pattern.compile(sb.toString());
    }


}
