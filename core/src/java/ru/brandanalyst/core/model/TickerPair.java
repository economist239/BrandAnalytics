package ru.brandanalyst.core.model;


public class TickerPair {
        private final String name;
        private final long id;

        public TickerPair(String name, long id) {
            this.name = name;
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

