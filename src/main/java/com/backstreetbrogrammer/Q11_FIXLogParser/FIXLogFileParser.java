package com.backstreetbrogrammer.Q11_FIXLogParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FIXLogFileParser {

    private static final Pattern isTradeLogPattern
            = Pattern.compile("^(?=.*\\b35=8\\b)(?=.*\\b150=([F12])\\b).*");
    private static final Pattern tagValuePairPattern = Pattern.compile("\\b(\\d+)=([^\\u0001]+)");

    public static void main(final String[] args) {
        try (final BufferedReader br
                     = Files.newBufferedReader(Path.of("src", "main", "resources", "fix_log2.txt"))) {
            final Map<String, Double> symbolPositionMap = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                // 1. check if its trade execution logs
                if (isTradeLog(line)) {
                    // for debug
                    // System.out.println(line);

                    // 2. extract all the tag-value pairs and store in a map
                    final Map<Integer, String> fixTagValueMap = extractTagValueFields(line);
                    if (!fixTagValueMap.isEmpty()) {
                        final SymbolPosition symbolPosition = createSymbolPosition(fixTagValueMap);
                        final String symbol = symbolPosition.getSymbol();
                        final double position = symbolPosition.getPosition();
                        symbolPositionMap.merge(symbol, position, Double::sum);
                    }
                }
            }
            System.out.println(symbolPositionMap);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SymbolPosition createSymbolPosition(final Map<Integer, String> fixTagValueMap) {
        final String symbol = fixTagValueMap.get(55);
        final double lastPrice = Double.parseDouble(fixTagValueMap.get(31));
        final double lastQty = Double.parseDouble(fixTagValueMap.get(32));

        double position = lastPrice * lastQty;
        final int side = Integer.parseInt(fixTagValueMap.get(54));
        if (side == 2 || side == 4 || side == 5) {
            position *= -1D;
        }
        return new SymbolPosition(symbol, position);
    }

    private static Map<Integer, String> extractTagValueFields(final String line) {
        final Map<Integer, String> fixTagValueMap = new HashMap<>();
        final Matcher matcher = tagValuePairPattern.matcher(line);
        while (matcher.find()) {
            final int fixTag = Integer.parseInt(matcher.group(1));
            final String fixTagValue = matcher.group(2);
            fixTagValueMap.put(fixTag, fixTagValue);
        }
        return fixTagValueMap;
    }

    private static boolean isTradeLog(final String line) {
        return isTradeLogPattern.matcher(line).matches();
    }

    private static class SymbolPosition {
        private final String symbol;
        private final double position;

        public SymbolPosition(final String symbol, final double position) {
            this.symbol = symbol;
            this.position = position;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getPosition() {
            return position;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof SymbolPosition)) return false;
            final SymbolPosition that = (SymbolPosition) o;
            return position == that.position && Objects.equals(symbol, that.symbol);
        }

        @Override
        public int hashCode() {
            return Objects.hash(symbol, position);
        }
    }
}
