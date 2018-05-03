package com.blogspot.syk3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Inheritance from Membership
 */
public class SingleMembership extends Membership {
    private Member member;

    // Constructor
    public SingleMembership(Calendar startDate, GymType gymType, Member member) {
        // calls super class with these parameters
        super(startDate, gymType);
        this.member = member;
    }

    // create and return list of members
    @Override
    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        members.add(member);
        return members;
    }
}
