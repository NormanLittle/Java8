package com.sandbox.distanceToTheScore.spreadsheet;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;

public class SheetsApiDemo {
    private static final String DISTANCE_TO_THE_SCORE_URL = "https://spreadsheets.google" +
                                                            ".com/feeds/worksheets/1sADQa0up1yXGdYHXt_ikIrD4D_7RaRQLHp3oU8gg7nw/public/full";

    public static void main(String[] args) throws IOException, ServiceException {
        new SpreadsheetService("Distance To The Score").getFeed(new URL(DISTANCE_TO_THE_SCORE_URL), SpreadsheetFeed.class)
                                                       .getEntries()
                                                       .stream()
                                                       .map(SpreadsheetEntry::getTitle)
                                                       .map(TextConstruct::getPlainText)
                                                       .forEach(System.out::println);

    }
}
