package edu.gemini.model;

/*
 * Copyright (c) 2021.
 * Chaiyong Ragkhitwetsagul, Morakot Choetkiertikul, Pipatpong Niyommungmee
 * All rights reserved.
 */

/**
 * The class containing all the star systems supported by Gemini.
 */
public final class StarSystem {

    /**
     * This is an enum containing all the star systems supported in Gemini.
     */
    public enum CONSTELLATIONS {
        /**
         * "Andromeda", area = 722.278, Quadrant = NQ1, starting latitude = 90, ending latitude = 40
         */
        Andromeda("Andromeda", 722.278, Quadrant.QUADRANT.NQ1, 90, 40, 11),
        /**
         * "Air Pump", area = 238.901, Quadrant = SQ2, starting latitude = 45, ending latitude = 90
         */
        Antlia("Air Pump", 238.901, Quadrant.QUADRANT.SQ2, 45, 90,4),
        /**
         * "Bird of Paradise", area = 206.327, Quadrant = SQ3, starting latitude = 5, ending latitude = 90
         */
        Apus("Bird of Paradise", 206.327, Quadrant.QUADRANT.SQ3, 5, 90,7),
        /**
         * "Water Bearer", area = 979.854, Quadrant = SQ4, starting latitude = 65, ending latitude = 90
         */
        Aquarius("Water Bearer", 979.854, Quadrant.QUADRANT.SQ4, 65, 90,10),
        /**
         * "Eagle", area = 652.473, Quadrant = NQ4, starting latitude = 90, ending latitude = 75
         */
        Aquila("Eagle", 652.473, Quadrant.QUADRANT.NQ4, 90, 75,8),
        /**
         * "Altar", area = 237.057, Quadrant = SQ3, starting latitude = 25, ending latitude = 90
         */
        Ara("Altar", 237.057, Quadrant.QUADRANT.SQ3,25, 90,7),
        /**
         * "Ram", area = 441.395, Quadrant = NQ1, starting latitude = 90, ending latitude = 60
         */
        Aries("Ram", 441.395, Quadrant.QUADRANT.NQ1,90, 60,12),
        /**
         * "Charioteer", area = 657.438, Quadrant = NQ2, starting latitude = 90, ending latitude = 40
         */
        Auriga("Charioteer",  657.438, Quadrant.QUADRANT.NQ2,90, 40,2),
        /**
         * "Herdsman", area =906.831, Quadrant = NQ3, starting latitude = 90, ending latitude = 50
         */
        Boötes("Herdsman",  906.831, Quadrant.QUADRANT.NQ3,90, 50,6),
        /**
         * "Chisel", area = 124.865, Quadrant = SQ1, starting latitude = 40, ending latitude = 90
         */
        Caelum("Chisel", 124.865, Quadrant.QUADRANT.SQ1,40, 90,1),
        /**
         * "Giraffe", area = 756.828, Quadrant = NQ2, starting latitude = 90, ending latitude = 10
         */
        Camelopardalis("Giraffe", 756.828, Quadrant.QUADRANT.NQ2,90, 10,2),
        /**
         * "Crab", area = 505.872, Quadrant = NQ2, starting latitude = 90, ending latitude = 60
         */
        Cancer("Crab", 505.872, Quadrant.QUADRANT.NQ2,90, 60,3),
        /**
         * "Hunting Dogs", area = 465.194, Quadrant = NQ3, starting latitude = 90, ending latitude = 40
         */
        CanesVenatici("Hunting Dogs", 465.194, Quadrant.QUADRANT.NQ3,90, 40,5),
        /**
         * "Greater Dog", area = 380.118, Quadrant = SQ2, starting latitude = 60, ending latitude = 90
         */
        CanisMajor("Greater Dog", 380.118, Quadrant.QUADRANT.SQ2,60, 90,2),
        /**
         * "Lesser Dog", area = 183.367, Quadrant = NQ2, starting latitude = 90, ending latitude = 75
         */
        CanisMinor("Lesser Dog", 183.367, Quadrant.QUADRANT.NQ2,90, 75,3),
        /**
         * "Sea Goat", area = 413.947, Quadrant = SQ4, starting latitude = 60, ending latitude = 90
         */
        Capricornus("Sea Goat", 413.947, Quadrant.QUADRANT.SQ4,60, 90,9),
        /**
         * "Keel", area = 494.184, Quadrant = SQ2, starting latitude = 20, ending latitude = 90
         */
        Carina("Keel", 494.184, Quadrant.QUADRANT.SQ2,20, 90,3),
        /**
         * "Cassiopeia", area = 598.407, Quadrant = NQ1, starting latitude = 90, ending latitude = 20
         */
        Cassiopeia("Cassiopeia", 598.407, Quadrant.QUADRANT.NQ1,90, 20,11),
        /**
         * "Centaur", area = 1060.422, Quadrant = SQ3, starting latitude = 25, ending latitude = 90
         */
        Centaurus("Centaur", 1060.422, Quadrant.QUADRANT.SQ3,25, 90,5),
        /**
         * "Cepheus", area = 587.787, Quadrant = NQ4, starting latitude = 90, ending latitude = 10
         */
        Cepheus("Cepheus", 587.787, Quadrant.QUADRANT.NQ4,90, 10,11),
        /**
         * "Whale (or Sea Monster)", area = 1231.411, Quadrant = SQ1, starting latitude = 70, ending latitude = 90
         */
        Cetus("Whale (or Sea Monster)", 1231.411, Quadrant.QUADRANT.SQ1,70, 90,11),
        /**
         * "Chameleon", area = 131.592, Quadrant = SQ2, starting latitude = 0, ending latitude = 90
         */
        Chamaeleon("Chameleon", 131.592, Quadrant.QUADRANT.SQ2,0, 90,4),
        /**
         * "Compass (drafting tool)", area = 93.353, Quadrant = SQ3, starting latitude = 30, ending latitude = 90
         */
        Circinus("Compass (drafting tool)", 93.353, Quadrant.QUADRANT.SQ3,30, 90,7),
        /**
         * "Dove", area = 270.184, Quadrant = SQ1, starting latitude = 45, ending latitude = 90
         */
        Columba("Dove", 270.184, Quadrant.QUADRANT.SQ1,45, 90,2),
        /**
         * "Berenice’s Hair", area = 386.475, Quadrant = NQ3, starting latitude = 90, ending latitude = 70
         */
        ComaBerenices("Berenice’s Hair", 386.475, Quadrant.QUADRANT.NQ3,90, 70,5),
        /**
         * "Southern Crown", area = 127.696, Quadrant = SQ4, starting latitude = 40, ending latitude = 90
         */
        CoronaAustralis("Southern Crown", 127.696, Quadrant.QUADRANT.SQ4,40, 90,8),
        /**
         * "Northern Crown", area = 178.71, Quadrant = NQ3, starting latitude = 90, ending latitude = 50
         */
        CoronaBorealis("Northern Crown", 178.71, Quadrant.QUADRANT.NQ3,90, 50,7),
        /**
         * "Crow", area = 183.801, Quadrant = SQ3, starting latitude = 60, ending latitude = 90
         */
        Corvus("Crow", 183.801, Quadrant.QUADRANT.SQ3,60, 90,5),
        /**
         * "Cup", area = 282.398, Quadrant = SQ2, starting latitude = 65, ending latitude = 90
         */
        Crater("Cup", 282.398, Quadrant.QUADRANT.SQ2,65, 90,4),
        /**
         * "Southern Cross", area = 68.447, Quadrant = SQ3, starting latitude = 20, ending latitude = 90
         */
        Crux("Southern Cross", 68.447, Quadrant.QUADRANT.SQ3,20, 90,5),
        /**
         * "Swan", area = 803.983, Quadrant = NQ4, starting latitude = 90, ending latitude = 40
         */
        Cygnus("Swan", 803.983, Quadrant.QUADRANT.NQ4,90, 40,9),
        /**
         * "Dolphin", area = 188.549, Quadrant = NQ4, starting latitude = 90, ending latitude = 70
         */
        Delphinus("Dolphin", 188.549, Quadrant.QUADRANT.NQ4,90, 70,9),
        /**
         * "Dolphinfish", area = 179.173, Quadrant = SQ1, starting latitude = 20, ending latitude = 90
         */
        Dorado("Dolphinfish", 179.173, Quadrant.QUADRANT.SQ1,20, 90,1),
        /**
         * "Dragon", area = 1082.952, Quadrant = NQ3, starting latitude = 90, ending latitude = 15
         */
        Draco("Dragon", 1082.952, Quadrant.QUADRANT.NQ3,90, 15,7),
        /**
         * "Little Horse (Foal)", area = 71.641, Quadrant = NQ4, starting latitude = 90, ending latitude = 80
         */
        Equuleus("Little Horse (Foal)", 71.641, Quadrant.QUADRANT.NQ4,90, 80,9),
        /**
         * "Eridanus (river)", area = 1137.919, Quadrant = SQ1, starting latitude = 32, ending latitude = 90
         */
        Eridanus("Eridanus (river)", 1137.919, Quadrant.QUADRANT.SQ1,32, 90,12),
        /**
         * "Furnace", area = 397.502, Quadrant = SQ1, starting latitude = 50, ending latitude = 90
         */
        Fornax("Furnace", 397.502, Quadrant.QUADRANT.SQ1,50, 90,12),
        /**
         * "Twins", area = 513.761, Quadrant = NQ2, starting latitude = 90, ending latitude = 60
         */
        Gemini("Twins", 513.761, Quadrant.QUADRANT.NQ2,90, 60,2),
        /**
         * "Crane", area = 365.513, Quadrant = SQ4, starting latitude = 34, ending latitude = 90
         */
        Grus("Crane", 365.513, Quadrant.QUADRANT.SQ4,34, 90,10),
        /**
         * "Hercules", area = 1225.148, Quadrant = NQ3, starting latitude = 90, ending latitude = 50
         */
        Hercules("Hercules", 1225.148, Quadrant.QUADRANT.NQ3,90, 50,7),
        /**
         * "Pendulum Clock", area = 248.885, Quadrant = SQ1, starting latitude = 30, ending latitude = 90
         */
        Horologium("Pendulum Clock", 248.885, Quadrant.QUADRANT.SQ1,30, 90,12),
        /**
         * "Hydra", area = 1302.844, Quadrant = SQ2, starting latitude = 54, ending latitude = 83
         */
        Hydra("Hydra", 1302.844, Quadrant.QUADRANT.SQ2,54, 83,4),
        /**
         * "Water Snake", area = 243.035, Quadrant = SQ1, starting latitude = 8, ending latitude = 90
         */
        Hydrus("Water Snake", 243.035, Quadrant.QUADRANT.SQ1,8, 90,11),
        /**
         * "Indian", area = 294.006, Quadrant = SQ4, starting latitude = 15, ending latitude = 90
         */
        Indus("Indian", 294.006, Quadrant.QUADRANT.SQ4,15, 90,9),
        /**
         * "Lizard", area = 200.688, Quadrant = NQ4, starting latitude = 90, ending latitude = 40
         */
        Lacerta("Lizard", 200.688, Quadrant.QUADRANT.NQ4,90, 40,10),
        /**
         * "Lion", area = 946.964, Quadrant = NQ2, starting latitude = 90, ending latitude = 65
         */
        Leo("Lion", 946.964, Quadrant.QUADRANT.NQ2,90, 65,4),
        /**
         * "Lesser Lion", area = 231.956, Quadrant = NQ2, starting latitude = 90, ending latitude = 45
         */
        LeoMinor("Lesser Lion", 231.956, Quadrant.QUADRANT.NQ2,90, 45,4),
        /**
         * "Hare", area = 290.291, Quadrant = SQ1, starting latitude = 63, ending latitude = 90
         */
        Lepus("Hare", 290.291, Quadrant.QUADRANT.SQ1,63, 90,1),
        /**
         * "Scales", area = 538.052, Quadrant = SQ3, starting latitude = 65, ending latitude = 90
         */
        Libra("Scales", 538.052, Quadrant.QUADRANT.SQ3,65, 90,6),
        /**
         * "Wolf", area = 333.683, Quadrant = SQ3, starting latitude = 35, ending latitude = 90
         */
        Lupus("Wolf", 333.683, Quadrant.QUADRANT.SQ3,35, 90,6),
        /**
         * "Lynx", area = 545.386, Quadrant = NQ2, starting latitude = 90, ending latitude = 55
         */
        Lynx("Lynx", 545.386, Quadrant.QUADRANT.NQ2,90, 55,3),
        /**
         * "Lyre", area = 286.476, Quadrant = NQ4, starting latitude = 90, ending latitude = 40
         */
        Lyra("Lyre", 286.476, Quadrant.QUADRANT.NQ4,90, 40,8),
        /**
         * "Table Mountain (Mons Mensae)", area = 153.484, Quadrant = SQ1, starting latitude = 4, ending latitude = 90
         */
        Mensa("Table Mountain (Mons Mensae)", 153.484, Quadrant.QUADRANT.SQ1,4, 90,1),
        /**
         * "Microscope", area = 209.513, Quadrant = SQ4, starting latitude = 45, ending latitude = 90
         */
        Microscopium("Microscope", 209.513, Quadrant.QUADRANT.SQ4,45, 90,9),
        /**
         * "Unicorn", area = 481.569, Quadrant = NQ2, starting latitude = 75, ending latitude = 90
         */
        Monoceros("Unicorn", 481.569, Quadrant.QUADRANT.NQ2,75, 90,2),
        /**
         * "Fly", area = 138.355, Quadrant = SQ3, starting latitude = 10, ending latitude = 90
         */
        Musca("Fly", 138.355, Quadrant.QUADRANT.SQ3,10, 90,5),
        /**
         * "Level", area = 165.29, Quadrant = SQ3, starting latitude = 30, ending latitude = 90
         */
        Norma("Level", 165.29, Quadrant.QUADRANT.SQ3,30, 90,7),
        /**
         * "Octant", area = 291.045, Quadrant = SQ4, starting latitude = 0, ending latitude = 90
         */
        Octans("Octant", 291.045, Quadrant.QUADRANT.SQ4,0, 90,10),
        /**
         * "Serpent Bearer", area = 948.34, Quadrant = SQ3, starting latitude =  80, ending latitude = 80
         */
        Ophiuchus("Serpent Bearer", 948.34, Quadrant.QUADRANT.SQ3, 80, 80,7),
        /**
         * "Orion (the Hunter)", area = 594.12, Quadrant = NQ1, starting latitude = 85, ending latitude = 75
         */
        Orion("Orion (the Hunter)", 594.12, Quadrant.QUADRANT.NQ1,85, 75,1),
        /**
         * "Peacock", area = 377.666, Quadrant = SQ4, starting latitude = 30, ending latitude = 90
         */
        Pavo("Peacock", 377.666, Quadrant.QUADRANT.SQ4,30, 90,8),
        /**
         * "Pegasus", area = 1120.794, Quadrant = NQ4, starting latitude = 90, ending latitude = 60
         */
        Pegasus("Pegasus", 1120.794, Quadrant.QUADRANT.NQ4,90, 60,10),
        /**
         * "Perseus", area = 614.997, Quadrant = NQ1, starting latitude = 90, ending latitude = 35
         */
        Perseus("Perseus", 614.997, Quadrant.QUADRANT.NQ1,90, 35,12),
        /**
         * "Phoenix", area = 469.319, Quadrant = SQ1, starting latitude = 32, ending latitude = 80
         */
        Phoenix("Phoenix", 469.319, Quadrant.QUADRANT.SQ1,32, 80,11),
        /**
         * "Easel", area = 246.739, Quadrant = SQ1, starting latitude = 26, ending latitude = 90
         */
        Pictor("Easel", 246.739, Quadrant.QUADRANT.SQ1,26, 90,1),
        /**
         * "Fishes", area = 889.417, Quadrant = NQ1, starting latitude = 90, ending latitude = 65
         */
        Pisces("Fishes", 889.417, Quadrant.QUADRANT.NQ1,90, 65,11),
        /**
         * "Southern Fish", area = 245.375, Quadrant = SQ4, starting latitude = 55, ending latitude = 90
         */
        PiscisAustrinus("Southern Fish", 245.375, Quadrant.QUADRANT.SQ4,55, 90,10),
        /**
         * "Stern", area = 673.434, Quadrant = SQ2, starting latitude = 40, ending latitude = 90
         */
        Puppis("Stern", 673.434, Quadrant.QUADRANT.SQ2,40, 90,2),
        /**
         * "Compass (mariner’s compass)", area = 220.833, Quadrant = SQ2, starting latitude = 50, ending latitude = 90
         */
        Pyxis("Compass (mariner’s compass)", 220.833, Quadrant.QUADRANT.SQ2,50, 90,3),
        /**
         * "Reticle", area = 113.936, Quadrant = SQ1, starting latitude = 23, ending latitude = 90
         */
        Reticulum("Reticle", 113.936, Quadrant.QUADRANT.SQ1,23, 90,1),
        /**
         * "Arrow", area = 79.932, Quadrant = NQ4, starting latitude = 90, ending latitude = 70
         */
        Sagitta("Arrow", 79.932, Quadrant.QUADRANT.NQ4,90, 70,8),
        /**
         * "Archer", area = 867.432, Quadrant = SQ4, starting latitude = 55, ending latitude = 90
         */
        Sagittarius("Archer", 867.432, Quadrant.QUADRANT.SQ4,55, 90,8),
        /**
         * "Scorpion", area = 496.783, Quadrant = SQ3, starting latitude = 40, ending latitude = 90
         */
        Scorpius("Scorpion", 496.783, Quadrant.QUADRANT.SQ3,40, 90,7),
        /**
         * "Sculptor", area = 474.764, Quadrant = SQ1, starting latitude = 50, ending latitude = 90
         */
        Sculptor("Sculptor", 474.764, Quadrant.QUADRANT.SQ1,50, 90,11),
        /**
         * "Shield (of Sobieski)", area = 109.114, Quadrant = SQ4, starting latitude = 80, ending latitude = 90
         */
        Scutum("Shield (of Sobieski)", 109.114, Quadrant.QUADRANT.SQ4,80, 90,8),
        /**
         * "Snake", area = 636.928, Quadrant = NQ3, starting latitude = 80, ending latitude = 80
         */
        Serpens("Snake", 636.928, Quadrant.QUADRANT.NQ3,80, 80,7),
        /**
         * "Sextant", area = 313.515, Quadrant = SQ2, starting latitude = 80, ending latitude = 90
         */
        Sextans("Sextant", 313.515, Quadrant.QUADRANT.SQ2,80, 90,4),
        /**
         * "Bull", area = 797.249, Quadrant = NQ1, starting latitude = 90, ending latitude = 65
         */
        Taurus("Bull", 797.249, Quadrant.QUADRANT.NQ1,90, 65,1),
        /**
         * "Telescope", area = 251.512, Quadrant = SQ4, starting latitude = 40, ending latitude = 90
         */
        Telescopiu("Telescope", 251.512, Quadrant.QUADRANT.SQ4,40, 90,8),
        /**
         * "Triangle", area = 131.847, Quadrant = NQ1, starting latitude = 90, ending latitude = 60
         */
        Triangulum("Triangle", 131.847, Quadrant.QUADRANT.NQ1, 90, 60,12),
        /**
         * "Southern Triangle", area = 109.978, Quadrant = SQ3, starting latitude = 25, ending latitude = 90
         */
        TriangulumAustrale("Southern Triangle", 109.978, Quadrant.QUADRANT.SQ3,25, 90,7),
        /**
         * "Toucan", area = 294.557, Quadrant = SQ4, starting latitude = 25, ending latitude = 90
         */
        Tucana("Toucan", 294.557, Quadrant.QUADRANT.SQ4,25, 90,11),
        /**
         * "Great Bear", area = 1279.66, Quadrant = NQ2, starting latitude = 90, ending latitude = 30
         */
        UrsaMajor("Great Bear", 1279.66, Quadrant.QUADRANT.NQ2,90, 30,4),
        /**
         * "Little Bear", area = 255.864, Quadrant = NQ3, starting latitude = 90, ending latitude = 10
         */
        UrsaMinor("Little Bear", 255.864, Quadrant.QUADRANT.NQ3,90, 10,6),
        /**
         * "Sails", area = 499.649, Quadrant = SQ2, starting latitude = 30, ending latitude = 90
         */
        Vela("Sails", 499.649, Quadrant.QUADRANT.SQ2,30, 90,3),
        /**
         * "Virgin (Maiden)", area = 1294.428, Quadrant = SQ3, starting latitude =  starting latitude = 80, ending latitude = 80
         */
        Virgo("Virgin (Maiden)", 1294.428, Quadrant.QUADRANT.SQ3,80, 80,5),
        /**
         * "Flying Fish", area = 141.354, Quadrant = SQ2, starting latitude = 15, ending latitude = 90
         */
        Volans("Flying Fish", 141.354, Quadrant.QUADRANT.SQ2,15, 90,3),
        /**
         * "Fox", area = 268.165, Quadrant = NQ4, starting latitude = 90, ending latitude = 55
         */
        Vulpecula("Fox", 268.165, Quadrant.QUADRANT.NQ4,90, 55,11);

        public final String engName;
        public final double area;
        public final Quadrant.QUADRANT quadrant;
        public final int startingLatitude;
        public final int endingLatitude;
        public final int month;

        /**
         * The constructor of the CONSTELLATIONS enum
         * @param engName English name of the star system
         * @param area the area of the star system
         * @param quadrant the quadrant of the star system
         * @param startingLatitude the starting latitude
         * @param endingLatitude the ending latitude
         */
        CONSTELLATIONS(String engName, double area, Quadrant.QUADRANT quadrant,
                       int startingLatitude, int endingLatitude, int month) {
            this.engName = engName;
            this.area = area;
            this.quadrant = quadrant;
            this.startingLatitude = startingLatitude;
            this.endingLatitude = endingLatitude;
            this.month = month;
        }

        /**
         * Return the star system's quadrant
         * @return the quadrant
         */
        public Quadrant.QUADRANT getQuadrant() { return quadrant; }

        public int getmonth() { return month; }
    }
}