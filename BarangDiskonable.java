public class BarangDiskonable extends Barang {
    Diskon diskon;

    public BarangDiskonable(String jenis, double harga, int jumlah, Diskon diskon) {
        super(jenis, harga, jumlah);
        this.diskon = diskon;
    }

    @Override
    public double hitungTotalSetelahDiskon() {
        double totalHarga = super.hitungTotal();
        double jumlahDiskon = diskon.hitungDiskon(totalHarga);
        return totalHarga - jumlahDiskon;
    }
}
