package mc322_slay;

public enum ColorEnum {
    reset("\u001B[0m"), red("\u001B[31m"), green("\u001B[32m"), yellow("\u001B[33m"), blue("\u001B[34m");

    private final String color;

    ColorEnum(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
