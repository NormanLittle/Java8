package com.sandbox;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Multimaps.index;
import static com.sandbox.PropertyTable.PropertyRow.readAll;
import static com.sandbox.PropertyTable.PropertyRow.toPartNumber;
import static com.sandbox.PropertyTable.PropertyRow.toPropertyRow;
import static java.lang.String.format;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;

public class PropertyTable {
    /* <partNumber>,<postalAreaIdentifier>,<countryCode>,<fulfilmentHouse> */
    private static final String PROPERTY_TABLE = "13950,NULL,ROI,UTL|" +
                                                 "13950,NULL,GBR,UTL|" +
                                                 "13949,NULL,NULL,NULL";

    public static void main(String[] args) {
        Map<String, Collection<PropertyRow>> propertyTable = readAll(split(PROPERTY_TABLE, '|'), toPropertyRow, toPartNumber);
        if (propertyTable.containsKey("13950")) {
            propertyTable.get("13950")
                         .stream()
                         .map(PropertyRow::toString)
                         .forEach(System.out::println);
        }
    }

    private static List<String> split(String source, char delimiter) {
        return Splitter.on(delimiter)
                       .omitEmptyStrings()
                       .splitToList(source);
    }

    static class PropertyRow {
        static final int NUMBER_OF_COLUMNS = 4;
        static final Function<String, PropertyRow> toPropertyRow = new Function<String, PropertyRow>() {
            @Nullable
            @Override
            public PropertyRow apply(@Nullable String source) {
                List<String> parts = split(nullToEmpty(source), ',');
                if (parts.size() != NUMBER_OF_COLUMNS) {
                    return null;
                }

                PropertyRow propertyRow = new PropertyRow();
                propertyRow.partNumber = parts.get(0);
                propertyRow.postalAreaIdentifier = parts.get(1);
                propertyRow.countryCode = parts.get(2);
                propertyRow.fulfilmentHouse = parts.get(3);

                return propertyRow;
            }
        };

        static final Function<PropertyRow, String> toPartNumber = new Function<PropertyRow, String>() {
            @Nullable
            @Override
            public String apply(@Nullable PropertyRow propertyRow) {
                return propertyRow.partNumber;
            }
        };
        private String partNumber;
        private String postalAreaIdentifier;
        private String countryCode;
        private String fulfilmentHouse;
        private PropertyRow() {
        }

        static <K, V> Map<K, Collection<V>> readAll(List<String> rows, Function<String, V> toRowType, Function<V, K> toKeyType) {
            return index(FluentIterable.from(rows)
                                       .transform(toRowType)
                                       .filter(notNull())
                                       .toList(), toKeyType).asMap();
        }

        @Override
        public String toString() {
            return format("PartNumber=%s, PostalAreaIdentifier=%s, CountryCode=%s, FulfilmentHouse=%s",
                          partNumber,
                          postalAreaIdentifier,
                          countryCode,
                          fulfilmentHouse);
        }
    }
}
