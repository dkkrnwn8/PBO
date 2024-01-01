import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;

public class AplikasiPenjualan {
    private static ArrayList<Barang> daftarBarang = new ArrayList<>();

    public static void main(String[] args) {
        tampilkanMenuAwal();
    }

    private static void tampilkanMenuAwal() {
        String[] options = {"Tambah Barang", "Tampilkan Daftar Barang", "Transaksi Pembayaran", "Keluar"};
        int response = JOptionPane.showOptionDialog(
                null,
                "=== SELAMAT DATANG DI TOKO PERHIASAN JOSES ===",
                "Aplikasi Penjualan Perhiasan",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        switch (response) {
            case 0:
                tambahBarang();
                break;
            case 1:
                tampilkanDaftarBarang();
                break;
            case 2:
                prosesPembelian();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Terima Kasih Telah Berbelanja di Toko Kami!");
                System.exit(0);
                break;
            default:
                tampilkanMenuAwal();
        }
    }

    private static void tambahBarang() {
        String[] jenisBarangOptions = {"Anting", "Cincin", "Gelang", "Kalung"};

        JOptionPane.showMessageDialog(
                null,
                "=== TAMBAH BARANG ===",
                "Tambah Barang",
                JOptionPane.PLAIN_MESSAGE);

        String jenisBarang = (String) JOptionPane.showInputDialog(
                null,
                "Pilih jenis barang:",
                "Tambah Barang",
                JOptionPane.PLAIN_MESSAGE,
                null,
                jenisBarangOptions,
                jenisBarangOptions[0]);

        double hargaBarang = 0;
        try {
            String hargaBarangString = JOptionPane.showInputDialog(null, "Masukkan harga barang: Rp");
            hargaBarang = Double.parseDouble(hargaBarangString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Masukkan harga yang valid!");
            tambahBarang();
            return;
        }

        int jumlahBarang = 0;
        try {
            String jumlahBarangString = JOptionPane.showInputDialog(null, "Masukkan jumlah barang: ");
            jumlahBarang = Integer.parseInt(jumlahBarangString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Masukkan jumlah yang valid!");
            tambahBarang();
            return;
        }

        Barang barang = new Barang(jenisBarang, hargaBarang, jumlahBarang);
        daftarBarang.add(barang);

        JOptionPane.showMessageDialog(null, "Barang berhasil ditambahkan!");
        tampilkanMenuAwal();
    }

    // Metode untuk menghitung total pembelian
    private static double totalPembelian() {
        double totalPembelian = 0;
        for (Barang barang : daftarBarang) {
            totalPembelian += barang.hitungTotal();
        }
        return totalPembelian;
    }

    private static void tampilkanDaftarBarang() {
        StringBuilder daftarBarangString = new StringBuilder("=== DAFTAR BARANG ===\n");

        double totalPembelian = 0;

        for (int i = 0; i < daftarBarang.size(); i++) {
            Barang barang = daftarBarang.get(i);
            daftarBarangString.append((i + 1)).append(". ").append(barang.jenis).append(" - Harga: ").append(formatRupiah(barang.harga)).append(" - Jumlah: ").append(barang.jumlah).append("\n");

            // Menambahkan total pembelian
            totalPembelian += barang.hitungTotal();
        }

        // Menambahkan informasi tentang total pembelian
        daftarBarangString.append("\nTotal Pembelian: ").append(formatRupiah(totalPembelian));

        // Menggunakan JTextArea untuk tampilan yang lebih baik
        JTextArea textArea = new JTextArea(daftarBarangString.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);

        JOptionPane.showMessageDialog(
                null,
                panel,
                "Daftar Barang",
                JOptionPane.PLAIN_MESSAGE);

        tampilkanMenuAwal();
    }

    private static void prosesPembelian() {
        StringBuilder pembelianString = new StringBuilder("=== TRANSAKSI PEMBAYARAN ===\n");
        double totalPembelian = 0;

        for (Barang barang : daftarBarang) {
            pembelianString.append(barang.jenis).append(" - Harga: ").append(formatRupiah(barang.harga))
                    .append(" - Jumlah: ").append(barang.jumlah).append(" - Total: ").append(formatRupiah(barang.hitungTotal())).append("\n");
            totalPembelian += barang.hitungTotal();
        }

        double totalSetelahDiskon = totalPembelian; // Total awal sebelum diskon
        StringBuilder infoDiskon = new StringBuilder();

        // Mengecek apakah total pembelian minimal Rp 1.000.000 dan memberikan diskon jika iya
        if (totalPembelian >= 1000000) {
            Diskon diskonTotal = new Diskon(10); // Diskon 10%
            totalSetelahDiskon -= (totalPembelian * 0.1); // Mengurangi 10% dari total pembelian
            infoDiskon.append("Diskon Total: ").append(formatRupiah(totalPembelian * 0.1)).append("\n");
        }

        // Menambahkan informasi tentang total pembelian sebelum dan setelah diskon
        pembelianString.append("\nTotal Pembelian sebelum Diskon: ").append(formatRupiah(totalPembelian));
        pembelianString.append("\n").append(infoDiskon);
        pembelianString.append("\nTotal Pembelian setelah Diskon: ").append(formatRupiah(totalSetelahDiskon));

        // Meminta input jumlah uang yang dibayarkan
        try {
            String uangDibayarkanString = JOptionPane.showInputDialog(null, "Masukkan jumlah uang yang dibayarkan: Rp");
            double uangDibayarkan = Double.parseDouble(uangDibayarkanString);

            // Menghitung kembalian
            double kembalian = uangDibayarkan - totalSetelahDiskon;

            // Menambahkan informasi tentang uang yang dibayarkan dan kembalian
            pembelianString.append("\nUang Dibayarkan: ").append(formatRupiah(uangDibayarkan));
            pembelianString.append("\nKembalian: ").append(formatRupiah(kembalian));

            // Menampilkan informasi transaksi
            JTextArea textArea = new JTextArea(pembelianString.toString());
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane);

            JOptionPane.showMessageDialog(
                    null,
                    panel,
                    "Transaksi Pembayaran",
                    JOptionPane.PLAIN_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Masukkan jumlah uang yang valid!");
        }

        tampilkanMenuAwal();
    }

    private static String formatRupiah(double nilai) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return formatRupiah.format(nilai);
    }
}