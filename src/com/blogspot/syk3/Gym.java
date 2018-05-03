package com.blogspot.syk3;

import java.util.Set;

public class Gym {
    private GymType gymType;
    private Address address;
    private Set<Equipment> equipment;

    // Constructor
    public Gym(GymType gymType, Address address, Set<Equipment> equipment) {
        // assign default enum if gymType is null
        if (gymType == null) {
            gymType = GymType.MAIN;
        }
        this.gymType = gymType;
        this.address = address;
        this.equipment = equipment;
    }

    // gymType getter
    public GymType getGymType() {
        return gymType;
    }

    // gymType setter
    public void setGymType(GymType gymType) {
        this.gymType = gymType;
    }

    // address getter
    public Address getAddress() {
        return address;
    }

    // address setter
    public void setAddress(Address address) {
        this.address = address;
    }

    // equipment getter
    public Set<Equipment> getEquipment() {
        return equipment;
    }

    // equipment adder
    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    // equipment remover
    public void removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
    }

    // when printing Gym as String
    public String toString() {
        return this.address.toString() + ", " + this.gymType.toString() + ", " + this.equipment.toString();
    }
}
