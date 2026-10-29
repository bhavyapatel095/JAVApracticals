package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {
    private ReportGenerator() {
    }

    public static String generate(Path logsFolder, Path reportFile)
            throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        StringBuilder report = new StringBuilder();

        report.append("MiniBank End-of-Day Report\n");
        report.append("==========================\n");

        try (DirectoryStream<Path> entries =
                     Files.newDirectoryStream(logsFolder)) {
            for (Path file : entries) {
                if (!Files.isRegularFile(file)
                        || !file.getFileName().toString().endsWith(".log")) {
                    continue;
                }

                BasicFileAttributes attributes = Files.readAttributes(
                        file, BasicFileAttributes.class
                );

                if (attributes.size() == 0) {
                    continue;
                }

                report.append("\nFile: ")
                        .append(file.getFileName())
                        .append(" | Size: ")
                        .append(attributes.size())
                        .append(" bytes | Last modified: ")
                        .append(attributes.lastModifiedTime())
                        .append("\n");

                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length != 2) {
                            continue;
                        }

                        try {
                            long amount = Long.parseLong(parts[1]);
                            if (amount <= 0) {
                                continue;
                            }

                            if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;
                            } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }
                        } catch (NumberFormatException ignored) {
                            // Ignore malformed log lines.
                        }
                    }
                }
            }
        }

        report.append("\nTotal deposits: Rs. ")
                .append(totalDeposits)
                .append("\nTotal withdrawals: Rs. ")
                .append(totalWithdrawals)
                .append("\n");

        Path parent = reportFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
                reportFile,
                report.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        return report.toString();
    }
}
