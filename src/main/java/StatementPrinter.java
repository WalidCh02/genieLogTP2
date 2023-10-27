/*import java.text.NumberFormat;
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
      // Initialize the amount for this performance to 0.
      int thisAmount = 0; 

      // Calculate the amount based on the type of play.
      switch (play.type) {
        case "tragedy":
          thisAmount = 40000;
          if (perf.audience > 30) {
            thisAmount += 1000 * (perf.audience - 30);
          }
          break;
        case "comedy":
          thisAmount = 30000;
          if (perf.audience > 20) {
            thisAmount += 10000 + 500 * (perf.audience - 20);
          }
          thisAmount += 300 * perf.audience;
          break;
        default:
          throw new Error("unknown type: ${play.type}"); // Handle unknown play types.
      }

      // Calculate and add volume credits.
      volumeCredits += Math.max(perf.audience - 30, 0);
      // Add extra credit for every ten comedy attendees.
      if ("comedy".equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // Add a line to the statement for this performance.
      sbResult.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), perf.audience));
      // Add this performance's amount to the total.
      totalAmount += thisAmount; 
    }
    
    // Add the total amount owed.
    sbResult.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount / 100))); 
     // Add the total earned credits.
    sbResult.append(String.format("You earned %s credits\n", volumeCredits));
    
    // Return the statement as a string.
    return sbResult.toString(); 
  }
}
*/
import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public static final String TRAGEDY = "tragedy";
  public static final String COMEDY = "comedy";

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
      // Initialize the amount for this performance to 0.
      int thisAmount = 0; 

      // Utiliser une méthode pour vérifier le type de pièce
      thisAmount = calculateAmount(play, perf);

      // Calculate and add volume credits.
      volumeCredits += Math.max(perf.audience - 30, 0);
      // Add extra credit for every ten comedy attendees.
      if (COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // Add a line to the statement for this performance.
      sbResult.append(String.format("  %s: %s (%s seats)\n", play.name, currencyFormatter.format(thisAmount / 100), perf.audience));
      // Add this performance's amount to the total.
      totalAmount += thisAmount; 
    }
    
    // Add the total amount owed.
    sbResult.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount / 100))); 
     // Add the total earned credits.
    sbResult.append(String.format("You earned %s credits\n", volumeCredits));
    
    // Return the statement as a string.
    return sbResult.toString(); 
  }

  // Méthode pour calculer le montant en fonction du type de pièce
  private int calculateAmount(Play play, Performance perf) {
    int thisAmount = 0;
    
    switch (play.type) {
      case TRAGEDY:
        thisAmount = 40000;
        if (perf.audience > 30) {
          thisAmount += 1000 * (perf.audience - 30);
        }
        break;
      case COMEDY:
        thisAmount = 30000;
        if (perf.audience > 20) {
          thisAmount += 10000 + 500 * (perf.audience - 20);
        }
        thisAmount += 300 * perf.audience;
        break;
      default:
        throw new Error("unknown type: " + play.type); // Handle unknown play types.
    }
    return thisAmount;
  }
}
