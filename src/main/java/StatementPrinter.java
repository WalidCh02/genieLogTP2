import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public String print(Invoice invoice, HashMap<String, Play> plays) {
    
    int totalAmount = 0;       
    int volumeCredits = 0;

    // Create a StringBuffer to store the statement text.
    StringBuffer sbResult = new StringBuffer();
    // Add a header with the customer's name.
    sbResult.append(String.format("Statement for %s\n", invoice.customer)); 

    // Create a NumberFormat instance for currency formatting.
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    // Iterate through each performance in the invoice.
    for (Performance perf : invoice.performances) {
      // Get the play details for the current performance.
      Play play = plays.get(perf.playID);  
      double thisAmount = 0.0;

      // Utiliser une méthode pour vérifier le type de pièce
      thisAmount = calculateAmount(play, perf);

      // Calculate and add volume credits.
      volumeCredits += Math.max(perf.audience - 30, 0);
      // Add extra credit for every ten comedy attendees.
      if (Play.Type.comedy.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // Add a line to the statement for this performance.
      sbResult.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount), perf.audience));
      // Add this performance's amount to the total.
      totalAmount += thisAmount; 
    }
    
    // Add the total amount owed.
    sbResult.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount))); 
     // Add the total earned credits.
    sbResult.append(String.format("You earned %s credits\n", volumeCredits));
    
    // Return the statement as a string.
    return sbResult.toString(); 
  }

  //Method to calculate the amount based on the type of play
  private double calculateAmount(Play play, Performance perf) {
    double thisAmount = 0.0;
    //Play.Type type = Play.Type.trajedy; 
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

}
