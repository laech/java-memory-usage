package mem;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @see <a href="http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/87ee5ee27509/src/share/vm/oops/markOop.hpp#l35">Object Header</a>
 */
class Main {

    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        Main.inst = inst;
        // A side note, executing the same code as in main() here will take a lot longer
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println();

        printFlags();

        System.out.println();
        printSize(inst, new Object());

        System.out.println();
        printSize(inst, new EmptyNumber());

        System.out.println();
        printSize(inst, true);
        printSize(inst, (byte) 0);
        printSize(inst, (short) 0);
        printSize(inst, '0');
        printSize(inst, 0);
        printSize(inst, (float) 0);
        printSize(inst, (double) 0);
        printSize(inst, (long) 0);

        System.out.println();
        printSize(inst, new boolean[0], "boolean[0]");
        printSize(inst, new boolean[1], "boolean[1]");
        printSize(inst, new boolean[2], "boolean[2]");
        printSize(inst, new boolean[3], "boolean[3]");
        printSize(inst, new boolean[4], "boolean[4]");
        printSize(inst, new boolean[5], "boolean[5]");
        printSize(inst, new boolean[6], "boolean[6]");
        printSize(inst, new boolean[7], "boolean[7]");
        printSize(inst, new boolean[8], "boolean[8]");
        printSize(inst, new boolean[9], "boolean[9]");

        System.out.println();
        printSize(inst, new byte[0], "byte[0]");
        printSize(inst, new byte[1], "byte[1]");
        printSize(inst, new byte[2], "byte[2]");
        printSize(inst, new byte[3], "byte[3]");
        printSize(inst, new byte[4], "byte[4]");
        printSize(inst, new byte[5], "byte[5]");
        printSize(inst, new byte[6], "byte[6]");
        printSize(inst, new byte[7], "byte[7]");
        printSize(inst, new byte[8], "byte[8]");
        printSize(inst, new byte[9], "byte[9]");

        System.out.println();
        printSize(inst, new short[0], "short[0]");
        printSize(inst, new short[1], "short[1]");
        printSize(inst, new short[2], "short[2]");
        printSize(inst, new short[3], "short[3]");
        printSize(inst, new short[4], "short[4]");
        printSize(inst, new short[5], "short[5]");

        System.out.println();
        printSize(inst, new char[0], "char[0]");
        printSize(inst, new char[1], "char[1]");
        printSize(inst, new char[2], "char[2]");
        printSize(inst, new char[3], "char[3]");
        printSize(inst, new char[4], "char[4]");
        printSize(inst, new char[5], "char[5]");

        System.out.println();
        printSize(inst, new int[0], "int[0]");
        printSize(inst, new int[1], "int[1]");
        printSize(inst, new int[2], "int[2]");
        printSize(inst, new int[3], "int[3]");

        System.out.println();
        printSize(inst, new long[0], "long[0]");
        printSize(inst, new long[1], "long[1]");
        printSize(inst, new long[2], "long[2]");
        printSize(inst, new long[3], "long[3]");

        System.out.println();
    }

    private static void printFlags() throws InterruptedException, IOException {
        String pid = getPid();
        System.out.println("PID: " + pid);
        new ProcessBuilder("jinfo", "-flag", "ObjectAlignmentInBytes", pid).inheritIO().start().waitFor();
        new ProcessBuilder("jinfo", "-flag", "UseCompressedOops", pid).inheritIO().start().waitFor();
    }

    private static String getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        return runtime.getName().split("@")[0];
    }

    private static void printSize(Instrumentation inst, Object o) {
        printSize(inst, o, o.getClass().getName());
    }

    private static void printSize(Instrumentation inst, Object o, String label) {
        System.out.format("%-22s%6s bytes%n", label, inst.getObjectSize(o));
    }
}
