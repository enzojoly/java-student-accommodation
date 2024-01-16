package uwe.tae.sys.model;

public enum AccommodationType {
    STANDARD {
        @Override
        public String toString() {
            return "STANDARD";
        }
    },
    SUPERIOR {
        @Override
        public String toString() {
            return "SUPERIOR";
        }
    };
}
