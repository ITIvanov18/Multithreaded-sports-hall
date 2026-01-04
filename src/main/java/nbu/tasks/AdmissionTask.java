package nbu.tasks;

import nbu.model.SpectatorGroup;
import nbu.model.SportsHall;

public class AdmissionTask implements Runnable {

    private final SportsHall hall;
    private final SpectatorGroup group;

    public AdmissionTask(SportsHall hall, SpectatorGroup group) {
        this.hall = hall;
        this.group = group;
    }

    @Override
    public void run() {
        // симулация на работата на едно гише
        try {
            // взима името на нишката
            String rawThreadName = Thread.currentThread().getName();

            // извлича само номера ѝ (примерно "Вход 2")
            char threadNumber = rawThreadName.charAt(rawThreadName.length() - 1);
            String entranceName = "Вход " + threadNumber;

            System.out.println(entranceName + " обработва " + group + "...");

            // време за проверка на билети (500 милисекунди)
            Thread.sleep(500);

            // опит за настаняване на групата (влиза в синхронизирания ресурс)
            hall.occupySeats(group, entranceName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задачата беше прекъсната!");
        }
    }
}