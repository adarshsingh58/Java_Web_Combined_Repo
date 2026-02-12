package javaconcepts.multithreading.IntQuestions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Problem Statement
 * <p>
 * Suppose we have a machine that creates molecules by combining atoms. We are creating water molecules by joining one
 * Oxygen and two Hydrogen atoms. The atoms are represented by threads. The machine will wait for the required atoms
 * (threads), then group one Oxygen and two Hydrogen threads to simulate the creation of a molecule. The molecule then
 * exists the machine. You have to ensure that one molecule is completed before moving onto the next molecule. If more
 * than the required number of threads arrive, they will have to wait. Two Hydrogen threads are admitted in the machine
 * as they arrive but when the third thread arrives in step 3, it is made to wait. When an Oxygen thread arrives in step
 * 4, it is allowed to enter the machine. A water molecule is formed in step 5 which exists the machine in step 6. That
 * is when the waiting Hydrogen thread is notified and the process of creating more molecules continues. The threads can
 * arrive in any order which means that HHO, OHH and HOH are all valid outputs.
 *
 */
public class BuildAMolecule {
    public static void main(String[] args) {
        BuildAMolecule buildAMolecule = new BuildAMolecule();
        buildAMolecule.m1();
    }

    /*
     * Here we have initiated 10 threads. We are submitted 20 task each for
     * adding a Hyd atom and Oxygen atom. They all are working on one instance of
     * H2OMachine object,h2OMachine.
     * Atoms are thread here and creating of Molecule is task.
     *
     * Now because we need exactly 2 hyd and 1 oxy, we need to put some sort of
     * count check. Countdown latch may not be a good idea.
     *
     * */
    private void m1() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        H2OMachine h2OMachine = new H2OMachine();

        for (int i = 0; i < 20; i++) {
            executorService.submit(h2OMachine::HydrogenAtom);
        }

        for (int i = 0; i < 10; i++) {
            executorService.submit(h2OMachine::OxygenAtom);
        }

        executorService.shutdown();
    }
}

class H2OMachine {

    int hAtom = 0;
    int oAtom = 0;

    public H2OMachine() {
    }

    public synchronized void HydrogenAtom() {
        try {
            while (hAtom >= 2) {
                wait();
            }
            hAtom++;
            System.out.println("One Hyd atom added");
            notifyAll();
            if (hAtom == 2 && oAtom == 1) {
                buildMolecule();
                hAtom = 0;
                oAtom = 0;
            }
        } catch (Exception e) {

        }
    }

    public synchronized void OxygenAtom() {
        try {
            while (oAtom >= 1) {
                wait();
            }
            oAtom++;
            System.out.println("One Oxy atom added");
            notifyAll();
            if (hAtom == 2 && oAtom == 1) {
                buildMolecule();
                hAtom = 0;
                oAtom = 0;
            }
        } catch (Exception e) {

        }
    }

    private void buildMolecule() {
        System.out.println("Molecule is built");
    }
}

