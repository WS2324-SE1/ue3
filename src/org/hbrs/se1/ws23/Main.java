package org.hbrs.se1.ws23;

import org.hbrs.se1.ws23.container.Container;
import org.hbrs.se1.ws23.member.ConcretMember;
import org.hbrs.se1.ws23.persistence.PersistenceStrategyStream;

public class Main
{
    public static void main(String[] args)
    {
        Container.getInstance().setPersistenceStrategy(new PersistenceStrategyStream<ConcretMember>());

        var client = new Client();
        client.display(args);
    }
}
