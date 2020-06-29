package id.wrbcatering.aplikasi.model;

public class KategoriModel {

    private String id;
    private String nama;
    private String desc;

    public KategoriModel(String id,String nama,String desc) {
        this.nama = nama;
        this.id = id;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
