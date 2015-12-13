package util;

import instructions.Operand2;

public class Register {

    private String name;

    public Register(String name) {
        this.name = name;
    }

    public Register() {}

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Register) {
            return ((Register) obj).name.equals(this.name);
        }
        if(obj instanceof Operand2) {
            Register other = ((Operand2) obj).getReg();
            return other != null && other.equals(this);
        }
        return false;
    }
}
