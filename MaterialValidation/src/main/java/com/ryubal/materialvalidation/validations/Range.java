package com.ryubal.materialvalidation.validations;

/**
 * Lets you validate an input, so that it only has numeric values within the range specified.
 */
public class Range {
    public int from, to;

    /**
     * Creates the actual range of numbers allowed.
     * @param from From which value (including)
     * @param to To which value (including)
     */
    public Range(int from, int to) {
        this.from = from;
        this.to = to;
    }
}
