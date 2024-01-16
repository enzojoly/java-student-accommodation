package uwe.tae.sys.model;

public enum AvailabilityStatus {
    AVAILABLE {
        @Override
        public String toString() {
            return "AVAILABLE";
        }
    },
    UNAVAILABLE {
        @Override
        public String toString() {
            return "UNAVAILABLE";
        }
    };
}
