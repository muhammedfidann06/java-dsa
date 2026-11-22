package io.github.muhammedfidann06.dsa;

import io.github.muhammedfidann06.dsa.greedy.IntervalScheduling;
import io.github.muhammedfidann06.dsa.greedy.IntervalScheduling.Interval;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntervalSchedulingTest {

    @Test
    void maximaleAnzahlTermine() {
        List<Interval> iv = List.of(new Interval(1, 4), new Interval(3, 5), new Interval(0, 6),
                new Interval(5, 7), new Interval(3, 9), new Interval(5, 9), new Interval(6, 10),
                new Interval(8, 11), new Interval(8, 12), new Interval(2, 14), new Interval(12, 16));
        List<Interval> chosen = IntervalScheduling.maxNonOverlapping(iv);
        assertEquals(4, chosen.size()); // z. B. [1,4) [5,7) [8,11) [12,16)
        for (int i = 1; i < chosen.size(); i++) {
            assertTrue(chosen.get(i - 1).end() <= chosen.get(i).start());
        }
    }

    @Test
    void minimaleAnzahlRaeume() {
        assertEquals(2, IntervalScheduling.minRooms(List.of(
                new Interval(0, 30), new Interval(5, 10), new Interval(15, 20))));
        assertEquals(1, IntervalScheduling.minRooms(List.of(
                new Interval(0, 10), new Interval(10, 20)))); // [0,10) und [10,20) berühren sich nur
        assertEquals(0, IntervalScheduling.minRooms(List.of()));
    }

    @Test
    void raeumeGegenZaehlungDerUeberlappungen() {
        // Mindestanzahl Räume = größte Anzahl gleichzeitig laufender Termine
        SplittableRandom r = new SplittableRandom(100);
        for (int t = 0; t < 100; t++) {
            List<Interval> iv = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                int s = r.nextInt(50);
                iv.add(new Interval(s, s + 1 + r.nextInt(10)));
            }
            int maxOverlap = 0;
            for (int time = 0; time < 70; time++) {
                final int now = time;
                maxOverlap = Math.max(maxOverlap, (int) iv.stream().filter(x -> x.start() <= now && now < x.end()).count());
            }
            assertEquals(maxOverlap, IntervalScheduling.minRooms(iv));
        }
    }

    @Test
    void ungueltigesIntervall() {
        assertThrows(IllegalArgumentException.class, () -> new Interval(5, 3));
    }
}
