package parallel.programming.week.two;

import com.sun.jna.Library;
import com.sun.jna.Native;


//https://stackoverflow.com/questions/45611701/how-does-scala-use-all-my-cores-here
public class ProcessorNumberNative {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary("Kernel32.dll",
                        CLibrary.class);

        Integer GetCurrentProcessorNumber();
    }
}