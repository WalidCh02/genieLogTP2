public class Play {

  public String name;
  public Type type;

  public static enum Type {
    trajedy,
    comedy,
  }

  public Play(String name, Type type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return this.name;
  }

  public Type getType() {
    return this.type;
  }
}