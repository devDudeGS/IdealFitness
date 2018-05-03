package com.blogspot.syk3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CoupleMembership extends Membership {
    private Member memberA;
    private Member memberB;

    // Constructor
    public CoupleMembership(Calendar startDate, GymType gymType, Member memberA, Member memberB) {
        // calls super class with these parameters
        super(startDate, gymType);
        this.memberA = memberA;
        this.memberB = memberB;
    }

    // create and return list of members
    @Override
    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        members.add(memberA);
        members.add(memberB);
        return members;
    }
}
