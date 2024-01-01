public class Diskon {
    double persentaseDiskon;

    public Diskon(double persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }

    public double hitungDiskon(double totalHarga) {
        return (persentaseDiskon / 100) * totalHarga;
    }
}
