import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {
    @Test
    void exampleStatement() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new Play("Hamlet", Play.Type.trajedy));
        plays.put("as-like",  new Play("As You Like It", Play.Type.comedy));
        plays.put("othello",  new Play("Othello", Play.Type.trajedy));
        //Customer customer = new Customer("BigCo", 1234, 267);
        Invoice invoice = new Invoice(new Customer("BigCo", 1234, 100), List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice,plays);

        verify(result);
    }
    // @Test
    // void exampleStatementCreditLess150() {
    //     HashMap<String, Play> plays = new HashMap<>();
    //     plays.put("hamlet",  new Play("Hamlet", Play.Type.trajedy));
    //     plays.put("as-like",  new Play("As You Like It", Play.Type.comedy));
    //     plays.put("othello",  new Play("Othello", Play.Type.trajedy));
    //     Customer customer = new Customer("BigCo", 1234, 67);
    //     Invoice invoice = new Invoice(customer, List.of(
    //             new Performance("hamlet", 55),
    //             new Performance("as-like", 35),
    //             new Performance("othello", 40)));

    //     StatementPrinter statementPrinter = new StatementPrinter();
    //     var result = statementPrinter.print(invoice,customer,plays);

    //     verify(result);
    // }
    /*@Test
    void statementWithNewPlayTypes() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("henry-v",  new Play("Henry V", "history"));
        plays.put("as-like",  new Play("As You Like It", "pastoral"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }*/


}
