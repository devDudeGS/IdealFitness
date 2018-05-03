package com.blogspot.syk3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

public class Application {

    public static void main(String[] args) {
        // list of memberships and gyms
        List<Membership> memberships = new ArrayList<>();
        List<Gym> gyms = new ArrayList<>();

        // read existing data from file into application
        try {
            // read csv file in lieu of DB
            //String fileName = args[0];
            String fileName = "idealfitnessdata.csv";

            // read file with FileReader and wrap with BufferedReader
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // add each line of file to an ArrayList
            List<String> fileLines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileLines.add(line);
            }

            // loop through ArrayList
            for (int i = 0; i < fileLines.size(); i++) {
                // for each line, split into String array by commas
                String[] lineParts = fileLines.get(i).split(",");

                switch (lineParts[0]) {
                    // if the array begins with "gym"
                    case "gym": {
                        // address variables are in indices 1-4
                        Address newAddress = new Address(lineParts[1], lineParts[2], lineParts[3],
                                Integer.valueOf(lineParts[4]));
                        // gym type variable is in index 5
                        GymType newGymType = GymType.valueOf(lineParts[5]);
                        // equipment variables are in index 6, split by semicolons
                        String[] equipmentList = lineParts[6].split(";");
                        Set<Equipment> newEquipment = new HashSet<>();
                        // loop through String array
                        for (String anEquipmentList : equipmentList) {
                            // create new variable from corresponding enum and add to set
                            Equipment equipment = Equipment.valueOf(anEquipmentList);
                            newEquipment.add(equipment);
                        }
                        // create new Gym variable and add to ArrayList
                        Gym newGym = new Gym(newGymType, newAddress, newEquipment);
                        gyms.add(newGym);

                        break;
                    }
                    // if the array begins with "membership"
                    case "membership": {
                        // gym type variable is in index 1
                        GymType newGymType = GymType.valueOf(lineParts[1]);
                        // start date variable is in index 2
                        Calendar newStartDate = Calendar.getInstance();
                        newStartDate.setTimeInMillis(Long.valueOf(lineParts[2]));
                        // initialize a list of members in the present membership, such as a family
                        List<Member> memberList = new ArrayList<>();
                        Membership newMembership;
                        // continue through ArrayList, which will contain member details
                        i++;
                        while (i < fileLines.size()) {
                            // String array with member attributes, split by commas
                            String[] memberParts = fileLines.get(i).split(",");
                            // index 0 should be "member"
                            if (!memberParts[0].equals("member")) {
                                i--;
                                break;
                            }
                            // name variable is in index 1
                            String newName = memberParts[1];
                            // gender variable is in index 2
                            String gen = memberParts[2];
                            Gender newGender = Gender.valueOf(gen);
                            // weight variable is in index 3
                            double newWeight = Double.valueOf(memberParts[3]);
                            // birthday variable is in index 4
                            Calendar newBirthdate = Calendar.getInstance();
                            newBirthdate.setTimeInMillis(Long.valueOf(memberParts[4]));
                            // stats variables are in indices 5-10
                            Stats newStats = new Stats();
                            // stat setters
                            newStats.setBackSquat(Integer.valueOf(memberParts[5]));
                            newStats.setBenchPress(Integer.valueOf(memberParts[6]));
                            newStats.setDeadlift(Integer.valueOf(memberParts[7]));
                            newStats.setDip(Integer.valueOf(memberParts[8]));
                            newStats.setOverheadPress(Integer.valueOf(memberParts[9]));
                            newStats.setPullUp(Integer.valueOf(memberParts[10]));

                            // create new member with above variables
                            Member newMember = new Member(newName, newBirthdate, newGender, newStats, newWeight);
                            memberList.add(newMember);
                            i++;
                        }
                        // create new Membership variable, Single or Couple or Family, and add to ArrayList
                        if (memberList.size() == 1) {
                            newMembership = new SingleMembership(newStartDate, newGymType, memberList.get(0));
                        } else if (memberList.size() == 2) {
                            newMembership = new CoupleMembership(newStartDate, newGymType, memberList.get(0),
                                    memberList.get(1));
                        } else {
                            newMembership = new FamilyMembership(newStartDate, newGymType, memberList);
                        }
                        memberships.add(newMembership);

                        // otherwise, keep going
                        break;
                    }
                    default:
                        continue;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file.");
        } catch (IOException ex) {
            System.out.println("Error reading file.");
        }

		/*Hard code of single membership
		Stats stats1 = new Stats();
		stats1.setBackSquat(305).setBenchPress(230).setDeadlift(350)
				.setDip(120).setOverheadPress(150).setPullUp(75);
		Calendar member1Birthdate = Calendar.getInstance();
		member1Birthdate.set(Calendar.YEAR, 1996);
		member1Birthdate.set(Calendar.MONTH, 6);
		member1Birthdate.set(Calendar.DAY_OF_MONTH, 16);
		Member member1 = new Member("Johnny Rocket", member1Birthdate, Gender.MALE,
				stats1, 160.6);
		Membership membership1 = new SingleMembership(Calendar.getInstance(), 
				GymType.MAIN, member1);
		memberships.add(membership1);
		
		//Hard code of couple membership example
		Stats stats2 = new Stats();
		Stats stats3 = new Stats();
		stats2.setBackSquat(280).setBenchPress(210).setDeadlift(320)
				.setDip(85).setOverheadPress(135).setPullUp(45);
		stats3.setBackSquat(175).setBenchPress(120).setDeadlift(210)
				.setDip(15).setOverheadPress(80).setPullUp(0);
		Calendar member2Birthdate = Calendar.getInstance();
		member2Birthdate.set(Calendar.YEAR, 1986);
		member2Birthdate.set(Calendar.MONTH, 1);
		member2Birthdate.set(Calendar.DAY_OF_MONTH, 19);
		Calendar member3Birthdate = Calendar.getInstance();
		member3Birthdate.set(Calendar.YEAR, 1986);
		member3Birthdate.set(Calendar.MONTH, 2);
		member3Birthdate.set(Calendar.DAY_OF_MONTH, 19);
		Member member2 = new Member("Bob Jones", member2Birthdate, Gender.MALE,
				stats2, 190.8);
		Member member3 = new Member("Jane Jones", member3Birthdate, Gender.FEMALE,
				stats3, 145.2);
		Membership membership2 = new CoupleMembership(Calendar.getInstance(), 
				GymType.EXPRESS, member2, member3);
		memberships.add(membership2);
		
		//Hard code of family membership example
		Stats stats4 = new Stats();
		Stats stats5 = new Stats();
		Stats stats6 = new Stats();
		stats4.setBackSquat(245).setBenchPress(180).setDeadlift(280)
				.setDip(30).setOverheadPress(120).setPullUp(5);
		stats5.setBackSquat(130).setBenchPress(85).setDeadlift(155)
				.setDip(0).setOverheadPress(55).setPullUp(0);
		stats6.setBackSquat(325).setBenchPress(240).setDeadlift(370)
		.setDip(130).setOverheadPress(155).setPullUp(85);
		Calendar member4Birthdate = Calendar.getInstance();
		member4Birthdate.set(Calendar.YEAR, 1975);
		member4Birthdate.set(Calendar.MONTH, 10);
		member4Birthdate.set(Calendar.DAY_OF_MONTH, 13);
		Calendar member5Birthdate = Calendar.getInstance();
		member5Birthdate.set(Calendar.YEAR, 1976);
		member5Birthdate.set(Calendar.MONTH, 4);
		member5Birthdate.set(Calendar.DAY_OF_MONTH, 14);
		Calendar member6Birthdate = Calendar.getInstance();
		member6Birthdate.set(Calendar.YEAR, 2000);
		member6Birthdate.set(Calendar.MONTH, 1);
		member6Birthdate.set(Calendar.DAY_OF_MONTH, 1);
		Member member4 = new Member("Chris Smith", member4Birthdate, Gender.MALE,
				stats4, 300.0);
		Member member5 = new Member("Jenny Smith", member5Birthdate, Gender.FEMALE,
				stats5, 138.4);
		Member member6 = new Member("Mike Smith", member6Birthdate, Gender.MALE,
				stats6, 160.0);
		List<Member> chrisFamily = new ArrayList<Member>();
		chrisFamily.add(member4);
		chrisFamily.add(member5);
		chrisFamily.add(member6);
		Membership membership3 = new FamilyMembership(Calendar.getInstance(), 
				GymType.EXPRESS, chrisFamily);
		memberships.add(membership3);
		
		//Hard code of main gym example
		Set<Equipment> gym1Equip = new HashSet<Equipment>();
		gym1Equip.add(Equipment.POOL);
		gym1Equip.add(Equipment.SAUNA);
		gym1Equip.add(Equipment.FREE_WEIGHTS);
		gym1Equip.add(Equipment.ELLIPTICALS);
		gym1Equip.add(Equipment.KETTLEBELLS);
		gym1Equip.add(Equipment.TREADMILLS);
		gym1Equip.add(Equipment.MACHINES);
		gym1Equip.add(Equipment.PUNCHING_BAGS);
		gym1Equip.add(Equipment.STRETCHING_AREA);
		Gym gym1 = new Gym(GymType.MAIN, new Address("123 Fake St.",
				"Springfield", "Maryland", 20901), gym1Equip);
		gyms.add(gym1);
		
		//Hard code of express gym example
		Set<Equipment> gym2Equip = new HashSet<Equipment>();
		gym2Equip.add(Equipment.FREE_WEIGHTS);
		gym2Equip.add(Equipment.ELLIPTICALS);
		gym2Equip.add(Equipment.KETTLEBELLS);
		gym2Equip.add(Equipment.TREADMILLS);
		gym2Equip.add(Equipment.MACHINES);
		gym2Equip.add(Equipment.STRETCHING_AREA);
		Gym gym2 = new Gym(GymType.EXPRESS, new Address("456 Fake St.",
				"Springfield", "Maryland", 20901), gym2Equip);
		gyms.add(gym2);*/

        while (true) {
            // print application and read responses
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to Ideal Fitness LLC!");
            System.out.println("Enter #: 1 Print members, 2 New membership, 3 Update member, 4 Print gyms, 5 New gym, "
                    + "6 Update gym, 7 Save");
            try {
                // read integer response
                int num = scanner.nextInt();
                // go down to one line to prepare next response
                scanner.nextLine();
                // 1 Print members
                if (num == 1) {
                    printMembers(memberships);
                    // 2 New membership
                } else if (num == 2) {
                    System.out.println("Enter membership type: 1 Single, 2 Couple, 3 Family");
                    int newSize = scanner.nextInt();
                    scanner.nextLine();
                    // obtain number of members in family
                    if (newSize == 3) {
                        System.out.println("Family size:");
                        int newFamilySize = scanner.nextInt();
                        scanner.nextLine();
                        newSize = newFamilySize;
                    }
                    // initialize member ArrayList
                    List<Member> memberList = new ArrayList<>();
                    for (int x = 0; x < newSize; x++) {
                        System.out.println("Member name:");
                        String newName = scanner.nextLine();
                        Calendar newMemberBirthdate = Calendar.getInstance();
                        System.out.println("Member birth year (YYYY):");
                        int newYear = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Member birth month number (MM):");
                        int newMonth = scanner.nextInt();
                        scanner.nextLine();
                        // adjust month since January = 1 = index 0
                        newMonth--;
                        System.out.println("Member birth day of month (DD):");
                        int newDay = scanner.nextInt();
                        scanner.nextLine();
                        newMemberBirthdate.set(Calendar.YEAR, newYear);
                        newMemberBirthdate.set(Calendar.MONTH, newMonth);
                        newMemberBirthdate.set(Calendar.DAY_OF_MONTH, newDay);
                        System.out.println("Member gender: M (Male) or F (Female)");
                        // exported method
                        Gender newGender = writeGender(scanner);
                        System.out.println("Member weight:");
                        double newWeight = scanner.nextDouble();
                        scanner.nextLine();

                        // create new member and add to ArrayList
                        Member newMember = new Member(newName, newMemberBirthdate, newGender, new Stats(), newWeight);
                        memberList.add(newMember);
                    }
                    // set gym type
                    System.out.println("Gym type: M (Main) or E (Express)");
                    String gymT = scanner.nextLine();
                    GymType newType;
                    if (gymT.equals("E")) {
                        newType = GymType.EXPRESS;
                    } else {
                        newType = GymType.MAIN;
                    }
                    // create new memberSHIP and add to ArrayList
                    Membership newMembership;
                    if (newSize == 1) {
                        newMembership = new SingleMembership(Calendar.getInstance(),
                                newType, memberList.get(0));
                    } else if (newSize == 2) {
                        newMembership = new CoupleMembership(Calendar.getInstance(),
                                newType, memberList.get(0), memberList.get(1));
                    } else {
                        newMembership = new FamilyMembership(Calendar.getInstance(),
                                GymType.EXPRESS, memberList);
                    }
                    memberships.add(newMembership);
                    System.out.println("Membership added. Please 'Update member' to add stats.");
                    // 3 Update member
                } else if (num == 3) {
                    System.out.println("Enter member's full name:");
                    String currentName = scanner.nextLine();
                    Member myMember = null;
                    Membership myMembership = null;
                    // get list of members in each membership
                    for (Membership membership : memberships) {
                        List<Member> members = membership.getMembers();
                        // get name of each member in member list
                        for (Member member : members) {
                            if (member.getName().equals(currentName)) {
                                myMember = member;
                                myMembership = membership;
                                break;
                            }
                        }
                        // stop looking if finds desired member
                        if (myMember != null) {
                            break;
                        }
                    }
                    if (myMember == null) {
                        System.out.println("Member not found.");
                    } else {
                        // print member and gym type details
                        System.out.println(myMember);
                        System.out.println(myMembership.getGymType());
                        // request further action
                        System.out.println("Enter #: 1 Update name, 2 Update gym type, 3 Update gender, "
                                + "4 Update weight, 5 Update birthdate, 6 Update stats");
                        int memberUpdate = scanner.nextInt();
                        scanner.nextLine();
                        // 1 Update name
                        if (memberUpdate == 1) {
                            System.out.println(myMember.getName());
                            System.out.println("New name:");
                            String newName = scanner.nextLine();
                            myMember.setName(newName);
                            // 2 Update gym type
                        } else if (memberUpdate == 2) {
                            System.out.println(myMembership.getGymType());
                            // exported method
                            GymType newType = writeGymType(scanner);
                            myMembership.setGymType(newType);
                            // 3 Update gender
                        } else if (memberUpdate == 3) {
                            System.out.println(myMember.getGender());
                            System.out.println("New gender: M (Male) or F (Female)");
                            // exported method
                            Gender newGender = writeGender(scanner);
                            myMember.setGender(newGender);
                            // 4 Update weight
                        } else if (memberUpdate == 4) {
                            System.out.println(myMember.getWeight());
                            System.out.println("New weight:");
                            double newWeight = scanner.nextDouble();
                            scanner.nextLine();
                            myMember.setWeight(newWeight);
                            // 5 Update birthdate
                        } else if (memberUpdate == 5) {
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                            String birthdateFormat = format1.format(myMember.getBirthdate().getTime());
                            System.out.println(birthdateFormat);
                            Calendar newMemberBirthdate = Calendar.getInstance();
                            System.out.println("New birth year (YYYY):");
                            int newYear = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("New birth month (MM):");
                            int newMonth = scanner.nextInt();
                            scanner.nextLine();
                            // January = 00, not 01
                            newMonth--;
                            System.out.println("New birth day of month (DD):");
                            int newDay = scanner.nextInt();
                            scanner.nextLine();
                            newMemberBirthdate.set(Calendar.YEAR, newYear);
                            newMemberBirthdate.set(Calendar.MONTH, newMonth);
                            newMemberBirthdate.set(Calendar.DAY_OF_MONTH, newDay);
                            myMember.setBirthdate(newMemberBirthdate);
                            // 6 Update stats
                        } else if (memberUpdate == 6) {
                            System.out.println(myMember.getStats());
                            System.out.println("Back squat 1 rep max weight:");
                            int newSquat = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setBackSquat(newSquat);
                            System.out.println("Bench press 1 rep max weight:");
                            int newBench = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setBenchPress(newBench);
                            System.out.println("Deadlift 1 rep max weight:");
                            int newDeadlift = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setDeadlift(newDeadlift);
                            System.out.println("Dip 1 rep max weight:");
                            int newDip = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setDip(newDip);
                            System.out.println("Overhead press 1 rep max weight:");
                            int newOverheadPress = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setOverheadPress(newOverheadPress);
                            System.out.println("Pull-up 1 rep max weight:");
                            int newPullUp = scanner.nextInt();
                            scanner.nextLine();
                            myMember.getStats().setPullUp(newPullUp);
                        }
                        System.out.println("Member updated.");
                    }
                    // 4 Print gyms
                } else if (num == 4) {
                    printGyms(gyms);
                    // 5 New gym
                } else if (num == 5) {
                    // exported method
                    GymType newType = writeGymType(scanner);
                    System.out.println("Gym street address:");
                    String newStreetAddress = scanner.nextLine();
                    System.out.println("Gym city:");
                    String newCity = scanner.nextLine();
                    System.out.println("Gym state:");
                    String newState = scanner.nextLine();
                    System.out.println("Gym zip code:");
                    int newZipcode = scanner.nextInt();
                    scanner.nextLine();
                    Gym newGym = new Gym(newType, new Address(newStreetAddress, newCity, newState, newZipcode),
                            new HashSet<>());
                    gyms.add(newGym);
                    System.out.println("Gym added. Please 'Update gym' to add equipment.");
                    // 6 Update gym
                } else if (num == 6) {
                    System.out.println("Enter gym street address:");
                    String oldGym = scanner.nextLine();
                    // loop through list of Gyms to find matching address
                    Gym myGym = null;
                    for (Gym gym : gyms) {
                        if (gym.getAddress().streetAddress.equals(oldGym)) {
                            myGym = gym;
                            break;
                        }
                    }
                    if (myGym == null) {
                        System.out.println("Gym not found.");
                    } else {
                        System.out.println(myGym);
                        System.out.println("Enter #: 1 Update gym type, 2 Update address, 3 Add equipment, 4 Remove"
                                + " equipment");
                        int gymUp = scanner.nextInt();
                        scanner.nextLine();
                        // 1 Update gym type
                        if (gymUp == 1) {
                            System.out.println(myGym.getGymType());
                            // exported method
                            GymType newType = writeGymType(scanner);
                            myGym.setGymType(newType);
                        // 2 Update address
                        } else if (gymUp == 2) {
                            System.out.println(myGym.getAddress());
                            System.out.println("New street address:");
                            String newStreetAddress = scanner.nextLine();
                            System.out.println("New city:");
                            String newCity = scanner.nextLine();
                            System.out.println("New state:");
                            String newState = scanner.nextLine();
                            System.out.println("New zip code:");
                            int newZipcode = scanner.nextInt();
                            scanner.nextLine();
                            Address newAddress = new Address(newStreetAddress, newCity, newState, newZipcode);
                            myGym.setAddress(newAddress);
                        // 3 Add equipment
                        } else if (gymUp == 3) {
                            System.out.println(myGym.getEquipment());
                            System.out.println("Enter # to add: 1 Free weights, 2 Treadmills, 3 Machines, 4 Ellipticals,"
                                    + " 5 Kettlebells, 6 Punching bags, 7 Stretching area, 8 Pool, 9 Sauna, 0 Stop");
                            while (true) {
                                int equipmentNum = scanner.nextInt();
                                scanner.nextLine();
                                if (equipmentNum == 0) {
                                    break;
                                } else if (equipmentNum == 1) {
                                    myGym.addEquipment(Equipment.FREE_WEIGHTS);
                                    System.out.println("Added free weights.");
                                } else if (equipmentNum == 2) {
                                    myGym.addEquipment(Equipment.TREADMILLS);
                                    System.out.println("Added treadmills.");
                                } else if (equipmentNum == 3) {
                                    myGym.addEquipment(Equipment.MACHINES);
                                    System.out.println("Added machines.");
                                } else if (equipmentNum == 4) {
                                    myGym.addEquipment(Equipment.ELLIPTICALS);
                                    System.out.println("Added ellipticals.");
                                } else if (equipmentNum == 5) {
                                    myGym.addEquipment(Equipment.KETTLEBELLS);
                                    System.out.println("Added kettlebells.");
                                } else if (equipmentNum == 6) {
                                    myGym.addEquipment(Equipment.PUNCHING_BAGS);
                                    System.out.println("Added punching bags.");
                                } else if (equipmentNum == 7) {
                                    myGym.addEquipment(Equipment.STRETCHING_AREA);
                                    System.out.println("Added stretching area.");
                                } else if (equipmentNum == 8) {
                                    myGym.addEquipment(Equipment.POOL);
                                    System.out.println("Added pool.");
                                } else if (equipmentNum == 9) {
                                    myGym.addEquipment(Equipment.SAUNA);
                                    System.out.println("Added sauna.");
                                } else {
                                    System.out.println("Please enter a valid number.");
                                }
                            }
                        // opposite of above, removeEquipment instead of addEquipment
                        } else if (gymUp == 4) {
                            System.out.println(myGym.getEquipment());
                            System.out.println("Enter # to remove: 1 Free weights, 2 Treadmills, 3 Machines, "
                                    + "4 Ellipticals, 5 Kettlebells, 6 Punching bags, 7 Stretching area, 8 Pool, 9 Sauna,"
                                    + " 0 Stop");
                            while (true) {
                                int equipmentNum = scanner.nextInt();
                                scanner.nextLine();
                                if (equipmentNum == 0) {
                                    break;
                                } else if (equipmentNum == 1) {
                                    myGym.removeEquipment(Equipment.FREE_WEIGHTS);
                                    System.out.println("Removed free weights.");
                                } else if (equipmentNum == 2) {
                                    myGym.removeEquipment(Equipment.TREADMILLS);
                                    System.out.println("Removed treadmills.");
                                } else if (equipmentNum == 3) {
                                    myGym.removeEquipment(Equipment.MACHINES);
                                    System.out.println("Removed machines.");
                                } else if (equipmentNum == 4) {
                                    myGym.removeEquipment(Equipment.ELLIPTICALS);
                                    System.out.println("Removed ellipticals.");
                                } else if (equipmentNum == 5) {
                                    myGym.removeEquipment(Equipment.KETTLEBELLS);
                                    System.out.println("Removed kettlebells.");
                                } else if (equipmentNum == 6) {
                                    myGym.removeEquipment(Equipment.PUNCHING_BAGS);
                                    System.out.println("Removed punching bags.");
                                } else if (equipmentNum == 7) {
                                    myGym.removeEquipment(Equipment.STRETCHING_AREA);
                                    System.out.println("Removed stretching area.");
                                } else if (equipmentNum == 8) {
                                    myGym.removeEquipment(Equipment.POOL);
                                    System.out.println("Removed pool.");
                                } else if (equipmentNum == 9) {
                                    myGym.removeEquipment(Equipment.SAUNA);
                                    System.out.println("Removed sauna.");
                                } else {
                                    System.out.println("Please enter a valid number.");
                                }
                            }
                        } else {
                            System.out.println("Please enter a valid number.");
                        }
                        System.out.println("Gym updated.");
                    }
                    // 7 Save
                } else if (num == 7) {
                    String fileName = "idealfitnessdata.csv";
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        // append data to StringBuilder object for CSV (comma-separated values) file
                        StringBuilder csvOutput = new StringBuilder();
                        for (Gym gym : gyms) {
                            csvOutput.append("gym,")
                                    .append(gym.getAddress().streetAddress).append(",")
                                    .append(gym.getAddress().city).append(",")
                                    .append(gym.getAddress().state).append(",")
                                    .append(gym.getAddress().zipcode).append(",")
                                    .append(gym.getGymType()).append(",");
                            // interates, or loops, through equipment to append
                            Iterator<Equipment> equipmentIterator = gym.getEquipment().iterator();
                            while (equipmentIterator.hasNext()) {
                                Equipment equipment = equipmentIterator.next();
                                csvOutput.append(equipment);
                                if (equipmentIterator.hasNext()) {
                                    csvOutput.append(";");
                                }
                            }
                            csvOutput.append("\n");
                        }
                        for (Membership membership : memberships) {
                            csvOutput.append("membership,")
                                    .append(membership.getGymType()).append(",")
                                    .append(membership.getStartDate().getTimeInMillis()).append("\n");
                            List<Member> members = membership.getMembers();
                            for (int j = 0; j < members.size(); j++) {
                                Member member = members.get(j);
                                csvOutput.append("member,")
                                        .append(member.getName()).append(",")
                                        .append(member.getGender()).append(",")
                                        .append(member.getWeight()).append(",")
                                        .append(member.getBirthdate().getTimeInMillis()).append(",")
                                        .append(member.getStats().getBackSquat()).append(",")
                                        .append(member.getStats().getBenchPress()).append(",")
                                        .append(member.getStats().getDeadlift()).append(",")
                                        .append(member.getStats().getDip()).append(",")
                                        .append(member.getStats().getOverheadPress()).append(",")
                                        .append(member.getStats().getPullUp()).append("\n");
                            }
                        }
                        bufferedWriter.write(csvOutput.toString());
                        bufferedWriter.close();
                        System.out.println("File saved.");
                    } catch (IOException ex) {
                        System.out.println("Error writing to file '" + fileName + "'");
                    }
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Sets the gym type to Express or Main
     * @param scanner
     * @return
     */
    private static GymType writeGymType(Scanner scanner) {
        System.out.println("New gym type: M (Main) or E (Express)");
        String gymT = scanner.nextLine();
        GymType newType;
        if (gymT.equals("E")) {
            newType = GymType.EXPRESS;
        } else {
            newType = GymType.MAIN;
        }
        return newType;
    }

    /**
     * Sets to the gender to Male or Female
     * @param scanner
     * @return
     */
    private static Gender writeGender(Scanner scanner) {
        Gender newGender;
        label:
        while (true) {
            String gen = scanner.nextLine();
            switch (gen) {
                case "M":
                    newGender = Gender.MALE;
                    break label;
                case "F":
                    newGender = Gender.FEMALE;
                    break label;
                default:
                    System.out.println("Please enter M or F.");
                    break;
            }
        }
        return newGender;
    }

    /**
     * Loop through list of Members in list of Memberships and print toString
     * @param memberships
     */
    private static void printMembers(List<Membership> memberships) {
        for (Membership membership : memberships) {
            List<Member> members = membership.getMembers();
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }

    /**
     * Loop through list of Gyms and print toString
     * @param gyms
     */
    private static void printGyms(List<Gym> gyms) {
        for (Gym gym : gyms) {
            System.out.println(gym);
        }
    }
}
