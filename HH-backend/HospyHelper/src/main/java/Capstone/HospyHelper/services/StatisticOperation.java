package Capstone.HospyHelper.services;

import Capstone.HospyHelper.employee.Employee;
import Capstone.HospyHelper.room.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatisticOperation {

    public double calculateRoomPrice(Room room) {
        if (room == null) {
            return 0.0;
        }
        double basePrice = room.getPrice();
        double seasonalAdjustment = calculateSeasonalAdjustment(room);
        return basePrice + seasonalAdjustment;
    }
    private double calculateSeasonalAdjustment(Room room) {
        return isHighSeason() ? room.getPrice() * 0.1 : 0.0;
    }
    private boolean isHighSeason() {
        int currentMonth = LocalDate.now().getMonthValue();
        return currentMonth >= 1 && currentMonth <= 8;
    }

    public double calculateEmployeeSalary(Employee employee) {
        return employee.getSalary();
    }

}
