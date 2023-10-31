import java.text.NumberFormat;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class StatementPrinter {

    // a boolean used to check if the customer is eligible for a sale
    Boolean sale = false;

    public String print(Invoice invoice, HashMap<String, Play> plays) {

        double totalAmount = 0;
        int volumeCredits = 0;

        // Create a StringBuffer to store the statement text.
        StringBuffer sbResult = new StringBuffer();
        // Add a header with the customer's name.
        sbResult.append(String.format("Statement for %s\n", invoice.customer.Name));

        // Create a NumberFormat instance for currency formatting.
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Iterate through each performance in the invoice.
        for (Performance perf : invoice.performances) {
            // Get the play details for the current performance.
            Play play = plays.get(perf.playID);
            double thisAmount = 0;

            // Utiliser une méthode pour vérifier le type de pièce
            thisAmount = calculateAmount(play, perf);

            // Calculate and add volume credits.
            volumeCredits += updateCredit(perf, play);

            // Add a line to the statement for this performance.
            sbResult.append(
                    String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount),
                            perf.audience));
            // Add this performance's amount to the total.
            totalAmount += thisAmount;
        }

        // Add the total amount owed.
        sbResult.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount)));
        // Add the total earned credits.
        sbResult.append(String.format("You earned %s credits\n", volumeCredits));

        // Applying the sale if custumor's credit is greater than 150.
        if (invoice.customer.getStoreCredits() > 150 && totalAmount > 0) {
            totalAmount -= 15.00;
            invoice.customer.setStoreCredits(invoice.customer.getStoreCredits() - 150);
            sale = true;
        }
        // Return the statement as a string.
        return sbResult.toString();
    }

    // Method to calculate the amount based on the type of play
    private double calculateAmount(Play play, Performance perf) {
        double thisAmount = 0.0;
        // Play.Type type = Play.Type.trajedy;
        switch (play.type) {
            case trajedy:
                thisAmount = 400.00;
                if (perf.audience > 30) {
                    thisAmount += 10.00 * (perf.audience - 30);
                }
                break;
            case comedy:
                thisAmount = 300.00;
                if (perf.audience > 20) {
                    thisAmount += 100.00 + 5.00 * (perf.audience - 20);
                }
                thisAmount += 3.00 * perf.audience;
                break;
            default:
                throw new Error("unknown type: " + play.type); // Handle unknown play types.
        }
        return thisAmount;
    }

    public float updateCredit(Performance perf, Play play) {
        int dif = Math.max(perf.audience - 30, 0);
        switch (play.type) {
            case comedy:
                // Add extra credit for every ten comedy attendees.
                dif += Math.floor(perf.audience / 5);
                break;
            default:
                // do nothing
        }
        return dif;
    }

    public String printToHTML(Invoice invoice, HashMap<String, Play> plays) {

        double totalAmount = 0;
        StringBuffer sb = new StringBuffer();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Invoice</title>");
        sb.append("<style>");
        sb.append("table, th, td { border: 2px solid black; }");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(String.format("<h1>Invoice for %s</h1>\n", invoice.customer.Name));
        sb.append("<table>\n");
        sb.append("<tr><th>Piece</th><th>Seats Sold</th><th>Price</th></tr>");
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = 0;
            thisAmount = calculateAmount(play, perf);
            sb.append(String.format("  <tr><td>%s</td><td>%s</td><td>%s</td></tr>\n", play.name, perf.audience,
                    currencyFormatter.format(thisAmount)));
            totalAmount += thisAmount;
        }

        sb.append("</table>\n");
        sb.append("<table>\n");
        sb.append(String.format(
                "<tr><td align=\"right\" style=\"border: 1px solid black\" colspan=\"2\">Total owed: </th><td style=\"border: 1px solid black\">%s</td></tr>",
                currencyFormatter.format(totalAmount)));
        sb.append(String.format(
                "<tr><td align=\"right\" style=\"border: 1px solid black\" colspan=\"2\">Fidelity Points Earned: </th><td style=\"border: 1px solid black\">%s</td></tr>",
                invoice.customer.getStoreCredits()));
        if (sale) {
            sb.append(String.format(
                    "<tr><td align=\"right\" style=\"border: 1px solid black\" colspan=\"2\">Total owed after sale: </th><td style=\"border: 1px solid black\">%s</td></tr>",
                    currencyFormatter.format(totalAmount - 15.00)));
        }
        sb.append("</table>\n");
        sb.append("<p>Payement is required under 30 days. We can break your knees if you don't do so.</p>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public void saveHTMLToFile(String htmlContent, String output) {
        try {
            // Create a FileWriter and write the HTML content to the specified file.
            FileWriter fileWriter = new FileWriter(output);
            fileWriter.write(htmlContent);
            fileWriter.close();
            System.out.println("HTML content has been saved to " + output);
        } catch (IOException e) {
            System.err.println("Error saving HTML content to file: " + e.getMessage());
        }
    }
}
