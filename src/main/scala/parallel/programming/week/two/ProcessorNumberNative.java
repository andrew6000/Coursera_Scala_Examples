package parallel.programming.week.two;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class ProcessorNumberNative {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary("Kernel32.dll",
                        CLibrary.class);

        Integer GetCurrentProcessorNumber();
    }
}