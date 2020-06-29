package id.wrbcatering.aplikasi.model;

public class MenuModel {
    private String id;
    private String nama;
    private String gambar;
    private String harga;

    public MenuModel(String id,String nama,String harga,String gambar) {
        this.nama = nama;
        this.id = id;
        this.gambar = gambar;
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
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
}
