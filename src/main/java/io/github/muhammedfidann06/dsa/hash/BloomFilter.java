package io.github.muhammedfidann06.dsa.hash;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

/**
 * Bloom-Filter: Ist ein Element (wahrscheinlich) in der Menge?
 *
 * <p>Antwort "nein" ist immer richtig. Antwort "ja" kann falsch sein (falsch positiv),
 * aber nur mit kleiner Wahrscheinlichkeit. Dafür braucht der Filter sehr wenig Speicher –
 * er speichert die Elemente gar nicht, nur Bits. Anwendung: Datenbanken, Web-Caches,
 * "Wurde diese URL schon besucht?".
 *
 * <p>Jedes Element setzt k Bits. Die k Hashwerte bilden wir mit "Double Hashing":
 * h_i = h1 + i·h2 (Kirsch & Mitzenmacher).
 */
public class BloomFilter {

    private final BitSet bits;
    private final int size;      // Anzahl Bits m
    private final int hashCount; // Anzahl Hashfunktionen k

    /**
     * @param expectedItems     erwartete Anzahl Elemente n
     * @param falsePositiveRate gewünschte Fehlerrate p, z. B. 0.01
     */
    public BloomFilter(int expectedItems, double falsePositiveRate) {
        if (expectedItems < 1 || falsePositiveRate <= 0 || falsePositiveRate >= 1) {
            throw new IllegalArgumentException("ungültige Parameter");
        }
        // Optimale Werte aus der Theorie: m = -n·ln(p) / (ln 2)², k = (m/n)·ln 2
        double ln2 = Math.log(2);
        this.size = (int) Math.ceil(-expectedItems * Math.log(falsePositiveRate) / (ln2 * ln2));
        this.hashCount = Math.max(1, (int) Math.round((double) size / expectedItems * ln2));
        this.bits = new BitSet(size);
    }

    public void add(String item) {
        long[] h = hashes(item);
        for (int i = 0; i < hashCount; i++) {
            bits.set(index(h, i));
        }
    }

    /** false = sicher nicht enthalten, true = wahrscheinlich enthalten. */
    public boolean mightContain(String item) {
        long[] h = hashes(item);
        for (int i = 0; i < hashCount; i++) {
            if (!bits.get(index(h, i))) {
                return false; // ein Bit fehlt -> wurde nie hinzugefügt
            }
        }
        return true;
    }

    public int bitCount() {
        return size;
    }

    public int hashFunctions() {
        return hashCount;
    }

    private int index(long[] h, int i) {
        return (int) Math.floorMod(h[0] + i * h[1], (long) size);
    }

    // Zwei unabhängige 64-Bit-Hashes (FNV-1a mit zwei verschiedenen Startwerten)
    private static long[] hashes(String item) {
        byte[] data = item.getBytes(StandardCharsets.UTF_8);
        long h1 = 0xcbf29ce484222325L;
        long h2 = 0x84222325cbf29ce4L;
        for (byte b : data) {
            h1 = (h1 ^ (b & 0xff)) * 0x100000001b3L;
            h2 = (h2 ^ (b & 0xff)) * 0x100000001b3L;
            h2 ^= h2 >>> 29;
        }
        return new long[]{mix(h1), mix(h2) | 1}; // h2 ungerade, damit die Schritte alle Bits erreichen
    }

    // Finalisierer aus SplitMix64: verteilt kleine Unterschiede auf alle 64 Bits ("Lawineneffekt")
    private static long mix(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }
}
