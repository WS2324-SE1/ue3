
package org.hbrs.se1.ws23.uebung2;

import org.hbrs.se1.ws23.container.Container;
import org.hbrs.se1.ws23.container.ContainerException;
import org.hbrs.se1.ws23.member.ConcretMember;
import org.hbrs.se1.ws23.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainerTest {
    private Container container = null;

    @BeforeEach
    void setUp() {
        // Den Container anlegen
        container = Container.getInstance(true);
    }

    @Test
    void addAndDeleteMember() {
        // Test-Objekte anlegen
        Member r1 = new ConcretMember(12);
        Member r2 = new ConcretMember(32);

        // Testfall 1 - Check auf leeren Container
        assertEquals(0, container.size(),
                "Testfall 1 - Pruefung auf leeren Container");

        // Vorbereitung für Testfall 2
        try {
            container.addMember(r1);
        } catch (ContainerException e) {
            e.printStackTrace();
        }

        // Testfall 2 - Check, ob ein Objekt hinzugefuegt wurde (Size = Zustand = 1)
        assertEquals(1, container.size(),
                "Testfall 2 - Pruefung auf Zustand 1");

        // Vorbereitung für Testfall 3
        try {
            container.addMember(r2);
        } catch (ContainerException e) {
            e.printStackTrace();
        }

        // Testfall 3 - Check, ob ein Objekt hinzugefuegt wurde (Size = Zustand = 1)
        assertEquals(2, container.size(),
                "Testfall 3 - Pruefung auf Zustand 2");

        // Testfälle 4 - 8 - Kein Zustandwechsel erlaubt, Zustand bleibt 2!
        assertThrows(ContainerException.class, () -> {
            container.addMember(r2); // Schon enthalten!
        });
        assertEquals(2, container.size(), "Testfall 4 - Pruefung auf Zustand 2");

        assertThrows(ContainerException.class, () -> {
            container.addMember(null); // Null kann nicht angenommen werden!
        });
        assertEquals(2, container.size(), "Testfall 5 - Pruefung auf Zustand 2");

        String ergebnis = container.deleteMember(999); // ID nicht vorhanden!
        assertEquals(2, container.size(), "Testfall 6 - Pruefung auf Zustand 2");
        // Zudem ok zu testen:
        assertEquals( "Member nicht enthalten - ERROR" , ergebnis );

        container.size(); // Size darf keinen Zustandswechsel auslösen!
        assertEquals(2, container.size(), "Testfall 7 - Pruefung auf Zustand 2");


        // Testfälle 9 - 10. Zurück auf Zustand 0
        container.deleteMember(12);
        assertEquals(1, container.size(), "Testfall 9 - Pruefung auf Zustand 1");

        container.deleteMember(32);
        assertEquals(0, container.size(), "Testfall 9 - Pruefung auf Zustand 1");
    }

    @Test
    public void testAufNullVerschiedeneWege() {
            // Test auf NULL - der "altbekannte" Weg
            try {
                container.addMember(null);
            } catch (ContainerException e) {
                assertEquals( "NULL-Werte dürfen nicht aufgenommen werden!" ,
                        e.getMessage() );
            }
            assertEquals(0 , container.size() ) ;

            // Test auf NULL - der "moderne" Weg
            Exception e = assertThrows( ContainerException.class, () -> { container.addMember(null); } );
            assertEquals(0 , container.size() ) ;
        }


}