package util;

public class Register {

    private String name;
    private boolean inUse;

    public Register(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
