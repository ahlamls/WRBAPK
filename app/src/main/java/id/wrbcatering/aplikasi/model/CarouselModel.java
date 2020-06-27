package id.wrbcatering.aplikasi.model;

public class CarouselModel {
    private String id;
    private String nama;
    private String gambar;

    public CarouselModel(String id,String nama,String gambar) {
        this.nama = nama;
        this.id = id;
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
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
