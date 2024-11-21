import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class BukuAlamatGUI extends javax.swing.JFrame {
    // Deklarasi variabel untuk menyimpan data kontak
    private final ArrayList<Kontak> daftarKontak = new ArrayList<>();
    // Deklarasi komponen GUI
    private DefaultTableModel modelTabel;
    // File untuk menyimpan data kontak
    private String fileName = "kontak.txt";
     
    public BukuAlamatGUI() {
        initComponents();  
        // Inisialisasi modelTabel
        modelTabel = new DefaultTableModel(new Object[]{"Nama", "Alamat", "Telepon"}, 0);
        tabelKontak.setModel(modelTabel); // Menetapkan modelTabel ke tabelKontak
       // Muat data dari file saat aplikasi dijalankan
        loadDataFromFile();
        
    } 
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
    // Method untuk mencari kontak
    private int findKontak(String nama) {
        for (int i = 0; i < daftarKontak.size(); i++) {
            if (daftarKontak.get(i).getNama().equals(nama)) {
                return i;
            }
        }
        return -1; // Kontak tidak ditemukan       
    }
    // Method untuk memperbarui tampilan outputKontak
    private void updateOutputKontak() {
        // Bersihkan isi JTextArea
        outputKontak.setText("");
        // Tampilkan daftar kontak di JTextArea
        for (Kontak kontak : daftarKontak) {
            outputKontak.append(kontak.getNama() + " - " + kontak.getAlamat() + "\n");
        }
    }
    // Method untuk memperbarui tampilan tabelKontak
    private void updateTabelKontak() {
        // Bersihkan data tabel
        modelTabel.setRowCount(0);
        // Tampilkan daftar kontak di tabel
        for (Kontak kontak : daftarKontak) {
            modelTabel.addRow(new Object[]{kontak.getNama(), kontak.getAlamat(), kontak.getTelepon()});
        }
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
    // Method untuk memperbarui tampilan tabelKontak dengan hasil pencarian
    private void updateTabelKontak(ArrayList<Kontak> kontakList) {
        // Bersihkan data tabel
        modelTabel.setRowCount(0);
        // Tampilkan daftar kontak di tabel
        for (Kontak kontak : kontakList) {
            modelTabel.addRow(new Object[]{kontak.getNama(), kontak.getAlamat(), kontak.getTelepon()});
        }
    }    
    // Method untuk memperbarui tampilan outputKontak dengan hasil pencarian
    private void updateOutputKontak(ArrayList<Kontak> kontakList) {
        // Bersihkan isi JTextArea
        outputKontak.setText("");
        // Tampilkan daftar kontak di JTextArea
        for (Kontak kontak : kontakList) {
            outputKontak.append(kontak.getNama() + " - " + kontak.getAlamat() + "\n");
        }
    }   
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        labelAlamat = new javax.swing.JLabel();
        inputNama = new javax.swing.JTextField();
        inputAlamat = new javax.swing.JTextField();
        labelTelepon = new javax.swing.JLabel();
        inputTelepon = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        tombolUbah = new javax.swing.JButton();
        tombolTambah = new javax.swing.JButton();
        tombolHapus = new javax.swing.JButton();
        tombolSort = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        tombolExport = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        tombolReportHTML = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        scrollPaneKontak = new javax.swing.JScrollPane();
        outputKontak = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        inputCari = new javax.swing.JTextField();
        labelCari = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKontak = new javax.swing.JTable();
        tombolKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 0, 153)));

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ujian Tengah Semester Mata Kuliah Pemrograman Berbasis Objek 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(102, 0, 204))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 204));
        jLabel4.setText("APLIKASI BUKU ALAMAT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(jLabel4)
                .addContainerGap(391, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 102, 102)), "Masukkan Data-data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 102, 102))); // NOI18N

        labelNama.setText("Nama         :");

        labelAlamat.setText("Alamat       :");

        inputNama.setBackground(new java.awt.Color(255, 153, 153));

        inputAlamat.setBackground(new java.awt.Color(255, 153, 153));

        labelTelepon.setText("Telepon      :");

        inputTelepon.setBackground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNama)
                    .addComponent(labelAlamat)
                    .addComponent(labelTelepon))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputNama, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(inputAlamat)
                    .addComponent(inputTelepon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNama)
                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAlamat)
                    .addComponent(inputAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelepon)
                    .addComponent(inputTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        jPanel4.setBackground(new java.awt.Color(255, 153, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 51, 153)));

        tombolUbah.setBackground(new java.awt.Color(255, 102, 153));
        tombolUbah.setText("Edit");
        tombolUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolUbahActionPerformed(evt);
            }
        });

        tombolTambah.setBackground(new java.awt.Color(255, 102, 153));
        tombolTambah.setText("Tambah");
        tombolTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolTambahActionPerformed(evt);
            }
        });

        tombolHapus.setBackground(new java.awt.Color(255, 102, 153));
        tombolHapus.setText("Hapus");
        tombolHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolHapusActionPerformed(evt);
            }
        });

        tombolSort.setBackground(new java.awt.Color(255, 102, 153));
        tombolSort.setText("Sortir");
        tombolSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(tombolTambah)
                .addGap(39, 39, 39)
                .addComponent(tombolUbah)
                .addGap(44, 44, 44)
                .addComponent(tombolHapus)
                .addGap(52, 52, 52)
                .addComponent(tombolSort)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tombolTambah)
                    .addComponent(tombolUbah)
                    .addComponent(tombolHapus)
                    .addComponent(tombolSort))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 204, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tombol Menyimpan ke File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tempus Sans ITC", 1, 16), new java.awt.Color(255, 153, 0))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(255, 204, 153));

        tombolExport.setBackground(new java.awt.Color(255, 153, 102));
        tombolExport.setText("Export");
        tombolExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolExportActionPerformed(evt);
            }
        });

        btnReport.setBackground(new java.awt.Color(255, 153, 102));
        btnReport.setText("Report txt");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        tombolReportHTML.setBackground(new java.awt.Color(255, 153, 102));
        tombolReportHTML.setText("Report HTML");
        tombolReportHTML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolReportHTMLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tombolExport, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tombolReportHTML, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addGap(48, 48, 48))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tombolReportHTML)
                .addGap(0, 11, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(tombolExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(204, 255, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 0)), "Daftar Kontak", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(153, 204, 0))); // NOI18N

        outputKontak.setBackground(new java.awt.Color(204, 255, 153));
        outputKontak.setColumns(20);
        outputKontak.setRows(5);
        scrollPaneKontak.setViewportView(outputKontak);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneKontak)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneKontak, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 153, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Untuk Pencarian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(204, 0, 204))); // NOI18N

        inputCari.setBackground(new java.awt.Color(255, 102, 255));
        inputCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCariActionPerformed(evt);
            }
        });

        labelCari.setForeground(new java.awt.Color(204, 0, 204));
        labelCari.setText("Cari Kontak  :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCari)
                    .addComponent(inputCari, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 102)), "Daftar Table", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 204, 0))); // NOI18N

        tabelKontak.setBackground(new java.awt.Color(204, 255, 204));
        tabelKontak.setForeground(new java.awt.Color(153, 153, 0));
        tabelKontak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama", "Alamat", "Telepon"
            }
        ));
        tabelKontak.setGridColor(new java.awt.Color(153, 204, 0));
        tabelKontak.setSelectionBackground(new java.awt.Color(153, 153, 0));
        tabelKontak.setSelectionForeground(new java.awt.Color(204, 204, 0));
        jScrollPane1.setViewportView(tabelKontak);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
        );

        tombolKeluar.setBackground(new java.awt.Color(204, 51, 255));
        tombolKeluar.setText("Keluar");
        tombolKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1))
                            .addComponent(tombolKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tombolKeluar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(186, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tombolUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolUbahActionPerformed
    tombolUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahKontak();
            }
        });
    }//GEN-LAST:event_tombolUbahActionPerformed

    private void tombolTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolTambahActionPerformed
    // Tambahkan listener ke tombol
    tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahKontak();
            }
        });
    }//GEN-LAST:event_tombolTambahActionPerformed

    private void tombolHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolHapusActionPerformed
     tombolHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusKontak();
            }
        });
    }//GEN-LAST:event_tombolHapusActionPerformed

    private void tombolSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolSortActionPerformed
    tombolSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortKontak();
            }
        });

    }//GEN-LAST:event_tombolSortActionPerformed

    private void tombolExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolExportActionPerformed
    tombolExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportKontak();
            }
        });
    }//GEN-LAST:event_tombolExportActionPerformed

    private void inputCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCariActionPerformed
    // Tambahkan listener ke inputCari
        inputCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariKontak();
            }
        });
    }//GEN-LAST:event_inputCariActionPerformed

    private void tombolKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolKeluarActionPerformed
    tombolKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }//GEN-LAST:event_tombolKeluarActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
     btnReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generateReport();
            }
        });
    }//GEN-LAST:event_btnReportActionPerformed

    private void tombolReportHTMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolReportHTMLActionPerformed
    tombolReportHTML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generateReportHTML();
            }
        });
    }//GEN-LAST:event_tombolReportHTMLActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BukuAlamatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BukuAlamatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BukuAlamatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BukuAlamatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BukuAlamatGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReport;
    private javax.swing.JTextField inputAlamat;
    private javax.swing.JTextField inputCari;
    private javax.swing.JTextField inputNama;
    private javax.swing.JTextField inputTelepon;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAlamat;
    private javax.swing.JLabel labelCari;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelTelepon;
    private javax.swing.JTextArea outputKontak;
    private javax.swing.JScrollPane scrollPaneKontak;
    private javax.swing.JTable tabelKontak;
    private javax.swing.JButton tombolExport;
    private javax.swing.JButton tombolHapus;
    private javax.swing.JButton tombolKeluar;
    private javax.swing.JButton tombolReportHTML;
    private javax.swing.JButton tombolSort;
    private javax.swing.JButton tombolTambah;
    private javax.swing.JButton tombolUbah;
    // End of variables declaration//GEN-END:variables
}
