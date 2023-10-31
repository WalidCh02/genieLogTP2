import java.util.*;

public class Invoice {

  public Customer customer;
  public List<Performance> performances;

  public Invoice(Customer customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }

  public List<Performance> getPerformance() {
    return this.performances;
  }

}
