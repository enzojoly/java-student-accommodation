package uwe.tae.sys.model;

public enum CleaningStatus {
    CLEAN {
        @Override
        public String toString() {
            return "CLEAN";
        }
    },
    DIRTY {
        @Override
        public String toString() {
            return "DIRTY";
        }
    },
    OFFLINE {
        @Override
        public String toString() {
            return "OFFLINE";
        }
    };
}
