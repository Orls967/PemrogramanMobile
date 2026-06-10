# Pembahasan Kode Program - Modul 5 Compose

Dokumen ini berisi penjelasan menyeluruh untuk setiap file kode program pada proyek Game List Compose, yang disusun berdasarkan kelompok baris fungsi sesuai dengan pedoman laporan praktikum.

---

## 1. MainActivity.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/MainActivity.kt`

```text
Line 1-7 : Blok fungsi ini digunakan untuk mendefinisikan identitas paket komponen (package) serta mengimpor kelas-kelasan eksternal pendukung seperti Activity inti, setContent untuk Compose, navigasi utama, serta pustaka Timber untuk pencatatan log debug aplikasi.

Line 9-22 : Blok fungsi ini mendeklarasikan kelas MainActivity yang mewarisi ComponentActivity sebagai titik masuk utama (entry point) aplikasi. Di dalam metode onCreate, Timber diinisialisasi agar dapat mencatat log aktivitas debug, pesan selamat datang dicetak ke logcat, dan setContent dipanggil untuk merender fungsi GameListApp sebagai antarmuka Compose utama aplikasi.
```

---

## 2. AppNavigation.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/app/navigation/AppNavigation.kt`

```text
Line 1-12 : Blok fungsi ini berperan untuk mendefinisikan lokasi paket navigasi serta melakukan impor komponen-komponen penting dari pustaka Jetpack Compose Navigation, ViewModel, komponen layar ListScreen dan DetailScreen, serta sistem pencatatan log Timber.

Line 14-27 : Blok fungsi ini mendeklarasikan fungsi GameListApp yang menginisiasi pengontrol navigasi NavController, mengambil context lokal saat ini, membuat instance GameViewModel melalui pabrik ViewModel (GameViewModelFactory), dan mengkoleksi StateFlow daftar game serta game pilihan saat ini menjadi Compose State yang reaktif.

Line 28-64 : Blok fungsi ini membangun komponen NavHost sebagai wadah navigasi utama dengan rute awal menuju halaman daftar game ("list"). Di dalamnya diatur rute "list" untuk menampilkan ListScreen dan merekam game yang dipilih untuk berpindah ke rute "detail" guna merender halaman DetailScreen yang menampilkan data game secara detail.
```

---

## 3. AppDatabase.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/core/database/AppDatabase.kt`

```text
Line 1-8 : Blok fungsi ini mendeklarasikan paket database dan mengimpor pustaka Room Database, komponen pembuat database Room, antarmuka GameDao, serta entitas database GameEntity.

Line 10-14 : Blok fungsi ini mengonfigurasi anotasi kelas database Room @Database dengan mendaftarkan entitas GameEntity, versi skema pertama, mematikan ekspor skema otomatis, dan mendefinisikan fungsi abstrak gameDao untuk mengekspos metode kueri data game.

Line 15-31 : Blok fungsi ini mendefinisikan companion object yang menerapkan pola Singleton dengan kata kunci @Volatile untuk memastikan instansiasi AppDatabase bersifat thread-safe dan hanya dibuat sekali di seluruh daur hidup aplikasi melalui kueri Room.databaseBuilder.
```

---

## 4. ApiResult.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/core/network/ApiResult.kt`

```text
Line 1-2 : Blok fungsi ini mendeklarasikan paket tempat sealed class pembungkus status pemanggilan jaringan berada.

Line 3-7 : Blok fungsi ini mendefinisikan generic sealed class ApiResult beserta tiga status turunannya yaitu Loading (proses pemuatan data), Success (proses pemuatan berhasil dengan membawa data objek generik T), dan Error (proses pemuatan gagal dengan membawa objek Exception beserta deskripsi pesan galat).
```

---

## 5. NetworkModule.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/core/network/NetworkModule.kt`

```text
Line 1-8 : Blok fungsi ini berfungsi mengimpor converter kotlinx serialization dari Jake Wharton, pustaka penanganan format JSON KotlinX, parser jenis media OkHttp, klien jaringan OkHttpClient, serta modul utama Retrofit.

Line 9-28 : Blok fungsi ini mendefinisikan singleton object NetworkModule yang mengonfigurasi alamat pangkalan URL API RAWG, instansiasi utilitas Json yang mengabaikan kunci tidak dikenal saat parsing, instansiasi klien OkHttpClient, perakitan instance Retrofit dengan konverter KotlinX Serialization, serta menyediakan fungsi generik createService untuk membuat layanan API secara dinamis.
```

---

## 6. SafeApiCall.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/core/network/SafeApiCall.kt`

```text
Line 1-5 : Blok fungsi ini mengimpor kelas eksekusi HTTP Exception dan IO Exception yang digunakan untuk mendeteksi jenis kegagalan jaringan yang terjadi saat melakukan pemanggilan API.

Line 6-16 : Blok fungsi ini mendeklarasikan fungsi penanganan tingkat tinggi safeApiCall yang mengeksekusi panggilan API di dalam blok try-catch untuk mengembalikan hasil data sukses dibungkus ApiResult.Success, serta menangkap galat HTTP, ketiadaan jaringan/timeout (IOException), maupun galat umum lainnya untuk dikembalikan sebagai ApiResult.Error.
```

---

## 7. AppPreferences.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/core/preferences/AppPreferences.kt`

```text
Line 1-5 : Blok fungsi ini mendeklarasikan paket preferensi lokal dan mengimpor Context dan SharedPreferences yang dibutuhkan untuk melakukan penyimpanan data persisten ringan.

Line 6-11 : Blok fungsi ini mendefinisikan kelas AppPreferences yang menginisiasi instansi SharedPreferences lokal secara privat menggunakan nama file preferensi tertentu dengan mode akses tertutup (MODE_PRIVATE).

Line 13-19 : Blok fungsi ini menyediakan metode getter dan setter bernama saveLastOpenedGame dan getLastOpenedGame untuk menyimpan data nama game terakhir yang diakses serta memuatnya kembali dari memori penyimpanan SharedPreferences.

Line 21-25 : Blok fungsi ini mendefinisikan companion object yang menampung string konstanta statis sebagai penanda nama file preferensi dan kunci penyimpanan data game terakhir yang dibuka.
```

---

## 8. GameDao.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/local/GameDao.kt`

```text
Line 1-5 : Blok fungsi ini mendeklarasikan lokasi paket DAO dan mengimpor anotasi manipulasi data Room (@Dao, @Query, @Insert) serta pustaka asinkronous Kotlin Flow.

Line 7-17 : Blok fungsi ini mendefinisikan interface GameDao sebagai objek akses database lokal yang menyediakan kueri getAllGames untuk memantau data di tabel secara realtime via Flow, kueri insertGames untuk menyimpan daftar entitas game dengan strategi replace konflik, serta kueri clearGames untuk mengosongkan cache tabel lokal.
```

---

## 9. GameEntity.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/local/GameEntity.kt`

```text
Line 1-5 : Blok fungsi ini mengimpor pustaka Entity dan PrimaryKey dari modul Room Database sebagai anotasi pendefinisian entitas tabel SQLite.

Line 6-17 : Blok fungsi ini mendeklarasikan data class GameEntity yang mendefinisikan skema struktur tabel SQLite bernama "games", termasuk kunci utama id yang dihasilkan secara otomatis, serta kolom nama, tahun rilis, deskripsi, genre, URL gambar remote, integer gambar lokal, dan URL halaman resmi game tersebut.
```

---

## 10. GameDto.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/remote/dto/GameDto.kt`

```text
Line 1-5 : Blok fungsi ini mengimpor anotasi @Serializable dan @SerialName dari pustaka KotlinX Serialization untuk mengonfigurasi pemetaan serialisasi data JSON.

Line 6-15 : Blok fungsi ini mendeklarasikan data class GameDto sebagai representasi data game dari API JSON RAWG dengan anotasi @Serializable, serta menggunakan anotasi @SerialName untuk memetakan nama field kunci ular (snake_case) dari JSON seperti "background_image" ke field camelCase Kotlin.

Line 17-22 : Blok fungsi ini mendeklarasikan sub-data class GenreDto untuk melakukan serialisasi data nested array list genre yang dikirimkan oleh server API RAWG bersama objek game utama.
```

---

## 11. GameResponseDto.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/remote/dto/GameResponseDto.kt`

```text
Line 1-4 : Blok fungsi ini mengimpor komponen anotasi serialisasi KotlinX untuk pemetaan struktur respon root API.

Line 5-11 : Blok fungsi ini mendeklarasikan data class root respon JSON API RAWG bernama GameResponseDto yang berisi metadata jumlah hit halaman beserta daftar koleksi data hasil game dalam bentuk list dari objek GameDto.
```

---

## 12. GameApiService.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/remote/GameApiService.kt`

```text
Line 1-6 : Blok fungsi ini mengimpor DTO respon game utama serta anotasi metode HTTP GET dan Query dari Retrofit untuk merancang panggilan endpoint API.

Line 7-20 : Blok fungsi ini mendefinisikan interface GameApiService yang mendeklarasikan dua fungsi asinkron (suspend) getGames untuk mengambil daftar game teratas, dan searchGame untuk mencari data game spesifik di endpoint "games" dengan parameter kata kunci kueri pencarian dan kunci API RAWG.
```

---

## 13. GameRepositoryImpl.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/data/repository/GameRepositoryImpl.kt`

```text
Line 1-17 : Blok fungsi ini mengimpor ketergantungan konfigurasi BuildConfig, resource lokal, ApiResult, DAO lokal Room, domain model, interface repositori, serta pustaka asinkron Kotlin Coroutine (async, coroutineScope, Flow).

Line 18-21 : Blok fungsi ini mendeklarasikan konstruktor kelas GameRepositoryImpl yang mengimplementasikan interface domain GameRepository dengan menginjeksikan dependency GameApiService untuk panggilan online dan GameDao untuk manipulasi database cache lokal.

Line 23-37 : Blok fungsi ini memulai implementasi getGames yang mengembalikan Flow. Proses diawali dengan memancarkan status ApiResult.Loading, lalu mendefinisikan daftar 10 nama game populer yang akan dicari secara dinamis dari API RAWG.

Line 39-49 : Blok fungsi ini melakukan panggilan asinkron ke API RAWG di dalam blok safeApiCall secara konkuren menggunakan fungsi async-await Kotlin Coroutine untuk mencari detail 10 game populer tersebut secara paralel demi mengoptimalkan kecepatan unduhan data.

Line 50-92 : Blok fungsi ini menangani kondisi pemanggilan API yang sukses dengan cara mengonversi data DTO menjadi entitas database lokal, mengosongkan cache Room yang usang (`clearGames`), menulis data baru (`insertGames`), memuat data terbaru dari database lokal, memetakan entitas ke model domain Game, lalu memancarkannya sebagai status ApiResult.Success.

Line 93-122 : Blok fungsi ini merupakan penanganan error fallback, di mana jika pemanggilan jaringan gagal, blok catch akan memuat data cache game yang ada di database lokal SQLite Room untuk dikembalikan sebagai ApiResult.Success agar aplikasi dapat tetap berjalan dan menampilkan data dalam kondisi tanpa internet (offline).
```

---

## 14. Game.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/domain/model/Game.kt`

```text
Line 1-2 : Blok fungsi ini mendefinisikan lokasi paket tempat objek domain utama aplikasi ditempatkan.

Line 3-11 : Blok fungsi ini mendeklarasikan data class Game sebagai entitas domain bersih (domain model) yang digunakan pada presentasi antarmuka Jetpack Compose, menampung field nama, tahun rilis, deskripsi, genre tunggal, resource gambar lokal, URL pranala resmi, dan URL gambar eksternal dari API.
```

---

## 15. GameRepository.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/domain/repository/GameRepository.kt`

```text
Line 1-6 : Blok fungsi ini mengimpor kelas pembungkus respon ApiResult, model domain Game, serta pustaka Kotlin Flow untuk mendefinisikan kontrak data stream.

Line 7-9 : Blok fungsi ini mendeklarasikan interface GameRepository pada domain layer yang mengekspos metode getGames sebagai kontrak penyediaan aliran data game (Flow) bagi presentasi UI.
```

---

## 16. GameItem.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/presentation/components/GameItem.kt`

```text
Line 1-24 : Blok fungsi ini berperan mengimpor pustaka context Android, intent peramban, Jetpack Compose UI (Modifier, Card, Text, Button, Spacing, alignment, dll.), komponen Coil SubcomposeAsyncImage, serta logger debug Timber.

Line 26-44 : Blok fungsi ini mendeklarasikan fungsi pembuat komponen kartu game individual bernama GameItem yang diawali dengan penambahan logger LaunchedEffect untuk mencatat data game yang berhasil dimuat masuk ke dalam daftar viewport antarmuka layar.

Line 46-85 : Blok fungsi ini membangun layout pembungkus Card dengan warna latar ungu muda (0xFFEDE7F6) dan sudut melengkung. Di dalamnya, komponen SubcomposeAsyncImage merender gambar game dari API, dan secara dinamis menampilkan kontainer bergradasi ungu dengan inisial nama game jika gambar sedang memuat atau gagal dimuat.

Line 86-148 : Blok fungsi ini menyusun area konten data game di sebelah kanan gambar yang disejajarkan secara proporsional. Di dalamnya terdapat judul game berukuran `18.sp` Bold (maksimal 2 baris), metadata tag Genre & Tahun berukuran `12.sp`, serta deskripsi game natural hasil pemformatan berukuran `11.sp` (maksimal 3 baris diakhiri ellipsis).

Line 149-166 : Blok fungsi ini merender dua tombol aksi sejajar di bagian bawah kartu: tombol "Official" yang memicu intent peramban eksternal ke URL RAWG, dan tombol "Detail" yang memicu navigasi antarmuka menuju halaman detail game bersangkutan.

Line 167-228 : Blok fungsi ini mendefinisikan fungsi utilitas privat formatDescription dan formatDateString untuk mem-parsing kalimat deskripsi mentah dari database/API dan menerjemahkan format tanggal ISO (YYYY-MM-DD) menjadi kalimat deskripsi yang lebih natural serta nyaman dibaca dalam Bahasa Indonesia.
```

---

## 17. DetailScreen.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/presentation/screens/DetailScreen.kt`

```text
Line 1-19 : Blok fungsi ini mengimpor pustaka dasar Jetpack Compose, bentuk radius, ikon Material Icons ArrowBack, komponen tata letak, komponen Surface dari Material 3, model domain Game, serta Coil image loader.

Line 21-49 : Blok fungsi ini mendeklarasikan layar utama DetailScreen yang membungkus seluruh konten dalam Column dengan dukungan vertical scroll, serta menyematkan bilah judul "Detail Game" beserta tombol kembali (ArrowBack) di bagian atas layar.

Line 50-83 : Blok fungsi ini menyusun komponen gambar banner utama game bertipe SubcomposeAsyncImage dengan rasio `16:9` yang memenuhi lebar layar. Di dalamnya disematkan penanganan state loading/error berupa gradasi linear ungu menawan beserta inisial huruf game berukuran `36.sp` tebal di tengah.

Line 84-162 : Blok fungsi ini membangun isi informasi game di bawah banner dengan susunan visual hierarki yang konsisten: Judul game berukuran `28.sp` ExtraBold, indikator rating bintang emas `⭐ X / 5` yang diekstrak dari deskripsi game, chip tag berlatar belakang khusus untuk genre dan tahun rilis, garis pemisah HorizontalDivider, serta teks deskripsi natural Bahasa Indonesia yang lapang dengan line-height `22.sp`.

Line 163-198 : Blok fungsi ini mengimplementasikan metode utilitas lokal formatDescription dan formatDateString untuk menerjemahkan format tanggal mentah dari API menjadi teks tanggal formal Bahasa Indonesia, serta menyusunnya menjadi paragraf deskripsi yang nyaman dibaca oleh pengguna.
```

---

## 18. ListScreen.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/presentation/screens/ListScreen.kt`

```text
Line 1-15 : Blok fungsi ini mengimpor utilitas Jetpack Compose, tata letak LazyColumn, CircularProgressIndicator dari Material 3, platform LocalContext, serta komponen GameItem untuk merender daftar item game secara dinamis.

Line 16-24 : Blok fungsi ini mendeklarasikan fungsi composable ListScreen yang menampung daftar list data game dan aksi klik navigasi detail, serta mengambil referensi context lokal saat ini.

Line 25-34 : Blok fungsi ini melakukan pemeriksaan status data. Jika daftar game kosong (list.isEmpty() saat memuat data pertama kali dari API), aplikasi akan menampilkan CircularProgressIndicator (indikator pemuatan berputar) berwarna ungu di tengah layar guna menghindari layar kosong putih yang merusak UX.

Line 35-56 : Blok fungsi ini merender daftar utama game menggunakan LazyColumn (scroll list dinamis) yang mengalirkan setiap entri game di dalam list untuk ditampilkan sebagai kartu GameItem dengan padding vertikal yang konsisten.
```

---

## 19. GameViewModel.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/presentation/viewModel/GameViewModel.kt`

```text
Line 1-14 : Blok fungsi ini mengimpor pustaka siklus hidup ViewModel, cakupan coroutine viewModelScope, ApiResult, kelas preferensi AppPreferences, model domain Game, interface repositori, serta pustaka StateFlow dan logger Timber.

Line 15-34 : Blok fungsi ini mendeklarasikan kelas GameViewModel beserta konstruktor pendukungnya yang melacak StateFlow reaktif daftar game (`listGame`) dan game yang sedang dipilih (`selectedGame`), serta memicu pemanggilan awal fungsi fetchGames di dalam blok inisialisasi init.

Line 35-53 : Blok fungsi ini mendeklarasikan metode fetchGames yang mengumpulkan (collect) data aliran Flow dari repositori di dalam coroutine scope. ViewModel akan memantau status pemanggilan: mencatat log pemuatan saat Loading, memutakhirkan nilai state `_listGame` saat Success, dan mencatat galat log saat terjadi kondisi Error.

Line 55-60 : Blok fungsi ini mendefinisikan fungsi selectGame untuk memperbarui status game yang dipilih (`_selectedGame`), mencatat log pemilihan, serta memanggil SharedPreferences lokal untuk mencatat nama game terakhir yang dibuka pengguna secara persisten.
```

---

## 20. GameViewModelFactory.kt

* **Lokasi File:** `app/src/main/java/com/example/gamelist/feature/game/presentation/viewModel/GameViewModelFactory.kt`

```text
Line 1-11 : Blok fungsi ini mengimpor Context Android, Factory dari ViewModelProvider, database AppDatabase, modul NetworkModule, kelas pembungkus AppPreferences, API Service, dan implementasi konkret repositori.

Line 12-24 : Blok fungsi ini mendeklarasikan kelas GameViewModelFactory yang mengimplementasikan ViewModelProvider.Factory untuk menyusun dependensi secara manual. Metode create membuat instansi GameViewModel dengan menginisiasi layanan API Retrofit, koneksi database lokal Room, instansiasi GameRepositoryImpl, preferensi AppPreferences, dan mengembalikannya sebagai kelas ViewModel terkait.
```
