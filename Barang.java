public class Barang {
    String jenis;
    double harga;
    int jumlah;

    public Barang(String jenis, double harga, int jumlah) {
        this.jenis = jenis;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    double hitungTotal() {
        return harga * jumlah;
    }

    // Method ini akan dioverride di kelas turunan jika diskon tersedia untuk jenis barang tertentu
    public double hitungTotalSetelahDiskon() {
        return hitungTotal();
    }
}
