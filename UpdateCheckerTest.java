class UpdateCheckerTest {
    static boolean isNewerVersion(String current, String latest) {
        String cleanCurrent = current.replace("v", "").split("-")[0];
        String cleanLatest = latest.replace("v", "").split("-")[0];
        String[] currentParts = cleanCurrent.split("\\.");
        String[] latestParts = cleanLatest.split("\\.");

        int length = Math.max(currentParts.length, latestParts.length);
        for (int i = 0; i < length; i++) {
            int c = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int l = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;
            if (l > c) return true;
            if (l < c) return false;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("2.1.0 vs 2.1.3: " + isNewerVersion("2.1.0", "2.1.3"));
        System.out.println("3.0.0 vs 2.1.3: " + isNewerVersion("3.0.0", "2.1.3"));
    }
}