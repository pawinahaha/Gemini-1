package edu.gemini.model;

/*
 * Copyright (c) 2021.
 * Chaiyong Ragkhitwetsagul, Morakot Choetkiertikul, Pipatpong Niyommungmee
 * All rights reserved.
 */

/**
 * The quadrants of a star system
 */
public class Quadrant {
    /**
     * Each star system can belong to one of the 8 quadrants:  NQ1, NQ2, NQ3, NQ4,
     *         SQ1, SQ2, SQ3, and SQ4
     */
    public static enum QUADRANT {
        /**
         * North Quadrant 1 - Must only be observed using the North Gemini observatory
         */
        NQ1,
        /**
         * North Quadrant 2 - Must only be observed using the North Gemini observatory
         */
        NQ2,
        /**
         * North Quadrant 3 - Must only be observed using the North Gemini observatory
         */
        NQ3,
        /**
         * North Quadrant 4 - Must only be observed using the North Gemini observatory
         */
        NQ4,
        /**
         * South Quadrant 1 - Must only be observed using the South Gemini observatory
         */
        SQ1,
        /**
         * South Quadrant 2 - Must only be observed using the South Gemini observatory
         */
        SQ2,
        /**
         * South Quadrant 3 - Must only be observed using the South Gemini observatory
         */
        SQ3,
        /**
         * South Quadrant 4 - Must only be observed using the South Gemini observatory
         */
        SQ4
    }
}
