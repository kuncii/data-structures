# Sistem Antrian Bank Java

Program simulasi antrean bank dengan prioritas nasabah VIP, tetapi tetap mencegah *starvation* nasabah reguler.

## Metode

Program menggunakan dua antrean terpisah:
- Antrean VIP
- Antrean reguler

Penjadwalan menggunakan **weighted round-robin 3:1** dengan pola tiga slot VIP dan satu slot reguler. Jika antrean yang dituju oleh slot jadwal kosong, program melayani antrean lain yang masih memiliki nasabah.

Jika kedua antrean terus berisi, nasabah reguler memperoleh kesempatan pada slot keempat dalam setiap siklus jadwal empat pelayanan.

## Struktur data

Kedua antrean diimplementasikan sendiri menggunakan array sirkular Java. Program tidak menggunakan `java.util.Queue`, `LinkedList`, `ArrayList`, atau `PriorityQueue`.

## Cara menjalankan

Pastikan Java JDK sudah terpasang, lalu jalankan:

```bash
javac Main.java
java Main
```

## Kompleksitas

| Operasi | Kompleksitas waktu |
|---|---:|
| enqueue VIP | O(1) |
| enqueue reguler | O(1) |
| dequeue | O(1) |
| displayQueue | O(n) |

Ruang yang digunakan adalah O(C), dengan C kapasitas masing-masing antrean. Pada contoh ini, setiap antrean memiliki kapasitas tetap 100 elemen.

## Catatan implementasi

- Jika antrean penuh, `enqueue` mengembalikan `false` dan nasabah tidak dimasukkan.
- Jadwal tetap membuat rasio prioritas mudah dipahami.
- Saat salah satu antrean kosong, slot yang tidak terpakai dialihkan ke antrean yang masih berisi.
