package org.hbrs.se1.ws23;

/*CR4: Entwickeln sie einen JUnit5-Testfall mit mindestens 4 aussagekräftigen Test-
        Methoden, um ihre Klasse Container hinreichend zu testen. Folgende Testfälle sollten
        dabei zumindest getestet werden (jeder Testfall eine Test-Methode):
        • Keine Strategie von außen gesetzt („Test auf NULL“)
        • Verwendung der Strategie „PersistenceStrategyMongoDB“
        • Fehlerhafte Location des Files, in dem Streams ihre Objekte abspeichern (Tipp:
        FileStreams mögen keine Directories ;-))
        • „Round-Trip-Test“: Objekt hinzufügen, Liste persistent abspeichern, Objekt aus
        Container löschen und Liste wieder einladen. Nach jedem Schritt den Zustand des
        Containers testen!*/

import org.hbrs.se1.ws23.container.Container;
import org.hbrs.se1.ws23.container.ContainerException;
import org.hbrs.se1.ws23.member.ConcretMember;
import org.hbrs.se1.ws23.persistence.PersistenceException;
import org.hbrs.se1.ws23.persistence.PersistenceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContainerTest
{
    private Container container = null;

    @BeforeEach
    void setUp() {
        container = Container.getInstance(true);
    }

    @Test
    void testNullStrategy() {
        container.setPersistenceStrategy(null);

        try
        {
            container.addMember(new ConcretMember(1));
        } catch (ContainerException e)
        {
            throw new RuntimeException(e);
        }

        assertThrows(PersistenceException.class, () -> {
            container.store();
        });

    }

}
