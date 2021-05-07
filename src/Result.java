/*
    Dieses Projekt ist in Zusammenarbeit mit
    Connor Breuer, Claas Diedrich, Lasse Knauff
    entstanden.
 */
public class Result {
    private String name;
    private String data;

    public Result(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }
}
