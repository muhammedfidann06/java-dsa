package io.github.muhammedfidann06.dsa.greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Zwei klassische Intervall-Probleme mit gierigen Algorithmen.
 *
 * <p>Beispiel: Termine in einem Hörsaal an der FH.
 */
public final class IntervalScheduling {

    /** Ein halboffenes Intervall [start, end). */
    public record Interval(int start, int end) {
        public Interval {
            if (end < start) {
                throw new IllegalArgumentException("end < start");
            }
        }
    }

    private IntervalScheduling() {
    }

    /**
     * Möglichst viele Termine ohne Überschneidung in EINEM Raum. O(n log n).
     * Gierig: immer den Termin nehmen, der als erster endet – das lässt am meisten Platz übrig.
     */
    public static List<Interval> maxNonOverlapping(List<Interval> intervals) {
        List<Interval> sorted = new ArrayList<>(intervals);
        sorted.sort(Comparator.comparingInt(Interval::end));
        List<Interval> chosen = new ArrayList<>();
        int lastEnd = Integer.MIN_VALUE;
        for (Interval iv : sorted) {
            if (iv.start() >= lastEnd) {
                chosen.add(iv);
                lastEnd = iv.end();
            }
        }
        return chosen;
    }

    /**
     * Wie viele Räume braucht man mindestens für alle Termine? O(n log n).
     * Nach Start sortieren; ein Min-Heap merkt sich, wann die belegten Räume frei werden.
     */
    public static int minRooms(List<Interval> intervals) {
        List<Interval> sorted = new ArrayList<>(intervals);
        sorted.sort(Comparator.comparingInt(Interval::start));
        PriorityQueue<Integer> roomFreeAt = new PriorityQueue<>();
        for (Interval iv : sorted) {
            if (!roomFreeAt.isEmpty() && roomFreeAt.peek() <= iv.start()) {
                roomFreeAt.poll(); // ein Raum ist wieder frei -> wiederverwenden
            }
            roomFreeAt.add(iv.end());
        }
        return roomFreeAt.size();
    }
}
