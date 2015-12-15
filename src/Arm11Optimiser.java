import instructions.*;
import util.Registers;

import java.util.List;

public class Arm11Optimiser {

    private WaccArm11Generator generator;

    private int lastStepChanges = 0;

    public Arm11Optimiser(WaccArm11Generator generator) {
        this.generator = generator;
    }

    public void optimise() {
        do {
            lastStepChanges = 0;
            optimisationStep();
        } while(lastStepChanges != 0);
    }

    private void optimisationStep() {
        for(List<Instruction> func : generator.getProgram().getCode().values()) {
            for(int i = 0; i < func.size() - 1; i++) {
                // pop 2 instructions at position i and i + 1
                Instruction curr = func.remove(i);
                Instruction next = func.remove(i);

                // assume successful optimisation
                lastStepChanges++;

                // OPTIMISATIONS

                // load to reg, then instantly move to another reg
                if(curr instanceof LoadInstruction && next instanceof MoveInstruction) {
                    LoadInstruction load = (LoadInstruction) curr;
                    MoveInstruction move = (MoveInstruction) next;
                    if(load.getDest().equals(move.getSrc())) {
                        func.add(i, new LoadInstruction(move.getDest(), load.getSrc()));
                        continue;
                    }
                }

                // store reg then load back to it
                if(curr instanceof StoreInstruction && next instanceof LoadInstruction) {
                    StoreInstruction store = (StoreInstruction) curr;
                    LoadInstruction load = (LoadInstruction) next;
                    if(store.getDest().equals(load.getSrc()) && store.getSrc().equals(load.getDest())
                            && store.getOffset() == load.getOffset()) {
                        func.add(i, store);
                        continue;
                    }
                }

                // double pop stack pointer
                if(curr instanceof PopInstruction && next instanceof PopInstruction) {
                    PopInstruction p1 = (PopInstruction) curr;
                    PopInstruction p2 = (PopInstruction) next;
                    if(p1.getReg().equals(Registers.sp) && p2.getReg().equals(Registers.sp)) {
                        func.add(i, p1);
                        continue;
                    }
                }

                // add instructions back if no optimisation found, and correct optimisation counter
                func.add(i, next);
                func.add(i, curr);
                lastStepChanges--;
            }
        }
    }
}