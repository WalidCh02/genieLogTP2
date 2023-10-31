public class Customer {
    
    public String Name;
    public int ID;
    public float StoreCredits;
    
    public Customer(String name,int ID,float credits)
    {
        this.Name=name;
        this.ID=ID;
        this.StoreCredits=credits;
    }

    public String getName() {
        return Name;
    }

    public float getStoreCredits() {
        return StoreCredits;
    } 

    public void setStoreCredits(float storeCredits) {
        this.StoreCredits=storeCredits;
    }
}
