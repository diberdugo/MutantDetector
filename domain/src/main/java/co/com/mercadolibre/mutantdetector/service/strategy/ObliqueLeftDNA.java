package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class ObliqueLeftDNA implements MutantDetectStrategy {

    @Override
    public Long execute(String[][] fullDNA) {
        return getObliqueLeftDNA(fullDNA).stream()
                .filter(this::verifySequenceDNA)
                .count();
    }

    private List<String> getObliqueLeftDNA(String[][] fullDNA) {
        List<String> sequences = new ArrayList<>();

        AtomicInteger col = new AtomicInteger();
        AtomicInteger row = new AtomicInteger();
        AtomicInteger temp = new AtomicInteger();
        AtomicReference<StringBuilder> sb = new AtomicReference<>(new StringBuilder());

        IntStream.range(0, fullDNA.length).forEach(i -> {
            col.set(0);
            temp.set(row.get());
            sb.set(new StringBuilder());

            while (temp.get() >= 0) {
                sb.get().append(fullDNA[temp.get()][col.get()]);
                temp.getAndDecrement();
                col.getAndIncrement();
            }

            row.getAndIncrement();

            if (sb.toString().length() >= MATCH_SEQUENCE) {
                sequences.add(sb.toString());
            }
        });

        col.set(1);

        IntStream.range(0, fullDNA.length).forEach(i -> {
            temp.set(col.get());
            row.set(fullDNA.length - 1);
            sb.set(new StringBuilder());

            while (temp.get() <= fullDNA.length - 1) {
                sb.get().append(fullDNA[row.get()][temp.get()]);
                row.getAndDecrement();
                temp.getAndIncrement();
            }

            col.getAndIncrement();

            if (sb.toString().length() >= MATCH_SEQUENCE) {
                sequences.add(sb.toString());
            }
        });

        return sequences;
    }
}
