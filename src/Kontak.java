class Kontak {
    private String nama;
    private String alamat;
    private String telepon;

    // Constructor
    public Kontak(String nama, String alamat, String gambar) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = gambar;
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}