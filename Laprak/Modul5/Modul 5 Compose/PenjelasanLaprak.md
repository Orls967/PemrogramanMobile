# Pedoman Penjelasan Kode Program

## Format Penjelasan yang Wajib Digunakan

Penjelasan tidak dilakukan per baris, tetapi per kelompok baris yang memiliki fungsi atau tujuan yang sama.

Format yang digunakan:

text Line A-B :  Blok fungsi ini berperan untuk ... ... ... ... 

Contoh:

php $praktikanArr = $praktikan->toArray(); $praktikanArr['hobi'] = $praktikan->hobi ?? []; $praktikanArr['skills'] = $praktikan->skills ?? []; $praktikan = $praktikanArr; 

Penulisan yang diinginkan:

text Line 25-28 :  Blok fungsi ini berperan untuk menyiapkan data praktikan sebelum dikirim ke halaman tampilan. Pada bagian ini objek praktikan terlebih dahulu diubah ke dalam bentuk array agar lebih mudah diproses oleh Blade View. Selain itu data hobi dan skill dipastikan selalu tersedia dalam bentuk array sehingga proses perulangan dapat berjalan dengan aman tanpa menimbulkan error ketika data kosong. Setelah seluruh data selesai dipersiapkan, hasilnya disimpan kembali ke variabel praktikan yang nantinya akan digunakan pada halaman yang ditampilkan kepada pengguna. 

---

# Aturan Penjelasan

## 1. Gunakan Kelompok Baris Berdasarkan Fungsi

Jika beberapa baris memiliki tujuan yang sama, gabungkan menjadi satu pembahasan.

Contoh:

php $cards = array_slice(     Praktikan::experiences(),     0,     2 ); 

Maka formatnya:

text Line 35-39 :  Blok fungsi ini digunakan untuk mengambil sebagian data pengalaman yang tersedia pada model. Hanya dua data pertama yang dipilih untuk ditampilkan pada halaman beranda sebagai ringkasan pengalaman terbaru. Dengan cara ini halaman utama tetap terlihat ringkas namun tetap memberikan gambaran mengenai aktivitas yang pernah dilakukan. 

---

## 2. Semua Baris Harus Tetap Tercakup

Walaupun dijelaskan sebagai satu kesatuan, seluruh baris dalam rentang tersebut harus ikut terbahas.

Jangan sampai ada baris yang tidak memiliki penjelasan.

Tujuannya adalah:

- Tidak menjelaskan per line
- Tetapi tetap mencakup seluruh kode

---

## 3. Untuk CSS Gunakan Kelompok Berdasarkan Komponen

Contoh:

css .img-wrap{     width:100%;     overflow:hidden;     border-top-left-radius:inherit;     border-top-right-radius:inherit; } 

Format:

text Line 120-124 :  Blok fungsi ini membentuk wadah utama untuk gambar pada kartu pengalaman. Pengaturan yang diberikan memastikan gambar memenuhi area kartu secara proporsional, bagian gambar yang keluar dari batas kartu tidak ditampilkan, serta sudut gambar mengikuti bentuk lengkungan kartu sehingga desain tetap konsisten dengan tampilan glassmorphism yang digunakan pada website. 

---

## 4. Untuk CSS Besar Jelaskan Sebagai Sistem Komponen

Contoh:

css .portfolio-card .portfolio-card:hover .portfolio-card::after .img-wrap .img-wrap img 

Karena seluruhnya membentuk satu fitur yang sama, maka cukup dijelaskan sebagai:

text Line 150-210 :  Blok fungsi ini membentuk komponen kartu pengalaman yang digunakan pada halaman beranda maupun profil. Di dalamnya terdapat pengaturan tampilan dasar kartu, pengaturan area gambar, efek hover, animasi pembesaran gambar, hingga efek glow gradasi yang muncul saat kartu dipilih oleh pengguna. Seluruh pengaturan tersebut bekerja secara bersamaan untuk memberikan tampilan yang lebih interaktif sekaligus memperjelas bahwa kartu dapat diklik menuju halaman detail pengalaman. 

---

## 5. Fokus Pada Tujuan Blok

Setiap penjelasan minimal harus menjawab:

- Blok ini digunakan untuk apa?
- Data apa yang diproses?
- Hasilnya digunakan di mana?
- Mengapa blok ini diperlukan?

---

## 6. Gunakan Bahasa Naratif

Gunakan bentuk paragraf.

Hindari:

text Baris ini ... Baris berikutnya ... Baris selanjutnya ... 

Gunakan:

text Blok fungsi ini berperan untuk ... ... ... ... 

---

## 7. Panjang Penjelasan Tidak Dibatasi

Jika sebuah blok memiliki fungsi penting, penjelasan boleh dibuat panjang selama:

- Tetap membahas tujuan utama blok
- Tetap membahas seluruh baris dalam rentang tersebut
- Tidak berubah menjadi penjelasan per baris

---

# Ringkasan Aturan Utama

Format yang diinginkan:

text Line A-B :  Blok fungsi ini berperan untuk ... ... ... ... 

Bukan:

text Baris 1 ... Baris 2 ... Baris 3 ... 

Fokus utama adalah menjelaskan fungsi keseluruhan blok kode sehingga seluruh kode tetap tercakup namun pembahasan menjadi lebih ringkas, natural, dan cocok digunakan pada laporan praktikum.