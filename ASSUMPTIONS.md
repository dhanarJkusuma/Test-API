Backend Developer Mini Project Assignment
====

Introduction
----
Dari pembuatan aplikasi backend ini, saya menggunakan Spring Framework, dan PostgreSQL.
Dan beberapa library seperti mapstruct untuk mapping, dan flyway untuk migrasi database.
Untuk file swagger sudah saya tambahkan di folder spec.
Untuk migrasi ada di folder resources > db > migration


Asumsi
-----
Dari project di atas saya pada awalnya mencoba membuka website loket.com.
Dan dari website tersebut saya membuat sebuah gambaran bahwa user membuat ticket dengan cara langsung post api secara serentak.
maksud saya user pertama kali membuat `Location` terlebih dahulu, dengan response yg mengembalikan data id location, maka
dapat dilanjutkan dengan mem-POST data `Event` yang juga mengembalikan data response eventId yang digunakan untuk membuat `Ticket`.
Sehingga dalam sekali klik asumsi saya terjadi 3 kali pemanggilan API POST, dan apabila ada gambar maka akan diupload setelahnya.
tetapi walaupun begitu, saya menyediakan api untuk mendapatkan daftar dari data `Location`, `Event`, dan `Ticket`.
Ketika user purchase transaksi, saya menganggap bahwa transaksi pembelian telah selesai, sehingga tidak ada status pembayaran ataupun data pembayaran.
Dari data event, terdapat attribute category, yang didapatkan dari data category yang sebelumnya harus di tambahkan terlebih dahulu.
Maka dari itu, saya membuat API /internal untuk memanajemen category sesuai yang diinginkan.
Untuk filter data event, saya hanya memberikan pilihan untuk fitler by name, tanggal, kota, dan kategori.
Untuk data location, saya beranggapan bahwa city memiliki standar penulisan yang sama, misal DKI Jakarta, Depok, Semarang.
Sehingga saya tidak menambahkan data table kota atau semacamnya.
Untuk category, dan event, saya menggunakan slug untuk pencarian pengunjung, slug tersebut unique (berdasar kode). Jika ada slug yang sama,
maka akan ditambahkan hash dari Date().

