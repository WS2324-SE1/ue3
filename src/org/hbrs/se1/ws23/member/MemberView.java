package org.hbrs.se1.ws23.member;

import java.util.List;

public class MemberView
{
    static public void dump(List<Member> members)
    {
        System.out.println("MemberView: Dump der Member-Liste");
        for (Member member : members)
        {
            System.out.println(member.toString());
        }
    }


}
