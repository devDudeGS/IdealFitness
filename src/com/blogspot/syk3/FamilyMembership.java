package com.blogspot.syk3;

import java.util.Calendar;
import java.util.List;

public class FamilyMembership extends Membership {
    private List<Member> members;

    // Constructor
    public FamilyMembership(Calendar startDate, GymType gymType, List<Member> members) {
        // calls super class with these parameters
        super(startDate, gymType);
        this.members = members;
    }

    // return list of members
    @Override
    public List<Member> getMembers() {
        return members;
    }

}
