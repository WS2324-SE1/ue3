package org.hbrs.se1.ws23;

import org.hbrs.se1.ws23.container.Container;
import org.hbrs.se1.ws23.container.ContainerException;
import org.hbrs.se1.ws23.member.ConcretMember;
import org.hbrs.se1.ws23.member.MemberView;

public class Client
{
    public void display(String[] args)
    {
        var container = Container.getInstance();

        try
        {
            container.addMember(new ConcretMember(1));
            container.addMember(new ConcretMember(2));
            container.addMember(new ConcretMember(3));
        }
        catch (ContainerException e)
        {
            System.out.println("Fehler beim Hinzufügen eines Members: " + e.getMessage());
        }

        var member_list = container.getCurrentList();

        MemberView.dump(member_list);
    }
}
