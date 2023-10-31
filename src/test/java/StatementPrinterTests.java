import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {

        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet", new Play("Hamlet", Play.Type.trajedy));
        plays.put("as-like", new Play("As You Like It", Play.Type.comedy));
        plays.put("othello", new Play("Othello", Play.Type.trajedy));
        Customer BC = new Customer("BigCo", 122, 200);
        Invoice invoice = new Invoice(BC, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
        String htmlContent = statementPrinter.printToHTML(invoice, plays);
        statementPrinter.saveHTMLToFile(htmlContent, "output.html");

    }

}