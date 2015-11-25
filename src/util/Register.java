package util;

public class Register {

    private String name;
    private boolean inUse;

    public Register(String name) {
        this.name = name;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void use() {
        inUse = true;
    }

    public void free() {
        inUse = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
