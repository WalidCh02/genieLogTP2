import java.util.*;

public class Invoice {

  public String customer;
  public List<Performance> performances;

  public Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }

  public String getCustomer() {
    return this.customer;
  }

  public List<Performance> getPerformance() {
    return this.performances;
  }
  
}
