public class Customer {

    public String Name;
    public int ID;
    public float StoreCredits;

    public Customer(String Name, int ID, float StoreCredits) {
        this.Name = Name;
        this.ID = ID;
        this.StoreCredits = StoreCredits;
    }

    public float getStoreCredits() {
        return StoreCredits;
    }

    public void setStoreCredits(float s) {
        this.StoreCredits = s;
    }
}