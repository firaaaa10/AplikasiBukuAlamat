# AplikasiBukuAlamat
 UTS Siti Safira 2210010336 5A

# Pembuat
Nama     : Siti Safira

NPM      : 2210010336
# 1. Deskripsi Program
BukuAlamatGUI adalah aplikasi berbasis Java Swing yang berfungsi untuk mengelola buku alamat. Aplikasi ini memungkinkan pengguna untuk menambahkan, mengedit, menghapus, mencari, dan menyortir kontak. Selain itu, aplikasi ini mendukung fitur ekspor dan pembuatan laporan dalam format TXT dan HTML, serta menyimpan data secara otomatis ke dalam file untuk memastikan data tetap aman.

# 2. Komponen-Komponen GUI
Aplikasi ini menggunakan berbagai komponen GUI untuk antarmuka yang interaktif dan mudah digunakan:

- JFrame: Sebagai jendela utama aplikasi.
- JPanel: Mengorganisasi tata letak komponen dalam aplikasi.
- JLabel: Menampilkan label teks untuk form, seperti "Nama", "Alamat", "Telepon".
- JTextField: Input data teks, misalnya untuk nama, alamat, dan telepon.
- JTextArea: Menampilkan daftar kontak atau laporan dalam bentuk teks.
- JTable: Menampilkan data kontak dalam bentuk tabel dengan kolom Nama, Alamat, dan Telepon.
- JButton:
Tombol untuk melakukan aksi, seperti:
  - Tambah kontak.
  - Ubah kontak.
  - Hapus kontak.
  - Cari kontak.
  - Sortir kontak.
  - Ekspor kontak.
  - Buat laporan TXT dan HTML.
- JFileChooser: Memilih lokasi file untuk menyimpan laporan atau ekspor kontak.

# 3. Fitur-Fitur Utama
# a. Tambah Kontak
Menambahkan kontak baru ke dalam buku alamat dengan validasi input.
```
// Method untuk menambahkan kontak
    private void tambahKontak() {
        // Ambil data dari JTextField
        String nama = inputNama.getText();
        String alamat = inputAlamat.getText();
        String telepon = inputTelepon.getText();
        // Validasi input
        if (nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Long.parseLong(telepon); // Validasi telepon sebagai angka
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Buat objek Kontak baru
        Kontak kontakBaru = new Kontak(nama, alamat, telepon);
        // Tambahkan kontak ke ArrayList
        daftarKontak.add(kontakBaru);
        // Bersihkan input JTextField
        inputNama.setText("");
        inputAlamat.setText("");
        inputTelepon.setText("");
        // Perbarui tampilan tabelKontak
        updateTabelKontak();        
        // Perbarui tampilan outputKontak
        updateOutputKontak();
        // Simpan data ke file
        saveDataToFile();       
    }
```
# b. Edit Kontak
Mengedit data kontak yang sudah ada berdasarkan nama.
```
// Method untuk mengubah kontak
    private void ubahKontak() {
        // Ambil data dari JTextField
        String nama = inputNama.getText();
        String alamat = inputAlamat.getText();
        String telepon = inputTelepon.getText();
        // Validasi input
        if (nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Cari kontak yang ingin diubah
        int index = findKontak(nama);

        if (index != -1) {
            // Ubah data kontak
            daftarKontak.get(index).setNama(nama);
            daftarKontak.get(index).setAlamat(alamat);
            daftarKontak.get(index).setTelepon(telepon);
            // Perbarui tampilan tabelKontak
            updateTabelKontak();            
            // Perbarui tampilan outputKontak
            updateOutputKontak();
            // Simpan data ke file
            saveDataToFile();
        } else {
            // Tampilkan pesan error jika kontak tidak ditemukan
            JOptionPane.showMessageDialog(this, "Kontak tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
```
# c. Hapus Kontak
Menghapus kontak berdasarkan nama.
```
// Method untuk menghapus kontak
    private void hapusKontak() {
        // Ambil data dari JTextField
        String nama = inputNama.getText();
        // Validasi input
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Cari kontak yang ingin dihapus
        int index = findKontak(nama);

        if (index != -1) {
            // Hapus kontak dari ArrayList
            daftarKontak.remove(index);
            // Perbarui tampilan tabelKontak
            updateTabelKontak();
            // Perbarui tampilan outputKontak
            updateOutputKontak();
            // Simpan data ke file
            saveDataToFile();
        } else {
            // Tampilkan pesan error jika kontak tidak ditemukan
            JOptionPane.showMessageDialog(this, "Kontak tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
```
# d. Cari Kontak
Mencari kontak berdasarkan alamat yang diinputkan pengguna.
```
// Method untuk mencari kontak
    private int findKontak(String nama) {
        for (int i = 0; i < daftarKontak.size(); i++) {
            if (daftarKontak.get(i).getNama().equals(nama)) {
                return i;
            }
        }
        return -1; // Kontak tidak ditemukan       
    }
// Method untuk mencari kontak berdasarkan alamat
    private void cariKontak() {
        String alamatDicari = inputCari.getText();
        ArrayList<Kontak> hasilPencarian = new ArrayList<>();

        for (Kontak kontak : daftarKontak) {
            if (kontak.getAlamat().toLowerCase().contains(alamatDicari.toLowerCase())) {
                hasilPencarian.add(kontak);
            }
        }
        updateTabelKontak(hasilPencarian);
        updateOutputKontak(hasilPencarian);
    }

```
# e. Sortir Kontak
Mengurutkan kontak berdasarkan nama secara alfabetis.
```
 // Method untuk mengurutkan kontak
    private void sortKontak() {
        Collections.sort(daftarKontak, new Comparator<Kontak>() {
            @Override
            public int compare(Kontak o1, Kontak o2) {
                return o1.getNama().compareToIgnoreCase(o2.getNama()); // Sorting berdasarkan nama
            }
        });
        updateTabelKontak();
        updateOutputKontak();
    }
```
# f. Ekspor Kontak
Menyimpan kontak dalam file TXT atau CSV.
```
// Method untuk mengekspor kontak ke file
    private void exportKontak() {
        // Pilih format file (txt, csv, dll.)
        String formatFile = JOptionPane.showInputDialog(this, "Pilih format file (txt, csv):", "Export Kontak", JOptionPane.QUESTION_MESSAGE);

        if (formatFile != null && (formatFile.equalsIgnoreCase("txt") || formatFile.equalsIgnoreCase("csv"))) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try (PrintWriter writer = new PrintWriter(file)) {
                    if (formatFile.equalsIgnoreCase("txt")) {
                        for (Kontak kontak : daftarKontak) {
                            writer.println(kontak.getNama() + " - " + kontak.getAlamat() + " - " + kontak.getTelepon());
                        }
                    } else { // formatFile.equalsIgnoreCase("csv")
                        writer.println("Nama,Alamat,Gambar");
                        for (Kontak kontak : daftarKontak) {
                            writer.println(kontak.getNama() + "," + kontak.getAlamat() + "," + kontak.getTelepon());
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Kontak berhasil diekspor ke file " + file.getName());
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal mengekspor kontak: " + ex.getMessage());
                }
            }
        }
    }
```
# g. Pembuatan Laporan:
Membuat laporan daftar kontak dalam format:
# 1. TXT
Teks sederhana.
```
// Method untuk generate laporan txt
    private void generateReport() {
        File reportFile = new File("LaporanKontak.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            writer.write("Laporan Buku Alamat\\n");
            writer.write("====================\\n");
            for (Kontak kontak : daftarKontak) {
                writer.write("Nama: " + kontak.getNama() + "\\n");
                writer.write("Alamat: " + kontak.getAlamat() + "\\n");
                writer.write("Telepon: " + kontak.getTelepon() + "\\n");
                writer.write("--------------------\\n");
            }
            JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat: " + reportFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat laporan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
```
# 2. HTML
Tampilan tabel yang lebih menarik di browser.
```
  // Method untuk generate laporan html
    private void generateReportHTML() {
    File reportFile = new File("LaporanKontak.html");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
        writer.write("<html><body>");
        writer.write("<h1>Laporan Buku Alamat</h1>");
        writer.write("<table border='1'>");
        writer.write("<tr><th>Nama</th><th>Alamat</th><th>Telepon</th></tr>");
        
        for (Kontak kontak : daftarKontak) {
            writer.write("<tr>");
            writer.write("<td>" + kontak.getNama() + "</td>");
            writer.write("<td>" + kontak.getAlamat() + "</td>");
            writer.write("<td>" + kontak.getTelepon() + "</td>");
            writer.write("</tr>");
        }

        writer.write("</table>");
        writer.write("</body></html>");
        JOptionPane.showMessageDialog(this, "Laporan HTML berhasil dibuat: LaporanKontak.html");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuat laporan HTML.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}    
```
# h. Simpan dan Muat Data Otomatis
Data kontak disimpan secara otomatis ke dalam file kontak.txt dan dimuat saat aplikasi dijalankan kembali.
```
// Method untuk menyimpan data kontak ke file
    private void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Kontak kontak : daftarKontak) {
                writer.println(kontak.getNama() + "," + kontak.getAlamat() + "," + kontak.getTelepon());
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data kontak: " + ex.getMessage());
        }
    }
    // Method untuk memuat data kontak dari file
    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    daftarKontak.add(new Kontak(parts[0], parts[1], parts[2]));
                }
            }
            updateTabelKontak();
        } catch (IOException ex) {
            // File tidak ditemukan, aplikasi akan berjalan tanpa data
            JOptionPane.showMessageDialog(this, "File kontak tidak ditemukan. Aplikasi akan berjalan tanpa data.");
        }
    }    
```
# 4. Validasi Input
# a. Nama dan alamat tidak boleh kosong.
```
// Validasi input
        if (nama.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
```
# b. Nomor telepon harus berupa angka.
```
try {
            Long.parseLong(telepon); // Validasi telepon sebagai angka
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
```
# c. Pesan kesalahan akan muncul jika input tidak valid.
```
  JOptionPane.showMessageDialog(this, "Nama dan alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
  JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
```
# 5. Events
# a. Tombol Tambah
```
private void tombolTambahActionPerformed(java.awt.event.ActionEvent evt) {                                             
    // Tambahkan listener ke tombol
    tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahKontak();
            }
        });
    }    
```
# b. Tombol Ubah
```
private void tombolUbahActionPerformed(java.awt.event.ActionEvent evt) {                                           
    tombolUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahKontak();
            }
        });
    }       
```
# c. Tombol Hapus
```
 private void tombolHapusActionPerformed(java.awt.event.ActionEvent evt) {                                            
     tombolHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusKontak();
            }
        });
    }    
```
# d. Tombol Sortir
```
private void tombolSortActionPerformed(java.awt.event.ActionEvent evt) {                                           
    tombolSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortKontak();
            }
        });

    }                                          

```
# e. Tombol Export
```
private void tombolExportActionPerformed(java.awt.event.ActionEvent evt) {                                             
    tombolExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportKontak();
            }
        });
    }       
```
# f. Tombol Keluar
```
private void tombolKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                             
    tombolKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }         
```
# h. Tombol Report txt
```
private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {                                          
     btnReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generateReport();
            }
        });
    }   
```
# i. Tombol Report HTML
```
private void tombolReportHTMLActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    tombolReportHTML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generateReportHTML();
            }
        });
    }     
```
# j. listener inputCari
```
private void inputCariActionPerformed(java.awt.event.ActionEvent evt) {                                          
    // Tambahkan listener ke inputCari
        inputCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariKontak();
            
            }
            
        });
    }        
```

# 6. Tampilan Pada Saat Aplikasi Di Jalankan
![](https://github.com/firaaaa10/Latihan3_AplikasiPengelolaanKontak/blob/main/Cuplikan%20layar%202024-11-13%20231510.png)

## Indikator Penilaian:

| No  | Komponen                       |  Persentase  |
| :-: | --------------                 |   :-----:    |
|  1  | Fungsional aplikasi            |      20      |
|  2  | Desain dan UX                  |      20      |
|  3  | Penerapan Konsep OOP           |      15      |
|  4  | Kreativitas dan Inovasi Fitur  |      15      |
|  5  | Dokumentasi Kode               |      10      |
|  6  | Tantangan                      |      20      |
|     | *TOTAL*        | *100* |
