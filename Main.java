class Nasabah {
    String nama;
    String jenis;

    Nasabah(String nama, String jenis) {
        this.nama = nama;
        this.jenis = jenis;
    }
}

// Antrean dengan array sirkular buatan sendiri.
class CircularQueue {
    private final Nasabah[] data;
    private int front;
    private int size;
    private final int capacity;

    CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Nasabah[capacity];
        this.front = 0;
        this.size = 0;
    }

    // Menambahkan nasabah ke belakang antrean: O(1).
    public boolean enqueue(Nasabah nasabah) {
        if (size == capacity) {
            return false;
        }

        int rear = (front + size) % capacity;
        data[rear] = nasabah;
        size++;
        return true;
    }

    // Menghapus nasabah dari depan antrean: O(1).
    public Nasabah dequeue() {
        if (size == 0) {
            return null;
        }

        Nasabah hasil = data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        return hasil;
    }

    public Nasabah peekAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return data[(front + index) % capacity];
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

public class Main {
    private static final int KAPASITAS = 100;

    private static final CircularQueue vip =
            new CircularQueue(KAPASITAS);
    private static final CircularQueue reguler =
            new CircularQueue(KAPASITAS);

    // Pola tiga giliran VIP dan satu giliran reguler.
    private static final String[] JADWAL = {
        "VIP", "VIP", "VIP", "REGULER"
    };

    private static int giliran = 0;

    public static void enqueueVIP(String nama) {
        if (vip.enqueue(new Nasabah(nama, "VIP"))) {
            System.out.println(nama + " masuk antrean VIP");
        } else {
            System.out.println("Antrean VIP penuh: " + nama);
        }
    }

    public static void enqueueReguler(String nama) {
        if (reguler.enqueue(new Nasabah(nama, "Reguler"))) {
            System.out.println(nama + " masuk antrean Reguler");
        } else {
            System.out.println("Antrean reguler penuh: " + nama);
        }
    }

    // Mengambil nasabah mengikuti jadwal berbobot.
    // Jika antrean pada slot jadwal kosong, antrean lain
    // yang masih berisi langsung dilayani.
    public static Nasabah dequeue() {
        if (vip.isEmpty() && reguler.isEmpty()) {
            return null;
        }

        String target = JADWAL[giliran];
        giliran = (giliran + 1) % JADWAL.length;

        if (target.equals("VIP") && !vip.isEmpty()) {
            return vip.dequeue();
        }

        if (target.equals("REGULER") && !reguler.isEmpty()) {
            return reguler.dequeue();
        }

        if (!vip.isEmpty()) {
            return vip.dequeue();
        }

        return reguler.dequeue();
    }

    // Menampilkan simulasi urutan layanan tanpa mengubah antrean.
    public static void displayQueue() {
        int i = 0;
        int j = 0;
        int posisi = giliran;
        int nomor = 1;

        System.out.println("\n=== PERKIRAAN URUTAN PELAYANAN ===");

        while (i < vip.getSize() || j < reguler.getSize()) {
            String target = JADWAL[posisi];
            Nasabah nasabah;

            if (target.equals("VIP") && i < vip.getSize()) {
                nasabah = vip.peekAt(i++);
            } else if (target.equals("REGULER")
                    && j < reguler.getSize()) {
                nasabah = reguler.peekAt(j++);
            } else if (i < vip.getSize()) {
                nasabah = vip.peekAt(i++);
            } else {
                nasabah = reguler.peekAt(j++);
            }

            System.out.println(nomor++ + ". " + nasabah.nama
                    + " (" + nasabah.jenis + ")");
            posisi = (posisi + 1) % JADWAL.length;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== PENDAFTARAN NASABAH ===");

        enqueueVIP("Andi");
        enqueueVIP("Budi");
        enqueueVIP("Citra");
        enqueueReguler("Dina");
        enqueueReguler("Eka");
        enqueueVIP("Fajar");
        enqueueVIP("Gina");
        enqueueVIP("Hadi");
        enqueueVIP("Indra");

        displayQueue();

        System.out.println("\n=== HASIL PELAYANAN BANK ===");
        Nasabah nasabah;
        int nomor = 1;

        while ((nasabah = dequeue()) != null) {
            System.out.println(nomor++ + ". Melayani "
                    + nasabah.nama + " - " + nasabah.jenis);
        }

        System.out.println("\nSemua nasabah telah dilayani.");
    }
}
