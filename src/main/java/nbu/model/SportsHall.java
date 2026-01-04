package nbu.model;

import java.util.HashMap;
import java.util.Map;

public class SportsHall {

    // Map-ва се категория -> max капацитет
    // final, защото не се строят нови сектори по време на работа
    private final Map<String, Integer> capacities;

    // Map-ва категория -> заети места в момента (променят се динамично)
    private final Map<String, Integer> occupiedSeats;

    public SportsHall() {
        this.capacities = new HashMap<>();
        this.occupiedSeats = new HashMap<>();

        initSector("VIP", 50);
        initSector("Сектор А", 125);
        initSector("Сектор Б", 175);
    }

    private void initSector(String category, int capacity) {
        capacities.put(category, capacity);
        occupiedSeats.put(category, 0); // в началото залата е празна
    }

    /**
     * осигурява thread-safe достъп и предотвратява race condition
     * гарантира, че проверката и настаняването са атомарна (неделима) операция
     */
    public synchronized void occupySeats(SpectatorGroup group, String entranceName) {
        String category = group.getCategory();
        int peopleToAdd = group.getPeopleCount();

        if (!capacities.containsKey(category)) {
            System.out.println("Грешка: Невалидна категория " + category);
            return;
        }

        int currentOccupied = occupiedSeats.get(category);
        int maxCapacity = capacities.get(category);
        int freeSeats = maxCapacity - currentOccupied;

        // има ли място за цялата група?
        if (freeSeats >= peopleToAdd) {
            // има място -> настани групата
            int newOccupiedCount = currentOccupied + peopleToAdd;
            occupiedSeats.put(category, newOccupiedCount);

            System.out.printf("[OK] %s настани %s. (Заети: %d/%d)%n",
                    entranceName, group, newOccupiedCount, maxCapacity);
        } else {
            // няма място -> върни ги
            System.out.printf("[NO] %s НЕ пусна %s. Места: %d, Търсени: %d%n",
                    entranceName, group, freeSeats, peopleToAdd);
        }
    }

    public void printFinalStats() {
        System.out.println("\n=== КРАЙНА СТАТИСТИКА НА ЗАЛАТА ===");
        for (String cat : capacities.keySet()) {
            int occupied = occupiedSeats.get(cat);
            int total = capacities.get(cat);
            // изчисляване на % запълване
            double percent = (occupied / (double) total) * 100;
            System.out.printf("- %s: %d от %d места (%.1f%%)%n", cat, occupied, total, percent);
        }
    }
}