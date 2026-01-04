package nbu.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceConfig {

    // FixedThreadPool с 4 нишки, който се създава веднъж и не се промня
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}