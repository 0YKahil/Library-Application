package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter {
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
            System.out.println();
        }
    }
}
