package kz.sgq.utils;

public class KeyGen {
    private final String DIGITS = "0123456789";
    private final char[] digit = DIGITS.toCharArray();
    private final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final char[] lower = LOWERCASE.toCharArray();
    private final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final char[] upper = UPPERCASE.toCharArray();

    public String generate(int amout) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amout; i++) {
            int chooseType = (int) (Math.random() * 3);
            switch (chooseType) {
                case 0: // lowercase
                    sb.append(lower[randomRange(0, 25)]);
                    break;
                case 1: // uppercase
                    sb.append(upper[randomRange(0, 25)]);
                    break;
                case 2: // digits
                    sb.append(digit[randomRange(0, 9)]);
                    break;
            }
        }
        return sb.toString();
    }

    private int randomRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}