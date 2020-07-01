package id.wrbcatering.aplikasi.model;

public class CartModel {
    private String id;
    private String nama;
    private String gambar;
    private int jumlah;
    private int minorder;
    private int harga;


    public CartModel(String id,String nama,String gambar,int jumlah, int minorder , int harga) {
        this.nama = nama;
        this.id = id;
        this.gambar = gambar;
        this.jumlah = jumlah;
        this.minorder = minorder;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getMinorder() {
        return minorder;
    }

    public void setMinorder(int minorder) {
        this.minorder = minorder;
    }
}
