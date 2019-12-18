# Jasapedia
Jasapedia merupakan sebuah aplikasi penyedia layanan jasa. Di aplikasi ini, pengguna dapat mencari dan menemukan berbagai macam jenis layanan jasa yang ingin digunakan olehnya, seperti jasa pijat urut, jasa laundry, jasa kecantikan, dll. Selain itu, pengguna juga dapat menambah layanan jasa baru serta memasukkan informasi dari layanan jasa tersebut. Pada aplikasi ini juga tersedia fitur booking, dimana pengguna dapat memesan suatu layanan jasa dengan memasukkan tanggal dan waktu pemesanan pemesanan, sehingga nantinya pada tanggal dan waktu tersebut pengguna akan mendapatkan notifikasi untuk menggunakan layanan jasa yang telah dipesan.

# Requirement dan Teknologi
Menerapkan seluruh stack Android Framework standard
- Menerapkan activity
- Menggunakan Service. Untuk penggunaan service, aplikasi ini menggunakan class AlarmManager yang menyediakan akses terhadap service Alarm pada android. Dengan service ini, pengguna dapat menjadwalkan fitur notifikasi booking untuk dijalankan di masa depan.
- Memanggil Remote Method Untuk pemanggilan remote method, aplikasi ini menggunakan library Retrofit untuk membuat HTTP request dan memproses HTTP response dari REST Api dalam bentuk JSON. REST Api yang saya gunakan adalah FusionApi milik yelp.com (https://www.yelp.com/developers/documentation/v3/all_categories). Saya menggunakan Gson untuk melakukan mapping Json. Saya melakukan pemanggilan GET untuk mendapatkan list judul kategori bisnis yang telah didefinisikan sebelumnya oleh yelp.com. List ini kemudian saya gunakan sebagai pilihan kategori pada service category. Pemanggilan remote method bekerja dengan langkah-langkah berikut.
- Membuat objek retrofit dengan baseUrl “https://api.yelp.com/v3/”
Membuat objek yelpApi dengan interface YelpApi, dimana objek ini berisi method-method HTTP request dan HTTP response untuk memanipulasi data pada REST Api. Pada objek, saya hanya menggunakan method getCategories pemanggilan GET dan Header statis.
Melakukan callback getCategories. Response body kemudian disimpan dalam bentuk array dan dimasukkan ke dalam spinner pada service category.

- Memanfaatkan Content Provider
File gambar yang telah diunggah dan disimpan akan ditampilkan pada lokasi paling atas halaman service details
Rancangan ide ini saya ganti, dikarenakan penyimpanan gambar sudah saya lakukan dengan cloudinary (https://cloudinary.com/) agar data tersimpan di cloud dan tidak memakan storage perangkat. Pemanfaatan content provider diganti menjadi penyimpanan nomor telepon pada kontak perangkat dengan menggunakan ContactsProvider. ContactsProvider merupakan adalah komponen Android yang kuat dan fleksibel yang dapat mengelola repositori data perangkat tentang kontak orang-orang. Isi penyimpanan dengan contactsProvider dapat dilihat pada contact atau kontak perangkat. Saya memanfaatkan content provider pada AddServiceActivity, yaitu dengan melakukan penyimpanan kontak baru dari layanan jasa yang dibuat beserta nomor teleponnya. Fitur ini akan berguna untuk pengguna yang ingin menghubungi secara langsung layanan jasa yang telah dia simpan pada aplikasi.
Berikut adalah gambar potongan kode dari pemanfaatan content provider.

- Menerapkan BroadcastReceiver
Aplikasi ini memanfaatkan broadcastreceiver dengan sesuai dengan rancangan ide, yaitu dengan memunculkan push notification ketika ketika pengguna sudah berada pada tanggal dan waktu dari suatu pemesanan yang telah dibuat sebelumnya. Seperti yang telah dijelaskan pada bagian Service, saya menerapkan BroadcastReceiver pada class AlertReceiver. AlertReceiver memiliki method onReceive yang akan dipanggil ketika BroadcastReceiver menerima siaran Intent. Method ini kemudian akan melakukan pembuatan NotificationHelper, dimana didalamnya akan dibuat objek Notification menggunakan NotificationManager. Setelah Alarm dijalankan, method BroadcastReceiver ini akan menyiarkan notifikasi pada aplikasi.
Berikut adalah gambar potongan kode dari penerapan BroadcastReceiver.

- Menerapkan Background Process (cth: AsyncTask) yang tidak “mati” ketika activity tidak aktif
Aplikasi ini menerapkan background process sesuai dengan rancangan ide, yaitu dengan memanfaatkan menjalankan fungsi alarm di background aplikasi ketika pengguna membuat pemesanan (booking) sebuah jasa pada tanggal dan waktu tertentu. Aplikasi ini akan terus berjalan di background untuk menampilkan push notification ketika pengguna telah berada pada tanggal dan waktu tersebut.
Keseluruhan proses dari fitur ini berada pada class AlarmManager. Class ini bekerja ketika pengguna melakukan pembuatan AlarmManager sesuai dengan tanggal dan waktu yang telah ditentukan sebelumnya. Kemudian aplikasi akan meminta AlarmManager mengirim intent untuk AlertReceiver. Kemudian akan dibuat pendingIntent, yaitu intent yang memiliki deskripsi dan target action untuk dilakukan dengannya. Terakhir, alarmManager akan mengeset berjalannya alarm dengan method setExact, dengan parameter metode alarm (RTC_WAKEUP) beserta tanggal waktu dan pendingIntent yang telah dibuat sebelumnya. Method setExact ini akan berjalan di backgroud hingga tanggal dan waktu yang telah ditetapkan.
Berikut adalah gambar potongan kode dari penerapan Background Process.

- Menerapkan multi environment
Multi Layout
Minimal 2 (dua) screen size dengan layout proporsional untuk portrait maupun landscape.	
Pada rancangan ide, aplikasi ini menerapkan multi layout pada beberapa halaman seperlunya, dengan mengubah lokasi dari fragmen-fragmen pada halaman tersebut sesuai dengan screen size. Ada dua screen size yang disediakan, yaitu dengan ukuran 320dp (ukuran layar handphone Android pada umumnya) dan 720dp (ukuran layar tablet 10 inch). Berikut ini adalah ilustrasi dari penerapan multi layout pada  halaman service details.

Pada pelaksanaan implementasinya, sampai sekarang aplikasi belum menerapkan multi layout dikarenakan tidak memiliki cukup waktu untuk menyelesaikan fitur ini.

Multi Language (i18n)
Minimal 2 bahasa. Memanfaatkan data konfigurasi language setting yang dipilih oleh user pada Android Settings/Configuration.
Sesuai dengan rancangan ide, aplikasi ini bisa menerapkan multi language pada semua halaman, dengan menampilkan resource menggunakan Bahasa Inggris atau Bahasa Indonesia sesuai dengan pengaturan bahasa yang diterapkan device pengguna. Berikut ini adalah ilustrasi dari penerapan multi language pada halaman service details.

Tampilan Bahasa Inggris

Tampilan Bahasa Indonesia

Penerapan multi language dilakukan dengan memanfaatkan resource strings.xml pada package values. Seluruh textView, label, button, serta objek lainnya memiliki dua string resource dengan dua bahasa yang berbeda, yaitu bahasa Inggris dan bahasa Indonesia.
Berikut adalah gambar potongan kode dari strings.xml Bahasa Inggris.


Berikut adalah gambar potongan kode dari strings.xml Bahasa Indonesia.



Menerapkan Design Pattern MVVM & Background Task
Setiap komponen (cth: Tombol, TextBox, Menu) yang tampil, memiliki ViewModel sehingga behaviour dari komponen tersebut memiliki sifat disjoint dengan komponen lainnya (relatif lebih independen)
	Sesuai dengan rancangan ide, aplikasi ini menerapkan MVVM sesuai dengan keperluan. Penerapan MVVM digunakan untuk menjaga kode UI agar tetap sederhana dan tanpa mengandung app logic sehingga lebih mudah untuk dikelola. Model aplikasi terdiri atas Booking, Service, dan User. ViewModel aplikasi terdiri atas BookingViewModel, ServiceViewModel, dan UserViewModel. View aplikasi teridiri atas setiap activity aplikasi. Pada penerapannya, MVVM dikolaborasikan dengan datapersistence. ViewModel selalu digunakan pada setiap activity atau fragment yang menampilkan informasi atau data dari object, dengan menerapkan method ViewModelProviders. 
Berikut adalah gambar potongan kode dari penerapan MVVM.





Melakukan Binding antara ViewModel dengan Model
	Saya menerapkan binding antara ViewModel dengan Model pada LoginActivity.
Berikut adalah gambar potongan kode dari penerapan binding.

	
Menerapkan Assets dengan benar
String resource dipergunakan untuk menyimpan label untuk segala teks statik yang dipergunakan di dalam aplikasi dan harus mendukung i18n (minimal 2 bahasa).
	Aplikasi ini mendukung perbedaan bahasa Inggris dan bahasa Indonesia dalam penggunaanya. Perbedaan resource files untuk bahasa yang berbeda pada device dilakukan seperti berikut:
Untuk string resource ketika bahasanya adalah Bahasa Inggris (default locale), aplikasi ini menggunakan /values/strings.xml
Untuk string resource ketika bahasanya adalah Bahasa Indonesia (in locale), aplikasi ini menggunakan /values-in/strings.xml

Berikut adalah gambar potongan kode dari strings.xml Bahasa Inggris.


Berikut adalah gambar potongan kode dari strings.xml Bahasa Indonesia.


Mempersiapkan drawable resource untuk 2 (dua) screen size (& beda resolusi).
	Pada rancangan ide, aplikasi ini menyediakan dua screen size, yaitu dengan ukuran 320dp (ukuran layar handphone Android pada umumnya) dan 720dp (ukuran layar tablet 10 inch). Namun, saat ini aplikasi belum menerapkan hal tersebut.
Memanfaatkan ContentProvider dan turunannya untuk keperluan penyimpanan data. Cth: FileProvider, DocumentProvider, ImageProvider, dll.
	Berbeda dengan rancangan ide, aplikasi ini memanfaatkan ContactsProvider untuk keperluan penyimpanan kontak dari layanan jasa yang telah digunakan.

Menerapkan Data Persistence
Menggunakan Room Persistence Library untuk melakukan penyimpanan data di dalam perangkat. Data yang disimpan paling tidak terdiri dari 3 Entity.
Aplikasi ini memanfaatkan room persistence library dengan menyimpan tiga entity data, yaitu entity Service, entity Booking, dan entity User. Dalam room terdapat tiga komponen utama: Database, DAO (Objek Akses Data), dan Entity. Setiap komponen memiliki tanggung jawabnya, dan semuanya harus diimplementasikan agar sistem berfungsi. Entity adalah kelas yang disimpan di Database. Tabel database eksklusif dibuat untuk setiap kelas yang diberi catatan @Entity. DAO adalah antarmuka yang dianotasikan dengan @Dao yang memediasi akses ke objek dalam database dan tabelnya. Ada empat anotasi khusus untuk operasi dasar DAO: @Insert, @Update, @Delete, dan @Query. Komponen Database adalah kelas abstrak yang dianotasikan @Database, yang memperluas RoomDatabase. Kelas mendefinisikan daftar Entitas dan DAO-nya.
Berikut adalah gambar potongan kode dari contoh penerapan DataPersistence pada entitas Booking.




