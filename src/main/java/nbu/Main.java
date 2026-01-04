package nbu;

import nbu.config.ExecutorServiceConfig;
import nbu.model.SpectatorGroup;
import nbu.model.SportsHall;
import nbu.tasks.AdmissionTask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        SportsHall sportsHall = new SportsHall();

        ExecutorService executor = ExecutorServiceConfig.getExecutorService();

        System.out.println("--- ЗАПОЧВА НАСТАНЯВАНЕТО ---\n");

        String[] categories = {"VIP", "Сектор А", "Сектор Б"};
        Random random = new Random();

        // 30 групи
        for (int i = 1; i <= 30; i++) {
            String randomCat = categories[random.nextInt(categories.length)];
            int randomCount = random.nextInt(12) + 1; // 1 до 12 души

            SpectatorGroup group = new SpectatorGroup(i, randomCat, randomCount);

            // подава се залата на всяка задача, за да знаят къде да настаняват хората
            AdmissionTask task = new AdmissionTask(sportsHall, group);

            executor.submit(task);
        }

        // спира приема на нови задачи
        executor.shutdown();

        try {
            // изчаква всички нишки да приключат
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                sportsHall.printFinalStats();
            } else {
                System.out.println("Времето изтече и не всички успяха да влязат!");
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}